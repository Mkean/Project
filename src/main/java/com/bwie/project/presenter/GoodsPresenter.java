package com.bwie.project.presenter;

import com.bwie.project.model.GoodsModel;
import com.bwie.project.view.GoodsView;

import bean.GoodsBean;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：王庆
 * 时间：2017/12/15
 */

public class GoodsPresenter extends IPresenter<GoodsView> implements GoodsModel.getGoodsData {

    private GoodsModel goodsModel;

    public GoodsPresenter(GoodsView view) {
        super(view);
    }

    @Override
    protected void init() {
        goodsModel = new GoodsModel();
    }

    public void getData(String pid) {
//        goodsModel.getGoodsData(pid);
//        goodsModel.setData(this);
        Observable<GoodsBean> goodsData = goodsModel.getGoodsData(pid, "android");
        goodsData.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GoodsBean>() {
                    @Override
                    public void accept(GoodsBean goodsBean) throws Exception {
                        view.getGoods(goodsBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.onFailed();
                    }
                });
    }

    @Override
    public void getListData(GoodsBean goodsBean) {
        String code = goodsBean.getCode();
        if (code.equals("0")) {
            view.getGoods(goodsBean);
        } else {
            view.onFailed();
        }
    }

}
