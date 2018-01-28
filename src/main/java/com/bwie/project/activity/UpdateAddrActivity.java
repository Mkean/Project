package com.bwie.project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.project.R;
import com.bwie.project.presenter.AddrUpdatePresenter;
import com.bwie.project.presenter.UpdateAddrPresenter;
import com.bwie.project.view.AddrUpdateView;
import com.bwie.project.view.UpdateAddrView;

import bean.AddrBean;
import bean.UpdateAddrBean;
import bean.UpdateBean;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class UpdateAddrActivity extends AppCompatActivity implements UpdateAddrView, AddrUpdateView {

    @InjectView(R.id.update_back)
    ImageView mUpdateBack;
    @InjectView(R.id.addr_user)
    EditText mAddrUser;
    @InjectView(R.id.addr_tel)
    EditText mAddrTel;
    @InjectView(R.id.addr)
    EditText mAddr;
    @InjectView(R.id.addr_check)
    CheckBox mAddrCheck;
    @InjectView(R.id.addr_Del)
    TextView mAddrDel;
    private AddrBean.DataBean dataBean;
    private UpdateAddrPresenter updateAddrPresenter;
    private AddrUpdatePresenter addrUpdatePresenter;
    private String user;
    private String tel;
    private String addr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_addr);
        ButterKnife.inject(this);
        Intent intent = getIntent();
        dataBean = (AddrBean.DataBean) intent.getSerializableExtra("dataBean");

    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();
    }

    private void initData() {
        mAddrUser.setText(dataBean.getName());
        mAddrTel.setText(dataBean.getMobile());
        mAddr.setText(dataBean.getAddr());
        user = mAddrUser.getText().toString();
        mAddrUser.setSelection(user.length());
        //修改默认地址
        updateAddrPresenter = new UpdateAddrPresenter(this);
        //修改地址
        addrUpdatePresenter = new AddrUpdatePresenter(this);


    }

    @OnClick({R.id.update_back, R.id.addr_check, R.id.addr_Del, R.id.addr_save})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.update_back:
                finish();
                break;
            case R.id.addr_save:
                user = mAddrUser.getText().toString();
                tel = mAddrTel.getText().toString();
                addr = mAddr.getText().toString();
//                Log.e("TAG", user + "==" + tel + "===" + addr + "===" + dataBean.getUid() + "===" + dataBean.getAddrid());
                addrUpdatePresenter.updateAddr(dataBean.getUid(), dataBean.getAddrid(), tel, user, addr);
//                addrUpdatePresenter.getData(dataBean.getUid(), dataBean.getAddrid(), tel, user, addr);
                break;
            case R.id.addr_check:
                updateAddrPresenter.updateAddr(dataBean.getUid(), dataBean.getAddrid(), "1");
                break;
            case R.id.addr_Del:
                Toast.makeText(this, "暂不支持此功能", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onSuccess(UpdateAddrBean updateAddrBean) {
        Toast.makeText(this, "设置默认地址成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailed(Throwable e) {
        Toast.makeText(this, "失败", Toast.LENGTH_SHORT).show();
        Log.e("TAG", e.getMessage().toString());
    }

    @Override
    public void onUpdateSuccess(UpdateBean updateBean) {
        Toast.makeText(this, "修改地址成功", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onUpdateFailed(Throwable e) {
        Log.e("TAG1", e.getMessage().toString());
        Toast.makeText(this, "失败", Toast.LENGTH_SHORT).show();
    }
}
