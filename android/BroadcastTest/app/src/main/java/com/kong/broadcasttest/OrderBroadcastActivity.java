package com.kong.broadcasttest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OrderBroadcastActivity extends AppCompatActivity {

    private Button m_SendBroadcast;
    private MyOnClickListener onClickListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_broadcast);
        onClickListener=new MyOnClickListener();
        m_SendBroadcast=findViewById(R.id.buttonSendBroadcast);
        m_SendBroadcast.setOnClickListener(onClickListener);
    }

    private class MyOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent=new Intent();
            intent.setAction("com.kong.broadcasttest.ORDER_BROADCAST");
            //发送顺序广播，默认的顺序为优先级从高到低
            //优先级相同的情况下为广播接收者注册时的顺序
            Bundle bundle=new Bundle();
            bundle.putString("title","黄河远上白云间");
            intent.putExtras(bundle);
            sendOrderedBroadcast(intent,null);

        }
    }
}
