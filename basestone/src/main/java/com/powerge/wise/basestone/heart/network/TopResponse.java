package com.powerge.wise.basestone.heart.network;

import com.google.gson.annotations.SerializedName;


public class TopResponse<Data> {
    @SerializedName("code")
    private int code;
    @SerializedName("info")
    private String msg;
    @SerializedName("data")
    private Data data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getInfo() {
        return msg;
    }

    public void setInfo(String info) {
        this.msg = info;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Error getError() {
        return new Error(code, msg);
    }
}
