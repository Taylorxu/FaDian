package com.powerge.wise.powerge.operationProjo.net.net.response;

import java.util.List;

/**
 * Created by ycs on 2016/12/16.
 */

public class FindUserByRoleIdResponse extends BaseResponse {
    private List<ResultBean> result;

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean{

        /**
         * ownDeptList : []
         * ownRoleList : []
         * ownRoleName :
         * selected : false
         * userAccountnum : liqiang
         * userCellphone : 13298890987
         * userCustom1 :
         * userCustom2 :
         * userCustom3 :
         * userEmail : liqiang_3902@163.com
         * userEntrydate : null
         * userId : 082caba0-d667-41b1-a7a1-8ea766e4965c
         * userName : 李强
         * userNamePy : liqiang
         * userNum : 201501012
         * userPassword : /vTvwjGYKNCCgSriiU8FDA==
         * userPosition :
         * userRemark :
         * userSex : 1
         * userState : 1
         * userTelephone : 010-5793092
         */

        private String ownRoleName;
        private boolean selected;
        private String userAccountnum;
        private String userCellphone;
        private String userCustom1;
        private String userCustom2;
        private String userCustom3;
        private String userEmail;
        private Object userEntrydate;
        private String userId;
        private String userName;
        private String userNamePy;
        private String userNum;
        private String userPassword;
        private String userPosition;
        private String userRemark;
        private int userSex;
        private int userState;
        private String userTelephone;
        private List<?> ownDeptList;
        private List<?> ownRoleList;
        private boolean isSelect;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public String getOwnRoleName() {
            return ownRoleName;
        }

        public void setOwnRoleName(String ownRoleName) {
            this.ownRoleName = ownRoleName;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public String getUserAccountnum() {
            return userAccountnum;
        }

        public void setUserAccountnum(String userAccountnum) {
            this.userAccountnum = userAccountnum;
        }

        public String getUserCellphone() {
            return userCellphone;
        }

        public void setUserCellphone(String userCellphone) {
            this.userCellphone = userCellphone;
        }

        public String getUserCustom1() {
            return userCustom1;
        }

        public void setUserCustom1(String userCustom1) {
            this.userCustom1 = userCustom1;
        }

        public String getUserCustom2() {
            return userCustom2;
        }

        public void setUserCustom2(String userCustom2) {
            this.userCustom2 = userCustom2;
        }

        public String getUserCustom3() {
            return userCustom3;
        }

        public void setUserCustom3(String userCustom3) {
            this.userCustom3 = userCustom3;
        }

        public String getUserEmail() {
            return userEmail;
        }

        public void setUserEmail(String userEmail) {
            this.userEmail = userEmail;
        }

        public Object getUserEntrydate() {
            return userEntrydate;
        }

        public void setUserEntrydate(Object userEntrydate) {
            this.userEntrydate = userEntrydate;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserNamePy() {
            return userNamePy;
        }

        public void setUserNamePy(String userNamePy) {
            this.userNamePy = userNamePy;
        }

        public String getUserNum() {
            return userNum;
        }

        public void setUserNum(String userNum) {
            this.userNum = userNum;
        }

        public String getUserPassword() {
            return userPassword;
        }

        public void setUserPassword(String userPassword) {
            this.userPassword = userPassword;
        }

        public String getUserPosition() {
            return userPosition;
        }

        public void setUserPosition(String userPosition) {
            this.userPosition = userPosition;
        }

        public String getUserRemark() {
            return userRemark;
        }

        public void setUserRemark(String userRemark) {
            this.userRemark = userRemark;
        }

        public int getUserSex() {
            return userSex;
        }

        public void setUserSex(int userSex) {
            this.userSex = userSex;
        }

        public int getUserState() {
            return userState;
        }

        public void setUserState(int userState) {
            this.userState = userState;
        }

        public String getUserTelephone() {
            return userTelephone;
        }

        public void setUserTelephone(String userTelephone) {
            this.userTelephone = userTelephone;
        }

        public List<?> getOwnDeptList() {
            return ownDeptList;
        }

        public void setOwnDeptList(List<?> ownDeptList) {
            this.ownDeptList = ownDeptList;
        }

        public List<?> getOwnRoleList() {
            return ownRoleList;
        }

        public void setOwnRoleList(List<?> ownRoleList) {
            this.ownRoleList = ownRoleList;
        }
    }
}
