package com.kong.broadcasttest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class CustomBroadcastActivity extends AppCompatActivity {

    private BroadCast_1 broadCast;
    private Button sendButton,sendButtonPermission;
    private myOnClickListener onClickListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_custom_broadcast);
        broadCast=new BroadCast_1();
        onClickListener=new myOnClickListener();
        sendButton=findViewById(R.id.SendButton);
        sendButton.setOnClickListener(onClickListener);
        sendButtonPermission=findViewById(R.id.SendButtonPermission);
        sendButtonPermission.setOnClickListener(onClickListener);
        //动态注册广播接收者
        //动态注册的广播接收者，需要启动app后才可以接收到
        IntentFilter intentFilter=new IntentFilter();
        //添加事件：普通自定义广播
        intentFilter.addAction("com.kong.broadcasttest.CUSTOM_BROADCAST");
        //添加事件： 带权限的自定义广播
        intentFilter.addAction("com.kong.broadcasttest.CUSTOM_BROADCAST_PERMISSION");
        registerReceiver(broadCast,intentFilter);

        Log.v("Activity PID", String.valueOf(android.os.Process.myPid()));
        Log.v("Activity TID", String.valueOf(Thread.currentThread().getId()));
    }

    class myOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent  intent=new Intent();
            if(v.getId()==R.id.SendButton)
            {
                intent.setAction("com.kong.broadcasttest.CUSTOM_BROADCAST");
                sendBroadcast(intent);
                Log.v("CUSTOM_BROADCAST","自定义消息已经发送");
            }
            else if(v.getId()==R.id.SendButtonPermission)
            {
                intent.setAction("com.kong.broadcasttest.CUSTOM_BROADCAST_PERMISSION");
                //需要由对应的APP才能接收该广播
                //必须在清单文件中，声明和使用该权限
                sendBroadcast(intent,"com.kong.broadcasttest.CUSTOM_PERMISSION");
                Log.v("CUSTOM_BROADCAST","带权限的自定义消息已经发送");
            }
        }
    }
    //当activity被销毁时，会自动调用该方法
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销广播接收器
        unregisterReceiver(broadCast);
        Log.v("BroadCast","Activity被销毁，广播接收者已注销");
    }
}
