package com.bwie.project.view;

import bean.DelBean;

/**
 * 作者：王庆
 * 时间：2018/1/10
 */

public interface DelView extends IView {
    void onDelSuccess(DelBean delBean);

    void onDelFailed(Throwable e);
}
