package com.wisesignsoft.OperationManagement.net.response;

/**
 * Created by ycs on 2016/11/23.
 */

public class QueryUnhandleProcessCountResponse extends BaseResponse {

    /**
     * returnValue : 待办工单数
     */

    private String returnValue;

    public String getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(String returnValue) {
        this.returnValue = returnValue;
    }
}
