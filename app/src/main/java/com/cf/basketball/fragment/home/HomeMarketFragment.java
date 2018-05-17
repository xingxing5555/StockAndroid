package com.cf.basketball.fragment.home;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cf.basketball.R;
import com.cf.basketball.adapter.home.HomeMarketAdapter;
import com.example.admin.basic.base.BaseRecyclerViewFragment;
import com.example.admin.basic.model.HomeCurrencyModel;

import java.util.List;

/**
 * 市值
 *
 * @author xinxin Shi
 */
public class HomeMarketFragment extends BaseRecyclerViewFragment {


    private List<HomeCurrencyModel> list;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = createData();
    }


    @Override
    public void initView() {
        mRecyclerView.setAdapter(new HomeMarketAdapter(R.layout.item_home_market, list));
    }


}
