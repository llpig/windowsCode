package com.kong.nightrunning;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

public class RecordStepNumberService extends Service implements SensorEventListener {
    //今日新增步数=今日新增步数+当前记录
    private static int todayAddStepNumber = 0;
    private int lastTodayAddStepNumber = todayAddStepNumber;
    //获取数据库
    public NightRunDatabase helper;
    private SQLiteDatabase db;
    private int todayStartStepNumber;
    private int todayShutdownStepNumber;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initDatabase();
        initSensor();
        final Intent intent = new Intent();
        intent.setAction(getPackageName() + ".UPDATESTEPNUMBER_BROADCAST");
        final Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        int delayTime = 0, periodTime = 3000;
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (lastTodayAddStepNumber != todayAddStepNumber) {
                    bundle.putInt("currentStepNumber", todayAddStepNumber);
                    //发送广播
                    sendBroadcast(intent);
                    //停止前台服务
                    stopForeground(Tool.MessageTypeEnum.FOREGROUNDSERVICE.getIndex());
                    //重新启动前台服务
                    startForeground(Tool.MessageTypeEnum.FOREGROUNDSERVICE.getIndex(), getNotification(String.valueOf(todayAddStepNumber)));
                    lastTodayAddStepNumber = todayAddStepNumber;
                }
            }
        };
        Timer timer = new Timer();
        //每隔30s，检测一次数据，如果数据有更新则发送一次广播，通知UI线程更新UI
        timer.schedule(timerTask, delayTime, periodTime);
    }

    public void initDatabase(){
        helper = new NightRunDatabase(getApplicationContext(), "NightRunning", null, 1);
        db = helper.getReadableDatabase();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(Tool.MessageTypeEnum.FOREGROUNDSERVICE.getIndex(), getNotification(null));
        return START_STICKY;
    }

    private Notification getNotification(String contentText) {
        //设置意图
        Intent notificationIntent = new Intent(this, MainActivity.class);
        //点击通知将打开主活动
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        //获取一个通知构造器
        Notification.Builder builder = new Notification.Builder(this.getApplicationContext());
        //设置通知的图标
        builder.setSmallIcon(R.drawable.logo);
        //设置标题
        builder.setContentTitle("NightRunning");
        String text = "当前步数" + contentText + "步";
        //设置通知内容
        builder.setContentText(text);
        //设置意图
        builder.setContentIntent(pendingIntent);
        //从建造者中获取通知
        return builder.getNotification();
    }

    //获取当前总步数
    public static int getCurrentTotalStepNumber() {
        return todayAddStepNumber;
    }

    //获取并注册加速度传感器
    private void initSensor() {
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
        todayAddStepNumber = todayShutdownStepNumber + (value - todayStartStepNumber);
        Log.v("message","todayAddStepNumber:"+todayAddStepNumber+",todayStartStepNumber:"+todayStartStepNumber+",value:"+value);
    }

    //单步记步传感器
    private void calcTodayStepNumber(float value) {
        todayAddStepNumber += 1;
    }

    //加速度传感器
    private void calcTodayStepNumber(float xValue, float yValue, float zValue) {
        todayAddStepNumber += 1;
    }


    //当传感器的精度发生变化时，会自动调用该接口
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    //当服务被注销时需要将当数据存入数据库中
    @Override
    public void onDestroy() {
        super.onDestroy();
        //当服务关闭时，将数据库关闭

        helper.close();
    }
}
