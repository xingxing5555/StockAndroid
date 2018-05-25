package com.cf.basketball.adapter.currency;

import android.content.Context;
import android.support.annotation.Nullable;

import com.cf.basketball.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author Xinxin Shi
 */

public class CurrencyLineAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private String[] titleArray;

    public CurrencyLineAdapter(Context context, int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
        titleArray = context.getResources().getStringArray(R.array.currency_line);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_line_title, titleArray[helper.getAdapterPosition()]);
        helper.setText(R.id.tv_line_value, item);
        if (helper.getAdapterPosition() < 3) {
            helper.itemView.findViewById(R.id.tv_line_value).setEnabled(false);
        }
    }
}
