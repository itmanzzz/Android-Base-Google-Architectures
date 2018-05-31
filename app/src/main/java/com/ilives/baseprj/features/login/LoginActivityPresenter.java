package com.ilives.baseprj.features.login;

import android.util.Log;

import com.ilives.baseprj.common.base.BaseActivityPresenter;
import com.ilives.baseprj.common.models.ToastType;
import com.ilives.baseprj.common.models.api_response.ApiResponse;
import com.ilives.baseprj.common.util.LogUtils;
import com.ilives.baseprj.features.login.models.LoginData;

import java.io.IOException;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

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
        this.mInteractor.authenticateUser(email, password, type).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<Response<LoginData>>() {
                    @Override
                    public void onNext(Response<LoginData> loginDataApiResponse) {
                        if (loginDataApiResponse.isSuccessful()) {
                            mView.authenticated();
                        } else {
                            mView.hideLoading();
                            try {
                                LogUtils.d("============>", loginDataApiResponse.errorBody().string());
                                mView.showToast(ToastType.ERROR, loginDataApiResponse.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
//                       if (loginDataApiResponse.getStatus()) {
////                           LogUtils.d(TAG, "Success ========>>" + loginDataApiResponse.getData().getToken().toString());
//                           mView.authenticated();
//                       } else  {
//                           mView.hideLoading();
//                       }
                    }
                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
//        this.mInteractor.authenticateUser(email, password, type)
//                .subscribe(res -> {
//                    if (res.getStatus()) {
//
//                    }
//                    this.mView.unAuthenticated(res.getError());
//                }, error -> {
//                    this.mView.hideLoading();
//                    LogUtils.d(TAG, "Failed ========>>" + error.toString());
//                    if (!handleCommonError(error) && this.mView != null) {
//                        this.mView.showToast(ToastType.ERROR, error.getMessage());
//                    }
//                });
    }

    @Override
    public void onDestroy() {

    }
}
