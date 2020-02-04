package com.kong.nightrunning;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserLoginActivity extends AppCompatActivity {

    private Button mButtonUserLogin,mButtonQQAvatar;
    private TextView mTextViewRegistered,mTextViewForgetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        this.initActivity();
        this.findViewAndSetOnClickListener();
    }

    private void initActivity() {
        getSupportActionBar().hide();
    }

    private void registeredOnClickListener() {
        startActivity(new Intent(UserLoginActivity.this, UserRegisteredActivity.class));
    }

    private void findViewAndSetOnClickListener() {
        ViewOnClickListener onClickListener=new ViewOnClickListener();
        //登录
        mButtonUserLogin=findViewById(R.id.ButtonUserLogin);
        mButtonUserLogin.setOnClickListener(onClickListener);
        //QQ头像
        mButtonQQAvatar=findViewById(R.id.ButtonQQAvatar);
        mButtonQQAvatar.setOnClickListener(onClickListener);
        //注册账号
        mTextViewRegistered=findViewById(R.id.TextViewRegistered);
        mTextViewRegistered.setOnClickListener(onClickListener);
        //忘记密码
        mTextViewForgetPassword=findViewById(R.id.TextViewForgetPassword);
        mTextViewForgetPassword.setOnClickListener(onClickListener);
    }

    private class ViewOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.ButtonUserLogin:{

                }
                case R.id.ButtonQQAvatar:{

                }
                case R.id.TextViewRegistered:{
                    registeredOnClickListener();
                    break;
                }
                case R.id.TextViewForgetPassword:{

                }
            }
        }

    }

}
