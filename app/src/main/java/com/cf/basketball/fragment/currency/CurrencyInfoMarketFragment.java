package com.cf.basketball.fragment.currency;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cf.basketball.R;
import com.cf.basketball.adapter.currency.CurrencyInfoMarketAdapter;
import com.cf.basketball.databinding.FragmentCurrencyInfoMarketBinding;
import com.example.admin.basic.base.BaseFragment;

/**
 * 货币详情-市场
 *
 * @author xinxin Shi
 */
public class CurrencyInfoMarketFragment extends BaseFragment {


    private FragmentCurrencyInfoMarketBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_currency_info_market,
                container, false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        binding.mrvMarketList.setLayoutManager(createLayoutManager(true));
        binding.mrvMarketList.addItemDecoration(createItemDecoration(R.color.grey_d));
        binding.mrvMarketList.setAdapter(new CurrencyInfoMarketAdapter(R.layout
                .item_currency_info_market, createData()));
    }


}
