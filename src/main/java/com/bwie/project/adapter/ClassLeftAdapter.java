package com.bwie.project.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bwie.project.R;

import java.util.List;

import bean.LeftBean;

/**
 * 作者：王庆
 * 时间：2017/12/14
 */

public class ClassLeftAdapter extends RecyclerView.Adapter<ClassLeftAdapter.MyLeftHolder> {

    private Context context;
    private List<LeftBean.DataBean> list;
    public static int TagPosition;

    public static int getTagPosition() {
        return TagPosition;
    }

    public static void setTagPosition(int tagPosition) {
        TagPosition = tagPosition;
    }

    public ClassLeftAdapter(Context context, List<LeftBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    private onClickListener listener;

    public void setOnClickListener(onClickListener listener) {
        this.listener = listener;
    }

    public interface onClickListener {
        void onClick(View view, int position);
    }

    @Override
    public MyLeftHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.class_left, parent, false);
        MyLeftHolder holder = new MyLeftHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(view, (Integer) view.getTag());
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(MyLeftHolder holder, int position) {
        if (position == getTagPosition()) {
            holder.itemView.setBackgroundColor(Color.GRAY);
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE);
        }
        holder.left.setText(list.get(position).getName());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyLeftHolder extends RecyclerView.ViewHolder {

        private final TextView left;

        public MyLeftHolder(View itemView) {
            super(itemView);
            left = itemView.findViewById(R.id.left);
        }
    }
}
