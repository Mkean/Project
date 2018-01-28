package com.bwie.project.login.view;

import com.bwie.project.login.bean.LoginBean;

/**
 * 作者：王庆
 * 时间：2017/12/15
 */

public interface Iview {
    void onFailed(LoginBean loginBean);

    void onSuccess(LoginBean loginBean);
}
