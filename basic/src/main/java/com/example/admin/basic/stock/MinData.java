package com.example.admin.basic.stock;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MinData implements Serializable {

    private double last;

    private List<List<Double>> minData = new ArrayList<>();

//    private List<List<Double>> trading;
    private List<String> times = new ArrayList<>();

    public List<List<Double>> getMinData() {
        return minData;
    }

    public void setMinData(List<List<Double>> minData) {
        this.minData = minData;
    }
//
//    public List<List<Double>> getTrading() {
//        return trading;
//    }
//
//    public void setTrading(List<List<Double>> trading) {
//        this.trading = trading;
//    }

    public double getLast() {
        return last;
    }

    public void setLast(double last) {
        this.last = last;
    }

    public List<String> getTimes() {
        return times;
    }

    public void setTimes(List<String> times) {
        this.times = times;
    }


}
