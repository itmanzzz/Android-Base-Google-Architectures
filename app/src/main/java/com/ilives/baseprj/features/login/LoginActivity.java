package com.ilives.baseprj.features.login;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookException;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.ilives.baseprj.R;
import com.ilives.baseprj.common.base.BaseActivity;
import com.ilives.baseprj.common.models.ToastType;
import com.ilives.baseprj.common.util.LogUtils;
import com.ilives.baseprj.common.util.facebook.FacebookUtil;
import com.ilives.baseprj.common.util.facebook.FbGraphCallback;
import com.ilives.baseprj.common.util.facebook.LoginCallback;
import com.ilives.baseprj.common.views.PopupDialog;
import com.ilives.baseprj.databinding.ActivityLoginBinding;
import com.ilives.baseprj.features.HomeActivity;

import org.json.JSONException;
import org.json.JSONObject;

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
public class LoginActivity extends BaseActivity<LoginContract.Presenter>
        implements LoginContract.View, View.OnClickListener, LoginCallback, FbGraphCallback {

    ActivityLoginBinding rootView;
    private LoginActivityPresenter mPresenter;
    private CallbackManager callbackManager;

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

        // Login Facebook
        this.callbackManager = CallbackManager.Factory.create();
        this.rootView.btnMyFbLogin.setOnClickListener(view -> this.doLoginFbIn());
    }

    private void doLoginFbIn() {
        if (this.rootView.btnMyFbLogin.getText().toString().trim().equalsIgnoreCase("FB LOGIN")) {
            FacebookUtil.doLoginFacebook(this, callbackManager, this);
            return;
        }

        LoginManager.getInstance().logOut();
        this.rootView.btnMyFbLogin.setText("FB LOGIN");
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
                showDialog();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showDialog() {
        PopupDialog dialog = new PopupDialog(this);
        dialog.setTitle("Title");
        dialog.setContent("Define content message here...");
        dialog.setActionListener(view -> {
            dialog.dismiss();
            Toasty.success(this, "Implement Action...").show();
        });
        dialog.show();
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        this.rootView.btnMyFbLogin.setText(isLoggedIn ? "LOGOUT" : "FB LOGIN");
        FacebookUtil.getFbGraphProfile(loginResult, this);
    }


    @Override
    public void onCancel() {

    }

    @Override
    public void onError(FacebookException error) {

    }

    @Override
    public void onCompleted(JSONObject data, GraphResponse res) {
        LogUtils.d("LoginActivity", res.toString());
        // Application code
        try {
            String email = data.getString("email");
            String name = data.getString("name");
            String birthday = "";
            if (data.has("birthday")) {
                birthday = data.getString("birthday"); // 01/31/1980 format
            }
            Toasty.success(LoginActivity.this, "Email: " + email + " -- birthday: " + birthday + " -- Name: " + name).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
