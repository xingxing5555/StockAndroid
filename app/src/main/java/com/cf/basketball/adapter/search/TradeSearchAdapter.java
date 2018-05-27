package com.cf.basketball.adapter.search;

import com.cf.basketball.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.admin.basic.model.search.DefaultSearchModel;

/**
 * @author Xinxin Shi
 */

public class TradeSearchAdapter extends BaseQuickAdapter<DefaultSearchModel.DataBean.ExchangesBean, BaseViewHolder> {

    public TradeSearchAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, DefaultSearchModel.DataBean.ExchangesBean item) {
        helper.setText(R.id.tv_trade_name, item.getName());
        helper.setText(R.id.tv_trade_volume, item.getDesc());
    }
}
