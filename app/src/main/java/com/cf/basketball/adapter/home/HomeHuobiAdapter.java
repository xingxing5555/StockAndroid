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
 * @author Xinxin Shi
 */

public class HomeHuobiAdapter  extends ListBaseAdapter<HomeCurrencyModel> {
    public HomeHuobiAdapter(Context context) {
        super(context);
    }

//    public HomeHuobiAdapter(int layoutResId, @Nullable List<HomeCurrencyModel> data) {
//        super(layoutResId, data);
//    }
//
//    @Override
//    protected void convert(BaseViewHolder helper, HomeCurrencyModel item) {
//        helper.setText(R.id.tv_huobi_source, item.getName());
//        helper.setText(R.id.tv_huobi_name, item.getType());
//        helper.setText(R.id.tv_market_volume, item.getVolume());
//        helper.setText(R.id.tv_huobi_price, item.getPrice());
//        helper.setText(R.id.tv_huobi_foreign_price, item.getForeignPrice());
//        helper.setText(R.id.btn_huobi, item.getIncrease());
//        if (TextUtils.equals("0", item.getState())) {
//            helper.itemView.findViewById(R.id.btn_huobi).setSelected(false);
//        } else {
//            helper.itemView.findViewById(R.id.btn_huobi).setSelected(true);
//        }
//    }

    @Override
    public int getLayoutId() {
        return R.layout.item_home_huobi;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        TextView tvHuobiName = holder.getView(R.id.tv_huobi_name);
        TextView tvHuobiSource = holder.getView(R.id.tv_huobi_source);
        TextView tvHuobiVolume = holder.getView(R.id.tv_market);
        TextView tvHuobiPrice = holder.getView(R.id.tv_huobi_price);
        TextView tvHuobiForeignPrice = holder.getView(R.id.tv_huobi_foreign_price);
        Button btnHuobi = holder.getView(R.id.btn_huobi);
        HomeCurrencyModel item = getDataList().get(position);
        tvHuobiName.setText(item.getName());
        tvHuobiSource.setText(item.getType());
        tvHuobiVolume.setText(item.getVolume());
        tvHuobiPrice.setText(item.getPrice());
        tvHuobiForeignPrice.setText(item.getForeignPrice());
        btnHuobi.setText(item.getIncrease());
        if (TextUtils.equals("0", item.getState())) {
            btnHuobi.setSelected(false);
        } else {
            btnHuobi.setSelected(true);
        }
    }
}
