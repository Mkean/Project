package com.bwie.project.view;

import bean.QueryBean;

/**
 * 作者：王庆
 * 时间：2017/12/27
 */

public interface QueryView {
    void isEmpty();

    void onFailed();

    void onSuccess(QueryBean queryBean);
}
