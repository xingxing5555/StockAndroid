package com.cf.basketball.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cf.basketball.R;
import com.cf.basketball.adapter.HomeCurrencyAdapter;
import com.example.admin.basic.base.BaseRecyclerViewFragment;
import com.example.admin.basic.model.HomeCurrencyModel;

import java.util.List;

/**
 * BTC
 *
 * @author xinxin Shi
 */
public class HomeBTCFragment extends BaseRecyclerViewFragment {

    private List<HomeCurrencyModel> list;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = createData();
    }

    @Override
    public void initView() {
        mRecyclerView.setAdapter(new HomeCurrencyAdapter(R.layout.item_home_currency, list));
    }
}
