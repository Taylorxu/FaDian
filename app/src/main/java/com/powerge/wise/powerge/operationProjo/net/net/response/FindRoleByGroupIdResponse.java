package com.powerge.wise.powerge.operationProjo.net.net.response;

import java.util.List;

/**
 * Created by ycs on 2016/12/12.
 */

public class FindRoleByGroupIdResponse extends BaseResponse {

    /**
     * ownResourceList : []
     * ownUserList : []
     * roleCode : 8
     * roleGroupId : 863039ff-b94d-4737-8ea5-cc743aa45ca4
     * roleId : 0a894add-96ad-4032-bcb5-b9ae23b7aa90
     * roleName : 配置管理人
     * roleRemark :
     * roleType : 0
     * selected : false
     */

    private List<ReturnValueBean> returnValue;
    private List<ReturnValueBean> result;

    public List<ReturnValueBean> getResult() {
        return result;
    }

    public void setResult(List<ReturnValueBean> result) {
        this.result = result;
    }

    public List<ReturnValueBean> getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(List<ReturnValueBean> returnValue) {
        this.returnValue = returnValue;
    }

    public static class ReturnValueBean {
        private String roleCode;
        private String roleGroupId;
        private String roleId;
        private String roleName;
        private String roleRemark;
        private int roleType;
        private boolean selected;
        private List<?> ownResourceList;
        private List<?> ownUserList;
        private boolean isSelect;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public String getRoleCode() {
            return roleCode;
        }

        public void setRoleCode(String roleCode) {
            this.roleCode = roleCode;
        }

        public String getRoleGroupId() {
            return roleGroupId;
        }

        public void setRoleGroupId(String roleGroupId) {
            this.roleGroupId = roleGroupId;
        }

        public String getRoleId() {
            return roleId;
        }

        public void setRoleId(String roleId) {
            this.roleId = roleId;
        }

        public String getRoleName() {
            return roleName;
        }

        public void setRoleName(String roleName) {
            this.roleName = roleName;
        }

        public String getRoleRemark() {
            return roleRemark;
        }

        public void setRoleRemark(String roleRemark) {
            this.roleRemark = roleRemark;
        }

        public int getRoleType() {
            return roleType;
        }

        public void setRoleType(int roleType) {
            this.roleType = roleType;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public List<?> getOwnResourceList() {
            return ownResourceList;
        }

        public void setOwnResourceList(List<?> ownResourceList) {
            this.ownResourceList = ownResourceList;
        }

        public List<?> getOwnUserList() {
            return ownUserList;
        }

        public void setOwnUserList(List<?> ownUserList) {
            this.ownUserList = ownUserList;
        }
    }
}
