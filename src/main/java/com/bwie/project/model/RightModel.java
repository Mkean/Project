package com.bwie.project.model;

import com.google.gson.Gson;

import java.io.IOException;

import bean.Right;
import io.reactivex.Observable;
import okhttp3.Call;
import tools.MyApi;
import utils.OkHttpUtils;
import utils.OnUiCallback;
import utils.RetrofitManager;

/**
 * 作者：王庆
 * 时间：2017/12/14
 */

public class RightModel implements IModel {

    private onRighListener listener;

    public void setOnRighListener(onRighListener listener) {
        this.listener = listener;
    }

    public interface onRighListener {
        void getrightData(Right right);
    }

    public void getRightData1(int cid) {
        OkHttpUtils.doGet(MyApi.RIGHT + "?cid=" + cid, new OnUiCallback() {
            @Override
            public void onFailed(Call call, IOException e) {

            }

            @Override
            public void onSuccess(String result) throws IOException {
                getJs(result);
            }
        });
    }

    public Observable<Right> getRightData(int cid) {
        constant.MyApi myApi = RetrofitManager.getInstance(MyApi.LOG).getmRetrofit().create(constant.MyApi.class);
        return myApi.getRightName(cid);
    }

    private void getJs(String s) {
        Gson gson = new Gson();
        Right right = gson.fromJson(s, Right.class);
        if (listener != null) {
            listener.getrightData(right);
        }
    }
}
