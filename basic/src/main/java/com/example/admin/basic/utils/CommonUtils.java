package com.example.admin.basic.utils;

import android.text.TextUtils;

import com.example.admin.basic.constants.Constants;
import com.example.admin.basic.model.home.HomeOptionalModel;

import java.util.List;

/**
 * @author Xinxin Shi
 */

public class CommonUtils {

    public static boolean isMinus(String value) {
        if (TextUtils.isEmpty(value) || !value.contains(Constants.SIGN_PERCENT)) {
            return true;
        }
        String[] split = value.split(Constants.SIGN_PERCENT);
        if (split.length != 1) {
            return true;
        }
        double updownValue = Double.parseDouble(split[0]);
        if (updownValue > 0) {
            return false;
        }
        return true;
    }

    public static String changeLocation(List<HomeOptionalModel.DataBean.CoinsBean> list, int
            oldPosition, int newPosition) {
        LogUtils.e("oldPosition="+oldPosition+";newPosition="+newPosition);
        StringBuilder sb = new StringBuilder();
        for (HomeOptionalModel.DataBean.CoinsBean bean : list) {
            LogUtils.e("原始list:" + bean.getId());
        }
        HomeOptionalModel.DataBean.CoinsBean oldCoinsBean = list.get(oldPosition);
        HomeOptionalModel.DataBean.CoinsBean newCoinsBean = list.get(newPosition);
        if (oldPosition < newPosition) {
            list.add(oldPosition, newCoinsBean);
            list.add(newPosition + 1, newCoinsBean);
        } else {
            list.add(oldPosition, newCoinsBean);
            list.add(newPosition, oldCoinsBean);
        }
        list.remove(oldPosition + 1);
        list.remove(newPosition + 1);
        for (int i = 0; i < list.size(); i++) {
            HomeOptionalModel.DataBean.CoinsBean coinsBean = list.get(i);
            LogUtils.e("新list:" + coinsBean.getId());
            sb.append(coinsBean.getId());
            if (i != list.size() - 1) {
                sb.append(",");
            }
        }

        return sb.toString();
    }

    public static String getIds(List<HomeOptionalModel.DataBean.CoinsBean> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            HomeOptionalModel.DataBean.CoinsBean coinsBean = list.get(i);
            sb.append(coinsBean.getId());
            if (i != list.size() - 1) {
                sb.append(",");
            }
        }

        return sb.toString();
    }
}
