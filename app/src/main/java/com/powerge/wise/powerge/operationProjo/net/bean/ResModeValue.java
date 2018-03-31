package com.powerge.wise.powerge.operationProjo.net.bean;

import java.util.List;

/**
 * Created by ycs on 2016/12/12.
 */

public class ResModeValue {

    /**
     * bmDisplayName : 合同信息
     * bmModelName : co
     * configValueJson : [{"mx_internal_uid":"AAF1357C-B194-26BA-A7A5-6C8F93681F51","fromDmAttrName":{"id":"textinput_3","dmAttrName":"CO_CUNA"},"toFmAttrName":{"id":"textinput_2","dmAttrName":"CUST_NAME"}},{"mx_internal_uid":"88572E54-A104-0B8C-B387-7C4303051A4C","fromDmAttrName":{"id":"textinput_2","dmAttrName":"CO_NO"},"toFmAttrName":{"id":"textinput_1","dmAttrName":"CUST_NO"}}]
     * dmAttrName : OBJ_PROI
     * dmDisplayName : 合同信息
     * toBmModelName : CONTRACTS
     */

    private String bmDisplayName;
    private String bmModelName;
    private String dmAttrName;
    private String dmDisplayName;
    private String toBmModelName;
    /**
     * mx_internal_uid : AAF1357C-B194-26BA-A7A5-6C8F93681F51
     * fromDmAttrName : {"id":"textinput_3","dmAttrName":"CO_CUNA"}
     * toFmAttrName : {"id":"textinput_2","dmAttrName":"CUST_NAME"}
     */

    private List<ConfigValueJsonBean> configValueJson;

    public String getBmDisplayName() {
        return bmDisplayName;
    }

    public void setBmDisplayName(String bmDisplayName) {
        this.bmDisplayName = bmDisplayName;
    }

    public String getBmModelName() {
        return bmModelName;
    }

    public void setBmModelName(String bmModelName) {
        this.bmModelName = bmModelName;
    }

    public String getDmAttrName() {
        return dmAttrName;
    }

    public void setDmAttrName(String dmAttrName) {
        this.dmAttrName = dmAttrName;
    }

    public String getDmDisplayName() {
        return dmDisplayName;
    }

    public void setDmDisplayName(String dmDisplayName) {
        this.dmDisplayName = dmDisplayName;
    }

    public String getToBmModelName() {
        return toBmModelName;
    }

    public void setToBmModelName(String toBmModelName) {
        this.toBmModelName = toBmModelName;
    }

    public List<ConfigValueJsonBean> getConfigValueJson() {
        return configValueJson;
    }

    public void setConfigValueJson(List<ConfigValueJsonBean> configValueJson) {
        this.configValueJson = configValueJson;
    }

    public static class ConfigValueJsonBean {
        private String mx_internal_uid;
        /**
         * id : textinput_3
         * dmAttrName : CO_CUNA
         */

        private FromDmAttrNameBean fromDmAttrName;
        /**
         * id : textinput_2
         * dmAttrName : CUST_NAME
         */

        private ToFmAttrNameBean toFmAttrName;

        public String getMx_internal_uid() {
            return mx_internal_uid;
        }

        public void setMx_internal_uid(String mx_internal_uid) {
            this.mx_internal_uid = mx_internal_uid;
        }

        public FromDmAttrNameBean getFromDmAttrName() {
            return fromDmAttrName;
        }

        public void setFromDmAttrName(FromDmAttrNameBean fromDmAttrName) {
            this.fromDmAttrName = fromDmAttrName;
        }

        public ToFmAttrNameBean getToFmAttrName() {
            return toFmAttrName;
        }

        public void setToFmAttrName(ToFmAttrNameBean toFmAttrName) {
            this.toFmAttrName = toFmAttrName;
        }

        public static class FromDmAttrNameBean {
            private String id;
            private String dmAttrName;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getDmAttrName() {
                return dmAttrName;
            }

            public void setDmAttrName(String dmAttrName) {
                this.dmAttrName = dmAttrName;
            }
        }

        public static class ToFmAttrNameBean {
            private String id;
            private String dmAttrName;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getDmAttrName() {
                return dmAttrName;
            }

            public void setDmAttrName(String dmAttrName) {
                this.dmAttrName = dmAttrName;
            }
        }
    }
}
