package com.kong.nightrunning;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button mButtonUserAvatar;
    private Button mButtonSportsShow, mButtonRunning, mButtonSportsCircle;
    private LinearLayout mLayoutMainActivity;
    private TextView mTextViewTitle;
    private Fragment mLastFragment, mSportsShowFragment, mRunningFragment, mSportsCircleFragment;
    private Tool tool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.initActivity();
        this.findViewAndSetOnClickListener();
    }

    //初始化Activity
    private void initActivity() {
        //取消App的标题栏
        getSupportActionBar().hide();
        mLayoutMainActivity = findViewById(R.id.LayoutMainActivity);
        mTextViewTitle = findViewById(R.id.TextViewTitle);
        tool=new Tool();
        mSportsShowFragment = new SportsShowFragment();
        mRunningFragment = new RunningFragment();
        mSportsCircleFragment = new SportsCircleFragment();
        //上一个点击使用的Fragment
        mLastFragment = mSportsShowFragment;
        //将运动展示界面作为App的首页
        getSupportFragmentManager().beginTransaction().add(R.id.LayoutContent,mSportsShowFragment).commit();
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
        tool.jumpActivity(MainActivity.this,PersonalCenterActivity.class);
        tool.hintMessage(MainActivity.this,"个人中心");
    }

    //Fragment管理
    //添加新的会导致之前的内容被覆盖
    private void fragmentManager(Fragment currentFragment) {
        //如果本次点击内容和上次点击内容相同
        if (currentFragment != mLastFragment) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            //隐藏上次点击展示的内容
            fragmentTransaction.hide(mLastFragment);
            //更新上次的Fragment
            mLastFragment=currentFragment;
            //判断该组件是否被加载过
            if (currentFragment.isAdded()) {
                fragmentTransaction.show(currentFragment);
            } else {
                fragmentTransaction.add(R.id.LayoutContent,currentFragment);
            }
            fragmentTransaction.commit();
        }
    }

    //运动展示点击事件
    private void sportsShowOnClickListener() {
        mTextViewTitle.setText(R.string.sports_show);
        mButtonSportsShow.setBackgroundResource(R.drawable.sports_show_red);
        mButtonRunning.setBackgroundResource(R.drawable.running_black);
        mButtonSportsCircle.setBackgroundResource(R.drawable.sports_circle_balck);
        fragmentManager(mSportsShowFragment);

    }

    //跑步点击事件
    private void runningOnClickListener() {
        mTextViewTitle.setText(R.string.running);
        mButtonSportsShow.setBackgroundResource(R.drawable.sports_show_black);
        mButtonRunning.setBackgroundResource(R.drawable.running_red);
        mButtonSportsCircle.setBackgroundResource(R.drawable.sports_circle_balck);
        fragmentManager(mRunningFragment);
    }

    //运圈点击事件
    private void sportsCircleOnClickListener() {
        mTextViewTitle.setText(R.string.sports_circle);
        mButtonSportsShow.setBackgroundResource(R.drawable.sports_show_black);
        mButtonRunning.setBackgroundResource(R.drawable.running_black);
        mButtonSportsCircle.setBackgroundResource(R.drawable.sports_circle_red);
        fragmentManager(mSportsCircleFragment);
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

}
