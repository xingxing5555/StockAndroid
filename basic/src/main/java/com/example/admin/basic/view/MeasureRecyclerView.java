package com.example.admin.basic.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * @author Xinxin Shi
 */

public class MeasureRecyclerView extends RecyclerView {
    public MeasureRecyclerView(Context context) {
        super(context);
    }

    public MeasureRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MeasureRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthSpec, expandSpec);
    }
}
