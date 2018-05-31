package com.ilives.baseprj.features.login;

import com.ilives.baseprj.R;
import com.ilives.baseprj.common.base.BaseActivityPresenter;
import com.ilives.baseprj.common.models.ToastType;
import com.ilives.baseprj.common.util.LogUtils;
import com.ilives.baseprj.data.core.ApiCallBack;
import com.ilives.baseprj.features.login.models.LoginData;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * -------------^_^-------------
 * ❖ com.ilives.baseprj.features.login
 * ❖ Created by IntelliJ IDEA
 * ❖ Author: Johnny
 * ❖ Date: 5/31/18
 * ❖ Time: 00:26
 * -------------^_^-------------
 **/
public class LoginActivityPresenter extends BaseActivityPresenter<LoginContract.View> implements LoginContract.Presenter {

    private static final String TAG = LoginActivityPresenter.class.getSimpleName();
    private LoginContract.Interactor mInteractor;

    public LoginActivityPresenter() {
        this.mInteractor = new LoginActivityInteractor();
    }

    @Override
    public void doAuthenticate(String email, String password, int type) {
        this.mView.showLoading();
        this.mInteractor.authenticateUser(email, password, type)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallBack<LoginData>() {
                    @Override
                    public void cannotConnectToServer() {
                        mView.hideLoading();
                        mView.showToast(ToastType.ERROR, R.string.msg_unexpected_error);
                    }

                    @Override
                    public void onFailed(String msg) {
                        mView.hideLoading();
                        LogUtils.d("============>", msg);
                        mView.showToast(ToastType.ERROR, msg);
                        mView.unAuthenticated(msg);
                    }

                    @Override
                    public void onSuccess(LoginData data) {
                        mView.hideLoading();
                        mView.authenticated();
                    }
                });
    }

    @Override
    public void onDestroy() {

    }
}
