package com.powerge.wise.powerge.operationProjo.net.net.response;

import java.util.List;

/**
 * Created by ycs on 2016/11/28.
 */

public class FindUnHandleProcessResponse extends BaseResponse {
    /**
     * total : 1
     * data : [{"PIKEY":"工单号","PROCESS_KEY_NAME":"流程类型","TITLE":"标题","CURRENT_DEALER":"当前处理人","CREATOR":"流程创建人名称","CREATEDATE":"创建时间"}]
     */

    private String total;
    /**
     * PIKEY : 工单号
     * PROCESS_KEY_NAME : 流程类型
     * TITLE : 标题
     * CURRENT_DEALER : 当前处理人
     * CREATOR : 流程创建人名称
     * CREATEDATE : 创建时间
     */

    private List<DataBean> data;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String PIKEY;
        private String PROCESS_KEY_NAME;
        private String TITLE;
        private String CURRENT_DEALER;
        private String CREATOR;
        private String CREATEDATE;

        public String getPIKEY() {
            return PIKEY;
        }

        public void setPIKEY(String PIKEY) {
            this.PIKEY = PIKEY;
        }

        public String getPROCESS_KEY_NAME() {
            return PROCESS_KEY_NAME;
        }

        public void setPROCESS_KEY_NAME(String PROCESS_KEY_NAME) {
            this.PROCESS_KEY_NAME = PROCESS_KEY_NAME;
        }

        public String getTITLE() {
            return TITLE;
        }

        public void setTITLE(String TITLE) {
            this.TITLE = TITLE;
        }

        public String getCURRENT_DEALER() {
            return CURRENT_DEALER;
        }

        public void setCURRENT_DEALER(String CURRENT_DEALER) {
            this.CURRENT_DEALER = CURRENT_DEALER;
        }

        public String getCREATOR() {
            return CREATOR;
        }

        public void setCREATOR(String CREATOR) {
            this.CREATOR = CREATOR;
        }

        public String getCREATEDATE() {
            return CREATEDATE;
        }

        public void setCREATEDATE(String CREATEDATE) {
            this.CREATEDATE = CREATEDATE;
        }
    }
}
