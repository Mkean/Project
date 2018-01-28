package com.bwie.project.model;

import com.google.gson.Gson;

import java.io.IOException;

import bean.Banner_Bean;
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

public class NetModel {

    private BannerImage bannerImage;

    public void setBannerImage(BannerImage bannerImage) {
        this.bannerImage = bannerImage;
    }

    public interface BannerImage {
        void getBannerImage(Banner_Bean banner_bean);
    }

    public Observable<Banner_Bean> getBanner() {
        constant.MyApi myApi = RetrofitManager.getInstance(MyApi.LOG).getmRetrofit().create(constant.MyApi.class);
        return myApi.getBanner();
    }

    public void getNetData() {
        OkHttpUtils.doGet(MyApi.BANNER, new OnUiCallback() {
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
        Banner_Bean banner_bean = gson.fromJson(s, Banner_Bean.class);
        if (bannerImage != null) {
            bannerImage.getBannerImage(banner_bean);
        }
    }
}
