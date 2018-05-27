package com.cf.basketball.adapter.currency;

import android.support.annotation.Nullable;

import com.cf.basketball.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.admin.basic.model.HomeCurrencyModel;

import java.util.List;

/**
 * @author Xinxin Shi
 */

public class CurrencyInfoMarketAdapter extends BaseQuickAdapter<HomeCurrencyModel, BaseViewHolder> {

    public CurrencyInfoMarketAdapter(int layoutResId, @Nullable List<HomeCurrencyModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeCurrencyModel item) {
        helper.setText(R.id.tv_market_time, "23:32:13");
        helper.setText(R.id.tv_market, "¥56352.54");
        helper.setText(R.id.tv_market, "0.02");
    }
}
