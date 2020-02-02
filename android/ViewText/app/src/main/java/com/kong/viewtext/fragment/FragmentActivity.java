package com.kong.viewtext.fragment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kong.viewtext.R;

public class FragmentActivity extends AppCompatActivity {

    AFragment aFragment;
//    BFragment bFragment;
    TextView m_TextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        aFragment=AFragment.newInstance("这是Activity中传递的参数");
//        bFragment=BFragment.newInstance("这是Activity传递给BFragment的参数");
        m_TextView=findViewById(R.id.containerTextView);
        //在Activity中加载Fragment
        getSupportFragmentManager().beginTransaction().add(R.id.container,aFragment).commitAllowingStateLoss();
    }

    public void sendMessage(String message)
    {
        m_TextView.setText(message);
    }
}
