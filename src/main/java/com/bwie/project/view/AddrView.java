package com.bwie.project.view;

import bean.AddrBean;

/**
 * 作者：王庆
 * 时间：2018/1/5
 */

public interface AddrView extends IView {
    void onSuccess(AddrBean addrBean);

    void onFailed(Throwable e);
}
