package com.ilives.baseprj.features.login;

import com.ilives.baseprj.common.models.User;
import com.ilives.baseprj.common.models.api_response.ApiResponse;
import com.ilives.baseprj.data.UserRepository;
import com.ilives.baseprj.features.login.models.LoginData;

import rx.Observable;

/**
 * -------------^_^-------------
 * ❖ com.ilives.baseprj.features.login
 * ❖ Created by IntelliJ IDEA
 * ❖ Author: Johnny
 * ❖ Date: 5/31/18
 * ❖ Time: 00:29
 * -------------^_^-------------
 **/
public class LoginActivityInteractor implements LoginContract.Interactor{

    @Override
    public Observable<ApiResponse<LoginData>> authenticateUser(String email, String password, int type) {
        return UserRepository.getInstance().doLogin(email, password, type);
    }

    @Override
    public Observable<ApiResponse<User>> getUserProfile() {
        return UserRepository.getInstance().getUserProfile();
    }
}
