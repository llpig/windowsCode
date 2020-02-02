package com.kong.viewtext.RecyclerView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.kong.viewtext.R;

public class MainRecyclerViewActivity extends AppCompatActivity {
    private Button m_ListView;
    private Button m_GripView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        m_ListView=findViewById(R.id.btn_ListView);
        m_GripView=findViewById(R.id.btn_GridView);

        setOnClickListener();
    }

    private void setOnClickListener()
    {
        MyOnClickListener onClickListener=new MyOnClickListener();
        m_ListView.setOnClickListener(onClickListener);
        m_GripView.setOnClickListener(onClickListener);
    }

    private class MyOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent=new Intent();
            Context context =MainRecyclerViewActivity.this;
            switch(v.getId())
            {
                case R.id.btn_ListView:
                    intent.setClass(context,ListViewActivity.class);
                    break;
                case R.id.btn_GridView:
                   intent.setClass(context,RecyclerGridViewActivity.class);
                    break;
            }
            startActivity(intent);
        }
    }

}
