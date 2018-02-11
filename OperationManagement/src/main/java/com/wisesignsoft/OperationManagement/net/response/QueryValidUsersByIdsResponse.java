package com.wisesignsoft.OperationManagement.net.response;

import java.util.List;

/**
 * Created by ycs on 2017/1/3.
 */

public class QueryValidUsersByIdsResponse extends BaseResponse {

    /**
     * userState : 0
     * userGender : 0
     * userDept : 服务中心经理室
     * userName : 系统管理员
     * userEmail :
     * userJob : null
     * userMove : null
     * userId
     */

    private List<ReturnValueBean> returnValue;

    public List<ReturnValueBean> getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(List<ReturnValueBean> returnValue) {
        this.returnValue = returnValue;
    }

    public static class ReturnValueBean {
        private String userState;
        private String userGender;
        private String userDept;
        private String userName;
        private String userEmail;
        private Object userJob;
        private Object userMove;
        private String userId;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserState() {
            return userState;
        }

        public void setUserState(String userState) {
            this.userState = userState;
        }

        public String getUserGender() {
            return userGender;
        }

        public void setUserGender(String userGender) {
            this.userGender = userGender;
        }

        public String getUserDept() {
            return userDept;
        }

        public void setUserDept(String userDept) {
            this.userDept = userDept;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserEmail() {
            return userEmail;
        }

        public void setUserEmail(String userEmail) {
            this.userEmail = userEmail;
        }

        public Object getUserJob() {
            return userJob;
        }

        public void setUserJob(Object userJob) {
            this.userJob = userJob;
        }

        public Object getUserMove() {
            return userMove;
        }

        public void setUserMove(Object userMove) {
            this.userMove = userMove;
        }
    }
}
