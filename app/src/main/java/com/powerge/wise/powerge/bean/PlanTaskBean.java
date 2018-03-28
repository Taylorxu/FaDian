package com.powerge.wise.powerge.bean;

import org.simpleframework.xml.Element;

/**
 * Created by Administrator on 2018/3/28.
 */

public class PlanTaskBean extends RootBean {


    /**
     * objID : WORK_PLAN:0c1383ea-a06c-4fdf-a793-5199fe3b4d70
     * name : 电厂计划
     * planDesc : 电厂计划电厂计划电厂计划电厂计划电厂计划电厂计划电厂计划电厂计划电厂计划电厂计划电厂计划电厂计划电厂计划电厂计划电厂计划电厂计划电厂计划电厂计划
     * createTime : 2018-03-27 03:06:08
     * beginTime : 2018-03-27 12:00:00
     * endTime : 2018-03-31 12:00:00
     */
    @Element(name = "arg1")

    String arg1;
    private String objID;
    private String name;
    private String planDesc;
    private String createTime;
    private String beginTime;
    private String endTime;

    public String getObjID() {
        return objID;
    }

    public void setObjID(String objID) {
        this.objID = objID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlanDesc() {
        return planDesc;
    }

    public void setPlanDesc(String planDesc) {
        this.planDesc = planDesc;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getArg1() {
        return arg1;
    }

    public void setArg1(String arg1) {
        this.arg1 = arg1;
    }
}
