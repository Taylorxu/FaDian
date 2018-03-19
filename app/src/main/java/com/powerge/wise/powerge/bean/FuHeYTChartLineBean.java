package com.powerge.wise.powerge.bean;

import android.os.Bundle;

import org.simpleframework.xml.Element;

import java.util.List;

/**
 * Created by Administrator on 2018/3/19.
 */

public class FuHeYTChartLineBean extends RootBean {
    public static FuHeYTChartLineBean newInstance() {
        FuHeYTChartLineBean fragment = new FuHeYTChartLineBean();
        return fragment;
    }
    @Element
    public String arg1;
    private List<YesterdayBean> yesterday;
    private List<TodayBean> today;

    public String getArg1() {
        return arg1;
    }

    public void setArg1(String arg1) {
        this.arg1 = arg1;
    }

    public List<YesterdayBean> getYesterday() {
        return yesterday;
    }

    public void setYesterday(List<YesterdayBean> yesterday) {
        this.yesterday = yesterday;
    }

    public List<TodayBean> getToday() {
        return today;
    }

    public void setToday(List<TodayBean> today) {
        this.today = today;
    }


    public static class YesterdayBean {

        private String Y;
        private String X;

        public String getY() {
            return Y;
        }

        public void setY(String Y) {
            this.Y = Y;
        }

        public String getX() {
            return X;
        }

        public void setX(String X) {
            this.X = X;
        }
    }

    public static class TodayBean {


        private String Y;
        private String X;

        public String getY() {
            return Y;
        }

        public void setY(String Y) {
            this.Y = Y;
        }

        public String getX() {
            return X;
        }

        public void setX(String X) {
            this.X = X;
        }
    }
}
