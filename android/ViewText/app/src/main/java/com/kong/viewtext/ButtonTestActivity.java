package com.kong.viewtext;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ButtonTestActivity extends AppCompatActivity {
    private Button m_ButtonFour;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_test);
        m_ButtonFour=findViewById(R.id.btn_four);
        m_ButtonFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str=((Button) v).getText().toString();
                str+="被点击";
                Toast.makeText(ButtonTestActivity.this,str,Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void showToast(View view)
    {
        String str=((Button) view).getText().toString();
        str+="被点击";
        Toast.makeText(ButtonTestActivity.this,str,Toast.LENGTH_SHORT).show();
    }
}
