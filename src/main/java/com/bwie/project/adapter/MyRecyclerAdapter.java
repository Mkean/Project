package com.bwie.project.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bwie.project.R;
import com.bwie.project.activity.GoodsActivity;
import com.bwie.project.activity.WebActivity;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import app.ImageUtils;
import bean.Banner_Bean;
import bean.Market;
import utils.TimeTextView;

/**
 * 作者：王庆
 * 时间：2017/12/14
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<Banner_Bean.MiaoshaBean.ListBeanX> list;
    private ArrayList<Market> marketList;
    private List<Banner_Bean.DataBean> data;
    private String uid;

    public MyRecyclerAdapter(Context context, List<Banner_Bean.MiaoshaBean.ListBeanX> list, List<Banner_Bean.DataBean> data, String uid) {
        this.context = context;
        this.list = list;
        this.data = data;
        this.uid = uid;
    }

    private onClickListener listener;

    public void setOnClickListener(onClickListener listener) {
        this.listener = listener;
    }

    public interface onClickListener {
        void onBannerListener(View view, int position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1) {
            View view = LayoutInflater.from(context).inflate(R.layout.market_layout, parent, false);
            MyViewHolderA holderA = new MyViewHolderA(view);
            return holderA;
        } else if (viewType == 3) {
            View view = LayoutInflater.from(context).inflate(R.layout.stag_layout, parent, false);
            MyViewHolderB holderB = new MyViewHolderB(view);
            return holderB;
        } else if (viewType == 0) {
            View view = LayoutInflater.from(context).inflate(R.layout.banner_item, null, false);
            MyViewHolderC holderC = new MyViewHolderC(view);
            return holderC;
        } else if (viewType == 2) {
            View view = LayoutInflater.from(context).inflate(R.layout.toptiao, parent, false);
            MyViewHolderD holderD = new MyViewHolderD(view);
            return holderD;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        initData();
        if (holder instanceof MyViewHolderA) {
            ((MyViewHolderA) holder).mlv.setLayoutManager(new GridLayoutManager(context, 5));
            MyMarketAdapter adapter = new MyMarketAdapter(context, marketList);
            adapter.setOnItemClickListener(new MyMarketAdapter.onItemClickListener() {
                @Override
                public void onClickItem(View view, int position) {
                    Toast.makeText(context, "点击了" + position, Toast.LENGTH_SHORT).show();
                }
            });
            ((MyViewHolderA) holder).mlv.setAdapter(adapter);
        }
        if (holder instanceof MyViewHolderB) {
            ((MyViewHolderB) holder).tv.setTimes(new String[]{"1", "1", "6"});
            ((MyViewHolderB) holder).tv.setRun(true);
            ((MyViewHolderB) holder).tv.run();
            ((MyViewHolderB) holder).slv.setLayoutManager(new GridLayoutManager(context, 2));
            MyGoodsAdapter goodsAdapter = new MyGoodsAdapter(context, list);
            goodsAdapter.setListener(new MyGoodsAdapter.onViewListener() {
                @Override
                public void setOnClickItemListener(View view, int position) {
                    Intent intent = new Intent(context, GoodsActivity.class);
                    intent.putExtra("pid", list.get(position).getPid());
                    intent.putExtra("uid", uid);
                    context.startActivity(intent);
                }
            });
            ((MyViewHolderB) holder).slv.setAdapter(goodsAdapter);
        }
        if (holder instanceof MyViewHolderC) {
            final List<String> list = new ArrayList<>();
            for (int i = 0; i < data.size(); i++) {
                list.add(data.get(i).getIcon());
            }
            ((MyViewHolderC) holder).mbanner.setImageLoader(new ImageUtils());
            ((MyViewHolderC) holder).mbanner.setImages(list);
            ((MyViewHolderC) holder).mbanner.start();
            ((MyViewHolderC) holder).mbanner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    String type = data.get(position).getType();
                    if (type.equals("0")) {
                        Intent intent = new Intent(context, WebActivity.class);
                        intent.putExtra("url", data.get(position).getUrl());
                        context.startActivity(intent);
                    }
                }
            });
        }
        if (holder instanceof MyViewHolderD) {
            ((MyViewHolderD) holder).top_img.setImageResource(R.drawable.tbtt);
            ((MyViewHolderD) holder).top_content.addView(View.inflate(context, R.layout.pmd, null));
        }
    }

    public void initData() {
        marketList = new ArrayList<>();
        marketList.add(new Market(R.drawable.g1, "天猫"));
        marketList.add(new Market(R.drawable.g2, "聚划算"));
        marketList.add(new Market(R.drawable.g3, "天猫国际"));
        marketList.add(new Market(R.drawable.g4, "外卖"));
        marketList.add(new Market(R.drawable.g5, "天猫超市"));
        marketList.add(new Market(R.drawable.g6, "充值中心"));
        marketList.add(new Market(R.drawable.g7, "飞猪旅行"));
        marketList.add(new Market(R.drawable.g8, "领金币"));
        marketList.add(new Market(R.drawable.g9, "拍卖"));
        marketList.add(new Market(R.drawable.g10, "分类"));
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return 4;
    }


    class MyViewHolderA extends RecyclerView.ViewHolder {

        private final RecyclerView mlv;

        public MyViewHolderA(View itemView) {
            super(itemView);
            mlv = itemView.findViewById(R.id.mlv);
        }
    }

    class MyViewHolderB extends RecyclerView.ViewHolder {

        private final RecyclerView slv;
        private final TimeTextView tv;

        public MyViewHolderB(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.time_tv);
            slv = itemView.findViewById(R.id.srlv);
        }
    }

    class MyViewHolderC extends RecyclerView.ViewHolder {

        private final Banner mbanner;

        public MyViewHolderC(View itemView) {
            super(itemView);
            mbanner = itemView.findViewById(R.id.mbanner);
        }
    }

    class MyViewHolderD extends RecyclerView.ViewHolder {

        private final ImageView top_img;
        private final ViewFlipper top_content;

        public MyViewHolderD(View itemView) {
            super(itemView);
            top_img = itemView.findViewById(R.id.top_img);
            top_content = itemView.findViewById(R.id.top_content);
        }
    }
}
