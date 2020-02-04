package com.kong.nightrunning;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

//工具类
public class Tool {
    Intent intent=new Intent();
    Toast toast;
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
