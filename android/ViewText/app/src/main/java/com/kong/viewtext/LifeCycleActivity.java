package com.kong.viewtext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class LifeCycleActivity extends AppCompatActivity {

    private Button m_ChangeActicity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_cycle);
        m_ChangeActicity=findViewById(R.id.changeActicity);
        m_ChangeActicity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(LifeCycleActivity.this,MainActivity.class);
                startActivity(intent);
                Log.v("ActivityLifeCycle","切换Acticty");
            }
        });
        Log.v("ActivityLifeCycle","--onCreate--");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("ActivityLifeCycle","--onDestroy--");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.v("ActivityLifeCycle","--onRestart--");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("ActivityLifeCycle","--onResume--");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v("ActivityLifeCycle","--onStart--");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v("ActivityLifeCycle","--onPause--");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v("ActivityLifeCycle","--onStop--");
    }
}
