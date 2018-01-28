package com.bwie.project.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bwie.project.R;
import com.bwie.project.activity.SearchActivity;
import com.bwie.project.adapter.MyRecyclerAdapter;
import com.bwie.project.presenter.NetPresenter;
import com.bwie.project.view.NetData;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.List;

import bean.Banner_Bean;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 作者：王庆
 * 时间：2017/12/14
 */

public class Fragment_Home extends Fragment implements NetData {

    @InjectView(R.id.erweima)
    ImageView mErweima;
    @InjectView(R.id.message)
    ImageView mMessage;
    private XRecyclerView lv;
    private EditText search;
    private String uid;
    public static final String CAMERA_PERMISSION = Manifest.permission.CAMERA;
    public static final int CAMERA_REQUSET_CODE = 100;
    public static final int CARMERA_RESULT_CODE = 10;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        lv = view.findViewById(R.id.xrlv);
        search = view.findViewById(R.id.searchview);
        initData();
        ButterKnife.inject(this, view);
        return view;
    }

    private void initData() {
        NetPresenter netPresenter = new NetPresenter(this);
        netPresenter.getNet();
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                intent.putExtra("uid", getUid());
                startActivity(intent);
            }
        });
    }

    @Override
    public void getImage(Banner_Bean data) {
        List<Banner_Bean.MiaoshaBean.ListBeanX> list = data.getMiaosha().getList();
        List<Banner_Bean.DataBean> data1 = data.getData();
        lv.setLayoutManager(new LinearLayoutManager(getContext()));
        MyRecyclerAdapter adapter = new MyRecyclerAdapter(getContext(), list, data1, uid);
        lv.setAdapter(adapter);
        adapter.setOnClickListener(new MyRecyclerAdapter.onClickListener() {
            @Override
            public void onBannerListener(View view, int position) {
                Toast.makeText(getContext(), "position:" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick({R.id.erweima, R.id.message})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.erweima:
//                Scanning();
                initPermission();

                break;
            case R.id.message:
                Toast.makeText(getContext(), "暂不支持此功能", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void initPermission() {
        if (ContextCompat.checkSelfPermission(getContext(), CAMERA_PERMISSION) == PackageManager.PERMISSION_GRANTED) {
            Scanning();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{CAMERA_PERMISSION}, CAMERA_REQUSET_CODE);
        }
    }

    private void Scanning() {
        Intent intent = new Intent(getActivity(), CaptureActivity.class);
        startActivityForResult(intent, 1000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.e("TAG", "onRequestPermissionsResult");
        switch (requestCode) {
            case CAMERA_REQUSET_CODE:
                for (int i = 0; i < permissions.length; i++) {
                    if (permissions[i].equalsIgnoreCase(CAMERA_PERMISSION)) {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                            Log.e("TAG", "ASD");
                            Scanning();
                        }
                    }
                }
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (data != null) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String string = bundle.getString(CodeUtils.RESULT_STRING);
                    Toast.makeText(getContext(), "解析结果" + string, Toast.LENGTH_SHORT).show();
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(getContext(), "解析数据失败", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
