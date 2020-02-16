package com.kong.nightrunning;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private Tool tool;
    private TextView mTextViewTitle;
    private Button mButtonUserAvatar, mButtonSportsShow, mButtonRunning, mButtonSportsCircle;
    public Fragment mLastFragment, mSportsShowFragment, mRunningFragment, mSportsCircleFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.initActivity();
        this.findViewAndSetOnClickListener();
        //注册广播
        registerBroadcastReceiver();
        //启动服务
        startService(new Intent(MainActivity.this, RecordStepNumberService.class));
    }

    //初始化Activity
    private void initActivity() {
        //取消App的标题栏
        getSupportActionBar().hide();
        tool = new Tool();
        mTextViewTitle = findViewById(R.id.TextViewTitle);
        mSportsShowFragment = new SportsShowFragment();
        mRunningFragment = new RunningFragment();
        mSportsCircleFragment = new SportsCircleFragment();
        //上一个点击使用的Fragment
        mLastFragment = mSportsShowFragment;
        //将运动展示界面作为App的首页
        getSupportFragmentManager().beginTransaction().add(R.id.LayoutContent, mSportsShowFragment).commit();
    }

    //Fragment管理(添加新的会导致之前的内容被覆盖)
    private void fragmentLoadingManager(Fragment currentFragment) {
        //如果本次点击内容和上次点击内容相同
        if (currentFragment != mLastFragment) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            //隐藏上次点击展示的内容
            fragmentTransaction.hide(mLastFragment);
            //更新上次的Fragment
            mLastFragment = currentFragment;
            //判断该组件是否被加载过
            if (currentFragment.isAdded()) {
                fragmentTransaction.show(currentFragment);
            } else {
                fragmentTransaction.add(R.id.LayoutContent, currentFragment);
            }
            fragmentTransaction.commit();
        }
    }

    //查询组件并设置点击事件
    private void findViewAndSetOnClickListener() {
        ViewOnClickListener onClickListener = new ViewOnClickListener();
        mButtonUserAvatar = findViewById(R.id.ButtonUserAvatar);
        mButtonUserAvatar.setOnClickListener(onClickListener);

        mButtonSportsShow = findViewById(R.id.ButtonSportsShow);
        mButtonSportsShow.setOnClickListener(onClickListener);

        mButtonRunning = findViewById(R.id.ButtonRunning);
        mButtonRunning.setOnClickListener(onClickListener);

        mButtonSportsCircle = findViewById(R.id.ButtonSportsCircle);
        mButtonSportsCircle.setOnClickListener(onClickListener);

    }

    //用户头像点击事件（点击头像进入“个人中心”）
    private void userAvatarOnClickListener() {
        tool.jumpActivity(MainActivity.this, PersonalCenterActivity.class);
        tool.hintMessage(MainActivity.this, "个人中心");
    }

    //运动展示点击事件
    private void sportsShowOnClickListener() {
        mTextViewTitle.setText(R.string.sports_show);
        mButtonSportsShow.setBackgroundResource(R.drawable.sports_show_red);
        mButtonRunning.setBackgroundResource(R.drawable.running_black);
        mButtonSportsCircle.setBackgroundResource(R.drawable.sports_circle_balck);
        fragmentLoadingManager(mSportsShowFragment);
//        ((SportsShowFragment)mSportsShowFragment).updateDetailedData();
    }

    //跑步点击事件
    private void runningOnClickListener() {
        mTextViewTitle.setText(R.string.running);
        mButtonSportsShow.setBackgroundResource(R.drawable.sports_show_black);
        mButtonRunning.setBackgroundResource(R.drawable.running_red);
        mButtonSportsCircle.setBackgroundResource(R.drawable.sports_circle_balck);
        fragmentLoadingManager(mRunningFragment);
    }

    //运圈点击事件
    private void sportsCircleOnClickListener() {
        mTextViewTitle.setText(R.string.sports_circle);
        mButtonSportsShow.setBackgroundResource(R.drawable.sports_show_black);
        mButtonRunning.setBackgroundResource(R.drawable.running_black);
        mButtonSportsCircle.setBackgroundResource(R.drawable.sports_circle_red);
        fragmentLoadingManager(mSportsCircleFragment);
    }

    //组件点击事件监听器
    private class ViewOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                //用户头像
                case R.id.ButtonUserAvatar: {
                    userAvatarOnClickListener();
                    break;
                }
                //运动展示
                case R.id.ButtonSportsShow: {
                    sportsShowOnClickListener();
                    break;
                }
                //跑步模式
                case R.id.ButtonRunning: {
                    runningOnClickListener();
                    break;
                }
                //运动圈
                case R.id.ButtonSportsCircle: {
                    sportsCircleOnClickListener();
                    break;
                }
            }
        }
    }

    //注册广播（通过广播来更新数据）
    private void registerBroadcastReceiver(){
        IntentFilter filter1=new IntentFilter();
        filter1.addAction(getPackageName()+".UPDATESTEPNUMBER_BROADCAST");
        registerReceiver(new CustomBroadCastReceiver(),filter1);
        IntentFilter filter2=new IntentFilter();
        filter2.addAction(Intent.ACTION_TIME_TICK);
        registerReceiver(new NightRunningBroadcastReceiver(),filter2);
    }

    private class CustomBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().toString().equals(getPackageName()+".UPDATESTEPNUMBER_BROADCAST")){
                int todayStepNumber=intent.getExtras().getInt("currentStepNumber");
                ((SportsShowFragment)mSportsShowFragment).updateTodayStopNumber(todayStepNumber);
            }
            Log.v("message","服务有消息");
        }
    }


}
