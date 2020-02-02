package com.kong.broadcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

//使用广播需要继承BroadcastReceiver类
public class BroadCast_1 extends BroadcastReceiver {

//   广播接收者的执行的执行线程时主线程，一般为UI的执行线程
//   所以在onReceive方法中不应该做耗时操作，负责会导致Activity和BroadCast无响应，从而被回收。
//   一般广播接收者的响应时间为15s，Activity的响应时间为5s
    public BroadCast_1()
    {
        Log.v("myBroadCast","广播接收者被实例化");
    }
    @Override
    //当广播通知到达时，会自动回调该方法
    //context : 表明BroadcastReceiver运行在那个上下文中
    //intent : 携带广播的一些信息
    public void onReceive(Context context, Intent intent) {
        Log.v("Activity PID", String.valueOf(android.os.Process.myPid()));
        Log.v("Activity TID", String.valueOf(Thread.currentThread().getId()));
        Log.v("BroadCast_1","广播已接收");
        Log.v("BroadCast_1",intent.getAction().toString());
        Log.v("BroadCast_1", intent.getExtras().get("title").toString());
    }


}

