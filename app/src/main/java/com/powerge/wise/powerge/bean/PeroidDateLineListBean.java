package com.powerge.wise.powerge.bean;

import android.os.Bundle;

import org.simpleframework.xml.Element;

/**
 * Created by Administrator on 2018/3/19.
 */

public class PeroidDateLineListBean extends RootBean {
    public static PeroidDateLineListBean newInstance() {
        PeroidDateLineListBean fragment = new PeroidDateLineListBean();
        return fragment;
    }

    @Element
    public String arg1;
    @Element
    public String arg2;
    @Element
    public String arg3;

    String loadRatio;
    String date;

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

    public String getArg3() {
        return arg3;
    }

    public void setArg3(String arg3) {
        this.arg3 = arg3;
    }

    public String getLoadRatio() {
        return loadRatio;
    }

    public void setLoadRatio(String loadRatio) {
        this.loadRatio = loadRatio;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
