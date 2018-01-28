package com.bwie.project.presenter;

import com.bwie.project.model.InsertAddrModel;
import com.bwie.project.view.InsertAddrView;

import bean.InsertAddrBean;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：王庆
 * 时间：2018/1/7
 */

public class InsertAddrPresenter extends IPresenter<InsertAddrView> {

    private InsertAddrModel insertAddrModel;

    public InsertAddrPresenter(InsertAddrView view) {
        super(view);
    }

    @Override
    protected void init() {
        insertAddrModel = new InsertAddrModel();
    }

    public void insertAddr(String uid, String mobile, String name, String addr) {
        Observable<InsertAddrBean> insertAddr = insertAddrModel.insertAddr(uid, mobile, name, addr);
        insertAddr.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<InsertAddrBean>() {
                    @Override
                    public void accept(InsertAddrBean insertAddrBean) throws Exception {
                        view.onInsertSuccess(insertAddrBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.onInsertFailed(throwable);
                    }
                });
    }
}
