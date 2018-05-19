package com.example.admin.basic.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.admin.basic.R;
import com.example.admin.basic.utils.DensityUtils;
import com.example.admin.basic.utils.LogUtils;

/**
 * 百分比View
 *
 * @author Xinxin Shi
 */

public class PercentageView extends RelativeLayout {

    private Drawable defaultPercentBackground, percentBackground;
    private String percentValue, defaultText;
    double percentWidth;
    private View view;

    public PercentageView(Context context) {
        super(context);
        init(context, null);
    }

    public PercentageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }


    public PercentageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.PercentageView);
        defaultPercentBackground = array.getDrawable(R.styleable.PercentageView_defaultBackground);
        percentBackground = array.getDrawable(R.styleable.PercentageView_percentBackground);
        percentValue = array.getString(R.styleable.PercentageView_percentValue);
        defaultText = array.getString(R.styleable.PercentageView_defaultText);
        array.recycle();
        setBackgroundDrawable(defaultPercentBackground);
        String[] split = percentValue.split("%");
        percentWidth = Double.parseDouble(split[0]);
        view = new View(context);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams
                .MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        view.setBackgroundDrawable(percentBackground);
        addView(view);
        TextView percentTextView = new TextView(context);
        percentTextView.setTextColor(context.getResources().getColor(R.color.white));
        percentTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
        percentTextView.setGravity(Gravity.CENTER);
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams
                .WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rlp.leftMargin = DensityUtils.dp2px(context, 15);
        percentTextView.setLayoutParams(rlp);
        addView(percentTextView);
        if (!TextUtils.isEmpty(percentValue)) {
            percentTextView.setText(percentValue);
        }
        TextView defaultTextView = new TextView(context);
        defaultTextView.setTextColor(context.getResources().getColor(R.color.white));
        defaultTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
        defaultTextView.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        RelativeLayout.LayoutParams rlp2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams
                .MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rlp2.rightMargin = DensityUtils.dp2px(context, 15);
        defaultTextView.setLayoutParams(rlp2);
        addView(defaultTextView);
        if (!TextUtils.isEmpty(defaultText)) {
            defaultTextView.setText(defaultText);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) view.getLayoutParams();
        int i = (int) ((getMeasuredWidth() * percentWidth) / 100);
        lp.width = i;
        lp.height = getMeasuredHeight() - 1;
        view.setLayoutParams(lp);
    }
}
