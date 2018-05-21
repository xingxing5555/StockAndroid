package com.example.admin.basic.model;

import com.example.admin.basic.stock.MinData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luoyu on 2017/5/26.
 */

public class HSFiveDayModel {
    private String symbol;
    private String name;
    private List<DataBean> data;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public MinData parseData(HSTodayModel today, String market) {
        MinData minData = new MinData();
        List<List<Double>> outData = new ArrayList<>();
        List<String> time = new ArrayList<>();

        for (int i = 0; i < getData().size() + 1; i++) {
            ArrayList<List<String>> temp = new ArrayList<>();

            if(i != 4){
                DataBean bean = getData().get(3-i);
                time.add(bean.getDate());
                temp.addAll(bean.getData());
                if(market.equals("hs")){
                    temp.remove(121);
                }else if(market.equals("hk")){
                    temp.remove(166);
                }
            }else{
                temp = new ArrayList<>(today.getData().size());
                temp.addAll(today.getData());

                    if(market.equals("hs")){
                        if(temp.size() > 121) {
                            temp.remove(121);
                        }
                    }else if(market.equals("hk")){
                        if(temp.size() > 166) {
                            temp.remove(166);
                        }
                    }

                time.add(today.getDate());
            }
            int j = 0;
            for (; j < temp.size(); j += 5) {

                List<Double> one = new ArrayList<>();
                one.add(Double.parseDouble(temp.get(j).get(1)));
                one.add(Double.parseDouble(temp.get(j).get(3)));
                one.add(Double.parseDouble(temp.get(j).get(2)));
                outData.add(one);
            }
        }
        minData.setLast(getData().get(0).yestclose);
        minData.setMinData(outData);
        minData.setTimes(time);
        return minData;
    }

    public static class DataBean {
        private int count;
        private double yestclose;
        private int lastVolume;
        private String date;
        private List<List<String>> data;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public double getYestclose() {
            return yestclose;
        }

        public void setYestclose(double yestclose) {
            this.yestclose = yestclose;
        }

        public int getLastVolume() {
            return lastVolume;
        }

        public void setLastVolume(int lastVolume) {
            this.lastVolume = lastVolume;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public List<List<String>> getData() {
            return data;
        }

        public void setData(List<List<String>> data) {
            this.data = data;
        }
    }
}
