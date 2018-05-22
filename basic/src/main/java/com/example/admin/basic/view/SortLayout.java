package com.example.admin.basic.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.admin.basic.R;
import com.example.admin.basic.utils.DensityUtils;

/**
 * @author Xinxin Shi
 */

public class SortLayout extends LinearLayout implements View.OnClickListener {
    private Context context;
    private RelativeLayout rlPrice;
    private ImageView ivSortPrice;
    private RelativeLayout rlIncrease;
    private ImageView ivSortIncrease;
    private OnSortChangeListener onSortChangeListener;

    public void setOnSortChangeListener(OnSortChangeListener onSortChangeListener) {
        this.onSortChangeListener = onSortChangeListener;
    }

    public SortLayout(Context context) {
        super(context);
        init(context, null);
    }

    public SortLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }


    public SortLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.common_sort_prompt, this, false);
        rlPrice = (RelativeLayout) view.findViewById(R.id.rl_price);
        ivSortPrice = (ImageView) view.findViewById(R.id.iv_sort_price);
        rlIncrease = (RelativeLayout) view.findViewById(R.id.rl_increase);
        ivSortIncrease = (ImageView) view.findViewById(R.id.iv_sort_increase);
        ivSortPrice.setEnabled(false);
        ivSortIncrease.setEnabled(false);
        rlIncrease.setOnClickListener(this);
        rlPrice.setOnClickListener(this);
        addView(view);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, DensityUtils.dp2px(context, 32));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.rl_price) {
            if (ivSortPrice.getVisibility() == GONE || onSortChangeListener == null) {
                return;
            }
            if (ivSortPrice.isEnabled()) {
//                价格降序
                onSortChangeListener.onSortChangeListener(0);
            } else {
//                价格升序
                onSortChangeListener.onSortChangeListener(1);
            }
            ivSortPrice.setEnabled(!ivSortPrice.isEnabled());
        }
        if (id == R.id.rl_increase) {
            if (ivSortIncrease.getVisibility() == GONE || onSortChangeListener == null) {
                return;
            }
            if (ivSortIncrease.isEnabled()) {
                //                涨幅降序
                onSortChangeListener.onSortChangeListener(2);
            } else {
//                涨幅升序
                onSortChangeListener.onSortChangeListener(3);
            }
            ivSortIncrease.setEnabled(!ivSortIncrease.isEnabled());
        }
    }


    public interface OnSortChangeListener {
        /**
         * 排序变化
         *
         * @param order 排序类型，值0为现价降序，1为现价升序，2为涨幅降序，3涨幅升序
         */
        public void onSortChangeListener(int order);
    }
}
