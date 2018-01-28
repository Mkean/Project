package com.bwie.project.view;

import bean.InsertAddrBean;

/**
 * 作者：王庆
 * 时间：2018/1/7
 */

public interface InsertAddrView extends IView {
    void onInsertSuccess(InsertAddrBean insertAddrBean);

    void onInsertFailed(Throwable e);
}
