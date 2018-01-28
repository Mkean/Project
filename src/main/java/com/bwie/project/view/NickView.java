package com.bwie.project.view;

import bean.NickBean;

/**
 * 作者：王庆
 * 时间：2018/1/4
 */

public interface NickView extends IView {
    void onSucces(NickBean nickBean);

    void onFailed(Throwable e);
}
