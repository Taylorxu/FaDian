package com.wisesignsoft.OperationManagement.bean;

import java.util.List;

/**
 * Created by ycs on 2017/1/4.
 */

public class DeptBean {
    /**
     * children : []
     * deptCode : 5
     * deptFullName : 运行维护部
     * deptId : 1400656e-b363-46c5-81ec-b9316e0c38a5
     * deptNum : 4
     * deptOrder : 3
     * deptParentId : 5668ed2d-9374-4689-affd-6f0006f6ff80
     * deptRemark : 运行维护部
     * deptShortName : 运行维护部
     * fullPathName :
     * selected : false
     */

    private String deptCode;
    private String deptFullName;
    private String deptId;
    private String deptNum;
    private int deptOrder;
    private String deptParentId;
    private String deptRemark;
    private String deptShortName;
    private String fullPathName;
    private boolean selected;
    private List<DeptBean> children;

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDeptFullName() {
        return deptFullName;
    }

    public void setDeptFullName(String deptFullName) {
        this.deptFullName = deptFullName;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptNum() {
        return deptNum;
    }

    public void setDeptNum(String deptNum) {
        this.deptNum = deptNum;
    }

    public int getDeptOrder() {
        return deptOrder;
    }

    public void setDeptOrder(int deptOrder) {
        this.deptOrder = deptOrder;
    }

    public String getDeptParentId() {
        return deptParentId;
    }

    public void setDeptParentId(String deptParentId) {
        this.deptParentId = deptParentId;
    }

    public String getDeptRemark() {
        return deptRemark;
    }

    public void setDeptRemark(String deptRemark) {
        this.deptRemark = deptRemark;
    }

    public String getDeptShortName() {
        return deptShortName;
    }

    public void setDeptShortName(String deptShortName) {
        this.deptShortName = deptShortName;
    }

    public String getFullPathName() {
        return fullPathName;
    }

    public void setFullPathName(String fullPathName) {
        this.fullPathName = fullPathName;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public List<DeptBean> getChildren() {
        return children;
    }

    public void setChildren(List<DeptBean> children) {
        this.children = children;
    }
}
