package com.cf.basketball.model;

/**
 * @author Xinxin Shi
 */

public class CurrencyInfoNewsModel {
    private String news;
    private String time;
    private String picture;

    public CurrencyInfoNewsModel(String news, String time, String picture) {
        this.news = news;
        this.time = time;
        this.picture = picture;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
