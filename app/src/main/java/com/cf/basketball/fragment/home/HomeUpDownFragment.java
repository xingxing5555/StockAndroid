package com.cf.basketball.fragment.home;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cf.basketball.activity.CurrencyInfoActivity;
import com.cf.basketball.adapter.home.HomeUpDownAdapter;
import com.cf.basketball.net.NetManager;
import com.example.admin.basic.base.BaseRecyclerViewFragment;
import com.example.admin.basic.interfaces.OnRequestListener;
import com.example.admin.basic.model.HomeCurrencyModel;
import com.example.admin.basic.utils.LogUtils;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;

import java.util.List;

/**
 * 涨幅
 *
 * @author xinxin Shi
 */
public class HomeUpDownFragment extends BaseRecyclerViewFragment implements OnRequestListener {


    private List<HomeCurrencyModel> list;
    private HomeUpDownAdapter adapter;
    private int pageNum = 1;
    private int order = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = createData();
        downData();
    }


    @Override
    public void initView() {
        setSortPromptVisible();
    }

    @Override
    public void refresh() {
        pageNum++;
        downData();
        list.addAll(0, createData());
        adapter.setDataList(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public LRecyclerViewAdapter getLRecyclerViewAdapter() {
        adapter = new HomeUpDownAdapter(getContext());
        adapter.setDataList(list);
        return new LRecyclerViewAdapter(adapter);
    }

    @Override
    public void onItemClickListener(int position) {
        startActivity(CurrencyInfoActivity.class);
    }

    private void downData() {
        NetManager.getInstance().getUpDown(pageNum, order, this);
    }

    @Override
    public void onResponse(String json) {
        mRecyclerView.refreshComplete(0);
        LogUtils.e("涨幅：" + json);
    }

    @Override
    public void onRequestFailure(String errorMsg) {
        LogUtils.e(errorMsg);
        mRecyclerView.refreshComplete(0);
    }
}
