package com.ilives.baseprj.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.multidex.MultiDex;
import android.support.v4.app.ActivityCompat;

import com.ilives.baseprj.manager.PreferenceManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * -------------^_^-------------
 * ❖ com.ilives.baseprj.app
 * ❖ Created by IntelliJ IDEA
 * ❖ Author: Johnny
 * ❖ Date: 5/30/18
 * ❖ Time: 15:06
 * -------------^_^-------------
 **/
public class MyApplication extends Application {
    private static final String TAG = MyApplication.class.getSimpleName();
    private static MyApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        PreferenceManager.init();
        AppsLifecycleHandler handler = new AppsLifecycleHandler(this);
        registerActivityLifecycleCallbacks(handler);
        registerComponentCallbacks(handler);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /**
     * Get application my application.
     *
     * @return the my application
     */
    public static MyApplication getApplication() {
        return sInstance;
    }

    public static boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) MyApplication.getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = Objects.requireNonNull(cm).getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

    public boolean doCheckAndRequestPermissions(Activity activity, int requestCode) {
        List<String> notGrantedPermissions = new ArrayList<>();
        for (String permission : Constants.APP_PERMISSIONS) {
            int permissionState = ActivityCompat.checkSelfPermission(this, permission);
            if (permissionState != PackageManager.PERMISSION_GRANTED) {
                notGrantedPermissions.add(permission);
            }
        }
        if (!notGrantedPermissions.isEmpty()) {
            String[] requestPermission = new String[notGrantedPermissions.size()];
            for (int i = 0; i < requestPermission.length; i++) {
                requestPermission[i] = notGrantedPermissions.get(i);
            }
            ActivityCompat.requestPermissions(activity, requestPermission, requestCode);
        }
        return notGrantedPermissions.isEmpty();
    }
}
