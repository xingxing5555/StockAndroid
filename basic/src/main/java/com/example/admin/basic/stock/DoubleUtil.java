package com.example.admin.basic.stock;

import java.text.DecimalFormat;

/**
 * Created by klcf on 2016/4/6.
 */
public class DoubleUtil {
    /**
     * The BigDecimal class provides operations for arithmetic, scale manipulation, rounding, comparison, hashing, and format conversion.
     *
     * @param d
     * @return
     */
    public static String formatDoubleDot2(double d) {
        DecimalFormat df = new DecimalFormat("0.00");//格式化小数
        return df.format(d);
    }

    public static String formatFloatDot2(float f) {
        DecimalFormat df = new DecimalFormat("0.00");//格式化小数
        return df.format(f);
    }

    public static String formatFloatDot1(float f) {
        DecimalFormat df = new DecimalFormat("0.0");//格式化小数
        return df.format(f);
    }
}
