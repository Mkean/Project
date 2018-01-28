package com.bwie.project.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.bwie.project.R;

import java.util.List;

import bean.Right;

/**
 * 作者：王庆
 * 时间：2017/12/14
 */

public class ClassRightLeft extends RecyclerView.Adapter<ClassRightLeft.MyRightHolder> {

    private Context context;
    private List<Right.DataBean> list;
    private String uid;

    public ClassRightLeft(Context context, List<Right.DataBean> list, String uid) {
        this.context = context;
        this.list = list;
        this.uid = uid;
    }

    @Override
    public MyRightHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.class_right, parent, false);
        MyRightHolder holder = new MyRightHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyRightHolder holder, int position) {
        holder.right_title.setText(list.get(position).getName());
        List<Right.DataBean.ListBean> list = this.list.get(position).getList();
        MyGridAdapter adapter = new MyGridAdapter(list, context,uid);
        holder.grid.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyRightHolder extends RecyclerView.ViewHolder {

        private final TextView right_title;
        private final GridView grid;

        public MyRightHolder(View itemView) {
            super(itemView);
            right_title = itemView.findViewById(R.id.right_title);
            grid = itemView.findViewById(R.id.grid);
        }
    }
}
