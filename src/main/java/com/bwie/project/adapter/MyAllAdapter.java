package com.bwie.project.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.project.R;
import com.bwie.project.presenter.UpdateOrderPresenter;
import com.bwie.project.view.UpdateOrderView;

import java.util.List;

import bean.OrdersBean;
import bean.UpdateOrdersBean;

/**
 * 作者：王庆
 * 时间：2018/1/3
 */

public class MyAllAdapter extends RecyclerView.Adapter<MyAllAdapter.WeiHolder> implements UpdateOrderView {
    private List<OrdersBean.DataBean> list;
    private Context context;
    private String uid;
    private final UpdateOrderPresenter updateOrderPresenter;
    private int postion;

    public MyAllAdapter(List<OrdersBean.DataBean> list, Context context, String uid) {
        this.list = list;
        this.context = context;
        this.uid = uid;
        updateOrderPresenter = new UpdateOrderPresenter(this);
    }

    @Override
    public WeiHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.wei_item, parent, false);
        WeiHolder holder = new WeiHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(WeiHolder holder, final int position) {
        holder.createTime.setText("创建时间：" + list.get(position).getCreatetime());
        holder.id.setText("订单号：" + list.get(position).getOrderid());
        holder.price.setText("价格：" + list.get(position).getPrice());

        if (list.get(position).getStatus().equals("0")) {
            holder.state.setText("订单状态：待支付");
            holder.orderBy.setText("点击支付");
            holder.orderBy.setVisibility(View.VISIBLE);
            holder.orderBy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    postion = position;
                    updateOrderPresenter.updateOrder(uid, "1", list.get(position).getOrderid());
                }
            });
        } else if (list.get(position).getStatus().equals("1")) {
            holder.state.setText("订单状态：待发货");
            holder.orderBy.setText("点击发货");
            holder.orderBy.setVisibility(View.VISIBLE);
            holder.orderBy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    postion = position;
                    updateOrderPresenter.updateOrder(uid, "2", list.get(position).getOrderid());
                }
            });
        } else if (list.get(position).getStatus().equals("2")) {
            holder.state.setText("订单状态：已收货");
            holder.orderBy.setVisibility(View.GONE);
        }
        holder.orderDel.setOnClickListener(new View.OnClickListener() {
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

    @Override
    public void onSuccess(UpdateOrdersBean updateOrdersBean) {
        Toast.makeText(context, "修改成功", Toast.LENGTH_SHORT).show();
        list.remove(postion);
        notifyDataSetChanged();
    }

    @Override
    public void onFailed(Throwable e) {
        Toast.makeText(context, "修改失败", Toast.LENGTH_SHORT).show();
        Log.e("TAG", e.getMessage().toString());
    }

    class WeiHolder extends RecyclerView.ViewHolder {

        private final TextView createTime;
        private final TextView price;
        private final TextView id;
        private final TextView state;
        private final TextView orderBy;
        private final TextView orderDel;

        public WeiHolder(View itemView) {
            super(itemView);
            createTime = itemView.findViewById(R.id.orders_createTime);
            price = itemView.findViewById(R.id.orders_price);
            id = itemView.findViewById(R.id.orders_id);
            state = itemView.findViewById(R.id.orders_state);
            orderBy = itemView.findViewById(R.id.orderBy);
            orderDel = itemView.findViewById(R.id.orderDel);
        }
    }
}
