package com.cf.basketball.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cf.basketball.R;
import com.cf.basketball.activity.CurrencyInfoActivity;
import com.cf.basketball.activity.SearchActivity;
import com.cf.basketball.adapter.search.DefaultSearchAdapter;
import com.cf.basketball.adapter.search.HistorySearchAdapter;
import com.cf.basketball.adapter.search.TradeSearchAdapter;
import com.cf.basketball.databinding.FragmentDefaultSearchBinding;
import com.cf.basketball.net.NetManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.admin.basic.base.BaseFragment;
import com.example.admin.basic.interfaces.OnRequestListener;
import com.example.admin.basic.model.search.DefaultSearchModel;
import com.example.admin.basic.utils.LogUtils;
import com.google.gson.Gson;


import java.util.List;

/**
 * 默认的搜索界面
 *
 * @author xinxin Shi
 */
public class DefaultSearchFragment extends BaseFragment implements BaseQuickAdapter
        .OnItemClickListener, OnRequestListener {


    private FragmentDefaultSearchBinding binding;
    private HistorySearchAdapter historyAdapter;
    private DefaultSearchAdapter hotAdapter;
    private TradeSearchAdapter tradeAdapter;
    private List<DefaultSearchModel.DataBean.HotCointsBean> hotCoints;
    private List<DefaultSearchModel.DataBean.ExchangesBean> tradeList;
    private List<String> history;

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
        historyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ((SearchActivity) getActivity()).setSearchContent(history.get(position));
            }
        });
        hotAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (hotCoints == null || hotCoints.isEmpty()) {
                    return;
                }
                String id = hotCoints.get(position).getId();
                startActivityToInfo(id);
            }
        });
        tradeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (tradeList == null || tradeList.isEmpty()) {
                    return;
                }
                String id = tradeList.get(position).getId();
                startActivityToInfo(id);
            }
        });
    }

    private void startActivityToInfo(String id) {
        startActivity(id, CurrencyInfoActivity.class);
        if (!getActivity().isDestroyed()) {
            getActivity().finish();
        }
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onResponse(String tag, String json) {
        LogUtils.e("搜索：" + json);
        DefaultSearchModel defaultSearchModel = new Gson().fromJson(json, DefaultSearchModel.class);
        DefaultSearchModel.DataBean data = defaultSearchModel.getData();
        history = data.getHistory();
        hotCoints = data.getHotCoints();
        tradeList = data.getExchanges();
        historyAdapter.setNewData(history);
        historyAdapter.notifyDataSetChanged();
        hotAdapter.setNewData(hotCoints);
        hotAdapter.notifyDataSetChanged();
        tradeAdapter.setNewData(tradeList);
        tradeAdapter.notifyDataSetChanged();

    }

    @Override
    public void onRequestFailure(String errorMsg) {
        LogUtils.e(errorMsg);
    }
}
