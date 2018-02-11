package com.wisesignsoft.OperationManagement.net.response;

import java.util.List;

/**
 * Created by ycs on 2016/12/1.
 */

public class FindWonhInfoResponse extends BaseResponse {

    /**
     * nodeUser :
     * nodeOpTime : 2016-10-13 16:10:22
     * nodeName : 开始
     * nodeRole :
     * nodeLineName : 开始--问题发起
     * nodeType : 开始
     * nodeOpType : 自动流转
     */

    private List<WonhListBean> wonhList;

    public List<WonhListBean> getWonhList() {
        return wonhList;
    }

    public void setWonhList(List<WonhListBean> wonhList) {
        this.wonhList = wonhList;
    }

    public static class WonhListBean {
        private String nodeUser;
        private String nodeOpTime;
        private String nodeName;
        private String nodeRole;
        private String nodeLineName;
        private String nodeType;
        private String nodeOpType;

        public String getNodeUser() {
            return nodeUser;
        }

        public void setNodeUser(String nodeUser) {
            this.nodeUser = nodeUser;
        }

        public String getNodeOpTime() {
            return nodeOpTime;
        }

        public void setNodeOpTime(String nodeOpTime) {
            this.nodeOpTime = nodeOpTime;
        }

        public String getNodeName() {
            return nodeName;
        }

        public void setNodeName(String nodeName) {
            this.nodeName = nodeName;
        }

        public String getNodeRole() {
            return nodeRole;
        }

        public void setNodeRole(String nodeRole) {
            this.nodeRole = nodeRole;
        }

        public String getNodeLineName() {
            return nodeLineName;
        }

        public void setNodeLineName(String nodeLineName) {
            this.nodeLineName = nodeLineName;
        }

        public String getNodeType() {
            return nodeType;
        }

        public void setNodeType(String nodeType) {
            this.nodeType = nodeType;
        }

        public String getNodeOpType() {
            return nodeOpType;
        }

        public void setNodeOpType(String nodeOpType) {
            this.nodeOpType = nodeOpType;
        }
    }
}
