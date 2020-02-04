package com.kong.nightrunning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserRegisteredActivity extends AppCompatActivity {

    private Button mButtonReturnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registered);
        this.initActivity();
        this.findViewAndSetOnClickListener();
    }

    public void initActivity() {
        getSupportActionBar().hide();
    }

    private void findViewAndSetOnClickListener(){
        ViewOnClickListener onClickListener=new ViewOnClickListener();
        mButtonReturnLogin=findViewById(R.id.ButtonReturnLogin);
        mButtonReturnLogin.setOnClickListener(onClickListener);
    }

    private void returnLoginOnClickListener(){
        startActivity(new Intent(UserRegisteredActivity.this,UserLoginActivity.class));
    }

    private class ViewOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.ButtonReturnLogin:
                {
                    returnLoginOnClickListener();
                    break;
                }
            }

        }
    }
}
