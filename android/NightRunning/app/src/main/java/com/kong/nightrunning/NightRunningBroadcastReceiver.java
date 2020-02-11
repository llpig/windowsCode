package com.kong.nightrunning;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class NightRunningBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intentService = new Intent(context, RecordStepNumberService.class);
        NightRunDatabase database = MainActivity.databaseHelper;
        SQLiteDatabase db = database.getReadableDatabase();
        int[] stepDate = database.selectRecords(db);
        stepDate[1] += RecordStepNumberService.getCurrentTotalStepNumber();
        String action=intent.getAction();
        switch (action){
            case Intent.ACTION_SHUTDOWN:{
                //关机：更新数据（起始步数：0，总步数）
                context.startService(intentService);
                database.updateRecords(db,0,stepDate[1]);
                context.stopService(intent);
                Log.v("database","关机");
                break;
            }
            case Intent.ACTION_DATE_CHANGED:{
                //日期变化：更新数据（起始步数，总步数）
                //添加新记录
                database.insertRecords(db,0,0);
                context.startService(intentService);
                database.updateRecords(db,stepDate[0],stepDate[1]);
                context.stopService(intent);
                Log.v("database","日期变化");
                break;
            }
        }
    }
}
