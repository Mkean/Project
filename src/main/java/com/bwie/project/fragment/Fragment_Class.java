package com.bwie.project.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bwie.project.R;
import com.bwie.project.adapter.ClassLeftAdapter;
import com.bwie.project.adapter.ClassRightLeft;
import com.bwie.project.presenter.LeftPresenter;
import com.bwie.project.presenter.RightPresenter;
import com.bwie.project.view.LeftView;
import com.bwie.project.view.RightView;

import java.util.List;

import bean.LeftBean;
import bean.Right;

/**
 * 作者：王庆
 * 时间：2017/12/14
 */

public class Fragment_Class extends Fragment implements LeftView, RightView {

    private View view;
    private RecyclerView left_lv;
    private RecyclerView right_lv;
    private RightPresenter rightPresenter;
    private ClassRightLeft classRightLeft;
    private String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_class, container, false);
        initView();
        initData();
        return view;
    }

    private void initData() {
        LeftPresenter leftPresenter = new LeftPresenter(this);
        leftPresenter.getLeftData();
        rightPresenter = new RightPresenter(this);
    }

    private void initView() {
        left_lv = view.findViewById(R.id.left_lv);
        right_lv = view.findViewById(R.id.right_lv);

    }

    @Override
    public void getData(LeftBean leftBean) {
        final List<LeftBean.DataBean> data = leftBean.getData();
        final ClassLeftAdapter adapter = new ClassLeftAdapter(getContext(), data);
        left_lv.setLayoutManager(new LinearLayoutManager(getContext()));
        rightPresenter.getData(1);
        adapter.setOnClickListener(new ClassLeftAdapter.onClickListener() {
            @Override
            public void onClick(View view, int position) {
//     Toast.makeText(getContext(), "position:" + position, Toast.LENGTH_SHORT).show();
                adapter.setTagPosition(position);
                int cid = data.get(position).getCid();
                rightPresenter.getData(cid);
                adapter.notifyDataSetChanged();
            }
        });
        left_lv.setAdapter(adapter);
    }

    @Override
    public void getData(List<Right.DataBean> data) {
        right_lv.setLayoutManager(new LinearLayoutManager(getContext()));
        classRightLeft = new ClassRightLeft(getContext(), data, getUid());
        right_lv.setAdapter(classRightLeft);

    }

    @Override
    public void isEmpty() {
        Toast.makeText(getContext(), "data值为空！", Toast.LENGTH_SHORT).show();
    }
}
