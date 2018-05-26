package com.cf.basketball.fragment.home;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cf.basketball.activity.BtcInfoActivity;
import com.cf.basketball.adapter.home.HomeMarketAdapter;
import com.example.admin.basic.base.BaseRecyclerViewFragment;
import com.example.admin.basic.model.HomeCurrencyModel;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;

import java.util.List;

/**
 * 市值
 *
 * @author xinxin Shi
 */
public class HomeMarketFragment extends BaseRecyclerViewFragment {


    private List<HomeCurrencyModel> list;
    private HomeMarketAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = createData();
    }


    @Override
    public void initView() {
    }

    @Override
    public void refresh() {
        list.addAll(0, createData());
        adapter.notifyDataSetChanged();
        mRecyclerView.refreshComplete(0);
    }

    @Override
    public LRecyclerViewAdapter getLRecyclerViewAdapter() {
        adapter = new HomeMarketAdapter(getContext());
        adapter.setDataList(list);
        return new LRecyclerViewAdapter(adapter);
    }

    @Override
    public void onItemClickListener(int position) {
        startActivity(BtcInfoActivity.class);
    }

}
