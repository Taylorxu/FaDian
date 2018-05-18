package com.powerge.wise.powerge.bean;


import org.simpleframework.xml.Element;


public class XuJianCheckBean extends RootBean {

    /**
     * checkResult : 0
     * checkItem : &#x5173;&#x8054;&#x68c0;&#x67e5;&#x9879;222
     */
    @Element(name = "arg1")
    public String arg1;

    private String checkResult;
    private String checkItem;

    public XuJianCheckBean() {
    }


    public String getArg1() {
        return arg1;
    }

    public void setArg1(String arg1) {
        this.arg1 = arg1;
    }

    public String getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
    }

    public String getCheckItem() {
        return checkItem;
    }

    public void setCheckItem(String checkItem) {
        this.checkItem = checkItem;
    }
}
