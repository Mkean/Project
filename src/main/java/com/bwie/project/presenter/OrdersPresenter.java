package com.bwie.project.presenter;

import com.bwie.project.model.OrdersModel;
import com.bwie.project.view.OrdersView;

import java.util.List;

import bean.OrdersBean;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：王庆
 * 时间：2017/12/20
 */

public class OrdersPresenter extends IPresenter<OrdersView> implements OrdersModel.onDataListener {
    private OrdersModel ordersModel;

    public OrdersPresenter(OrdersView view) {
        super(view);
    }


    public void getOrdersList(String uid, int page) {
//        Log.e("TAG", uid + " :  " + page);
//        model.getOrder(uid, page);
//        model.setOnDataListener(this);
        Observable<OrdersBean> orders = ordersModel.getOrders(uid, page);
        orders.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<OrdersBean>() {
                    @Override
                    public void accept(OrdersBean ordersBean) throws Exception {
                        List<OrdersBean.DataBean> data = ordersBean.getData();
                        view.onSuccess(data);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.onFailed();
                    }
                });
    }

    @Override
    public void getOrdersList(OrdersBean ordersBean) {
        String code = ordersBean.getCode();
        List<OrdersBean.DataBean> data = ordersBean.getData();
//        Log.e("TAG", code + "  " + data.size());
        if (code.equals("0")) {
            view.onSuccess(data);
        } else {
            view.onFailed();
        }
    }

    @Override
    protected void init() {
        ordersModel = new OrdersModel();
    }
}
