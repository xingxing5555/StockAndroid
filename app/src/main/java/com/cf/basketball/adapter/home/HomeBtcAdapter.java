package com.cf.basketball.adapter.home;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;

import com.cf.basketball.R;
import com.example.admin.basic.model.HomeCurrencyModel;
import com.example.admin.basic.view.ListBaseAdapter;
import com.example.admin.basic.view.SuperViewHolder;

/**
 * BTC列表
 *
 * @author Xinxin Shi
 */

public class HomeBtcAdapter extends ListBaseAdapter<HomeCurrencyModel> {

    public HomeBtcAdapter(Context context) {
        super(context);
    }

//    public HomeBtcAdapter(int layoutResId, @Nullable List<HomeCurrencyModel> data) {
//        super(layoutResId, data);
//    }

//    @Override
//    protected void convert(BaseViewHolder helper, HomeCurrencyModel item) {
//        helper.setText(R.id.tv_btc_name, item.getName());
//        helper.setText(R.id.tv_btc_source, item.getType());
//        helper.setText(R.id.tv_market_volume, item.getVolume());
//        helper.setText(R.id.tv_btc_price, item.getPrice());
//        helper.setText(R.id.tv_btc_foreign_price, item.getForeignPrice());
//        helper.setText(R.id.btn_btc, item.getIncrease());
//        if (TextUtils.equals("0", item.getState())) {
//            helper.itemView.findViewById(R.id.btn_btc).setSelected(false);
//        } else {
//            helper.itemView.findViewById(R.id.btn_btc).setSelected(true);
//        }
//    }


    @Override
    public int getLayoutId() {
        return R.layout.item_home_btc;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        TextView tvBtcName = holder.getView(R.id.tv_btc_name);
        TextView tvBtcSource = holder.getView(R.id.tv_btc_source);
        TextView tvBtcVolume = holder.getView(R.id.tv_market_price);
        TextView tvBtcPrice = holder.getView(R.id.tv_btc_price);
        TextView tvBtcForeignPrice = holder.getView(R.id.tv_btc_foreign_price);
        Button btnBtc = holder.getView(R.id.btn_btc);
        HomeCurrencyModel item = getDataList().get(position);
        tvBtcName.setText(item.getName());
        tvBtcSource.setText(item.getType());
        tvBtcVolume.setText(item.getVolume());
        tvBtcPrice.setText(item.getPrice());
        tvBtcForeignPrice.setText(item.getForeignPrice());
        btnBtc.setText(item.getIncrease());
        if (TextUtils.equals("0", item.getState())) {
            btnBtc.setSelected(false);
        } else {
            btnBtc.setSelected(true);
        }
    }
}
