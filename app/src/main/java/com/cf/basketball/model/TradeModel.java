package com.cf.basketball.model;

/**
 * @author Xinxin Shi
 */

public class TradeModel {
    private String name;
    private String volume;

    public TradeModel(String name, String volume) {
        this.name = name;
        this.volume = volume;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }
}
