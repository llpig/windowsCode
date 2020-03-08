package com.kong.nightrunning;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class NightRunningDatabase extends SQLiteOpenHelper {
    //表名,字段
    private String tableName = "StepNumber_table",
            fieldName1 = "Date", fieldName2 = "startStepNumber", fieldName3 = "totalStepNumber";

    public NightRunningDatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //仅在数据创建时调用一次
        db.execSQL(createUserInfoTable());
        db.execSQL(createMotionInfoTable());
        db.execSQL(createMovementLocusTable());
        db.execSQL(createAchievementTable());
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

    //更新记录(true:更新当天记录;false:更新前一天记录）
    public boolean updateRecords(SQLiteDatabase db, int startStepNumber, int totalStepNumber, boolean flag) {
        String whereStr1 = "=(select date('now','localtime'))";
        String whereStr2 = "=(select date('now','localtime','-1 days'))";
        //如果返回值不等于，则说明出现问题
        ContentValues values = getContentValues(startStepNumber, totalStepNumber);
        if (flag) {
            return ((db.update(tableName, values, fieldName1 + whereStr1, null) != 1) ? false : true);
        } else {
            return ((db.update(tableName, values, fieldName1 + whereStr2, null) != 1) ? false : true);
        }
    }

    //查询记录
    public int[] selectRecords(SQLiteDatabase db) {
        int startStepNumber = -1, totalStepNumber = -1;
        String whereStr = "=(select date('now','localtime'))";
        Cursor cursor = db.query(tableName, new String[]{fieldName2, fieldName3}, fieldName1 + whereStr, null, null, null, null);
        //如果当前记录不存在，则创建该记录。
        while (cursor.getCount() == 0) {
            int[] stepNumber = selectRecordsBeforeDay(db);
            int sum = stepNumber[0] + stepNumber[1];
            if (sum >= 0) {
                insertRecords(db, sum, 0);
            } else {
                //第一次启动App或者长时间没有启动App数据库没有创建出对应的数据表
                insertRecords(db, 0, 0);
            }
            //重新查询

            cursor = db.query(tableName, new String[]{fieldName2, fieldName3}, fieldName1 + whereStr, null, null, null, null);
        }
        while (cursor.moveToNext()) {
            startStepNumber = cursor.getInt(cursor.getColumnIndex(fieldName2));
            totalStepNumber = cursor.getInt(cursor.getColumnIndex(fieldName3));
        }
        return new int[]{startStepNumber, totalStepNumber};
    }

    //查询前一天记录
    public int[] selectRecordsBeforeDay(SQLiteDatabase db) {
        int startStepNumber = -1, totalStepNumber = -1;
        String whereStr = "=(select date('now','localtime','-1 days'))";
        Cursor cursor = db.query(tableName, new String[]{fieldName2, fieldName3}, fieldName1 + whereStr, null, null, null, null);
        while (cursor.moveToNext()) {
            startStepNumber = cursor.getInt(cursor.getColumnIndex(fieldName2));
            totalStepNumber = cursor.getInt(cursor.getColumnIndex(fieldName3));
        }
        return new int[]{startStepNumber, totalStepNumber};
    }

    //返回创建用户信息表的SQL语句
    private String createUserInfoTable() {
        String sql = "create Table UserInfoTable (\n" +
                "[UserId]              INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "[UserName]            varchar(64)              Not Null,\n" +
                "[Password]            varchar(32)              Not Null,\n" +
                "[Sex]                 boolean                  Not Null,\n" +
                "[Age]                 int      check(Age>0)    Not Null,\n" +
                "[Height]              double   check(Height>0) Not Null,\n" +
                "[Weight]              double   check(Weight>0) Not Null,\n" +
                "[TargetStepNumber]    int      check(TargetStepNumber>0) default 0,\n" +
                "[TargetMileage]       int      check(TargetMileage)      default 0,\n" +
                "[Avatar]              varchar(255),\n" +
                "[Signature]           varchar(255),\n" +
                "[Email]               varchar(320)\n" +
                ");";
        return sql;
    }

    //返回创建运动信息表的SQL语句
    private String createMotionInfoTable() {
        String sql = "Create Table MotionInfoTable (\n" +
                "[UserName]            varchar(64),\n" +
                "[Date]                date Not Null DEFAULT (date('now','localtime')) PRIMARY KEY,\n" +
                "[StepNumber]          int Not Null default 0,\n" +
                "[Mileage]             double default 0,\n" +
                "[RunningStartTime]    time, \n" +
                "[RunningFinishTime]   time,\n" +
                "[EquipmentInfo]       boolean default 0,\n" +
                "FOREIGN KEY(UserName) REFERENCES UserInfoTable(UserName)\n" +
                ");";
        return sql;
    }

    //返回运动轨迹表的SQL语句
    private String createMovementLocusTable() {
        String sql = "Create Table MovementLocusTable (\n" +
                "[UserName]            varchar(64),\n" +
                "[MovementLocus]       varchar(255),\n" +
                "[CreateTime]          datetime     Not Null,\n" +
                "Primary Key(UserName,MovementLocus),\n" +
                "Foreign Key(UserName) References UserInfoTable(UserName) \n" +
                ");";
        return sql;
    }

    //返回创建个人成就表SQL语句
    private String createAchievementTable() {
        String sql = "Create Table AchievementTable (\n" +
                "[UserName]           varchar(64) ,\n" +
                "[Achievement]        varchar(255),\n" +
                "[completeTime]       datetime     Not Null,\n" +
                "Primary Key(UserName,Achievement),\n" +
                "Foreign Key(UserName) References UserInfoTable(UserName)\n" +
                ");";
        return sql;
    }

    //更新数据库版本是调用
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
