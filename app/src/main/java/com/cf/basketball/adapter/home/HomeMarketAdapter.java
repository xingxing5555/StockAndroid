package com.cf.basketball.adapter.home;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;

import com.cf.basketball.R;
import com.example.admin.basic.constants.Constants;
import com.example.admin.basic.model.home.HomeMarketModel;
import com.example.admin.basic.utils.CommonUtils;
import com.example.admin.basic.view.ListBaseAdapter;
import com.example.admin.basic.view.SuperViewHolder;

/**
 * 市值列表
 *
 * @author Xinxin Shi
 */

public class HomeMarketAdapter extends ListBaseAdapter<HomeMarketModel.DataBean.CoinsBean> {

    public HomeMarketAdapter(Context context) {
        super(context);
    }


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_home_market;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        TextView tvRank = holder.getView(R.id.tv_rank);
        TextView tvMarketName = holder.getView(R.id.tv_market_name);
        TextView tvMarketTotalValue = holder.getView(R.id.tv_market_total_value);
        TextView tvMarketPrice = holder.getView(R.id.tv_market);
        Button btnIncrease = holder.getView(R.id.btn_increase);
        if (position > 2) {
            tvRank.setEnabled(false);
        } else {
            tvRank.setEnabled(true);
        }
        HomeMarketModel.DataBean.CoinsBean bean = getDataList().get(position);
        tvRank.setText(String.valueOf(position + 1));
        tvMarketName.setText(bean.getName());
        tvMarketTotalValue.setText(TextUtils.concat(Constants.SIGN_MONEY, bean.getValue()));
        tvMarketPrice.setText(TextUtils.concat(Constants.SIGN_MONEY, bean.getPrice()));
        btnIncrease.setText(bean.getUpdown());
        boolean minus = CommonUtils.isMinus(bean.getUpdown());
        btnIncrease.setSelected(minus);
        tvMarketPrice.setEnabled(minus);
    }
}
