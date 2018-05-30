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
import com.example.admin.basic.model.home.CommonStateModel;
import com.example.admin.basic.model.home.HomeOptionalModel;
import com.example.admin.basic.utils.CommonUtils;
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

public class HomeOptionalAdapter extends ListBaseAdapter<HomeOptionalModel.DataBean.CoinsBean> {

    private View view;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private String token;
    private Context context;

    public HomeOptionalAdapter(Context context, String token) {
        super(context);
        this.context = context;
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
        HomeOptionalModel.DataBean.CoinsBean item = getDataList().get(position);
        TextView tvIncreaseSource = holder.getView(R.id.tv_increase_source);
        TextView tvIncreaseName = holder.getView(R.id.tv_increase_name);
        TextView tvIncreaseVolume = holder.getView(R.id.tv_market);
        TextView tvIncreasePrice = holder.getView(R.id.tv_increase_price);
        TextView tvIncreaseForeignPrice = holder.getView(R.id.tv_increase_foreign_price);
        Button btnIncrease = holder.getView(R.id.btn_increase);
        tvIncreaseName.setText(TextUtils.concat(item.getName(), "/", item.getChange()));
        tvIncreaseSource.setText(item.getMarket());
        tvIncreaseVolume.setText(TextUtils.concat(context.getString(R.string.volume), item
                .getVolume()));
        tvIncreasePrice.setText(item.getPrice1());
        tvIncreaseForeignPrice.setText(item.getPrice2());
        btnIncrease.setText(item.getUpdown());
        boolean minus = CommonUtils.isMinus(item.getUpdown());
        btnIncrease.setSelected(minus);
        tvIncreasePrice.setEnabled(minus);
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
//        String ids = CommonUtils.changeLocation(getDataList(), source.getAdapterPosition(),
//                target.getAdapterPosition());
        String ids = CommonUtils.getIds(getDataList());
        LogUtils.e("ids=" + ids);
        NetManager.getInstance().changeOrder(token, ids, new OnRequestListener() {
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
