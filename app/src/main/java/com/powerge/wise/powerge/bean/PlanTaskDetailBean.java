package com.powerge.wise.powerge.bean;

import org.simpleframework.xml.Element;

/**
 * Created by Administrator on 2018/3/28.
 */

public class PlanTaskDetailBean extends RootBean {
    @Element(name = "arg1")
    String arg1;
    @Element(name = "arg2")
    String arg2;



    private String taskName;
    private String taskContent;
    private String taskDesc;//不用
    private String createTime;
    private Object taskStatus;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskContent() {
        return taskContent;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Object getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Object taskStatus) {
        this.taskStatus = taskStatus;
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

    public void setArg2(String arg12) {
        this.arg2 = arg12;
    }
}
