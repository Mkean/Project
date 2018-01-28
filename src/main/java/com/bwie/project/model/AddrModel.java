package com.bwie.project.model;


import bean.AddrBean;
import io.reactivex.Observable;
import tools.MyApi;
import utils.RetrofitManager;

/**
 * 作者：王庆
 * 时间：2018/1/5
 */

public class AddrModel implements IModel {
    public Observable<AddrBean> getAddrList(String uid) {
        constant.MyApi myApi = RetrofitManager.getInstance(MyApi.LOG).getmRetrofit().create(constant.MyApi.class);
        return myApi.getAddrList(uid);
    }
}
