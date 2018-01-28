package com.bwie.project.model;

import bean.InsertAddrBean;
import io.reactivex.Observable;
import tools.MyApi;
import utils.RetrofitManager;

/**
 * 作者：王庆
 * 时间：2018/1/7
 */

public class InsertAddrModel implements IModel {
    public Observable<InsertAddrBean> insertAddr(String uid, String mobile, String name, String addr) {
        constant.MyApi myApi = RetrofitManager.getInstance(MyApi.LOG).getmRetrofit().create(constant.MyApi.class);
        return myApi.insertAddr(uid, mobile, name, addr);
    }
}
