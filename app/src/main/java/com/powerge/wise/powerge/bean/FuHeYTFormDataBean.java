package com.powerge.wise.powerge.bean;

import android.os.Bundle;

import org.simpleframework.xml.Element;

/**
 * Created by Administrator on 2018/3/19.
 */

public class FuHeYTFormDataBean {
    public static FuHeYTFormDataBean newInstance() {
        FuHeYTFormDataBean fragment = new FuHeYTFormDataBean();
        return fragment;
    }

    @Element
    public String nameSpace;
    @Element
    public String arg0;
    @Element
    public String arg1;
    public String name;
    public String loadAvg;
    public String annRatio;
    public String agc;
    public String realValue;

    public String getArg0() {
        return arg0;
    }

    public void setArg0(String arg0) {
        this.arg0 = arg0;
    }

    public String getArg1() {
        return arg1;
    }

    public void setArg1(String arg1) {
        this.arg1 = arg1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoadAvg() {
        return loadAvg;
    }

    public void setLoadAvg(String loadAvg) {
        this.loadAvg = loadAvg;
    }

    public String getAnnRatio() {
        return annRatio;
    }

    public void setAnnRatio(String annRatio) {
        this.annRatio = annRatio;
    }

    public String getAgc() {
        return agc;
    }

    public void setAgc(String agc) {
        this.agc = agc;
    }

    public String getRealValue() {
        return realValue;
    }

    public void setRealValue(String realValue) {
        this.realValue = realValue;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }
}
