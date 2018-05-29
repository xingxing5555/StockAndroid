package com.cf.basketball.adapter.home;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;

import com.cf.basketball.R;
import com.example.admin.basic.model.home.HomeType3Model;
import com.example.admin.basic.utils.CommonUtils;
import com.example.admin.basic.view.ListBaseAdapter;
import com.example.admin.basic.view.SuperViewHolder;

/**
 * BTC列表
 *
 * @author Xinxin Shi
 */

public class HomeType3Adapter extends ListBaseAdapter<HomeType3Model.DataBean.CoinsBean> {

    public HomeType3Adapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_home_btc;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        TextView tvBtcName = holder.getView(R.id.tv_btc_name);
        TextView tvBtcSource = holder.getView(R.id.tv_btc_source);
        TextView tvBtcVolume = holder.getView(R.id.tv_market);
        TextView tvBtcPrice = holder.getView(R.id.tv_btc_price);
        TextView tvBtcForeignPrice = holder.getView(R.id.tv_btc_foreign_price);
        Button btnBtc = holder.getView(R.id.btn_btc);
        HomeType3Model.DataBean.CoinsBean item = getDataList().get(position);
        tvBtcName.setText(item.getName());
        tvBtcSource.setText(item.getChange());
        tvBtcVolume.setText(TextUtils.concat("交易量", item.getVolume()));
        tvBtcPrice.setText(item.getPrice1());
        tvBtcForeignPrice.setText(item.getPrice2());
        String updown = item.getUpdown();
        btnBtc.setText(updown);
        boolean plus = CommonUtils.isMinus(updown);
        btnBtc.setSelected(plus);
        tvBtcPrice.setEnabled(plus);
    }
}
