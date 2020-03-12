package com.kong.nightrunning;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

//工具类
public class Tool {
    Intent intent = new Intent();
    Toast toast;
    private static String packName = "com.kong.nightrunning";

    //消息类型
    public static enum MessageType {
        CURRENTSTEPNUMBERKEY(1), FOREGROUNDSERVICE(2);
        private int index;

        MessageType(int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }
    }

    //自定义广播
    public static enum CustomBroadcast {
        UPDATASTEPNUMBER(packName + ".updatastepnumber"),
        NULLSERSOR(packName+".nullsersor");
        private String index;

        CustomBroadcast(String index) {
            this.index = index;
        }

        public String getIndex() {
            return index;
        }
    }

    //跳转活动
    public void jumpActivity(Context currentContext, Class<?> jumpedClass) {
        intent.setClass(currentContext, jumpedClass);
        currentContext.startActivity(intent);
    }

    //提示消息
    public void hintMessage(Context currentContext, String message) {
        toast = Toast.makeText(currentContext, "", Toast.LENGTH_SHORT);
        toast.setText(message);
        toast.show();
    }

    //保存数据相关信息
    public class NightRunningDB {
        UserInfoTable userInfoTable = new UserInfoTable();
        MotionInfoTable motionInfoTable = new MotionInfoTable();
        MovementLocusTable movementLocusTable = new MovementLocusTable();
        AchievementTable achievementTable = new AchievementTable();

        public class UserInfoTable {
            String tableName = "UserInfoTable", userName = "UserName", password = "Password", sex = "Sex", height = "Height",
                    weight = "Weight", age = "Age", targetStepNumber = "TargetStepNumber", targetMileage = "TargetMileage", avatar = "Avatar";
        }

        public class MotionInfoTable {
            String tableName = "MotionInfoTable", userName = "UserName", date = "Date", runningStartTime = "RunningStartTime", runningFinishTime = "RunningFinishTime", stepNumber = "StepNumber", mileage = "Mileage", equipmentInfo = "EquipmentInfo";
        }

        public class MovementLocusTable {
            String tableName = "MovementLocusTable", userName = "UserName", movementLocus = "MovementLocus";
        }

        public class AchievementTable {
            String tableName = "AchievementTable", userName = "UserName", achievement = "Achievement";
        }
    }
}
