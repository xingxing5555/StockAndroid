package com.cf.basketball.fragment.home;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.cf.basketball.R;
import com.cf.basketball.activity.BTCInfoActivity;
import com.cf.basketball.adapter.home.HomeBtcAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.admin.basic.base.BaseRecyclerViewFragment;
import com.example.admin.basic.model.HomeCurrencyModel;

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
        homeBTCAdapter = new HomeBtcAdapter(R.layout.item_home_btc, list);
        mRecyclerView.setAdapter(homeBTCAdapter);
        homeBTCAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(BTCInfoActivity.class);
            }
        });
    }
}
