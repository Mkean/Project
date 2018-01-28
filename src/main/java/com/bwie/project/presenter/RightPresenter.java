package com.bwie.project.presenter;

import com.bwie.project.model.RightModel;
import com.bwie.project.view.RightView;

import java.util.List;

import bean.Right;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：王庆
 * 时间：2017/12/14
 */

public class RightPresenter extends IPresenter<RightView> implements RightModel.onRighListener {

    private RightModel rightModel;

    public RightPresenter(RightView view) {
        super(view);
    }

    public void getData(int cid) {
//        rightModel.getRightData(cid);
//        rightModel.setOnRighListener(this);
        Observable<Right> rightData = rightModel.getRightData(cid);
        rightData.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Right>() {
                    @Override
                    public void accept(Right right) throws Exception {
                        List<Right.DataBean> data = right.getData();
                        if (data.size() > 0) {
                            view.getData(data);
                        } else {
                            view.isEmpty();
                        }
                    }
                });
    }

    @Override
    public void getrightData(Right right) {
        List<Right.DataBean> data = right.getData();
        if (data.size() > 0) {
            view.getData(data);
        } else {
            view.isEmpty();
        }
    }

    @Override
    protected void init() {
        rightModel = new RightModel();
    }
}
