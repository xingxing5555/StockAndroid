package com.cf.basketball.net;

import com.example.admin.basic.interfaces.OnRequestListener;
import com.example.admin.basic.net.RequestManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Xinxin Shi
 */

public class NetManager implements Callback<String> {
    private static NetManager instance;
    public OnRequestListener listener;

    public NetManager() {
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
        RequestManager.getService().getHomeTab().enqueue(this);
    }

    public void getMineList(String token, int pageNum, int order, OnRequestListener listener) {
        this.listener = listener;
        RequestManager.getService().getMineList(token, pageNum, order).enqueue(this);
    }

    public void getUpDown(int pageNum, int order, OnRequestListener listener) {
        this.listener = listener;
        RequestManager.getService().getUpDown(pageNum, order).enqueue(this);
    }

    public void getCoinData(int pageNum, String id, OnRequestListener listener) {
        this.listener = listener;
        RequestManager.getService().getCoinData(pageNum, id).enqueue(this);
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        listener.onResponse(response.body());
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        listener.onRequestFailure("错误日志：" + t.getMessage());
    }
}
