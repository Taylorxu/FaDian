package com.powerge.wise.powerge.bean;

import android.os.Bundle;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

/**
 * Created by Administrator on 2018/3/15.
 */

public class TongJiForm {

    public static TongJiForm newInstance() {
        TongJiForm fragment = new TongJiForm();
        return fragment;
    }

    @Attribute(name = "xmlns:n0")
    String nameSpace;
    @Element(name = "arg0")
    public String userName;

    private String name;
    private String value;
    private String unit;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
