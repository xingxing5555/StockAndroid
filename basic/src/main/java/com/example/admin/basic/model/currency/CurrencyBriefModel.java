package com.example.admin.basic.model.currency;

/**
 * @author Xinxin Shi
 */

public class CurrencyBriefModel {



    private int code;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {


        private String desc;
        private String rank;
        private String volume;
        private String circulation;
        private String cost;
        private String time;
        private String url1;
        private String url2;
        private String cn_name;
        private String en_name;
        private String bourse;
        private String rate1;
        private String rate2;
        private String rate3;
        private String value;

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public String getVolume() {
            return volume;
        }

        public void setVolume(String volume) {
            this.volume = volume;
        }

        public String getCirculation() {
            return circulation;
        }

        public void setCirculation(String circulation) {
            this.circulation = circulation;
        }

        public String getCost() {
            return cost;
        }

        public void setCost(String cost) {
            this.cost = cost;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getUrl1() {
            return url1;
        }

        public void setUrl1(String url1) {
            this.url1 = url1;
        }

        public String getUrl2() {
            return url2;
        }

        public void setUrl2(String url2) {
            this.url2 = url2;
        }

        public String getCn_name() {
            return cn_name;
        }

        public void setCn_name(String cn_name) {
            this.cn_name = cn_name;
        }

        public String getEn_name() {
            return en_name;
        }

        public void setEn_name(String en_name) {
            this.en_name = en_name;
        }

        public String getBourse() {
            return bourse;
        }

        public void setBourse(String bourse) {
            this.bourse = bourse;
        }

        public String getRate1() {
            return rate1;
        }

        public void setRate1(String rate1) {
            this.rate1 = rate1;
        }

        public String getRate2() {
            return rate2;
        }

        public void setRate2(String rate2) {
            this.rate2 = rate2;
        }

        public String getRate3() {
            return rate3;
        }

        public void setRate3(String rate3) {
            this.rate3 = rate3;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
