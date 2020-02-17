package com.kong.nightrunning;

import android.content.ContentValues;
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
    }

    //构造ContentValues
    private ContentValues getContentValues(int startStepNumber, int totalStepNumber) {
        ContentValues values = new ContentValues();
        values.put(fieldName2, startStepNumber);
        values.put(fieldName3, totalStepNumber);
        return values;
    }

    //插入记录
    public boolean insertRecords(SQLiteDatabase db, int startStepNumber, int totalStepNumber) {
        //如果返回值为-1，则说明插入失败
        ContentValues values = getContentValues(startStepNumber, totalStepNumber);
        return ((db.insert(tableName, null, values) == -1) ? false : true);
    }

    //更新记录
    public boolean updateRecords(SQLiteDatabase db, int startStepNumber, int totalStepNumber) {
        //如果返回值不等于，则说明出现问题
        ContentValues values = getContentValues(startStepNumber, totalStepNumber);
        return ((db.update(tableName, values, fieldName1 + "=(select date('now','localtime'))", null) != 1) ? false : true);
    }

    //查询记录
    public int[] selectRecords(SQLiteDatabase db) {
        Cursor cursor = db.query(tableName, new String[]{fieldName2, fieldName3}, fieldName1 + "=(select date('now','localtime'))", null, null, null, null);
        int startStepNumber = -1, totalStepNumber = -1;
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
