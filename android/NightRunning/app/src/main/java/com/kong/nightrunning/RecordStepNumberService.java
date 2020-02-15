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
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

public class RecordStepNumberService extends Service implements SensorEventListener {
    //今日新增步数
    private static int todayAddStepNumber = 0;
    private int[] stepData = new int[]{0, 0};
    //数据库
//    NightRunDatabase databaseHelper = MainActivity.databaseHelper;
//    SQLiteDatabase db = databaseHelper.getReadableDatabase();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //生成
    @Override
    public void onCreate() {
        super.onCreate();
        getSensor();
        final Intent intent = new Intent();
        intent.setAction(getPackageName() + ".UPDATESTEPNUMBER_BROADCAST");
        final Bundle bundle = new Bundle();
        int delayTime = 0, periodTime = 1000;
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                bundle.putInt("currentStepNumber", todayAddStepNumber);
                intent.putExtras(bundle);
                //发送广播
                sendBroadcast(intent);
                //停止前台服务
                stopForeground(Tool.MessageTypeEnum.FOREGROUNDSERVICE.getIndex());
                //重新启动前台服务
                startForeground(Tool.MessageTypeEnum.FOREGROUNDSERVICE.getIndex(),getNotification(String.valueOf(todayAddStepNumber)));
                //模拟更新数据
                todayAddStepNumber += 10;
            }
        };
        Timer timer = new Timer();
        //每隔30s，检测一次数据，如果数据有更新则发送一次广播，通知UI线程更新UI
        timer.schedule(timerTask, delayTime, periodTime);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(Tool.MessageTypeEnum.FOREGROUNDSERVICE.getIndex(), getNotification(null)
        );
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
//                //stepData=databaseHelper.selectRecords(db);
//                //今日起始步数为0,从此时开始记录步数
//                if(stepData[0]==0){
//                    databaseHelper.updateRecords(db,(int)event.values[0],stepData[1]);
//                    stepData[0]=(int)event.values[0];
//                }
//                todayAddStepNumber=(int)event.values[0]-stepData[0]+stepData[1];
//                Log.v("database","今日起始步数："+String.valueOf(stepData[0])+"今日总步数："+String.valueOf(stepData[1])+
//                        "今日累计步数："+String.valueOf(todayAddStepNumber));
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

}
