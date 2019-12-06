package com.kong.customview;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    CustomView customView;
    LinearLayout layout;
    Button buttonClearScreen;
    Spinner spinnerPaintSize,spinnerPaintColor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linearlayout_layout);
        layout = findViewById(R.id.mainLayout);
        customView = new CustomView(MainActivity.this);
        layout.addView(customView);
        buttonClearScreen=findViewById(R.id.buttonClearScreen);
        //当点击清空屏幕时，将屏幕上的内容清空
        buttonClearScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customView.clearScreen();
            }
        });
        //寻找下拉框的按钮
        spinnerPaintSize=findViewById(R.id.spinnerPaintSize);
        spinnerPaintColor=findViewById(R.id.spinnerPaintColor);
        spinnerPaintColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==1)
                {
                    customView.selectPaintColor(Color.rgb(255,0,0));
                }
                else if(position==2)
                {
                    customView.selectPaintColor(Color.rgb(0,255,0));
                }
                else
                {
                    customView.selectPaintColor(Color.rgb(0,0,255));
                }
                if(position!=0)
                {
                    String text=parent.getItemAtPosition(position).toString();
                    Toast.makeText(MainActivity.this,"当前画笔颜色:"+text,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerPaintSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==1)
                {
                    customView.selectPaintSize(10);
                }
                else if(position==2)
                {
                    customView.selectPaintSize(20);
                }
                else
                {
                    customView.selectPaintSize(30);
                }
                if(position!=0)
                {
                    String text=parent.getItemAtPosition(position).toString();
                    Toast.makeText(MainActivity.this,"当前画笔尺寸:"+text,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}
