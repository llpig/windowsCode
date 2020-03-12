package com.kong.nightrunning;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

public class NightRunningService extends Service {
    private Sensor mSensor;
    //数据库
    private NightRunningDatabase helper;
    private SQLiteDatabase db;
    private SharedPreferences preferences;
    private String userName;
    //广播接收者
    private BroadcastReceiver broadcastReceiver;
    //工具类
    private Tool tool;
    private Tool.NightRunningDB nightRunningDB;

    ContentValues values;
    int lastTodayAddStepNumber;

    NightRunningSensorEventListener sensorEventListener;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //测试数据（只允许本程序访问）
        preferences = getSharedPreferences("test1", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("userName", "测试1");
        editor.commit();
        initService();
    }

    //初始化服务器
    private void initService() {
        tool = new Tool();
        nightRunningDB = tool.new NightRunningDB();
        userName = preferences.getString("userName", "");
        helper = new NightRunningDatabase(getApplicationContext(), "NightRunning", null, 1);
        db = helper.getReadableDatabase();
        sensorEventListener = new NightRunningSensorEventListener();
        initRelevantData();
        mSensor = sensorEventListener.registerSensor(this);
        if (mSensor != null) {
            registerBroadcastReceiver();
            sendBroadcastToMainActivity();
        }
    }

    //初始化相关数据
    private boolean initRelevantData() {
        boolean bRet = true;
        values = helper.selectRecordsToMotionInfoTable(db, userName, "date('now','localtime')");
        //如果values的值为0,则说明该当天跑步记录没有被创建。
        if (values.size() == 0) {
            if (helper.insertRecordsToMotionInfoTable(db, userName)) {
                //重新查询
                values = helper.selectRecordsToMotionInfoTable(db, userName, "date('now','localtime')");
            } else {
                //如果没有创建成功，则提醒用户登录,并发送广播，退出服务
                bRet = false;
            }
        } else {
            lastTodayAddStepNumber = 0;
            int startStepNumber = (int) values.get(nightRunningDB.motionInfoTable.stepNumber);
            int isOff = (int) values.get(nightRunningDB.motionInfoTable.equipmentInfo);
            Log.v("message", "startStepNumber:" + startStepNumber + ",isOff:" + isOff);
            sensorEventListener.updateData(startStepNumber, isOff);
        }
        return bRet;
    }

    //发送广播，通知MainActivity
    private void sendBroadcastToMainActivity() {
        int delayTime = 0, periodTime = 3000;
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                int todayAddStepNumber = sensorEventListener.getTodayAddStepNumber();
                if (lastTodayAddStepNumber != todayAddStepNumber) {
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

    private void registerBroadcastReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_TIME_TICK);
        filter.addAction(Intent.ACTION_SHUTDOWN);
        broadcastReceiver = new NightRunningBroadcastReceiver();
        registerReceiver(broadcastReceiver, filter);
    }

    public void sensorUpdateData(int startStepNumber){
        helper.upDateRecordsToMotionInfoTableNormal(db,userName,"date('now','localtime')",startStepNumber,0);
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
            helper.upDateRecordsToMotionInfoTableNormal(db, userName, "date('now','localtime')", sensorEventListener.getTodayAddStepNumber(), 0);
        }
        //当服务关闭时，将数据库关闭
        helper.close();
    }

    public class NightRunningBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                //关机
                case Intent.ACTION_SHUTDOWN: {
                    //将当前数据更新入数据中
                    helper.upDateRecordsToMotionInfoTableNormal(db, userName, "date('now','localtime')", sensorEventListener.getTodayAddStepNumber(), 1);
                }
                //日期改变
                case Intent.ACTION_TIME_TICK: {
                    Calendar calendar = Calendar.getInstance();
                    int hour = calendar.get(Calendar.HOUR);
                    int minute = calendar.get(Calendar.MINUTE);
                    int second = calendar.get(Calendar.SECOND);
                    if (hour == 0 && minute == 0 && second == 0) {
                        //将前一天的数据更新入数据库中
                        helper.upDateRecordsToMotionInfoTableNormal(db, userName, "date('now','localtime','-1 days')", sensorEventListener.getTodayAddStepNumber(), 0);
                        //插入一条今天新的记录
                        helper.insertRecordsToMotionInfoTable(db, userName);
                        //重新对数据进行初始化
                        initRelevantData();
                    }
                }
            }
        }
    }
}