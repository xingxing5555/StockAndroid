package com.cf.basketball.stock;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zhengyonghui on 15/9/7.
 */
public class DateUtils {
    /**
     * 按要求格式返回当前时间
     *
     * @param dateType "yy-MM-dd","yy-MM-dd HH:mm:ss"等
     * @return
     */
    public static String getDateStrNow(String dateType) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateType);
        return formatter.format(new Date());
    }

    public static Calendar getDateNow() {
        Calendar now = Calendar.getInstance();
/*        System.out.println("年: " + now.get(Calendar.YEAR));
        System.out.println("月: " + (now.get(Calendar.MONTH) + 1) + "");
        System.out.println("日: " + now.get(Calendar.DAY_OF_MONTH));
        System.out.println("时: " + now.get(Calendar.HOUR_OF_DAY));
        System.out.println("分: " + now.get(Calendar.MINUTE));
        System.out.println("秒: " + now.get(Calendar.SECOND));
        System.out.println("当前时间毫秒数：" + now.getTimeInMillis());
        System.out.println(now.getTime());*/
        return now;
    }

    public static String getDateStr(long time, String dateType) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateType);
        return formatter.format(new Date(time));
    }

    public static Date getDateByStr(String date, String dateType) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateType);
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            return new Date();
        }
    }

    public static String getSectionByTime(long secondDiff) {
        long subsecond = System.currentTimeMillis() - secondDiff;
        if (subsecond >= 86400 * 1000) {
            return getDateStr(secondDiff, "MM-dd HH:mm");
        } else if (subsecond >= (3600 * 1000)) {
            int hour = (int) (subsecond / (3600 * 1000));
            return hour + "小时前";
        } else if (subsecond >= 60 * 1000) {
            int minute = (int) (subsecond / (60 * 1000));
            return minute + "分钟前";
        } else if (subsecond > 1000) {
            return (subsecond / 1000) + "秒前";
        } else {
            return "1秒前";
        }
    }


    /**
     * 返回5天23小时43分20秒
     *
     * @param secondDiff
     * @param isContinue
     * @return
     */
    public static String getReckonByTime(long secondDiff, boolean isContinue) {
        String time = "";
        if (secondDiff >= 86400 * 1000) {
            int day = (int) (secondDiff / (86400 * 1000));
            time = day + "天";
            if (isContinue) {
                time += getReckonByTime(secondDiff % (86400 * 1000), isContinue);
            }
        } else if (secondDiff >= (3600 * 1000)) {
            int hour = (int) (secondDiff / (3600 * 1000));
            time = hour + "小时";
            if (isContinue) {
                time += getReckonByTime(secondDiff % (3600 * 1000), isContinue);
            }
        } else if (secondDiff >= 60) {
            int minute = (int) (secondDiff / (60 * 1000));
            time = minute + "分";
            if (isContinue) {
                time += getReckonByTime(secondDiff % (60 * 1000), isContinue);
            }
        } else if (secondDiff > 0) {
            time = secondDiff + "秒";
        }
        return time;
    }

    public static boolean isReadCacheValid(long time) {
        //Logger.i("time space"+(System.currentTimeMillis() - time) * 1.0 / 1000);
        return (System.currentTimeMillis() - time) * 1.0 / (1000 * 3600 * 24) < 7;
        //return (System.currentTimeMillis() - time) * 1.0 / 1000 < 10;
    }

    public static boolean isSignTipValid(long time) {
//        return false;
//        return (System.currentTimeMillis() - time) <= 60000;
        return (System.currentTimeMillis() - time) * 1.0 / (1000 * 3600) <= 24;
    }
}
