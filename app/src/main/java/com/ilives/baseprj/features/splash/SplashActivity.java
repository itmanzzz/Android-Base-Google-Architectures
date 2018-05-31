package com.ilives.baseprj.features.splash;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.ilives.baseprj.R;
import com.ilives.baseprj.common.base.BaseActivity;
import com.ilives.baseprj.common.base.BaseActivityContract;
import com.ilives.baseprj.databinding.ActivitySplashBinding;
import com.ilives.baseprj.features.login.LoginActivity;

/**
 * -------------^_^-------------
 * ❖ com.ilives.baseprj.features.splash
 * ❖ Created by IntelliJ IDEA
 * ❖ Author: Johnny
 * ❖ Date: 5/31/18
 * ❖ Time: 01:05
 * -------------^_^-------------
 **/
public class SplashActivity extends BaseActivity {

    ActivitySplashBinding rootView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.rootView = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        this.initView();
    }

    private void initView() {
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
        }, 1500);
    }

    @Override
    public BaseActivityContract.Presenter getPresenter() {
        return null;
    }
}
