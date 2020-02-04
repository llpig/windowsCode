package com.kong.nightrunning;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

//运动展示Fragment
public class SportsShowFragment extends Fragment {
    @Nullable
    @Override
    //每次添加该Fragment就会调用该方法
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_sports_show,container,false);
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.LayoutDetailedData,new DetailedDataFragment()).commit();
        return view;
    }
}
