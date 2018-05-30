package com.cf.basketball.adapter.btc;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.cf.basketball.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.admin.basic.model.market.BtcMarketModel;
import com.example.admin.basic.utils.CommonUtils;

import java.util.List;

/**
 * BTC列表
 *
 * @author Xinxin Shi
 */

public class BtcMarketListAdapter extends BaseQuickAdapter<BtcMarketModel.DataBean.MarketsBean,
        BaseViewHolder> {


    public BtcMarketListAdapter(int layoutResId, @Nullable List<BtcMarketModel.DataBean.MarketsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BtcMarketModel.DataBean.MarketsBean item) {
        helper.setText(R.id.tv_btc_name, item.getName()) ;
//        tvBtcSource.setText(item.getChange());
        helper.setText(R.id.tv_market,TextUtils.concat("交易量", item.getValue()));
        helper.setText(R.id.tv_btc_price,item.getPrice1());
        helper.setText(R.id.tv_btc_foreign_price,item.getPrice2());
        String updown = item.getUpdown();
        helper.setText(R.id.btn_btc,updown);
        boolean plus = CommonUtils.isMinus(updown);
        if (!plus) {
            helper.setText(R.id.btn_btc,TextUtils.concat("+", item.getUpdown()));
        } else {
            helper.setText(R.id.btn_btc,item.getUpdown());
        }
        helper.itemView.findViewById(R.id.btn_btc).setSelected(plus);
        helper.itemView.findViewById(R.id.tv_btc_price).setEnabled(plus);
    }
}
