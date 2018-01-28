package com.bwie.project.presenter;

import com.bwie.project.model.AddrUpdateModel;
import com.bwie.project.view.AddrUpdateView;

import bean.UpdateBean;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：王庆
 * 时间：2018/1/6
 */

public class AddrUpdatePresenter extends IPresenter<AddrUpdateView> implements AddrUpdateModel.onListener {

    private AddrUpdateModel addrUpdateModel;

    public AddrUpdatePresenter(AddrUpdateView view) {
        super(view);
    }

    @Override
    protected void init() {
        addrUpdateModel = new AddrUpdateModel();
    }

    public void updateAddr(String uid, String addrid, String mobile, String name, String addr) {
//        Log.e("TAG", uid + "==" + addrid + "===" + mobile + "===" + name + "===" + addr);
        Observable<UpdateBean> updateBeanObservable = addrUpdateModel.updateAddr(uid, addrid, mobile, name, addr);
        updateBeanObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UpdateBean>() {
                    @Override
                    public void accept(UpdateBean updateBean) throws Exception {
                        view.onUpdateSuccess(updateBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.onUpdateFailed(throwable);
                    }
                });
    }

    public void getData(String uid, String addrid, long mobile, String name, String addr) {
        addrUpdateModel.getData(uid, addrid, mobile, name, addr);
    }

    @Override
    public void listener(UpdateBean updateBean) {
        String code = updateBean.getCode();
        if (code.equals("0")) {
            view.onUpdateSuccess(updateBean);
        } else {
            view.onUpdateFailed(null);
        }
    }
}
