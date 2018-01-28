package com.bwie.project.model;

import constant.MyApi;
import bean.NickBean;
import io.reactivex.Observable;
import utils.RetrofitManager;

/**
 * 作者：王庆
 * 时间：2018/1/4
 */

public class NickModel {
    public Observable<NickBean> UpdateNick(String uid, String nickName) {
        MyApi myApi = RetrofitManager.getInstance(tools.MyApi.LOG).getmRetrofit().create(MyApi.class);
        return myApi.updateNick(uid, nickName);
    }
}
