package com.cf.basketball.fragment.btc;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cf.basketball.R;
import com.cf.basketball.adapter.btc.BtcBriefProjectAdapter;
import com.cf.basketball.databinding.FragmentBtcBriefBinding;
import com.cf.basketball.net.NetManager;
import com.example.admin.basic.base.BaseFragment;
import com.example.admin.basic.constants.Constants;
import com.example.admin.basic.interfaces.OnRequestListener;
import com.example.admin.basic.model.market.MarketDescModel;
import com.example.admin.basic.utils.LogUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * btc-简况
 *
 * @author xinxin Shi
 */
public class BtcBriefFragment extends BaseFragment implements OnRequestListener {

    private List<String> list = new ArrayList<>();
    private FragmentBtcBriefBinding binding;
    private String id;
    private BtcBriefProjectAdapter btcBriefProjectAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments().getString("id");
        LogUtils.e("btc 简介id=" + id);
        NetManager.getInstance().getMarketDesc(id, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_btc_brief, container, false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        binding.mrvProjectList.setLayoutManager(createLayoutManager(true));
        binding.mrvProjectList.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        btcBriefProjectAdapter = new BtcBriefProjectAdapter(getActivity(), R.layout
                .item_btc_brief_project);
        binding.mrvProjectList.setAdapter(btcBriefProjectAdapter);
    }


    @Override
    public void onResponse(String tag, String json) {
        LogUtils.e("市值描述：" + json);
        MarketDescModel marketDescModel = new Gson().fromJson(json, MarketDescModel.class);
        if (marketDescModel == null || marketDescModel.getCode() != Constants
                .NET_REQUEST_SUCCESS_CODE) {
            return;
        }
        MarketDescModel.DataBean data = marketDescModel.getData();
        binding.pvMarket.setPercentValue(data.getRate1());
        binding.pvCirculation.setPercentValue(data.getRate2());
        binding.pvChangeHand.setPercentValue(data.getRate3());
        binding.tvProjectContent.setText(data.getDesc());
        btcBriefProjectAdapter.setNewData(getData(data));
        btcBriefProjectAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestFailure(String errorMsg) {

    }

    private List<String> getData(MarketDescModel.DataBean data) {
        List<String> list = new ArrayList<>();
        list.add(String.valueOf(data.getRank()));
        list.add(data.getValue());
        list.add(String.valueOf(data.getCirculation()));
        list.add(String.valueOf(data.getVolume()));
        list.add(data.getCost());
        list.add(data.getTime());
        list.add(data.getUrl1());
        return list;
    }
}
