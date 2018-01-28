package com.bwie.project.model;

import bean.DelBean;
import io.reactivex.Observable;
import tools.MyApi;
import utils.RetrofitManager;

/**
 * 作者：王庆
 * 时间：2018/1/10
 */

public class DelModel {
    public Observable<DelBean> delCart(String uid, String pid) {
        constant.MyApi myApi = RetrofitManager.getInstance(MyApi.LOG).create(constant.MyApi.class);
        return myApi.delCart(uid, pid);
    }
}
