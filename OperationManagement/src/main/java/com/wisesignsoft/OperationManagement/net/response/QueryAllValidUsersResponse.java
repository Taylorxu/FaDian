package com.wisesignsoft.OperationManagement.net.response;

import java.util.List;

/**
 * Created by ycs on 2016/12/5.
 */

public class QueryAllValidUsersResponse extends BaseResponse {

    /**
     * userState : null
     * userName : aaa
     * userAccountnum : 123123
     * userPosition : null
     */

    private List<ReturnValueBean> returnValue;

    public List<ReturnValueBean> getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(List<ReturnValueBean> returnValue) {
        this.returnValue = returnValue;
    }

    public static class ReturnValueBean {
        private Object userState;
        private String userName;
        private String userAccountnum;
        private Object userPosition;
        private String userId;
        private boolean isSelect;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public Object getUserState() {
            return userState;
        }

        public void setUserState(Object userState) {
            this.userState = userState;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserAccountnum() {
            return userAccountnum;
        }

        public void setUserAccountnum(String userAccountnum) {
            this.userAccountnum = userAccountnum;
        }

        public Object getUserPosition() {
            return userPosition;
        }

        public void setUserPosition(Object userPosition) {
            this.userPosition = userPosition;
        }
    }
}
