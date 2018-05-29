package com.example.admin.basic.model.home;

import java.util.List;

/**
 * @author Xinxin Shi
 */

public class HomeType4Model {

    /**
     * code : 200
     * msg :
     * data : {"coins":[{"name":"DTA","id":430,"volume":"376081862.0216108","change":"ETH",
     * "price1":"￥0.08364965","price2":"0.00002184","updown":"0.23%"},{"name":"LBA","id":329,
     * "volume":"354047859.201975","change":"BTC","price1":"￥1.47111303","price2":"0.00003156",
     * "updown":"5.62%"},{"name":"LBA","id":412,"volume":"282006781.8129695","change":"ETH",
     * "price1":"￥1.47111303","price2":"0.000401","updown":"6.93%"},{"name":"DTA","id":342,
     * "volume":"249118958.806292","change":"BTC","price1":"￥0.08364965","price2":"0.00000172",
     * "updown":"-1.71%"},{"name":"DTA","id":300,"volume":"206873798.0045451","change":"USDT",
     * "price1":"￥0.08364965","price2":"0.01261873","updown":"-3.72%"},{"name":"SWFTC","id":448,
     * "volume":"201301522.0701839","change":"ETH","price1":"￥0.07170499","price2":"0.00001836",
     * "updown":"-0.81%"},{"name":"SWFTC","id":355,"volume":"185778687.4252291","change":"BTC",
     * "price1":"￥0.07170499","price2":"0.00000145","updown":"-2.03%"},{"name":"TNB","id":375,
     * "volume":"183653366.6890235","change":"BTC","price1":"￥0.23530232","price2":"0.00000503",
     * "updown":"3.29%"},{"name":"TRX","id":422,"volume":"182429846.5614233","change":"ETH",
     * "price1":"￥0.46271457","price2":"0.00012509","updown":"4.22%"},{"name":"TRX","id":335,
     * "volume":"170449110.0879826","change":"BTC","price1":"￥0.46271457","price2":"0.00000986",
     * "updown":"3.03%"}],"nextPage":2}
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
        private int nextPage;
        private List<CoinsBean> coins;

        public int getNextPage() {
            return nextPage;
        }

        public void setNextPage(int nextPage) {
            this.nextPage = nextPage;
        }

        public List<CoinsBean> getCoins() {
            return coins;
        }

        public void setCoins(List<CoinsBean> coins) {
            this.coins = coins;
        }

        public static class CoinsBean {
            /**
             * name : DTA
             * id : 430
             * volume : 376081862.0216108
             * change : ETH
             * price1 : ￥0.08364965
             * price2 : 0.00002184
             * updown : 0.23%
             */

            private String name;
            private int id;
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
