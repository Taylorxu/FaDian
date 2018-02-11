package com.wisesignsoft.OperationManagement.net.response;

/**
 * Created by ycs on 2016/11/23.
 */

public class UpdateUserPos extends BaseResponse {

    /**
     * returnValue : 多个人员Id
     */

    private String returnValue;

    public String getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(String returnValue) {
        this.returnValue = returnValue;
    }
}
