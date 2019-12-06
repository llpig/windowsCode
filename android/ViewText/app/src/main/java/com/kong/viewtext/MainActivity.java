package com.kong.viewtext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button m_TextView;
    private Button m_Layout;
    private Button m_Button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        m_TextView=findViewById(R.id.btn_textView);
        m_TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击按钮将活动切换到TextViewActivity活动
                Toast.makeText(MainActivity.this,"已切换到TextViewActivity",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,TextViewActivity.class);
                startActivity(intent);
            }
        });
        m_Layout=findViewById(R.id.btn_Layout);
        m_Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"已切换到LayoutTestActivity",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,LayoutTestActivity.class);
                startActivity(intent);
            }
        });

        m_Button=findViewById(R.id.btn_Button);
        m_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"已切换到ButtonTestActivity",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivity.this,ButtonTestActivity.class);
                startActivity(intent);
            }
        });
    }
}
