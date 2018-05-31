package com.ilives.baseprj.data;

import com.google.gson.JsonObject;
import com.ilives.baseprj.common.models.User;
import com.ilives.baseprj.common.models.api_response.ApiResponse;
import com.ilives.baseprj.data.core.RetrofitProvider;
import com.ilives.baseprj.features.login.models.LoginData;
import com.ilives.baseprj.manager.PreferenceManager;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * -------------^_^-------------
 * ❖ com.ilives.baseprj.data
 * ❖ Created by IntelliJ IDEA
 * ❖ Author: Johnny
 * ❖ Date: 5/30/18
 * ❖ Time: 17:00
 * -------------^_^-------------
 **/
public class UserRepository {

    private UserApi mUserApi;

    private static UserRepository sInstance;

    public static synchronized UserRepository getInstance() {
        if (sInstance == null) {
            sInstance = new UserRepository();
        }
        return sInstance;
    }

    private UserRepository() {
        mUserApi = RetrofitProvider.getInstance().makeApi(UserApi.class);
    }

    public Observable<ApiResponse<LoginData>> doLogin(String email, String password, int type) {
        return mUserApi.login(email, password, type);
    }

    public Observable<ApiResponse<User>> getUserProfile() {
        return mUserApi.getProfile();
    }


    public Observable<ApiResponse<String>> sendResetPassEmail(String email) {
        return mUserApi.sendResetPassEmail(email);
    }


    public Observable<ApiResponse<String>> doLogout() {
        return mUserApi.doLogout();
    }

    public Observable<ApiResponse<JsonObject>> refreshToken() {
        String tokenIa = PreferenceManager.getInstance().getAccessToken();
        return mUserApi.refreshToken(tokenIa != null && !tokenIa.isEmpty() ? tokenIa : null);
    }

    interface UserApi {
        //region Account

        @POST("login")
        Observable<ApiResponse<LoginData>> login(@Field("email") String email,
                                                 @Field("password") String password,
                                                 @Field("type") int type);

        @GET("profile")
        Observable<ApiResponse<User>> getProfile();

        @FormUrlEncoded
        @POST("passwords")
        Observable<ApiResponse<String>> sendResetPassEmail(@Field("email") String email);

        @POST("logout")
        Observable<ApiResponse<String>> doLogout();

        @GET("refresh_token")
        Observable<ApiResponse<JsonObject>> refreshToken(@Query("token_ia") String tokenIa);

        //endregion
    }
}
