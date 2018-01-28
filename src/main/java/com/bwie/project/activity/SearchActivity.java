package com.bwie.project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.project.R;
import com.bwie.project.sqliteutils.SQLiteUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText et_search;
    private ImageView iv_back;
    private TextView tv_cancel;
    private SQLiteUtils dao;
    private List<String> list;
    private TagFlowLayout tagflow;
    private TextView tv;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            list = dao.selectAll();
            switch (what) {
                case 1:
                    tagflow.setAdapter(new TagAdapter<String>(list) {
                        @Override
                        public View getView(FlowLayout parent, int position, String s) {
                            tv = (TextView) LayoutInflater.from(SearchActivity.this).inflate(R.layout.tv, tagflow, false);
                            tv.setText(s);
                            return tv;
                        }
                    });
                    break;
            }
            super.handleMessage(msg);
        }
    };
    private TextView del_all;
    private String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");
        initView();
        initData();
    }

    private void initData() {
        dao = new SQLiteUtils(this);
        handler.post(new Runnable() {
            @Override
            public void run() {
                list = dao.selectAll();
                handler.sendEmptyMessage(1);
            }
        });
        iv_back.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        del_all.setOnClickListener(this);

        et_search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    String s = et_search.getText().toString();

                    if (!dao.isHas(s)) {
                        dao.insert(s);
                        handler.sendEmptyMessage(1);
                    }
                    addIntent(s);
//                    Toast.makeText(SearchActivity.this, "搜索", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
        tagflow.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                addIntent(list.get(position));
                return false;
            }
        });
    }

    private void addIntent(String keywords) {
        Intent intent = new Intent(SearchActivity.this, SearchListActivity.class);
        intent.putExtra("keywords", keywords);
        intent.putExtra("uid",uid);
        startActivity(intent);
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        et_search = (EditText) findViewById(R.id.et_search);
        tv_cancel = (TextView) findViewById(R.id.tv_cancel);
        tagflow = (TagFlowLayout) findViewById(R.id.tagflow);
        del_all = (TextView) findViewById(R.id.del_all);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.del_all:
                dao.del();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        list = dao.selectAll();
                        handler.sendEmptyMessage(1);
                    }
                });
                break;
        }
    }

}
