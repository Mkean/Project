package com.bwie.project.view;

import java.util.List;

import bean.Right;

/**
 * 作者：王庆
 * 时间：2017/12/14
 */

public interface RightView extends IView {
    void getData(List<Right.DataBean> data );

    void isEmpty();
}
