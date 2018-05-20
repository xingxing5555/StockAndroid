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
        if(id==R.id.rl_price){
            if (ivSortPrice.getVisibility() == GONE||onSortChangeListener==null) {
                return;
            }
            if(ivSortPrice.isEnabled()){
                    onSortChangeListener.onSortChangeListener(1,1);
            }else{
                onSortChangeListener.onSortChangeListener(1,2);
            }
            ivSortPrice.setEnabled(!ivSortPrice.isEnabled());
        }
        if(id==R.id.rl_increase){
            if (ivSortIncrease.getVisibility() == GONE||onSortChangeListener==null) {
                return;
            }
            if(ivSortIncrease.isEnabled()){
                    onSortChangeListener.onSortChangeListener(1,1);
            }else{
                onSortChangeListener.onSortChangeListener(1,2);
            }
            ivSortIncrease.setEnabled(!ivSortIncrease.isEnabled());
        }
    }


    public interface OnSortChangeListener {
        /**
         * 排序变化
         *
         * @param type        1: 价格 2：涨幅
         * @param changeState 1：升 2：降
         */
        public void onSortChangeListener(int type, int changeState);
    }
}
