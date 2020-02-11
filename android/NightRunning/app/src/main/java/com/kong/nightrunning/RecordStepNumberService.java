package com.kong.nightrunning;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class RecordStepNumberService extends Service implements SensorEventListener {
    //今日新增步数
    private static int todayAddStepNumber=0;
    private int[] stepData=new int[]{0,0};
    //启动标志
    private StepBinder stepBinder;
    //数据库
    NightRunDatabase databaseHelper=MainActivity.databaseHelper;
    SQLiteDatabase db=databaseHelper.getReadableDatabase();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        if(stepBinder==null){
            stepBinder=new StepBinder();
        }
        return stepBinder;
    }

    //在Activity中主动调用该方法(每个30s更新一次)
    public class StepBinder extends Binder {
        public int getStepNumber(){
            return todayAddStepNumber;
        }
    }

    //生成
    @Override
    public void onCreate() {
        super.onCreate();
        getSensor();
    }

    //获取当前总步数
    public static int getCurrentTotalStepNumber(){
        return todayAddStepNumber;
    }

    //从计步总数传感器、单步计步器、加速度传感器中任选一个
    private void getSensor() {
        //获取传感器管理类(计步总数传感器、单步计数传感器、加速度传感器三选一)
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
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
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    //当传感器的数值发生变化时，会自动调用该接口
    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()) {
            case Sensor.TYPE_STEP_COUNTER: {
                stepData=databaseHelper.selectRecords(db);
                //今日起始步数为0,从此时开始记录步数
                if(stepData[0]==0){
                    databaseHelper.updateRecords(db,(int)event.values[0],0);
                    stepData[0]=(int)event.values[0];
                }
                todayAddStepNumber=(int)event.values[0]-stepData[0]+stepData[1];
                Log.v("database","今日起始步数："+String.valueOf(stepData[0])+"今日总步数："+String.valueOf(stepData[1])+
                        "今日累计步数："+String.valueOf(todayAddStepNumber));
            }
            case Sensor.TYPE_STEP_DETECTOR: {

            }
            case Sensor.TYPE_ACCELEROMETER: {

            }
        }
    }

    //当传感器的精度发生变化时，会自动调用该接口
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    //销毁
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
