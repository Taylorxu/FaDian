package com.powerge.wise.basestone.heart.network;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.lang.*;

import static org.json.JSONObject.quote;

/**
 * Created by xu on 2018/2/5.
 */

public class ResultModel<Data> {
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


    public Data getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(Data returnValue) {
        this.returnValue = returnValue;
    }

    public Error getError() {
        return new Error(Integer.decode(getReturnState()), returnMsg);
    }


}
