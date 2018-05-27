package com.cf.basketball.fragment.currency;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cf.basketball.R;
import com.cf.basketball.adapter.currency.CurrencyInfoMarketAdapter;
import com.cf.basketball.databinding.FragmentCurrencyInfoMarketBinding;
import com.cf.basketball.net.NetManager;
import com.example.admin.basic.base.BaseFragment;
import com.example.admin.basic.interfaces.OnRequestListener;
import com.example.admin.basic.utils.LogUtils;

/**
 * 货币详情-市场
 *
 * @author xinxin Shi
 */
public class CurrencyInfoMarketFragment extends BaseFragment implements OnRequestListener {


    private FragmentCurrencyInfoMarketBinding binding;
    private String id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments().getString("id");
        NetManager.getInstance().getCurrencyMarketData(id, this);
    }

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
        binding.mrvMarketList.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        binding.mrvMarketList.setAdapter(new CurrencyInfoMarketAdapter(R.layout
                .item_currency_info_market, createData()));
    }


    @Override
    public void onResponse(String tag, String json) {
        LogUtils.e("货币市场：" + json);
    }

    @Override
    public void onRequestFailure(String errorMsg) {

    }
}
