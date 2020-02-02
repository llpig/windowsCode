package com.kong.viewtext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditTextActivity extends AppCompatActivity {
    private EditText m_UserName;
    private EditText m_PassWorld;
    private Button m_ButtonLogin;
    private Button m_ButtonCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);
        m_UserName=findViewById(R.id.editUSerName);
        //添加文本变化监听器
        m_UserName.addTextChangedListener(new EditTextChangedListener());
        m_PassWorld=findViewById(R.id.editPassWorld);
        m_ButtonLogin=findViewById(R.id.buttonLogin);
        m_ButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName,passWorld;
                userName=m_UserName.getText().toString().trim();
                passWorld=m_PassWorld.getText().toString().trim();
                if(userName.isEmpty()||passWorld.isEmpty())
                {
                    Toast.makeText(EditTextActivity.this,"用户名或密码为空，请检查。",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //如果登录成功就启动TextView活动
                    Intent intent=new Intent(EditTextActivity.this,TextViewActivity.class);
                    startActivity(intent);
                }
            }
        });

        m_ButtonCancel=findViewById(R.id.buttonCancel);
        m_ButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //返回上一级目录
                Intent intent=new Intent(EditTextActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
    public class EditTextChangedListener implements TextWatcher
    {
        //文本内容变化之前
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }
        //文本内容变化中
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //count表示本次输入的字符数
            if(s.length()>0&&count!=0&&s.charAt(s.length()-1)=='@')
            {
                Toast.makeText(EditTextActivity.this,"邮箱地址:",Toast.LENGTH_SHORT).show();
                //当输入的内容为@时，自动在末尾加上qq.com
                m_UserName.setText(s+"qq.com");
            }
        }
        //文本内容变化之后
        @Override
        public void afterTextChanged(Editable s) {

        }
    }

}
