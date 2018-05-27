package com.example.admin.basic.model.home;

import java.util.List;

/**
 * @author Xinxin Shi
 */

public class HomeTabModel {


    /**
     * code : 200
     * msg :
     * data : {"tabs":[{"id":1,"name":"自选","type":0},{"id":2,"name":"市值","type":1},{"id":3,
     * "name":"涨幅","type":2},{"id":4,"name":"BTC","type":3},{"id":5,"name":"ETH","type":3},
     * {"id":6,"name":"火币","type":4},{"id":7,"name":"币安","type":4}]}
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
        private List<TabsBean> tabs;

        public List<TabsBean> getTabs() {
            return tabs;
        }

        public void setTabs(List<TabsBean> tabs) {
            this.tabs = tabs;
        }

        public static class TabsBean {
            /**
             * id : 1
             * name : 自选
             * type : 0
             */

            private int id;
            private String name;
            private int type;

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

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }
    }
}
