package com.example.admin.basic.model.search;

import java.util.List;

/**
 * @author Xinxin Shi
 */

public class DefaultSearchModel {

    /**
     * code : 200
     * msg :
     * data : {"history":["B"],"hotCoints":[{"id":907,"name":"LBA"},{"id":574,"name":"SWFTC"},
     * {"id":1098,"name":"DTA"},{"id":1114,"name":"MEET"},{"id":636,"name":"SOC"},{"id":898,
     * "name":"RUFF"},{"id":441,"name":"MTN"},{"id":482,"name":"THETA"},{"id":105,"name":"DAT"},
     * {"id":439,"name":"SNC"},{"id":389,"name":"BFT"},{"id":1119,"name":"MDS"},{"id":549,
     * "name":"STK"},{"id":1211,"name":"TOPC"},{"id":931,"name":"SMT"}],
     * "exchanges":[{"name":"币安","id":"binance","desc":"324个交易对"},{"name":"火币","id":"huobi",
     * "desc":"201个交易对"}]}
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
        private List<String> history;
        private List<HotCointsBean> hotCoints;
        private List<ExchangesBean> exchanges;

        public List<String> getHistory() {
            return history;
        }

        public void setHistory(List<String> history) {
            this.history = history;
        }

        public List<HotCointsBean> getHotCoints() {
            return hotCoints;
        }

        public void setHotCoints(List<HotCointsBean> hotCoints) {
            this.hotCoints = hotCoints;
        }

        public List<ExchangesBean> getExchanges() {
            return exchanges;
        }

        public void setExchanges(List<ExchangesBean> exchanges) {
            this.exchanges = exchanges;
        }

        public static class HotCointsBean {
            /**
             * id : 907
             * name : LBA
             */

            private int id;
            private String name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class ExchangesBean {
            /**
             * name : 币安
             * id : binance
             * desc : 324个交易对
             */

            private String name;
            private String id;
            private String desc;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }
        }
    }
}
