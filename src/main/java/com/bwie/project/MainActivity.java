package com.bwie.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

import com.bwie.project.fragment.Fragment_Cart;
import com.bwie.project.fragment.Fragment_Class;
import com.bwie.project.fragment.Fragment_Home;
import com.bwie.project.fragment.Fragment_My;

public class MainActivity extends AppCompatActivity {

    private Fragment_Home fragment_home;
    private Fragment_Class fragment_class;
    private Fragment_Cart fragment_cart;
    private Fragment_My fragment_my;
    private RadioGroup rg;
    private FragmentManager manager;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");
        rg = (RadioGroup) findViewById(R.id.rg);

        manager = getSupportFragmentManager();
        fragment_home = new Fragment_Home();
        fragment_home.setUid(uid);
        manager.beginTransaction().add(R.id.frame, fragment_home).commit();

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                getFragment();
                FragmentTransaction transaction = manager.beginTransaction();
                switch (checkedId) {
                    case R.id.bt1:
                        transaction.show(fragment_home).commit();
                        break;
                    case R.id.bt2:
                        if (fragment_class == null) {
                            fragment_class = new Fragment_Class();
                            fragment_class.setUid(uid);
                            transaction.add(R.id.frame, fragment_class).commit();
                        } else {
                            transaction.show(fragment_class).commit();
                        }
                        break;
                    case R.id.bt3:
                        if (fragment_cart == null) {
                            fragment_cart = new Fragment_Cart();
                            fragment_cart.setUid(uid);
                            transaction.add(R.id.frame, fragment_cart).commit();
                        } else {
                            transaction.show(fragment_cart).commit();
                        }
                        break;
                    case R.id.bt4:
                        if (fragment_my == null) {
                            fragment_my = new Fragment_My();
                            fragment_my.setUid(uid);
                            transaction.add(R.id.frame, fragment_my).commit();
                        } else {
                            transaction.show(fragment_my).commit();
                        }
                        break;

                }
            }
        });
    }

    private void getFragment() {
        FragmentTransaction transaction = manager.beginTransaction();
        if (fragment_home != null && fragment_home.isAdded()) {
            transaction.hide(fragment_home);
        }
        if (fragment_class != null && fragment_class.isAdded()) {
            transaction.hide(fragment_class);
        }
        if (fragment_cart != null && fragment_cart.isAdded()) {
            transaction.hide(fragment_cart);
        }
        if (fragment_my != null && fragment_my.isAdded()) {
            transaction.hide(fragment_my);
        }
        transaction.commit();
    }
}
