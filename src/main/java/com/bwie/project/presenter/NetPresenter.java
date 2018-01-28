package com.bwie.project.presenter;

import com.bwie.project.model.NetModel;
import com.bwie.project.view.NetData;

import bean.Banner_Bean;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：王庆
 * 时间：2017/12/14
 */

public class NetPresenter extends IPresenter<NetData> implements NetModel.BannerImage {


    private NetModel netModel;

    public NetPresenter(NetData view) {
        super(view);
    }

    @Override
    protected void init() {
        netModel = new NetModel();
    }

    public void getNet() {
//        netModel.getNetData();
//        netModel.setBannerImage(this);
        Observable<Banner_Bean> banner = netModel.getBanner();
        banner.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Banner_Bean>() {
                    @Override
                    public void accept(Banner_Bean banner_bean) throws Exception {
                        view.getImage(banner_bean);
                    }
                });
    }


    @Override
    public void getBannerImage(Banner_Bean banner_bean) {
        view.getImage(banner_bean);
    }
}
