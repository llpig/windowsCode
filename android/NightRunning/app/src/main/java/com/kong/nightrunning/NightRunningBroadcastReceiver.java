package com.kong.nightrunning;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class NightRunningBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action=intent.getAction();
        NightRunDatabase database = MainActivity.databaseHelper;
        SQLiteDatabase db = database.getReadableDatabase();
        int[] stepDate = database.selectRecords(db);
        stepDate[1] += RecordStepNumberService.getCurrentTotalStepNumber();
        switch (action){
            case Intent.ACTION_SHUTDOWN:{
                //关机：更新数据（起始步数：0，总步数）
                database.updateRecords(db,0,stepDate[1]);
                Log.v("database","关机");
                break;
            }
            case Intent.ACTION_TIME_TICK:{
                //时间变化，每分钟发送一次：（更新数据（起始步数，总步数）） 添加新记录

                database.insertRecords(db,stepDate[0]+stepDate[1],0);
                Log.v("database","日期变化");
                break;
            }
        }
    }
}
