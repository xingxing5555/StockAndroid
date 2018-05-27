package com.example.admin.basic.utils;

import android.text.TextUtils;

import com.example.admin.basic.constants.Constants;

/**
 * @author Xinxin Shi
 */

public class CommonUtils {

    public static boolean isMinus(String value){
        if(TextUtils.isEmpty(value)||!value.contains(Constants.SIGN_PERCENT)){
            return true;
        }
        String[] split = value.split(Constants.SIGN_PERCENT);
        if(split.length!=1){
            return true;
        }
        double updownValue = Double.parseDouble(split[0]);
        if(updownValue>0){
            return false;
        }
        return true;
    }
}
