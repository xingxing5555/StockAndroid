package com.cf.basketball.stock;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.cf.basketball.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by GongWen on 16/10/15.
 */

public class TabIndicatorViewV2 extends View {
    private final float density;

    private OnTabSelectedListener mTabSelectedListener;
    private OnTabSelectedListener mVpSelectedListener;

    private List<String> titles;
    private Paint mPaint;
    private TextPaint mTextPaint;
    private Paint.FontMetrics fm;

    private float itemWidth;
    private int itemCount;
    private float indicatorHeight;
    private int indicatorPadding;

    private int selectedColor;
    private int normalColor;

    private float mRadius;
    private float textSize;

    public static final int TYPE_INDICATOR = 0;//带指示器
    public static final int TYPE_RECT = 1;//带矩形边框
    public static final int TYPE_ROUND_RECT = 2;//带圆角矩形边框
    private int type = TYPE_INDICATOR;

    private int position;//被选中的位置
    private int lastPosition = -1;

    public TabIndicatorViewV2(Context context) {
        this(context, null, 0);
    }

    public TabIndicatorViewV2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabIndicatorViewV2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        density = getResources().getDisplayMetrics().density;
        //抗锯齿,防抖动
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TabIndicatorViewV2);
        type = a.getInt(R.styleable.TabIndicatorViewV2_indicatorType, TYPE_INDICATOR);
        textSize = a.getDimensionPixelSize(R.styleable.TabIndicatorViewV2_textSize, 15);
        indicatorPadding = a.getDimensionPixelSize(R.styleable.TabIndicatorViewV2_indicatorPadding, 0);
        indicatorHeight = a.getDimensionPixelSize(R.styleable.TabIndicatorViewV2_indicatorHeight, 0);

        mRadius = a.getDimensionPixelSize(R.styleable.TabIndicatorViewV2_radius, 15);
        selectedColor = a.getColor(R.styleable.TabIndicatorViewV2_selectedColor, 0xffffffff);
        normalColor = a.getColor(R.styleable.TabIndicatorViewV2_normalColor, 0xff089FE6);

        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = View.resolveSize(0, widthMeasureSpec);
        int height = View.resolveSize(0, heightMeasureSpec);
        height += indicatorHeight;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (titles == null || titles.size() == 0) {
            return;
        }

        itemCount = titles.size();
        mTextPaint.setTextSize(textSize);
        itemWidth = (getWidth() - getPaddingLeft() - getPaddingRight()) * 1.0f / itemCount;
        //画指示器／边框
        drawBorder(canvas);
        //画文案
        fm = mTextPaint.getFontMetrics();
        for (int i = 0; i < itemCount; i++) {
            if (i == position) {
                mTextPaint.setColor(selectedColor);
            } else {
                mTextPaint.setColor(normalColor);
            }
            if (type == TYPE_INDICATOR) {
                canvas.drawText(titles.get(i), getPaddingLeft() + itemWidth * (i + 0.5f),
                        (getHeight() - getPaddingTop() - getPaddingBottom() - indicatorHeight) / 2 - fm.descent + (fm.bottom - fm.top) / 2, mTextPaint);
            } else {
                canvas.drawText(titles.get(i), getPaddingLeft() + itemWidth * (i + 0.5f), (getHeight() - getPaddingTop() - getPaddingBottom()) / 2 - fm.descent + (fm.bottom - fm.top) / 2, mTextPaint);
            }
        }
    }

    private void drawBorder(Canvas canvas) {
        switch (type) {
            case TYPE_INDICATOR:
                drawIndicator(canvas);
                break;
            case TYPE_RECT:
                drawRect(canvas);
                break;
            case TYPE_ROUND_RECT:
                drawRoundRect(canvas);
                break;
        }
    }

    private void drawRoundRect(Canvas canvas) {
        mPaint.setColor(normalColor);
        mPaint.setStyle(Paint.Style.FILL);
        if (itemCount != 1) {
            float[] leftOuterRadii = new float[]{mRadius, mRadius, 0, 0, 0, 0, mRadius, mRadius};
            float[] rightOuterRadii = new float[]{0, 0, mRadius, mRadius, mRadius, mRadius, 0, 0};
            if (position == 0) {
                ShapeDrawable mDrawables = new ShapeDrawable(new RoundRectShape(leftOuterRadii, null, null));
                mDrawables.getPaint().setColor(mPaint.getColor());
                mDrawables.setBounds((int) (position * itemWidth), 0, (int) ((position + 1) * itemWidth), getHeight());
                mDrawables.draw(canvas);
            } else if (position == (itemCount - 1)) {
                ShapeDrawable mDrawables = new ShapeDrawable(new RoundRectShape(rightOuterRadii, null, null));
                mDrawables.getPaint().setColor(mPaint.getColor());
                mDrawables.setBounds((int) (position * itemWidth), 0, (int) ((position + 1) * itemWidth), getHeight());
                mDrawables.draw(canvas);
            } else {
                canvas.drawRect(position * itemWidth, 0, (position + 1) * itemWidth, getHeight(), mPaint);
            }

        } else {
            canvas.drawRoundRect(new RectF(0, 0, getWidth(), getHeight()), mRadius, mRadius, mPaint);
        }

        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRoundRect(new RectF(0, 0, getWidth(), getHeight()), mRadius, mRadius, mPaint);

        for (int i = 1; i < itemCount; i++) {
            canvas.drawLine(getPaddingLeft() + i * itemWidth, getPaddingTop(), getPaddingLeft() + i * itemWidth, getHeight() - getPaddingBottom(), mPaint);
        }
    }

    private void drawRect(Canvas canvas) {
        mPaint.setColor(normalColor);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(position * itemWidth, 0, (position + 1) * itemWidth, getHeight(), mPaint);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(new RectF(0, 0, getWidth(), getHeight()), mPaint);
        for (int i = 1; i < itemCount; i++) {
            canvas.drawLine(getPaddingLeft() + i * itemWidth, getPaddingTop(), getPaddingLeft() + i * itemWidth, getHeight() - getPaddingBottom(), mPaint);
        }
    }

    private void drawIndicator(Canvas canvas) {
        mPaint.setColor(selectedColor);
        mPaint.setStyle(Paint.Style.FILL);
        float indicatorWidth = itemWidth - 2 * indicatorPadding;
        RectF rectF = new RectF();
        rectF.left = getPaddingLeft() + position * itemWidth + indicatorPadding;
        rectF.top = getHeight() - indicatorHeight;
        rectF.right = rectF.left + indicatorWidth;
        rectF.bottom = getHeight();
        canvas.drawRect(rectF, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                position = (int) ((event.getX() - getPaddingLeft()) / itemWidth);
                if (position > 4) {
                    position = 4;
                }
                if (position < 0) {
                    position = 0;
                }
                selectPosition(position, true);
                break;
        }
        return super.onTouchEvent(event);
    }

    public void selectPosition(int position, boolean isTabSelected) {
        if (isTabSelected) {
            if (mVpSelectedListener != null) {
                if (position != lastPosition) {
                    mVpSelectedListener.onTabSelected(position);
                } else {
                    mTabSelectedListener.onTabReselected(position);
                }
            } else if (mTabSelectedListener != null) {//未和ViewPager关联时
                if (position != lastPosition) {
                    mTabSelectedListener.onTabSelected(position);
                } else {
                    mTabSelectedListener.onTabReselected(position);
                }
            }
        } else {
            if (mTabSelectedListener != null) {
                if (position != lastPosition) {
                    mTabSelectedListener.onTabSelected(position);
                } else {
                    mTabSelectedListener.onTabReselected(position);
                }
            }
        }
        this.position = position;
        this.lastPosition = position;
        postInvalidate();
    }

    public void setOnTabSelectedListener(OnTabSelectedListener listener) {
        mTabSelectedListener = listener;
    }

    private void setOnTabSelectedListener(OnTabSelectedListener listener, boolean isTabSelected) {
        if (isTabSelected) {
            mTabSelectedListener = listener;
        } else {
            mVpSelectedListener = listener;
        }
    }

    public interface OnTabSelectedListener {
        void onTabSelected(int position);

        void onTabReselected(int position);
    }

    public void setupWithViewPager(@Nullable ViewPager viewPager) {
        PagerAdapter adapter = viewPager.getAdapter();
        if (adapter == null) {
            throw new IllegalArgumentException("ViewPager does not have a PagerAdapter set");
        } else {
            final int adapterCount = adapter.getCount();
            List<String> titles = new ArrayList<>();
            for (int i = 0; i < adapterCount; i++) {
                CharSequence title = adapter.getPageTitle(i);
                titles.add(title == null ? "" : title.toString());
                setTitles(titles);
            }
            viewPager.addOnPageChangeListener(new TabLayoutOnPageChangeListener(this));
            setOnTabSelectedListener(new ViewPagerOnTabSelectedListener(viewPager), false);
        }
    }

    public static class ViewPagerOnTabSelectedListener implements OnTabSelectedListener {
        private final ViewPager mViewPager;

        public ViewPagerOnTabSelectedListener(ViewPager viewPager) {
            mViewPager = viewPager;
        }

        @Override
        public void onTabSelected(int position) {
            mViewPager.setCurrentItem(position);
        }

        @Override
        public void onTabReselected(int position) {
            // No-op
        }
    }

    public static class TabLayoutOnPageChangeListener implements ViewPager.OnPageChangeListener {
        private final WeakReference<TabIndicatorViewV2> mTabIndicatorViewRef;

        public TabLayoutOnPageChangeListener(TabIndicatorViewV2 tabIndicatorView) {
            this.mTabIndicatorViewRef = new WeakReference(tabIndicatorView);
        }

        public void onPageScrollStateChanged(int state) {
        }

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        public void onPageSelected(int position) {
            TabIndicatorViewV2 tabIndicatorView = this.mTabIndicatorViewRef.get();
            if (tabIndicatorView != null) {
                tabIndicatorView.selectPosition(position, false);
            }

        }
    }

    public void setTabTextColors(int normalColor, int selectedColor) {
        this.normalColor = normalColor;
        this.selectedColor = selectedColor;
        postInvalidate();
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
        postInvalidate();
    }

    public void setIndicatorHeight(float indicatorHeight) {
        this.indicatorHeight = indicatorHeight * density;
        requestLayout();//重新执行onMeasure－>onLayout->onDraw
    }

    public void setIndicatorPadding(int indicatorPadding) {
        this.indicatorPadding = (int) (indicatorPadding * density);
        postInvalidate();
    }

    public void setRadius(float mRadius) {
        this.mRadius = mRadius * density;
        postInvalidate();
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize * density;
        postInvalidate();
    }

    public void setType(int type) {
        this.type = type;
        requestLayout();
    }
}
