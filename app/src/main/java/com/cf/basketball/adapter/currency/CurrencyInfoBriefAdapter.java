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

public class CurrencyInfoBriefAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private String[] briefInfoTitle;
    public CurrencyInfoBriefAdapter(Context context,int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
        briefInfoTitle=context.getResources().getStringArray(R.array.currency_info_brief_basic_info);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_currency_brief_title, briefInfoTitle[helper.getAdapterPosition()]);
        helper.setText(R.id.tv_currency_brief_content, item);
    }
}
