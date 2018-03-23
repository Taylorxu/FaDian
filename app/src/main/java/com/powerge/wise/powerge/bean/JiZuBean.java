package com.powerge.wise.powerge.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.Element;

/**
 * Created by Administrator on 2018/3/16.
 */

public class JiZuBean extends RootBean implements Parcelable {
    public static String INTENTKEY = "JIZUARRAY";
    private String name;
    private String id;
    static Parcel parcel;

    public JiZuBean() {

    }

    protected JiZuBean(Parcel in) {
        name = in.readString();
        id = in.readString();
    }

    public static final Creator<JiZuBean> CREATOR = new Creator<JiZuBean>() {
        @Override
        public JiZuBean createFromParcel(Parcel in) {
            parcel = in;
            return new JiZuBean(in);
        }

        @Override
        public JiZuBean[] newArray(int size) {
            return new JiZuBean[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(id);
    }
}
