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
import com.bwie.project.adapter.FaAdapter;
import com.bwie.project.presenter.OrdersPresenter;
import com.bwie.project.view.OrdersView;

import java.util.ArrayList;
import java.util.List;

import bean.OrdersBean;

/**
 * 作者：王庆
 * 时间：2017/12/29
 */

public class Fragment_Fa extends LazyFragment implements OrdersView {
    private String uid;
    private RecyclerView lv;
    private OrdersPresenter ordersPresenter;
    private List<OrdersBean.DataBean> list;
    private FaAdapter adapter;
    private View view;
    private boolean isPrepared;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_fa, container, false);
        Intent intent = getActivity().getIntent();
        uid = intent.getStringExtra("uid");
        list = new ArrayList<>();
        isPrepared = true;
        initView();
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
        if (!isPrepared || !isVisible) {
            return;
        }
        ordersPresenter = new OrdersPresenter(this);
        ordersPresenter.getOrdersList(uid, 3);
    }

    private void initView() {
        lv = view.findViewById(R.id.fa_lv);
    }

    @Override
    public void onFailed() {
        Toast.makeText(getContext(), "请求数据失败，请重新请求！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(List<OrdersBean.DataBean> data) {
        list.clear();
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getStatus().equals("1")) {
                list.add(data.get(i));
            }
        }
        if (list.size() <= 0) {
            Toast.makeText(getContext(), "你没有订单要处理！", Toast.LENGTH_SHORT).show();
        } else {
            adapter = new FaAdapter(list, getContext(), uid);
            lv.setLayoutManager(new LinearLayoutManager(getContext()));
            lv.setAdapter(adapter);
        }
    }

}
