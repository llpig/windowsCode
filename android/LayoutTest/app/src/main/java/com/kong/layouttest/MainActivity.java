package com.kong.layouttest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
//    int count=0;
//    //定义颜色数组
//    int colorArray[]={R.color.colorAccent,R.color.colorGreen, R.color.colorBlue,
//            R.color.colorGreen, R.color.colorRed,R.color.colorYellow,R.color.colorPrimaryDark};
//    //定义视图数组
//    int viewArray[]={R.id.view1,R.id.view2,R.id.view3,
//            R.id.view4,R.id.view5,R.id.view6,R.id.view7};
//    TextView textView[]=new TextView[viewArray.length];
//    //消息处理函数
//    Handler handler=new Handler()
//    {
//        //当接收到消息时的动作
//        public void handleMessage(Message message)
//        {
//            if(message.what==0x123)
//            {
//                for(int i=0;i<viewArray.length;++i)
//                {
//                    textView[i].setBackgroundResource(colorArray[(i+count)%colorArray.length]);
//                }
//                ++count;
//            }
//            //调用父类方法
//            super.handleMessage(message);
//        }
//    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //调用父类的方法
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gridlayout);
//        //给每个View赋值
//        for(int i=0;i<viewArray.length;++i)
//        {
//            textView[i]=findViewById(viewArray[i]);
//        }
//        //创建一个定时器，其中的参数为线程
//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                //发送消息
//                handler.sendEmptyMessage(0x123);
//            }
//        },0,300);
    }
}
