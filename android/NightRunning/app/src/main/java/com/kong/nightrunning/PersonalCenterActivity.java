package com.kong.nightrunning;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class PersonalCenterActivity extends AppCompatActivity {

    private ImageView mImageViewUserAvatar;
    private Tool tool;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center);
        this.initActivity();
        this.findViewAndSetOnclickListener();
    }

    private void initActivity(){
        getSupportActionBar().hide();
        tool=new Tool();
    }

    private void findViewAndSetOnclickListener(){
        ViewOnClickListener onClickListener=new ViewOnClickListener();
        mImageViewUserAvatar=findViewById(R.id.ImageViewUserAvatar);
        mImageViewUserAvatar.setOnClickListener(onClickListener);
    }

    private class ViewOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.ImageViewUserAvatar:{
                    tool.jumpActivity(PersonalCenterActivity.this,UserLoginActivity.class);
                }
            }

        }
    }
}
