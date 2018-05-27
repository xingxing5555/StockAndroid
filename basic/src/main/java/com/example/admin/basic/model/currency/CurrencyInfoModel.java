package com.example.admin.basic.model.currency;

/**
 * @author Xinxin Shi
 */

public class CurrencyInfoModel {

    /**
     * code : 200
     * msg :
     * data : {"barName":"OMG/BTC","time":"2018/05/27 11:55:48","price1":"69.2640585",
     * "price2":"10.8425","updown":"-0.000047","rate":"-0.21%","open":"0.022294",
     * "close":"0.022247","high":"0.022728","low":"0.022118","last":"0.00144","buy1":"0.022241",
     * "sell1":"0.022319","volumn":"23134.14"}
     */

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
        /**
         * barName : OMG/BTC
         * time : 2018/05/27 11:55:48
         * price1 : 69.2640585
         * price2 : 10.8425
         * updown : -0.000047
         * rate : -0.21%
         * open : 0.022294
         * close : 0.022247
         * high : 0.022728
         * low : 0.022118
         * last : 0.00144
         * buy1 : 0.022241
         * sell1 : 0.022319
         * volumn : 23134.14
         */

        private String barName;
        private String time;
        private String price1;
        private String price2;
        private String updown;
        private String rate;
        private String open;
        private String close;
        private String high;
        private String low;
        private String last;
        private String buy1;
        private String sell1;
        private String volumn;

        public String getBarName() {
            return barName;
        }

        public void setBarName(String barName) {
            this.barName = barName;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getPrice1() {
            return price1;
        }

        public void setPrice1(String price1) {
            this.price1 = price1;
        }

        public String getPrice2() {
            return price2;
        }

        public void setPrice2(String price2) {
            this.price2 = price2;
        }

        public String getUpdown() {
            return updown;
        }

        public void setUpdown(String updown) {
            this.updown = updown;
        }

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

        public String getOpen() {
            return open;
        }

        public void setOpen(String open) {
            this.open = open;
        }

        public String getClose() {
            return close;
        }

        public void setClose(String close) {
            this.close = close;
        }

        public String getHigh() {
            return high;
        }

        public void setHigh(String high) {
            this.high = high;
        }

        public String getLow() {
            return low;
        }

        public void setLow(String low) {
            this.low = low;
        }

        public String getLast() {
            return last;
        }

        public void setLast(String last) {
            this.last = last;
        }

        public String getBuy1() {
            return buy1;
        }

        public void setBuy1(String buy1) {
            this.buy1 = buy1;
        }

        public String getSell1() {
            return sell1;
        }

        public void setSell1(String sell1) {
            this.sell1 = sell1;
        }

        public String getVolumn() {
            return volumn;
        }

        public void setVolumn(String volumn) {
            this.volumn = volumn;
        }
    }
}
