package com.ilives.baseprj.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.ilives.baseprj.app.Constants;
import com.ilives.baseprj.app.MyApplication;

/**
 * -------------^_^-------------
 * ❖ com.ilives.baseprj.manager
 * ❖ Created by IntelliJ IDEA
 * ❖ Author: Johnny
 * ❖ Date: 5/30/18
 * ❖ Time: 16:54
 * -------------^_^-------------
 **/
public class PreferenceManager {
    private static final String TAG = PreferenceManager.class.getSimpleName();
    private static PreferenceManager sInstance;
    private SharedPreferences mSharedPreferences;
    private String mAccessToken;

    public static synchronized void init(Context context) {
        if (sInstance == null) {
            sInstance = new PreferenceManager();
        }
    }

    public synchronized static PreferenceManager getInstance() {
        if (sInstance == null) {
            sInstance = new PreferenceManager();
        }
        return sInstance;
    }

    private PreferenceManager() {
        Context context = MyApplication.getApplication();
        mSharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        mAccessToken = mSharedPreferences.getString(Constants.SHARED_PREFERENCE_TOKEN, "");
    }

    public SharedPreferences getSharedPreferences() {
        return mSharedPreferences;
    }

    public void updateAccessToken(String accessToken) {
        mAccessToken = accessToken;
        mSharedPreferences.edit()
                .putString(Constants.SHARED_PREFERENCE_TOKEN, mAccessToken)
                .apply();
    }

    public String getAccessToken() {
        return mAccessToken;
    }
}
