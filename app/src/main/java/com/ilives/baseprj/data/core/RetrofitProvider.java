package com.ilives.baseprj.data.core;

import com.google.gson.GsonBuilder;
import com.ilives.baseprj.BuildConfig;
import com.ilives.baseprj.app.Constants;
import com.ilives.baseprj.app.MyApplication;
import com.ilives.baseprj.manager.PreferenceManager;

import java.lang.reflect.Modifier;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * -------------^_^-------------
 * ❖ com.ilives.baseprj.data
 * ❖ Created by IntelliJ IDEA
 * ❖ Author: Johnny
 * ❖ Date: 5/30/18
 * ❖ Time: 16:52
 * -------------^_^-------------
 **/
public class RetrofitProvider {
    private static final String BASE_URL = BuildConfig.ENDPOINT;
    private static final String HEADER_TOKEN = "Authorization";
    private static final String HEADER_CONTENT_TYPE = "Content-Type";

    private Retrofit mRetrofit;

    private static RetrofitProvider sInstance;

    public static synchronized RetrofitProvider getInstance() {
        if (sInstance == null) {
            sInstance = new RetrofitProvider();
        }
        return sInstance;
    }

    private RetrofitProvider() {
        GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC);
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getConfigOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(builder.create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        mRetrofit = retrofitBuilder.build();
    }

    private static synchronized OkHttpClient getConfigOkHttpClient() {
        /* Config OkHttpClient */
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.cache(new Cache(MyApplication.getApplication().getCacheDir(), Constants.CACHE_SIZE));
        okHttpClient.addInterceptor(logging);
        // ignore timeout exception in case response is big json string
        okHttpClient.connectTimeout(Constants.CONNECT_TIMEOUT, TimeUnit.SECONDS);
        okHttpClient.readTimeout(Constants.READ_TIMEOUT, TimeUnit.SECONDS);
        okHttpClient.writeTimeout(Constants.WRITE_TIMEOUT, TimeUnit.SECONDS);
        okHttpClient.retryOnConnectionFailure(true);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClient.addInterceptor(interceptor);
        okHttpClient.interceptors().add(chain -> {
            Request original = chain.request();
            //Log.d("API URL |=========| ", original.url().toString());
            // Request customization: add request headers
            Request.Builder requestBuilder = original.newBuilder()
                    .addHeader(HEADER_TOKEN, "Bearer " + PreferenceManager.getInstance().getAccessToken())
                    .addHeader(HEADER_CONTENT_TYPE, "application/json");
            Request request = requestBuilder.build();
            return chain.proceed(request);
        });
        return okHttpClient.build();
    }

    public <T> T makeApi(Class<T> tClass) {
        return mRetrofit.create(tClass);
    }
}
