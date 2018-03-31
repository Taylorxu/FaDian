package com.powerge.wise.powerge.operationProjo.net.bean;

import java.util.List;

/**
 * Created by ycs on 2017/1/7.
 */

public class SpecificValueUpdate {

    /**
     * formItemValue : {"value":"PRO_STA: 44f0b7b9-5a14-414e-b354-44c73f2a1854","name":"新提交"}
     * formItem : {"ID":"combobox_10","precision":null,"value_label":"null","seeHisWorkOrderInfo":false,"isCanDelete":true,"configModifiedField":null,"bmModelName":"PRO_PROCESS","dataProvider":null,"dmAttrDisplayName":"流程状态","nodeType":"ComboBox","value":"","required":false,"parentDeptJSON":null,"allColumnsJson":null,"isCanAdd":true,"isVisible":false,"modelName":"PRO_PROCESS","modified":false,"isFilterUser":false,"id":"combobox_10","dmAttrName":"PROCESS_STATE","isCanUpdate":true,"srclib":"PRO_STA","isMult":false,"sectionId":"Section0","isExecute":false,"srclib_label":"问题状态","name":"问题状态","maxChars":null}
     */

    private List<ValuesBean> values;

    public List<ValuesBean> getValues() {
        return values;
    }

    public void setValues(List<ValuesBean> values) {
        this.values = values;
    }

    public static class ValuesBean {
        /**
         * value : PRO_STA: 44f0b7b9-5a14-414e-b354-44c73f2a1854
         * name : 新提交
         */

        private FormItemValueBean formItemValue;
        /**
         * ID : combobox_10
         * precision : null
         * value_label : null
         * seeHisWorkOrderInfo : false
         * isCanDelete : true
         * configModifiedField : null
         * bmModelName : PRO_PROCESS
         * dataProvider : null
         * dmAttrDisplayName : 流程状态
         * nodeType : ComboBox
         * value :
         * required : false
         * parentDeptJSON : null
         * allColumnsJson : null
         * isCanAdd : true
         * isVisible : false
         * modelName : PRO_PROCESS
         * modified : false
         * isFilterUser : false
         * id : combobox_10
         * dmAttrName : PROCESS_STATE
         * isCanUpdate : true
         * srclib : PRO_STA
         * isMult : false
         * sectionId : Section0
         * isExecute : false
         * srclib_label : 问题状态
         * name : 问题状态
         * maxChars : null
         */

        private FormItemBean formItem;

        public FormItemValueBean getFormItemValue() {
            return formItemValue;
        }

        public void setFormItemValue(FormItemValueBean formItemValue) {
            this.formItemValue = formItemValue;
        }

        public FormItemBean getFormItem() {
            return formItem;
        }

        public void setFormItem(FormItemBean formItem) {
            this.formItem = formItem;
        }

        public static class FormItemValueBean {
            private String value;
            private String name;

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class FormItemBean {
            private String ID;
            private Object precision;
            private String value_label;
            private boolean seeHisWorkOrderInfo;
            private boolean isCanDelete;
            private Object configModifiedField;
            private String bmModelName;
            private Object dataProvider;
            private String dmAttrDisplayName;
            private String nodeType;
            private String value;
            private boolean required;
            private Object parentDeptJSON;
            private Object allColumnsJson;
            private boolean isCanAdd;
            private boolean isVisible;
            private String modelName;
            private boolean modified;
            private boolean isFilterUser;
            private String id;
            private String dmAttrName;
            private boolean isCanUpdate;
            private String srclib;
            private boolean isMult;
            private String sectionId;
            private boolean isExecute;
            private String srclib_label;
            private String name;
            private Object maxChars;

            public String getID() {
                return ID;
            }

            public void setID(String ID) {
                this.ID = ID;
            }

            public Object getPrecision() {
                return precision;
            }

            public void setPrecision(Object precision) {
                this.precision = precision;
            }

            public String getValue_label() {
                return value_label;
            }

            public void setValue_label(String value_label) {
                this.value_label = value_label;
            }

            public boolean isSeeHisWorkOrderInfo() {
                return seeHisWorkOrderInfo;
            }

            public void setSeeHisWorkOrderInfo(boolean seeHisWorkOrderInfo) {
                this.seeHisWorkOrderInfo = seeHisWorkOrderInfo;
            }

            public boolean isIsCanDelete() {
                return isCanDelete;
            }

            public void setIsCanDelete(boolean isCanDelete) {
                this.isCanDelete = isCanDelete;
            }

            public Object getConfigModifiedField() {
                return configModifiedField;
            }

            public void setConfigModifiedField(Object configModifiedField) {
                this.configModifiedField = configModifiedField;
            }

            public String getBmModelName() {
                return bmModelName;
            }

            public void setBmModelName(String bmModelName) {
                this.bmModelName = bmModelName;
            }

            public Object getDataProvider() {
                return dataProvider;
            }

            public void setDataProvider(Object dataProvider) {
                this.dataProvider = dataProvider;
            }

            public String getDmAttrDisplayName() {
                return dmAttrDisplayName;
            }

            public void setDmAttrDisplayName(String dmAttrDisplayName) {
                this.dmAttrDisplayName = dmAttrDisplayName;
            }

            public String getNodeType() {
                return nodeType;
            }

            public void setNodeType(String nodeType) {
                this.nodeType = nodeType;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public boolean isRequired() {
                return required;
            }

            public void setRequired(boolean required) {
                this.required = required;
            }

            public Object getParentDeptJSON() {
                return parentDeptJSON;
            }

            public void setParentDeptJSON(Object parentDeptJSON) {
                this.parentDeptJSON = parentDeptJSON;
            }

            public Object getAllColumnsJson() {
                return allColumnsJson;
            }

            public void setAllColumnsJson(Object allColumnsJson) {
                this.allColumnsJson = allColumnsJson;
            }

            public boolean isIsCanAdd() {
                return isCanAdd;
            }

            public void setIsCanAdd(boolean isCanAdd) {
                this.isCanAdd = isCanAdd;
            }

            public boolean isIsVisible() {
                return isVisible;
            }

            public void setIsVisible(boolean isVisible) {
                this.isVisible = isVisible;
            }

            public String getModelName() {
                return modelName;
            }

            public void setModelName(String modelName) {
                this.modelName = modelName;
            }

            public boolean isModified() {
                return modified;
            }

            public void setModified(boolean modified) {
                this.modified = modified;
            }

            public boolean isIsFilterUser() {
                return isFilterUser;
            }

            public void setIsFilterUser(boolean isFilterUser) {
                this.isFilterUser = isFilterUser;
            }

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

            public boolean isIsCanUpdate() {
                return isCanUpdate;
            }

            public void setIsCanUpdate(boolean isCanUpdate) {
                this.isCanUpdate = isCanUpdate;
            }

            public String getSrclib() {
                return srclib;
            }

            public void setSrclib(String srclib) {
                this.srclib = srclib;
            }

            public boolean isIsMult() {
                return isMult;
            }

            public void setIsMult(boolean isMult) {
                this.isMult = isMult;
            }

            public String getSectionId() {
                return sectionId;
            }

            public void setSectionId(String sectionId) {
                this.sectionId = sectionId;
            }

            public boolean isIsExecute() {
                return isExecute;
            }

            public void setIsExecute(boolean isExecute) {
                this.isExecute = isExecute;
            }

            public String getSrclib_label() {
                return srclib_label;
            }

            public void setSrclib_label(String srclib_label) {
                this.srclib_label = srclib_label;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Object getMaxChars() {
                return maxChars;
            }

            public void setMaxChars(Object maxChars) {
                this.maxChars = maxChars;
            }
        }
    }
}
