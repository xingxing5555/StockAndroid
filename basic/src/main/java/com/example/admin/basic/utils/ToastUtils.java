package com.example.admin.basic.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * @author Xinxin Shi
 */

public class ToastUtils {

    public static void toastShot(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
