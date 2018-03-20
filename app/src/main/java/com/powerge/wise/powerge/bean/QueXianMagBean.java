package com.powerge.wise.powerge.bean;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import org.simpleframework.xml.Element;

/**
 * Created by Administrator on 2018/3/15.
 */

public class QueXianMagBean extends RootBean {


    private String name;
    private String processStatus;
    private String content;
    private String uploader;
    private String handler;
    private String date;
    private String emergencyStatus;

    public static QueXianMagBean newInstance() {
        QueXianMagBean fragment = new QueXianMagBean();
        return fragment;
    }

    @Element(name = "arg1")
    public String arg1;
    @Element(name = "arg2")
    public String arg2;


    String stateArry[] = new String[]{"非常紧急", "紧急", "一般"};
    String stateArry2[] = new String[]{"已完成", "处理中", "待处理"};

    public SpannableString getForMemergencyStatus() {
        int state = Integer.parseInt(emergencyStatus);
        SpannableString s = new SpannableString(stateArry[state - 1]);
        if (state == 1) {
            s.setSpan(new ForegroundColorSpan(Color.rgb(227, 30, 77)), 0, s.length(), 0);
        } else if (state == 2) {
            s.setSpan(new ForegroundColorSpan(Color.rgb(255, 138, 0)), 0, s.length(), 0);
        } else if (state == 3) {
            s.setSpan(new ForegroundColorSpan(Color.rgb(17, 199, 116)), 0, s.length(), 0);

        }
        return s;
    }

    public String getForProcessStatusStatus() {
        int state = Integer.parseInt(processStatus);
        return stateArry2[state - 1];
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }


    public String getEmergencyStatus() {
        return emergencyStatus;
    }

    public void setEmergencyStatus(String emergencyStatus) {
        this.emergencyStatus = emergencyStatus;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
