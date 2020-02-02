package com.kong.viewtext;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class ImageViewTestActivity extends AppCompatActivity {
    private ImageView m_ImageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageviewtest);
        m_ImageView=findViewById(R.id.imageView1);
        Glide.with(ImageViewTestActivity.this).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1575818897818&di=fc574e516ced1272fdc559edba1e8741&imgtype=0&src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2F7d4d8b176060892ae869693396600b7a4309660aa4ad-cEOasT_fw658").into(m_ImageView);
    }
}
