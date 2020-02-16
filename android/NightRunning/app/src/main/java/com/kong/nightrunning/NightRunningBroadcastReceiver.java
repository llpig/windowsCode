package com.kong.nightrunning;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.util.Calendar;
import android.util.Log;

public class NightRunningBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
//        String action=intent.getAction();
//        NightRunDatabase helper=MainActivity.databaseHelper;
//        SQLiteDatabase db=helper.getReadableDatabase();
//        int currentStepNumber=RecordStepNumberService.getCurrentTotalStepNumber();
//        //从数据库中取出今日计数器传感器的起始数据（如果采用单步计数器和加速度传感器该数据为0）
//        int startStepNumber=(helper.selectRecords(db))[0];
//        switch (action){
//            //关机
//            case Intent.ACTION_SHUTDOWN:{
//                //将当前数据更新入数据中
//                helper.updateRecords(db,0,currentStepNumber);
//            }
//            //日期改变
//            case Intent.ACTION_TIME_TICK:{
//                Calendar calendar=Calendar.getInstance();
//                int hour=calendar.get(Calendar.HOUR);
//                int minute=calendar.get(Calendar.MINUTE);
//                int second=calendar.get(Calendar.SECOND);
//                if(hour==0&&minute==0&&second==0){
//                    //将数据更新入数据库中，并插入一条新的记录
//                    helper.updateRecords(db,startStepNumber,currentStepNumber);
//                    helper.insertRecords(db,startStepNumber+currentStepNumber,0);
//                }
//            }
//        }
    }
}
