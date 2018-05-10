package com.example.admin.basic.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.admin.basic.application.BaseApplication;
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration;

/**
 * @author Xinxin Shi
 */

public class BaseFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public RecyclerView.LayoutManager createLayoutManager(boolean isVertical) {
        if (isVertical) {
            return new LinearLayoutManager(BaseApplication.getInstance());
        }
        return new LinearLayoutManager(BaseApplication.getInstance(), LinearLayoutManager
                .HORIZONTAL, false);
    }

    protected RecyclerView.ItemDecoration createItemDecoration(int colorRes) {
        return new DefaultItemDecoration(ContextCompat.getColor(BaseApplication.getInstance(),
                colorRes));
    }
}
