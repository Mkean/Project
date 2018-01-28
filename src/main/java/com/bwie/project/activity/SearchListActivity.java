package com.bwie.project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.bwie.project.R;
import com.bwie.project.adapter.MySearchAdapter;
import com.bwie.project.presenter.QueryPresenter;
import com.bwie.project.view.QueryView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import bean.QueryBean;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class SearchListActivity extends AppCompatActivity implements QueryView {
    @InjectView(R.id.search_lv)
    XRecyclerView mSearchLv;
    private int page = 1;
    private String keywords;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);
        ButterKnife.inject(this);
        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");
        keywords = intent.getStringExtra("keywords");
        initData();
    }

    private void initData() {
        QueryPresenter presenter = new QueryPresenter(this);
        presenter.getQueryGoods(keywords, page);
    }

    @Override
    public void isEmpty() {
        Toast.makeText(this, "没有找到与你要找的有关的信息", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailed() {
        Toast.makeText(this, "请求数据失败，请重新请求", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(final QueryBean queryBean) {
        final List<QueryBean.DataBean> data = queryBean.getData();
        MySearchAdapter adapter = new MySearchAdapter(data, this);
        mSearchLv.setLayoutManager(new LinearLayoutManager(this));
        adapter.setListener(new MySearchAdapter.onItemListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                String pid = data.get(position).getPid();
                Intent intent = new Intent(SearchListActivity.this, GoodsActivity.class);
                intent.putExtra("uid", uid);
                intent.putExtra("pid", pid);
                startActivity(intent);
            }
        });
        mSearchLv.setAdapter(adapter);
    }
}
