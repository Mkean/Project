package com.bwie.project.fragment;

import android.support.v4.app.Fragment;

/**
 * 作者：王庆
 * 时间：2018/1/4
 */

public abstract class LazyFragment extends Fragment {
    protected boolean isVisible;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
        }
    }

    private void onVisible() {
        LazyLoad();
    }

    protected abstract void LazyLoad();
}
