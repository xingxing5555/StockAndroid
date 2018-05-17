package com.cf.basketball.adapter.home;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.cf.basketball.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.admin.basic.model.HomeCurrencyModel;

import java.util.List;

/**
 * 市值列表
 *
 * @author Xinxin Shi
 */

public class HomeMarketAdapter extends BaseQuickAdapter<HomeCurrencyModel, BaseViewHolder> {

    public HomeMarketAdapter(int layoutResId, @Nullable List<HomeCurrencyModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeCurrencyModel item) {
        if (helper.getAdapterPosition() > 2) {
            helper.itemView.findViewById(R.id.tv_rank).setEnabled(false);
        }
        helper.setText(R.id.tv_rank, String.valueOf(helper.getAdapterPosition() + 1));
        helper.setText(R.id.tv_market_name, item.getName());
        helper.setText(R.id.tv_market_total_value, item.getVolume());
        helper.setText(R.id.tv_market_volume, item.getPrice());
        helper.setText(R.id.btn_increase, item.getIncrease());
        if (TextUtils.equals("0", item.getState())) {
            helper.itemView.findViewById(R.id.btn_increase).setSelected(false);
        } else {
            helper.itemView.findViewById(R.id.btn_increase).setSelected(true);
        }
    }


}
