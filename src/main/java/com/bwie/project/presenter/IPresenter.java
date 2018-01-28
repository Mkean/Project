package com.bwie.project.presenter;

import com.bwie.project.view.IView;

/**
 * 作者：王庆
 * 时间：2018/1/4
 */

public abstract class IPresenter<T extends IView> {
    protected T view;

    public IPresenter(T view) {
        this.view = view;
        init();
    }

    protected abstract void init();
}
