package com.powerge.wise.powerge.bean;

import org.simpleframework.xml.Element;

import java.util.List;

/**
 * Created by Administrator on 2018/3/5.
 */

public class KaoHeChildItemBean extends RootBean {

    /**
     * details : [{"time":"2018-03-22 11:24:07","indicatorName":"sulfide","indicatorValue":"1200.0","maxValue":"1000.0"}]
     * count : 0
     * month : 2
     */
    @Element(name = "arg1")
    String arg1;

    private String count;
    private String month;
    private List<DetailsBean> details;

    public String getArg1() {
        return arg1;
    }

    public void setArg1(String arg1) {
        this.arg1 = arg1;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public List<DetailsBean> getDetails() {
        return details;
    }

    public void setDetails(List<DetailsBean> details) {
        this.details = details;
    }

    public static class DetailsBean {
        /**
         * time : 2018-03-22 11:24:07
         * indicatorName : sulfide
         * indicatorValue : 1200.0
         * maxValue : 1000.0
         */

        private String time;
        private String indicatorName;
        private String indicatorValue;
        private String maxValue;

        public String getTime() {
            return time.substring(time.indexOf("-") + 1, time.length());
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getIndicatorName() {
            return indicatorName;
        }

        public void setIndicatorName(String indicatorName) {
            this.indicatorName = indicatorName;
        }

        public String getIndicatorValue() {
            return indicatorValue;
        }

        public void setIndicatorValue(String indicatorValue) {
            this.indicatorValue = indicatorValue;
        }

        public String getMaxValue() {
            return maxValue;
        }

        public void setMaxValue(String maxValue) {
            this.maxValue = maxValue;
        }
    }
}
