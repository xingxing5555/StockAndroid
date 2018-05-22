package com.cf.basketball.adapter.search;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cf.basketball.R;
import com.example.admin.basic.model.HomeCurrencyModel;

import java.util.List;

/**
 * 搜索结果适配器
 *
 * @author Xinxin Shi
 */

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private List<HomeCurrencyModel> list;
    private OnSearchListener onSearchListener;

    public void setOnSearchListener(OnSearchListener onSearchListener) {
        this.onSearchListener = onSearchListener;
    }

    public SearchResultAdapter(Context context, @Nullable List<HomeCurrencyModel> data) {
        inflater = LayoutInflater.from(context);
        this.list = data;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_search_result, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        HomeCurrencyModel model = list.get(position);
        holder.tvSearchResultName.setText(model.getName());
        holder.tvSearchResultSource.setText(model.getType());
        holder.tvMarketVolume.setText(model.getVolume());
        holder.tvSearchResultPrice.setText(model.getPrice());
        holder.tvSearchResultForeignPrice.setText(model.getForeignPrice());
        holder.btnSearchResult.setText(model.getIncrease());
        if (TextUtils.equals("0", model.getState())) {
            holder.btnSearchResult.setSelected(false);
            holder.cbSelected.setChecked(false);
        } else {
            holder.btnSearchResult.setSelected(true);
            holder.cbSelected.setChecked(true);
        }
        holder.cbSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (onSearchListener != null) {
                    if (isChecked) {
//                        删除货币
                        onSearchListener.onAddOrSubChangeListener(holder.getAdapterPosition(), 2);
                    } else {
//                        添加货币
                        onSearchListener.onAddOrSubChangeListener(holder.getAdapterPosition(), 1);
                    }
                }

                holder.cbSelected.setChecked(isChecked);
            }
        });
        holder.llSearchResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSearchListener != null) {
                    onSearchListener.onItemClickListener(holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvSearchResultName, tvSearchResultSource, tvMarketVolume,
                tvSearchResultPrice;
        private TextView tvSearchResultForeignPrice;
        private Button btnSearchResult;
        private CheckBox cbSelected;
        private LinearLayout llSearchResult;

        public ViewHolder(View itemView) {
            super(itemView);
            tvSearchResultName = (TextView) itemView.findViewById(R.id.tv_search_result_name);
            tvSearchResultSource = (TextView) itemView.findViewById(R.id.tv_search_result_source);
            tvMarketVolume = (TextView) itemView.findViewById(R.id.tv_market_price);
            tvSearchResultPrice = (TextView) itemView.findViewById(R.id.tv_search_result_price);
            tvSearchResultForeignPrice = (TextView) itemView.findViewById(R.id
                    .tv_search_result_foreign_price);
            btnSearchResult = (Button) itemView.findViewById(R.id.btn_search_result);
            cbSelected = (CheckBox) itemView.findViewById(R.id.cb_selected);
            llSearchResult = (LinearLayout) itemView.findViewById(R.id.ll_search_result);
        }
    }


    public interface OnSearchListener {
        /**
         * item的点击事件
         *
         * @param position 位置
         */
        void onItemClickListener(int position);

        /**
         * 添加/删除的事件
         *
         * @param position 位置
         * @param state    状态 1：添加 ；2：删除
         */
        void onAddOrSubChangeListener(int position, int state);
    }
}
