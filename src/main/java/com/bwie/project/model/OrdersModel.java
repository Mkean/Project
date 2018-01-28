package com.bwie.project.model;

import com.google.gson.Gson;

import java.io.IOException;

import bean.OrdersBean;
import io.reactivex.Observable;
import okhttp3.Call;
import tools.MyApi;
import utils.OkHttpUtils;
import utils.OnUiCallback;
import utils.RetrofitManager;

/**
 * 作者：王庆
 * 时间：2017/12/20
 */

public class OrdersModel implements IModel {

    private onDataListener listener;

    public void setOnDataListener(onDataListener listener) {
        this.listener = listener;
    }

    public interface onDataListener {
        void getOrdersList(OrdersBean ordersBean);
    }

    public Observable<OrdersBean> getOrders(String uid, int page) {
        constant.MyApi myApi = RetrofitManager.getInstance(MyApi.LOG).getmRetrofit().create(constant.MyApi.class);
        return myApi.getOrders(uid, "android", page);
    }

    public void getOrder(String uid, int page) {
        OkHttpUtils.doGet(MyApi.ORDERS + "?uid=" + uid + "&source=android&page=" + page, new OnUiCallback() {
            @Override
            public void onFailed(Call call, IOException e) {

            }

            @Override
            public void onSuccess(String result) throws IOException {
                getJs(result);
            }
        });
    }

    private void getJs(String s) {
        Gson gson = new Gson();
        OrdersBean ordersBean = gson.fromJson(s, OrdersBean.class);
        if (listener != null) {
            listener.getOrdersList(ordersBean);
        }
    }
}
