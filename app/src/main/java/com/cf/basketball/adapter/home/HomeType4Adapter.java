package com.cf.basketball.adapter.home;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;

import com.cf.basketball.R;
import com.example.admin.basic.model.home.HomeType4Model;
import com.example.admin.basic.utils.CommonUtils;
import com.example.admin.basic.view.ListBaseAdapter;
import com.example.admin.basic.view.SuperViewHolder;

/**
 * @author Xinxin Shi
 */

public class HomeType4Adapter extends ListBaseAdapter<HomeType4Model.DataBean.CoinsBean> {
    private Context context;

    public HomeType4Adapter(Context context) {
        super(context);
        this.context = context;
    }


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
        HomeType4Model.DataBean.CoinsBean item = getDataList().get(position);
        tvHuobiName.setText(item.getName());
        tvHuobiSource.setText(TextUtils.concat("/", item.getChange()));
        tvHuobiVolume.setText(TextUtils.concat(context.getString(R.string.volume), item.getVolume
                ()));
        tvHuobiPrice.setText(item.getPrice1());
        tvHuobiForeignPrice.setText(item.getPrice2());
        btnHuobi.setText(item.getUpdown());
        boolean minus = CommonUtils.isMinus(item.getUpdown());
        btnHuobi.setSelected(minus);
        tvHuobiPrice.setEnabled(minus);
    }
}
