package com.kong.viewtext;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ToggleButtonTestActivity extends AppCompatActivity {
    private ImageView m_ImageLight;
    private ToggleButton m_ToggleButton;
    private LinearLayout m_Linear;
    private Switch m_Switch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_togglebutton_test);
        m_ImageLight = findViewById(R.id.imageLight);
        m_ToggleButton = findViewById(R.id.toggleButton);
        m_Linear = findViewById(R.id.linear);
        m_Switch = findViewById(R.id.switchDark);

        //m_Switch.setOnCheckedChangeListener(new ToggleButtonOnCheckChange());

        m_ToggleButton.setOnCheckedChangeListener(new ToggleButtonOnCheckChange());
    }

    private class ToggleButtonOnCheckChange implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                m_ImageLight.setImageResource(R.drawable.off_light);
                if (m_Switch.isChecked()) {
                    //当点击黑夜模式时，关灯后背景为黄色
                    m_Linear.setBackgroundColor(Color.rgb(255, 255, 0));
                }
                else
                {
                    //当点击黑夜模式时，关灯后的背景为黑色
                    m_Linear.setBackgroundColor(Color.rgb(0, 0, 0));
                }
            } else {
                m_ImageLight.setImageResource(R.drawable.on_light);
                m_Linear.setBackgroundColor(Color.rgb(255, 255, 255));
            }
        }
    }
}
