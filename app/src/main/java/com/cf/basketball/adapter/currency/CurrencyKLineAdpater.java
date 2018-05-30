package com.cf.basketball.adapter.currency;

import android.support.annotation.Nullable;

import com.cf.basketball.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author Xinxin Shi
 */

public class CurrencyKLineAdpater extends BaseQuickAdapter<String, BaseViewHolder> {

    public CurrencyKLineAdpater(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_kline_type, item);
    }
}
