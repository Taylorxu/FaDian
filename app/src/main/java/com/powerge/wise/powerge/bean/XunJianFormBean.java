package com.powerge.wise.powerge.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2018/5/8.
 */

public class XunJianFormBean extends RootBean implements Parcelable {
    private String itemName;
    private String itemValue;

    public XunJianFormBean() {

    }

    protected XunJianFormBean(Parcel in) {
        itemName = in.readString();
        itemValue = in.readString();
    }

    public static final Creator<XunJianFormBean> CREATOR = new Creator<XunJianFormBean>() {
        @Override
        public XunJianFormBean createFromParcel(Parcel in) {
            return new XunJianFormBean(in);
        }

        @Override
        public XunJianFormBean[] newArray(int size) {
            return new XunJianFormBean[size];
        }
    };

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }


    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(itemName);
        dest.writeString(itemValue);
    }
}
