package com.bwie.project.cart.model;

import com.bwie.project.cart.bean.CartBean;
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
 * 时间：2017/12/16
 */

public class ExpanModel {

    private onListener listener;

    public void setListener(onListener listener) {
        this.listener = listener;
    }

    public interface onListener {
        void getData(CartBean cartBean);

        void isNull();
    }

    public void getExpan(String uid) {
        Map map = new HashMap();
        map.put("source", "android");
        map.put("uid", uid);
        OkHttpUtils.doPost(MyApi.SELECT, map, new OnUiCallback() {
            @Override
            public void onFailed(Call call, IOException e) {

            }

            @Override
            public void onSuccess(String result) throws IOException {
                getJs(result);
            }
        });
    }

    private void getJs(String s) {
        if (s == null || s.equals(null) || s.equals("null")) {
            if (listener != null) {
                listener.isNull();
            }
            return;
        }
        Gson gson = new Gson();
        CartBean cartBean = gson.fromJson(s, CartBean.class);
        if (listener != null) {
            listener.getData(cartBean);
        }
    }
}
