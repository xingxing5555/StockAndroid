package com.cf.basketball.net;

import android.text.TextUtils;

import com.example.admin.basic.constants.Constants;
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

//    首页接口

    public void getHomeTab(OnRequestListener listener) {
        RequestManager.getService().getHomeTab().enqueue(createListener("", listener));
    }

    public void changeOrder(String token, String ids, OnRequestListener listener) {
        RequestManager.getService().getHomeTab().enqueue(createListener("", listener));
    }

    public void getMineList(String token, int pageNum, int order, OnRequestListener listener) {
        RequestManager.getService().getMineList(token, pageNum, order).enqueue(createListener
                (Constants.TAG_MINE, listener));
    }

    public void getUpDown(int pageNum, int order, OnRequestListener listener) {
        RequestManager.getService().getUpDown(pageNum, order).enqueue(createListener("", listener));
    }

    public void getHomeHuobiList(int pageNum, String id, OnRequestListener listener) {
        RequestManager.getService().getHomeHuobiList(id, pageNum).enqueue(createListener("",
                listener));
    }

    public void getCoinData(int pageNum, String id, OnRequestListener listener) {
        RequestManager.getService().getCoinData(id, pageNum).enqueue(createListener("", listener));
    }


    public void getMarketList(int pageNum, int order, OnRequestListener listener) {
        RequestManager.getService().getMarketList(pageNum, order).enqueue(createListener("",
                listener));
    }

    /**
     * 添加/删除自选货币
     *
     * @param token    用户
     * @param id       标识
     * @param event    事件
     * @param listener 监听
     */
    public void addOrDelCurrency(String token, String id, String event, OnRequestListener
            listener) {
        RequestManager.getService().addOrDelCurrency(token, id, event).enqueue(createListener
                (Constants.TAG_ADD_DEL_EVENT, listener));
    }


    public void getDefaultSearchData(String token, OnRequestListener listener) {
        RequestManager.getService().getDefaultSearchData(token).enqueue(createListener("",
                listener));
    }

    public void getSearchKeyData(String key, String token, OnRequestListener listener) {
        RequestManager.getService().getSearchKeyData(key, token).enqueue(createListener(Constants
                .TAG_SEARCH, listener));
    }


//    货币详情

    public void getCurrencyInfo(String id, OnRequestListener listener) {
        RequestManager.initString();
        RequestManager.getService().getCurrencyInfo(id).enqueue(createListener(Constants
                .TAG_CURRENCY_INFO, listener));
    }

    public void getMLine(String id, OnRequestListener listener) {
        RequestManager.getService().getMline(id).enqueue(createListener(Constants
                .TAG_CURRENCY_MLINE, listener));
    }

    public void getKLine(String id, int klineType, OnRequestListener listener) {
        RequestManager.getService().getKline(id, klineType).enqueue(createListener(TextUtils
                .concat(Constants.TAG_CURRENCY_KLINE, String.valueOf(klineType)).toString(),
                listener));
    }

    public void getCurrencyMarketList(int pageNum, String id, OnRequestListener listener) {
        RequestManager.getService().getCurrencyMarketList(pageNum, id).enqueue(createListener("",
                listener));
    }

    public void getCurrencyMarketData(String id, OnRequestListener listener) {
        RequestManager.getService().getCurrencyMarketData(id).enqueue(createListener("", listener));
    }

// 市值详情

    public void getMarketInfo(String id, OnRequestListener listener) {
        RequestManager.getService().getMarketInfo(id).enqueue(createListener("", listener));
    }

    public void getBtcMarket(String id, OnRequestListener listener) {
        RequestManager.getService().getBtcMarket(id).enqueue(createListener("", listener));
    }

    public void getMarketDesc(String id, OnRequestListener listener) {
        RequestManager.getService().getMarketDesc(id).enqueue(createListener("", listener));
    }


    private Callback<String> createListener(final String tag, final OnRequestListener listener) {
        return new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                listener.onResponse(tag, response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                listener.onRequestFailure("错误日志：" + t.getMessage());
            }
        };
    }

}
