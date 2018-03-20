package com.powerge.wise.powerge.bean;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

/**
 * Created by Administrator on 2018/3/14.
 */

public class MorningMeetingBean extends RootBean {


    @Element(name = "arg1")
    public String arg1;

    private String host;
    private String title;
    private String date;
    private String detail;

    public static MorningMeetingBean newInstance() {
        MorningMeetingBean fragment = new MorningMeetingBean();
        return fragment;
    }


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getArg1() {
        return arg1;
    }

    public void setArg1(String arg1) {
        this.arg1 = arg1;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
