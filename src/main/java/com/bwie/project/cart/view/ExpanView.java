package com.bwie.project.cart.view;

import com.bwie.project.cart.bean.CartBean;

/**
 * 作者：王庆
 * 时间：2017/12/16
 */

public interface ExpanView {
    void onIsNull();

    void onFailed();

    void onSuccess(CartBean cartBean);
}
