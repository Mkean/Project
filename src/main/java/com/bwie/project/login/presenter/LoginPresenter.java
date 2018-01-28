package com.bwie.project.login.presenter;

import android.util.Log;

import com.bwie.project.login.bean.LoginBean;
import com.bwie.project.login.bean.User;
import com.bwie.project.login.model.LoginModel;
import com.bwie.project.login.view.Iview;

/**
 * 作者：王庆
 * 时间：2017/12/15
 */

public class LoginPresenter implements LoginModel.onFinishListener {
    public static final String TAG = "LoginPresenter";
    private Iview iview;
    private LoginModel loginModel;

    public LoginPresenter(Iview iview) {
        this.iview = iview;
        loginModel = new LoginModel();
    }

    public void login(User user) {
        loginModel.login(user);
//        Log.e(TAG, user.getUsername() + "  " + user.getPass());
        loginModel.setOnFinishlisenter(this);
    }


    @Override
    public void onFinish(LoginBean loginBean) {
        String code = loginBean.getCode();
        if (code.equals("0")) {
            iview.onSuccess(loginBean);
        } else {
            iview.onFailed(loginBean);
        }
    }
}

