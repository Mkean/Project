package com.bwie.project.register.presenter;


import com.bwie.project.register.bean.RegisterBean;
import com.bwie.project.register.bean.RegisterUser;
import com.bwie.project.register.model.RegisterModel;
import com.bwie.project.register.view.RegView;

/**
 * 作者：王庆
 * 时间：2017/12/8
 */

public class RegPresenter implements RegisterModel.onFinishListener {
    private RegView regView;
    private RegisterModel registerModel;

    public RegPresenter(RegView regView) {
        this.regView = regView;
        registerModel = new RegisterModel();
    }

    public void Register(RegisterUser user) {
        registerModel.Register(user);
        registerModel.setOnFinishListener(this);
    }

    @Override
    public void onFinish(RegisterBean registerBean) {
        String code = registerBean.getCode();
        if (code.equals("0")) {
            regView.onSuccess(registerBean);
        } else {
            regView.onFailed(registerBean);
        }
    }
}
