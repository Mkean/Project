package com.bwie.project.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bwie.project.R;

import java.util.List;

import bean.OrdersBean;

/**
 * 作者：王庆
 * 时间：2017/12/20
 */

public class ShouAdapter extends RecyclerView.Adapter<ShouAdapter.ShouHolder> {
    private List<OrdersBean.DataBean> list;
    private Context context;

    public ShouAdapter(List<OrdersBean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ShouHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.shou_item, parent, false);
        ShouHolder holder = new ShouHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ShouHolder holder, final int position) {
        holder.createTime.setText("创建时间：" + list.get(position).getCreatetime());
        holder.id.setText("订单号：" + list.get(position).getOrderid());
        holder.price.setText("价格：" + list.get(position).getPrice());
        holder.state.setText(list.get(position).getStatus().equals("2") ? "订单状态：已收货" : "订单状态：未收回");
        holder.orderDels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(position);
//                Toast.makeText(context, "position:" + position, Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    class ShouHolder extends RecyclerView.ViewHolder {

        private final TextView createTime;
        private final TextView price;
        private final TextView id;
        private final TextView state;
        private final View orderDels;

        public ShouHolder(View itemView) {
            super(itemView);
            createTime = itemView.findViewById(R.id.shou_createTime);
            price = itemView.findViewById(R.id.shou_price);
            id = itemView.findViewById(R.id.shou_id);
            state = itemView.findViewById(R.id.shou_state);
            orderDels = itemView.findViewById(R.id.orderDels);
        }
    }
}
