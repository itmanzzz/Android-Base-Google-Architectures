package com.ilives.baseprj.common.util.facebook;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.facebook.CallbackManager;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;

/**
 * -------------^_^-------------
 * ❖ com.ilives.baseprj.common.util
 * ❖ Created by IntelliJ IDEA
 * ❖ Author: Johnny
 * ❖ Date: 6/4/18
 * ❖ Time: 10:14
 * -------------^_^-------------
 **/
public class FacebookUtil {
    /**
     * user_birthday must request review first
     */
    private static final String[] fbPermission = {"public_profile", "email", "user_friends"};


    public static void doLoginFacebook(Context context,CallbackManager callbackManager,  LoginCallback callback) {
        LoginManager.getInstance().logInWithReadPermissions((Activity) context, Arrays.asList(fbPermission));
        LoginManager.getInstance().registerCallback(callbackManager, callback);
    }

    public static void getFbGraphProfile(LoginResult loginResult, FbGraphCallback callback) {
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(), callback);
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender");
        request.setParameters(parameters);
        request.executeAsync();
    }
}
