package com.bwie.project.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.project.R;
import com.bwie.project.activity.UpdateAddrActivity;
import com.bwie.project.presenter.UpdateAddrPresenter;
import com.bwie.project.view.UpdateAddrView;

import java.util.List;

import bean.AddrBean;
import bean.UpdateAddrBean;

/**
 * 作者：王庆
 * 时间：2018/1/4
 */

public class MyAddrAdapter extends RecyclerView.Adapter<MyAddrAdapter.MyAddrHolder> implements UpdateAddrView {
    private List<AddrBean.DataBean> list;
    private Context context;
    private final UpdateAddrPresenter updateAddrPresenter;

    public MyAddrAdapter(List<AddrBean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
        updateAddrPresenter = new UpdateAddrPresenter(this);
    }

    private onLisenter lisenter;

    public void setLisenter(onLisenter lisenter) {
        this.lisenter = lisenter;
    }

    public interface onLisenter {
        void onLisener();
    }

    @Override
    public MyAddrHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.addr_item, parent, false);
        MyAddrHolder holder = new MyAddrHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyAddrHolder holder, final int position) {
        holder.name.setText(list.get(position).getName());
        holder.phone.setText(list.get(position).getMobile());
        holder.address.setText(list.get(position).getAddr());
        holder.box.setChecked(list.get(position).getStatus().equals("0") ? false : true);

        holder.status.setText(list.get(position).getStatus().equals("0") ? "设为默认" : "默认地址");
//        Log.e("TAG", list.get(position).getStatus());
        //设置默认地址
        holder.box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.box.setChecked(list.get(position).getStatus().equals("0") ? false : true);
//                Log.e("TAG", "===" + list.get(position).getStatus());
                if (list.get(position).getStatus().equals("0")) {
                    updateAddrPresenter.updateAddr(list.get(position).getUid(), list.get(position).getAddrid(), "1");
                }

            }
        });

        //编辑收货地址
        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateAddrActivity.class);
//                AddrBean.DataBean dataBean = list.get(position);
                intent.putExtra("dataBean", list.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public void onSuccess(UpdateAddrBean updateAddrBean) {
        lisenter.onLisener();
        Toast.makeText(context, "设置默认地址成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailed(Throwable e) {

    }

    class MyAddrHolder extends RecyclerView.ViewHolder {

        private final TextView name;
        private final TextView phone;
        private final TextView address;
        private final TextView update;
        private final CheckBox box;
        private final TextView status;

        public MyAddrHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.addr_name);
            phone = itemView.findViewById(R.id.addr_phone);
            address = itemView.findViewById(R.id.addr_address);
            update = itemView.findViewById(R.id.addr_update);
            box = itemView.findViewById(R.id.addr_checkbox);
            status = itemView.findViewById(R.id.addr_status);
        }
    }
}
