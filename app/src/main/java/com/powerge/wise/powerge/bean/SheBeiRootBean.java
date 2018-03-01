package com.powerge.wise.powerge.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/1.
 */

public class SheBeiRootBean {
    /**
     * sheBeiName : 机组设备
     * sheBeiChild : [{"name":"Rufus"},{"name":"Marty"}]
     */

    private String sheBeiName;
    private List<SheBeiChildBean> sheBeiChild;

    public String getSheBeiName() {
        return sheBeiName;
    }

    public void setSheBeiName(String sheBeiName) {
        this.sheBeiName = sheBeiName;
    }

    public List<SheBeiChildBean> getSheBeiChild() {
        return sheBeiChild;
    }

    public void setSheBeiChild(List<SheBeiChildBean> sheBeiChild) {
        this.sheBeiChild = sheBeiChild;
    }

    public static class SheBeiChildBean {
        /**
         * name : Rufus
         */

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
