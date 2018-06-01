package com.ilives.baseprj.features.login;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.ilives.baseprj.R;
import com.ilives.baseprj.common.base.BaseActivity;
import com.ilives.baseprj.common.models.ToastType;
import com.ilives.baseprj.common.views.PopupDialog;
import com.ilives.baseprj.databinding.ActivityLoginBinding;
import com.ilives.baseprj.features.HomeActivity;

import es.dmoral.toasty.Toasty;

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
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initRootView(Bundle savedInstanceState) {
        this.rootView = DataBindingUtil.setContentView(this, R.layout.activity_login);
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        this.initViews();
    }

    @Override
    protected void loadData(Bundle savedInstanceState) {

    }

    private void initViews() {
        getPresenter().bindView(this);
        this.rootView.btnLogin.setOnClickListener(this);
        this.rootView.btnShowDialog.setOnClickListener(this);
    }

    @Override
    public LoginContract.Presenter getPresenter() {
        return this.mPresenter = this.mPresenter != null ? this.mPresenter : new LoginActivityPresenter();
    }

    @Override
    public void authenticated() {
        showToast(ToastType.SUCCESS, "authenticated");
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    @Override
    public void unAuthenticated(String msg) {
        showToast(ToastType.ERROR, msg);
    }

    private void doAuthenticate() {
        if (!preCheckConnection()) return;
        String email = "vu.lt@neo-lab.vn";
        String password = "123123123";
        int type = 2;

        getPresenter().doAuthenticate(email, password, type);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                this.doAuthenticate();
                break;
            case R.id.btnShowDialog:
                //showDialog();
                break;
            default:
                break;
        }
    }

    private void showDialog() {
        PopupDialog dialog = new PopupDialog(this);
        dialog.setTitle("Title");
        dialog.setContent("Define content message here...");
        dialog.setActionListener(view -> {
            Toasty.success(this, "Pressed Action");
        });
        dialog.show();
    }
}
