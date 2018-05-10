package com.example.admin.basic.model;

/**
 * 自选数据
 *
 * @author Xinxin Shi
 */

public class HomeCurrencyModel {
    private String name;
    private String type;
    private String price;
    private String foreignPrice;
    private String volume;
    private String increase;
    private String state;

    public HomeCurrencyModel(String name, String type, String price, String foreignPrice, String
            volume, String increase,String state) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.foreignPrice = foreignPrice;
        this.volume = volume;
        this.increase = increase;
        this.state=state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getForeignPrice() {
        return foreignPrice;
    }

    public void setForeignPrice(String foreignPrice) {
        this.foreignPrice = foreignPrice;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getIncrease() {
        return increase;
    }

    public void setIncrease(String increase) {
        this.increase = increase;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
