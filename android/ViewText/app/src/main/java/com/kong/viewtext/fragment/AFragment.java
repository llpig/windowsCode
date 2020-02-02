package com.kong.viewtext.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kong.viewtext.R;

public class AFragment extends Fragment {

    Button m_ButtonFragment,m_ButtonText,m_SendMessage;
    BFragment bFragment;

    @Nullable
    TextView textView;
    //通过Activity给Fragment传递参数
    public static AFragment newInstance(String string)
    {
        AFragment aFragment =new AFragment();
        Bundle bundle=new Bundle();
        bundle.putString("title",string);
        aFragment.setArguments(bundle);
        return aFragment;
    }

    @Override
    //设置布局文件
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_a,container,false);
    }
    //当组件加载完毕时，会自动调用
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView=view.findViewById(R.id.textViewA);
        Bundle bundle=getArguments();
        if(bundle!=null)
        {
            textView.setText(bundle.getString("title").toString()+getActivity().toString());
        }
        bFragment=BFragment.newInstance("这是从AFragment中传递的数据");
        m_ButtonFragment=view.findViewById(R.id.btnChangeFragment);
        m_ButtonFragment.setOnClickListener(new OnClick());
        m_ButtonText=view.findViewById(R.id.btnChangeText);
        m_ButtonText.setOnClickListener(new OnClick());
        m_SendMessage=view.findViewById(R.id.sendMessage);
        m_SendMessage.setOnClickListener(new OnClick());
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    //修改文字的点击事件
    class OnClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnChangeText:
                    textView.setText("这是新替换的文字");
                    break;
                case R.id.btnChangeFragment:
                    getFragmentManager().beginTransaction().replace(R.id.container,bFragment).addToBackStack(null).commitAllowingStateLoss();
                    break;
                case R.id.sendMessage:
                    //通过getActivity获得当前的Activity，调用Activity中提供的方法
                    ((FragmentActivity)getActivity()).sendMessage("AFragment传递的参数");
                    break;
            }
        }
    }
}
