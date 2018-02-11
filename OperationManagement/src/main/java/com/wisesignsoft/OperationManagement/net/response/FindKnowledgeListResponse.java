package com.wisesignsoft.OperationManagement.net.response;

import java.util.List;
import java.util.Map;

/**
 * Created by ycs on 2016/12/7.
 */

public class FindKnowledgeListResponse extends BaseResponse {

    /**
     * total : 1
     * data : [{"COLORS":"","K_TITLE":"111","LAST_MODIFIED_TIME":"2015-07-07 17:58:41","K_ATTACHMENT":"","LAST_MODIFIED_NAME":"null","K_SUBMIT_TIME":"2015-07-07 17:51:14","CREATOR":"null","K_KEYWORD":"1111","WORK_ORDER_NO":"K1507070007","K_NUMBER":"2015-4","K_SUBMIT_USER":"","K_ATTACHMENT_NAME":"null","CREATEDATE":"2015-07-07 17:58:41","K_DESC":"1111","K_KNOW_CAT":"基础运维","ISCANCEL":"0","OBJECTID":"KNOWLEDGE:2bf9e22b-fad0-4785-983b-fe8dcc64f77e"}]
     */

    private String total;
    /**
     * COLORS :
     * K_TITLE : 111
     * LAST_MODIFIED_TIME : 2015-07-07 17:58:41
     * K_ATTACHMENT :
     * LAST_MODIFIED_NAME : null
     * K_SUBMIT_TIME : 2015-07-07 17:51:14
     * CREATOR : null
     * K_KEYWORD : 1111
     * WORK_ORDER_NO : K1507070007
     * K_NUMBER : 2015-4
     * K_SUBMIT_USER :
     * K_ATTACHMENT_NAME : null
     * CREATEDATE : 2015-07-07 17:58:41
     * K_DESC : 1111
     * K_KNOW_CAT : 基础运维
     * ISCANCEL : 0
     * OBJECTID : KNOWLEDGE:2bf9e22b-fad0-4785-983b-fe8dcc64f77e
     */

    private List<Map<String,String>> data;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Map<String, String>> getData() {
        return data;
    }

    public void setData(List<Map<String, String>> data) {
        this.data = data;
    }

    public static class DataBean {
        private String COLORS;
        private String K_TITLE;
        private String LAST_MODIFIED_TIME;
        private String K_ATTACHMENT;
        private String LAST_MODIFIED_NAME;
        private String K_SUBMIT_TIME;
        private String CREATOR;
        private String K_KEYWORD;
        private String WORK_ORDER_NO;
        private String K_NUMBER;
        private String K_SUBMIT_USER;
        private String K_ATTACHMENT_NAME;
        private String CREATEDATE;
        private String K_DESC;
        private String K_KNOW_CAT;
        private String ISCANCEL;
        private String OBJECTID;

        public String getCOLORS() {
            return COLORS;
        }

        public void setCOLORS(String COLORS) {
            this.COLORS = COLORS;
        }

        public String getK_TITLE() {
            return K_TITLE;
        }

        public void setK_TITLE(String K_TITLE) {
            this.K_TITLE = K_TITLE;
        }

        public String getLAST_MODIFIED_TIME() {
            return LAST_MODIFIED_TIME;
        }

        public void setLAST_MODIFIED_TIME(String LAST_MODIFIED_TIME) {
            this.LAST_MODIFIED_TIME = LAST_MODIFIED_TIME;
        }

        public String getK_ATTACHMENT() {
            return K_ATTACHMENT;
        }

        public void setK_ATTACHMENT(String K_ATTACHMENT) {
            this.K_ATTACHMENT = K_ATTACHMENT;
        }

        public String getLAST_MODIFIED_NAME() {
            return LAST_MODIFIED_NAME;
        }

        public void setLAST_MODIFIED_NAME(String LAST_MODIFIED_NAME) {
            this.LAST_MODIFIED_NAME = LAST_MODIFIED_NAME;
        }

        public String getK_SUBMIT_TIME() {
            return K_SUBMIT_TIME;
        }

        public void setK_SUBMIT_TIME(String K_SUBMIT_TIME) {
            this.K_SUBMIT_TIME = K_SUBMIT_TIME;
        }

        public String getCREATOR() {
            return CREATOR;
        }

        public void setCREATOR(String CREATOR) {
            this.CREATOR = CREATOR;
        }

        public String getK_KEYWORD() {
            return K_KEYWORD;
        }

        public void setK_KEYWORD(String K_KEYWORD) {
            this.K_KEYWORD = K_KEYWORD;
        }

        public String getWORK_ORDER_NO() {
            return WORK_ORDER_NO;
        }

        public void setWORK_ORDER_NO(String WORK_ORDER_NO) {
            this.WORK_ORDER_NO = WORK_ORDER_NO;
        }

        public String getK_NUMBER() {
            return K_NUMBER;
        }

        public void setK_NUMBER(String K_NUMBER) {
            this.K_NUMBER = K_NUMBER;
        }

        public String getK_SUBMIT_USER() {
            return K_SUBMIT_USER;
        }

        public void setK_SUBMIT_USER(String K_SUBMIT_USER) {
            this.K_SUBMIT_USER = K_SUBMIT_USER;
        }

        public String getK_ATTACHMENT_NAME() {
            return K_ATTACHMENT_NAME;
        }

        public void setK_ATTACHMENT_NAME(String K_ATTACHMENT_NAME) {
            this.K_ATTACHMENT_NAME = K_ATTACHMENT_NAME;
        }

        public String getCREATEDATE() {
            return CREATEDATE;
        }

        public void setCREATEDATE(String CREATEDATE) {
            this.CREATEDATE = CREATEDATE;
        }

        public String getK_DESC() {
            return K_DESC;
        }

        public void setK_DESC(String K_DESC) {
            this.K_DESC = K_DESC;
        }

        public String getK_KNOW_CAT() {
            return K_KNOW_CAT;
        }

        public void setK_KNOW_CAT(String K_KNOW_CAT) {
            this.K_KNOW_CAT = K_KNOW_CAT;
        }

        public String getISCANCEL() {
            return ISCANCEL;
        }

        public void setISCANCEL(String ISCANCEL) {
            this.ISCANCEL = ISCANCEL;
        }

        public String getOBJECTID() {
            return OBJECTID;
        }

        public void setOBJECTID(String OBJECTID) {
            this.OBJECTID = OBJECTID;
        }
    }
}
