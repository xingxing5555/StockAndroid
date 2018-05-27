package com.cf.basketball.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cf.basketball.R;
import com.cf.basketball.activity.CurrencyInfoActivity;
import com.cf.basketball.adapter.search.DefaultSearchAdapter;
import com.cf.basketball.adapter.search.HistorySearchAdapter;
import com.cf.basketball.adapter.search.TradeSearchAdapter;
import com.cf.basketball.databinding.FragmentDefaultSearchBinding;
import com.cf.basketball.net.NetManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.admin.basic.base.BaseFragment;
import com.example.admin.basic.interfaces.OnRequestListener;
import com.example.admin.basic.model.TradeModel;
import com.example.admin.basic.model.search.DefaultSearchModel;
import com.example.admin.basic.utils.LogUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * 默认的搜索界面
 *
 * @author xinxin Shi
 */
public class DefaultSearchFragment extends BaseFragment implements BaseQuickAdapter
        .OnItemClickListener, OnRequestListener {


    private FragmentDefaultSearchBinding binding;
    private List<String> dataList = new ArrayList<>();
    private List<TradeModel> tradeList = new ArrayList<>();
    private HistorySearchAdapter historyAdapter;
    private DefaultSearchAdapter hotAdapter;
    private TradeSearchAdapter tradeAdapter;
    private List<DefaultSearchModel.DataBean.HotCointsBean> hotCoints;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_default_search, container,
                false);
        initView();
        return binding.getRoot();
    }

    private void initData() {
        NetManager.getInstance().getDefaultSearchData(token, this);
        getData();
    }

    private void initView() {
        binding.mrvSearchHistoryList.setLayoutManager(createGridLayoutManager(5));
        binding.mrvSearchHotList.setLayoutManager(createGridLayoutManager(5));
        binding.mrvSearchTradeList.setLayoutManager(createLayoutManager(true));
        historyAdapter = new HistorySearchAdapter(R.layout.item_default_search_btn);
        hotAdapter = new DefaultSearchAdapter(R.layout.item_default_search_btn);
        tradeAdapter = new TradeSearchAdapter(R.layout.item_trade_search);
        binding.mrvSearchHistoryList.setAdapter(historyAdapter);
        binding.mrvSearchHotList.setAdapter(hotAdapter);
        binding.mrvSearchTradeList.setAdapter(tradeAdapter);
        historyAdapter.setOnItemClickListener(this);
        hotAdapter.setOnItemClickListener(this);
        tradeAdapter.setOnItemClickListener(this);
    }


    public void getData() {
        for (int i = 0; i < 10; i++) {
            dataList.add("BTC");
        }
        for (int i = 0; i < 3; i++) {
            tradeList.add(new TradeModel("火币", "交易量为10000"));
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        startActivity(CurrencyInfoActivity.class);
        if (!getActivity().isDestroyed()) {
            getActivity().finish();
        }
    }

    @Override
    public void onResponse(String tag, String json) {
        LogUtils.e("搜索：" + json);
        DefaultSearchModel defaultSearchModel = new Gson().fromJson(json, DefaultSearchModel.class);
        DefaultSearchModel.DataBean data = defaultSearchModel.getData();
        List<String> history = data.getHistory();
        hotCoints = data.getHotCoints();
        List<DefaultSearchModel.DataBean.ExchangesBean> exchanges = data.getExchanges();
        historyAdapter.setNewData(history);
        historyAdapter.notifyDataSetChanged();
        hotAdapter.setNewData(hotCoints);
        hotAdapter.notifyDataSetChanged();
        tradeAdapter.setNewData(exchanges);
        tradeAdapter.notifyDataSetChanged();

    }

    @Override
    public void onRequestFailure(String errorMsg) {
        LogUtils.e(errorMsg);
    }
}
