package com.bwie.project.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.bwie.project.R;
import com.youth.banner.Banner;

import java.util.Arrays;
import java.util.List;

import app.ImageUtils;
import bean.GoodsBean;

/**
 * 作者：王庆
 * 时间：2017/12/15
 */

public class GoodsAdapter extends RecyclerView.Adapter {

    private GoodsBean.DataBean data;
    private Context context;

    public GoodsAdapter(GoodsBean.DataBean data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(context).inflate(R.layout.goods_item, null, false);
            GoodsHolder holder = new GoodsHolder(view);
            return holder;
        } else if (viewType == 1) {
            View view = LayoutInflater.from(context).inflate(R.layout.webview_item, null, false);
            MyWebHolder holder = new MyWebHolder(view);
            return holder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof GoodsHolder) {
            String[] split = data.getImages().split("\\|");
            List<String> list = Arrays.asList(split);
            ((GoodsHolder) holder).gbanner.setImageLoader(new ImageUtils());
            ((GoodsHolder) holder).gbanner.setImages(list);
            ((GoodsHolder) holder).gbanner.start();
            ((GoodsHolder) holder).js.setText(data.getSubhead());
            ((GoodsHolder) holder).title.setText(data.getTitle());
            ((GoodsHolder) holder).price.setText("￥" + data.getPrice());
            ((GoodsHolder) holder).price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            ((GoodsHolder) holder).newPrice.setText("￥" + data.getBargainPrice());
        }
        if (holder instanceof MyWebHolder) {
            ((MyWebHolder) holder).web.loadUrl(data.getDetailUrl());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    class GoodsHolder extends RecyclerView.ViewHolder {

        private final Banner gbanner;
        private final TextView title;
        private final TextView js;
        private final TextView price;
        private final TextView newPrice;

        public GoodsHolder(View itemView) {
            super(itemView);
            gbanner = itemView.findViewById(R.id.gbanner);
            title = itemView.findViewById(R.id.g_title);
            js = itemView.findViewById(R.id.goods_js);
            price = itemView.findViewById(R.id.goods_price);
            newPrice = itemView.findViewById(R.id.goods_newPrice);
        }
    }

    class MyWebHolder extends RecyclerView.ViewHolder {

        private final WebView web;

        public MyWebHolder(View itemView) {
            super(itemView);
            web = itemView.findViewById(R.id.web);
        }
    }
}
