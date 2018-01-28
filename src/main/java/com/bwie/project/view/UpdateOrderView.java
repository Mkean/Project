package com.bwie.project.view;

import bean.UpdateOrdersBean;

/**
 * 作者：王庆
 * 时间：2018/1/9
 */

public interface UpdateOrderView extends IView {
    void onSuccess(UpdateOrdersBean updateOrdersBean);

    void onFailed(Throwable e);
}
