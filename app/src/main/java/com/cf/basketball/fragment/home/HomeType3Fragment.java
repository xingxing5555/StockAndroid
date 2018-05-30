package com.cf.basketball.fragment.home;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cf.basketball.activity.CurrencyInfoActivity;
import com.cf.basketball.adapter.home.HomeType3Adapter;
import com.cf.basketball.net.NetManager;
import com.example.admin.basic.base.BaseRecyclerViewFragment;
import com.example.admin.basic.constants.Constants;
import com.example.admin.basic.interfaces.OnRequestListener;
import com.example.admin.basic.model.home.HomeType3Model;
import com.example.admin.basic.utils.LogUtils;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * BTC
 *
 * @author xinxin Shi
 */
public class HomeType3Fragment extends BaseRecyclerViewFragment implements OnRequestListener {

    private HomeType3Adapter homeType3Adapter;
    private List<HomeType3Model.DataBean.CoinsBean> list = new ArrayList<>();
    private int pageNum = 1;
    private String id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments().getString("id");
        LogUtils.e("btc =" + id);
        downData();
    }

    private void downData() {
        NetManager.getInstance().getCoinData(pageNum, id, this);
    }

    @Override
    public void initView() {
    }

    @Override
    public void refresh() {
        pageNum++;
        downData();
    }

    @Override
    public LRecyclerViewAdapter getLRecyclerViewAdapter() {
        homeType3Adapter = new HomeType3Adapter(getContext());
        homeType3Adapter.setDataList(list);
        return new LRecyclerViewAdapter(homeType3Adapter);
    }

    @Override
    public void onItemClickListener(int position) {
        int id = list.get(position).getId();
        startActivity(String.valueOf(id), CurrencyInfoActivity.class);
    }

    @Override
    public void onResponse(String tag, String json) {
        HomeType3Model marketMarketModel = new Gson().fromJson(json, HomeType3Model.class);
        if (marketMarketModel == null || marketMarketModel.getCode() != Constants
                .NET_REQUEST_SUCCESS_CODE) {
            return;
        }
        List<HomeType3Model.DataBean.CoinsBean> coins = marketMarketModel.getData().getCoins();
        list.addAll(coins);
        homeType3Adapter.setDataList(list);
        mRecyclerView.refreshComplete(0);
        homeType3Adapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestFailure(String errorMsg) {

    }
}
