package com.example.admin.basic.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.basic.R;
import com.example.admin.basic.application.BaseApplication;
import com.example.admin.basic.utils.LogUtils;
import com.example.admin.basic.view.SortLayout;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;


/**
 * 基础RecyclerView Fragment
 *
 * @author xinxin Shi
 */
public abstract class BaseRecyclerViewFragment extends BaseFragment {

    private View view;
    public LRecyclerView mRecyclerView;
    public SortLayout slSort;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        view = LayoutInflater.from(BaseApplication.getInstance()).inflate(R.layout
                .common_fragment_list, container, false);
        init();
        initView();
        return view;
    }

    private void init() {
        mRecyclerView = (LRecyclerView) view.findViewById(R.id.rv_list);
        slSort = (SortLayout) view.findViewById(R.id.sl_sort);
        mRecyclerView.setLayoutManager(createLayoutManager(true));
        mRecyclerView.addItemDecoration(createItemDecoration(R.color.grey_d));
        LRecyclerViewAdapter lRecyclerViewAdapter = getLRecyclerViewAdapter();
        mRecyclerView.setAdapter(lRecyclerViewAdapter);
        mRecyclerView.setPullRefreshEnabled(false);
        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                refresh();
            }
        });
        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                LogUtils.e("点击事件已发生");
                onItemClickListener(i);
            }
        });
    }

    public void setRefreshEnable(boolean isEnable) {
        mRecyclerView.setPullRefreshEnabled(isEnable);
    }

    public abstract void initView();

    public abstract void refresh();

    public abstract LRecyclerViewAdapter getLRecyclerViewAdapter();

    public abstract void onItemClickListener(int position);

    public void setSortPromptVisible() {
        slSort.setVisibility(View.VISIBLE);
    }
}
