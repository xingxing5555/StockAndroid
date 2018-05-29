package com.cf.basketball.fragment.home;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cf.basketball.activity.CurrencyInfoActivity;
import com.cf.basketball.adapter.home.HomeType4Adapter;
import com.cf.basketball.net.NetManager;
import com.example.admin.basic.base.BaseRecyclerViewFragment;
import com.example.admin.basic.constants.Constants;
import com.example.admin.basic.interfaces.OnRequestListener;
import com.example.admin.basic.model.home.HomeType4Model;
import com.example.admin.basic.utils.LogUtils;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * 火币
 *
 * @author xinxin Shi
 */
public class HomeType4Fragment extends BaseRecyclerViewFragment implements OnRequestListener {

    private List<HomeType4Model.DataBean.CoinsBean> list = new ArrayList<>();
    private HomeType4Adapter adapter;
    private int pageNum = 1;
    private String id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.e("火币 =" + id);
        id = getArguments().getString("id");
        downData();
    }

    private void downData() {
        NetManager.getInstance().getHomeHuobiList(pageNum, id, this);
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
        adapter = new HomeType4Adapter(getContext());
        adapter.setDataList(list);
        return new LRecyclerViewAdapter(adapter);
    }

    @Override
    public void onItemClickListener(int position) {
        int id = list.get(position).getId();
        startActivity(String.valueOf(id), CurrencyInfoActivity.class);
    }

    @Override
    public void onResponse(String tag, String json) {
        LogUtils.e("火币：" + json);
        HomeType4Model huobiModel = new Gson().fromJson(json, HomeType4Model.class);
        if (huobiModel == null || huobiModel.getCode() != Constants.NET_REQUEST_SUCCESS_CODE) {
            return;
        }
        List<HomeType4Model.DataBean.CoinsBean> coins = huobiModel.getData().getCoins();
        list.addAll(coins);
        adapter.setDataList(list);
        adapter.notifyDataSetChanged();
        mRecyclerView.refreshComplete(0);
    }

    @Override
    public void onRequestFailure(String errorMsg) {
        mRecyclerView.refreshComplete(0);
    }
}
