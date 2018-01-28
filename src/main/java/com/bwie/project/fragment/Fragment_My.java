package com.bwie.project.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.project.R;
import com.bwie.project.activity.AddressActivity;
import com.bwie.project.activity.InfoActivity;
import com.bwie.project.activity.OrdersActivity;
import com.google.gson.Gson;

import java.io.IOException;

import bean.UserInfoBean;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;
import tools.MyApi;
import utils.OkHttpUtils;
import utils.OnUiCallback;


/**
 * 作者：王庆
 * 时间：2017/12/14
 */

public class Fragment_My extends Fragment implements View.OnClickListener {

    @InjectView(R.id.all_orders)
    LinearLayout mAllOrders;
    @InjectView(R.id.weifukuan)
    LinearLayout weiFuKuan;
    @InjectView(R.id.daifahuo)
    LinearLayout daiFaHuo;
    @InjectView(R.id.daishouhuo)
    LinearLayout daiShouHuo;
    private View view;
    private TextView my_username;
    private String uid;
    private TextView address;
    private ImageView info;
    private UserInfoBean.DataBean data;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my, container, false);
        initView();
        ButterKnife.inject(this, view);
        return view;
    }

    private void initData(String uid) {
        OkHttpUtils.doGet(MyApi.USERINFO + "?uid=" + uid, new OnUiCallback() {
            @Override
            public void onFailed(Call call, IOException e) {

            }

            @Override
            public void onSuccess(String result) throws IOException {
                getJs(result);
            }
        });
        address.setOnClickListener(this);
        info.setOnClickListener(this);
    }


    @Override
    public void onStart() {
        super.onStart();
        initData(uid);
    }

    private void getJs(String s) {
        Gson gson = new Gson();
        UserInfoBean userInfoBean = gson.fromJson(s, UserInfoBean.class);
        String code = userInfoBean.getCode();
        if (code.equals("0")) {
            onSuccess(userInfoBean);
        } else {
            onFailed();
        }
    }

    private void initView() {
        my_username = view.findViewById(R.id.my_username);
        address = view.findViewById(R.id.address);
        info = view.findViewById(R.id.info);

    }

    @Override
    @OnClick({R.id.all_orders, R.id.weifukuan, R.id.daifahuo, R.id.daishouhuo})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.weifukuan:
                Intent intent = new Intent(getActivity(), OrdersActivity.class);
                intent.putExtra("uid", uid);
                intent.putExtra("id", 0);
                getContext().startActivity(intent);
//                onWeiOrders();
                break;

            case R.id.daifahuo:
                Intent intent1 = new Intent(getActivity(), OrdersActivity.class);
                intent1.putExtra("uid", uid);
                intent1.putExtra("id", 1);
                getContext().startActivity(intent1);
                break;

            case R.id.daishouhuo:
                Intent intent2 = new Intent(getActivity(), OrdersActivity.class);
                intent2.putExtra("uid", uid);
                intent2.putExtra("id", 2);
                getContext().startActivity(intent2);
                break;

            case R.id.address:
                Intent intent5 = new Intent(getActivity(), AddressActivity.class);
                intent5.putExtra("uid", uid);
                startActivity(intent5);
                break;
            case R.id.info:
                Intent intent3 = new Intent(getActivity(), InfoActivity.class);
                intent3.putExtra("data", data);
                startActivity(intent3);
                break;
            case R.id.all_orders:
                Intent intent4 = new Intent(getActivity(), OrdersActivity.class);
                intent4.putExtra("uid", uid);
                intent4.putExtra("id", 4);
                getContext().startActivity(intent4);
                break;

        }
    }

    private void onSuccess(UserInfoBean userInfoBean) {

        data = userInfoBean.getData();
//        Log.e("TAG", data.toString());
        String nickname = data.getNickname();
        my_username.setText(nickname);
    }

    public void onFailed() {
        Toast.makeText(getContext(), "请求数据失败", Toast.LENGTH_SHORT).show();
    }

}
