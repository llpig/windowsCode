package com.kong.nightrunning;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

//工具类
public class Tool {
    Intent intent=new Intent();
    Toast toast;
    public static enum MessageTypeEnum{
        ADDSTEPNUMBER(1),FOREGROUNDSERVICE(2);
        private int index;
        MessageTypeEnum(int index) {
            this.index=index;
        }
        public int getIndex(){
            return index;
        }
    }
    //跳转活动
    public void jumpActivity(Context currentContext,Class<?> jumpedClass){
        intent.setClass(currentContext,jumpedClass);
        currentContext.startActivity(intent);
    }
    //提示消息
    public void hintMessage(Context currentContext,String message){
        toast=Toast.makeText(currentContext,"",Toast.LENGTH_SHORT);
        toast.setText(message);
        toast.show();
    }
}
