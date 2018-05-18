package com.powerge.wise.powerge.otherPages.xunJian;

import com.powerge.wise.powerge.bean.Detail;
import com.powerge.wise.powerge.bean.RootBean;

import org.simpleframework.xml.Element;

import java.util.List;

/**
 * Created by Administrator on 2018/3/27.
 */

public class SignSoapRequest extends RootBean {
    @Element(name = "arg1")
    String arg1;
    @Element(name = "arg2")
    String arg2;
    @Element(name = "arg3")
    String arg3;
    @Element(name = "arg4")
    String arg4;
    @Element(name = "arg5")
    String arg5;

    /**
     * shouldInspectNum : 40
     * inspectedDetails : [{"detail":"2018-03-27 08:02:49"},{"detail":"2018-03-27 06:40:20"},{"detail":"2018-03-27 06:38:03"}]
     * inspectedNum : 3
     */

    private String shouldInspectNum;
    private int inspectedNum;
    private List<Detail> inspectedDetails;


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

    public String getArg3() {
        return arg3;
    }

    public void setArg3(String arg3) {
        this.arg3 = arg3;
    }

    public String getArg4() {
        return arg4;
    }

    public void setArg4(String arg4) {
        this.arg4 = arg4;
    }

    public String getArg5() {
        return arg5;
    }

    public void setArg5(String arg5) {
        this.arg5 = arg5;
    }

    public String getShouldInspectNum() {
        return shouldInspectNum;
    }

    public void setShouldInspectNum(String shouldInspectNum) {
        this.shouldInspectNum = shouldInspectNum;
    }

    public int getInspectedNum() {
        return inspectedNum;
    }

    public void setInspectedNum(int inspectedNum) {
        this.inspectedNum = inspectedNum;
    }

    public List<Detail> getInspectedDetails() {
        return inspectedDetails;
    }

    public void setInspectedDetails(List<Detail> inspectedDetails) {
        this.inspectedDetails = inspectedDetails;
    }

}
