package com.cf.basketball.fragment.home;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cf.basketball.activity.BtcInfoActivity;
import com.cf.basketball.adapter.home.HomeMarketAdapter;
import com.cf.basketball.net.NetManager;
import com.example.admin.basic.base.BaseRecyclerViewFragment;
import com.example.admin.basic.constants.Constants;
import com.example.admin.basic.interfaces.OnRequestListener;
import com.example.admin.basic.model.home.HomeMarketModel;
import com.example.admin.basic.utils.LogUtils;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * 市值
 *
 * @author xinxin Shi
 */
public class HomeMarketFragment extends BaseRecyclerViewFragment implements OnRequestListener {


    private List<HomeMarketModel.DataBean.CoinsBean> list=new ArrayList<>();
    private HomeMarketAdapter adapter;
    private int pageNum = 1, order;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NetManager.getInstance().getMarketList(pageNum, order, this);
    }


    @Override
    public void initView() {
    }

    @Override
    public void refresh() {
        pageNum++;
        NetManager.getInstance().getMarketList(pageNum, order, this);
    }

    @Override
    public LRecyclerViewAdapter getLRecyclerViewAdapter() {
        adapter = new HomeMarketAdapter(getContext());
        return new LRecyclerViewAdapter(adapter);
    }

    @Override
    public void onItemClickListener(int position) {
        startActivity(BtcInfoActivity.class);
    }

    @Override
    public void onResponse(String tag,String json) {
        LogUtils.e("市值：" + json);
        HomeMarketModel model = new Gson().fromJson(json, HomeMarketModel.class);
        if (model == null || model.getCode() != Constants.NET_REQUEST_SUCCESS_CODE) {
            return;
        }
        List<HomeMarketModel.DataBean.CoinsBean> coins = model.getData().getCoins();
        list.addAll(coins);
        adapter.setDataList(list);
        adapter.notifyDataSetChanged();
        mRecyclerView.refreshComplete(0);
    }

    @Override
    public void onRequestFailure(String errorMsg) {
        LogUtils.e("市值" + errorMsg);
    }
}
