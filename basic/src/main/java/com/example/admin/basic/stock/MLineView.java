package com.example.admin.basic.stock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.FillType;
import android.graphics.Shader;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import com.touguyun.R;
//import com.touguyun.module.v3.MinData;
//import com.touguyun.utils.DoubleUtil;
//import com.touguyun.utils.SMMACurve;
//import com.touguyun.utils.Util;
//import com.touguyun.view.StockLineView;

public class MLineView extends StockLineView {

    public static final int ONE_DAY_MINUTES_LINE = 0;
    public static final int FIVE_DAY_MINUTES_LINE = 1;


    public final static int mline_volbg = 0xffF5F5F5;//���ʱ�ɽ�������ɫ(��)

    public final static int mline_trendfill = 0x55CAE7FF;//�������ɫֵ(��ɫ)

    public final static int mline_biankuang = 0xff373E47;//���Ʊ߿�ɫֵ(��ɫ)

    public final static int mline_trendline = 0xffCAE7FF;//������ɫֵ(��ɫ)

    public final static int mline_junxian = 0x00FDBE02;//��ʱ������ɫ(��ɫ)


    private int mlineType = 0;

    private MLineView _this = this;

    public float maxTrendDiff;
    public long maxVolume, minVolume;
    private int ColorText = CharColor;
    private int ColorYellow = 0xffFFFF00;
    private int ColorGreen = 0xff01AA3B;
    private int ColorRed = 0xffFE4249;
    public float closeTrend = 0;
    public boolean isYB;
    public int TEXTSIZE = 18;
    int MAX_MDATA_NUM = 242;
    private SMMACurve smmaCurve = new SMMACurve(MAX_MDATA_NUM, MAX_MDATA_NUM);

    public MLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mainType = TYPE_MLINE;
    }

    public void setMarket(String market){
        this.market = market;
        switch (market){
            case "hs":
                MAX_MDATA_NUM = 242;
                break;
            case "hk":
                MAX_MDATA_NUM = 332;
                break;
            case "us":
                MAX_MDATA_NUM = 391;
                break;
        }
    }


    public void setMlineType(int mlineType) {
        this.mlineType = mlineType;
        switch (mlineType) {
            case ONE_DAY_MINUTES_LINE:
                isOneDayMLine = true;
                switch (market){
                    case "hs":
                        MAX_MDATA_NUM = 242;
                        break;
                    case "hk":
                        MAX_MDATA_NUM = 332;
                        break;
                    case "us":
                        MAX_MDATA_NUM = 391;
                        break;
                }
                break;
            case FIVE_DAY_MINUTES_LINE:
                isOneDayMLine = false;
                setMarket(market);
                switch (market){
                    case "hs":
                        MAX_MDATA_NUM = 245;
                        break;
                    case "hk":
                        MAX_MDATA_NUM = 336;
                        break;
                    case "us":
                        MAX_MDATA_NUM = 395;
                        break;
                }
                break;
        }

    }
    int FontHeight;

    Paint paint = null;

    int btHeight = 5;


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        System.out.println("6: "+System.currentTimeMillis());
        if (FontHeight == 0) {
            paint = new Paint();
            paint.setTextSize(TEXTSIZE);
            FontMetrics fm = paint.getFontMetrics();
            FontHeight = (int) Math.ceil(fm.descent - fm.top) + 5;

        }

        distanceX = Util.stringWidth("10000.00", paint);
        screenW = (int) getWidth();
        screenH = (int) getHeight();
        startX = 3 + distanceX + 0.01f;

        if (isHengping) {
            startY = 2 * FontHeight + 5 + 0.1f;
        } else {
            startY = btHeight + 2 + 0.1f;
        }
        if (!isLarge) {
            startY = 2 + 0.1f;
        }
        if (!isLarge) {
            endX = screenW - 3 + 0.1f - Util.stringWidth("-10.00%", paint);
        } else {
            endX = screenW - 7 + 0.1f - Util.stringWidth("-10.00%", paint);
        }

        endY = screenH * 2 / 3 + 0.1f;
        volumeStartY = endY + FontHeight + 4;
        volumeEndY = screenH + 0.1f;

        paintEdge(canvas);

        paintTrendLine(canvas);

        expandMline(false);

        paintKD(canvas);

        paintVolumeLine(canvas);

        paintPJX(canvas);
//        System.out.println("7: "+System.currentTimeMillis());
        paintHighLight(canvas);
    }

    public MLineView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected List<List<Double>> getLineData() {
        return minDataList;
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
        return 0;
    }

    public MLineView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    /**
     * ��ǰ�����Ƹ���
     */
    public int curNum = 0;

    /**
     * ��Ʊ����--- ָ��0�����ɣ�1
     */
    byte stocktype;

    /**
     * �г���0�����У�1�����У�2
     */
    byte markettype;

    /**
     * ����������ɵ�N���ӿ�ʼ��ȡֵ��ΧΪ��������ܷ������0ֵ��ʼ
     */
    int starttime = 0;

    /**
     * һ�������˶��ٷ��ӵ����
     */
    int totletime;


    /**
     * ҵ�������
     */
    short packagetype = -1;

    public void clearData() {
        packagetype = -1;
        curNum = 0;
        stocktype = 0;
        savepoint = 2;
        markettype = 0;
        closeTrend = 0;
        starttime = 0;
        totletime = 0;
        minDataList = new ArrayList<>();
        maxVolume = 0;
        minVolume = 0;
        maxTrendDiff = 0;
        smmaCurve = new SMMACurve(MAX_MDATA_NUM, MAX_MDATA_NUM);
        this.postInvalidate();
    }

    public void setData(MinData data) {
        savepoint = 2;

        closeTrend = (float) data.getLast();
        minDataList = data.getMinData();
        curNum = minDataList.size();
        if (curNum == 0) {
            return;
        }
//        if (mlineType == FIVE_DAY_MINUTES_LINE && data.getTimes() != null) {
            times = data.getTimes();
//        }

        if (closeTrend <= 0) {

        }

        if (maxTrendDiff == 0) {// ��ֹ����Ϊ0
            maxTrendDiff = 0.0001f;
        }

//        System.out.println("4: "+System.currentTimeMillis());
        calcScales();
//        System.out.println("5: "+System.currentTimeMillis());
        this.postInvalidate();
    }


    public void calcScales() {
        smmaCurve.Count = minDataList.size();
        smmaCurve.Price = new double[MAX_MDATA_NUM];
        smmaCurve.Volume = new double[MAX_MDATA_NUM];
        smmaCurve.SMMA = new double[MAX_MDATA_NUM];
        smmaCurve.Amount = new double[MAX_MDATA_NUM];
        smmaCurve.Volume[0] = minDataList.get(0).get(1);
        smmaCurve.Amount[0] = minDataList.get(0).get(0) * minDataList.get(0).get(1);
        smmaCurve.SMMA[0] = minDataList.get(0).get(0);
        maxTrendDiff = (float) Math.abs(minDataList.get(0).get(0) - closeTrend);
        maxVolume = minDataList.get(0).get(1).longValue();
        minVolume = minDataList.get(0).get(1).longValue();

        for (int i = 1; i < minDataList.size() && i < MAX_MDATA_NUM; i++) {
            if (Math.abs(minDataList.get(i).get(0) - closeTrend) > maxTrendDiff) {
                maxTrendDiff = (float) Math.abs(minDataList.get(i).get(0) - closeTrend);
            }

            double curVolume = 0;

            if (mlineType == ONE_DAY_MINUTES_LINE) {
                curVolume = minDataList.get(i).get(1);
            } else {
                if (i % 49 == 0) {
                    curVolume = minDataList.get(i).get(1);
                } else {
                    curVolume = minDataList.get(i).get(1) - minDataList.get(i - 1).get(1);
                }
            }
//
//            System.out.println(i+" "+curVolume);

            if (curVolume > maxVolume) {
                maxVolume = (long) curVolume;
            } else if (curVolume < minVolume) {
                minVolume = (long) curVolume;
            }

            if (mlineType == ONE_DAY_MINUTES_LINE) {
                smmaCurve.Amount[i] = minDataList.get(i).get(0) * curVolume + smmaCurve.Amount[i - 1];
            } else {
                if (i % 49 == 0) {
                    smmaCurve.Amount[i] = minDataList.get(i).get(0) * curVolume;
                } else {
                    smmaCurve.Amount[i] = minDataList.get(i).get(0) * curVolume + smmaCurve.Amount[i - 1];
                }
            }
            smmaCurve.SMMA[i] = smmaCurve.Amount[i] / minDataList.get(i).get(1);

        }
        if (maxTrendDiff < closeTrend * 0.01f) {
            maxTrendDiff = closeTrend * 0.01f;
        }
    }


    private void paintEdge(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(mline_volbg);
        paint.setColor(0xff000000);


        paint.setColor(mline_biankuang);
        Util.drawRect((int) startX, (int) startY, (int) (endX - startX + 2), (int) (endY - startY), false, canvas, paint);
        Util.drawRect((int) startX, (int) volumeStartY, (int) (endX - startX + 2), (int) (volumeEndY - volumeStartY), false, canvas, paint);//量图边框

        paint.setStyle(Style.STROKE);
        paint.setTextSize(TEXTSIZE);


        float drawY = endY + FontHeight / 2 + 4;

        if (mlineType == FIVE_DAY_MINUTES_LINE) {

            for (int i = 1; i < 5; i++) {
                float evenWidth = (endX - startX) / 5.0f;
                canvas.drawLine(startX + evenWidth * i, startY, startX + evenWidth * i, endY, paint);
            }
            paint.setColor(ColorText);
            if (times.size() > 0) {
                float evenWidth = (endX - startX) / 5;
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd");
                for (int i = 0; i < times.size(); i++) {
                    String date = times.get(i);
                    float tmp = paint.measureText(date);
                    canvas.drawText(date, startX + evenWidth * (i + 0.5f) - tmp / 2, drawY, paint);

                }
            }
        } else if (mlineType == ONE_DAY_MINUTES_LINE) {
            float tmp = paint.measureText("09:30");
            float offsetX = (endX - startX - 0.1f) / (MAX_MDATA_NUM);
            float marketRestTimeX = 0;
            switch (market){
                case "hs":
                    marketRestTimeX = startX + offsetX * 121;
                    canvas.drawLine(marketRestTimeX, startY, marketRestTimeX, endY, paint);
                    paint.setColor(ColorText);
                    canvas.drawText("9:30", startX, drawY, paint);
                    canvas.drawText("11:30/13:00", marketRestTimeX - tmp, drawY, paint);
                    canvas.drawText("15:00", endX - tmp, drawY, paint);
                    break;
                case "hk":
                    marketRestTimeX = startX + offsetX * 151;
                    canvas.drawLine(marketRestTimeX, startY, marketRestTimeX, endY, paint);
                    paint.setColor(ColorText);
                    canvas.drawText("9:30", startX, drawY, paint);
                    canvas.drawText("12:00/13:00", marketRestTimeX - tmp, drawY, paint);
                    canvas.drawText("16:00", endX - tmp, drawY, paint);
                    break;
                case "us":
                    marketRestTimeX = startX + offsetX * 181;
                    canvas.drawLine(marketRestTimeX, startY, marketRestTimeX, endY, paint);
                    paint.setColor(ColorText);
                    canvas.drawText("9:30", startX, drawY, paint);
                    canvas.drawText("12:30", marketRestTimeX - tmp/2, drawY, paint);
                    canvas.drawText("16:00", endX - tmp, drawY, paint);
                    break;
            }
        }
        paint.setColor(mline_biankuang);
        canvas.drawLine(startX, startY + (endY - startY) * 2 / 4, endX + 1, startY + (endY - startY) * 2 / 4, paint);
        canvas.drawLine(startX, startY + (endY - startY) / 4, endX, startY + (endY - startY) / 4, paint);
        canvas.drawLine(startX, startY + (endY - startY) * 3 / 4, endX, startY + (endY - startY) * 3 / 4, paint);

    }

    Map<Integer, Float> hashMap = new HashMap<>();

    private void paintTrendLine(Canvas canvas) {
        if (minDataList != null && minDataList.size() > 0) {
            Paint linePaint = new Paint();
            linePaint.setAntiAlias(true);
            linePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
            // ���¿ճ�10%�Ŀռ�
            float trendHeight = (endY - startY) * 9 / 20;
            float offsetX = (endX - startX - 0.1f) / (MAX_MDATA_NUM);
            this.mOffsetX = offsetX;
            float lastX = 0.0f, lastY = 0.0f;
            float left, top;
            left = startX;
            Path path = new Path();

            for (int i = 0; i < minDataList.size() && i < MAX_MDATA_NUM; i++) {
                left += offsetX;
//                System.out.println("left: "+left);
                top = (float) ((endY - startY) / 2.0f + startY - (trendHeight) * (minDataList.get(i).get(0) - closeTrend + 0.0f) / maxTrendDiff);
                hashMap.put(i, top);
                minDataList.get(i).add((double) left);
                minDataList.get(i).add((double) top);
                if (i == 0) {
                    lastX = startX;
                    lastY = top;
                    path.moveTo(left - 2, endY - 1);
                    path.lineTo(left - 2, top);
                } else {
                    linePaint.setColor(mline_trendline);
                    linePaint.setStrokeWidth(2.0f);
                    if (left <= endX) {
                        canvas.drawLine(lastX, lastY, left, top, linePaint);
                    }
                    path.lineTo(left, top);
                    lastX = left;
                    lastY = top;
                }
//                System.out.println(" i: "+i+" lastX: "+lastX+" endX: "+endX+" offsetX: "+offsetX+" startX: "+startX+" offsetX: "+offsetX);
            }
            path.lineTo(lastX, endY - 1);
            path.close();
            path.setFillType(FillType.EVEN_ODD);
            int color = linePaint.getColor();
            linePaint.setColor(mline_trendfill);
            canvas.drawPath(path, linePaint);
            linePaint.setColor(color);
        }
    }

    public void paintPJX(Canvas canvas) {
//        if (mlineType == FIVE_DAY_MINUTES_LINE) {
//            return;
//        }
        if (minDataList != null && minDataList.size() > 0) {
            Paint linePaint = new Paint();
            linePaint.setAntiAlias(true);
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            LinearGradient gradient = new LinearGradient(2, endY, 2, 2, new int[]{Color.BLACK, ColorYellow}, null, Shader.TileMode.MIRROR);
            paint.setShader(gradient);
            float trendHeight = (endY - startY) * 9 / 20;
            System.out.println("trendHeight pjx： "+trendHeight);
            float offsetX = (endX - startX - 0.1f) / MAX_MDATA_NUM;
            float lastX = 0.0f, lastY = 0.0f;
            float left, top, right, bottom;
            left = startX;
            for (int i = 0; i < minDataList.size() && i < MAX_MDATA_NUM; i++) {

                left += offsetX;
//                top = (float) ((endY - startY) / 2 + startY - (trendHeight) * (smmaCurve.SMMA[i] - closeTrend + 0.0f) / maxTrendDiff);
                top = (float) ((endY - startY) / 2 + startY - (trendHeight) * (minDataList.get(i).get(2) - closeTrend + 0.0f) / maxTrendDiff);
                if (top < startY) {
                    top = startY;
                }
                right = left + offsetX;
                if (i == 0) {
                    lastX = startX;
                    lastY = top;
                } else {
                    linePaint.setColor(mline_junxian);
                    linePaint.setStrokeWidth(2.0f);
                    if (left <= endX) {
                        canvas.drawLine(lastX, lastY, left, top, linePaint);
                    }
                    lastX = left;
                    lastY = top;
                }
            }
        }
    }

    /**
     * �̶���ɵı߾�
     */
    float distanceX = 0;

    /**
     * С��㱣��λ��
     */
    int savepoint = 2;

    public void paintKD(Canvas canvas) {
        Paint linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setTextSize(TEXTSIZE);

        String kd1, kd2, kd3, kd4, kd5;

        kd1 = Util.saveNumFromat(String.valueOf((closeTrend + maxTrendDiff + 0.0f) + maxTrendDiff * 0.1), savepoint);
        kd2 = Util.saveNumFromat(String.valueOf((closeTrend + maxTrendDiff / 2 + 0.0f) + maxTrendDiff * 0.1), savepoint);
        kd3 = Util.saveNumFromat(String.valueOf((closeTrend + 0.0f)), savepoint);
        kd4 = Util.saveNumFromat(String.valueOf((closeTrend - maxTrendDiff / 2 + 0.0f) - maxTrendDiff * 0.1), savepoint);
        kd5 = Util.saveNumFromat(String.valueOf((closeTrend - maxTrendDiff + 0.0f) - maxTrendDiff * 0.1), savepoint);


        linePaint.setColor(CharColor);
        canvas.drawText(kd1, startX - Util.stringWidth(kd1, linePaint), startY + FontHeight / 2, linePaint);
        canvas.drawText(kd2, startX - Util.stringWidth(kd2, linePaint), (endY - startY) / 4 + FontHeight / 2, linePaint);
//        linePaint.setColor(CharColor);
        canvas.drawText(kd3, startX - Util.stringWidth(kd3, linePaint), startY + (endY - startY) / 2, linePaint);
//        linePaint.setColor(CharColor);
        canvas.drawText(kd4, startX - Util.stringWidth(kd4, linePaint), (endY - startY) * 3 / 4 + FontHeight / 2, linePaint);
        canvas.drawText(kd5, startX - Util.stringWidth(kd5, linePaint), endY, linePaint);

        String kd_1, kd_2, kd_3, kd_4, kd_5;
        if (closeTrend == 0) {
            kd_4 = kd_5 = kd_3 = kd_1 = kd_2 = "0.00%";
        } else {
            kd_1 = Util.saveNumFromat(String.valueOf((maxTrendDiff / closeTrend) * 100f), 2) + "%";
            kd_2 = Util.saveNumFromat(String.valueOf((maxTrendDiff / 2 / closeTrend) * 100f), 2) + "%";
            kd_3 = "0.00%";
            kd_4 = "-" + kd_2;
            kd_5 = "-" + kd_1;

            if ((maxTrendDiff / closeTrend > 0 && maxTrendDiff / closeTrend < 0.00001) || (maxTrendDiff / closeTrend < 0 && maxTrendDiff / closeTrend > -0.00001)) {
                kd_1 = "0.00%";
                kd_2 = "0.00%";
                kd_3 = "0.00%";
                kd_4 = "0.00%";
                kd_5 = "0.00%";
            }
        }

        linePaint.setColor(ColorRed);
        canvas.drawText(kd_1, endX + 2, startY + FontHeight / 2, linePaint);
        canvas.drawText(kd_2, endX + 2, (endY - startY) / 4 + FontHeight / 2, linePaint);
        linePaint.setColor(ColorText);
        canvas.drawText(kd_3, endX + 2, startY + (endY - startY) / 2, linePaint);
        linePaint.setColor(ColorGreen);
        canvas.drawText(kd_4, endX + 2, (endY - startY) * 3 / 4 + FontHeight / 2, linePaint);
        canvas.drawText(kd_5, endX + 2, endY, linePaint);
//        }
    }

    /**
     * �����Ƴɽ�����
     *
     * @param canvas
     */
    private void paintVolumeLine(Canvas canvas) {
        if (minDataList != null && minDataList.size() > 0) {
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            float volHeight = (volumeEndY - volumeStartY - 2);
            float offsetX;
            if (MAX_MDATA_NUM != 0) {
                offsetX = (endX - startX - 0.1f) / MAX_MDATA_NUM;
            } else {
                offsetX = (endX - startX - 1.0f) / curNum;
            }
            paint.setStrokeWidth(offsetX);
            float left, top, right, bottom;
            left = right = startX;
            paint.setColor(mline_trendline);



            double curVolume = 0;
            for (int i = 0; i < minDataList.size() && i < MAX_MDATA_NUM; i++) {
                if(i == 0){
                    paint.setColor(mline_trendline);
                }else{
                    if(minDataList.get(i).get(0) > minDataList.get(i - 1).get(0)){
                        paint.setColor(ColorRed);
                    }else if(minDataList.get(i).get(0) < minDataList.get(i - 1).get(0)){
                        paint.setColor(ColorGreen);
                    }else{
                        paint.setColor(mline_trendline);
                    }
                }


                left += offsetX;
                right = left > endX ? endX : left;
                bottom = this.volumeEndY - 1;

                if (mlineType == ONE_DAY_MINUTES_LINE) {
                    if (i == 0) {
                        curVolume = minDataList.get(i).get(1);
                    } else {
                        curVolume = minDataList.get(i).get(1) - minDataList.get(i - 1).get(1);
                    }
                } else {
                    if (i == 0 || i % 49 == 0) {
                        curVolume = minDataList.get(i).get(1);
                    } else {
                        curVolume = minDataList.get(i).get(1) - minDataList.get(i - 1).get(1);
                    }
                }


                float h = (float) (volHeight
                        * curVolume
                        / (maxVolume));
                top = bottom - h;

                canvas.drawLine(left, top, right, bottom, paint);
            }
            paint.setTextSize(TEXTSIZE);
            paint.setColor(CharColor);

            float x, y;
            String s;
            s = Util.translate_long_thousand((long) maxVolume / 100, 2, true) + "手";
            String[] content = s.split(",");
            x = startX - Util.stringWidth(content[0], paint);
            y = (int) (volumeStartY + FontHeight);
            canvas.drawText(content[0], x, y, paint);

            x = startX - Util.stringWidth(content[1], paint);
            y = (int) (volumeEndY - FontHeight / 2);
            canvas.drawText(content[1], x, y, paint);
        }
    }


    /**
     * �Ƿ�ȫ��
     */
    public boolean isLarge = true;

    /**
     * �Ƿ����
     */
    public boolean isHengping = false;

    /**
     * �����߷Ŵ�
     */
    public void expandMline(boolean isheng) {
        initViews();
        M_ZB_type = 0;
        isLarge = true;
        isYB = false;
    }

    /**
     * ���ʽ����ٻ��ǳɽ��� 0���ɽ��� 1���Y������ 2���ʽ�����
     */
    int M_ZB_type = 0;

    /**
     * ��ʼ������view
     */
    public void initViews() {

    }

    @Override
    protected Float getCurrentY(int index) {
        return hashMap.get(index);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public String getTime() {
        StringBuffer sb = new StringBuffer(String.valueOf(minDataList.get(getIndex()).get(2).longValue()));
        if (sb.length() == 3) {
            sb.insert(0, "0");
        }
        sb.insert(2, ":");

        return "时间：" + sb.toString();
    }

    public SpannableString getPrice() {
        double price = minDataList.get(getIndex()).get(0).doubleValue();
        SpannableString ss = new SpannableString("价格：" + DoubleUtil.formatDoubleDot2(price));
        if (getChangeRateValue() < 0) {
            ss.setSpan(new ForegroundColorSpan(getResources().getColor(android.R.color.holo_green_dark)), 0, ss.length(), SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
        } else if (getChangeRateValue() > 0) {
            ss.setSpan(new ForegroundColorSpan(getResources().getColor(android.R.color.holo_red_dark)), 0, ss.length(), SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
        } else {
            ss.setSpan(new ForegroundColorSpan(getResources().getColor(android.R.color.darker_gray)), 0, ss.length(), SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        return ss;
    }

    public SpannableString getChangeRate() {
        SpannableString ss = new SpannableString("涨幅：" + DoubleUtil.formatDoubleDot2(getChangeRateValue()) + "%");
        if (getChangeRateValue() < 0) {
            ss.setSpan(new ForegroundColorSpan(getResources().getColor(android.R.color.holo_green_dark)), 0, ss.length(), SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
        } else if (getChangeRateValue() > 0) {
            ss.setSpan(new ForegroundColorSpan(getResources().getColor(android.R.color.holo_red_dark)), 0, ss.length(), SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
        } else {
            ss.setSpan(new ForegroundColorSpan(getResources().getColor(android.R.color.darker_gray)), 0, ss.length(), SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        return ss;
    }


    public String getAmount() {
        return "成交" + Util.translate_long_thousand((long) (getIndex() == 0 ? minDataList.get(getIndex()).get(1) / 100 : (minDataList.get(getIndex()).get(1) - minDataList.get(getIndex() - 1).get(1)) / 100)) + "手";
    }

    public String getAvgPrice() {
        return "均价：" + DoubleUtil.formatDoubleDot2(smmaCurve.SMMA[getIndex()]);
    }

    public double getChangeRateValue() {
        double price = minDataList.get(getIndex()).get(0).doubleValue();
        return ((price - closeTrend) / closeTrend) * 100;
    }
}

