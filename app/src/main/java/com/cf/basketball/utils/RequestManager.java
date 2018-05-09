package com.cf.basketball.utils;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by luoyu on 2017/5/8.
 */

public class RequestManager {

    private static String BaseUrl = "http://img1.money.126.net/data/";

    private static RequestManager _self;

    private static Retrofit mRetrofit;

    private static NetworkService mService;

//    public static RequestManager getInstance(){
//        if(_self == null){
//            _self = new RequestManager();
//        }
//        return _self;
//    }

    public static void init(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor( new AppInterceptor2())//增加App级别的拦截器，可以在较底层处理请求和返回的数据  new AppInterceptor
                .build();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mService = mRetrofit.create(NetworkService.class);
    }

    public static NetworkService getService(){
        return mService;
    }
}
