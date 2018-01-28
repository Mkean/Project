package com.bwie.project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bwie.project.R;
import com.bwie.project.adapter.GoodsAdapter;
import com.bwie.project.presenter.GoodsPresenter;
import com.bwie.project.view.GoodsView;
import com.google.gson.Gson;

import java.io.IOException;

import bean.CollectBean;
import bean.GoodsBean;
import okhttp3.Call;
import tools.MyApi;
import utils.OkHttpUtils;
import utils.OnUiCallback;

public class GoodsActivity extends AppCompatActivity implements GoodsView, View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private GoodsPresenter goodsPresenter;
    //    private WebView web;
    private RecyclerView glv;
    private Button collect_cart;
    private Button nowBuy;
    private RadioGroup rg;
    private String uid;
    private String pid;
    private double price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);
        Intent intent = getIntent();
        pid = intent.getStringExtra("pid");
        uid = intent.getStringExtra("uid");
        goodsPresenter = new GoodsPresenter(this);
        goodsPresenter.getData(pid);
        initView();
        initData();
    }

    private void initData() {
        collect_cart.setOnClickListener(this);
        nowBuy.setOnClickListener(this);
        rg.setOnCheckedChangeListener(this);
    }

    private void initView() {
        glv = (RecyclerView) findViewById(R.id.glv);
        collect_cart = (Button) findViewById(R.id.collect_cart);
        nowBuy = (Button) findViewById(R.id.nowBuy);
        rg = (RadioGroup) findViewById(R.id.goods_rg);
    }

    @Override
    public void getGoods(GoodsBean goodsBean) {
        GoodsBean.DataBean data = goodsBean.getData();
        price = data.getPrice();
        GoodsAdapter adapter = new GoodsAdapter(data, this);
        glv.setLayoutManager(new LinearLayoutManager(this));
        glv.setAdapter(adapter);
//        web.loadUrl(data.getDetailUrl());
    }

    @Override
    public void onFailed() {
        Toast.makeText(this, "请求数据失败！！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.collect_cart:
                getCollect(uid, pid);
                break;
            case R.id.nowBuy:

                OkHttpUtils.doGet("http://120.27.23.105/product/createOrder?uid=" + uid + "&price=" + price, new OnUiCallback() {
                    @Override
                    public void onFailed(Call call, IOException e) {

                    }

                    @Override
                    public void onSuccess(String result) throws IOException {
                        Intent intent = new Intent(GoodsActivity.this, OrdersActivity.class);
                        intent.putExtra("uid", uid);
                        intent.putExtra("id", 0);
                        startActivity(intent);
                        finish();
                    }
                });

                break;

        }
    }

    private void getCollect(String uid, String pid) {
        OkHttpUtils.doGet(MyApi.COLLECT + "?uid=" + uid + "&pid=" + pid, new OnUiCallback() {
            @Override
            public void onFailed(Call call, IOException e) {

            }

            @Override
            public void onSuccess(String result) throws IOException {
                getJs(result);
            }
        });
    }

    private void getJs(String s) {
        Gson gson = new Gson();
        CollectBean collectBean = gson.fromJson(s, CollectBean.class);
        String code = collectBean.getCode();
        if (code.equals("0")) {
            Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "添加商品失败", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.dian:
                Toast.makeText(this, "店铺", Toast.LENGTH_SHORT).show();
                break;
            case R.id.keFu:
                Toast.makeText(this, "客服", Toast.LENGTH_SHORT).show();
                break;
            case R.id.collect:
                Toast.makeText(this, "收藏", Toast.LENGTH_SHORT).show();
                break;

        }
    }
}
