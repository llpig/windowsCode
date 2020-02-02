package com.kong.funrun;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private LinearLayout mLinearLayout;
    private Button mButtonUserAvatar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //取消App的标题栏
        getSupportActionBar().hide();
        //旋转中部
        mLinearLayout=findViewById(R.id.Middle);
        mLinearLayout.animate().rotationXBy(360).setDuration(2000).start();
        //头像
        mButtonUserAvatar=findViewById(R.id.ButtonUserAvatar);

        mButtonUserAvatar.setOnClickListener(new ViewOnClickListener());
    }
    private class ViewOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.ButtonUserAvatar:
                    setContentView(R.layout.user_login);
                    Toast.makeText(MainActivity.this,"头像被点击",Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }
}
