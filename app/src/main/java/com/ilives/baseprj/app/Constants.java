package com.ilives.baseprj.app;

import android.Manifest;
import android.annotation.SuppressLint;

import com.ilives.baseprj.BuildConfig;

/**
 * -------------^_^-------------
 * ❖ com.ilives.baseprj.app
 * ❖ Created by IntelliJ IDEA
 * ❖ Author: Johnny
 * ❖ Date: 5/30/18
 * ❖ Time: 14:53
 * -------------^_^-------------
 **/
public class Constants {

    //region API Constants
    /**
     * The constant API_ENDPOINT.
     */
    private static final String API_ENDPOINT = BuildConfig.ENDPOINT;
    /**
     * The constant URL_LOGIN.
     */
    public static final String URL_LOGIN = API_ENDPOINT + "login";
    /**
     * The constant URL_RESET_PWD.
     */
    public static final String URL_RESET_PWD = API_ENDPOINT + "api/user/password/reset";

    // TODO ==== Define API HERE!!! ======


    //endregion API Constants

    //region Code Constants

    /**
     * The constant CODE_REQUEST_CAMERA.
     */
    public static final int CODE_REQUEST_CAMERA = 999;


    /**
     * The constant CODE_RESULT_CAMERA.
     */
    public static final int CODE_RESULT_CAMERA = 111;


    // TODO ==== Define Result, Request Code HERE!!! ======

    //endregion Code Constants

    //region Other Constants

    /**
     * The constant EXTRA_INTENT_ID.
     */
    public static final String EXTRA_INTENT_ID = "EXTRA_INTENT_ID";

    /**
     * The constant CACHE_SIZE.
     */
    public static final int CACHE_SIZE = 10 * 1024 * 1024;
    /**
     * The constant CONNECT_TIMEOUT.
     */
    public static final int CONNECT_TIMEOUT = 15; // ms
    /**
     * The constant READ_TIMEOUT.
     */
    public static final int READ_TIMEOUT = 30; // ms
    /**
     * The constant WRITE_TIMEOUT.
     */
    public static final int WRITE_TIMEOUT = 30; // ms

    /**
     * The constant APP_PERMISSIONS.
     */
    @SuppressLint("InlinedApi")
    public static final String[] APP_PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};

    /**
     * The constant DATE_FORMAT_SERVER.
     */
    public static final String DATE_FORMAT_SERVER = "yyyy/MM/dd HH:mm";

    /**
     * The constant DATE_FORMAT_CLIENT.
     */
    public static final String DATE_FORMAT_CLIENT = "yyyy/MM/dd HH:mm";

    public static final String DATE_FORMAT_FOR_DATE_LIST = "yyyy/MM/dd(EEEE) hh:mm a";

    /**
     * The constant SHARED_PREFERENCE_NAME.
     */
    public static final String SHARED_PREFERENCE_NAME = BuildConfig.APPLICATION_ID;
    /**
     * The constant SHARED_PREFERENCE_TOKEN.
     */
    public static final String SHARED_PREFERENCE_TOKEN = "sp_token";
    /**
     * The constant SHARED_PREFERENCE_USER_ID.
     */
    public static final String SHARED_PREFERENCE_USER_ID = "sp_user_id";
    // TODO ==== Other HERE!!! ======

    //endregion Other Constants

}
