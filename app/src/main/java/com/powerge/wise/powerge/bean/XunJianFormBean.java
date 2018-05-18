package com.powerge.wise.powerge.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;

import org.simpleframework.xml.Element;

/**
 * Created by Administrator on 2018/5/8.
 */

public class XunJianFormBean extends RootBean implements Parcelable {

    /**
     * checkItemId : CHECK-20180514-012
     * checkNeeded : dict1id
     * checkItem : &#x68c0;&#x67e5;&#x9879;2
     * checkType : dict2id
     * checkNeeded：（是否必填）：dict1id-非必输，dict2id-必输
     * checkType：检查项类型(dict1id  选择输入   dict2id  文本框输入 )、checkItem：检查项名称
     */
    @Element(name = "arg1")
    public String arg1;

    private String checkItemId;
    private String checkNeeded;
    private String checkItem;
    private String checkType;
    private String checkResult = "";  //查看时 的数据值
    private int radioBtResult = 9;  // 单选时，返回的结果

    public XunJianFormBean() {
    }

    protected XunJianFormBean(Parcel in) {
        checkItemId = in.readString();
        checkNeeded = in.readString();
        checkItem = in.readString();
        checkType = in.readString();
        checkResult = in.readString();
        radioBtResult = in.readInt();
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

    public String getArg1() {
        return arg1;
    }

    public void setArg1(String arg1) {
        this.arg1 = arg1;
    }


    public String getCheckItemId() {
        return checkItemId;
    }

    public void setCheckItemId(String checkItemId) {
        this.checkItemId = checkItemId;
    }

    public String getCheckNeeded() {
        return checkNeeded;
    }

    public void setCheckNeeded(String checkNeeded) {
        this.checkNeeded = checkNeeded;
    }

    public String getCheckItem() {
        return checkItem;
    }

    public void setCheckItem(String checkItem) {
        this.checkItem = checkItem;
    }

    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    String options[] = new String[]{"是", "否"};

    public String getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
    }

    public String getItemValue() {
        if (TextUtils.isEmpty(checkResult)) return "";
        String value;
        if (!checkType.equals("dict1id")) {
            value = checkResult;
        } else {
            value = options[Integer.parseInt(checkResult)];
        }
        return value;
    }

    public int getRadioBtResult() {
        return radioBtResult;
    }

    public void setRadioBtResult(int radioBtResult) {
        this.radioBtResult = radioBtResult;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(checkItemId);
        dest.writeString(checkNeeded);
        dest.writeString(checkItem);
        dest.writeString(checkType);
        dest.writeString(checkResult);
        dest.writeInt(radioBtResult);
    }

    public int getImgvisibility() {
        if (checkType.equals("dict1id")) {// 单选
            return View.VISIBLE;
        } else if (checkType.equals("dict2id")) {//文本框
            return View.INVISIBLE;
        }
        return View.INVISIBLE;
    }

    public boolean checkEmpty() {
        if (checkNeeded.equals("dict2id") &&TextUtils.isEmpty(checkResult)) {//必填
            return true;
        }
        return false;
    }
}
