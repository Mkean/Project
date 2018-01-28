package com.bwie.project.cart.presenter;

import com.bwie.project.cart.bean.CartBean;
import com.bwie.project.cart.model.ExpanModel;
import com.bwie.project.cart.view.ExpanView;

/**
 * 作者：王庆
 * 时间：2017/12/16
 */

public class ExpanPresenter implements ExpanModel.onListener {
    private ExpanModel expanModel;
    private ExpanView expanView;

    public ExpanPresenter(ExpanView expanView) {
        this.expanView = expanView;
        expanModel = new ExpanModel();
    }

    public void getData(String uid) {
        expanModel.getExpan(uid);
        expanModel.setListener(this);
    }

    @Override
    public void getData(CartBean cartBean) {
        String code = cartBean.getCode();
        if (code.equals("0")) {
            expanView.onSuccess(cartBean);
        } else {
            expanView.onFailed();
        }
    }

    @Override
    public void isNull() {
        expanView.onIsNull();
    }
}
