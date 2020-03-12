package com.kong.nightrunning;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.Timer;
import java.util.TimerTask;

//运动展示Fragment
public class SportsShowFragment extends Fragment {

    private SensorHandler handler;
    private FragmentTransaction fragmentTransaction;
    private TextView mTextViewTodayNumber, mTextViewTargetNumber;
    private DetailedDataFragment mDetailedDataFragment, mNormalModeDetailedDataFragment, mRunningModeDetailedFragment;

    SportsShowFragment() {
        mDetailedDataFragment = new DetailedDataFragment();
        mNormalModeDetailedDataFragment = new DetailedDataFragment();
        mRunningModeDetailedFragment = new DetailedDataFragment();
    }

    @Nullable
    @Override
    //每次添加该Fragment就会调用该方法
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sports_show, container, false);
        findView(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        handler = new SensorHandler();
        int delayTime = 0, periodTime = 3000;
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.arg1 = NightRunningSensorEventListener.getTodayAddStepNumber();
                handler.sendMessage(message);
            }
        };
        Timer timer = new Timer();
        //每隔30s，检测一次数据，如果数据有更新则发送一次广播，通知UI线程更新UI
        timer.schedule(timerTask, delayTime, periodTime);
    }

    private void findView(View view) {
        mTextViewTodayNumber = view.findViewById(R.id.TextViewTodayNumber);
        mTextViewTargetNumber = view.findViewById(R.id.TextViewTargetNumber);

        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.LayoutDetailedData, mDetailedDataFragment);
        fragmentTransaction.add(R.id.LayoutNormalModeDetailedData, mNormalModeDetailedDataFragment);
        fragmentTransaction.add(R.id.LayoutRunningModeDetailedData, mRunningModeDetailedFragment);
        fragmentTransaction.commit();
    }


    //更新今日步数
    public void updateTodayStopNumber(int stopNumber) {
        mTextViewTodayNumber.setText(String.valueOf(stopNumber));
        updateDetailedData();
    }

    //更新目标步数
    public void updateTargetStopNumber(int targetNumber) {
        mTextViewTargetNumber.setText("目标步数:" + String.valueOf(targetNumber));
    }

    //更新详细数据
    private void updateDetailedData() {
        mNormalModeDetailedDataFragment.setTextViewData("普通模式", "0", "0", "0", "0", "0");
        mRunningModeDetailedFragment.setTextViewData("跑步模式", "0", "0", "0", "0", "0");
    }

    private class SensorHandler extends Handler {
        public void handleMessage(Message message) {
            updateTodayStopNumber(message.arg1);
        }
    }

}
