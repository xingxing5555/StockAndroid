package com.example.admin.basic.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author Xinxin Shi
 */

public class DateUtils {

    public static String getCurrentTime(){
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd\tHH:mm:ss");
        return sdf.format(d);
    }
}
