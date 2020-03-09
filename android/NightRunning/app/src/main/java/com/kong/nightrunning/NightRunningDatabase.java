package com.kong.nightrunning;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class NightRunningDatabase extends SQLiteOpenHelper {
    //表名,字段
    private NightRunningDB nightRunningDB;

    public NightRunningDatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        nightRunningDB = new NightRunningDB();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //仅在数据创建时调用一次
        db.execSQL(createUserInfoTable());
        db.execSQL(createMotionInfoTable());
        db.execSQL(createMovementLocusTable());
        db.execSQL(createAchievementTable());

        //测试数据
        if (insertRecordsToUserInfoTable(db, "测试1", "1111", 0, 32, 175, 55, 3000, 3, "") == false) {
            Log.v("message", "记录插入失败");
        }
    }

    //返回创建用户信息表的SQL语句
    private String createUserInfoTable() {
        String sql = "create Table UserInfoTable (" +
                "[UserName] varchar(64) PRIMARY KEY  Not Null," +
                "[Password] varchar(32) Not Null," +
                "[Sex] boolean Not Null," +
                "[Age] int check(Age>0) Not Null," +
                "[Height] double check(Height>0) Not Null," +
                "[Weight] double check(Weight>0) Not Null," +
                "[TargetStepNumber] int check(TargetStepNumber>0) default 0," +
                "[TargetMileage] int check(TargetMileage>0) default 0," +
                "[Avatar] varchar(255)" +
                ");";
        return sql;
    }


    //插入记录（用户信息表）
    public boolean insertRecordsToUserInfoTable(SQLiteDatabase db, String userName, String password, int sex, int age,
                                                double height, double weight, int targetStepNumber, int targetMileage, String avatar) {
        ContentValues values = new ContentValues();
        values.put(nightRunningDB.userInfoTable.userName, userName);
        values.put(nightRunningDB.userInfoTable.password, password);
        values.put(nightRunningDB.userInfoTable.sex, sex);
        values.put(nightRunningDB.userInfoTable.age, age);
        values.put(nightRunningDB.userInfoTable.height, height);
        values.put(nightRunningDB.userInfoTable.weight, weight);
        values.put(nightRunningDB.userInfoTable.targetStepNumber, targetStepNumber);
        values.put(nightRunningDB.userInfoTable.targetMileage, targetMileage);
        values.put(nightRunningDB.userInfoTable.avatar, avatar);
        //如果结果返回-1，则说明插入失败
        return (db.insert(nightRunningDB.userInfoTable.tableName, null, values) == -1 ? false : true);
    }

    //返回创建运动信息表的SQL语句
    private String createMotionInfoTable() {
        String sql = "Create Table MotionInfoTable (" +
                "[UserName]            varchar(64)," +
                "[Date]                date Not Null DEFAULT (date('now','localtime')) PRIMARY KEY," +
                "[StepNumber]          int Not Null default 0," +
                "[Mileage]             double default 0," +
                "[RunningStartTime]    time Not Null DEFAULT (time('now','localtime'))," +
                "[RunningFinishTime]   time Not Null DEFAULT (time('now','localtime'))," +
                "[EquipmentInfo]       boolean default 0," +
                "FOREIGN KEY(UserName) REFERENCES UserInfoTable(UserName)" +
                ");";
        return sql;
    }

    //插入记录（运动信息表）
    public boolean insertRecordsToMotionInfoTable(SQLiteDatabase db, String userName) {
        ContentValues values = new ContentValues();
        //添加一条只有用户名的新记录，其他值采用默认值。
        values.put(nightRunningDB.motionInfoTable.userName, userName);
        return (db.insert(nightRunningDB.motionInfoTable.tableName, null, values) == -1 ? false : true);
    }

    //返回运动轨迹表的SQL语句
    private String createMovementLocusTable() {
        String sql = "Create Table MovementLocusTable (" +
                "[UserName] varchar(64)," +
                "[MovementLocus] varchar(255)," +
                "[CreateTime] datetime Not Null DEFAULT(datetime('now','localtime'))," +
                "Primary Key (UserName,MovementLocus)," +
                "Foreign Key(UserName) References UserInfoTable(UserName)" +
                ");";
        return sql;
    }

    //插入记录（运动轨迹表）
    public boolean insertRecordsToMovementLocusTable(SQLiteDatabase db, String userName, String movementLocus) {
        ContentValues values = new ContentValues();
        values.put(nightRunningDB.movementLocusTable.userName, userName);
        values.put(nightRunningDB.movementLocusTable.movementLocus, movementLocus);
        return (db.insert(nightRunningDB.movementLocusTable.tableName, null, values) == -1 ? false : true);
    }

    //返回创建个人成就表SQL语句
    private String createAchievementTable() {
        String sql = "Create Table AchievementTable (" +
                "[UserName]     varchar(64) ," +
                "[Achievement]  varchar(255)," +
                "[completeTime] datetime Not Null DEFAULT(datetime('now','localtime'))," +
                "Primary Key(UserName,Achievement)," +
                "Foreign Key(UserName) References UserInfoTable(UserName)" +
                ");";
        return sql;
    }

    //插入记录（个人成就表）
    public boolean insertRecordsToAchievementTable(SQLiteDatabase db, String userName, String achievement) {
        ContentValues values = new ContentValues();
        values.put(nightRunningDB.achievementTable.userName, userName);
        values.put(nightRunningDB.achievementTable.achievement, achievement);
        return (db.insert(nightRunningDB.achievementTable.tableName, null, values) == -1 ? false : true);
    }

//    //更新记录(true:更新当天记录;false:更新前一天记录）
//    public boolean updateRecords(SQLiteDatabase db, int startStepNumber, int totalStepNumber, boolean flag) {
//        String whereStr1 = "=(select date('now','localtime'))";
//        String whereStr2 = "=(select date('now','localtime','-1 days'))";
//        //如果返回值不等于，则说明出现问题
//        ContentValues values = getContentValues(startStepNumber, totalStepNumber);
//        if (flag) {
//            return ((db.update(tableName, values, fieldName1 + whereStr1, null) != 1) ? false : true);
//        } else {
//            return ((db.update(tableName, values, fieldName1 + whereStr2, null) != 1) ? false : true);
//        }
//    }
//
//    //查询记录
//    public int[] selectRecords(SQLiteDatabase db) {
//        int startStepNumber = -1, totalStepNumber = -1;
//        String whereStr = "=(select date('now','localtime'))";
//        Cursor cursor = db.query(tableName, new String[]{fieldName2, fieldName3}, fieldName1 + whereStr, null, null, null, null);
//        //如果当前记录不存在，则创建该记录。
//        while (cursor.getCount() == 0) {
//            int[] stepNumber = selectRecordsBeforeDay(db);
//            int sum = stepNumber[0] + stepNumber[1];
//            if (sum >= 0) {
//                insertRecords(db, sum, 0);
//            } else {
//                //第一次启动App或者长时间没有启动App数据库没有创建出对应的数据表
//                insertRecords(db, 0, 0);
//            }
//            //重新查询
//
//            cursor = db.query(tableName, new String[]{fieldName2, fieldName3}, fieldName1 + whereStr, null, null, null, null);
//        }
//        while (cursor.moveToNext()) {
//            startStepNumber = cursor.getInt(cursor.getColumnIndex(fieldName2));
//            totalStepNumber = cursor.getInt(cursor.getColumnIndex(fieldName3));
//        }
//        return new int[]{startStepNumber, totalStepNumber};
//    }
//
//    //查询前一天记录
//    public int[] selectRecordsBeforeDay(SQLiteDatabase db) {
//        int startStepNumber = -1, totalStepNumber = -1;
//        String whereStr = "=(select date('now','localtime','-1 days'))";
//        Cursor cursor = db.query(tableName, new String[]{fieldName2, fieldName3}, fieldName1 + whereStr, null, null, null, null);
//        while (cursor.moveToNext()) {
//            startStepNumber = cursor.getInt(cursor.getColumnIndex(fieldName2));
//            totalStepNumber = cursor.getInt(cursor.getColumnIndex(fieldName3));
//        }
//        return new int[]{startStepNumber, totalStepNumber};
//    }

    //保存数据相关信息
    private class NightRunningDB {
        UserInfoTable userInfoTable = new UserInfoTable();
        MotionInfoTable motionInfoTable = new MotionInfoTable();
        MovementLocusTable movementLocusTable = new MovementLocusTable();
        AchievementTable achievementTable = new AchievementTable();

        private class UserInfoTable {
            String tableName = "UserInfoTable", userName = "UserName", password = "Password", sex = "Sex", height = "Height",
                    weight = "Weight", age = "Age", targetStepNumber = "TargetStepNumber", targetMileage = "TargetMileage", avatar = "Avatar";
        }

        private class MotionInfoTable {
            String tableName="MotionInfoTable", userName="UserName", stepNumber="StepNumber", mileage="Mileage", equipmentInfo="EquipmentInfo";
        }

        private class MovementLocusTable {
            String tableName="MovementLocusTable", userName="UserName", movementLocus="MovementLocus";
        }

        private class AchievementTable {
            String tableName="AchievementTable", userName="UserName", achievement="Achievement";
        }
    }

    //更新数据库版本是调用
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
