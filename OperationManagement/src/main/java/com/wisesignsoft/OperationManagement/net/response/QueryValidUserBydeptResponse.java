package com.wisesignsoft.OperationManagement.net.response;

import java.util.List;

/**
 * Created by ycs on 2017/1/9.
 */

public class QueryValidUserBydeptResponse extends BaseResponse {

    /**
     * userAccountnum : 人员帐号
     * userName : 人员姓名
     * userState : 人员状态
     * userPosition : 人员位置
     * userId : 用户id
     */

    private List<ReturnValueBean> returnValue;

    public List<ReturnValueBean> getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(List<ReturnValueBean> returnValue) {
        this.returnValue = returnValue;
    }

    public static class ReturnValueBean {
        private String userAccountnum;
        private String userName;
        private String userState;
        private String userPosition;
        private String userId;
        private boolean isSelect;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public String getUserAccountnum() {
            return userAccountnum;
        }

        public void setUserAccountnum(String userAccountnum) {
            this.userAccountnum = userAccountnum;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserState() {
            return userState;
        }

        public void setUserState(String userState) {
            this.userState = userState;
        }

        public String getUserPosition() {
            return userPosition;
        }

        public void setUserPosition(String userPosition) {
            this.userPosition = userPosition;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
}
