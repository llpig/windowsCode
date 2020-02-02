package com.kong.cameratest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button m_ButtonCallSystemCamera;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.findView();
    }

    private void findView()
    {
        m_ButtonCallSystemCamera=findViewById(R.id.buttonCallSystemCamera);
        m_ButtonCallSystemCamera.setOnClickListener(new MyOnClickListener());
    }

    private class MyOnClickListener implements View.OnClickListener{
        Intent intent=new Intent();
        Context context=MainActivity.this;
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.buttonCallSystemCamera:
                    intent.setClass(context, CallSystemCameraActivity.class);
                    break;
            }
            startActivity(intent);
        }
    }
}
