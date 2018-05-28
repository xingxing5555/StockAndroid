package com.cf.basketball.fragment.home;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cf.basketball.activity.CurrencyInfoActivity;
import com.cf.basketball.adapter.home.HomeBtcAdapter;
import com.cf.basketball.net.NetManager;
import com.example.admin.basic.base.BaseRecyclerViewFragment;
import com.example.admin.basic.constants.Constants;
import com.example.admin.basic.interfaces.OnRequestListener;
import com.example.admin.basic.model.market.MarketMarketModel;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * BTC
 *
 * @author xinxin Shi
 */
public class HomeBtcFragment extends BaseRecyclerViewFragment implements OnRequestListener {

    private HomeBtcAdapter homeBTCAdapter;
    private List<MarketMarketModel.DataBean.CoinsBean> list = new ArrayList<>();
    private int pageNum = 1;
    private String id = "3";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        downData();
    }

    private void downData() {
        NetManager.getInstance().getHomeBtcList(pageNum, id, this);
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
        homeBTCAdapter = new HomeBtcAdapter(getContext());
        homeBTCAdapter.setDataList(list);
        return new LRecyclerViewAdapter(homeBTCAdapter);
    }

    @Override
    public void onItemClickListener(int position) {
        int id = list.get(position).getId();
        startActivity(String.valueOf(id), CurrencyInfoActivity.class);
    }

    @Override
    public void onResponse(String tag, String json) {
        MarketMarketModel marketMarketModel = new Gson().fromJson(json, MarketMarketModel.class);
        if (marketMarketModel == null || marketMarketModel.getCode() != Constants
                .NET_REQUEST_SUCCESS_CODE) {
            return;
        }
        List<MarketMarketModel.DataBean.CoinsBean> coins = marketMarketModel.getData().getCoins();
        list.addAll(coins);
        homeBTCAdapter.setDataList(list);
        mRecyclerView.refreshComplete(0);
        homeBTCAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestFailure(String errorMsg) {

    }
}
