package com.cf.basketball.fragment.btc;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cf.basketball.R;
import com.cf.basketball.adapter.home.HomeBTCAdapter;
import com.cf.basketball.databinding.FragmentBtcchartBinding;
import com.example.admin.basic.base.BaseFragment;

/**
 * BTC的图表界面
 *
 * @author xinxin Shi
 */
public class BTCChartFragment extends BaseFragment {
    private FragmentBtcchartBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_btcchart, container, false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        binding.getRoot().findViewById(R.id.ll_sort_prompt).setVisibility(View.VISIBLE);
        binding.mrvList.setLayoutManager(createLayoutManager(true));
        binding.mrvList.addItemDecoration(createItemDecoration(R.color.grey_d));
        binding.mrvList.setAdapter(new HomeBTCAdapter(R.layout.item_home_btc, createData()));
    }

}
