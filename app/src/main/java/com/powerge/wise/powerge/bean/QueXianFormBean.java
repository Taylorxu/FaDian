package com.powerge.wise.powerge.bean;

import org.simpleframework.xml.Element;

/**
 * Created by Administrator on 2018/3/26.
 */

public class QueXianFormBean extends RootBean {
    @Element(name = "arg1")
    String arg1;

    public int passingRate;
    public int normal;
    public int urgent;
    public int critical;

    public int getPassingRate() {
        return passingRate;
    }

    public void setPassingRate(int passingRate) {
        this.passingRate = passingRate;
    }

    public int getNormal() {
        return normal;
    }

    public void setNormal(int normal) {
        this.normal = normal;
    }

    public int getUrgent() {
        return urgent;
    }

    public void setUrgent(int urgent) {
        this.urgent = urgent;
    }

    public int getCritical() {
        return critical;
    }

    public void setCritical(int critical) {
        this.critical = critical;
    }

    public String getArg1() {
        return arg1;
    }

    public void setArg1(String arg1) {
        this.arg1 = arg1;
    }

    public  float getPieChartData(int index) {
        int [] array = new int[]{getPassingRate(), getCritical(), getUrgent(), getNormal()};
        return Float.parseFloat(String.valueOf(array[index]));
    }
}
