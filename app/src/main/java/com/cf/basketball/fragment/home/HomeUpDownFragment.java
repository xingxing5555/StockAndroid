package com.cf.basketball.fragment.home;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cf.basketball.activity.CurrencyInfoActivity;
import com.cf.basketball.adapter.home.HomeUpDownAdapter;
import com.cf.basketball.net.NetManager;
import com.example.admin.basic.base.BaseRecyclerViewFragment;
import com.example.admin.basic.constants.Constants;
import com.example.admin.basic.interfaces.OnRequestListener;
import com.example.admin.basic.model.home.HomeUpDownModel;
import com.example.admin.basic.utils.LogUtils;
import com.example.admin.basic.view.SortLayout;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * 涨幅
 *
 * @author xinxin Shi
 */
public class HomeUpDownFragment extends BaseRecyclerViewFragment implements OnRequestListener,
        SortLayout.OnSortChangeListener {


    private List<HomeUpDownModel.DataBean.CoinsBean> list = new ArrayList<>();
    private HomeUpDownAdapter adapter;
    private int pageNum = 1;
    private int order = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        downData();
    }


    @Override
    public void initView() {
        slSort.setOnSortChangeListener(this);
        setSortPromptVisible();
    }

    @Override
    public void refresh() {
        pageNum++;
        downData();
    }

    @Override
    public LRecyclerViewAdapter getLRecyclerViewAdapter() {
        adapter = new HomeUpDownAdapter(getContext());
        adapter.setDataList(list);
        return new LRecyclerViewAdapter(adapter);
    }

    @Override
    public void onItemClickListener(int position) {
        startActivity(CurrencyInfoActivity.class);
    }

    private void downData() {
        NetManager.getInstance().getUpDown(pageNum, order, this);
    }

    @Override
    public void onResponse(String tag, String json) {
        HomeUpDownModel model = new Gson().fromJson(json, HomeUpDownModel.class);
        if (model == null || model.getCode() != Constants.NET_REQUEST_SUCCESS_CODE) {
            return;
        }
        List<HomeUpDownModel.DataBean.CoinsBean> coins = model.getData().getCoins();
        list.addAll(coins);
        adapter.setDataList(list);
        adapter.notifyDataSetChanged();
        mRecyclerView.refreshComplete(0);
        LogUtils.e("涨幅：" + json);
    }

    @Override
    public void onRequestFailure(String errorMsg) {
        LogUtils.e(errorMsg);
        mRecyclerView.refreshComplete(0);
    }

    @Override
    public void onSortChangeListener(int order) {
        list.clear();
        pageNum = 1;
        this.order = order;
        downData();
    }
}
