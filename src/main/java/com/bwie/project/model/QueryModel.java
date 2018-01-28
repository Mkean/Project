package com.bwie.project.model;

import com.google.gson.Gson;

import java.io.IOException;

import bean.QueryBean;
import okhttp3.Call;
import tools.MyApi;
import utils.OkHttpUtils;
import utils.OnUiCallback;

/**
 * 作者：王庆
 * 时间：2017/12/27
 */

public class QueryModel {

    private getQueryGoodsListener listener;

    public void setData(getQueryGoodsListener data) {
        this.listener = data;
    }

    public interface getQueryGoodsListener {
        void getQueryGoods(QueryBean queryBean);
    }

    public void QueryData(String name, int page) {
        OkHttpUtils.doGet(MyApi.QUERYGOODS + "?keywords=" + name + "&page=" + page + "&source=android", new OnUiCallback() {
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
        QueryBean queryBean = gson.fromJson(s, QueryBean.class);
        if (listener != null) {
            listener.getQueryGoods(queryBean);
        }
    }
}
