package com.cf.basketball.adapter.home;

import android.content.Context;
import android.widget.Button;
import android.widget.TextView;

import com.cf.basketball.R;
import com.example.admin.basic.model.home.HomeUpDownModel;
import com.example.admin.basic.utils.CommonUtils;
import com.example.admin.basic.view.ListBaseAdapter;
import com.example.admin.basic.view.SuperViewHolder;

/**
 * 涨幅列表
 *
 * @author Xinxin Shi
 */

public class HomeUpDownAdapter extends ListBaseAdapter<HomeUpDownModel.DataBean.CoinsBean> {
    public HomeUpDownAdapter(Context context) {
        super(context);
    }


    @Override
    public int getLayoutId() {
        return R.layout.item_home_up_down;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        TextView tvIncreaseName = holder.getView(R.id.tv_increase_name);
        TextView tvIncreaseSource = holder.getView(R.id.tv_increase_source);
        TextView tvIncreasePrice = holder.getView(R.id.tv_increase_price);
        TextView tvIncreaseForeignPrice = holder.getView(R.id.tv_increase_foreign_price);
        Button btnIncrease = holder.getView(R.id.btn_increase);
        HomeUpDownModel.DataBean.CoinsBean item = getDataList().get(position);
        tvIncreaseName.setText(item.getMarket());
        tvIncreaseSource.setText(item.getName());
        tvIncreasePrice.setText(item.getPrice1());
        tvIncreaseForeignPrice.setText(item.getPrice2());
        btnIncrease.setText(item.getUpdown());
        boolean plus = CommonUtils.isMinus(item.getUpdown());
        btnIncrease.setSelected(plus);
        tvIncreasePrice.setEnabled(plus);
    }
}
