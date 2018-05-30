package com.example.admin.basic.base;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ScrollView;

import com.example.admin.basic.R;
import com.example.admin.basic.application.BaseApplication;
import com.github.jdsjlzx.ItemDecoration.DividerDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Xinxin Shi
 */
public abstract class BaseActivity extends AppCompatActivity {
    public static final String token = "0";
    public static final int MLINE = 0;
    public static final int DAY_KLINE = 1;
    public static final int WEEK_KLINE = 2;
    public static final int MONTH_KLINE = 3;
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
        return new DividerDecoration.Builder(this).setHeight(R.dimen.dp_1).setColorResource
                (colorRes).build();
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

    public void startActivity(String id, Class activityCls) {
        Intent intent = new Intent(this, activityCls);
        intent.putExtra("id",id);
        startActivity(intent);
    }

    public Bitmap shotScrollView(ScrollView scrollView) {
        int h = 0;
        Bitmap bitmap = null;
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
            scrollView.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));
        }
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h, Bitmap.Config.RGB_565);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        return bitmap;
    }
}
