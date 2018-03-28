package com.powerge.wise.powerge.bean;

import org.simpleframework.xml.Element;

/**
 * Created by Administrator on 2018/3/27.
 */

public class GonGaoBean extends RootBean {


    @Element(name = "arg1")
    String arg1;

    public String getArg1() {
        return arg1;
    }

    public void setArg1(String arg1) {
        this.arg1 = arg1;
    }

    /**
     * title : 公告1
     * content : 公告1
     * creator : 2967e352-8749-4c3a-b377-008ae1f0395d
     * createTime : 2018-03-26 06:53:42
     * beginTime : 2018-03-05 12:00:01
     * endTime : 2018-03-31 11:59:59
     */

    private String title;
    private String content;
    private String creator;
    private String createTime;
    private String beginTime;
    private String endTime;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
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
}
