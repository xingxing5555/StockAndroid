package com.example.admin.basic.model.currency;

import android.text.TextUtils;

import com.example.admin.basic.stock.MinData;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Xinxin Shi
 */

public class MLineModel {
    private int code;
    private String msg;
    private List<DataBean> data;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * time : 2018/05/29 16:04:00
         * price : 0.00136000
         * volume : 0.09243003
         */

        private String time;
        private String price;
        private String volume;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getVolume() {
            return volume;
        }

        public void setVolume(String volume) {
            this.volume = volume;
        }
    }

    public MinData parseData(String yest) {
        double yestclose = 0.00;//昨收
        if (!TextUtils.isEmpty(yest)) {
            yestclose = Double.parseDouble(yest);
        }
        MinData minData = new MinData();
        List<List<Double>> outData = new ArrayList<>();
        List<String> time = new ArrayList<>();
        List<DataBean> dayLine = getData();
//        List<List<String>> data = getData();
        for (int i = 0; i < dayLine.size(); i++) {
            time.add(dayLine.get(i).getTime());
            List<Double> one = new ArrayList<>();
            one.add(Double.parseDouble(dayLine.get(i).getPrice()));//价格
            one.add(Double.parseDouble(dayLine.get(i).getVolume()));//发行量
            one.add(Double.parseDouble(dayLine.get(i).getPrice()));
            one.add(yestclose);
            outData.add(one);
        }
        minData.setLast(yestclose);
        minData.setMinData(outData);
        minData.setTimes(time);
        return minData;
    }
}
