package com.cf.basketball.fragment.btc;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cf.basketball.R;
import com.cf.basketball.adapter.btc.BtcBriefProjectAdapter;
import com.cf.basketball.databinding.FragmentBtcBriefBinding;
import com.example.admin.basic.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * btc-简况
 *
 * @author xinxin Shi
 */
public class BTCBriefFragment extends BaseFragment {

    private List<String> list = new ArrayList<>();
    private FragmentBtcBriefBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_btc_brief, container, false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        binding.mrvProjectList.setLayoutManager(createLayoutManager(true));
        binding.mrvProjectList.addItemDecoration(createItemDecoration(R.color.grey_d));
        binding.mrvProjectList.setAdapter(new BtcBriefProjectAdapter(getActivity(), R.layout
                .item_btc_brief_project, getData()));
    }


    private List<String> getData() {
        List<String> list = new ArrayList<>();
        list.add("#1");
        list.add("$162,123,123,123.0");
        list.add("$162,123,123,123.0");
        list.add("21000000BTC");
        list.add("0");
        list.add("2009 年1月3日");
        list.add("官网，区块链浏览器，白皮书");
        return list;
    }

}
