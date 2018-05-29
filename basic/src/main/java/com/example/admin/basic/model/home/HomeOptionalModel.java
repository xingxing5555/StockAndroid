package com.example.admin.basic.model.home;

import java.util.List;

/**
 * @author Xinxin Shi
 */

public class HomeOptionalModel {
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
             * name : BTC
             * id : 280
             * market : 火币
             * volume : 77778428.01546898
             * change : USDT
             * price1 : ￥48235.956678
             * price2 : 7324.68
             * updown : -2.53%
             */

            private String name;
            private int id;
            private String market;
            private String volume;
            private String change;
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

            public String getUpdown() {
                return updown;
            }

            public void setUpdown(String updown) {
                this.updown = updown;
            }
        }
    }
}
