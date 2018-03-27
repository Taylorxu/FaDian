package com.powerge.wise.powerge.bean;

import android.databinding.Bindable;
import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import com.powerge.wise.powerge.BR;

import org.simpleframework.xml.Element;

import java.util.List;

/**
 * Created by Administrator on 2018/3/6.
 */

public class XunJianSignBean extends RootBean implements Parcelable{
    @Element(name = "arg1")
    String arg1;
    @Element(name = "arg2")
    String arg2;
    /**
     * pointNo : 1001
     * name : 锅炉
     * num : 3
     * blueToothNo : fda50693-a4e2-4fb1-afcf-c6eb07647825
     * inspectedNum : 0
     * inspectedDetails : []
     */

    private String pointNo;
    private String name;
    private int num;
    private String blueToothNo;
    private int inspectedNum;//已经签过次数
    private List<Detail> inspectedDetails;

    public XunJianSignBean() {
    }

    public XunJianSignBean(Parcel in) {
        arg1 = in.readString();
        arg2 = in.readString();
        pointNo = in.readString();
        name = in.readString();
        num = in.readInt();
        blueToothNo = in.readString();
        inspectedNum = in.readInt();
    }

    public static final Creator<XunJianSignBean> CREATOR = new Creator<XunJianSignBean>() {
        @Override
        public XunJianSignBean createFromParcel(Parcel in) {
            return new XunJianSignBean(in);
        }

        @Override
        public XunJianSignBean[] newArray(int size) {
            return new XunJianSignBean[size];
        }
    };

    /**
     * 为巡检次数设置颜色
     *
     * @return
     */
    public SpannableString formShouldSign() {
        String signCount = getInspectedNum() + "/" + num;
        SpannableString s = new SpannableString(signCount);
        int index = signCount.indexOf("/");
        s.setSpan(new ForegroundColorSpan(Color.rgb(66, 177, 255)), 0, index, 0);
        s.setSpan(new ForegroundColorSpan(Color.rgb(153, 153, 153)), index, signCount.length(), 0);
        return s;
    }

    public void signAction() {

    }

    public String getPointNo() {
        return pointNo;
    }

    public void setPointNo(String pointNo) {
        this.pointNo = pointNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getBlueToothNo() {
        return blueToothNo;
    }

    public void setBlueToothNo(String blueToothNo) {
        this.blueToothNo = blueToothNo;
    }

    @Bindable
    public int getInspectedNum() {
        return inspectedNum;
    }

    public void setInspectedNum(int inspectedNum) {
        this.inspectedNum = inspectedNum;
        notifyPropertyChanged(BR.inspectedNum);
    }

    public List<?> getInspectedDetails() {
        return inspectedDetails;
    }

    public String getArg1() {
        return arg1;
    }

    public void setArg1(String arg1) {
        this.arg1 = arg1;
    }

    public String getArg2() {
        return arg2;
    }

    public void setArg2(String arg2) {
        this.arg2 = arg2;
    }

    public void setInspectedDetails(List<Detail> inspectedDetails) {
        this.inspectedDetails = inspectedDetails;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(arg1);
        dest.writeString(arg2);
        dest.writeString(pointNo);
        dest.writeString(name);
        dest.writeInt(num);
        dest.writeString(blueToothNo);
        dest.writeInt(inspectedNum);
    }
}
