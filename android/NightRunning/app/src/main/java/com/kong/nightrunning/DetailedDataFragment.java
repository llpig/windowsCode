package com.kong.nightrunning;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DetailedDataFragment extends Fragment {
    private TextView mDetailedDataTitle, mDetailedDataStopNumber, mDetailedDataCalories,
            mDetailedDataMileage, mDetailedDataTime, mDetailedDataSpeed;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detailed_data, container, false);
        findView(view);
        return view;
    }

    private void findView(View view) {
        mDetailedDataTitle = view.findViewById(R.id.TextViewDetailedDataTitle);
        mDetailedDataStopNumber = view.findViewById(R.id.TextViewDetailedDataStopNumber);
        mDetailedDataCalories = view.findViewById(R.id.TextViewDetailedDataCalories);
        mDetailedDataMileage = view.findViewById(R.id.TextViewDetailedDataMileage);
        mDetailedDataTime = view.findViewById(R.id.TextViewDetailedDataTime);
        mDetailedDataSpeed = view.findViewById(R.id.TextViewDetailedDataSpeed);
    }

    public void setTextViewData(String title, String stopNumber, String calories, String mileage, String time, String speed){
        mDetailedDataTitle.setText(title);
        mDetailedDataStopNumber.setText(stopNumber);
        mDetailedDataCalories.setText(calories);
        mDetailedDataMileage.setText(mileage);
        mDetailedDataTime.setText(time);
        mDetailedDataSpeed.setText(speed);
    }
}
