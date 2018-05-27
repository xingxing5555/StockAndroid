package com.example.admin.basic.model.market;

/**
 * @author Xinxin Shi
 */

public class MarketInfoModel {
    /**
     * code : 200
     * msg :
     * data : {"barName":"XRP/USD","time":"2018/05/27 11:58:03",
     * "icon":"/uploads/currency/logo/36/c9eb965d2f25954f97cfd9d4d83fab6_small.png",
     * "cnName":"瑞波币","enName":"Ripple(XRP)","price1":"￥3.96678473","price2":"$0.620955",
     * "volumn":"415519643.130339558","amount":"258019000.0"}
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
         * barName : XRP/USD
         * time : 2018/05/27 11:58:03
         * icon : /uploads/currency/logo/36/c9eb965d2f25954f97cfd9d4d83fab6_small.png
         * cnName : 瑞波币
         * enName : Ripple(XRP)
         * price1 : ￥3.96678473
         * price2 : $0.620955
         * volumn : 415519643.130339558
         * amount : 258019000.0
         */

        private String barName;
        private String time;
        private String icon;
        private String cnName;
        private String enName;
        private String price1;
        private String price2;
        private String volumn;
        private String amount;

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

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getCnName() {
            return cnName;
        }

        public void setCnName(String cnName) {
            this.cnName = cnName;
        }

        public String getEnName() {
            return enName;
        }

        public void setEnName(String enName) {
            this.enName = enName;
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

        public String getVolumn() {
            return volumn;
        }

        public void setVolumn(String volumn) {
            this.volumn = volumn;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }
    }
}
