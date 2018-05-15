package com.example.admin.basic.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.admin.basic.R;
import com.example.admin.basic.interfaces.OnItemClickListener;
import com.example.admin.basic.utils.LogUtils;

/**
 * 切换View
 *
 * @author Xinxin Shi
 */

public class SwitchLayout extends RelativeLayout implements View.OnClickListener {

    private TextView firstView, secondView, thirdView, selectedView;
    private View firstLine, secondLine, thirdLine, selectedLine;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public SwitchLayout(Context context) {
        super(context);
        init(context);
    }


    public SwitchLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SwitchLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.common_switch_title, this, false);
        firstView = (TextView) view.findViewById(R.id.first_text);
        secondView = (TextView) view.findViewById(R.id.second_text);
        thirdView = (TextView) view.findViewById(R.id.third_text);
        firstLine = view.findViewById(R.id.first_line);
        secondLine = view.findViewById(R.id.second_line);
        thirdLine = view.findViewById(R.id.third_line);
        firstView.setOnClickListener(this);
        secondView.setOnClickListener(this);
        thirdView.setOnClickListener(this);
        selectedLine = firstLine;
        selectedView = firstView;
        addView(view);
    }
//
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int width = measureWidth(widthMeasureSpec);
//        int height = measureHeight(heightMeasureSpec);
//        int size = MeasureSpec.getSize(widthMeasureSpec);
//        int size1 = MeasureSpec.getSize(heightMeasureSpec);
//        LogUtils.e("width=" + size + ";height=" + size1);
//        setMeasuredDimension(size, size1);
//        super.onMeasure(size,size1);
////        int measuredWidth = getMeasuredWidth();
////        int measuredHeight = getMeasuredHeight();
////        LogUtils.e("width=" + width + ";height=" + height + ";measureHight=" + measuredHeight +
////                ";measureWidth=" + measuredWidth);
//
//    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.first_text) {
            setViewState(firstView, firstLine, 0);
        }
        if (id == R.id.second_text) {
            setViewState(secondView, secondLine, 1);
        }
        if (id == R.id.third_text) {
            setViewState(thirdView, thirdLine, 2);
        }
    }

    private void setViewState(TextView textView, View lineView, int position) {
        if (selectedLine != null && selectedView != null) {
            selectedLine.setVisibility(INVISIBLE);
            selectedView.setEnabled(true);
        }
        selectedView = textView;
        selectedLine = lineView;
        textView.setEnabled(false);
        lineView.setVisibility(VISIBLE);
        if (onItemClickListener != null) {
            onItemClickListener.onItemClickListener(position);
        }
    }
}
