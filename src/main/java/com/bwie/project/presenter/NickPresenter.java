package com.bwie.project.presenter;

import android.util.Log;

import com.bwie.project.model.NickModel;
import com.bwie.project.view.NickView;

import bean.NickBean;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：王庆
 * 时间：2018/1/4
 */

public class NickPresenter extends IPresenter<NickView> {
    private NickModel nickModel;

    public NickPresenter(NickView view) {
        super(view);
    }

    @Override
    protected void init() {
        nickModel = new NickModel();
    }

    public void updateNick(String uid, final String nickName) {
        Observable<NickBean> nickBeanObservable = nickModel.UpdateNick(uid, nickName);
        nickBeanObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<NickBean>() {
                    @Override
                    public void accept(NickBean nickBean) throws Exception {
//                        Log.e("TAG", nickBean.toString());
                        view.onSucces(nickBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.onFailed(throwable);
//                        Log.e("TAG", throwable.getMessage().toString());
                    }
                });
    }
}
