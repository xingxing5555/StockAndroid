package com.cf.basketball.fragment.currency;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cf.basketball.R;
import com.cf.basketball.adapter.currency.CurrencyInfoBriefAdapter;
import com.cf.basketball.databinding.FragmentCurrencyInfoBriefBinding;
import com.example.admin.basic.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 货币详情-简况
 *
 * @author xinxin Shi
 */
public class CurrencyInfoBriefFragment extends BaseFragment {


    private FragmentCurrencyInfoBriefBinding binding;


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
        binding.mrvCurrencyBriefList.setAdapter(new CurrencyInfoBriefAdapter(getActivity(),R.layout.item_currency_info_brief,getList()));
    }

    private List<String> getList() {
        List<String> list = new ArrayList<>();
        list.add("Bitcion/BTC");
        list.add("比特币");
        list.add("http://bitcion.org");
        list.add("No.1");
        list.add("21,000,000");
        list.add("17,006,362");
        list.add("166家");
        return list;
    }
}

