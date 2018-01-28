package com.bwie.project.view;

import bean.GoodsBean;

/**
 * 作者：王庆
 * 时间：2017/12/15
 */

public interface GoodsView extends IView {
    void getGoods(GoodsBean goodsBean);

    void onFailed();
}
