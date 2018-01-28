package com.bwie.project.view;

import bean.UpdateBean;

/**
 * 作者：王庆
 * 时间：2018/1/6
 */

public interface AddrUpdateView extends IView {
    void onUpdateSuccess(UpdateBean updateBean);

    void onUpdateFailed(Throwable e);
}
