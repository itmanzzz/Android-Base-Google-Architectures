package com.ilives.baseprj.features.login;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ilives.baseprj.R;
import com.ilives.baseprj.common.base.BaseActivity;
import com.ilives.baseprj.common.models.Error;
import com.ilives.baseprj.common.models.ToastType;
import com.ilives.baseprj.databinding.ActivityLoginBinding;

/**
 * -------------^_^-------------
 * ❖ com.ilives.baseprj.features.login
 * ❖ Created by IntelliJ IDEA
 * ❖ Author: Johnny
 * ❖ Date: 5/31/18
 * ❖ Time: 00:41
 * -------------^_^-------------
 **/
public class LoginActivity extends BaseActivity<LoginContract.Presenter> implements LoginContract.View, View.OnClickListener {

    ActivityLoginBinding rootView;
    private LoginActivityPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.rootView = DataBindingUtil.setContentView(this, R.layout.activity_login);
        this.initViews();
    }

    private void initViews() {
        getPresenter().bindView(this);
        this.rootView.btnLogin.setOnClickListener(this);
    }

    @Override
    public LoginContract.Presenter getPresenter() {
        return this.mPresenter = this.mPresenter != null ? this.mPresenter : new LoginActivityPresenter();
    }

    @Override
    public void authenticated() {
        showToast(ToastType.SUCCESS, "authenticated");
    }

    @Override
    public void unAuthenticated(Error error) {
        showToast(ToastType.ERROR, "unAuthenticated");
    }

    private void doAuthenticate() {
        if (!preCheckConnection()) return;
        String email = "aavu.lt@neo-lab.vn";
        String password = "123123123";
        int type = 2;

        getPresenter().doAuthenticate(email, password, type);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnLogin) {
            this.doAuthenticate();
        }
    }
}
