package com.example.admin.basic.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
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
public abstract class BaseActivity extends AppCompatActivity {
    public static final int MLINE = 0;
    public static final int FIVE_MLINE = 1;
    public static final int DAY_KLINE = 2;
    public static final int WEEK_KLINE = 3;
    public static final int MONTH_KLINE = 4;
    public static final String HS_MARKET = "hs";
    public static final String HK_MARKET = "hk";
    public static final String US_MARKET = "us";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    public abstract void initView();

    public abstract void initData();

    public RecyclerView.LayoutManager createGridLayoutManager(int spanCount) {
        return new GridLayoutManager(BaseApplication.getInstance(), spanCount);
    }

    public RecyclerView.LayoutManager createLinearLayoutManager() {
        return new LinearLayoutManager(BaseApplication.getInstance());
    }

    protected RecyclerView.ItemDecoration createItemDecoration(int colorRes) {
        return new DefaultItemDecoration(ContextCompat.getColor(BaseApplication.getInstance(),
                colorRes));
    }


    /**
     * RecyclerView 移动到当前位置，
     *
     * @param manager 设置RecyclerView对应的manager
     * @param n       要跳转的位置
     */
    public void moveToPosition(LinearLayoutManager manager, int n) {
        int firstCompletelyVisibleItemPosition = manager.findFirstCompletelyVisibleItemPosition();
        int lastCompletelyVisibleItemPosition = manager.findLastCompletelyVisibleItemPosition();
        if (n >= lastCompletelyVisibleItemPosition) {
            manager.scrollToPositionWithOffset(n, 0);
            manager.setStackFromEnd(true);
        }
        if (n <= firstCompletelyVisibleItemPosition) {
            manager.scrollToPositionWithOffset(0, n);
            manager.setStackFromEnd(false);
        }
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

    public List<String> getData() {
        List<String> list = new ArrayList<>();
        list.add("8908.31");
        list.add("8908.31");
        list.add("8908.31");
        list.add("8908.31");
        list.add("8908.3");
        list.add("8908.31");
        return list;
    }

    public void startActivity(Class activityCls) {
        startActivity(new Intent(this, activityCls));
    }
}
