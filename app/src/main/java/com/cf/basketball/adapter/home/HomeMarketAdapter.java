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
 * 市值列表
 *
 * @author Xinxin Shi
 */

public class HomeMarketAdapter extends ListBaseAdapter<HomeCurrencyModel> {
    public HomeMarketAdapter(Context context) {
        super(context);
    }

//    public HomeMarketAdapter(int layoutResId, @Nullable List<HomeCurrencyModel> data) {
//        super(layoutResId, data);
//    }
//
//    @Override
//    protected void convert(BaseViewHolder helper, HomeCurrencyModel item) {
//        if (helper.getAdapterPosition() > 2) {
//            helper.itemView.findViewById(R.id.tv_rank).setEnabled(false);
//        }
//        helper.setText(R.id.tv_rank, String.valueOf(helper.getAdapterPosition() + 1));
//        helper.setText(R.id.tv_market_name, item.getName());
//        helper.setText(R.id.tv_market_total_value, item.getVolume());
//        helper.setText(R.id.tv_market_price, item.getPrice());
//        helper.setText(R.id.btn_increase, item.getIncrease());
//        if (TextUtils.equals("0", item.getState())) {
//            helper.itemView.findViewById(R.id.btn_increase).setSelected(false);
//        } else {
//            helper.itemView.findViewById(R.id.btn_increase).setSelected(true);
//        }
//    }


    @Override
    public int getLayoutId() {
        return R.layout.item_home_market;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        TextView tvRank = holder.getView(R.id.tv_rank);
        TextView tvMarketName = holder.getView(R.id.tv_market_name);
        TextView tvMarketTotalValue = holder.getView(R.id.tv_market_total_value);
        TextView tvMarketPrice = holder.getView(R.id.tv_market_price);
        Button btnIncrease = holder.getView(R.id.btn_increase);
        if (position > 2) {
            tvRank.setEnabled(false);
        }
        HomeCurrencyModel item = getDataList().get(position);
        tvRank.setText(String.valueOf(position + 1));
        tvMarketName.setText(item.getName());
        tvMarketTotalValue.setText(item.getVolume());
        tvMarketPrice.setText(item.getPrice());
        btnIncrease.setText(item.getIncrease());
        if (TextUtils.equals("0", item.getState())) {
            btnIncrease.setSelected(false);
        } else {
            btnIncrease.setSelected(true);
        }
    }
}
