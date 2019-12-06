package com.kong.simplepicbrower;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.net.URI;

public class MainActivity extends AppCompatActivity {
    int count=0;//计数器
    //将图片直接放入Drawable文件夹下，不能含有中文名
    int imageList[]={R.drawable.a1,R.drawable.a2,R.drawable.a3};
    //通过Id寻找View
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        //使用新定义的布局文件，文件名不能包含大写
        setContentView(R.layout.acticity_main_linearlayout);
        imageView=findViewById(R.id.imageBrowser);
        imageView.setImageResource(imageList[count%imageList.length]);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //当点击图片时切换下一张图片
                imageView.setImageResource(imageList[++count%imageList.length]);
            }
        });
    }
}
