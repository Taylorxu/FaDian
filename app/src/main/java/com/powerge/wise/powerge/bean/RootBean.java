package com.powerge.wise.powerge.bean;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

/**
 * Created by Administrator on 2018/3/15.
 */

public abstract class RootBean {

    @Attribute(name = "xmlns:n0")
    String nameSpace;
    @Element(name = "arg0")
    public String userName;
    @Element(name = "arg1")
    public String page;

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

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
