package com.example.admin.basic.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

/**
 * @author Xinxin Shi
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * RecyclerView 移动到当前位置，
     *
     * @param manager 设置RecyclerView对应的manager
     * @param n       要跳转的位置
     */
    public  void moveToPosition(LinearLayoutManager manager, int n) {
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
}
