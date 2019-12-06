package com.kong.viewtext;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextPaint;
import android.widget.TextView;

public class TextViewActivity extends AppCompatActivity {
    private TextView m_TextViewJAVA;
    private TextView m_TextViewC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.textview_layout);
        m_TextViewJAVA=findViewById(R.id.textViewJAVA);
        //获取画笔
        TextPaint textPaint=m_TextViewJAVA.getPaint();
        //给文本设置下划线
        textPaint.setUnderlineText(true);

        m_TextViewC=findViewById(R.id.textViewC);
        m_TextViewC.getPaint().setFlags(TextPaint.STRIKE_THRU_TEXT_FLAG);
    }
}
