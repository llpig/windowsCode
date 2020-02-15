package com.kong.nightrunning;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class NightRunDatabase extends SQLiteOpenHelper {
    //表名,字段
    private String tableName = "StepNumber_table",
            fieldName1 = "Date", fieldName2 = "startStepNumber", fieldName3 = "totalStepNumber";

    public NightRunDatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //仅在数据创建时调用一次
        db.execSQL(createStepNumberTable());
        //用户第一次启动App
        insertRecords(db, 0, 0);
    }

    //插入记录
    public void insertRecords(SQLiteDatabase db, int startStepNumber, int totalStepNumber) {
        String sql = "insert into " + tableName +
                "(" + fieldName2 + "," + fieldName3 + ") values" +
                "(" + String.valueOf(startStepNumber) + "," + String.valueOf(totalStepNumber) + ");";
        Log.v("database", sql);
        db.execSQL(sql);
    }

    //更新记录
    public void updateRecords(SQLiteDatabase db, int startStepNumber, int totalStepNumber) {
        String sql = "update " + tableName + " set " +
                fieldName2 + "=" + String.valueOf(startStepNumber) + "," +
                fieldName3 + "=" + String.valueOf(totalStepNumber) +
                " where Date=(select date('now','localtime'));";
        Log.v("database", sql);
        db.execSQL(sql);
    }

    //查询记录
    public int[] selectRecords(SQLiteDatabase db) {
        Cursor cursor = db.query(tableName, new String[]{fieldName2, fieldName3}, fieldName1 + "=(select date('now','localtime'))", null, null, null, null);
        int startStepNumber = 0, totalStepNumber = 0;
        while (cursor.moveToNext()) {
            startStepNumber = cursor.getInt(cursor.getColumnIndex(fieldName2));
            totalStepNumber = cursor.getInt(cursor.getColumnIndex(fieldName3));

        }
        return new int[]{startStepNumber, totalStepNumber};
    }

    //创建今日“今日步数表”(日期：主键，跑步步数，普通步数)
    private String createStepNumberTable() {
        String sql = "CREATE TABLE StepNumber_table (" +
                "[date] date NOT NULL DEFAULT (date('now','localtime')) PRIMARY KEY," +
                "[startStepNumber] int," +
                "[totalStepNumber] int);";
        return sql;
    }

    //更新数据库版本是调用
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
