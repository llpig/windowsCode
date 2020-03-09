package com.kong.nightrunning;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

public class NightRunningService extends Service implements SensorEventListener {
    //今日新增步数=今日新增步数+当前记录
    private static int todayAddStepNumber = 0;
    private int lastTodayAddStepNumber = todayAddStepNumber;
    private Sensor mSensor;
    private int todayStartStepNumber = -1;
    private int todayShutdownStepNumber = -1;
    private SQLiteDatabase db;
    //数据库
    public static NightRunningDatabase helper;
    //广播接收者
    private BroadcastReceiver broadcastReceiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //获取当前总步数
    public static int getCurrentTotalStepNumber() {
        return todayAddStepNumber;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initService();
    }

    //初始化服务器
    private void initService() {
        if (registerSensor() == false) {
            Intent intent = new Intent().setAction(Tool.CustomBroadcast.NULLSERSOR.getIndex());
            sendBroadcast(intent);
            stopForeground(Tool.MessageType.FOREGROUNDSERVICE.getIndex());
        } else {
            registerBroadcastReceiver();
            helper = new NightRunningDatabase(getApplicationContext(), "NightRunning", null, 1);
            db=helper.getReadableDatabase();
//            int[] stepNumber = helper.selectRecords(db);
//            todayStartStepNumber = stepNumber[0];
//            todayShutdownStepNumber = stepNumber[1];
            sendBroadcastToMainActivity();
        }
    }

    //发送广播，通知MainActivity
    private void sendBroadcastToMainActivity() {
        final Intent intent = new Intent();
        intent.setAction(Tool.CustomBroadcast.UPDATASTEPNUMBER.getIndex());
        final Bundle bundle = new Bundle();
        int delayTime = 0, periodTime = 3000;
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (lastTodayAddStepNumber != todayAddStepNumber) {
                    bundle.putInt(String.valueOf(Tool.MessageType.CURRENTSTEPNUMBERKEY.getIndex()), todayAddStepNumber);
                    intent.putExtras(bundle);
                    //发送广播
                    sendBroadcast(intent);
                    //启动前台服务(更换文字)
                    startForeground(Tool.MessageType.FOREGROUNDSERVICE.getIndex(), getNotification(String.valueOf(todayAddStepNumber)));
                    lastTodayAddStepNumber = todayAddStepNumber;
                }
            }
        };
        Timer timer = new Timer();
        //每隔30s，检测一次数据，如果数据有更新则发送一次广播，通知UI线程更新UI
        timer.schedule(timerTask, delayTime, periodTime);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //开启前台服务
        startForeground(Tool.MessageType.FOREGROUNDSERVICE.getIndex(), getNotification(null));
        return START_STICKY;
    }

    //设置通知样式
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

    private void registerBroadcastReceiver(){
        IntentFilter filter=new IntentFilter();
        filter.addAction(Intent.ACTION_TIME_TICK);
        filter.addAction(Intent.ACTION_SHUTDOWN);
        broadcastReceiver=new NightRunningBroadcastReceiver();
        registerReceiver(broadcastReceiver,filter);
    }

    //获取并注册加速度传感器
    private boolean registerSensor() {
        boolean bRet = false;
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
            mSensor = sensor;
            bRet = true;
        }
        return bRet;
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
//        int[] stepNumber = helper.selectRecords(db);
//        //1、防止服务被杀死数据在一天内更新多次，2、防止设备关机后的错误更新。
//        if (stepNumber[0] + stepNumber[1] == 0) {
//            helper.updateRecords(db, value, 0, true);
//            stepNumber = helper.selectRecords(db);
//            todayStartStepNumber = stepNumber[0];
//            todayShutdownStepNumber = stepNumber[1];
//        }
//        todayAddStepNumber = todayShutdownStepNumber + (value - todayStartStepNumber);
    }

    //单步记步传感器
    private void calcTodayStepNumber(float value) {
        if (value == 1.0) {
            todayAddStepNumber += 1;
        }
    }

    //加速度传感器
    private void calcTodayStepNumber(float xValue, float yValue, float zValue) {

        todayAddStepNumber += 1;
    }

    //当传感器的精度发生变化时，会自动调用该接口
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    public class NightRunningBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            NightRunningDatabase helper = NightRunningService.helper;
            SQLiteDatabase db = helper.getReadableDatabase();
            int currentStepNumber = NightRunningService.getCurrentTotalStepNumber();
            //从数据库中取出今日计数器传感器的起始数据（如果采用单步计数器和加速度传感器该数据为0）
//            int startStepNumber = (helper.selectRecords(db))[0];
            switch (action) {
                //关机
                case Intent.ACTION_SHUTDOWN: {
                    //将当前数据更新入数据中
//                    helper.updateRecords(db, 0, currentStepNumber, true);
                }
                //日期改变
                case Intent.ACTION_TIME_TICK: {
                    Calendar calendar = Calendar.getInstance();
                    int hour = calendar.get(Calendar.HOUR);
                    int minute = calendar.get(Calendar.MINUTE);
                    int second = calendar.get(Calendar.SECOND);
                    if (hour == 0 && minute == 0 && second == 0) {
//                        //将前一天的数据更新入数据库中
//                        helper.updateRecords(db, startStepNumber, currentStepNumber, false);
//                        //插入一条今天新的记录
//                        helper.insertRecords(db, startStepNumber + currentStepNumber, 0);
                    }
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        startForeground(Tool.MessageType.FOREGROUNDSERVICE.getIndex(), getNotification("前台服务被杀死"));
        stopForeground(Tool.MessageType.FOREGROUNDSERVICE.getIndex());
        closeDatabase();
    }

    private void closeDatabase() {
        //当服务被注销时需要将当数据存入数据库中
        if (mSensor.getType() == Sensor.TYPE_STEP_DETECTOR || mSensor.getType() == Sensor.TYPE_ACCELEROMETER) {
//            helper.updateRecords(helper.getReadableDatabase(), 0, getCurrentTotalStepNumber(), true);
        }
        //当服务关闭时，将数据库关闭
        helper.close();
    }
}