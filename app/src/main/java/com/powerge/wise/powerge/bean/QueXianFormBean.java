package com.powerge.wise.powerge.bean;

import org.simpleframework.xml.Element;

/**
 * Created by Administrator on 2018/3/26.
 */

public class QueXianFormBean extends RootBean {
    @Element(name = "arg1")
    String arg1;

    public String passingRate;
    public String normal;
    public String urgent;
    public String critical;

    public String getArg1() {
        return arg1;
    }

    public void setArg1(String arg1) {
        this.arg1 = arg1;
    }

    public String getPassingRate() {
        return passingRate;
    }

    public void setPassingRate(String passingRate) {
        this.passingRate = passingRate;
    }

    public String getNormal() {
        return normal;
    }

    public void setNormal(String normal) {
        this.normal = normal;
    }

    public String getUrgent() {
        return urgent;
    }

    public void setUrgent(String urgent) {
        this.urgent = urgent;
    }

    public String getCritical() {
        return critical;
    }

    public void setCritical(String critical) {
        this.critical = critical;
    }

    public  float getPieChartData(int index) {
        String[] array = new String[]{getPassingRate(), getCritical(), getUrgent(), getNormal()};
        return Float.parseFloat(array[index]);
    }
}
