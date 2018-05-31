package com.ilives.baseprj.data.core;

import com.google.gson.Gson;
import com.ilives.baseprj.common.models.Error;
import com.ilives.baseprj.common.util.LogUtils;
import com.ilives.baseprj.common.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;

import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

/**
 * -------------^_^-------------
 * ❖ com.ilives.baseprj.data.core
 * ❖ Created by IntelliJ IDEA
 * ❖ Author: Johnny
 * ❖ Date: 5/31/18
 * ❖ Time: 10:43
 * -------------^_^-------------
 **/
public abstract class ApiCallBack<T> extends DisposableObserver<Response<T>> {

    @Override
    public void onNext(Response<T> res) {
        if (res == null) {
            cannotConnectToServer();
            return;
        }

        if (res.isSuccessful()) {
            onSuccess(res.body());
            return;
        }

            try {
                JSONObject errorRes = null;
                try {
                    errorRes = new JSONObject(res.errorBody().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String errorStr = errorRes.getString("error");
                if (!Utils.isJson(errorStr)) {
                    LogUtils.d("TAG STRING", errorStr);
                    this.onFailed(errorStr);
                    return;
                }
                Error error = new Gson().fromJson(errorRes.toString(), Error.class);
                LogUtils.d("TAG JSON", error.getFirstMessage());
                this.onFailed(error.getFirstMessage());
            } catch (JSONException e) {
                e.printStackTrace();
            }
    }

    @Override
    public void onError(Throwable e) {
        String error;
        if (e instanceof HttpException) {
            ResponseBody responseBody = ((HttpException) e).response().errorBody();
            error = getErrorMessage(responseBody);
        } else if (e instanceof SocketTimeoutException) {
            error = "Connection time out!";
        } else if (e instanceof IOException) {
            cannotConnectToServer();
            error = "Cannot connect to server";
        } else {
            error = e.getMessage();
        }
        this.onFailed(error);
    }

    @Override
    public void onComplete() {

    }

    public abstract void cannotConnectToServer();

    public abstract void onFailed(String error);

    public abstract void onSuccess(T data);

    private String getErrorMessage(ResponseBody responseBody) {
        try {
            JSONObject jsonObject = new JSONObject(responseBody.string());
            return jsonObject.getString("message");
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
