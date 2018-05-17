package com.cf.basketball.adapter.home;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.cf.basketball.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.admin.basic.model.HomeCurrencyModel;

import java.util.List;

/**
 * BTC列表
 *
 * @author Xinxin Shi
 */

public class HomeBTCAdapter extends BaseQuickAdapter<HomeCurrencyModel, BaseViewHolder> {

    public HomeBTCAdapter(int layoutResId, @Nullable List<HomeCurrencyModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeCurrencyModel item) {
        helper.setText(R.id.tv_btc_name, item.getName());
        helper.setText(R.id.tv_btc_source, item.getType());
        helper.setText(R.id.tv_market_volume, item.getVolume());
        helper.setText(R.id.tv_btc_price, item.getPrice());
        helper.setText(R.id.tv_btc_foreign_price, item.getForeignPrice());
        helper.setText(R.id.btn_btc, item.getIncrease());
        if (TextUtils.equals("0", item.getState())) {
            helper.itemView.findViewById(R.id.btn_btc).setSelected(false);
        } else {
            helper.itemView.findViewById(R.id.btn_btc).setSelected(true);
        }
    }


}
