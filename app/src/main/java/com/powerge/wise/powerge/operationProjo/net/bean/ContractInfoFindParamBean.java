package com.powerge.wise.powerge.operationProjo.net.bean;

/**
 * Created by ycs on 2016/12/7.
 */

public class ContractInfoFindParamBean {

    /**
     * connector : like
     * dmAttrName : CUST_NAME
     * value :
     */

    private String connector;
    private String dmAttrName;
    private String value;

    public String getConnector() {
        return connector;
    }

    public void setConnector(String connector) {
        this.connector = connector;
    }

    public String getDmAttrName() {
        return dmAttrName;
    }

    public void setDmAttrName(String dmAttrName) {
        this.dmAttrName = dmAttrName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

