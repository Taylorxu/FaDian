package com.powerge.wise.powerge.bean;

import org.simpleframework.xml.Element;

import java.util.List;

/**
 * Created by Administrator on 2018/3/5.
 */

/**
 * rank= "排名1";
 * groupName= "班组名1";
 * details=[{
 * GROUP_ID= "值班组ID";
 * GROUP_NAME= "值班组 组名";
 * INDICATOR_ID= "指标ID";
 * INDICATOR_NAME= "指标名";
 * UNIT_ID= "机组ID";
 * UNIT_NAME= "机组名";
 * MONTH_ALL_SCORE= "月总分";
 * MONTH_ALL_MINUTES= "月总分钟数";
 * AVG_SCORE= "月均分";
 * },
 */
public class PaiMingChildItemBean  extends RootBean{
    @Element(name = "arg1")
    public String arg1;

    private String rank;
    private String groupName;
    private List<PmChildBean> details;

    public String getArg1() {
        return arg1;
    }

    public void setArg1(String arg1) {
        this.arg1 = arg1;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<PmChildBean> getDetails() {
        return details;
    }

    public void setDetails(List<PmChildBean> details) {
        this.details = details;
    }

    public static class PmChildBean {
        private String AVG_SCORE;
        private String MONTH_ALL_SCORE;
        private String GROUP_ID;
        private String GROUP_NAME;
        private String INDICATOR_ID;
        private String INDICATOR_NAME;
        private String UNIT_ID;
        private String UNIT_NAME;

        public String getAVG_SCORE() {
            return AVG_SCORE;
        }

        public void setAVG_SCORE(String AVG_SCORE) {
            this.AVG_SCORE = AVG_SCORE;
        }

        public String getMONTH_ALL_SCORE() {
            return MONTH_ALL_SCORE;
        }

        public void setMONTH_ALL_SCORE(String MONTH_ALL_SCORE) {
            this.MONTH_ALL_SCORE = MONTH_ALL_SCORE;
        }

        public String getGROUP_ID() {
            return GROUP_ID;
        }

        public void setGROUP_ID(String GROUP_ID) {
            this.GROUP_ID = GROUP_ID;
        }

        public String getGROUP_NAME() {
            return GROUP_NAME;
        }

        public void setGROUP_NAME(String GROUP_NAME) {
            this.GROUP_NAME = GROUP_NAME;
        }

        public String getINDICATOR_ID() {
            return INDICATOR_ID;
        }

        public void setINDICATOR_ID(String INDICATOR_ID) {
            this.INDICATOR_ID = INDICATOR_ID;
        }

        public String getINDICATOR_NAME() {
            return INDICATOR_NAME;
        }

        public void setINDICATOR_NAME(String INDICATOR_NAME) {
            this.INDICATOR_NAME = INDICATOR_NAME;
        }

        public String getUNIT_ID() {
            return UNIT_ID;
        }

        public void setUNIT_ID(String UNIT_ID) {
            this.UNIT_ID = UNIT_ID;
        }

        public String getUNIT_NAME() {
            return UNIT_NAME;
        }

        public void setUNIT_NAME(String UNIT_NAME) {
            this.UNIT_NAME = UNIT_NAME;
        }
    }
}
