package com.powerge.wise.powerge.operationProjo.net.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ycs on 2016/11/30.
 */

public class EventClassificationModel implements Serializable {
    private String dictValue;
    private String dictParentValue;
    private String dictId;
    private String dictName;
    private List<EventClassificationModel> list;

    public String getDictValue() {
        return dictValue;
    }

    public void setDictValue(String dictValue) {
        this.dictValue = dictValue;
    }

    public String getDictParentValue() {
        return dictParentValue;
    }

    public void setDictParentValue(String dictParentValue) {
        this.dictParentValue = dictParentValue;
    }

    public String getDictId() {
        return dictId;
    }

    public void setDictId(String dictId) {
        this.dictId = dictId;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public List<EventClassificationModel> getList() {
        return list;
    }

    public void setList(List<EventClassificationModel> list) {
        this.list = list;
    }
}
