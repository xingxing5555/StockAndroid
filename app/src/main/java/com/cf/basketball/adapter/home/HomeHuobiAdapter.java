package com.cf.basketball.adapter.home;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.cf.basketball.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.admin.basic.model.HomeCurrencyModel;

import java.util.List;

/**
 * @author Xinxin Shi
 */

public class HomeHuobiAdapter  extends BaseQuickAdapter<HomeCurrencyModel, BaseViewHolder> {

    public HomeHuobiAdapter(int layoutResId, @Nullable List<HomeCurrencyModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeCurrencyModel item) {
        helper.setText(R.id.tv_huobi_source, item.getName());
        helper.setText(R.id.tv_huobi_name, item.getType());
        helper.setText(R.id.tv_market_volume, item.getVolume());
        helper.setText(R.id.tv_huobi_price, item.getPrice());
        helper.setText(R.id.tv_huobi_foreign_price, item.getForeignPrice());
        helper.setText(R.id.btn_huobi, item.getIncrease());
        if (TextUtils.equals("0", item.getState())) {
            helper.itemView.findViewById(R.id.btn_huobi).setSelected(false);
        } else {
            helper.itemView.findViewById(R.id.btn_huobi).setSelected(true);
        }
    }
}
