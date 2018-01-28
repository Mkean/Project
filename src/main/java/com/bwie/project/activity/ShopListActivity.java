package com.bwie.project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.bwie.project.R;
import com.bwie.project.adapter.MyShopAdapter;
import com.bwie.project.presenter.ShopPresenter;
import com.bwie.project.view.ShopView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import bean.ShopBean;

public class ShopListActivity extends AppCompatActivity implements ShopView {

    private XRecyclerView shop_lv;
    private int pscid;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);
        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");
        pscid = intent.getIntExtra("pscid", 0);

        initView();
        ShopPresenter shopPresenter = new ShopPresenter(this);
        shopPresenter.getData(pscid);
    }

    private void initView() {
        shop_lv = (XRecyclerView) findViewById(R.id.shop_lv);
    }

    @Override
    public void isShopEmpty() {
        Toast.makeText(this, "Data内容为空！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getDatas(final List<ShopBean.DataBean> data) {
        shop_lv.setLayoutManager(new LinearLayoutManager(this));
        MyShopAdapter adapter = new MyShopAdapter(data, this);
        adapter.setOnIitemClickListener(new MyShopAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                Toast.makeText(ShopListActivity.this, "position" + position, Toast.LENGTH_SHORT).show();
                String pid = data.get(position).getPid();
                Intent intent = new Intent(ShopListActivity.this, GoodsActivity.class);
                intent.putExtra("uid", uid);
                intent.putExtra("pid", pid);
                startActivity(intent);
            }
        });
        shop_lv.setAdapter(adapter);
    }
}
