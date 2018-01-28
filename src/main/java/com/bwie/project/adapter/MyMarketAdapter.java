package com.bwie.project.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.project.R;

import java.util.List;

import bean.Market;

/**
 * 作者：王庆
 * 时间：2017/12/14
 */

public class MyMarketAdapter extends RecyclerView.Adapter<MyMarketAdapter.MyMarketHolder> {

    private Context context;
    private List<Market> list;

    public MyMarketAdapter(Context context, List<Market> list) {
        this.context = context;
        this.list = list;
    }

    private onItemClickListener listener;

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

    public interface onItemClickListener {
        void onClickItem(View view, int position);
    }

    @Override
    public MyMarketHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.market_item, null, false);
        MyMarketHolder holder = new MyMarketHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClickItem(v, (Integer) v.getTag());
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(MyMarketHolder holder, int position) {
        holder.market_img.setImageResource(list.get(position).getImg());
        holder.market_title.setText(list.get(position).getTitle());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyMarketHolder extends RecyclerView.ViewHolder {

        private final ImageView market_img;
        private final TextView market_title;

        public MyMarketHolder(View itemView) {
            super(itemView);
            market_img = itemView.findViewById(R.id.market_img);
            market_title = itemView.findViewById(R.id.market_title);
        }
    }
}
