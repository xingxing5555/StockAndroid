package com.example.admin.basic.stock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

;

/**
 * Created by Administrator on 2016/8/22.
 */
public abstract class StockLineView extends View implements ILineScatter {
    private String TAG = "StockLineView";
    protected final int TYPE_MLINE = 0;
    protected final int TYPE_KLINE = 1;
    protected int mainType = -1;
    protected boolean isOneDayMLine;
    private Paint highlightPaint;
    private Paint highlightCirclePaint;
    private Path mPath;
    private DashPathEffect mHighlightDashPathEffect;
    private boolean mDrawVerticalHighlightIndicator = false;
    private boolean mDrawHorizontalHighlightIndicator = false;
    private boolean isShowHighLightIndicator = true;
    //    private int mHighLightColor = Color.rgb(255, 255, 255);
    private int mHighLightColor =0xff999999;
    private float mHighlightLineWidth = 1f;
    protected float startX, startY, endX, endY;
    protected float volumeStartY;//量图的顶部Y值
    protected float volumeEndY;//量图的底部Y值
    public float screenW;
    public float screenH;
    private float currentX, currentY;
    private int index;

    protected float mOffsetX;
    public static final int K_BOTTOM_TYPE_CJL = 0;
    public static final int K_BOTTOM_TYPE_KDJ = 1;
    public static final int K_BOTTOM_TYPE_MACD = 2;
    protected int K_BOTTOM_TYPE = K_BOTTOM_TYPE_CJL;

    public final static int CharColor = 0xffA9B4C7;
    public List<List<Double>> minDataList = new ArrayList<>();
    public List<List<Double>> klineDataList = new ArrayList<>();
    public List<String> times = new ArrayList<>();
    public String market;

    public void setK_RIGHT_TYPE(int k_RIGHT_TYPE) {
        K_RIGHT_TYPE = k_RIGHT_TYPE;
        if (mOnKLineRightTypeChanged != null) {
            mOnKLineRightTypeChanged.onKLineRightTypeChanged(K_RIGHT_TYPE);
        }
        postInvalidate();
    }

    public void setK_BOTTOM_TYPE(int k_BOTTOM_TYPE) {
        K_BOTTOM_TYPE = k_BOTTOM_TYPE;
        if (mOnKLineBottomTypeChanged != null) {
            mOnKLineBottomTypeChanged.onKLineBottomTypeChanged(K_BOTTOM_TYPE);
        }
        postInvalidate();
    }

    protected boolean isShowTitles = true;
    public static final int K_RIGHT_YES = 0;
    public static final int K_RIGHT_NO = 1;
    protected int K_RIGHT_TYPE = K_RIGHT_YES;

    public void setShowTitles(boolean showTitles) {
        isShowTitles = showTitles;
    }

    protected int volumeIndex = -1;

    public StockLineView(Context context) {
        this(context, null, 0);
    }

    public StockLineView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StockLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        highlightPaint = new Paint();
        highlightPaint.setAntiAlias(true);//抗锯齿
        highlightPaint.setDither(true);//防抖动
        highlightPaint.setStyle(Paint.Style.STROKE);
        highlightCirclePaint = new Paint();
        highlightCirclePaint.setAntiAlias(true);//抗锯齿
        highlightCirclePaint.setDither(true);//防抖动
        highlightCirclePaint.setStyle(Paint.Style.FILL);
        mPath = new Path();
        mHighlightDashPathEffect = new DashPathEffect(new float[]{10f, 5f}, 0f);
    }


    protected void paintHighLight(Canvas canvas) {
        if (canvas == null) {
            return;
        }
        highlightPaint.setStrokeWidth(getHighlightLineWidth());
        highlightPaint.setColor(getHighLightColor());
        //highlightPaint.setPathEffect(getDashPathEffectHighlight());

        if (isHorizontalHighlightIndicatorEnabled()) {
            mPath.reset();
            mPath.moveTo(0, currentY);
            mPath.lineTo(getWidth(), currentY);
            canvas.drawPath(mPath, highlightPaint);
        }
        if (isVerticalHighlightIndicatorEnabled()) {
            mPath.reset();
            mPath.moveTo(currentX, 0);
            mPath.lineTo(currentX, getHeight());
            canvas.drawPath(mPath, highlightPaint);
        }

        if (isHorizontalHighlightIndicatorEnabled() && isVerticalHighlightIndicatorEnabled()) {
//            highlightCirclePaint.setColor(0x55CAE7FF);
            highlightCirclePaint.setColor(Color.WHITE);
            highlightCirclePaint.setStyle(Paint.Style.FILL);
            highlightCirclePaint.setAntiAlias(true);
            canvas.drawCircle(currentX, currentY, 8, highlightCirclePaint);
            highlightCirclePaint.setColor(0xffCAE7FF);
            highlightCirclePaint.setStyle(Paint.Style.STROKE);
            highlightCirclePaint.setStrokeWidth(3);
            highlightCirclePaint.setAntiAlias(true);
            canvas.drawCircle(currentX, currentY, 8, highlightCirclePaint);

            ArrayList<String> content = new ArrayList<>();
            if (this instanceof KlineView) {
                int dataIndex = getBeginFlag() + index;
                content.add(String.format("时间:%s", times.get(dataIndex)));
                content.add(String.format("现价:%s", klineDataList.get(dataIndex).get(1)));
                content.add(String.format("涨幅:%s%s", klineDataList.get(dataIndex).get(5), "%"));
                content.add(String.format("开盘:%s", klineDataList.get(dataIndex).get(0)));
                content.add(String.format("最高:%s", klineDataList.get(dataIndex).get(2)));
                content.add(String.format("最低:%s", klineDataList.get(dataIndex).get(3)));
                if (index > 0) {
                    content.add(String.format("昨收:%s", klineDataList.get(dataIndex - 1).get(1)));
                }
                content.add(String.format("成交量:%s", klineDataList.get(dataIndex).get(4)));
            } else {
                content.add(String.format("时间:%s", times.get(index)));
                content.add(String.format("现价:%s", minDataList.get(index).get(0)));
                double zf = (minDataList.get(index).get(0) - minDataList.get(index).get(3)) /
                        minDataList.get(index).get(3) * 100;
                content.add(String.format("涨幅:%.2f%s", zf, "%"));
                content.add(String.format("昨收:%s", minDataList.get(index).get(3)));
                content.add(String.format("成交量:%s", minDataList.get(index).get(1)));
            }

            float left = startX + 1;
            float top = startY + 4;
            float width = 120;
            float height = 90;

            if (currentX < startX + (endX - startX) / 2) {
                left = endX - width - 1;
            }
            if (this instanceof KlineView) {
                height = 130;
            }

            Paint linePaint = new Paint();
            linePaint.setColor(Color.BLACK);
            linePaint.setStyle(Paint.Style.FILL);
            RectF rect = new RectF(left, top, left + width + 1, top + height);
            canvas.drawRoundRect(rect, 5, 5, linePaint);
            linePaint.setTextSize(14);
            linePaint.setColor(Color.WHITE);
            for (String item : content) {
                canvas.drawText(item, left + 3, top + 15, linePaint);
                top += 15;
            }
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isDealOnTouchEventBySelf(event)) {
            gestureDetector.onTouchEvent(event);
            if (isLongPressed) {
                drawLineHighLight(event);
            }
            return true;
        } else {
            return super.onTouchEvent(event);
        }
    }

    //自己处理onTouchEvent事件
    public boolean isDealOnTouchEventBySelf(MotionEvent event) {
        boolean result = false;
        if (mainType == TYPE_MLINE && isShowHighLightIndicator) {
            result = true;
        }
        if (mainType == TYPE_KLINE) {
            if (isKBottomClick(event)) {
                result = true;
            } else {
                if (isShowHighLightIndicator || (isShowTitles && (isKRightRegionClick(event) ||
                        isKVolumeRegionClick(event)))) {
                    return true;
                }
            }
        }
        return result;
    }

    protected boolean isKRightRegionClick(MotionEvent event) {
        return false;
    }

    protected boolean isKVolumeRegionClick(MotionEvent event) {
        return false;
    }

    protected boolean isKBottomClick(MotionEvent event) {
        return false;
    }

    private void drawLineHighLight(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                mDrawVerticalHighlightIndicator = true;
                mDrawHorizontalHighlightIndicator = true;
                currentX = Math.max(Math.min(event.getX(), getStartX() + mOffsetX * (getLineData
                        ().size() - getBeginFlag() - 1)), getStartX());//控制边界问题
                int tempIndex = Math.round((currentX - getStartX()) / mOffsetX);
                currentX = getStartX() + (tempIndex) * mOffsetX + (mainType == TYPE_MLINE ? 3 :
                        +mOffsetX / 2);
                index = (int) ((currentX - getStartX()) / mOffsetX) - (isOneDayMLine ? 1 : 0);
                Float tempCurrentY = getCurrentY(index);
                if (tempCurrentY == null) {
                    return;
                }
                currentY = getCurrentY(index).floatValue();
                volumeIndex = getIndex();
                postInvalidate();
                if (mIOnTouchActinListener != null) {
                    mIOnTouchActinListener.onTouchDownAndMove();
                }
                break;
            case MotionEvent.ACTION_UP:
                mDrawVerticalHighlightIndicator = false;
                mDrawHorizontalHighlightIndicator = false;
                postInvalidate();
                isLongPressed = false;
                if (mIOnTouchActinListener != null) {
                    mIOnTouchActinListener.onTouchUp();
                }
                break;
            default:
                break;
        }
    }

    protected abstract List<List<Double>> getLineData();

    protected abstract Float getCurrentY(int index);

    protected abstract float getStartX();

    protected abstract float getStartY();

    protected abstract float getEndX();

    protected abstract float getEndY();

    protected abstract int getBeginFlag();

    public int getIndex() {
        return index + getBeginFlag();
    }

    @Override
    public boolean isVerticalHighlightIndicatorEnabled() {
        return mDrawVerticalHighlightIndicator;
    }

    @Override
    public boolean isHorizontalHighlightIndicatorEnabled() {
        return mDrawHorizontalHighlightIndicator;
    }

    @Override
    public float getHighlightLineWidth() {
        return mHighlightLineWidth;
    }

    @Override
    public DashPathEffect getDashPathEffectHighlight() {
        return mHighlightDashPathEffect;
    }

    @Override
    public int getHighLightColor() {
        return mHighLightColor;
    }

    public void setmHighlightDashPathEffect(DashPathEffect mHighlightDashPathEffect) {
        this.mHighlightDashPathEffect = mHighlightDashPathEffect;
    }

    public void setmDrawVerticalHighlightIndicator(boolean mDrawVerticalHighlightIndicator) {
        this.mDrawVerticalHighlightIndicator = mDrawVerticalHighlightIndicator;
    }

    public void setmDrawHorizontalHighlightIndicator(boolean mDrawHorizontalHighlightIndicator) {
        this.mDrawHorizontalHighlightIndicator = mDrawHorizontalHighlightIndicator;
    }

    public void setmHighLightColor(int mHighLightColor) {
        this.mHighLightColor = mHighLightColor;
    }

    public void setmHighlightLineWidth(float mHighlightLineWidth) {
        this.mHighlightLineWidth = mHighlightLineWidth;
    }

    public void setShowHighLightIndicator(boolean showHighLightIndicator) {
        isShowHighLightIndicator = showHighLightIndicator;
    }

    public boolean isShowHighLightIndicator() {
        return isShowHighLightIndicator;
    }

    public void setmIOnTouchActinListener(IOnTouchActionListener mIOnTouchActinListener) {
        this.mIOnTouchActinListener = mIOnTouchActinListener;
    }

    private IOnTouchActionListener mIOnTouchActinListener;

    public interface IOnTouchActionListener {
        void onTouchDownAndMove();

        void onTouchUp();
    }

    protected float scrollDelta;
    protected boolean isLongPressed = false;
    GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector
            .SimpleOnGestureListener() {

        @Override
        public boolean onDown(MotionEvent e) {
            scrollDelta = 0f;
            return super.onDown(e);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            if (isKBottomClick(e)) {
                switch (K_BOTTOM_TYPE) {
                    case K_BOTTOM_TYPE_CJL:
                    case K_BOTTOM_TYPE_KDJ:
                    case K_BOTTOM_TYPE_MACD:
                        K_BOTTOM_TYPE = (K_BOTTOM_TYPE + 1) % 3;
                        if (mOnKLineBottomTypeChanged != null) {
                            mOnKLineBottomTypeChanged.onKLineBottomTypeChanged(K_BOTTOM_TYPE);
                        }
                        postInvalidate();
                        return true;
                }
            } else {
                if (isShowTitles) {
                    if (isKRightRegionClick(e)) {
                        showRightDialog();
                        return true;
                    } else if (isKVolumeRegionClick(e)) {
                        showVolumeDialog();
                        return true;
                    } else {
                        StockLineView.this.performClick();
                    }
                }
            }


            return super.onSingleTapConfirmed(e);
        }

        @Override
        public void onLongPress(MotionEvent event) {
            super.onLongPress(event);
            if (!isKBottomClick(event)) {
                isLongPressed = true;
            }
        }


        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            if (distanceX > 0) {
                if (scrollDelta < 0) {
                    scrollDelta = 0;
                }
                scrollDelta += distanceX;
                if (scrollDelta > 10) {
                    scrollToLeft();
                    scrollDelta = 0;
                }
            } else if (distanceX < 0) {
                if (scrollDelta > 0) {
                    scrollDelta = 0;
                }
                scrollDelta += distanceX;
                if (scrollDelta < -10) {
                    scrollToRight();
                    scrollDelta = 0;
                }
            }
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
//            if (isShowHighLightIndicator) {
//                ((Activity) getContext()).finish();
//                return true;
//            }
            return super.onDoubleTap(e);
        }
    });
    private OnKLineBottomTypeChanged mOnKLineBottomTypeChanged;

    public void setOnKLineBottomTypeChanged(OnKLineBottomTypeChanged mOnKLineBottomTypeChanged) {
        this.mOnKLineBottomTypeChanged = mOnKLineBottomTypeChanged;
    }

    public interface OnKLineBottomTypeChanged {
        void onKLineBottomTypeChanged(int type);
    }

    private OnKLineRightTypeChanged mOnKLineRightTypeChanged;

    public void setOnKLineRightTypeChanged(OnKLineRightTypeChanged mOnKLineRightTypeChanged) {
        this.mOnKLineRightTypeChanged = mOnKLineRightTypeChanged;
    }

    public interface OnKLineRightTypeChanged {
        void onKLineRightTypeChanged(int type);
    }

    public void scrollToLeft() {

    }

    public void scrollToRight() {

    }

    public void showRightDialog() {
//        AlertDialog rightDialog = new AlertDialog.Builder(getContext()).create();
//        rightDialog.show();
//        Window window = rightDialog.getWindow();
//        window.getAttributes().gravity = Gravity.CENTER;
//        window.setGravity(Gravity.CENTER);
//        window.setContentView(R.layout.kline_right_dialog);
//        GradientDrawable gd = new GradientDrawable();
//        gd.setCornerRadius(20);
//        gd.setColor(0xffF8F8F8);
//        window.findViewById(R.id.container).setBackgroundDrawable(gd);
//        if (K_RIGHT_TYPE == K_RIGHT_YES) {
//            ((TextView) window.findViewById(R.id.kRightYes)).setTextColor(0xff089FE6);
//        } else if (K_RIGHT_TYPE == K_RIGHT_NO) {
//            ((TextView) window.findViewById(R.id.kRightNo)).setTextColor(0xff089FE6);
//        }
//
//        window.findViewById(R.id.kRightYes).setOnClickListener(view -> {
//            if (K_RIGHT_TYPE != K_RIGHT_YES) {
//                K_RIGHT_TYPE = K_RIGHT_YES;
//                if (mOnKLineRightTypeChanged != null) {
//                    mOnKLineRightTypeChanged.onKLineRightTypeChanged(K_RIGHT_TYPE);
//                }
//                postInvalidate();
//            }
//            rightDialog.dismiss();
//        });
//        window.findViewById(R.id.kRightNo).setOnClickListener(view -> {
//            if (K_RIGHT_TYPE != K_RIGHT_NO) {
//                K_RIGHT_TYPE = K_RIGHT_NO;
//                if (mOnKLineRightTypeChanged != null) {
//                    mOnKLineRightTypeChanged.onKLineRightTypeChanged(K_RIGHT_TYPE);
//                }
//                postInvalidate();
//            }
//            rightDialog.dismiss();
//        });
//        window.findViewById(R.id.close).setOnClickListener(view -> {
//            rightDialog.dismiss();
//        });
    }

    public void showVolumeDialog() {

    }
}
