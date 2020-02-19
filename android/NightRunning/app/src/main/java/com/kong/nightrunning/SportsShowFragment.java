package com.kong.nightrunning;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

//运动展示Fragment
public class SportsShowFragment extends Fragment {

    private FragmentTransaction fragmentTransaction;
    private DetailedDataFragment mDetailedDataFragment, mNormalModeDetailedDataFragment, mRunningModeDetailedFragment;
    private TextView mTextViewTodayNumber, mTextViewTargetNumber;

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
        initFragment();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateDetailedData();
    }

    private void findView(View view) {
        mTextViewTodayNumber = view.findViewById(R.id.TextViewTodayNumber);
        mTextViewTodayNumber.setText(String.valueOf(NightRunningService.getCurrentTotalStepNumber()));
        mTextViewTargetNumber = view.findViewById(R.id.TextViewTargetNumber);
    }

    private void initFragment() {
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
}
