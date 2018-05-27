package com.example.admin.basic.model.home;

import java.util.List;

/**
 * @author Xinxin Shi
 */

public class HomeMarketModel {

    /**
     * code : 200
     * msg :
     * data : {"coins":[{"name":"BTC(比特币)","id":4,"value":"29009391138.0","price":"48235.956678",
     * "updown":"1.25%"},{"name":"XRP(瑞波币)","id":36,"value":"1648276975.8","price":"3.96678473",
     * "updown":"0.47%"},{"name":"ETH(以太坊)","id":19,"value":"12276203940.0",
     * "price":"3838.829085","updown":"0.84%"},{"name":"ATMC(ATMC)","id":890,
     * "value":"165975018.3","price":"22.1613685","updown":"0.75%"},{"name":"XLM(恒星币)","id":117,
     * "value":"281876130.9","price":"1.87143597","updown":"1.81%"},{"name":"SLR(SLR)","id":1020,
     * "value":"234812.34504","price":"1.73662578","updown":"-7.53%"},{"name":"XUC(雪币)","id":70,
     * "value":"1832033.5488","price":"37.91211442","updown":"1.19%"},{"name":"BCH(比特现金)",
     * "id":55,"value":"3689524074.6","price":"6618.81402","updown":"0.99%"},{"name":"EOS(柚子)",
     * "id":75,"value":"8766910152.0","price":"81.07520148","updown":"4.62%"},{"name":"VERI(VERI)
     * ","id":1526,"value":"3543464.2698","price":"527.25072582","updown":"6.76%"}],"nextPage":2}
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
         * coins : [{"name":"BTC(比特币)","id":4,"value":"29009391138.0","price":"48235.956678",
         * "updown":"1.25%"},{"name":"XRP(瑞波币)","id":36,"value":"1648276975.8",
         * "price":"3.96678473","updown":"0.47%"},{"name":"ETH(以太坊)","id":19,
         * "value":"12276203940.0","price":"3838.829085","updown":"0.84%"},{"name":"ATMC(ATMC)",
         * "id":890,"value":"165975018.3","price":"22.1613685","updown":"0.75%"},{"name":"XLM
         * (恒星币)","id":117,"value":"281876130.9","price":"1.87143597","updown":"1.81%"},
         * {"name":"SLR(SLR)","id":1020,"value":"234812.34504","price":"1.73662578",
         * "updown":"-7.53%"},{"name":"XUC(雪币)","id":70,"value":"1832033.5488",
         * "price":"37.91211442","updown":"1.19%"},{"name":"BCH(比特现金)","id":55,
         * "value":"3689524074.6","price":"6618.81402","updown":"0.99%"},{"name":"EOS(柚子)",
         * "id":75,"value":"8766910152.0","price":"81.07520148","updown":"4.62%"},{"name":"VERI
         * (VERI)","id":1526,"value":"3543464.2698","price":"527.25072582","updown":"6.76%"}]
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
             * name : BTC(比特币)
             * id : 4
             * value : 29009391138.0
             * price : 48235.956678
             * updown : 1.25%
             */

            private String name;
            private int id;
            private String value;
            private String price;
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

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
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
