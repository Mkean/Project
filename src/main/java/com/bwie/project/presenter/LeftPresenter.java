package com.bwie.project.presenter;

import com.bwie.project.model.LeftModel;
import com.bwie.project.view.LeftView;

import bean.LeftBean;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：王庆
 * 时间：2017/12/14
 */

public class LeftPresenter extends IPresenter<LeftView> {

    private LeftModel leftModel;

    public LeftPresenter(LeftView view) {
        super(view);
    }

    @Override
    protected void init() {
        leftModel = new LeftModel();
    }

    public void getLeftData() {
//        leftModel.getLeftName();
//        leftModel.setOnLeftListener(this);
        Observable<LeftBean> leftName = leftModel.getLeftName();
        leftName.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LeftBean>() {
                    @Override
                    public void accept(LeftBean leftBean) throws Exception {
                        view.getData(leftBean);
                    }
                });
    }

//    @Override
//    public void getleftData(LeftBean leftBean) {
//        view.getData(leftBean);
//    }


}
