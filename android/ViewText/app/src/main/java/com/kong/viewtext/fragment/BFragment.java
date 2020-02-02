package com.kong.viewtext.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kong.viewtext.R;

public class BFragment extends Fragment {
    private TextView textView;

    public static BFragment newInstance(String string)
    {
        BFragment bFragment=new BFragment();
        Bundle bundle=new Bundle();
        bundle.putString("title",string);
        bFragment.setArguments(bundle);
        return bFragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_b,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView=view.findViewById(R.id.textViewB);
        Bundle bundle=getArguments();
        if(bundle!=null)
        {
            textView.setText(bundle.getString("title").toString());
        }
    }
}
