package com.powerge.wise.powerge.bean;

import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * 值长日志 bean
 * Created by Administrator on 2018/3/15.
 */

public class ZhiZhangLogBean extends RootBean {

    public static ZhiZhangLogBean newInstance() {
        ZhiZhangLogBean fragment = new ZhiZhangLogBean();
        return fragment;
    }

    @Element(name = "arg1")
    public String arg1;

    private String monitor;
    private String title;
    private String detail;
    private String time;

    public String getMonitor() {
        return monitor;
    }

    public void setMonitor(String monitor) {
        this.monitor = monitor;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<ZhiZhangLogBean> getDeadData() {
        String result = "[{\"no\":\"01\",\"name\":\"汽机\",\"deviceDesc\":\"汽机是蒸汽机和汽轮机的简称。19世纪末，瑞典拉瓦尔和英国帕森斯分别创制了实用的汽机。拉瓦尔于1882年制成了第一台5马力(3.67千瓦)的单级冲动式汽机，并解决了有关的喷嘴设计和强度设计问题。单级冲动式汽机功率很小，现在已很少采用。现在汽机通常指电厂，火车上的汽轮机\"},{\"no\":\"02\",\"name\":\"锅炉\",\"deviceDesc\":\"锅炉是一种能量转换设备，向锅炉输入的能量有燃料中的化学能、电能，锅炉输出具有一定热能的蒸汽、高温水或有机热载体。\\r\\n锅的原义指在火上加热的盛水容器，炉指燃烧燃料的场所，锅炉包括锅和炉两大部分。锅炉中产生的热水或蒸汽可直接为工业生产和人民生活提供所需热能，也可通过蒸汽动力装置转换为机械能，或再通过发电机将机械能转换为电能。提供热水的锅炉称为热水锅炉，主要用于生活，工业生产中也有少量应用。产生蒸汽的锅炉称为蒸汽锅炉，常简称为锅炉，多用于火电站、船舶、机车和工矿企业\"},{\"no\":\"03\",\"name\":\"过滤器\",\"deviceDesc\":\"过过滤器（filter）是输送介质管道上不可缺少的一种装置，通常安装在减压阀、泄压阀、定水位阀 ,方工过滤器其它设备的进口端设备。过滤器由筒体、不锈钢滤网、排污部分、传动装置及电气控制部分组成。待处理的水经过过滤器滤网的滤筒后，其杂质被阻挡，当需要清洗时，只要将可拆卸的滤筒取出，处理后重新装入即可，因此，使用维护极为方便\"},{\"no\":\"04\",\"name\":\"汽轮机\",\"deviceDesc\":\"汽轮机是一种将蒸汽的热势能转换成机械能的旋转原动机。分冲动式和反动式汽轮机。\"},{\"no\":\"05\",\"name\":\"给水泵\",\"deviceDesc\":\"将除氧水箱的凝结水通过给水泵提高压力，经过高压加热器加热后，输送到锅炉省煤器入口，作为锅炉主给水。\"},{\"no\":\"06\",\"name\":\"除氧器\",\"deviceDesc\":\"除去锅炉给水中的各种气体，主要是水中的游离氧。\"},{\"no\":\"07\",\"name\":\"燃烧器\",\"deviceDesc\":\"将携带煤粉的一次风和助燃的二次风送入炉膛，并组织一定的气流结构，使煤粉能迅速稳定的着火，同时使煤粉和空气合理混合，达到煤粉在炉内迅速完全燃烧。煤粉燃烧器可分为直流燃烧器和旋流燃烧器两大类\"},{\"no\":\"08\",\"name\":\"磨煤机\",\"deviceDesc\":\"将原煤磨成需要细度的煤粉，完成粗细粉分离及干燥。\"},{\"no\":\"09\",\"name\":\"空预器\",\"deviceDesc\":\"空气预热器是利用锅炉尾部烟气热量来加热燃烧所需空气的一种热交换装置。\"},{\"no\":\"10\",\"name\":\"引风机\",\"deviceDesc\":\"将烟气排除，维持炉膛压力，形成流动烟气，完成烟气及空气的热交换。\"}]";
        List<ZhiZhangLogBean> sheBeiRootBeans = new ArrayList<>();
        Gson g = new Gson();
        sheBeiRootBeans = g.fromJson(result, new TypeToken<List<ZhiZhangLogBean>>() {
        }.getType());
        return sheBeiRootBeans;

    }

}
