package com.powerge.wise.powerge.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/26.
 */

public class ZhiBIaoValueBean extends RootBean {

    /**
     * details : [{"name":"R","value":"1130.00"}]
     * unitName : 机组2
     */

    private String unitName;
    private List<DetailsBean> details;

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public List<DetailsBean> getDetails() {
        return details;
    }

    public void setDetails(List<DetailsBean> details) {
        this.details = details;
    }

    public static class DetailsBean {
        /**
         * name : R
         * value : 1130.00
         */

        private String name;
        private String value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
