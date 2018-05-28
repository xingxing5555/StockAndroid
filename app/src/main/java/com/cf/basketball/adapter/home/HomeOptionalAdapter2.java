package com.cf.basketball.adapter.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cf.basketball.R;
import com.cf.basketball.net.NetManager;
import com.example.admin.basic.constants.Constants;
import com.example.admin.basic.interfaces.OnRequestListener;
import com.example.admin.basic.model.HomeCurrencyModel;
import com.example.admin.basic.model.home.CommonStateModel;
import com.example.admin.basic.utils.LogUtils;
import com.example.admin.basic.utils.ToastUtils;
import com.example.admin.basic.view.ListBaseAdapter;
import com.example.admin.basic.view.SuperViewHolder;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.google.gson.Gson;

import java.util.Collections;

/**
 * 自选列表
 *
 * @author Xinxin Shi
 */

public class HomeOptionalAdapter2 extends ListBaseAdapter<HomeCurrencyModel> {

    private View view;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private String token;

    public HomeOptionalAdapter2(Context context, String token) {
        super(context);
        this.token = token;
    }

    public void setmLRecyclerViewAdapter(LRecyclerViewAdapter mLRecyclerViewAdapter) {
        this.mLRecyclerViewAdapter = mLRecyclerViewAdapter;
    }


    @Override
    public int getLayoutId() {
        return R.layout.item_home_optional;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        HomeCurrencyModel item = getDataList().get(position);
        TextView tvIncreaseSource = (TextView) holder.getView(R.id.tv_increase_source);
        TextView tvIncreaseName = (TextView) holder.getView(R.id.tv_increase_name);
        TextView tvIncreaseVolume = (TextView) holder.getView(R.id.tv_market);
        TextView tvIncreasePrice = (TextView) holder.getView(R.id.tv_increase_price);
        TextView tvIncreaseForeignPrice = (TextView) holder.getView(R.id.tv_increase_foreign_price);
        Button btnIncrease = (Button) holder.getView(R.id.btn_increase);
        tvIncreaseSource.setText(item.getName());
        tvIncreaseName.setText(item.getType());
        tvIncreaseVolume.setText(item.getVolume());
        tvIncreasePrice.setText(item.getPrice());
        tvIncreaseForeignPrice.setText(item.getForeignPrice());
        btnIncrease.setText(item.getIncrease());
        if (TextUtils.equals("0", item.getState())) {
            btnIncrease.setSelected(false);
        } else {
            btnIncrease.setSelected(true);
        }

    }

    public void onItemDragMoving(RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {
        int from = getViewHolderPosition(source);
        int to = getViewHolderPosition(target);
        LogUtils.e("onItemDragMoving from " + from + "   to  " + to);
        if (from < to) {
            for (int i = from; i < to; i++) {
                Collections.swap(getDataList(), i, i + 1);
            }
        } else {
            for (int i = from; i > to; i--) {
                Collections.swap(getDataList(), i, i - 1);
            }
        }

        mLRecyclerViewAdapter.notifyItemMoved(source.getAdapterPosition(), target
                .getAdapterPosition());

        NetManager.getInstance().changeOrder(token, "36", new OnRequestListener() {
            @Override
            public void onResponse(String tag, String json) {
                CommonStateModel model = new Gson().fromJson(json, CommonStateModel.class);
                if (model == null || model.getCode() != Constants.NET_REQUEST_SUCCESS_CODE) {
                    return;
                }
                ToastUtils.toastShot(mContext, mContext.getString(R.string.success));
            }

            @Override
            public void onRequestFailure(String errorMsg) {

            }
        });
    }

    public int getViewHolderPosition(RecyclerView.ViewHolder viewHolder) {
        return viewHolder.getAdapterPosition() - (mLRecyclerViewAdapter.getHeaderViewsCount() + 1);
    }

}
