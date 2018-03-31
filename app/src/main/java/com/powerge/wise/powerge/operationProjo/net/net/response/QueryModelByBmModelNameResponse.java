package com.powerge.wise.powerge.operationProjo.net.net.response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ycs on 2016/12/17.
 */

public class QueryModelByBmModelNameResponse extends BaseResponse {

    /**
     * attrDefineList : [{"configModifiedField":"","dataType":"string","defaultValue":"","dictModelName":"","dmAttrDisplayName":"客户编码","dmAttrName":"CUST_NO","hasBusinessKey":false,"hasCanUpdate":false,"hasDateTime":false,"hasModified":true,"hasMult":false,"hasMultiChoice":false,"hasRealTimeUpdate":false,"hasVisible":true,"id":"textinput_1","identityRule":"","inputLength":0,"name":"客户编码","parentDeptJSON":"","precision":0,"required":false,"resModelValueJson":"","type":"TextInput"},{"configModifiedField":"","dataType":"string","defaultValue":"","dictModelName":"","dmAttrDisplayName":"客户名称","dmAttrName":"CUST_NAME","hasBusinessKey":false,"hasCanUpdate":false,"hasDateTime":false,"hasModified":true,"hasMult":false,"hasMultiChoice":false,"hasRealTimeUpdate":false,"hasVisible":true,"id":"textinput_2","identityRule":"","inputLength":0,"name":"客户名称","parentDeptJSON":"","precision":0,"required":false,"resModelValueJson":"","type":"TextInput"},{"configModifiedField":"","dataType":"string","defaultValue":"","dictModelName":"","dmAttrDisplayName":"项目编号","dmAttrName":"PROJ_NO","hasBusinessKey":false,"hasCanUpdate":false,"hasDateTime":false,"hasModified":true,"hasMult":false,"hasMultiChoice":false,"hasRealTimeUpdate":false,"hasVisible":true,"id":"textinput_4","identityRule":"","inputLength":0,"name":"项目编号","parentDeptJSON":"","precision":0,"required":false,"resModelValueJson":"","type":"TextInput"},{"configModifiedField":"","dataType":"string","defaultValue":"","dictModelName":"","dmAttrDisplayName":"项目名称","dmAttrName":"PROJ_NAME","hasBusinessKey":true,"hasCanUpdate":false,"hasDateTime":false,"hasModified":true,"hasMult":false,"hasMultiChoice":false,"hasRealTimeUpdate":false,"hasVisible":true,"id":"textinput_5","identityRule":"","inputLength":0,"name":"项目名称","parentDeptJSON":"","precision":0,"required":false,"resModelValueJson":"","type":"TextInput"},{"configModifiedField":"","dataType":"string","defaultValue":"","dictModelName":"","dmAttrDisplayName":"合同编号","dmAttrName":"CON_NO","hasBusinessKey":false,"hasCanUpdate":false,"hasDateTime":false,"hasModified":true,"hasMult":false,"hasMultiChoice":false,"hasRealTimeUpdate":false,"hasVisible":true,"id":"textinput_6","identityRule":"","inputLength":0,"name":"合同编号","parentDeptJSON":"","precision":0,"required":false,"resModelValueJson":"","type":"TextInput"},{"configModifiedField":"","dataType":"string","defaultValue":"","dictModelName":"CUST_LEV","dmAttrDisplayName":"客户级别","dmAttrName":"CUST_LEV","hasBusinessKey":false,"hasCanUpdate":false,"hasDateTime":false,"hasModified":true,"hasMult":false,"hasMultiChoice":false,"hasRealTimeUpdate":false,"hasVisible":true,"id":"combobox_1","identityRule":"","inputLength":0,"name":"客户级别","parentDeptJSON":"","precision":0,"required":false,"resModelValueJson":"","type":"ComboBox"},{"configModifiedField":"","dataType":"string","defaultValue":"","dictModelName":"","dmAttrDisplayName":"业务员","dmAttrName":"PROJ_SALE","hasBusinessKey":false,"hasCanUpdate":false,"hasDateTime":false,"hasModified":true,"hasMult":false,"hasMultiChoice":false,"hasRealTimeUpdate":false,"hasVisible":true,"id":"textinput_7","identityRule":"","inputLength":0,"name":"业务员","parentDeptJSON":"","precision":0,"required":false,"resModelValueJson":"","type":"TextInput"},{"configModifiedField":"","dataType":"string","defaultValue":"","dictModelName":"","dmAttrDisplayName":"项目经理","dmAttrName":"PROJ_PM","hasBusinessKey":false,"hasCanUpdate":false,"hasDateTime":false,"hasModified":true,"hasMult":false,"hasMultiChoice":false,"hasRealTimeUpdate":false,"hasVisible":true,"id":"textinput_8","identityRule":"","inputLength":0,"name":"项目经理","parentDeptJSON":"","precision":0,"required":false,"resModelValueJson":"","type":"TextInput"},{"configModifiedField":"","dataType":"string","defaultValue":"","dictModelName":"PROJ_ST","dmAttrDisplayName":"服务类型","dmAttrName":"PROJ_ST","hasBusinessKey":false,"hasCanUpdate":false,"hasDateTime":false,"hasModified":true,"hasMult":false,"hasMultiChoice":false,"hasRealTimeUpdate":false,"hasVisible":true,"id":"combobox_2","identityRule":"","inputLength":0,"name":"服务类型","parentDeptJSON":"","precision":0,"required":false,"resModelValueJson":"","type":"ComboBox"},{"configModifiedField":"","dataType":"string","defaultValue":"","dictModelName":"PROJ_SM","dmAttrDisplayName":"服务方式","dmAttrName":"PROJ_SM","hasBusinessKey":false,"hasCanUpdate":false,"hasDateTime":false,"hasModified":true,"hasMult":false,"hasMultiChoice":false,"hasRealTimeUpdate":false,"hasVisible":true,"id":"combobox_3","identityRule":"","inputLength":0,"name":"服务方式","parentDeptJSON":"","precision":0,"required":false,"resModelValueJson":"","type":"ComboBox"},{"configModifiedField":"","dataType":"string","defaultValue":"","dictModelName":"","dmAttrDisplayName":"总结报告提交周期","dmAttrName":"PROJ_SRP","hasBusinessKey":false,"hasCanUpdate":false,"hasDateTime":false,"hasModified":true,"hasMult":false,"hasMultiChoice":false,"hasRealTimeUpdate":false,"hasVisible":true,"id":"textinput_9","identityRule":"","inputLength":0,"name":"总结报告提交周期","parentDeptJSON":"","precision":0,"required":false,"resModelValueJson":"","type":"TextInput"},{"configModifiedField":"","dataType":"string","defaultValue":"","dictModelName":"","dmAttrDisplayName":"培训课程","dmAttrName":"PROJ_TC","hasBusinessKey":false,"hasCanUpdate":false,"hasDateTime":false,"hasModified":true,"hasMult":false,"hasMultiChoice":false,"hasRealTimeUpdate":false,"hasVisible":true,"id":"textinput_10","identityRule":"","inputLength":0,"name":"培训课程","parentDeptJSON":"","precision":0,"required":false,"resModelValueJson":"","type":"TextInput"},{"configModifiedField":"","dataType":"string","defaultValue":"","dictModelName":"","dmAttrDisplayName":"培训人次","dmAttrName":"PROJ_TP","hasBusinessKey":false,"hasCanUpdate":false,"hasDateTime":false,"hasModified":true,"hasMult":false,"hasMultiChoice":false,"hasRealTimeUpdate":false,"hasVisible":true,"id":"textinput_11","identityRule":"","inputLength":0,"name":"培训人次","parentDeptJSON":"","precision":0,"required":false,"resModelValueJson":"","type":"TextInput"},{"configModifiedField":"","dataType":"string","defaultValue":"","dictModelName":"","dmAttrDisplayName":"其他重要承诺","dmAttrName":"PROJ_OIC","hasBusinessKey":false,"hasCanUpdate":false,"hasDateTime":false,"hasModified":true,"hasMult":false,"hasMultiChoice":false,"hasRealTimeUpdate":false,"hasVisible":true,"id":"textarea_1","identityRule":"","inputLength":0,"name":"其他重要承诺","parentDeptJSON":"","precision":0,"required":false,"resModelValueJson":"","type":"TextArea"},{"configModifiedField":"","dataType":"string","defaultValue":"","dictModelName":"","dmAttrDisplayName":"原始项目编号","dmAttrName":"PROJ_OPN","hasBusinessKey":false,"hasCanUpdate":false,"hasDateTime":false,"hasModified":true,"hasMult":false,"hasMultiChoice":false,"hasRealTimeUpdate":false,"hasVisible":true,"id":"textinput_12","identityRule":"","inputLength":0,"name":"原始项目编号","parentDeptJSON":"","precision":0,"required":false,"resModelValueJson":"","type":"TextInput"},{"configModifiedField":"","dataType":"string","defaultValue":"","dictModelName":"","dmAttrDisplayName":"用户回访喜好","dmAttrName":"CUST_RP","hasBusinessKey":false,"hasCanUpdate":false,"hasDateTime":false,"hasModified":true,"hasMult":false,"hasMultiChoice":false,"hasRealTimeUpdate":false,"hasVisible":true,"id":"textinput_14","identityRule":"","inputLength":0,"name":"用户回访喜好","parentDeptJSON":"","precision":0,"required":false,"resModelValueJson":"","type":"TextInput"},{"configModifiedField":"","dataType":"string","defaultValue":"","dictModelName":"","dmAttrDisplayName":"合同状态","dmAttrName":"CON_STAT","hasBusinessKey":false,"hasCanUpdate":false,"hasDateTime":false,"hasModified":true,"hasMult":false,"hasMultiChoice":false,"hasRealTimeUpdate":false,"hasVisible":true,"id":"textinput_15","identityRule":"","inputLength":0,"name":"合同状态","parentDeptJSON":"","precision":0,"required":false,"resModelValueJson":"","type":"TextInput"},{"configModifiedField":"","dataType":"string","defaultValue":"","dictModelName":"","dmAttrDisplayName":"客户位置","dmAttrName":"CUST_LOCATE","hasBusinessKey":false,"hasCanUpdate":false,"hasDateTime":false,"hasModified":true,"hasMult":false,"hasMultiChoice":false,"hasRealTimeUpdate":false,"hasVisible":true,"id":"textinput_16","identityRule":"","inputLength":0,"name":"客户位置","parentDeptJSON":"","precision":0,"required":false,"resModelValueJson":"","type":"TextInput"},{"configModifiedField":"","dataType":"string","defaultValue":"yesorno:bfb0d4b8-0f93-4c44-b124-b3a43e2c3798","dictModelName":"yesorno","dmAttrDisplayName":"是否VIP","dmAttrName":"WHETHER_VIP","hasBusinessKey":false,"hasCanUpdate":false,"hasDateTime":false,"hasModified":true,"hasMult":false,"hasMultiChoice":false,"hasRealTimeUpdate":false,"hasVisible":true,"id":"combobox_4","identityRule":"","inputLength":0,"name":"是否为VIP用户","parentDeptJSON":"","precision":0,"required":true,"resModelValueJson":"","type":"ComboBox"},{"configModifiedField":"","dataType":"date","defaultValue":"","dictModelName":"","dmAttrDisplayName":"起始日期","dmAttrName":"PROJ_SD","hasBusinessKey":false,"hasCanUpdate":false,"hasDateTime":true,"hasModified":true,"hasMult":false,"hasMultiChoice":false,"hasRealTimeUpdate":false,"hasVisible":true,"id":"datefeild_1","identityRule":"","inputLength":0,"name":"起始日期","parentDeptJSON":"","precision":0,"required":false,"resModelValueJson":"","type":"DateFeild"},{"configModifiedField":"","dataType":"date","defaultValue":"","dictModelName":"","dmAttrDisplayName":"结束日期","dmAttrName":"PROJ_ED","hasBusinessKey":false,"hasCanUpdate":false,"hasDateTime":true,"hasModified":true,"hasMult":false,"hasMultiChoice":false,"hasRealTimeUpdate":false,"hasVisible":true,"id":"datefeild_2","identityRule":"","inputLength":0,"name":"结束日期","parentDeptJSON":"","precision":0,"required":false,"resModelValueJson":"","type":"DateFeild"},{"configModifiedField":"","dataType":"string","defaultValue":"","dictModelName":"","dmAttrDisplayName":"客户电话","dmAttrName":"CUST_PHONE","hasBusinessKey":false,"hasCanUpdate":false,"hasDateTime":false,"hasModified":true,"hasMult":false,"hasMultiChoice":false,"hasRealTimeUpdate":false,"hasVisible":true,"id":"textinput_17","identityRule":"","inputLength":0,"name":"客户电话","parentDeptJSON":"","precision":0,"required":false,"resModelValueJson":"","type":"TextInput"},{"configModifiedField":"","dataType":"string","defaultValue":"","dictModelName":"CUST_SUB","dmAttrDisplayName":"是否转包客户","dmAttrName":"CUST_SUB","hasBusinessKey":false,"hasCanUpdate":false,"hasDateTime":false,"hasModified":true,"hasMult":false,"hasMultiChoice":false,"hasRealTimeUpdate":false,"hasVisible":true,"id":"combobox_5","identityRule":"","inputLength":0,"name":"是否转包客户","parentDeptJSON":"","precision":0,"required":false,"resModelValueJson":"","type":"ComboBox"},{"configModifiedField":"","dataType":"string","defaultValue":"","dictModelName":"CUST_DIS","dmAttrDisplayName":"区域","dmAttrName":"CUST_DIS","hasBusinessKey":false,"hasCanUpdate":false,"hasDateTime":false,"hasModified":true,"hasMult":false,"hasMultiChoice":false,"hasRealTimeUpdate":false,"hasVisible":true,"id":"combobox_6","identityRule":"","inputLength":0,"name":"区域","parentDeptJSON":"","precision":0,"required":false,"resModelValueJson":"","type":"ComboBox"},{"configModifiedField":"","dataType":"string","defaultValue":"","dictModelName":"PROJ_RT","dmAttrDisplayName":"响应时间","dmAttrName":"PROJ_RT","hasBusinessKey":false,"hasCanUpdate":false,"hasDateTime":false,"hasModified":true,"hasMult":false,"hasMultiChoice":false,"hasRealTimeUpdate":false,"hasVisible":true,"id":"combobox_7","identityRule":"","inputLength":0,"name":"响应时限","parentDeptJSON":"","precision":0,"required":false,"resModelValueJson":"","type":"ComboBox"},{"configModifiedField":"","dataType":"string","defaultValue":"","dictModelName":"PROJ_AATS","dmAttrDisplayName":"到达现场时限","dmAttrName":"PROJ_AATS","hasBusinessKey":false,"hasCanUpdate":false,"hasDateTime":false,"hasModified":true,"hasMult":false,"hasMultiChoice":false,"hasRealTimeUpdate":false,"hasVisible":true,"id":"combobox_8","identityRule":"","inputLength":0,"name":"到达现场时限","parentDeptJSON":"","precision":0,"required":false,"resModelValueJson":"","type":"ComboBox"},{"configModifiedField":"","dataType":"string","defaultValue":"","dictModelName":"PROJ_TS","dmAttrDisplayName":"故障解决时限","dmAttrName":"PROJ_TS","hasBusinessKey":false,"hasCanUpdate":false,"hasDateTime":false,"hasModified":true,"hasMult":false,"hasMultiChoice":false,"hasRealTimeUpdate":false,"hasVisible":true,"id":"combobox_9","identityRule":"","inputLength":0,"name":"故障解决时限","parentDeptJSON":"","precision":0,"required":false,"resModelValueJson":"","type":"ComboBox"},{"configModifiedField":"","dataType":"string","defaultValue":"","dictModelName":"PROJ_PI","dmAttrDisplayName":"巡检周期","dmAttrName":"PROJ_PI","hasBusinessKey":false,"hasCanUpdate":false,"hasDateTime":false,"hasModified":true,"hasMult":false,"hasMultiChoice":false,"hasRealTimeUpdate":false,"hasVisible":true,"id":"combobox_10","identityRule":"","inputLength":0,"name":"巡检周期","parentDeptJSON":"","precision":0,"required":false,"resModelValueJson":"","type":"ComboBox"}]
     * bmDisplayName : 合同信息
     * bmHelp :
     * bmId : 35d4d777-9bd0-4b8f-b861-b73b7089de9d
     * bmModelName : CONTRACTS
     * bmProcessorJson :
     * bmValidateJson : [{'componentsJson':'CUST_PHONE','methodName':'isMobilePhone','mx_internal_uid':'CFF87860-74F6-7498-AC2A-7246C7353F7E','methodDesc':'验证手机号码','mustPass':'true','checkJson':'[{conditionId_key_0:CUST_PHONE}]'}]
     * businessKeyAttrDefine : {"configModifiedField":"","dataType":"string","defaultValue":"","dictModelName":"","dmAttrDisplayName":"项目名称","dmAttrName":"PROJ_NAME","hasBusinessKey":true,"hasCanUpdate":false,"hasDateTime":false,"hasModified":true,"hasMult":false,"hasMultiChoice":false,"hasRealTimeUpdate":false,"hasVisible":true,"id":"textinput_5","identityRule":"","inputLength":0,"name":"项目名称","parentDeptJSON":"","precision":0,"required":false,"resModelValueJson":"","type":"TextInput"}
     * dmDisplayName : 合同信息
     * dmModelName : CONTRACTS
     * iconName :
     * order : 0
     * superBmModelName : ACCOUNT_FORM
     * width : 700
     */

    private ReturnValueBean returnValue;

    public ReturnValueBean getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(ReturnValueBean returnValue) {
        this.returnValue = returnValue;
    }

    public static class ReturnValueBean {
        private String bmDisplayName;
        private String bmHelp;
        private String bmId;
        private String bmModelName;
        private String bmProcessorJson;
        private String bmValidateJson;
        /**
         * configModifiedField :
         * dataType : string
         * defaultValue :
         * dictModelName :
         * dmAttrDisplayName : 项目名称
         * dmAttrName : PROJ_NAME
         * hasBusinessKey : true
         * hasCanUpdate : false
         * hasDateTime : false
         * hasModified : true
         * hasMult : false
         * hasMultiChoice : false
         * hasRealTimeUpdate : false
         * hasVisible : true
         * id : textinput_5
         * identityRule :
         * inputLength : 0
         * name : 项目名称
         * parentDeptJSON :
         * precision : 0
         * required : false
         * resModelValueJson :
         * type : TextInput
         */

        private BusinessKeyAttrDefineBean businessKeyAttrDefine;
        private String dmDisplayName;
        private String dmModelName;
        private String iconName;
        private int order;
        private String superBmModelName;
        private String width;
        /**
         * configModifiedField :
         * dataType : string
         * defaultValue :
         * dictModelName :
         * dmAttrDisplayName : 客户编码
         * dmAttrName : CUST_NO
         * hasBusinessKey : false
         * hasCanUpdate : false
         * hasDateTime : false
         * hasModified : true
         * hasMult : false
         * hasMultiChoice : false
         * hasRealTimeUpdate : false
         * hasVisible : true
         * id : textinput_1
         * identityRule :
         * inputLength : 0
         * name : 客户编码
         * parentDeptJSON :
         * precision : 0
         * required : false
         * resModelValueJson :
         * type : TextInput
         */

        private List<AttrDefineListBean> attrDefineList;



        public String getBmDisplayName() {
            return bmDisplayName;
        }

        public void setBmDisplayName(String bmDisplayName) {
            this.bmDisplayName = bmDisplayName;
        }

        public String getBmHelp() {
            return bmHelp;
        }

        public void setBmHelp(String bmHelp) {
            this.bmHelp = bmHelp;
        }

        public String getBmId() {
            return bmId;
        }

        public void setBmId(String bmId) {
            this.bmId = bmId;
        }

        public String getBmModelName() {
            return bmModelName;
        }

        public void setBmModelName(String bmModelName) {
            this.bmModelName = bmModelName;
        }

        public String getBmProcessorJson() {
            return bmProcessorJson;
        }

        public void setBmProcessorJson(String bmProcessorJson) {
            this.bmProcessorJson = bmProcessorJson;
        }

        public String getBmValidateJson() {
            return bmValidateJson;
        }

        public void setBmValidateJson(String bmValidateJson) {
            this.bmValidateJson = bmValidateJson;
        }

        public BusinessKeyAttrDefineBean getBusinessKeyAttrDefine() {
            return businessKeyAttrDefine;
        }

        public void setBusinessKeyAttrDefine(BusinessKeyAttrDefineBean businessKeyAttrDefine) {
            this.businessKeyAttrDefine = businessKeyAttrDefine;
        }

        public String getDmDisplayName() {
            return dmDisplayName;
        }

        public void setDmDisplayName(String dmDisplayName) {
            this.dmDisplayName = dmDisplayName;
        }

        public String getDmModelName() {
            return dmModelName;
        }

        public void setDmModelName(String dmModelName) {
            this.dmModelName = dmModelName;
        }

        public String getIconName() {
            return iconName;
        }

        public void setIconName(String iconName) {
            this.iconName = iconName;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public String getSuperBmModelName() {
            return superBmModelName;
        }

        public void setSuperBmModelName(String superBmModelName) {
            this.superBmModelName = superBmModelName;
        }

        public String getWidth() {
            return width;
        }

        public void setWidth(String width) {
            this.width = width;
        }

        public List<AttrDefineListBean> getAttrDefineList() {
            return attrDefineList;
        }

        public void setAttrDefineList(List<AttrDefineListBean> attrDefineList) {
            this.attrDefineList = attrDefineList;
        }

        public static class BusinessKeyAttrDefineBean {
            private String configModifiedField;
            private String dataType;
            private String defaultValue;
            private String dictModelName;
            private String dmAttrDisplayName;
            private String dmAttrName;
            private boolean hasBusinessKey;
            private boolean hasCanUpdate;
            private boolean hasDateTime;
            private boolean hasModified;
            private boolean hasMult;
            private boolean hasMultiChoice;
            private boolean hasRealTimeUpdate;
            private boolean hasVisible;
            private String id;
            private String identityRule;
            private int inputLength;
            private String name;
            private String parentDeptJSON;
            private int precision;
            private boolean required;
            private String resModelValueJson;
            private String type;

            public String getConfigModifiedField() {
                return configModifiedField;
            }

            public void setConfigModifiedField(String configModifiedField) {
                this.configModifiedField = configModifiedField;
            }

            public String getDataType() {
                return dataType;
            }

            public void setDataType(String dataType) {
                this.dataType = dataType;
            }

            public String getDefaultValue() {
                return defaultValue;
            }

            public void setDefaultValue(String defaultValue) {
                this.defaultValue = defaultValue;
            }

            public String getDictModelName() {
                return dictModelName;
            }

            public void setDictModelName(String dictModelName) {
                this.dictModelName = dictModelName;
            }

            public String getDmAttrDisplayName() {
                return dmAttrDisplayName;
            }

            public void setDmAttrDisplayName(String dmAttrDisplayName) {
                this.dmAttrDisplayName = dmAttrDisplayName;
            }

            public String getDmAttrName() {
                return dmAttrName;
            }

            public void setDmAttrName(String dmAttrName) {
                this.dmAttrName = dmAttrName;
            }

            public boolean isHasBusinessKey() {
                return hasBusinessKey;
            }

            public void setHasBusinessKey(boolean hasBusinessKey) {
                this.hasBusinessKey = hasBusinessKey;
            }

            public boolean isHasCanUpdate() {
                return hasCanUpdate;
            }

            public void setHasCanUpdate(boolean hasCanUpdate) {
                this.hasCanUpdate = hasCanUpdate;
            }

            public boolean isHasDateTime() {
                return hasDateTime;
            }

            public void setHasDateTime(boolean hasDateTime) {
                this.hasDateTime = hasDateTime;
            }

            public boolean isHasModified() {
                return hasModified;
            }

            public void setHasModified(boolean hasModified) {
                this.hasModified = hasModified;
            }

            public boolean isHasMult() {
                return hasMult;
            }

            public void setHasMult(boolean hasMult) {
                this.hasMult = hasMult;
            }

            public boolean isHasMultiChoice() {
                return hasMultiChoice;
            }

            public void setHasMultiChoice(boolean hasMultiChoice) {
                this.hasMultiChoice = hasMultiChoice;
            }

            public boolean isHasRealTimeUpdate() {
                return hasRealTimeUpdate;
            }

            public void setHasRealTimeUpdate(boolean hasRealTimeUpdate) {
                this.hasRealTimeUpdate = hasRealTimeUpdate;
            }

            public boolean isHasVisible() {
                return hasVisible;
            }

            public void setHasVisible(boolean hasVisible) {
                this.hasVisible = hasVisible;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getIdentityRule() {
                return identityRule;
            }

            public void setIdentityRule(String identityRule) {
                this.identityRule = identityRule;
            }

            public int getInputLength() {
                return inputLength;
            }

            public void setInputLength(int inputLength) {
                this.inputLength = inputLength;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getParentDeptJSON() {
                return parentDeptJSON;
            }

            public void setParentDeptJSON(String parentDeptJSON) {
                this.parentDeptJSON = parentDeptJSON;
            }

            public int getPrecision() {
                return precision;
            }

            public void setPrecision(int precision) {
                this.precision = precision;
            }

            public boolean isRequired() {
                return required;
            }

            public void setRequired(boolean required) {
                this.required = required;
            }

            public String getResModelValueJson() {
                return resModelValueJson;
            }

            public void setResModelValueJson(String resModelValueJson) {
                this.resModelValueJson = resModelValueJson;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }

        public static class AttrDefineListBean implements Serializable{
            private String configModifiedField;
            private String dataType;
            private String defaultValue;
            private String dictModelName;
            private String dmAttrDisplayName;
            private String dmAttrName;
            private boolean hasBusinessKey;
            private boolean hasCanUpdate;
            private boolean hasDateTime;
            private boolean hasModified;
            private boolean hasMult;
            private boolean hasMultiChoice;
            private boolean hasRealTimeUpdate;
            private boolean hasVisible;
            private String id;
            private String identityRule;
            private int inputLength;
            private String name;
            private String parentDeptJSON;
            private int precision;
            private boolean required;
            private String resModelValueJson;
            private String type;

            public String getConfigModifiedField() {
                return configModifiedField;
            }

            public void setConfigModifiedField(String configModifiedField) {
                this.configModifiedField = configModifiedField;
            }

            public String getDataType() {
                return dataType;
            }

            public void setDataType(String dataType) {
                this.dataType = dataType;
            }

            public String getDefaultValue() {
                return defaultValue;
            }

            public void setDefaultValue(String defaultValue) {
                this.defaultValue = defaultValue;
            }

            public String getDictModelName() {
                return dictModelName;
            }

            public void setDictModelName(String dictModelName) {
                this.dictModelName = dictModelName;
            }

            public String getDmAttrDisplayName() {
                return dmAttrDisplayName;
            }

            public void setDmAttrDisplayName(String dmAttrDisplayName) {
                this.dmAttrDisplayName = dmAttrDisplayName;
            }

            public String getDmAttrName() {
                return dmAttrName;
            }

            public void setDmAttrName(String dmAttrName) {
                this.dmAttrName = dmAttrName;
            }

            public boolean isHasBusinessKey() {
                return hasBusinessKey;
            }

            public void setHasBusinessKey(boolean hasBusinessKey) {
                this.hasBusinessKey = hasBusinessKey;
            }

            public boolean isHasCanUpdate() {
                return hasCanUpdate;
            }

            public void setHasCanUpdate(boolean hasCanUpdate) {
                this.hasCanUpdate = hasCanUpdate;
            }

            public boolean isHasDateTime() {
                return hasDateTime;
            }

            public void setHasDateTime(boolean hasDateTime) {
                this.hasDateTime = hasDateTime;
            }

            public boolean isHasModified() {
                return hasModified;
            }

            public void setHasModified(boolean hasModified) {
                this.hasModified = hasModified;
            }

            public boolean isHasMult() {
                return hasMult;
            }

            public void setHasMult(boolean hasMult) {
                this.hasMult = hasMult;
            }

            public boolean isHasMultiChoice() {
                return hasMultiChoice;
            }

            public void setHasMultiChoice(boolean hasMultiChoice) {
                this.hasMultiChoice = hasMultiChoice;
            }

            public boolean isHasRealTimeUpdate() {
                return hasRealTimeUpdate;
            }

            public void setHasRealTimeUpdate(boolean hasRealTimeUpdate) {
                this.hasRealTimeUpdate = hasRealTimeUpdate;
            }

            public boolean isHasVisible() {
                return hasVisible;
            }

            public void setHasVisible(boolean hasVisible) {
                this.hasVisible = hasVisible;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getIdentityRule() {
                return identityRule;
            }

            public void setIdentityRule(String identityRule) {
                this.identityRule = identityRule;
            }

            public int getInputLength() {
                return inputLength;
            }

            public void setInputLength(int inputLength) {
                this.inputLength = inputLength;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getParentDeptJSON() {
                return parentDeptJSON;
            }

            public void setParentDeptJSON(String parentDeptJSON) {
                this.parentDeptJSON = parentDeptJSON;
            }

            public int getPrecision() {
                return precision;
            }

            public void setPrecision(int precision) {
                this.precision = precision;
            }

            public boolean isRequired() {
                return required;
            }

            public void setRequired(boolean required) {
                this.required = required;
            }

            public String getResModelValueJson() {
                return resModelValueJson;
            }

            public void setResModelValueJson(String resModelValueJson) {
                this.resModelValueJson = resModelValueJson;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
