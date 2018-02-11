package com.wisesignsoft.OperationManagement.bean;

import java.io.Serializable;

/**
 * Created by ycs on 2016/12/19.
 */

public class ContractBean implements Serializable {
    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
