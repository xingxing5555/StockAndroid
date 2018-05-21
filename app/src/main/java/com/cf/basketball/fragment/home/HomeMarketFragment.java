package com.cf.basketball.fragment.home;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.cf.basketball.R;
import com.cf.basketball.activity.BTCInfoActivity;
import com.cf.basketball.adapter.home.HomeMarketAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.admin.basic.base.BaseRecyclerViewFragment;
import com.example.admin.basic.model.HomeCurrencyModel;

import java.util.List;

/**
 * 市值
 *
 * @author xinxin Shi
 */
public class HomeMarketFragment extends BaseRecyclerViewFragment implements BaseQuickAdapter
        .OnItemClickListener {


    private List<HomeCurrencyModel> list;
    private HomeMarketAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = createData();
    }


    @Override
    public void initView() {
        adapter = new HomeMarketAdapter(R.layout.item_home_market, list);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        startActivity(BTCInfoActivity.class);
    }
}
