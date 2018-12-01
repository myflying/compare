package com.yc.compare.common;


import com.yc.compare.common.interceptor.BasicParamsInterceptor;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by admin on 2017/3/10.
 */

public class RetrofitManager {

    public static OkHttpClient mOkHttpClient;

    public static Retrofit mRetrofit;

    public static Retrofit retrofit() {
        initOkHttpClient();
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(Constants.HOT_DATA_URL)
                    .client(mOkHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

        }
        return mRetrofit;
    }

    public static void initOkHttpClient() {
        //基本参数拦截器
        BasicParamsInterceptor basicParamsInterceptor = new BasicParamsInterceptor.Builder()
                .addParam("sex", "男")
                .build();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        if (mOkHttpClient == null) {
            synchronized (RetrofitManager1.class) {
                if (mOkHttpClient == null) {
                    mOkHttpClient = new OkHttpClient.Builder()
                            //.addInterceptor(basicParamsInterceptor)
                            .addInterceptor(logging)
                            .build();
                }
            }
        }
    }
}