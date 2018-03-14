package com.powerge.wise.powerge.bean;

import android.os.Bundle;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Administrator on 2018/3/1.
 */
@Root
public class SheBeiRootBean {
    public static SheBeiRootBean newInstance() {
        SheBeiRootBean fragment = new SheBeiRootBean();
        return fragment;
    }


    @Attribute(name = "xmlns:n0")
    String nameSpace;

    @Element(name = "arg0")
    public String userName;
    @Element(name = "arg1")
    public String keyWord;
    @Element(name = "arg2")
    public String page;


    private String no;
    private String name;
    private String deviceDesc;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeviceDesc() {
        return deviceDesc;
    }

    public void setDeviceDesc(String deviceDesc) {
        this.deviceDesc = deviceDesc;
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

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
