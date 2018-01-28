package com.bwie.project.model;

import bean.LeftBean;
import io.reactivex.Observable;
import tools.MyApi;
import utils.RetrofitManager;

/**
 * 作者：王庆
 * 时间：2017/12/14
 */

public class LeftModel implements IModel {
//
//    private onLeftListener listener;
//
//    public void setOnLeftListener(onLeftListener listener) {
//        this.listener = listener;
//    }
//
//    public interface onLeftListener {
//        void getleftData(LeftBean leftBean);
//    }

    public Observable<LeftBean> getLeftName() {
//        OkHttpUtils.doGet(MyApi.LEFT, new OnUiCallback() {
//            @Override
//            public void onFailed(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onSuccess(String result) throws IOException {
//                getJs(result);
//            }
//        });
        constant.MyApi myApi = RetrofitManager.getInstance(MyApi.LOG).getmRetrofit().create(constant.MyApi.class);
        return myApi.getLeftName();
    }

//    private void getJs(String s) {
//        Gson gson = new Gson();
//        LeftBean leftBean = gson.fromJson(s, LeftBean.class);
//        if (listener != null) {
//            listener.getleftData(leftBean);
//        }
//    }
}
