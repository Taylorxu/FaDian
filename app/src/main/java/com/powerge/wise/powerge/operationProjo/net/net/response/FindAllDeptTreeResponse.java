package com.powerge.wise.powerge.operationProjo.net.net.response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ycs on 2016/12/15.
 */

public class FindAllDeptTreeResponse extends  BaseResponse {
    private List<Children> result;
    public List<Children> getResult() {
        return result;
    }

    public void setResult(List<Children> result) {
        this.result = result;
    }

    public class Children implements Serializable{
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
        private List<Children> children;
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

        public List<Children> getChildren() {
            return children;
        }

        public void setChildren(List<Children> children) {
            this.children = children;
        }
    }
}
