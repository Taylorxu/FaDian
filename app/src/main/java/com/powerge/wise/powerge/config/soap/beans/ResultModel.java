package com.powerge.wise.powerge.config.soap.beans;

/**
 * Created by xu on 2018/2/5.
 */

public class ResultModel<Data>   {
    private String returnMsg;
    private Data returnValue;
    private String returnState;

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }


    public String getReturnState() {
        return returnState;
    }

    public void setReturnState(String returnState) {
        this.returnState = returnState;
    }
/*
    public String getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(String returnValue) {
        this.returnValue = returnValue;
    }*/

    public Data getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(Data returnValue) {
        this.returnValue = returnValue;
    }

    public Error getError() {
        return new Error(returnMsg);
    }

}
