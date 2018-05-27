package com.example.admin.basic.model.currency;

import java.util.List;

/**
 * @author Xinxin Shi
 */

public class MLineModel {

    /**
     * code : 200
     * msg :
     * data : {"dayLine":[{"time":"11:11","volume":"286.97","price":"2.03091687"},
     * {"time":"11:12","volume":"215.49","price":"1.52380319"},{"time":"11:13","volume":"103.01",
     * "price":"0.72834654"},{"time":"11:14","volume":"485.27","price":"3.4322064"},
     * {"time":"11:15","volume":"212.12","price":"1.50083816"},{"time":"11:16","volume":"447.37",
     * "price":"3.16504086"},{"time":"11:17","volume":"212.59","price":"1.50368073"},
     * {"time":"11:18","volume":"101.83","price":"0.72010863"},{"time":"11:19","volume":"36.88",
     * "price":"0.26066805"},{"time":"11:20","volume":"13.04","price":"0.09220882"}]}
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
        private List<DayLineBean> dayLine;

        public List<DayLineBean> getDayLine() {
            return dayLine;
        }

        public void setDayLine(List<DayLineBean> dayLine) {
            this.dayLine = dayLine;
        }

        public static class DayLineBean {
            /**
             * time : 11:11
             * volume : 286.97
             * price : 2.03091687
             */

            private String time;
            private String volume;
            private String price;

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getVolume() {
                return volume;
            }

            public void setVolume(String volume) {
                this.volume = volume;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }
        }
    }
}
