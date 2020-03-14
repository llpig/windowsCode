package com.kong.nightrunning;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class NightRunningSensorEventListener implements SensorEventListener {

    //加速度传感器相关数据
    private final int ValueNum = 4;
    //用于存放计算阈值的波峰波谷差值
    private float[] tempValue = new float[ValueNum];
    private int tempCount = 0;
    //是否上升的标志位
    private boolean isDirectionUp = false;
    //持续上升次数
    private int continueUpCount = 0;
    //上一点的持续上升的次数，为了记录波峰的上升次数
    private int continueUpFormerCount = 0;
    //上一点的状态，上升还是下降
    private boolean lastStatus = false;
    //波峰值
    private float peakOfWave = 0;
    //波谷值
    private float valleyOfWave = 0;
    //此次波峰的时间
    private long timeOfThisPeak = 0;
    //上次波峰的时间
    private long timeOfLastPeak = 0;
    //当前的时间
    private long timeOfNow = 0;
    //当前传感器的值
    private float gravityNew = 0;
    //上次传感器的值
    private float gravityOld = 0;
    //动态阈值需要动态的数据，这个值用于这些动态数据的阈值
    private final float InitialValue = (float) 1.3;
    //初始阈值
    private float ThreadValue = (float) 2.0;
    //波峰波谷时间差
    private int TimeInterval = 250;
    //公用数据
    int mStartStepNumber = 0, mIsOff = 0;
    private static int todayAddStepNumber;
    NightRunningService mService;

    public Sensor registerSensor(NightRunningService service) {
        mService=service;
        //获取传感器管理类(计步总数传感器、单步计数传感器、加速度传感器三选一)
        SensorManager sensorManager = (SensorManager) service.getSystemService(service.SENSOR_SERVICE);
        //计录总数传感器
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (sensor == null) {
            //单步计数传感器
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
            if (sensor == null) {
                //加速度传感器
                sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            }
        }
        if (sensor != null) {
            todayAddStepNumber = 0;
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            todayAddStepNumber = -1;
        }
        return sensor;
    }

    public static int getTodayAddStepNumber() {
        return todayAddStepNumber;
    }

    //更新数据
    public void updateData(int startStepNumber, int isOff) {
        mStartStepNumber = startStepNumber;
        mIsOff = isOff;
    }

    //当传感器的数值发生变化时，会自动调用该接口
    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()) {
            case Sensor.TYPE_STEP_COUNTER: {
                calcTodayStepNumber(((int) event.values[0]));
                break;
            }
            case Sensor.TYPE_STEP_DETECTOR: {
                calcTodayStepNumber(event.values[0]);
                break;
            }
            case Sensor.TYPE_ACCELEROMETER: {
                calcTodayStepNumber(event.values[0], event.values[1], event.values[2]);
                break;
            }
        }
    }

    //计算当前步数，不同的传感器调用不同的算法(计步器传感器)
    private void calcTodayStepNumber(int value) {
        if (mStartStepNumber == 0 && value != 0) {
            mStartStepNumber = value;
            mService.sensorUpdateData(mStartStepNumber);
        }
        if (mIsOff == 0) {
            todayAddStepNumber = value - mStartStepNumber;
        } else {
            todayAddStepNumber = value + mStartStepNumber;
        }
    }

    //单步记步传感器
    private void calcTodayStepNumber(float value) {
        if (value == 1.0) {
            mStartStepNumber += 1;
            todayAddStepNumber = mStartStepNumber;
        }
    }

    //加速度传感器
    private void calcTodayStepNumber(float xValue, float yValue, float zValue) {
        gravityNew = (float) Math.sqrt(xValue * xValue
                + yValue * yValue + zValue * zValue);
        detectorNewStep(gravityNew);
    }

    /*
     * 检测步子，并开始计步
     * 1.传入sersor中的数据
     * 2.如果检测到了波峰，并且符合时间差以及阈值的条件，则判定为1步
     * 3.符合时间差条件，波峰波谷差值大于initialValue，则将该差值纳入阈值的计算中
     * */
    public void detectorNewStep(float values) {
        if (gravityOld == 0) {
            gravityOld = values;
        } else {
            if (detectorPeak(values, gravityOld)) {
                timeOfLastPeak = timeOfThisPeak;
                timeOfNow = System.currentTimeMillis();
                if (timeOfNow - timeOfLastPeak >= TimeInterval
                        && (peakOfWave - valleyOfWave >= ThreadValue)) {
                    timeOfThisPeak = timeOfNow;
                    //统计步数
                    mStartStepNumber += 1;
                    todayAddStepNumber = mStartStepNumber;
                }
                if (timeOfNow - timeOfLastPeak >= TimeInterval
                        && (peakOfWave - valleyOfWave >= InitialValue)) {
                    timeOfThisPeak = timeOfNow;
                    ThreadValue = peakValleyThread(peakOfWave - valleyOfWave);
                }
            }
        }
        gravityOld = values;
    }

    /*
     * 检测波峰
     * 以下四个条件判断为波峰：
     * 1.目前点为下降的趋势：isDirectionUp为false
     * 2.之前的点为上升的趋势：lastStatus为true
     * 3.到波峰为止，持续上升大于等于2次
     * 4.波峰值大于20
     * 记录波谷值
     * 1.观察波形图，可以发现在出现步子的地方，波谷的下一个就是波峰，有比较明显的特征以及差值
     * 2.所以要记录每次的波谷值，为了和下次的波峰做对比
     * */
    public boolean detectorPeak(float newValue, float oldValue) {
        lastStatus = isDirectionUp;
        if (newValue >= oldValue) {
            isDirectionUp = true;
            continueUpCount++;
        } else {
            continueUpFormerCount = continueUpCount;
            continueUpCount = 0;
            isDirectionUp = false;
        }

        if (!isDirectionUp && lastStatus
                && (continueUpFormerCount >= 2 || oldValue >= 20)) {
            peakOfWave = oldValue;
            return true;
        } else if (!lastStatus && isDirectionUp) {
            valleyOfWave = oldValue;
            return false;
        } else {
            return false;
        }
    }

    /*
     * 阈值的计算
     * 1.通过波峰波谷的差值计算阈值
     * 2.记录4个值，存入tempValue[]数组中
     * 3.在将数组传入函数averageValue中计算阈值
     * */
    public float peakValleyThread(float value) {
        float tempThread = ThreadValue;
        if (tempCount < ValueNum) {
            tempValue[tempCount] = value;
            tempCount++;
        } else {
            tempThread = averageValue(tempValue, ValueNum);
            for (int i = 1; i < ValueNum; i++) {
                tempValue[i - 1] = tempValue[i];
            }
            tempValue[ValueNum - 1] = value;
        }
        return tempThread;

    }

    /*
     * 梯度化阈值
     * 1.计算数组的均值
     * 2.通过均值将阈值梯度化在一个范围里
     * */
    public float averageValue(float value[], int n) {
        float ave = 0;
        for (int i = 0; i < n; i++) {
            ave += value[i];
        }
        ave = ave / ValueNum;
        if (ave >= 8)
            ave = (float) 4.3;
        else if (ave >= 7 && ave < 8)
            ave = (float) 3.3;
        else if (ave >= 4 && ave < 7)
            ave = (float) 2.3;
        else if (ave >= 3 && ave < 4)
            ave = (float) 2.0;
        else {
            ave = (float) 1.3;
        }
        return ave;
    }

    //当传感器的精度发生变化时，会自动调用该接口
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
