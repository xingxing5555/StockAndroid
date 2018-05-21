package com.example.admin.basic.stock;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by luoyu on 16/7/15.
 */
public class Util {

    public static boolean checkPermission(Context context, String permission) {
        boolean result = false;
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                Class<?> clazz = Class.forName("android.content.Context");
                Method method = clazz.getMethod("checkSelfPermission", String.class);
                int rest = (Integer) method.invoke(context, permission);
                if (rest == PackageManager.PERMISSION_GRANTED) {
                    result = true;
                } else {
                    result = false;
                }
            } catch (Exception e) {
                result = false;
            }
        } else {
            PackageManager pm = context.getPackageManager();
            if (pm.checkPermission(permission, context.getPackageName()) == PackageManager.PERMISSION_GRANTED) {
                result = true;
            }
        }
        return result;
    }

    public static String getDeviceInfo(Context context) {
        try {
            org.json.JSONObject json = new org.json.JSONObject();
            android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String device_id = null;
            if (checkPermission(context, Manifest.permission.READ_PHONE_STATE)) {
                device_id = tm.getDeviceId();
            }
            String mac = null;
            FileReader fstream = null;
            try {
                fstream = new FileReader("/sys/class/net/wlan0/address");
            } catch (FileNotFoundException e) {
                fstream = new FileReader("/sys/class/net/eth0/address");
            }
            BufferedReader in = null;
            if (fstream != null) {
                try {
                    in = new BufferedReader(fstream, 1024);
                    mac = in.readLine();
                } catch (IOException e) {
                } finally {
                    if (fstream != null) {
                        try {
                            fstream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            json.put("mac", mac);
            if (TextUtils.isEmpty(device_id)) {
                device_id = mac;
            }
            if (TextUtils.isEmpty(device_id)) {
                device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),
                        android.provider.Settings.Secure.ANDROID_ID);
            }
            json.put("device_id", device_id);
            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void drawString(String text, float x, float y, int anchor,
                                  Canvas g, Paint paint) {

        Paint p = paint;
        float cord_x = 0;
        float cord_y = 0;
        float H = -((int) p.getFontMetrics().top - 1);
        switch (anchor) {
            case 0:// top left
                p.setTextAlign(Paint.Align.LEFT);
                cord_x = x;
                cord_y = y + H;
                g.drawText(text, cord_x, cord_y, p);
                // g.drawLine(cord_x, cord_y, 200, cord_y, paint);
                break;
            case 1:// middle left
                p.setTextAlign(Paint.Align.LEFT);
                cord_x = x;
                cord_y = y + H / 2;
                g.drawText(text, cord_x, cord_y, p);
                break;
            case 2:// top Right
                p.setTextAlign(Paint.Align.RIGHT);
                cord_x = x;
                cord_y = y + H;
                g.drawText(text, cord_x, cord_y, p);
                // g.drawLine(cord_x, cord_y, 200, cord_y, paint);
                break;
            case 3:// top middle
                p.setTextAlign(Paint.Align.CENTER);
                cord_x = x;
                cord_y = y + H;
                g.drawText(text, cord_x, cord_y, p);
                break;
            case 4:// bottom left
                p.setTextAlign(Paint.Align.LEFT);
                cord_x = x;
                cord_y = y;
                g.drawText(text, cord_x, cord_y, p);
                break;
            case 5:// bottom right
                p.setTextAlign(Paint.Align.RIGHT);
                cord_x = x;
                cord_y = y;
                g.drawText(text, cord_x, cord_y, p);
                // g.draw
                break;
        }
    }

    public static float stringWidth(String s, Paint paint) {

        if (s == null || "".equals(s))
            return 0;
        float[] width = new float[s.length()];
        paint.getTextWidths(s, width);
        float sum = 0;
        for (int i = 0; i < width.length; i++) {
            sum += width[i];
        }
        return  sum + 1;
    }

    public static void drawRect(int x, int y, int w, int h, boolean isFill,
                                Canvas g, Paint paint) {

        Paint p = paint;
        if (isFill) {
            p.setStyle(Paint.Style.FILL);
        } else {
            p.setStyle(Paint.Style.STROKE);
        }
        g.drawRect(x, y, x + w, y + h, p);
    }

    public static void drawRect(float x, float y, float w, float h, boolean isFill,
                                Canvas g, Paint paint) {

        Paint p = paint;
        if (isFill) {
            p.setStyle(Paint.Style.FILL);
        } else {
            p.setStyle(Paint.Style.STROKE);
        }
        g.drawRect(x, y, x + w, y + h, p);
    }

    public static boolean isNum(String str) {

        return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
    }

    public static String saveNumFromat(String num1, int savepoint) {

        boolean isF = false;
        if (!isNum(num1)) {
            return num1;
        }
        if (num1 == null) {
            return "--";
        }
        if (num1.startsWith("-")) {
            isF = true;
            num1 = num1.substring(1);
        }
        // Logout.sysout("num1:"+num1);
        int index = num1.indexOf(".");
        if (index == -1) {
            return num1;
        }
        double num = Double.parseDouble(num1);
        String s;
        if (savepoint == 0) {
            s = String.valueOf((int) (num + 0.5));
            s = s.substring(0, index);
            return s;
        }
        int temp = 1;
        for (int i = 0; i < savepoint; i++) {
            temp *= 10;
        }
        int numInt = (int) (num * temp + 0.5);
        num = (double) numInt / temp;
        s = String.valueOf(num);
        int id = 0;
        if (s.indexOf('.', id) != -1) {
            s = s + "00000";
            s = s.substring(id, s.indexOf('.', id) + savepoint + 1);
        }
        if (isF) {
            s = "-" + s;
        }
        return s;
    }

    public static String processKD(String v, int scale) {

        if (scale == 0) {
            int index = v.indexOf(".");
            if (index != -1) {
                String str = v.substring(0, index);
                String s1 = v.substring(index + 1, index + 2);
                if (Util.parseInt(s1) >= 5)
                    str = String.valueOf(Util.parseInt(str) + 1);
                return str;
            }
        }
        BigDecimal b = new BigDecimal(v);
        BigDecimal one = new BigDecimal("1");
        double bb = b.divide(one, scale, BigDecimal.ROUND_HALF_UP)
                .doubleValue();
        String ss = String.valueOf(bb) + "000";
        int index = ss.indexOf(".");
        if (index != -1) {
            String s1 = ss.substring(0, index);
            String s2 = ss.substring(index + 1);
            if (s2.length() > scale) {
                s2 = s2.substring(0, scale);
            }
            ss = s1 + "." + s2;
        }
        return ss;
    }

    public static int parseInt(String srcString) {

        int returnVal = 0;
        try {
            returnVal = Integer.parseInt(srcString);
        } catch (NumberFormatException ne) {
        } catch (Exception e) {
        }
        return returnVal;
    }

    public static String translate_long_thousand(long num) {
        return translate_long_thousand(num, 2, false);
    }

    public static String translate_long_thousand(long num, int savePoint, boolean split) {
        String enter;
        if (split) {
            enter = ",";
        } else {
            enter = "";
        }
        String u = "";
        double num1 = 0;
        if (Math.abs(num) >= 100000000) {
            u = enter + "亿";
            num1 = num / 100000000.0;
        } else if (Math.abs(num) >= 10000) {
            u = enter + "万";
            num1 = num / 10000.0;
        } else {
            return enter + String.valueOf((int) num);
        }
        String temp = "0.";
        for (int i = 0; i < savePoint; i++) {
            temp += "#";
        }
        DecimalFormat df = new DecimalFormat(temp);
        return df.format(num1).concat(u);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
