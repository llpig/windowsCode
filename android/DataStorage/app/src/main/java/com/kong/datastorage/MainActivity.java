package com.kong.datastorage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//通过主活动继承点击事件
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button m_SharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findButton();
    }
    //查询按钮
    void findButton(){
        m_SharedPreferences=findViewById(R.id.ButtonSharedPreferences);
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent();
        Context context=MainActivity.this;
        switch(v.getId()){
            case R.id.ButtonSharedPreferences:
                intent.setClass(context,SharedPreferencesActivity.class);
                break;
        }
        startActivity(intent);
    }
}
