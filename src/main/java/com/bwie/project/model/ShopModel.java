package com.bwie.project.model;

import com.google.gson.Gson;

import java.io.IOException;

import bean.ShopBean;
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

public class ShopModel implements IModel {

    private getShopListData data;

    public void setData(getShopListData data) {
        this.data = data;
    }

    public interface getShopListData {
        void getListData(ShopBean shopBean);
    }

    public void getShopData1(int pscid) {
        OkHttpUtils.doGet(MyApi.SHOP + "?pscid=" + pscid, new OnUiCallback() {
            @Override
            public void onFailed(Call call, IOException e) {

            }

            @Override
            public void onSuccess(String result) throws IOException {
                getJs(result);
            }
        });
    }

    public Observable<ShopBean> getShopData(int pscid) {
        constant.MyApi myApi = RetrofitManager.getInstance(MyApi.LOG).getmRetrofit().create(constant.MyApi.class);
        return myApi.getShopData(pscid);
    }

    private void getJs(String s) {
        Gson gson = new Gson();
        ShopBean shopBean = gson.fromJson(s, ShopBean.class);
        if (data != null) {
            data.getListData(shopBean);
        }
    }
}
