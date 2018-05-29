package com.cf.basketball.adapter.currency;

import android.support.annotation.Nullable;

import com.cf.basketball.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.admin.basic.model.currency.CurrencyMarketModel;

import java.util.List;

/**
 * @author Xinxin Shi
 */

public class CurrencyInfoMarketAdapter extends BaseQuickAdapter<CurrencyMarketModel.DataBean
        .DetailsBean, BaseViewHolder> {

    public CurrencyInfoMarketAdapter(int layoutResId, @Nullable List<CurrencyMarketModel.DataBean
            .DetailsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CurrencyMarketModel.DataBean.DetailsBean item) {
        helper.setText(R.id.tv_market_time, item.getTime());
        helper.setText(R.id.tv_market, item.getPrice());
        helper.setText(R.id.tv_market, item.getVolume());
    }
}
