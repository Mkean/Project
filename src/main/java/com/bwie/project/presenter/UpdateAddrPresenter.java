package com.bwie.project.presenter;

import com.bwie.project.model.UpdateAddrModel;
import com.bwie.project.view.UpdateAddrView;

import bean.UpdateAddrBean;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：王庆
 * 时间：2018/1/5
 */

public class UpdateAddrPresenter extends IPresenter<UpdateAddrView> {

    private UpdateAddrModel model;

    public UpdateAddrPresenter(UpdateAddrView view) {
        super(view);
    }

    @Override
    protected void init() {
        model = new UpdateAddrModel();
    }

    public void updateAddr(String uid, String addrid, String status) {
        Observable<UpdateAddrBean> updateAddrBeanObservable = model.updateAddr(uid, addrid, status);
        updateAddrBeanObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UpdateAddrBean>() {
                    @Override
                    public void accept(UpdateAddrBean updateAddrBean) throws Exception {
                        view.onSuccess(updateAddrBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.onFailed(throwable);
                    }
                });
    }
}
