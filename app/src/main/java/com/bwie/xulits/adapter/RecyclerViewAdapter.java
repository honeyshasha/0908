
package com.bwie.xulits.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bwie.xulits.R;
import com.bwie.xulits.bean.Catogray;

import java.util.List;

/**
 * Created by 小傻瓜 on 2017/9/5.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{
    private Context context;
    private List<Catogray> list;
    private OnItemClickListener onItemClickListener;

    public RecyclerViewAdapter(Context context, List<Catogray> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //初始化条目view
        View view= LayoutInflater.from(context).inflate(R.layout.item_xia_zai,null);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        //实现自己的回调接口（注意回调接口，哪个场景下使用，就在哪里设置一下，才能起作用）
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClickListener((Integer) view.getTag(), view);
            }
        });
        return myViewHolder;
    }
    /**
     * 这个方法主要用于处理逻辑（绘制ui数据）
     */
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.name.setText(list.get(position).name);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private CheckBox checkBox;
        public MyViewHolder(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            checkBox=itemView.findViewById(R.id.off_checkBox);
        }
    }
    /**
     * 供调用者调用的接口（所以声明为public）
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    /**
     * 条目点击事件接口（recyclerview本身不支持点击事件，必须要自己写）
     */
    public interface OnItemClickListener {
        void onItemClickListener(int pos, View view);
    }
}
