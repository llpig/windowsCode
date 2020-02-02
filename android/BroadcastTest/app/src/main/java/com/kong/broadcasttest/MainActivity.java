package com.kong.broadcasttest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private BroadCast_1 broadCast_1;
    private BroadCast_2 broadCast_2;
    private BroadCast_3 broadCast_3;
    private Button m_CustomBroadCast;
    private Button m_OrderBroadCast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        setOnClickListener();
        registerBroadcastReceiver();
    }

    private void findView()
    {
        m_CustomBroadCast=findViewById(R.id.buttonCustomBroadCast);
        m_OrderBroadCast=findViewById(R.id.buttonOrderBroadCast);
    }

    private void setOnClickListener()
    {
        MyOnClickListener onClickListener=new MyOnClickListener();
        m_CustomBroadCast.setOnClickListener(onClickListener);
        m_OrderBroadCast.setOnClickListener(onClickListener);
    }

    //注册广播接收者
    private void registerBroadcastReceiver()
    {
        IntentFilter filter=new IntentFilter();
        filter.addAction("com.kong.broadcasttest.ORDER_BROADCAST");
        filter.addAction("com.kong.broadcasttest.ORDER_CUSTOM_BROADCAST");
        broadCast_1=new BroadCast_1();
        broadCast_2=new BroadCast_2();
        broadCast_3=new BroadCast_3();
        registerReceiver(broadCast_1,filter);
        registerReceiver(broadCast_2,filter);
        registerReceiver(broadCast_3,filter);
    }

    private class MyOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent=new Intent();
            Context context=MainActivity.this;
            switch (v.getId())
            {
                case R.id.buttonCustomBroadCast:
                    intent.setClass(context, CustomBroadcastActivity.class);
                    break;
                case R.id.buttonOrderBroadCast:
                    intent.setClass(context,OrderBroadcastActivity.class);
                    break;
            }
            //必须开始执行活动
            startActivity(intent);
        }
    }


}
