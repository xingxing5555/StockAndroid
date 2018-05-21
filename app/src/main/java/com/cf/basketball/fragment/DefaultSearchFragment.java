package com.cf.basketball.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cf.basketball.R;
import com.cf.basketball.adapter.search.DefaultSearchAdapter;
import com.cf.basketball.adapter.search.TradeSearchAdapter;
import com.cf.basketball.databinding.FragmentDefaultSearchBinding;
import com.example.admin.basic.model.TradeModel;
import com.example.admin.basic.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 默认的搜索界面
 *
 * @author xinxin Shi
 */
public class DefaultSearchFragment extends BaseFragment {


    private FragmentDefaultSearchBinding binding;
    private List<String> dataList = new ArrayList<>();
    private List<TradeModel> tradeList = new ArrayList<>();

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
        getData();
    }

    private void initView() {
        binding.mrvSearchHistoryList.setLayoutManager(createGridLayoutManager(5));
        binding.mrvSearchHotList.setLayoutManager(createGridLayoutManager(5));
        binding.mrvSearchTradeList.setLayoutManager(createLayoutManager(true));
        binding.mrvSearchHistoryList.setAdapter(new DefaultSearchAdapter(R.layout
                .item_default_search_btn, dataList));
        binding.mrvSearchHotList.setAdapter(new DefaultSearchAdapter(R.layout
                .item_default_search_btn, dataList));
        binding.mrvSearchTradeList.setAdapter(new TradeSearchAdapter(R.layout.item_trade_search,
                tradeList));
    }


    public void getData() {
        for (int i = 0; i < 10; i++) {
            dataList.add("BTC");
        }
        for (int i = 0; i < 3; i++) {
            tradeList.add(new TradeModel("火币", "交易量为10000"));
        }
    }
}
