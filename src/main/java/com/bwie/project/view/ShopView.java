package com.bwie.project.view;

import java.util.List;

import bean.ShopBean;

/**
 * 作者：王庆
 * 时间：2017/12/14
 */

public interface ShopView extends IView {
    void isShopEmpty();

    void getDatas(List<ShopBean.DataBean> data);
}
