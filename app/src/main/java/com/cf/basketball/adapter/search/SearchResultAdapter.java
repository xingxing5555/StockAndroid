package com.cf.basketball.adapter.search;

import android.content.Context;
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
import com.example.admin.basic.constants.Constants;
import com.example.admin.basic.model.search.SearchModel;
import com.example.admin.basic.utils.CommonUtils;
import com.example.admin.basic.utils.LogUtils;

import java.util.List;

/**
 * 搜索结果适配器
 *
 * @author Xinxin Shi
 */

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private List<SearchModel.DataBean.CoinsBean> list;
    private OnSearchListener onSearchListener;

    public void setOnSearchListener(OnSearchListener onSearchListener) {
        this.onSearchListener = onSearchListener;
    }

    public SearchResultAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    public void setList(List<SearchModel.DataBean.CoinsBean> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_search_result, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        SearchModel.DataBean.CoinsBean model = list.get(position);
        holder.tvSearchResultName.setText(model.getName());
        holder.tvSearchResultCurrency.setText(TextUtils.concat("/", model.getCurrency()));
        holder.tvMarket.setText(model.getMarket());
        holder.tvSearchResultPrice.setText(model.getPrice1());
        holder.tvSearchResultForeignPrice.setText(model.getPrice2());
        holder.btnSearchResult.setText(model.getRate());
        String updown = model.getRate();
        boolean minus = CommonUtils.isMinus(updown);
        holder.btnSearchResult.setSelected(minus);
        holder.tvSearchResultPrice.setEnabled(minus);
        holder.cbSelected.setChecked(model.isIsSelfSelected());
        holder.cbSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                LogUtils.e("监听一直细长的女                                 ");
                if (onSearchListener != null) {
                    LogUtils.e("已执行");
                    if (isChecked) {
//                        删除货币
                        onSearchListener.onAddOrSubChangeListener(holder.getAdapterPosition(),
                                Constants.EVENT_DEL);
                    } else {
//                        添加货币
                        onSearchListener.onAddOrSubChangeListener(holder.getAdapterPosition(),
                                Constants.EVENT_ADD);
                    }
                } else {
                    LogUtils.e("onSearchListener is null");
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
        private TextView tvSearchResultName, tvSearchResultCurrency, tvMarket, tvSearchResultPrice;
        private TextView tvSearchResultForeignPrice;
        private Button btnSearchResult;
        private CheckBox cbSelected;
        private LinearLayout llSearchResult;

        public ViewHolder(View itemView) {
            super(itemView);
            tvSearchResultName = (TextView) itemView.findViewById(R.id.tv_search_result_name);
            tvSearchResultCurrency = (TextView) itemView.findViewById(R.id
                    .tv_search_result_currency);
            tvMarket = (TextView) itemView.findViewById(R.id.tv_market);
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
         * @param event    状态 1：添加 ；2：删除
         */
        void onAddOrSubChangeListener(int position, String event);
    }
}
