package com.bwie.project.view;

import java.util.List;

import bean.OrdersBean;

/**
 * 作者：王庆
 * 时间：2017/12/20
 */

public interface OrdersView extends IView {
    void onFailed();

    void onSuccess(List<OrdersBean.DataBean> data);

//    void isDataEmpty();
//    void onWeiOrders(List<OrdersBean.DataBean> data);
//
//    void onFaOrders(List<OrdersBean.DataBean> data);
//
//    void onHouOrders(List<OrdersBean.DataBean> data);
}
