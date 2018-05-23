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
 * 涨幅列表
 *
 * @author Xinxin Shi
 */

public class HomeUpDownAdapter extends ListBaseAdapter<HomeCurrencyModel> {
    public HomeUpDownAdapter(Context context) {
        super(context);
    }

//    public HomeUpDownAdapter(int layoutResId, @Nullable List<HomeCurrencyModel> data) {
//        super(layoutResId, data);
//    }
//
//    @Override
//    protected void convert(BaseViewHolder helper, HomeCurrencyModel item) {
//        helper.setText(R.id.tv_increase_name, item.getType());
//        helper.setText(R.id.tv_increase_source, item.getName());
//        helper.setText(R.id.tv_increase_price, item.getPrice());
//        helper.setText(R.id.tv_increase_foreign_price, item.getForeignPrice());
//        helper.setText(R.id.btn_increase, item.getIncrease());
//        if (TextUtils.equals("0", item.getState())) {
//            helper.itemView.findViewById(R.id.btn_increase).setSelected(false);
//        } else {
//            helper.itemView.findViewById(R.id.btn_increase).setSelected(true);
//        }
//    }


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
        HomeCurrencyModel item = getDataList().get(position);
        tvIncreaseName.setText(item.getName());
        tvIncreaseSource.setText(item.getType());
        tvIncreasePrice.setText(item.getPrice());
        tvIncreaseForeignPrice.setText(item.getForeignPrice());
        btnIncrease.setText(item.getIncrease());
        if (TextUtils.equals("0", item.getState())) {
            btnIncrease.setSelected(false);
        } else {
            btnIncrease.setSelected(true);
        }
    }
}
