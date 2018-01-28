package com.bwie.project.presenter;

import com.bwie.project.model.AddrModel;
import com.bwie.project.view.AddrView;

import bean.AddrBean;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：王庆
 * 时间：2018/1/5
 */

public class AddrPresenter extends IPresenter<AddrView> {

    private AddrModel addrModel;

    public AddrPresenter(AddrView view) {
        super(view);
    }

    @Override
    protected void init() {
        addrModel = new AddrModel();
    }

    public void getAddrList(String uid) {
        Observable<AddrBean> addrList = addrModel.getAddrList(uid);
        addrList.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AddrBean>() {
                    @Override
                    public void accept(AddrBean addrBean) throws Exception {
                        view.onSuccess(addrBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.onFailed(throwable);
                    }
                });
    }
}
