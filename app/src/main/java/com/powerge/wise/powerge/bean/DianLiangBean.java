package com.powerge.wise.powerge.bean;

import android.os.Bundle;

import org.simpleframework.xml.Element;

/**
 * Created by Administrator on 2018/3/16.
 */

public class DianLiangBean extends RootBean {
    public static DianLiangBean newInstance() {
        DianLiangBean fragment = new DianLiangBean();
        return fragment;
    }

    @Element
    String arg1;

    public String getArg1() {
        return arg1;
    }

    public void setArg1(String arg1) {
        this.arg1 = arg1;
    }

    private MonthBean month;
    private YearBean year;
    private DayBean day;

    public MonthBean getMonth() {
        return month;
    }

    public void setMonth(MonthBean month) {
        this.month = month;
    }

    public YearBean getYear() {
        return year;
    }

    public void setYear(YearBean year) {
        this.year = year;
    }

    public DayBean getDay() {
        return day;
    }

    public void setDay(DayBean day) {
        this.day = day;
    }

    public static class MonthBean {
        /**
         * planedAmount : 0.0
         * monthlyHours : 0
         * finishRate : 0.0
         * name :
         * realAmount : 0.0
         * annRatio : 0.0
         */

        private String planedAmount;
        private String monthlyHours;
        private String finishRate;
        private String name;
        private String realAmount;
        private String annRatio;
        private String surplusAvg;

        public String getSurplusAvg() {
            return surplusAvg;
        }

        public void setSurplusAvg(String surplusAvg) {
            this.surplusAvg = surplusAvg;
        }

        public String getPlanedAmount() {
            return planedAmount;
        }

        public void setPlanedAmount(String planedAmount) {
            this.planedAmount = planedAmount;
        }

        public String getMonthlyHours() {
            return monthlyHours;
        }

        public void setMonthlyHours(String monthlyHours) {
            this.monthlyHours = monthlyHours;
        }

        public String getFinishRate() {
            return finishRate;
        }

        public void setFinishRate(String finishRate) {
            this.finishRate = finishRate;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRealAmount() {
            return realAmount;
        }

        public void setRealAmount(String realAmount) {
            this.realAmount = realAmount;
        }

        public String getAnnRatio() {
            return annRatio;
        }

        public void setAnnRatio(String annRatio) {
            this.annRatio = annRatio;
        }
    }

    public static class YearBean {
        /**
         * planedAmount : 0.0
         * name :
         * realAmount : 0.0
         * growth : 0.0
         */

        private String planedAmount;
        private String name;
        private String realAmount;
        private String growth;

        public String getPlanedAmount() {
            return planedAmount;
        }

        public void setPlanedAmount(String planedAmount) {
            this.planedAmount = planedAmount;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRealAmount() {
            return realAmount;
        }

        public void setRealAmount(String realAmount) {
            this.realAmount = realAmount;
        }

        public String getGrowth() {
            return growth;
        }

        public void setGrowth(String growth) {
            this.growth = growth;
        }
    }

    public static class DayBean {
        /**
         * planedAmount : 0.0
         * avgLoad : 0.0
         * name :
         * realAmount : 0.0
         * annRatio : 0.0
         * dailyHours : 0
         */

        private String planedAmount;
        private String avgLoad;
        private String name;
        private String realAmount;
        private String annRatio;
        private String dailyHours;
        private String yesterdayAmount;

        public String getYesterdayAmount() {
            return yesterdayAmount;
        }

        public void setYesterdayAmount(String yesterdayAmount) {
            this.yesterdayAmount = yesterdayAmount;
        }

        public String getPlanedAmount() {
            return planedAmount;
        }

        public void setPlanedAmount(String planedAmount) {
            this.planedAmount = planedAmount;
        }

        public String getAvgLoad() {
            return avgLoad;
        }

        public void setAvgLoad(String avgLoad) {
            this.avgLoad = avgLoad;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRealAmount() {
            return realAmount;
        }

        public void setRealAmount(String realAmount) {
            this.realAmount = realAmount;
        }

        public String getAnnRatio() {
            return annRatio;
        }

        public void setAnnRatio(String annRatio) {
            this.annRatio = annRatio;
        }

        public String getDailyHours() {
            return dailyHours;
        }

        public void setDailyHours(String dailyHours) {
            this.dailyHours = dailyHours;
        }
    }
}
