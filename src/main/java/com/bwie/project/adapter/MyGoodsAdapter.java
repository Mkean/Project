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

import bean.Banner_Bean;
import utils.ImageLoaderUtils;

/**
 * 作者：王庆
 * 时间：2017/12/14
 */

public class MyGoodsAdapter extends RecyclerView.Adapter<MyGoodsAdapter.MyGoodsHolder> {

    private Context context;
    List<Banner_Bean.MiaoshaBean.ListBeanX> list;

    public MyGoodsAdapter(Context context, List<Banner_Bean.MiaoshaBean.ListBeanX> list) {
        this.context = context;
        this.list = list;
    }

    private onViewListener listener;

    public void setListener(onViewListener listener) {
        this.listener = listener;
    }

    public interface onViewListener {
        void setOnClickItemListener(View view, int position);
    }

    @Override
    public MyGoodsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.stag_item, parent, false);
        MyGoodsHolder holder = new MyGoodsHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.setOnClickItemListener(v, (Integer) v.getTag());
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(MyGoodsHolder holder, int position) {
        holder.stag_title.setText(list.get(position).getTitle());
        ImageLoaderUtils.getInstance().initImageLoader(list.get(position).getImages().split("!")[0], holder.stag_img);
        holder.itemView.setTag(position);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyGoodsHolder extends RecyclerView.ViewHolder {

        private final ImageView stag_img;
        private final TextView stag_title;


        public MyGoodsHolder(View itemView) {
            super(itemView);
            stag_img = itemView.findViewById(R.id.stag_img);
            stag_title = itemView.findViewById(R.id.stag_title);

        }
    }
}
