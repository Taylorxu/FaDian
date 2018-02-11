package com.wisesignsoft.OperationManagement.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ycs on 2016/12/28.
 */

public class ResModelConfigure implements Serializable{
    /**
     * displayName : 配置项表单顶级模型
     * className : CI_FORM
     * attrDatasOfForm : [{"hasBusinessKey":false,"dmAttrName":"CI_CODE","hasModified":true,"orderType":0,"hasMultiChoice":false,"dmAttrDisplayName":"配置项编号","columnLinkType":null,"hasDateTime":false,"dictModelName":null,"mx_internal_uid":"2F67D646-705F-477A-A768-B3FC55F81D5A","id":"textinput_3","name":"配置项编号","selected":true,"required":false,"columnWidth":200,"type":"TextInput"},{"hasBusinessKey":false,"dmAttrName":"NAME","hasModified":true,"orderType":0,"hasMultiChoice":false,"dmAttrDisplayName":"配置项名称","columnLinkType":null,"hasDateTime":false,"dictModelName":null,"mx_internal_uid":"7A8C7A4C-A587-E543-AEC4-B3FC56019EE3","id":"textinput_4","name":"配置项名称","selected":true,"required":false,"columnWidth":200,"type":"TextInput"},{"hasBusinessKey":false,"dmAttrName":"REMARK","hasModified":true,"orderType":0,"hasMultiChoice":false,"dmAttrDisplayName":"备注","columnLinkType":null,"hasDateTime":false,"dictModelName":null,"mx_internal_uid":"8B2B7616-7381-2608-5B68-B3FC5608C5EC","id":"textarea_1","name":"备注","selected":true,"required":false,"columnWidth":200,"type":"TextArea"}]
     */

    private String displayName;
    private String className;
    /**
     * hasBusinessKey : false
     * dmAttrName : CI_CODE
     * hasModified : true
     * orderType : 0
     * hasMultiChoice : false
     * dmAttrDisplayName : 配置项编号
     * columnLinkType : null
     * hasDateTime : false
     * dictModelName : null
     * mx_internal_uid : 2F67D646-705F-477A-A768-B3FC55F81D5A
     * id : textinput_3
     * name : 配置项编号
     * selected : true
     * required : false
     * columnWidth : 200
     * type : TextInput
     */

    private List<AttrDatasOfFormBean> attrDatasOfForm;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<AttrDatasOfFormBean> getAttrDatasOfForm() {
        return attrDatasOfForm;
    }

    public void setAttrDatasOfForm(List<AttrDatasOfFormBean> attrDatasOfForm) {
        this.attrDatasOfForm = attrDatasOfForm;
    }

    public static class AttrDatasOfFormBean {
        private boolean hasBusinessKey;
        private String dmAttrName;
        private boolean hasModified;
        private int orderType;
        private boolean hasMultiChoice;
        private String dmAttrDisplayName;
        private Object columnLinkType;
        private boolean hasDateTime;
        private Object dictModelName;
        private String mx_internal_uid;
        private String id;
        private String name;
        private boolean selected;
        private boolean required;
        private int columnWidth;
        private String type;

        public boolean isHasBusinessKey() {
            return hasBusinessKey;
        }

        public void setHasBusinessKey(boolean hasBusinessKey) {
            this.hasBusinessKey = hasBusinessKey;
        }

        public String getDmAttrName() {
            return dmAttrName;
        }

        public void setDmAttrName(String dmAttrName) {
            this.dmAttrName = dmAttrName;
        }

        public boolean isHasModified() {
            return hasModified;
        }

        public void setHasModified(boolean hasModified) {
            this.hasModified = hasModified;
        }

        public int getOrderType() {
            return orderType;
        }

        public void setOrderType(int orderType) {
            this.orderType = orderType;
        }

        public boolean isHasMultiChoice() {
            return hasMultiChoice;
        }

        public void setHasMultiChoice(boolean hasMultiChoice) {
            this.hasMultiChoice = hasMultiChoice;
        }

        public String getDmAttrDisplayName() {
            return dmAttrDisplayName;
        }

        public void setDmAttrDisplayName(String dmAttrDisplayName) {
            this.dmAttrDisplayName = dmAttrDisplayName;
        }

        public Object getColumnLinkType() {
            return columnLinkType;
        }

        public void setColumnLinkType(Object columnLinkType) {
            this.columnLinkType = columnLinkType;
        }

        public boolean isHasDateTime() {
            return hasDateTime;
        }

        public void setHasDateTime(boolean hasDateTime) {
            this.hasDateTime = hasDateTime;
        }

        public Object getDictModelName() {
            return dictModelName;
        }

        public void setDictModelName(Object dictModelName) {
            this.dictModelName = dictModelName;
        }

        public String getMx_internal_uid() {
            return mx_internal_uid;
        }

        public void setMx_internal_uid(String mx_internal_uid) {
            this.mx_internal_uid = mx_internal_uid;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public boolean isRequired() {
            return required;
        }

        public void setRequired(boolean required) {
            this.required = required;
        }

        public int getColumnWidth() {
            return columnWidth;
        }

        public void setColumnWidth(int columnWidth) {
            this.columnWidth = columnWidth;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
