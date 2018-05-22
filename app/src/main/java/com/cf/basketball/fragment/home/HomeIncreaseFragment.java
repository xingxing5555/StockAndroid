package com.cf.basketball.fragment.home;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cf.basketball.activity.CurrencyInfoActivity;
import com.cf.basketball.adapter.home.HomeIncreaseAdapter;
import com.example.admin.basic.base.BaseRecyclerViewFragment;
import com.example.admin.basic.model.HomeCurrencyModel;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;

import java.util.List;

/**
 * 涨幅
 *
 * @author xinxin Shi
 */
public class HomeIncreaseFragment extends BaseRecyclerViewFragment {


    private List<HomeCurrencyModel> list;
    private HomeIncreaseAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = createData();
    }

    @Override
    public void initView() {
        setSortPromptVisible();
    }

    @Override
    public void refresh() {
        list.addAll(0, createData());
        mRecyclerView.refreshComplete(0);
        adapter.notifyDataSetChanged();

    }

    @Override
    public LRecyclerViewAdapter getLRecyclerViewAdapter() {
        adapter = new HomeIncreaseAdapter(getContext());
        adapter.setDataList(list);
        return new LRecyclerViewAdapter(adapter);
    }

    @Override
    public void onItemClickListener(int position) {
        startActivity(CurrencyInfoActivity.class);
    }

}
