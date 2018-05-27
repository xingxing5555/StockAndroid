package com.example.admin.basic.model.search;

import java.util.List;

/**
 * @author Xinxin Shi
 */

public class SearchModel {

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
        private List<CoinsBean> coins;

        public List<CoinsBean> getCoins() {
            return coins;
        }

        public void setCoins(List<CoinsBean> coins) {
            this.coins = coins;
        }

        public static class CoinsBean {
            /**
             * name : BNB
             * currency : BTC
             * id : 2
             * market : 币安
             * volume : 98960.36
             * change : BTC
             * price1 : ￥83.32257024
             * price2 : 0.016054
             * rate : 0.31%
             * isSelfSelected : false
             */

            private String name;
            private String currency;
            private int id;
            private String market;
            private String volume;
            private String change;
            private String price1;
            private String price2;
            private String rate;
            private boolean isSelfSelected;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCurrency() {
                return currency;
            }

            public void setCurrency(String currency) {
                this.currency = currency;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getMarket() {
                return market;
            }

            public void setMarket(String market) {
                this.market = market;
            }

            public String getVolume() {
                return volume;
            }

            public void setVolume(String volume) {
                this.volume = volume;
            }

            public String getChange() {
                return change;
            }

            public void setChange(String change) {
                this.change = change;
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

            public String getRate() {
                return rate;
            }

            public void setRate(String rate) {
                this.rate = rate;
            }

            public boolean isIsSelfSelected() {
                return isSelfSelected;
            }

            public void setIsSelfSelected(boolean isSelfSelected) {
                this.isSelfSelected = isSelfSelected;
            }
        }
    }
}
