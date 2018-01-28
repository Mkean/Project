package com.bwie.project.presenter;

import com.bwie.project.model.ShopModel;
import com.bwie.project.view.ShopView;

import java.util.List;

import bean.ShopBean;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：王庆
 * 时间：2017/12/14
 */

public class ShopPresenter extends IPresenter<ShopView> implements ShopModel.getShopListData {


    private ShopModel shopModel;

    public ShopPresenter(ShopView view) {
        super(view);
    }

    public void getData(int pscid) {
//        shopModel.getShopData(pscid);
////        Log.e("TAG", pscid + "");
//        shopModel.setData(this);
        Observable<ShopBean> shopData = shopModel.getShopData(pscid);
        shopData.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ShopBean>() {
                    @Override
                    public void accept(ShopBean shopBean) throws Exception {
                        List<ShopBean.DataBean> data = shopBean.getData();
                        if (data.size() <= 0) {
                            view.isShopEmpty();
                        } else {
                            view.getDatas(data);
                        }
                    }
                });
    }

    @Override
    public void getListData(ShopBean shopBean) {
        List<ShopBean.DataBean> data = shopBean.getData();
        if (data.size() <= 0) {
            view.isShopEmpty();
        } else {
            view.getDatas(data);
        }
    }

    @Override
    protected void init() {
        shopModel = new ShopModel();
    }
}
