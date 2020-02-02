package com.kong.viewtext.RecyclerView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.kong.viewtext.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerGridViewActivity extends AppCompatActivity {
    private RecyclerView m_RecyclerView;
    private List<ListViewActivity.ItemDate> m_ItemList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_grid_view);
        m_RecyclerView=findViewById(R.id.recyclerGridView);
        Context context=RecyclerGridViewActivity.this;
        m_ItemList=new ArrayList<>();
        ListViewActivity listViewActivity=new ListViewActivity();
        listViewActivity.CreateItemList(m_ItemList);
        //Toast.makeText(context,""+m_ItemList.size(),Toast.LENGTH_SHORT).show();
        //第一个参数为上下文，第二个参数为每行有几个Item
        GridLayoutManager gridLayoutManager=new GridLayoutManager(context,2);
        m_RecyclerView.setLayoutManager(gridLayoutManager);
        //设置Adapter
        RecyclerListAdapter adapter=new RecyclerListAdapter(context,m_ItemList);
        m_RecyclerView.setAdapter(adapter);
    }
}
