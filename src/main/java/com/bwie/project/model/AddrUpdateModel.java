package com.bwie.project.model;

import com.google.gson.Gson;

import java.io.IOException;

import bean.UpdateBean;
import io.reactivex.Observable;
import okhttp3.Call;
import tools.MyApi;
import utils.OkHttpUtils;
import utils.OnUiCallback;
import utils.RetrofitManager;

/**
 * 作者：王庆
 * 时间：2018/1/6
 */

public class AddrUpdateModel implements IModel {
    public Observable<UpdateBean> updateAddr(String uid, String addrid, String mobile, String name, String addr) {
        constant.MyApi myApi = RetrofitManager.getInstance(MyApi.LOG).getmRetrofit().create(constant.MyApi.class);
//        Log.e("YAG", myApi.toString());
        return myApi.addrUpdate(uid, addrid, mobile, name, addr);
    }

    onListener listener;

    public void setListener(onListener listener) {
        this.listener = listener;
    }

    public interface onListener {
        void listener(UpdateBean updateBean);
    }

    public void getData(String uid, String addrid, long mobile, String name, String addr) {
        OkHttpUtils.doGet(MyApi.LOG + "user/updateAddr?uid=" + uid + "&addrid=" + addrid + "&mobile=" + mobile + "&name=" + name + "&addr=" + addr, new OnUiCallback() {
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

        Gson gson = new Gson();
        UpdateBean updateBean = gson.fromJson(s, UpdateBean.class);
        if (listener != null) {
            listener.listener(updateBean);
        }
    }
}
