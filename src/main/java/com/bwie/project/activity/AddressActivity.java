package com.bwie.project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.project.R;
import com.bwie.project.adapter.MyAddrAdapter;
import com.bwie.project.presenter.AddrPresenter;
import com.bwie.project.presenter.UpdateAddrPresenter;
import com.bwie.project.view.AddrView;
import com.bwie.project.view.UpdateAddrView;

import java.util.List;

import bean.AddrBean;
import bean.UpdateAddrBean;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class AddressActivity extends AppCompatActivity implements AddrView, UpdateAddrView {

    @InjectView(R.id.addr_back)
    ImageView mAddrBack;
    @InjectView(R.id.addr_lv)
    RecyclerView mAddrLv;
    @InjectView(R.id.textView)
    TextView mTextView;
    private String uid;
    private List<AddrBean.DataBean> data;
    private AddrPresenter presenter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            data = (List<AddrBean.DataBean>) msg.obj;
            final MyAddrAdapter myAddrAdapter = new MyAddrAdapter(data, AddressActivity.this);
            mAddrLv.setLayoutManager(new LinearLayoutManager(AddressActivity.this));
            myAddrAdapter.setLisenter(new MyAddrAdapter.onLisenter() {
                @Override
                public void onLisener() {
                    presenter.getAddrList(uid);
                    myAddrAdapter.notifyDataSetChanged();
                }
            });
            mAddrLv.setAdapter(myAddrAdapter);
        }
    };
    private UpdateAddrPresenter updateAddrPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        ButterKnife.inject(this);
        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");

    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();
    }

    private void initData() {
        updateAddrPresenter = new UpdateAddrPresenter(this);
        presenter = new AddrPresenter(this);
        presenter.getAddrList(uid);
    }

    @OnClick({R.id.addr_back, R.id.textView})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.addr_back:
                finish();
                break;
            case R.id.textView:
                Intent intent = new Intent(this, NewAddrActivity.class);
                intent.putExtra("uid", uid);
                startActivityForResult(intent, 100);
                break;
        }
    }

    @Override
    public void onSuccess(AddrBean addrBean) {
        List<AddrBean.DataBean> data = addrBean.getData();
        Message message = Message.obtain();
        message.obj = data;
        message.what = 1;
        handler.sendMessage(message);
    }

    @Override
    public void onSuccess(UpdateAddrBean updateAddrBean) {
        Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailed(Throwable e) {
        Toast.makeText(this, "请求数据失败", Toast.LENGTH_SHORT).show();
        Log.e("TAG", "====" + e.getMessage().toString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 10) {
            boolean isChecked = data.getBooleanExtra("isChecked", false);
            if (isChecked) {
//                updateAddrPresenter.updateAddr(uid, this.data.get(this.data.size() - 1).getAddrid(), "1");
            }
        }
    }
}
