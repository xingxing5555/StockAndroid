package com.example.admin.basic.net;

import com.example.admin.basic.constants.Constants;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by luoyu on 2017/5/8.
 */

public class RequestManager {

    private static String BaseUrl = Constants.BASE_URL;

    private static RequestManager _self;

    private static Retrofit mRetrofit;

    private static NetworkService mService;
    private static NetworkService jsonService;

//    public static RequestManager getInstance(){
//        if(_self == null){
//            _self = new RequestManager();
//        }
//        return _self;
//    }

    public static void init() {
        String BaseUrl = "http://img1.money.126.net/data/";
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor( new AppInterceptor2())//增加App级别的拦截器，可以在较底层处理请求和返回的数据  new
// AppInterceptor
                .build();
        mRetrofit = new Retrofit.Builder().baseUrl(BaseUrl).client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()).build();
        mService = mRetrofit.create(NetworkService.class);
    }
    public static void initString() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor( new AppInterceptor2())//增加App级别的拦截器，可以在较底层处理请求和返回的数据  new
// AppInterceptor
                .build();
        mRetrofit = new Retrofit.Builder().baseUrl(BaseUrl).client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create()).build();
        mService = mRetrofit.create(NetworkService.class);
    }

    public static NetworkService getService() {
        return mService;
    }
}
