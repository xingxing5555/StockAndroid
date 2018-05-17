package com.cf.basketball.adapter.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cf.basketball.R;
import com.example.admin.basic.application.BaseApplication;

import java.util.List;

/**
 * 货币详情页第二行右侧数据的列表
 *
 * @author Xinxin Shi
 */

public class HomeCurrencyInfoDataAdapter extends RecyclerView.Adapter<HomeCurrencyInfoDataAdapter
        .ViewHolder> {
    private final String[] stringArray;
    private List<String> dataList;

    public HomeCurrencyInfoDataAdapter(Context context, List<String> dataList) {
        stringArray = context.getResources().getStringArray(R.array.info_data_name);
        this.dataList = dataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(BaseApplication.getInstance()).inflate(R.layout
                .item_currency_info_data, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String value = dataList.get(position);
        holder.tvData.setText(TextUtils.concat(stringArray[position], "\t", value));
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvData;

        ViewHolder(View itemView) {
            super(itemView);
            tvData = (TextView) itemView.findViewById(R.id.tv_data);
        }
    }
}
