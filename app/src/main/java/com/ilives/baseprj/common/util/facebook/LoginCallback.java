package com.ilives.baseprj.common.util.facebook;

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;

/**
 * -------------^_^-------------
 * ❖ com.ilives.baseprj.common.util.facebook
 * ❖ Created by IntelliJ IDEA
 * ❖ Author: Johnny
 * ❖ Date: 6/4/18
 * ❖ Time: 10:19
 * -------------^_^-------------
 **/
public interface LoginCallback extends FacebookCallback<LoginResult> {
    void onSuccess(LoginResult loginResult);
    void onCancel();
    void onError(FacebookException exception);
}
