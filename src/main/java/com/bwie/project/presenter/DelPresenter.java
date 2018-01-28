package com.bwie.project.presenter;

import com.bwie.project.model.DelModel;
import com.bwie.project.view.DelView;

import bean.DelBean;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：王庆
 * 时间：2018/1/10
 */

public class DelPresenter extends IPresenter<DelView> {

    private DelModel delModel;

    public DelPresenter(DelView view) {
        super(view);
    }

    @Override
    protected void init() {
        delModel = new DelModel();
    }

    public void del(String uid, String pid) {
        Observable<DelBean> delBeanObservable = delModel.delCart(uid, pid);
        delBeanObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DelBean>() {
                    @Override
                    public void accept(DelBean delBean) throws Exception {
                        view.onDelSuccess(delBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.onDelFailed(throwable);
                    }
                });
    }
}
