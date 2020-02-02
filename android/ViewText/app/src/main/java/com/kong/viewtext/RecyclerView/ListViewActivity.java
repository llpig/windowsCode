package com.kong.viewtext.RecyclerView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kong.viewtext.R;

import java.util.ArrayList;
import java.util.List;

public class ListViewActivity extends AppCompatActivity {
    private RecyclerView m_ListRecycler;
    private List<ItemDate> DateList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        DateList=new ArrayList<>();
        try {
            CreateItemList(DateList);
            m_ListRecycler=findViewById(R.id.listViewTest);
            //设置布局管理器
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(ListViewActivity.this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            m_ListRecycler.setLayoutManager(linearLayoutManager);
            RecyclerListAdapter recyclerListAdapter=new RecyclerListAdapter(ListViewActivity.this,DateList);
            //设置点击事件
            recyclerListAdapter.setItemOnClickListener(new RecyclerListAdapter.ItemOnClickListener() {
                @Override
                public void onClick(View view, int pos) {
                    Toast.makeText(ListViewActivity.this,"第"+pos+"个按钮被点击",Toast.LENGTH_SHORT).show();
                }
            });

            recyclerListAdapter.setItemLongOnClickListener(new RecyclerListAdapter.ItemLongOnClickListener() {
                @Override
                public void longOnClick(View view, int pos) {
                    Toast.makeText(ListViewActivity.this,"第"+pos+"个按钮被长按",Toast.LENGTH_SHORT).show();

                }
            });
            //设置Adapter
            m_ListRecycler.setAdapter(recyclerListAdapter);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(ListViewActivity.this,"程序发生异常",Toast.LENGTH_SHORT).show();
        }

    }

    public void CreateItemList(List dataList)
    {
        dataList.add(new ItemDate(R.drawable.gg_bond,"GGBond","何必呢？何必逼我出手呢！","棒棒糖"));
        dataList.add(new ItemDate(R.drawable.chaorenqiang,"超人强","生命是用来浪费在路上的，才华是用来挥霍的","棒棒糖"));
        dataList.add(new ItemDate(R.drawable.feifei_princess,"菲菲公主","小猪猪,你没事吧","棒棒糖"));
        dataList.add(new ItemDate(R.drawable.mihu_doctor,"迷糊博士","乌鸦复活，那叫尸变；凤凰重生，那叫涅盘！","棒棒糖"));
        dataList.add(new ItemDate(R.drawable.poby,"波比","虽然我长的帅，但你也不能对帅哥有这么大的偏见呀！","棒棒糖"));
        dataList.add(new ItemDate(R.drawable.xiaodaidai,"小呆呆","想想人都会死的，骂人都觉得没意思了","棒棒糖"));

    }

    public class ItemDate
    {
        ItemDate(int imageResource,String userName,String signature,String snack)
        {
            imageView=imageResource;
            textView1=userName;
            textView2=signature;
            textView3="最喜欢的零食:"+snack;
        }
        //定义数据结构
        public int imageView;
        public String textView1,textView2,textView3;
    }
}
