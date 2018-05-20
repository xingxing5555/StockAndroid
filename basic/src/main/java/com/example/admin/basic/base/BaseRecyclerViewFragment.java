package com.example.admin.basic.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.basic.R;
import com.example.admin.basic.application.BaseApplication;
import com.example.admin.basic.view.SortLayout;


/**
 * 基础RecyclerView Fragment
 *
 * @author xinxin Shi
 */
public abstract class BaseRecyclerViewFragment extends BaseFragment {

    private View view;
    public RecyclerView mRecyclerView;
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
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list);
        slSort = (SortLayout) view.findViewById(R.id.sl_sort);
        mRecyclerView.setLayoutManager(createLayoutManager(true));
        mRecyclerView.addItemDecoration(createItemDecoration(R.color.grey_d));
    }

    public abstract void initView();

    public void setSortPromptVisible() {
        slSort.setVisibility(View.VISIBLE);
    }
}
