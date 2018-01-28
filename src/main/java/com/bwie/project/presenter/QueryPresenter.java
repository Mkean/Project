package com.bwie.project.presenter;

import com.bwie.project.model.QueryModel;
import com.bwie.project.view.QueryView;

import bean.QueryBean;

/**
 * 作者：王庆
 * 时间：2017/12/27
 */

public class QueryPresenter implements QueryModel.getQueryGoodsListener {
    private QueryModel model;
    private QueryView view;

    public QueryPresenter(QueryView view) {
        this.view = view;
        model = new QueryModel();
    }

    public void getQueryGoods(String name, int page) {
        model.QueryData(name, page);
        model.setData(this);
    }

    @Override
    public void getQueryGoods(QueryBean queryBean) {
        String code = queryBean.getCode();
        if (code.equals("0")) {
            if (queryBean.getData().size() > 0) {
                view.onSuccess(queryBean);
            } else {
                view.isEmpty();
            }
        } else {
            view.onFailed();
        }
    }
}
