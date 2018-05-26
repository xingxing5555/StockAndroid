package com.cf.basketball.net;

import com.example.admin.basic.interfaces.OnRequestListener;
import com.example.admin.basic.net.RequestManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Xinxin Shi
 */

public class NetManager {
    private static NetManager instance;
    public OnRequestListener listener;

    private NetManager() {
        RequestManager.initString();
    }

    public static NetManager getInstance() {
        if (null == instance) {
            synchronized (NetManager.class) {
                if (null == instance) {
                    instance = new NetManager();
                }
            }
        }
        return instance;
    }

    public void getHomeTab(OnRequestListener listener) {
        this.listener = listener;
        RequestManager.getService().getHomeTab().enqueue(createListener(listener));
    }

    public void getMineList(String token, int pageNum, int order, OnRequestListener listener) {
        RequestManager.getService().getMineList(token, pageNum, order).enqueue(createListener
                (listener));
    }

    public void getUpDown(int pageNum, int order, OnRequestListener listener) {
        RequestManager.getService().getUpDown(pageNum, order).enqueue(createListener(listener));
    }

    public void getCoinData(int pageNum, String id, OnRequestListener listener) {
        RequestManager.getService().getCoinData(pageNum, id).enqueue(createListener(listener));
    }

    public void getSearchData(String token,OnRequestListener listener){
        RequestManager.getService().getSearchData(token).enqueue(createListener(listener));
    }

    public Callback<String> createListener(final OnRequestListener listener) {
        return new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                listener.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                listener.onRequestFailure("错误日志：" + t.getMessage());
            }
        };
    }

}
