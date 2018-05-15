package com.example.admin.basic.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.admin.basic.application.BaseApplication;
import com.example.admin.basic.model.HomeCurrencyModel;
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration;

import java.util.ArrayList;
import java.util.List;

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


    public List<HomeCurrencyModel> createData() {
        List<HomeCurrencyModel> list = new ArrayList<>();
        list.add(new HomeCurrencyModel("火币", "BTC/USD", "¥56352.54", "8856.825",
                "交易量27163.070BT", "-4.81%", "0"));
        list.add(new HomeCurrencyModel("火币", "BTC/USD", "¥56352.54", "8856.825",
                "交易量27163.070BT", "-4.81%", "1"));
        list.add(new HomeCurrencyModel("火币", "BTC/USD", "¥56352.54", "8856.825",
                "交易量27163.070BT", "-4.81%", "1"));
        return list;
    }

    public void startActivity(Class toClass) {
        startActivity(new Intent(BaseApplication.getInstance(), toClass));
    }
}
