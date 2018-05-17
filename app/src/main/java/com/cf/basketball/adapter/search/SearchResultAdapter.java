package com.cf.basketball.adapter.search;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;

import com.cf.basketball.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.admin.basic.model.HomeCurrencyModel;
import com.example.admin.basic.utils.LogUtils;

import java.util.List;

/**
 * 搜索结果适配器
 *
 * @author Xinxin Shi
 */

public class SearchResultAdapter extends BaseQuickAdapter<HomeCurrencyModel, BaseViewHolder> {

    private CheckBox cbSelected;

    public SearchResultAdapter(int layoutResId, @Nullable List<HomeCurrencyModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeCurrencyModel item) {
        helper.setText(R.id.tv_search_result_name, item.getName());
        helper.setText(R.id.tv_search_result_source, item.getType());
        helper.setText(R.id.tv_market_volume, item.getVolume());
        helper.setText(R.id.tv_search_result_price, item.getPrice());
        helper.setText(R.id.tv_search_result_foreign_price, item.getForeignPrice());
        helper.setText(R.id.btn_search_result, item.getIncrease());
        cbSelected = (CheckBox) helper.itemView.findViewById(R.id.cb_selected);
        if (TextUtils.equals("0", item.getState())) {
            helper.itemView.findViewById(R.id.btn_search_result).setSelected(false);
            cbSelected.setChecked(false);
        } else {
            helper.itemView.findViewById(R.id.btn_search_result).setSelected(true);
            cbSelected.setChecked(true);
        }
        cbSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbSelected.isChecked()) {
                    LogUtils.e("加入列表" + true);
                    cbSelected.setChecked(false);
                } else {
                    LogUtils.e("减出列表");
                    cbSelected.setChecked(false);
                }
            }
        });
    }
}
