package com.example.admin.basic.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.admin.basic.R;

/**
 * 标题栏工具类
 *
 * @author Xinxin Shi
 */

public class RxToolBar extends RelativeLayout implements View.OnClickListener {

    private ImageView ivBack, ivDown;
    private Activity activity;
    private TextView tvToolbarTitle;
    private TextView tvToolbarContent;
    private String toolbarTitle, toolbarContent;

    public RxToolBar(Context context) {
        super(context);
        init(context, null);
    }

    public RxToolBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RxToolBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RxToolBar);
        toolbarTitle = array.getString(R.styleable.RxToolBar_toolbarTitle);
        toolbarContent = array.getString(R.styleable.RxToolBar_toolbarContent);
        array.recycle();
        activity = (Activity) context;
        View view = LayoutInflater.from(context).inflate(R.layout.common_toolbar, this, false);
        ivBack = (ImageView) view.findViewById(R.id.iv_back);
        ivDown = (ImageView) view.findViewById(R.id.iv_down);
        tvToolbarTitle = (TextView) view.findViewById(R.id.tv_toolbar_name);
        tvToolbarContent = (TextView) view.findViewById(R.id.tv_toolbar_time);
        tvToolbarTitle.setText(toolbarTitle);
        tvToolbarContent.setText(toolbarContent);
        ivBack.setOnClickListener(this);
        addView(view);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_back) {
            activity.finish();
        }
    }

    public void setToolbarTitle(String title) {
        tvToolbarTitle.setText(title);
    }

    public void setTvToolbarContent(String content) {
        tvToolbarContent.setText(content);
    }

    public void setDownVisible(boolean isShow) {
        if (isShow) {
            ivDown.setVisibility(VISIBLE);
        } else {
            ivDown.setVisibility(GONE);
        }
    }
}
