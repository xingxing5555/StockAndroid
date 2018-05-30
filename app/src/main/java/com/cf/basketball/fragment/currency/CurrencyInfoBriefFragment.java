package com.cf.basketball.fragment.currency;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cf.basketball.R;
import com.cf.basketball.adapter.currency.CurrencyInfoBriefAdapter;
import com.cf.basketball.databinding.FragmentCurrencyInfoBriefBinding;
import com.cf.basketball.net.NetManager;
import com.example.admin.basic.base.BaseFragment;
import com.example.admin.basic.constants.Constants;
import com.example.admin.basic.interfaces.OnRequestListener;
import com.example.admin.basic.model.currency.CurrencyBriefModel;
import com.example.admin.basic.utils.LogUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * 货币详情-简况
 *
 * @author xinxin Shi
 */
public class CurrencyInfoBriefFragment extends BaseFragment implements OnRequestListener {


    private FragmentCurrencyInfoBriefBinding binding;
    private String id;
    private List<String> list = new ArrayList<>();
    private CurrencyInfoBriefAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments().getString("id");
        NetManager.getInstance().getCurrencyBriefData(id, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_currency_info_brief,
                container, false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        binding.mrvCurrencyBriefList.setLayoutManager(createLayoutManager(true));
        adapter = new CurrencyInfoBriefAdapter(getActivity(), R.layout.item_currency_info_brief,
                list);
        binding.mrvCurrencyBriefList.setAdapter(adapter);
    }


    @Override
    public void onResponse(String tag, String json) {
        LogUtils.e("货币简介：" + json);
        CurrencyBriefModel model = new Gson().fromJson(json, CurrencyBriefModel.class);
        if (model == null || model.getCode() != Constants.NET_REQUEST_SUCCESS_CODE) {
            return;
        }
        CurrencyBriefModel.DataBean data = model.getData();
        binding.tvDesc.setText(data.getDesc());
        list.addAll(getList(data));
        adapter.setNewData(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestFailure(String errorMsg) {

    }

    private List<String> getList(CurrencyBriefModel.DataBean data) {
        List<String> list = new ArrayList<>();
        list.add("");//英文名称
        list.add("");//中文名称
        list.add(data.getUrl1());
        list.add(data.getRank());
        list.add(data.getVolume());
        list.add(data.getCirculation());
        list.add("");//上市家数
        return list;
    }
}

