package com.example.admin.basic.model.currency;

import java.util.List;

/**
 * @author Xinxin Shi
 */

public class KLineModel {


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


        private String last;
        private int klineType;
        private List<KlineBean> kline;

        public String getLast() {
            return last;
        }

        public void setLast(String last) {
            this.last = last;
        }

        public int getKlineType() {
            return klineType;
        }

        public void setKlineType(int klineType) {
            this.klineType = klineType;
        }

        public List<KlineBean> getKline() {
            return kline;
        }

        public void setKline(List<KlineBean> kline) {
            this.kline = kline;
        }

        public static class KlineBean {
            /**
             * open : 0.007314
             * close : 0.00731
             * high : 0.007316
             * low : 0.00731
             * time : 2018-05-25 05
             */

            private String open;
            private String close;
            private String high;
            private String low;
            private String time;

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

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }
    }
}
