package com.example.admin.basic.model.home;

import java.util.List;

/**
 * @author Xinxin Shi
 */

public class HomeTabModel {

    /**
     * code : 200
     * msg :
     * data : {"tabs":[]}
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
        private List<?> tabs;

        public List<?> getTabs() {
            return tabs;
        }

        public void setTabs(List<?> tabs) {
            this.tabs = tabs;
        }
    }
}
