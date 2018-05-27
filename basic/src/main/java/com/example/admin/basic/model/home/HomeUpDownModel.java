package com.example.admin.basic.model.home;

import java.util.List;

/**
 * @author Xinxin Shi
 */

public class HomeUpDownModel {

    /**
     * code : 200
     * msg :
     * data : {"coins":[{"name":"BTC","id":280,"market":"火币","price1":"￥48235.956678",
     * "price2":"$7537.14","updown":"1.14%"},{"name":"BCH","id":281,"market":"火币",
     * "price1":"￥6618.81402","price2":"$1038.19","updown":"1.88%"},{"name":"ETH","id":282,
     * "market":"火币","price1":"￥3838.829085","price2":"$603.98","updown":"1.65%"},{"name":"DASH",
     * "id":288,"market":"火币","price1":"￥2157.6209382","price2":"$335.88","updown":"-2.0%"},
     * {"name":"ZEC","id":289,"market":"火币","price1":"￥1778.9156658","price2":"$279.44",
     * "updown":"-1.53%"},{"name":"LTC","id":284,"market":"火币","price1":"￥776.8178964",
     * "price2":"$120.7","updown":"1.14%"},{"name":"NEO","id":301,"market":"火币",
     * "price1":"￥341.75017422","price2":"$54.08","updown":"2.29%"},{"name":"ETC","id":283,
     * "market":"火币","price1":"￥99.92550204","price2":"$15.6891","updown":"2.5%"},{"name":"QTUM",
     * "id":302,"market":"火币","price1":"￥86.69873394","price2":"$13.52","updown":"1.96%"},
     * {"name":"EOS","id":285,"market":"火币","price1":"￥81.07520148","price2":"$12.7",
     * "updown":"4.76%"}],"nextPage":2}
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
         * coins : [{"name":"BTC","id":280,"market":"火币","price1":"￥48235.956678",
         * "price2":"$7537.14","updown":"1.14%"},{"name":"BCH","id":281,"market":"火币",
         * "price1":"￥6618.81402","price2":"$1038.19","updown":"1.88%"},{"name":"ETH","id":282,
         * "market":"火币","price1":"￥3838.829085","price2":"$603.98","updown":"1.65%"},
         * {"name":"DASH","id":288,"market":"火币","price1":"￥2157.6209382","price2":"$335.88",
         * "updown":"-2.0%"},{"name":"ZEC","id":289,"market":"火币","price1":"￥1778.9156658",
         * "price2":"$279.44","updown":"-1.53%"},{"name":"LTC","id":284,"market":"火币",
         * "price1":"￥776.8178964","price2":"$120.7","updown":"1.14%"},{"name":"NEO","id":301,
         * "market":"火币","price1":"￥341.75017422","price2":"$54.08","updown":"2.29%"},
         * {"name":"ETC","id":283,"market":"火币","price1":"￥99.92550204","price2":"$15.6891",
         * "updown":"2.5%"},{"name":"QTUM","id":302,"market":"火币","price1":"￥86.69873394",
         * "price2":"$13.52","updown":"1.96%"},{"name":"EOS","id":285,"market":"火币",
         * "price1":"￥81.07520148","price2":"$12.7","updown":"4.76%"}]
         * nextPage : 2
         */

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
             * name : BTC
             * id : 280
             * market : 火币
             * price1 : ￥48235.956678
             * price2 : $7537.14
             * updown : 1.14%
             */

            private String name;
            private int id;
            private String market;
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
