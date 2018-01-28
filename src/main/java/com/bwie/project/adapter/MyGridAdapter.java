package com.bwie.project.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.bwie.project.R;
import com.bwie.project.activity.ShopListActivity;

import java.util.List;

import bean.Right;

/**
 * 作者：王庆
 * 时间：2017/12/14
 */

public class MyGridAdapter extends BaseAdapter {
    private List<Right.DataBean.ListBean> list;
    private Context context;
    private String uid;

    public MyGridAdapter(List<Right.DataBean.ListBean> list, Context context, String uid) {
        this.list = list;
        this.context = context;
        this.uid = uid;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHodler holder;
        if (view == null) {
            holder = new ViewHodler();
            view = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false);
            holder.Bt = view.findViewById(R.id.grid_bt);
            view.setTag(holder);
        } else {
            holder = (ViewHodler) view.getTag();
        }

        holder.Bt.setText(list.get(position).getName());
        holder.Bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pscid = list.get(position).getPscid();
                Intent intent = new Intent(context, ShopListActivity.class);
                intent.putExtra("uid", uid);
                intent.putExtra("pscid", pscid);
                context.startActivity(intent);
            }
        });
        return view;
    }

    class ViewHodler {
        Button Bt;
    }
}
