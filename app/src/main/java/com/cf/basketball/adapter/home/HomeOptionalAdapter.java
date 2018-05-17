package com.cf.basketball.adapter.home;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.cf.basketball.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.admin.basic.model.HomeCurrencyModel;

import java.util.List;

/**
 * 自选列表
 *
 * @author Xinxin Shi
 */

public class HomeOptionalAdapter extends BaseQuickAdapter<HomeCurrencyModel, BaseViewHolder> {

    public HomeOptionalAdapter(int layoutResId, @Nullable List<HomeCurrencyModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeCurrencyModel item) {
        helper.setText(R.id.tv_increase_source, item.getName());
        helper.setText(R.id.tv_increase_name, item.getType());
        helper.setText(R.id.tv_market_volume, item.getVolume());
        helper.setText(R.id.tv_increase_price, item.getPrice());
        helper.setText(R.id.tv_increase_foreign_price, item.getForeignPrice());
        helper.setText(R.id.btn_increase, item.getIncrease());
        if (TextUtils.equals("0", item.getState())) {
            helper.itemView.findViewById(R.id.btn_increase).setSelected(false);
        } else {
            helper.itemView.findViewById(R.id.btn_increase).setSelected(true);
        }
    }


}
