package com.powerge.wise.powerge.bean;

import android.os.Bundle;
import android.text.format.DateFormat;

import org.simpleframework.xml.Element;

import java.util.Date;

/**
 * Created by Administrator on 2018/3/2.
 */

public class HuanBaoBean extends RootBean {
    public static HuanBaoBean newInstance() {
        HuanBaoBean fragment = new HuanBaoBean();
        return fragment;
    }

    /**
     * { value = "指标值1";
     * time = "采集时间1";}
     */
    public String value;
    public String time;
    @Element(name = "arg1")
    String arg1;//机组名
    @Element(name = "arg2")
    String arg2;//指标名称

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

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
}
