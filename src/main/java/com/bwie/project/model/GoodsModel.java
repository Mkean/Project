package com.bwie.project.model;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import bean.GoodsBean;
import io.reactivex.Observable;
import okhttp3.Call;
import tools.MyApi;
import utils.OkHttpUtils;
import utils.OnUiCallback;
import utils.RetrofitManager;

/**
 * 作者：王庆
 * 时间：2017/12/15
 */

public class GoodsModel implements IModel {

    private getGoodsData data;

    public void setData(getGoodsData data) {
        this.data = data;
    }

    public interface getGoodsData {
        void getListData(GoodsBean goodsBean);
    }

    public Observable<GoodsBean> getGoodsData(String pid, String source) {
        constant.MyApi myApi = RetrofitManager.getInstance(MyApi.LOG).getmRetrofit().create(constant.MyApi.class);
        return myApi.getGoodsData(pid, source);
    }

    public void getGoodsData(String pid) {
        Map map = new HashMap();
        map.put("source", "android");
        map.put("pid", pid);
        OkHttpUtils.doPost(MyApi.GOODS, map, new OnUiCallback() {
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
        GoodsBean goodsBean = gson.fromJson(s, GoodsBean.class);
        if (data != null) {
            data.getListData(goodsBean);
        }
    }
}
