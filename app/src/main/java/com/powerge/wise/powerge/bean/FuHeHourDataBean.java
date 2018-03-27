package com.powerge.wise.powerge.bean;

import org.simpleframework.xml.Element;

/**
 * Created by Administrator on 2018/3/27.
 */

public class FuHeHourDataBean extends RootBean {
    @Element(name = "arg1")
    String arg1;
    String time;
    String value;

    public String getArg1() {
        return arg1;
    }

    public void setArg1(String arg1) {
        this.arg1 = arg1;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
