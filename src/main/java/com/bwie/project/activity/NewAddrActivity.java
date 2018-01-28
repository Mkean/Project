package com.bwie.project.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.project.R;
import com.bwie.project.presenter.InsertAddrPresenter;
import com.bwie.project.view.InsertAddrView;

import bean.InsertAddrBean;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class NewAddrActivity extends AppCompatActivity implements InsertAddrView {

    @InjectView(R.id.newAddr_back)
    ImageView mNewAddBack;
    @InjectView(R.id.newAddr_save)
    TextView mNewAddSave;
    @InjectView(R.id.newAddr_user)
    EditText mNewAddUser;
    @InjectView(R.id.newAddr_tel)
    EditText mNewAddTel;
    @InjectView(R.id.newAddr_addr)
    EditText mNewAddAddr;
    @InjectView(R.id.newAddr_check)
    CheckBox mNewAddCheck;
    private String uid;
    private InsertAddrPresenter insertAddrPresenter;
    private boolean isSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_addr);
        ButterKnife.inject(this);
        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");
        initData();
    }

    private void initData() {
        insertAddrPresenter = new InsertAddrPresenter(this);

    }

    @OnClick({R.id.newAddr_back, R.id.newAddr_save, R.id.newAddr_check})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.newAddr_back:
                isDestroy();
                break;
            case R.id.newAddr_save:
                isSave = true;
                String name = mNewAddUser.getText().toString();
                String tel = mNewAddTel.getText().toString();
                String addr = mNewAddAddr.getText().toString();
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(tel) || TextUtils.isEmpty(addr)) {
                    Toast.makeText(this, "请检查是否全部填写", Toast.LENGTH_SHORT).show();
                    return;
                }
                insertAddrPresenter.insertAddr(uid, tel, name, addr);
                break;
        }
    }


    @Override
    public void onInsertSuccess(InsertAddrBean insertAddrBean) {
        Toast.makeText(this, "成功", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.putExtra("isChecked", mNewAddCheck.isChecked());
        setResult(10, intent);
        finish();
    }

    @Override
    public void onInsertFailed(Throwable e) {
        Toast.makeText(this, "失败", Toast.LENGTH_SHORT).show();
    }

    private void isDestroy() {
        if (!isSave) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("修改了信息还未保存，确认现在返回吗？");
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("取消", null);
            builder.show();
        }
    }
}
