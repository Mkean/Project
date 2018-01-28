package com.bwie.project.model;

import bean.UpdateAddrBean;
import io.reactivex.Observable;
import tools.MyApi;
import utils.RetrofitManager;

/**
 * 作者：王庆
 * 时间：2018/1/5
 */

public class UpdateAddrModel implements IModel {
    public Observable<UpdateAddrBean> updateAddr(String uid, String addrid, String status) {
        constant.MyApi myApi = RetrofitManager.getInstance(MyApi.LOG).getmRetrofit().create(constant.MyApi.class);
        return myApi.updateAddr(uid, addrid, status);
    }
}
