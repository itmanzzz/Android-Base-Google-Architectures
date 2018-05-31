package com.ilives.baseprj.features.login;

import com.ilives.baseprj.common.base.BaseActivityPresenter;
import com.ilives.baseprj.common.models.ToastType;
import com.ilives.baseprj.common.util.LogUtils;

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
                .subscribe(res -> {
                    if (res.getStatus()) {
                        LogUtils.d(TAG, "Success ========>>" + res.toString());
                        this.mView.authenticated();
                        return;
                    }
                    this.mView.unAuthenticated(res.getError());
                }, error -> {
                    this.mView.hideLoading();
                    LogUtils.d(TAG, "Failed ========>>" + error.toString());
                    if (!handleCommonError(error) && this.mView != null) {
                        this.mView.showToast(ToastType.ERROR, error.getMessage());
                    }
                });
    }

    @Override
    public void onDestroy() {

    }
}
