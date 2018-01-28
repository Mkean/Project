package com.bwie.project.model;

import bean.UpdateOrdersBean;
import io.reactivex.Observable;
import tools.MyApi;
import utils.RetrofitManager;

/**
 * 作者：王庆
 * 时间：2018/1/9
 */

public class UpdateOrdersModel implements IModel{
    public Observable<UpdateOrdersBean> updateOrders(String uid, String status, String orderId) {
        constant.MyApi myApi = RetrofitManager.getInstance(MyApi.LOG).getmRetrofit().create(constant.MyApi.class);
        return myApi.updateOrders(uid, status, orderId);
    }
}
