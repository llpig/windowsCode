package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //通过id查询定义的控件
        View botton=findViewById(R.id.bt_call);
        //给botton注册一个点击事件，该函数的参数为一个接口
        botton.setOnClickListener(new bottonClickListener());

    }
    private class bottonClickListener implements View.OnClickListener{
        //当按钮被点击时调用的方法
        @Override
        public void onClick(View v) {
            //在按钮点击后，在屏幕上显示文本内容
            Toast.makeText(MainActivity.this,"正在呼叫用户",Toast.LENGTH_SHORT).show();
            //创建一个意图
            Intent intent=new Intent();
            //为意图设置一个动作
            intent.setAction(Intent.ACTION_CALL);
            //设置数据，通过parse方法可以解析一个字符串
            intent.setData(Uri.parse("tel://18092464520"));
            //开始执行这个意图
            startActivity(intent);
        }
    }
}
