package com.cf.basketball.adapter.search;

import android.support.annotation.Nullable;

import com.cf.basketball.R;
import com.example.admin.basic.model.TradeModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author Xinxin Shi
 */

public class TradeSearchAdapter extends BaseQuickAdapter<TradeModel, BaseViewHolder> {

    public TradeSearchAdapter(int layoutResId, @Nullable List<TradeModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TradeModel item) {
        helper.setText(R.id.tv_trade_name, item.getName());
        helper.setText(R.id.tv_trade_volume, item.getVolume());
    }
}
