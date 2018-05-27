package com.cf.basketball.fragment.home;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cf.basketball.activity.CurrencyInfoActivity;
import com.cf.basketball.adapter.home.HomeBtcAdapter;
import com.example.admin.basic.base.BaseRecyclerViewFragment;
import com.example.admin.basic.model.HomeCurrencyModel;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;

import java.util.List;

/**
 * BTC
 *
 * @author xinxin Shi
 */
public class HomeBtcFragment extends BaseRecyclerViewFragment {

    private List<HomeCurrencyModel> list;
    private HomeBtcAdapter homeBTCAdapter;

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
        mRecyclerView.refreshComplete(0);
        homeBTCAdapter.notifyDataSetChanged();
    }

    @Override
    public LRecyclerViewAdapter getLRecyclerViewAdapter() {
        homeBTCAdapter = new HomeBtcAdapter(getContext());
//        homeBTCAdapter.setDataList(list);
        return new LRecyclerViewAdapter(homeBTCAdapter);
    }

    @Override
    public void onItemClickListener(int position) {
        startActivity(CurrencyInfoActivity.class);
    }

}
