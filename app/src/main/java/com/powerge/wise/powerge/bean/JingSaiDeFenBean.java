package com.powerge.wise.powerge.bean;

import org.simpleframework.xml.Element;

import java.util.List;

/**
 * Created by Administrator on 2018/3/28.
 * String GROUP_ID= "值班组ID";
 * String  GROUP_NAME= "值班组 组名";
 * String  UNIT_ID= "机组ID";
 * String   UNIT_NAME= "机组名";
 * String   INDICATOR_ID= "指标ID";
 * String  INDICATOR_NAME= "指标名";
 * String  INDICATOR_VALUE= "指标值";
 * String   REAL_SCORE= "实际得分";
 * String   SHOULD_SCORE= "应得分";
 * String   RULE_OPTIMAL= "最优区间";
 * String   avgRealScore= "当值均分";
 */

public class JingSaiDeFenBean extends RootBean {
    @Element(name = "arg1")
    String arg1;
    @Element(name = "arg2")
    String arg2;

    public String getArg1() {
        return arg1;
    }

    public void setArg1(String arg1) {
        this.arg1 = arg1;
    }

    public String getArg2() {
        return arg2;
    }

    public void setArg2(String arg2) {
        this.arg2 = arg2;
    }

    String dayAVGScore;
    String monthAVGScore;
    List<ResultListBean> resultList;

    public class ResultListBean {
        String GROUP_ID = "值班组ID";
        String GROUP_NAME = "值班组 组名";
        String UNIT_ID = "机组ID";
        String UNIT_NAME = "机组名";
        String INDICATOR_ID = "指标ID";
        String INDICATOR_NAME = "指标名";
        String INDICATOR_VALUE = "指标值";
        String REAL_SCORE = "实际得分";
        String SHOULD_SCORE = "应得分";
        String RULE_OPTIMAL = "最优区间";
        String avgRealScore = "当值均分";

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

        public String getINDICATOR_VALUE() {
            return INDICATOR_VALUE;
        }

        public void setINDICATOR_VALUE(String INDICATOR_VALUE) {
            this.INDICATOR_VALUE = INDICATOR_VALUE;
        }

        public String getREAL_SCORE() {
            return REAL_SCORE;
        }

        public void setREAL_SCORE(String REAL_SCORE) {
            this.REAL_SCORE = REAL_SCORE;
        }

        public String getSHOULD_SCORE() {
            return SHOULD_SCORE;
        }

        public void setSHOULD_SCORE(String SHOULD_SCORE) {
            this.SHOULD_SCORE = SHOULD_SCORE;
        }

        public String getRULE_OPTIMAL() {
            return RULE_OPTIMAL;
        }

        public void setRULE_OPTIMAL(String RULE_OPTIMAL) {
            this.RULE_OPTIMAL = RULE_OPTIMAL;
        }

        public String getAvgRealScore() {
            return avgRealScore;
        }

        public void setAvgRealScore(String avgRealScore) {
            this.avgRealScore = avgRealScore;
        }
    }


    public String getDayAVGScore() {
        return dayAVGScore;
    }

    public void setDayAVGScore(String dayAVGScore) {
        this.dayAVGScore = dayAVGScore;
    }

    public String getMonthAVGScore() {
        return monthAVGScore;
    }

    public void setMonthAVGScore(String monthAVGScore) {
        this.monthAVGScore = monthAVGScore;
    }

    public List<ResultListBean> getResultList() {
        return resultList;
    }

    public void setResultList(List<ResultListBean> resultList) {
        this.resultList = resultList;
    }
}
