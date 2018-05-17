package com.cf.basketball.fragment.home;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cf.basketball.R;
import com.cf.basketball.adapter.home.HomeIncreaseAdapter;
import com.example.admin.basic.base.BaseRecyclerViewFragment;
import com.example.admin.basic.model.HomeCurrencyModel;

import java.util.List;

/**
 * 涨幅
 *
 * @author xinxin Shi
 */
public class HomeIncreaseFragment extends BaseRecyclerViewFragment {


    private List<HomeCurrencyModel> list;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = createData();
    }

    @Override
    public void initView() {
        mRecyclerView.setAdapter(new HomeIncreaseAdapter(R.layout.item_home_increase, list));
        setSortPromptVisible();
    }


}
