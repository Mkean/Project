package com.bwie.project.login.model;

import com.bwie.project.login.bean.LoginBean;
import com.bwie.project.login.bean.User;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import tools.MyApi;
import utils.OkHttpUtils;
import utils.OnUiCallback;

/**
 * 作者：王庆
 * 时间：2017/12/15
 */

public class LoginModel implements Imodel {
    public static final String TAG = "LoginModel";
    private onFinishListener lisenter;

    public void setOnFinishlisenter(onFinishListener lisenter) {
        this.lisenter = lisenter;
    }

    public interface onFinishListener {
        void onFinish(LoginBean loginBean);
    }

    @Override
    public void login(User user) {
        Map<String, String> map = new HashMap<>();
        map.put("mobile", user.getUsername());
        map.put("password", user.getPass());
//        Log.e(TAG, user.getUsername() + "  :: " + user.getPass());
        OkHttpUtils.doPost(MyApi.LOGIN, map, new OnUiCallback() {
            @Override
            public void onFailed(Call call, IOException e) {

            }

            @Override
            public void onSuccess(String result) throws IOException {
                getJS(result);
            }
        });
    }

    private void getJS(String s) {
        Gson gson = new Gson();
        LoginBean loginBean = gson.fromJson(s, LoginBean.class);
        if (lisenter != null) {
            lisenter.onFinish(loginBean);
        }
    }
}
