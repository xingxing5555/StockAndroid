package com.example.admin.basic.stock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import com.orhanobut.logger.Logger;
//import com.touguyun.R;
//import com.touguyun.module.KDJEntity;
//import com.touguyun.module.MACDEntity;
//import com.touguyun.module.v3.DayData;
//import com.touguyun.utils.DateUtils;
//import com.touguyun.utils.DoubleUtil;
//import com.touguyun.utils.SMMACurve;
//import com.touguyun.utils.Util;
//import com.touguyun.view.StockDetailHeaderView;
//import com.touguyun.view.StockLineView;


/**
 * @author K���� arDev
 */
public class KlineView extends StockLineView {
    private float density;
    public final static int HIGH = 2;
    public final static int OPEN = 0;
    public final static int LOW = 3;
    public final static int CLOSE = 1;
    //    public final static int TIME = 4;
    public final static int VOLUME = 4;

    public final static int kline_bg = 0xff171B20;
    //    public final static int kline_biankuang = 0xff373E47;
    public final static int kline_biankuang = 0xffDDDDDD;
    public final static int kline_hengzong_zuobiao = 0xff464646;
    public final static int kline_pingpan_color = 0xff323232;
    public final static int kline_dise_color = 0xffd4f0ff;


    public int MAX_DAYDATA_NUM = 40;
    private final int Midden_line = 0xff999999;

    public void setDrawDaydataNum(int drawDaydataNum) {
        DRAW_DAYDATA_NUM = drawDaydataNum;
    }

    public int DRAW_DAYDATA_NUM = 40;

    public int beginflag;

    public int oldbeginflag;

    private float ScalesOfPrice[] = new float[5];

    public long maxVolume;

    private int nScalesOfPrice;

    private SMMACurve smmaCurve5;

    private SMMACurve smmaCurve10;

    private SMMACurve smmaCurve20;
    public static float KLINE_WIDTH = 4;

    public int nDayDatas;

    private float Left;

    public static float Width;

    public float Height;

    private float volumeHeight;

//    public int startX, startY, endY;
//
//    private int volumeStartY;//量图的顶部Y值
//    private int volumeEndY;//量图的底部Y值
//
//    public float endX;

    public boolean isDrawYB;

    public int YBNo;

    public float Hx1, Hy1, Hx2, Zx1, Zy1, Zy2;

    public int GroundColor = kline_bg;

    public int UpColor = 0xffFE4249;
    public int VolumeUpColor = 0xe0FFCFCF;

    public int DownColor = 0xff01AA3B;
    public int VolumeDownColor = 0xe0B2FFCA;

    private int PMA5Color = 0xff770094;

    private int PMA10Color = 0xffFFF100;

    private int PMA20Color = 0xff0A5BEA;

    public int VolumeColor = 0xffA9B4C7;

    private int ColorWhite = 0xff464646;

    private int ColorBlack = 0xff000000;

    public int FontHeight;

    public int TEXTSIZE = 9;

    public float distanceX = 0;

    float mainMax;
    float mainMin;

    int baoliuY = 0;

    int rightPadding = 5;

    int lastDrawTime = -6;

    public Paint paint;

    boolean canLookHistory;

    GetMoreDataCallback callback;

    public boolean getMore;
//
//    List<KDJEntity> kdjDataList;
//    List<MACDEntity> macdDataList;

    public KlineView(Context context) {
        this(context, null);
    }

    public KlineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mainType = TYPE_KLINE;
        density = getResources().getDisplayMetrics().density;
        titlesPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (nDayDatas > 0) {
            calcScales();
        }

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(Util.sp2px(this.getContext(), TEXTSIZE));
        if (FontHeight == 0) {
            FontMetrics fm = paint.getFontMetrics();
            FontHeight = (int) Math.ceil(fm.descent - fm.top) + 2;
        }
        Paint paint_temp = new Paint();
        paint_temp.setAntiAlias(true);
        paint_temp.setTextSize(Util.sp2px(this.getContext(), TEXTSIZE + 2));
        String s = String.valueOf(ScalesOfPrice[0]);
        if (Double.parseDouble(s) >= 1000) {
            s = Util.processKD(s, 0);
        } else {
            s = Util.processKD(s, savepoint);
        }
        distanceX = Util.stringWidth(s, paint_temp);
        isDrawYB = false;
        rightPadding = 15;
        screenW = (int) (getWidth() - 2 + 0.1f);
        screenH = (int) (getHeight() + 0.1f);
//        startX = distanceX + 3;
        startX = 5;
//        startY = 2 + 2 * FontHeight + baoliuY;
        startY = 5 + baoliuY;
//        endX = screenW - 2 + 0.1f - rightPadding;
        endX = screenW - distanceX;
        endY = screenH * 3 / 5;
        Width = endX - startX;
//        volumeStartY = (int) (endY + FontHeight + 25 * density);
//        volumeStartY = (int) (endY + FontHeight + 5);
        volumeStartY = endY;
//        volumeEndY = screenH - 5;
        volumeEndY = screenH - (FontHeight + 4);
        Left = startX;
        Height = endY - startY;
        volumeHeight = volumeEndY - volumeStartY;
        KLINE_WIDTH = (endX - startX) / (DRAW_DAYDATA_NUM) / 2.0f;
        mOffsetX = 2 * KLINE_WIDTH;

        paint(canvas);
        oldbeginflag = beginflag;
        paintHighLight(canvas);
    }

    public void scrollToLeft() {
        if (beginflag + DRAW_DAYDATA_NUM < nDayDatas) {
            beginflag++;
        }
        this.postInvalidate();
    }

    public void scrollToRight() {
        if (beginflag > 0) {
            beginflag--;
        }
        this.postInvalidate();
        if (beginflag < 20 && getMore) {
            if (callback != null) {
                callback.getMoreData(klineType);
            }
        }
    }

    public void reInitDrawParameter() {
        if (MAX_DAYDATA_NUM != 0) {
            nScalesOfPrice = ScalesOfPrice.length;

            smmaCurve5 = new SMMACurve(5, nDayDatas);
            smmaCurve10 = new SMMACurve(10, nDayDatas);
            smmaCurve20 = new SMMACurve(20, nDayDatas);
            if (nDayDatas > DRAW_DAYDATA_NUM) {
                if (!isRefresh) {
                    beginflag = nDayDatas - DRAW_DAYDATA_NUM;
                    YBNo = DRAW_DAYDATA_NUM - 1;
                    isDrawYB = false;
                }
                if ((nDayDatas % DRAW_DAYDATA_NUM) > 0) {
                    canLookHistory = true;
                } else {
                    canLookHistory = false;
                }
            } else {
                if (!isRefresh) {
                    beginflag = 0;
                    YBNo = nDayDatas - 1;
                    isDrawYB = false;
                }
                canLookHistory = false;
            }
        }
    }

    public void clearData() {
        klineDataList = new ArrayList<>();
        nDayDatas = 0;
        isInitdata = true;
        this.postInvalidate();
    }

    boolean isInitdata = true;

    public synchronized void paint(Canvas g) {
        if (isInitdata) {
            this.reInitDrawParameter();
            isInitdata = false;
        }
        paint.setAntiAlias(true);
        paintMainPanel(g);
        if (klineDataList.size() != 0) {
//        } else {
            if (!isLongPressed) {
                setVolumeLastIndex();
            }
//            paintMainPanel(g);
//            paintTopMessage(g);
            paintLZ(g);
            paintKD(g);
            paintBottom(g);
            if (isDrawYB) {
                paintYB(g);
            }
//            if (isShowTitles) {
//                paintRightTitles(g);
//                paintVolumeTitles(g);
//            }
            paintBottomValue(g);
        }
    }

    private float bottomValueStartX;

    private void paintBottomValue(Canvas g) {
//        String txt = "";
//        Rect bounds = new Rect();
//        float gapDistance = 10 * density;
//        titlesPaint.setTextSize(Util.sp2px(this.getContext(), TEXTSIZE));
//        titlesPaint.setTextAlign(Paint.Align.LEFT);
//
//        bottomValueStartX = startX + (isShowTitles ? volumesRF.width() + gapDistance : 0);
//
//        if (K_BOTTOM_TYPE == K_BOTTOM_TYPE_CJL) {
//            txt = Util.translate_long_thousand(klineDataList.get(volumeIndex).get(VOLUME)
// .longValue() / 100, 2, true).replaceAll(",", "") + "手";
//            titlesPaint.setColor(0xff5F5F5F);
//            g.drawText(txt, bottomValueStartX, volumeStartY - 15, titlesPaint);
//        } else if (K_BOTTOM_TYPE == K_BOTTOM_TYPE_KDJ || K_BOTTOM_TYPE == K_BOTTOM_TYPE_MACD) {
//            if (K_BOTTOM_TYPE == K_BOTTOM_TYPE_KDJ) {
//                txt = "K:" + DoubleUtil.formatFloatDot2(klineDataList.get(volumeIndex).get(6)
// .floatValue());
//            } else {
//                txt = "DIF:" + DoubleUtil.formatFloatDot2(klineDataList.get(volumeIndex).get(9)
// .floatValue());
//            }
//            titlesPaint.setColor(kColor);
//            g.drawText(txt, bottomValueStartX, volumeStartY - 15, titlesPaint);
//
//            titlesPaint.getTextBounds(txt, 0, txt.length(), bounds);
//            bottomValueStartX += (bounds.width() + gapDistance);
//            if (K_BOTTOM_TYPE == K_BOTTOM_TYPE_KDJ) {
//                txt = "D:" + DoubleUtil.formatFloatDot2(klineDataList.get(volumeIndex).get(7)
// .floatValue());
//            } else {
//                txt = "DEA:" + DoubleUtil.formatFloatDot2(klineDataList.get(volumeIndex).get
// (10).floatValue());
//            }
//            titlesPaint.setColor(dColor);
//            g.drawText(txt, bottomValueStartX, volumeStartY - 15, titlesPaint);
//
//            titlesPaint.getTextBounds(txt, 0, txt.length(), bounds);
//            bottomValueStartX += (bounds.width() + gapDistance);
//            if (K_BOTTOM_TYPE == K_BOTTOM_TYPE_KDJ) {
//                txt = "J:" + DoubleUtil.formatFloatDot2(klineDataList.get(volumeIndex).get(8)
// .floatValue());
//            } else {
//                txt = "MACD:" + DoubleUtil.formatFloatDot2(klineDataList.get(volumeIndex).get
// (11).floatValue());
//            }
//            titlesPaint.setColor(jColor);
//            g.drawText(txt, bottomValueStartX, volumeStartY - 15, titlesPaint);
//        }
    }

    private RectF rightsRF, volumesRF;
    private TextPaint titlesPaint;

//    private void paintRightTitles(Canvas g) {
//        titlesPaint.setTextSize(Util.sp2px(this.getContext(), 1.5f * TEXTSIZE));
//        titlesPaint.setColor(0xffFEFEFE);
//        titlesPaint.setTextAlign(Paint.Align.CENTER);
//        FontMetrics fm = titlesPaint.getFontMetrics();
//        rightsRF = new RectF(screenW - 65 * density, 4 * density, screenW - 15 * density,
// startY - 4 * density);
//        paint.setColor(0xff089FE6);
//        g.drawRoundRect(rightsRF, 5, 5, paint);
//        g.drawText(rightTitles.get(K_RIGHT_TYPE),
//                (rightsRF.left + rightsRF.right) / 2,
//                (rightsRF.top + rightsRF.bottom) / 2 - fm.descent + (fm.bottom - fm.top) / 2,
//                titlesPaint);
//    }
//
//    private void paintVolumeTitles(Canvas g) {
//        volumesRF = new RectF(startX, volumeStartY - 22 * density, startX + 50 * density,
// volumeStartY - 2 * density);
//        FontMetrics fm = titlesPaint.getFontMetrics();
//        g.drawRoundRect(volumesRF, 5, 5, paint);
//        g.drawText(volumeTitles.get(K_BOTTOM_TYPE),
//                (volumesRF.left + volumesRF.right) / 2,
//                (volumesRF.top + volumesRF.bottom) / 2 - fm.descent + (fm.bottom - fm.top) / 2,
//                titlesPaint);
//
//
//    }

    @Override
    protected boolean isKRightRegionClick(MotionEvent event) {
        if (rightsRF != null) {
            if (rightsRF.contains(event.getX(), event.getY())) {
                return true;
            }
        }
        return super.isKRightRegionClick(event);
    }

    @Override
    protected boolean isKVolumeRegionClick(MotionEvent event) {
        if (volumesRF != null) {
            if (volumesRF.contains(event.getX(), event.getY())) {
                return true;
            }
        }
        return super.isKVolumeRegionClick(event);
    }

    private void paintBottom(Canvas g) {
        if (K_BOTTOM_TYPE == K_BOTTOM_TYPE_CJL) {
            paintCJL(g);
//            Logger.i("切换至CJL");
        } else if (K_BOTTOM_TYPE == K_BOTTOM_TYPE_KDJ) {
            paintKDJ(g);
//            Logger.i("切换至KDJ");

        } else if (K_BOTTOM_TYPE == K_BOTTOM_TYPE_MACD) {
            paintMACD(g);
//            Logger.i("切换至MACD");
        }
    }

    int moveX = 0;

    int savepoint = 2;

    public void paintYB(Canvas g) {
        if (isDrawYB) {
            paint.setColor(kline_hengzong_zuobiao);
            g.drawLine(Hx1, Hy1, Hx2 + Left, Hy1, paint);
            if (Zx1 < startX) {
                Zx1 = startX;
            }
            g.drawLine(Zx1, Zy1 - baoliuY, Zx1, Zy2 + Height, paint);
            if (YBNo < 0) {
                YBNo = 0;
            }
            if (YBNo >= klineDataList.size()) {
                YBNo = klineDataList.size() - 1;
            }
            Paint linePaint = new Paint();
            linePaint.setColor(kline_dise_color);
            RectF rect = new RectF(0, Hy1 - FontHeight / 2 - 5, startX, Hy1 + FontHeight);
            g.drawRoundRect(rect, 5, 5, linePaint);
            if (klineDataList == null || YBNo > klineDataList.size()) {
                return;
            }
            float closed, shoupan;
            if (YBNo > 0) {
                closed = klineDataList.get(YBNo - 1).get(CLOSE).floatValue();
            } else {
                closed = klineDataList.get(YBNo).get(CLOSE).floatValue();
            }
            shoupan = klineDataList.get(YBNo).get(CLOSE).floatValue();
            String shoupanString = Util.saveNumFromat(String.valueOf(shoupan), savepoint);
            if (shoupan > closed) {
                paint.setColor(UpColor);
            } else if (shoupan < closed) {
                paint.setColor(DownColor);
            } else {
                paint.setColor(CharColor);
            }
            g.drawText(shoupanString, startX - Util.stringWidth(shoupanString, paint), Hy1, paint);
            String zhangfuString = "";
            float zhangfu_f = 0;
            if (YBNo - 1 > 0) {

                zhangfu_f = (float) ((klineDataList.get(YBNo).get(CLOSE) - klineDataList.get(YBNo
                        - 1).get(CLOSE)) / klineDataList.get(YBNo - 1).get(CLOSE));

                df = new DecimalFormat("0.00");
                zhangfuString = df.format(zhangfu_f * 100f) + "%";
            }
            int color_zhangfu = CharColor;
            if (zhangfu_f > 0) {
                color_zhangfu = UpColor;
            } else if (zhangfu_f < 0) {
                color_zhangfu = DownColor;
            }
            paint.setColor(color_zhangfu);
            if (zhangfuString == null) {
                return;
            }
            g.drawText(zhangfuString, startX - Util.stringWidth(zhangfuString, paint), Hy1 +
                    FontHeight - 5, paint);
        }
    }

    private void calcScales() {
        maxVolume = 0;
        float MaxPrice = 0.0f, MinPrice = 0.0f;
        int index;
        if (beginflag <= 0) {
            beginflag = 0;
            index = 0;
        } else {
            index = beginflag;
        }
        if (nDayDatas < 1) {
            return;
        }
        int length = beginflag + DRAW_DAYDATA_NUM > nDayDatas ? nDayDatas : beginflag +
                DRAW_DAYDATA_NUM;

        MaxPrice = klineDataList.get(beginflag).get(HIGH).floatValue();
        MinPrice = klineDataList.get(beginflag).get(LOW).floatValue();
        for (int i = beginflag; i < length; i++) {
            if (klineDataList.get(i).get(HIGH) > MaxPrice) {
                MaxPrice = klineDataList.get(i).get(HIGH).floatValue();
            }
            if (klineDataList.get(i).get(LOW) < MinPrice) {
                MinPrice = klineDataList.get(i).get(LOW).floatValue();
            }
            if (klineDataList.get(i).get(VOLUME) > maxVolume) {
                maxVolume = klineDataList.get(i).get(VOLUME).longValue();
            }
        }
        if (MaxPrice == MinPrice) {
            MaxPrice = MinPrice + 1;
        }
        for (int i = 0; i < nScalesOfPrice; i++) {
            ScalesOfPrice[i] = MinPrice + i * ((MaxPrice - MinPrice) / (nScalesOfPrice - 1));
        }
        smmaCurve5.Count = smmaCurve10.Count = smmaCurve20.Count = nDayDatas;
        for (int i = 0; i < nDayDatas; i++) {
            smmaCurve5.Price[i] = klineDataList.get(i).get(CLOSE);
            smmaCurve10.Price[i] = klineDataList.get(i).get(CLOSE);
            smmaCurve20.Price[i] = klineDataList.get(i).get(CLOSE);
        }
        smmaCurve5.ccMathMA(false);
        float MaxPrice1 = MaxPrice;
        float MinPrice1 = MinPrice;
        if (smmaCurve5.SMMA != null) {
            for (int i = index; i < length; i++) {
                if (smmaCurve5.SMMA[i] > MaxPrice1) {
                    MaxPrice1 = (float) smmaCurve5.SMMA[i];
                }
                if (smmaCurve5.SMMA[i] < MinPrice1) {
                    MinPrice1 = (float) smmaCurve5.SMMA[i];
                }
            }
        }

        smmaCurve10.ccMathMA(false);
        if (smmaCurve10.SMMA != null) {
            for (int i = index; i < length; i++) {
                if (smmaCurve10.SMMA[i] > MaxPrice1) {
                    MaxPrice1 = (float) smmaCurve10.SMMA[i];
                }
                if (smmaCurve10.SMMA[i] < MinPrice1) {
                    MinPrice1 = (float) smmaCurve10.SMMA[i];
                }
            }
        }

        smmaCurve20.ccMathMA(false);
        if (smmaCurve20.SMMA != null) {
            for (int i = index; i < length; i++) {
                if (smmaCurve20.SMMA[i] > MaxPrice1) {
                    MaxPrice1 = (float) smmaCurve20.SMMA[i];
                }
                if (smmaCurve20.SMMA[i] < MinPrice1) {
                    MinPrice1 = (float) smmaCurve20.SMMA[i];
                }
            }
        }

//        MinPrice1 -= MinPrice1*0.01;
//        MaxPrice1 += MaxPrice1*0.01;
        for (int i = 0; i < nScalesOfPrice; i++) {
            ScalesOfPrice[i] = MinPrice1 + i * ((MaxPrice1 - MinPrice1) / (nScalesOfPrice - 1));
        }
        mainMax = MaxPrice1;
        mainMin = MinPrice1;
    }

    public void paintMainPanel(Canvas g) {
        float d, y;
        paint.setColor(GroundColor);

//        Util.drawRect(startX, startY - baoliuY, Width, endY - startY + 2 * baoliuY, true, g,
// paint);
//        Util.drawRect(startX, volumeStartY - baoliuY, Width, volumeEndY - volumeStartY + 2 *
// baoliuY, true, g, paint);

        paint.setColor(kline_biankuang);
//        Util.drawRect(startX, startY - baoliuY, Width, endY - startY, false, g, paint);
//        Util.drawRect(startX, volumeStartY - baoliuY, Width, volumeEndY - volumeStartY, false, g,
//                paint);
        Util.drawRect(startX, startY, Width, volumeEndY, false, g, paint);
        paint.setStyle(Style.STROKE);
        PathEffect effects = new DashPathEffect(new float[]{5, 5, 5, 5}, 1);
        paint.setPathEffect(effects);
        d = (endX - startX) / 6;
//        背景的竖线
        for (int i = 1; i < 6; i++) {
            float x = startX + (i * d);
            g.drawLine(x, startY, x, endY, paint);
        }
//        中间的线
        paint.setColor(Midden_line);
        g.drawLine(startX, endY, screenW, endY + 5, paint);


//        g.drawLine(startX + (endX - startX + 0.1f) / 3, startY, startX + (endX - startX + 0.1f)
// / 3.0f, endY, paint);
//        g.drawLine(startX + (endX - startX + 0.1f) * 2 / 3, startY, startX + (endX - startX +
// 0.1f) * 2.0f / 3.0f, endY, paint);

    }

    /*画蜡烛*/
    private void paintLZ(Canvas g) {
        long t;
        float bottom = endY - 10;
        float drawH = Height - 40;
        float maxToMin = mainMax - mainMin;
        if (maxToMin == 0) maxToMin = 1;
        float tempC;
        float x1, y1, x2, y2, w, h;
        float d;
        int index, length;
        d = KLINE_WIDTH;
        if (beginflag == 1) {
            length = nDayDatas;
            index = beginflag;
        } else {
            length = DRAW_DAYDATA_NUM;
            index = beginflag;
        }
        for (int i = 0, k = index; i < length; i++, k++) {
            if (maxToMin / 10 == 0) {
                break;
            }
            if (k == klineDataList.size()) {
                break;
            }
            if (klineDataList.get(k).get(OPEN) == 0 || klineDataList.get(k).get(HIGH) == 0 ||
                    klineDataList.get(k).get(LOW) == 0 || klineDataList.get(k).get(CLOSE) == 0) {
                continue;
            }
            tempC = startX + d * 2 * i;

            if (k > beginflag + 5 && k < klineDataList.size() - 2) {
                if (k % (int) (180 / KLINE_WIDTH) == 0) {
                    x1 = tempC + (d * 2 - 1) / 2;
                    y1 = startY;
                    x2 = x1;
                    y2 = endY;
//                    paint.setColor(kline_biankuang);
//                    g.drawLine(x1, y1, x2, y2, paint);
                    paint.setColor(CharColor);
                    String endTime = times.get(k);
//                    修改时间位置
                    g.drawText(endTime, x1 - Util.stringWidth(endTime, paint) / 2, volumeEndY +
                            FontHeight - 4, paint);
                }
//                修改日期位置
//                int num = (int) (endX - startX) / times.size();
//                for (int j = 0; j < times.size(); j++) {
//                    g.drawText(times.get(j), startX + j * num, volumeEndY + FontHeight - 4, paint);
//                }
            }


            if (klineDataList.get(k).get(OPEN) < klineDataList.get(k).get(CLOSE)) {
                paint.setColor(UpColor);
            } else if (klineDataList.get(k).get(OPEN) == klineDataList.get(k).get(CLOSE)) {
                paint.setColor(kline_pingpan_color);
            } else {
                paint.setColor(DownColor);
            }

            x1 = tempC + (d * 2 - 1) / 2;
            y1 = (int) (bottom - (drawH * (klineDataList.get(k).get(HIGH) - mainMin)) / (maxToMin));
            x2 = x1;
            y2 = (int) (bottom - (drawH * (klineDataList.get(k).get(LOW) - mainMin)) / (maxToMin));
            //draw竖线
            g.drawLine(x1, y1, x2, y2, paint);

            int open = (int) (klineDataList.get(k).get(OPEN) * 100);
            int close = (int) (klineDataList.get(k).get(CLOSE) * 100);

            if (open < close) {
                paint.setColor(UpColor);
                x1 = tempC;
                t = (int) ((drawH * (klineDataList.get(k).get(CLOSE) - mainMin)) / (maxToMin));
                y1 = bottom - (t < 1 ? 1 : (int) t);
                w = (d * 2 - 1);
                t = (int) ((drawH * (klineDataList.get(k).get(CLOSE) - klineDataList.get(k).get
                        (OPEN))) / (maxToMin));
                h = t < 1 ? 1 : (int) t;
                if (h > screenH) {
                    continue;
                }
                //y1是矩形上边，y1+h是矩形下边
                Util.drawRect(x1, y1, w, h, true, g, paint);

                hashMap.put(i, y1);
            } else if (open == close) {
                paint.setColor(0xff999999);
                x1 = tempC;
                t = (int) ((drawH * (klineDataList.get(k).get(CLOSE) - mainMin)) / (maxToMin));
                y1 = bottom - (t < 1 ? 1 : (int) t);
                w = (d * 2 - 1);
                t = (int) ((drawH * (klineDataList.get(k).get(CLOSE) - klineDataList.get(k).get
                        (OPEN))) / (maxToMin));
                h = t < 1 ? 1 : (int) t;
                if (h > screenH) {
                    continue;
                }
                Util.drawRect(x1, y1, w, h, true, g, paint);

                hashMap.put(i, y1);
            } else {
                paint.setColor(DownColor);
                x1 = tempC;
                t = (int) ((drawH * (klineDataList.get(k).get(OPEN) - mainMin)) / (maxToMin));
                y1 = bottom - (t < 1 ? 1 : (int) t);
                w = (d * 2 - 1);
                t = (int) ((drawH * (klineDataList.get(k).get(OPEN) - klineDataList.get(k).get
                        (CLOSE))) / (maxToMin));
                h = t < 1 ? 1 : (int) t;
                if (y1 < startY) {
                    y1 = startY;
                }
                if (h > screenH) {
                    continue;
                }
                Util.drawRect(x1, y1, w, h, true, g, paint);

                hashMap.put(i, y1 + h);
            }
            if (k == nDayDatas - 1) {
                break;
            }
            if (i < (nDayDatas - 1)) {
                if (smmaCurve5.SMMA != null && k >= 4) {
                    paint.setColor(PMA5Color);
                    x1 = tempC;
                    y1 = (int) (bottom - (drawH * (smmaCurve5.SMMA[k] - mainMin)) / maxToMin);
                    x2 = x1 + d * 2;
                    y2 = (int) (bottom - (drawH * (smmaCurve5.SMMA[k + 1] - mainMin)) / maxToMin);
                    g.drawLine(x1 + d / 2.0f, y1, x2 + d / 2.0f, y2, paint);
                }
                if (smmaCurve10.SMMA != null && k >= 9) {
                    paint.setColor(PMA10Color);
                    y1 = (int) (bottom - (drawH * (smmaCurve10.SMMA[k] - mainMin)) / maxToMin);
                    y2 = (int) (bottom - (drawH * (smmaCurve10.SMMA[k + 1] - mainMin)) / maxToMin);
                    g.drawLine(x1 + d / 2.0f, y1, x2 + d / 2.0f, y2, paint);
                }
                if (smmaCurve20.SMMA != null && k >= 19) {
                    paint.setColor(PMA20Color);
                    y1 = (int) (bottom - (drawH * (smmaCurve20.SMMA[k] - mainMin)) / maxToMin);
                    y2 = (int) (bottom - (drawH * (smmaCurve20.SMMA[k + 1] - mainMin)) / maxToMin);
                    g.drawLine(x1 + d / 2.0f, y1, x2 + d / 2.0f, y2, paint);
                }
            }
        }
    }

    private DecimalFormat df;

    public void paintTopMessage(Canvas canvas) {
        Paint linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setTextSize(Util.sp2px(this.getContext(), TEXTSIZE));
        df = new DecimalFormat("0.00");
        if (!isDrawYB) {
            if (klineDataList != null && klineDataList.size() > 0) {
                YBNo = klineDataList.size() - 1;
            } else {
                return;
            }
        }
        if (YBNo < 0 || YBNo > klineDataList.size() - 1) {
            return;
        }
        float sx = startX + 10;
        float sy = startY + FontHeight;
        String mString;
        final int DISTANCE = 40;

        mString = "MA5: ";
        linePaint.setColor(PMA5Color);
        if (smmaCurve5.SMMA == null) {
            linePaint.setColor(ColorBlack);
            mString += "--";
        } else {
            if (isVerticalHighlightIndicatorEnabled() || isHorizontalHighlightIndicatorEnabled()) {
                mString += df.format(smmaCurve5.SMMA[getIndex()]);
            } else {
                mString += df.format(smmaCurve5.SMMA[smmaCurve5.SMMA.length - 1]);
            }
        }
        canvas.drawCircle(sx, sy - 6, 3, linePaint);// 小圆
        linePaint.setColor(CharColor);
        canvas.drawText(mString, sx + 3 * density, sy, linePaint);

        sx += Util.stringWidth(mString, linePaint) + 20;
        mString = "MA10: ";
        linePaint.setColor(PMA10Color);
        if (smmaCurve10.SMMA == null) {
            linePaint.setColor(CharColor);
            mString += "--";
        } else {
            if (isVerticalHighlightIndicatorEnabled() || isHorizontalHighlightIndicatorEnabled()) {
                mString += df.format(smmaCurve10.SMMA[getIndex()]);
            } else {
                mString += df.format(smmaCurve10.SMMA[smmaCurve10.SMMA.length - 1]);
            }
        }
        canvas.drawCircle(sx, sy - 6, 3, linePaint);// 小圆
        linePaint.setColor(CharColor);
        canvas.drawText(mString, sx + 3 * density, sy, linePaint);

        sx += Util.stringWidth(mString, linePaint) + 20;
        mString = "MA20: ";
        linePaint.setColor(PMA20Color);
        if (smmaCurve20.SMMA == null) {
            linePaint.setColor(CharColor);
            mString += "--";
        } else {
            if (isVerticalHighlightIndicatorEnabled() || isHorizontalHighlightIndicatorEnabled()) {
                mString += df.format(smmaCurve20.SMMA[getIndex()]);
            } else {
                mString += df.format(smmaCurve20.SMMA[smmaCurve20.SMMA.length - 1]);
            }
        }
        canvas.drawCircle(sx, sy - 6, 3, linePaint);// 小圆
        linePaint.setColor(CharColor);
        canvas.drawText(mString, sx + 3 * density, sy, linePaint);


    }

    /*画刻度*/
    private void paintKD(Canvas g) {
        float d = (Height) / (nScalesOfPrice - 1);
        paint.setColor(CharColor);
        float y;
        String s;
        savepoint = 2;
        paint.setColor(kline_biankuang);
        for (int i = 0; i < nScalesOfPrice; i++) {
            s = String.valueOf(ScalesOfPrice[nScalesOfPrice - 1 - i]);
            if (Double.parseDouble(s) >= 1000) {
                s = Util.processKD(s, 0);
            } else {
                s = Util.processKD(s, savepoint);
            }
            if (i == 0) {
                y = startY + 20;
            } else if (i == nScalesOfPrice - 1) {
                y = startY + i * d - FontHeight;
            } else {
                y = startY + i * d - FontHeight / 2;
            }
//           修改右侧数据
            Util.drawString(s, endX + 8, y, 0, g, paint);

//            文字对应的横线
            g.drawLine(startX, y + FontHeight / 2, endX + 5, y + FontHeight / 2, paint);
        }
    }

    /*画成交量*/
    private void paintCJL(Canvas g) {
        float tempA = volumeEndY;
        volumeHeight = volumeEndY - volumeStartY;
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(Util.sp2px(this.getContext(), TEXTSIZE));
        //        成交量右侧数据
        paint.setColor(VolumeColor);
        float x, y;
        String s;
        s = Util.translate_long_thousand((long) maxVolume / 100, 2, true) + "手";
        String[] content = s.split(",");
//        x = endX - Util.stringWidth(content[0],paint);
        x = endX;
        y = volumeStartY + (volumeEndY - volumeStartY) / 2;
        Util.drawString(content[0], x, y, 0, g, paint);
        y = volumeStartY + (volumeEndY - volumeStartY) / 2 + FontHeight / 2;
        paint.setColor(kline_biankuang);
        g.drawLine(startX, y, endX + 5, y, paint);

//        x = startX - Util.stringWidth(content[1],paint);
        x = endX;
        y = volumeEndY - FontHeight;
        Util.drawString(content[1], x, y, 0, g, paint);
        y = volumeEndY - FontHeight / 2;
        paint.setColor(kline_biankuang);
        g.drawLine(startX, y, endX + 5, y, paint);

        if (klineDataList != null) {
            float w, h;
            int length, index;

            if (beginflag == 1) {
                length = nDayDatas;
                index = beginflag;
            } else {
                length = DRAW_DAYDATA_NUM;
                index = beginflag;
            }

            for (int i = 0, k = index; i < length; i++, k++) {

                if (k >= klineDataList.size()) {
                    break;
                }

                if (klineDataList.get(k).get(OPEN) == 0 || klineDataList.get(k).get(HIGH) == 0 ||
                        klineDataList.get(k).get(LOW) == 0 || klineDataList.get(k).get(CLOSE) ==
                        0) {
                    break;
                }
                x = startX + KLINE_WIDTH * 2 * i;


                h = (int) ((long) volumeHeight * 0.9 * (long) klineDataList.get(k).get(VOLUME)
                        .longValue() / (long) maxVolume);
                y = tempA - h;
                w = KLINE_WIDTH * 2 - 1;

                if (klineDataList.get(k).get(OPEN) < klineDataList.get(k).get(CLOSE)) {
                    paint.setColor(VolumeUpColor);
                    Util.drawRect(x, y, w, h, true, g, paint);
                } else if (((int) (100 * klineDataList.get(k).get(OPEN))) == ((int) (100 *
                        klineDataList.get(k).get(CLOSE)))) {
                    if (k == 0 || (k > 0 && ((int) (100 * klineDataList.get(k).get(CLOSE))) >= (
                            (int) (100 * klineDataList.get(k - 1).get(CLOSE))))) {
                        paint.setColor(VolumeUpColor);
                        Util.drawRect(x, y, w, h, true, g, paint);
                    } else {
                        paint.setColor(VolumeDownColor);
                        Util.drawRect(x, y, w, h, true, g, paint);
                    }
                } else {
                    paint.setColor(VolumeDownColor);
                    Util.drawRect(x, y, w, h, true, g, paint);
                }
            }

        }


    }

    /*画KDJ*/
    private void paintKDJ(Canvas g) {
        if (klineDataList == null || klineDataList.size() == 0) {
            return;
        }
        int index, length;
        if (beginflag == 1) {
            length = nDayDatas;
            index = beginflag;
        } else {
            length = DRAW_DAYDATA_NUM;
            index = beginflag;
        }
        calcKdjScale(index);
        float kdjHeight = volumeEndY - volumeStartY;
        float kdjItemHeight = kdjHeight / (kdjMax - kdjMin);
        float tempC;
        float x1, x2, yk1, yk2, yd1, yd2, yj1, yj2;
        float d;
        d = KLINE_WIDTH;
        for (int i = 0, k = index; i < length - 1; i++, k++) {
            if (k >= (klineDataList.size() - 1)) {
                break;
            }
            tempC = startX + d * 2 * i;
            x1 = tempC + d;
            x2 = x1 + d * 2;

            paint.setColor(kColor);
            yk1 = volumeStartY + (kdjMax - klineDataList.get(k).get(6).floatValue()) *
                    kdjItemHeight;
            yk2 = volumeStartY + (kdjMax - klineDataList.get(k + 1).get(6).floatValue()) *
                    kdjItemHeight;
            g.drawLine(x1 + d / 2.0f, yk1, x2 + d / 2.0f, yk2, paint);

            paint.setColor(dColor);
            yd1 = volumeStartY + (kdjMax - klineDataList.get(k).get(7).floatValue()) *
                    kdjItemHeight;
            yd2 = volumeStartY + (kdjMax - klineDataList.get(k + 1).get(7).floatValue()) *
                    kdjItemHeight;
            g.drawLine(x1 + d / 2.0f, yd1, x2 + d / 2.0f, yd2, paint);

            paint.setColor(jColor);
            yj1 = volumeStartY + (kdjMax - klineDataList.get(k).get(8).floatValue()) *
                    kdjItemHeight;
            yj2 = volumeStartY + (kdjMax - klineDataList.get(k + 1).get(8).floatValue()) *
                    kdjItemHeight;
            g.drawLine(x1, yj1, x2, yj2, paint);
        }

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(CharColor);
        paint.setTextSize(Util.sp2px(this.getContext(), TEXTSIZE));
        FontMetrics fm = paint.getFontMetrics();
        paint.setTextAlign(Paint.Align.LEFT);
        float x, y;
        x = Left - Util.stringWidth("80", paint);
        y = (int) (volumeStartY + (kdjMax - 80) / (kdjMax - kdjMin) * kdjHeight - fm.descent +
                (fm.bottom - fm.top) / 2);
        g.drawText("80", x, y, paint);
        y = (int) (volumeStartY + (kdjMax - 20) / (kdjMax - kdjMin) * kdjHeight - fm.descent +
                (fm.bottom - fm.top) / 2);
        g.drawText("20", x, y, paint);
        /*y = (int) (volumeStartY + (kdjMax - 0) / (kdjMax - kdjMin) * kdjHeight *//*- (fm.bottom
         - fm.top)*//*);
        g.drawText("0", x, y, paint);*/
    }

    /*画MACD*/
    private void paintMACD(Canvas g) {
        if (klineDataList == null || klineDataList.size() == 0) {
            return;
        }
        int index, length;
        if (beginflag == 1) {
            length = nDayDatas;
            index = beginflag;
        } else {
            length = DRAW_DAYDATA_NUM;
            index = beginflag;
        }
        calcMacdScale(index);
        float macdHeight = volumeEndY - volumeStartY;
        float macdItemHeight = macdHeight / (macdMax - macdMin);
        float tempC;
        float x1, x2, yDif1, yDif2, yDea1, yDea2, yMacd1, yMacd2;
        float d;
        d = KLINE_WIDTH;

        for (int i = 0, k = index; i < length; i++, k++) {
            if (k >= klineDataList.size()) {
                break;
            }
            tempC = startX + d * 2 * i;
            x1 = tempC + d;
            x2 = x1 + d * 2;
            if (k < klineDataList.size() - 1) {
                paint.setColor(kColor);
                yDif1 = volumeStartY + (macdMax - klineDataList.get(k).get(9).floatValue()) *
                        macdItemHeight;
                yDif2 = volumeStartY + (macdMax - klineDataList.get(k + 1).get(9).floatValue()) *
                        macdItemHeight;
                g.drawLine(x1 + d / 2.0f, yDif1, x2 + d / 2.0f, yDif2, paint);

                paint.setColor(dColor);
                yDea1 = volumeStartY + (macdMax - klineDataList.get(k).get(10).floatValue()) *
                        macdItemHeight;
                yDea2 = volumeStartY + (macdMax - klineDataList.get(k + 1).get(10).floatValue())
                        * macdItemHeight;
                g.drawLine(x1, yDea1, x2, yDea2, paint);
            }
            float macd = klineDataList.get(k).get(11).floatValue();
            yMacd1 = volumeStartY + (macdMax - macd) * macdItemHeight;
            if (macd > 0) {
                paint.setColor(getResources().getColor(android.R.color.holo_red_dark));
                g.drawRect(x1 - 2, yMacd1, x1 + 2, volumeStartY + macdItemHeight * macdMax, paint);
            } else {
                paint.setColor(getResources().getColor(android.R.color.holo_green_dark));
                g.drawRect(x1 - 2, volumeStartY + macdItemHeight * macdMax, x1 + 2, yMacd1, paint);
            }

        }
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(Util.sp2px(this.getContext(), TEXTSIZE));
        paint.setColor(kline_hengzong_zuobiao);
        FontMetrics fm = paint.getFontMetrics();
        paint.setTextAlign(Paint.Align.RIGHT);
        float x, y;
        x = startX;
        y = (int) (volumeStartY + (-fm.descent + (fm.bottom - fm.top) / 2) * 2);
        g.drawText(String.format("%.2f", macdMax) + "", x, y, paint);
        y = volumeEndY;
        g.drawText(String.format("%.2f", macdMin) + "", x, y, paint);
    }

    private int kColor = 0xff0000FF;
    private int dColor = 0xffFF9000;
    private int jColor = 0xffF707F7;
    private float kdjMax = 100, kdjMin = 0;
    private float macdMax = Integer.MIN_VALUE, macdMin = Integer.MAX_VALUE;

    private void calcKdjScale(int from) {
        kdjMax = 100;
        kdjMin = 0;
    }

    private void calcMacdScale(int from) {
        macdMax = Integer.MIN_VALUE;
        macdMin = Integer.MAX_VALUE;
        for (int i = from; i < klineDataList.size(); i++) {
            List<Double> list = klineDataList.get(i);
            for (int j = 9; j < 12; j++) {

                if (list.get(j) > macdMax) {
                    macdMax = list.get(j).floatValue();
                }
                if (list.get(j) < macdMin) {
                    macdMin = list.get(j).floatValue();
                }

            }
        }
        if (Math.abs(macdMax) > Math.abs(macdMin)) {
            macdMin = -Math.abs(macdMax);
        } else {
            macdMax = Math.abs(macdMin);
        }
    }

    @Override
    protected boolean isKBottomClick(MotionEvent event) {
        if (event.getY() > (volumeStartY - baoliuY)) {
            return true;
        }
        return super.isKBottomClick(event);
    }

    public int klineType;

    public void setData(DayData data, int type, boolean isRight, int k_bottom_type) {
        this.klineType = type;
        this.K_BOTTOM_TYPE = k_bottom_type;
        this.K_RIGHT_TYPE = isRight ? K_RIGHT_YES : K_RIGHT_NO;
        times = data.getTimes();
        klineDataList = data.getKData();
        nDayDatas = data.getKData().size();
        reInitDrawParameter();
        if (nDayDatas > DRAW_DAYDATA_NUM) {
            beginflag = nDayDatas - DRAW_DAYDATA_NUM + moveX;
        } else {
            beginflag = 0;
        }
        this.postInvalidate();
    }

    public void setMoreData(DayData data) {
        klineDataList = data.getKData();
        nDayDatas = data.getKData().size();
        times = data.getTimes();
        this.postInvalidate();
    }

    public void setVolumeLastIndex() {
//        if (K_BOTTOM_TYPE == K_BOTTOM_TYPE_CJL) {
        this.volumeIndex = klineDataList.size() - 1;
//        } else if (K_BOTTOM_TYPE == K_BOTTOM_TYPE_KDJ) {
//            this.volumeIndex = kdjDataList.size() - 1;
//        } else if (K_BOTTOM_TYPE == K_BOTTOM_TYPE_MACD) {
//            this.volumeIndex = macdDataList.size() - 1;
//        }
    }

    boolean isRefresh = false;

    @Override
    protected List<List<Double>> getLineData() {
        return klineDataList;
    }

    @Override
    protected Float getCurrentY(int index) {
        return hashMap.get(index);
    }

    @Override
    protected float getStartX() {
        return this.startX;
    }

    @Override
    protected float getStartY() {
        return this.startY;
    }

    @Override
    protected float getEndX() {
        return this.endX;
    }

    @Override
    protected float getEndY() {
        return this.endY;
    }

    @Override
    protected int getBeginFlag() {
        return beginflag;
    }

    @Override
    protected int getScrollTextBg() {
        return 0xff2FDF61;
    }


    Map<Integer, Float> hashMap = new HashMap<>();

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public String getTime() {
        if (klineDataList.size() == 0) {
            return "";
        }
        return "日期：" + DateUtils.getDateStr(klineDataList.get(getIndex()).get(4).longValue(),
                "yy-M-d");
    }

    public SpannableString getHigh() {
        if (klineDataList.size() == 0) {
            return new SpannableString("");
        }
        return getSpannableString(klineDataList.get(getIndex()).get(0), klineDataList.get
                (getIndex()).get(6), "高：", "");
    }


    public SpannableString getOpen() {
        if (klineDataList.size() == 0) {
            return new SpannableString("");
        }
        return getSpannableString(klineDataList.get(getIndex()).get(1), klineDataList.get
                (getIndex()).get(6), "开：", "");
    }

    public SpannableString getLow() {
        if (klineDataList.size() == 0) {
            return new SpannableString("");
        }
        return getSpannableString(klineDataList.get(getIndex()).get(2), klineDataList.get
                (getIndex()).get(6), "低：", "");
    }

    public SpannableString getClose() {
        if (klineDataList.size() == 0) {
            return new SpannableString("");
        }
        return getSpannableString(klineDataList.get(getIndex()).get(3), klineDataList.get
                (getIndex()).get(6), "收：", "");
    }

    public SpannableString getChangeRateStr() {
        if (klineDataList.size() == 0) {
            return new SpannableString("");
        }
        return getSSForChangeRate(getChangeRate(), "涨幅：", "%");
    }

    public double getChangeRate() {
        return (klineDataList.get(getIndex()).get(3) - klineDataList.get(getIndex()).get(6)) /
                klineDataList.get(getIndex()).get(6) * 100;
    }

    @NonNull
    private SpannableString getSpannableString(double value, double baseValue, String valueName,
                                               String valueSuffix) {
        SpannableString ss = new SpannableString(valueName + DoubleUtil.formatDoubleDot2(value) +
                valueSuffix);
        if ((value - baseValue) < 0) {
            ss.setSpan(new ForegroundColorSpan(getResources().getColor(android.R.color
                    .holo_green_dark)), 0, ss.length(), SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
        } else if ((value - baseValue) > 0) {
            ss.setSpan(new ForegroundColorSpan(getResources().getColor(android.R.color
                    .holo_red_dark)), 0, ss.length(), SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
        } else {
            ss.setSpan(new ForegroundColorSpan(getResources().getColor(android.R.color
                    .darker_gray)), 0, ss.length(), SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        return ss;
    }

    @NonNull
    private SpannableString getSSForChangeRate(double value, String valueName, String valueSuffix) {
        SpannableString ss;
        if (value < 0) {
            ss = new SpannableString(valueName + DoubleUtil.formatDoubleDot2(value) + valueSuffix);
            ss.setSpan(new ForegroundColorSpan(getResources().getColor(android.R.color
                    .holo_green_dark)), 0, ss.length(), SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
        } else if (value > 0) {
            ss = new SpannableString(valueName + "+" + DoubleUtil.formatDoubleDot2(value) +
                    valueSuffix);
            ss.setSpan(new ForegroundColorSpan(getResources().getColor(android.R.color
                    .holo_red_dark)), 0, ss.length(), SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
        } else {
            ss = new SpannableString(valueName + DoubleUtil.formatDoubleDot2(value) + valueSuffix);
            ss.setSpan(new ForegroundColorSpan(getResources().getColor(android.R.color
                    .darker_gray)), 0, ss.length(), SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        return ss;
    }

    public interface GetMoreDataCallback {
        public void getMoreData(int klineType);
    }
}
