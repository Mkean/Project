package com.bwie.project.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bwie.project.R;
import com.bwie.project.adapter.MyAllAdapter;
import com.bwie.project.presenter.OrdersPresenter;
import com.bwie.project.view.OrdersView;

import java.util.List;

import bean.OrdersBean;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者：王庆
 * 时间：2017/12/29
 */

public class Fragment_All extends LazyFragment implements OrdersView {
    @InjectView(R.id.all_lv)
    RecyclerView mAllLv;
    private OrdersPresenter ordersPresenter;
    private MyAllAdapter adapter;
    private String uid;
    private boolean isPrepared;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all, container, false);
        ButterKnife.inject(this, view);
        Intent intent = getActivity().getIntent();
        uid = intent.getStringExtra("uid");
        isPrepared = true;
        LazyLoad();
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            LazyLoad();
        }
    }

    @Override
    protected void LazyLoad() {
        if(!isPrepared ||!isVisible){
            return;
        }
        ordersPresenter = new OrdersPresenter(this);
        ordersPresenter.getOrdersList(uid, 3);
    }


    @Override
    public void onFailed() {
        Toast.makeText(getContext(), "请求数据失败，请重新请求！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(List<OrdersBean.DataBean> data) {
        if (data.size() <= 0) {
            Toast.makeText(getContext(), "你没有订单要处理！", Toast.LENGTH_SHORT).show();
        } else {
            adapter = new MyAllAdapter(data, getContext(), uid);
            mAllLv.setLayoutManager(new LinearLayoutManager(getContext()));
            mAllLv.setAdapter(adapter);
        }
    }

}
