package com.kong.viewtext.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.kong.viewtext.R;

import java.util.List;
import java.util.SplittableRandom;

public class RecyclerListAdapter extends Adapter<RecyclerListAdapter.MyViewHolder> {
    @NonNull
    private Context m_Context;
    private List<ListViewActivity.ItemDate> m_ItemList;
    private View inflater;

    private ItemOnClickListener m_ItemOnClickListener;
    private ItemLongOnClickListener m_LongOnClickListener;

    public void setItemOnClickListener(ItemOnClickListener OnClickListener)
    {
        this.m_ItemOnClickListener=OnClickListener;
    }

    public void setItemLongOnClickListener(ItemLongOnClickListener longOnClickListener)
    {
        this.m_LongOnClickListener=longOnClickListener;
    }

    public RecyclerListAdapter(Context context, List itemList)
    {
        this.m_Context=context;
        this.m_ItemList=itemList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater=LayoutInflater.from(m_Context).inflate(R.layout.recycle_linear_item_layout,parent,false);

        return new MyViewHolder(inflater);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        //绑定数据和控件
        holder.itemDate.imageView.setImageResource(m_ItemList.get(position).imageView);
        holder.itemDate.textView1.setText(m_ItemList.get(position).textView1);
        holder.itemDate.textView2.setText(m_ItemList.get(position).textView2);
        holder.itemDate.textView3.setText(m_ItemList.get(position).textView3);
        holder.itemDate.textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //当按钮被点击时获得焦点
                Toast.makeText(m_Context,"Text被点击",Toast.LENGTH_SHORT).show();
                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                v.requestFocus();
            }
        });
        holder.itemDate.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(m_ItemOnClickListener!=null)
                {
                    m_ItemOnClickListener.onClick(v,position);
                }
            }
        });
        holder.itemDate.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                if(m_LongOnClickListener!=null)
                {
                    m_LongOnClickListener.longOnClick(v,position);
                }
                //返回值：false 此处对消息的处理没有成功，其他事件可以继续处理
                //返回值：true 此处对消息的处理完成，其他事件不必再处理
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        //该返回值时指界面上有多少行
        return m_ItemList.size();
    }

    static private class ItemDate{
        public LinearLayout linearLayout;
        public ImageView imageView;
        public TextView textView1,textView2,textView3;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        //初始化Item中的控件
        ItemDate itemDate=new ItemDate();
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemDate.linearLayout=itemView.findViewById(R.id.listViewLinear);
            itemDate.imageView=itemView.findViewById(R.id.listViewImage);
            itemDate.textView1=itemView.findViewById(R.id.listViewTextView1);
            itemDate.textView2=itemView.findViewById(R.id.listViewTextView2);
            itemDate.textView3=itemView.findViewById(R.id.listViewTextView3);
        }
    }

    //定义点击事件的接口
    public interface ItemOnClickListener{
        void onClick(View view, int pos);
    }
    //定义长按事件的接口
    public interface ItemLongOnClickListener{
        void longOnClick(View view,int pos);
    }
}
