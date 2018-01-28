package com.bwie.project.presenter;

import com.bwie.project.model.UpdateOrdersModel;
import com.bwie.project.view.UpdateOrderView;

import bean.UpdateOrdersBean;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：王庆
 * 时间：2018/1/9
 */

public class UpdateOrderPresenter extends IPresenter<UpdateOrderView> {

    private UpdateOrdersModel updateOrdersModel;

    public UpdateOrderPresenter(UpdateOrderView view) {
        super(view);
    }

    @Override
    protected void init() {
        updateOrdersModel = new UpdateOrdersModel();
    }

    public void updateOrder(String uid, String status, String orderId) {
        Observable<UpdateOrdersBean> updateOrdersBeanObservable = updateOrdersModel.updateOrders(uid, status, orderId);
        updateOrdersBeanObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UpdateOrdersBean>() {
                    @Override
                    public void accept(UpdateOrdersBean updateOrdersBean) throws Exception {
                        view.onSuccess(updateOrdersBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.onFailed(throwable);
                    }
                });
    }
}
