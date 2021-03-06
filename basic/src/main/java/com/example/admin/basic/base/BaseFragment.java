package com.example.admin.basic.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.admin.basic.application.BaseApplication;
import com.example.admin.basic.model.HomeCurrencyModel;
import com.github.jdsjlzx.ItemDecoration.DividerDecoration;

import java.util.ArrayList;
import java.util.List;
import com.example.admin.basic.R;

/**
 * @author Xinxin Shi
 */

public class BaseFragment extends Fragment {
    public  String token="0";
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

    public RecyclerView.LayoutManager createGridLayoutManager(int spanCount) {
        return new GridLayoutManager(BaseApplication.getInstance(), spanCount);
    }


    protected RecyclerView.ItemDecoration createItemDecoration(int colorRes) {
        return new DividerDecoration.Builder(getContext()).setHeight(R.dimen
                .dp_1)
                .setColorResource(colorRes).build();
    }



    public void startActivity(Class toClass) {
        startActivity(new Intent(BaseApplication.getInstance(), toClass));
    }
    public void startActivity(String id,Class toClass) {
        Intent intent = new Intent(BaseApplication.getInstance(), toClass);
        intent.putExtra("id",id);
        startActivity(intent);
    }
}
