package com.bwie.project.view;

import bean.UpdateAddrBean;

/**
 * 作者：王庆
 * 时间：2018/1/5
 */

public interface UpdateAddrView extends IView {
    void onSuccess(UpdateAddrBean updateAddrBean);

    void onFailed(Throwable e);
}
