package com.powerge.wise.powerge.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2018/3/27.
 */

public class Detail implements Parcelable {

    /**
     * detail : 2018-03-27 08:02:49
     */

    private String detail;

    protected Detail(Parcel in) {
        detail = in.readString();
    }

    public static final Creator<Detail> CREATOR = new Creator<Detail>() {
        @Override
        public Detail createFromParcel(Parcel in) {
            return new Detail(in);
        }

        @Override
        public Detail[] newArray(int size) {
            return new Detail[size];
        }
    };

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(detail);
    }

    public String getIndex() {
        int i = 0;
        return String.valueOf(i++);
    }
}
