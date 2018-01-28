package com.bwie.project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.bwie.project.R;
import com.bwie.project.fragment.Fragment_All;
import com.bwie.project.fragment.Fragment_Fa;
import com.bwie.project.fragment.Fragment_Shou;
import com.bwie.project.fragment.Fragment_Wei;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class OrdersActivity extends AppCompatActivity {

    @InjectView(R.id.orders_tab)
    TabLayout mOrdersTab;
    @InjectView(R.id.orders_pager)
    ViewPager mOrdersPager;
    private String uid;
    private List<Fragment> list;
    private String[] status = new String[]{"全部", "未付款", "待发货", "待收货"};
    private static int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        ButterKnife.inject(this);
        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");
        id = intent.getIntExtra("id", 10);
        initData();
        mOrdersPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return status[position];
            }
        });
        if (id == 0) {
            mOrdersPager.setCurrentItem(1);
        } else if (id == 1) {
            mOrdersPager.setCurrentItem(2);
        } else if (id == 2) {
            mOrdersPager.setCurrentItem(3);
        } else {
            mOrdersPager.setCurrentItem(0);
        }
        mOrdersTab.setTabMode(TabLayout.MODE_FIXED);
        mOrdersTab.setupWithViewPager(mOrdersPager);
    }

    private void initData() {
        list = new ArrayList<>();
        Fragment_All fragment_all = new Fragment_All();
        Fragment_Wei fragment_wei = new Fragment_Wei();
        Fragment_Fa fragment_fa = new Fragment_Fa();
        Fragment_Shou fragment_shou = new Fragment_Shou();
        list.add(fragment_all);
        list.add(fragment_wei);
        list.add(fragment_fa);
        list.add(fragment_shou);
    }
}
