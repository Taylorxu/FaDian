package com.powerge.wise.powerge.operationProjo.net.bean;

import java.util.List;

/**
 * Created by ycs on 2016/11/29.
 */

public class Section {
    private String ID;
    private String label;
    private boolean isCurrent;
    private List<WorkOrder> section;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isCurrent() {
        return isCurrent;
    }

    public void setCurrent(boolean current) {
        isCurrent = current;
    }

    public List<WorkOrder> getSection() {
        return section;
    }

    public void setSection(List<WorkOrder> section) {
        this.section = section;
    }
}
