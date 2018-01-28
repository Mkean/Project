package com.bwie.project.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.project.R;

import java.util.List;

import bean.ShopBean;
import utils.ImageLoaderUtils;

/**
 * 作者：王庆
 * 时间：2017/12/14
 */

public class MyShopAdapter extends RecyclerView.Adapter<MyShopAdapter.MyShopHolder> {
    private List<ShopBean.DataBean> list;
    private Context context;

    public MyShopAdapter(List<ShopBean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    private onItemClickListener listener;

    public void setOnIitemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

    public interface onItemClickListener {
        void onItemClick(View view, int position);
    }

    @Override
    public MyShopHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.shop_item, parent, false);
        MyShopHolder holder = new MyShopHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(v, (Integer) v.getTag());
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(MyShopHolder holder, int position) {
        ImageLoaderUtils.getInstance().initImageLoader(list.get(position).getImages().split("!")[0], holder.img);
        holder.title.setText(list.get(position).getTitle());
        //省略号
//        holder.js.setText(list.get(position).getSubhead().substring(0,12) + "……");
        holder.js.setText(list.get(position).getSubhead());
        holder.price.setText("原价：" + list.get(position).getPrice());
        //删除线
        holder.price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.newPrice.setText("现价：" + list.get(position).getBargainPrice());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyShopHolder extends RecyclerView.ViewHolder {

        private final ImageView img;
        private final TextView title;
        private final TextView js;
        private final TextView price;
        private final TextView newPrice;

        public MyShopHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.shop_img);
            title = itemView.findViewById(R.id.shop_title);
            js = itemView.findViewById(R.id.shop_js);
            price = itemView.findViewById(R.id.shop_price);
            newPrice = itemView.findViewById(R.id.shop_newprice);
        }
    }
}
