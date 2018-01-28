package com.bwie.project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bwie.project.R;
import com.bwie.project.presenter.NickPresenter;
import com.bwie.project.view.NickView;

import bean.NickBean;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class UpdateNickActivity extends AppCompatActivity implements NickView {

    @InjectView(R.id.back)
    ImageView mBack;
    @InjectView(R.id.update_nickName)
    EditText mUpdateNickName;
    @InjectView(R.id.update_save)
    Button mUpdateSave;
    private String nickName;
    private String uid;
    private NickPresenter nickPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_nick);
        ButterKnife.inject(this);
        Intent intent = getIntent();
        nickName = intent.getStringExtra("nickName");
        uid = intent.getStringExtra("uid");
//        Log.e("TAG", uid);
        mUpdateNickName.setText(nickName);
        initData();
        //光标位置
        mUpdateNickName.setSelection(nickName.length());
    }

    private void initData() {
        nickPresenter = new NickPresenter(this);
    }

    @OnClick({R.id.back, R.id.update_save})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.back:
                finish();
                break;
            case R.id.update_save:
                String name = mUpdateNickName.getText().toString();
                nickPresenter.updateNick(uid, name);
                Intent intent = new Intent();
                intent.putExtra("name", name);
                setResult(10, intent);
                finish();
                break;
        }
    }

    @Override
    public void onSucces(NickBean nickBean) {
        Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailed(Throwable e) {
        Toast.makeText(this, "修改失败", Toast.LENGTH_SHORT).show();
        Log.e("TAG", e.getMessage().toString());
    }
}
