package com.cf.basketball.fragment.home;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.cf.basketball.R;
import com.cf.basketball.activity.CurrencyInfoActivity;
import com.cf.basketball.adapter.home.HomeIncreaseAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.admin.basic.base.BaseRecyclerViewFragment;
import com.example.admin.basic.model.HomeCurrencyModel;

import java.util.List;

/**
 * 涨幅
 *
 * @author xinxin Shi
 */
public class HomeIncreaseFragment extends BaseRecyclerViewFragment implements BaseQuickAdapter
        .OnItemClickListener {


    private List<HomeCurrencyModel> list;
    private HomeIncreaseAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = createData();
    }

    @Override
    public void initView() {
        adapter = new HomeIncreaseAdapter(R.layout.item_home_increase, list);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        setSortPromptVisible();
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        startActivity(CurrencyInfoActivity.class);
    }
}
