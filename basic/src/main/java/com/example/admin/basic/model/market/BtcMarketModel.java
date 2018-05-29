package com.example.admin.basic.model.market;

import java.util.List;

/**
 * @author Xinxin Shi
 */

public class BtcMarketModel {

    /**
     * code : 200
     * msg :
     * data : {"names":["币安","火币"],"rates":["0.01%","0.63%"],"amount":"258019000.0",
     * "markets":[{"name":"币安","id":36,"value":"201134.01","price1":"0.0451314444",
     * "price2":"0.007077","updown":"1.0%"},{"name":"火币","id":36,"value":"13146492.26164449",
     * "price1":"3.8709604","price2":"0.607","updown":"-1.33%"}]}
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
         * names : ["币安","火币"]
         * rates : ["0.01%","0.63%"]
         * amount : 258019000.0
         * markets : [{"name":"币安","id":36,"value":"201134.01","price1":"0.0451314444",
         * "price2":"0.007077","updown":"1.0%"},{"name":"火币","id":36,"value":"13146492.26164449",
         * "price1":"3.8709604","price2":"0.607","updown":"-1.33%"}]
         */

        private String amount;
        private List<String> names;
        private List<String> rates;
        private List<MarketsBean> markets;

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public List<String> getNames() {
            return names;
        }

        public void setNames(List<String> names) {
            this.names = names;
        }

        public List<String> getRates() {
            return rates;
        }

        public void setRates(List<String> rates) {
            this.rates = rates;
        }

        public List<MarketsBean> getMarkets() {
            return markets;
        }

        public void setMarkets(List<MarketsBean> markets) {
            this.markets = markets;
        }

        public static class MarketsBean {
            /**
             * name : 币安
             * id : 36
             * value : 201134.01
             * price1 : 0.0451314444
             * price2 : 0.007077
             * updown : 1.0%
             */

            private String name;
            private int id;
            private String value;
            private String price1;
            private String price2;
            private String updown;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
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
        }
    }
}
