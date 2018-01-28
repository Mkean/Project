package com.bwie.project.register.view;


import com.bwie.project.register.bean.RegisterBean;

/**
 * 作者：王庆
 * 时间：2017/12/8
 */

public interface RegView {
    void onFailed(RegisterBean registerBean);

    void onSuccess(RegisterBean registerBean);
}
