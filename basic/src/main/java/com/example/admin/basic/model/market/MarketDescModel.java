package com.example.admin.basic.model.market;

/**
 * @author Xinxin Shi
 */

public class MarketDescModel {

    /**
     * code : 200
     * msg :
     * data : {"desc":"瑞波币是ripple网络的基础货币，它可以在整个ripple网络中流通，总数量为1000
     * 亿，并且随着交易的增多而逐渐减少，瑞波币的运营公司为ripplelabs（其前身为opencoin）。瑞波币是ripple系统中唯一的通用货币，其不同于ripple
     * 系统中的其他货币，其他货币比如cny、usd不能跨网关提现的，换句话说，a网关发行的cny只能在a网关提现，若想在b网关提现，必须通过ripple系统的挂单功能转化为b网关的cny
     * 才可以到b网关提现。而瑞波币完全没有这方面的限制，它在ripple系统内是通用的。瑞波币（xrp）和比特币一样都是基于数学和密码学的数字货币，但是与比特币没有真正的用途不同，xrp
     * 在ripple系统中有主要桥梁货币和有保障安全的功能，其中保障安全的功能是不可或缺的，这要求参与这个协议的网关都必须持有少量xrp。","rank":3,
     * "volume":99992200000,"circulation":100000000000,"cost":"-","time":"2011-04-18",
     * "url1":"https://ripple.com/","url2":"https://ripple.com/files/ripple_consensus_whitepaper
     * .pdf","rate1":"10.56%","rate2":"1.83%","rate3":"0.42%","value":"￥396647532079.106"}
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
         * desc : 瑞波币是ripple网络的基础货币，它可以在整个ripple网络中流通，总数量为1000
         * 亿，并且随着交易的增多而逐渐减少，瑞波币的运营公司为ripplelabs（其前身为opencoin）。瑞波币是ripple系统中唯一的通用货币，其不同于ripple
         * 系统中的其他货币，其他货币比如cny、usd不能跨网关提现的，换句话说，a网关发行的cny只能在a网关提现，若想在b网关提现，必须通过ripple系统的挂单功能转化为b
         * 网关的cny才可以到b网关提现。而瑞波币完全没有这方面的限制，它在ripple系统内是通用的。瑞波币（xrp
         * ）和比特币一样都是基于数学和密码学的数字货币，但是与比特币没有真正的用途不同，xrp在ripple
         * 系统中有主要桥梁货币和有保障安全的功能，其中保障安全的功能是不可或缺的，这要求参与这个协议的网关都必须持有少量xrp。
         * rank : 3
         * volume : 99992200000
         * circulation : 100000000000
         * cost : -
         * time : 2011-04-18
         * url1 : https://ripple.com/
         * url2 : https://ripple.com/files/ripple_consensus_whitepaper.pdf
         * rate1 : 10.56%
         * rate2 : 1.83%
         * rate3 : 0.42%
         * value : ￥396647532079.106
         */

        private String desc;
        private int rank;
        private String volume;
        private String circulation;
        private String cost;
        private String time;
        private String url1;
        private String url2;
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

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
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
