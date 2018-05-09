package com.cf.basketball.stock;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DayData implements Serializable{

    private List<String> times = new ArrayList<>();

    private List<List<Double>> kData;

    public List<List<Double>> getKData() {
        return kData;
    }

    public void setKData(List<List<Double>> kData) {
        this.kData = kData;
    }

    public List<String> getTimes() {
        return times;
    }

    public void setTimes(List<String> times) {
        this.times = times;
    }
}