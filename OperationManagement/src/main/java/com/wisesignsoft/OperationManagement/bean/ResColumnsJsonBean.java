package com.wisesignsoft.OperationManagement.bean;

import java.io.Serializable;

/**
 * Created by ycs on 2016/12/28.
 */

public class ResColumnsJsonBean implements Serializable{

    /**
     * headerText : 配置项编号
     * width : 200
     * ID : textinput_1
     * requiredObj : {"value":0,"name":"否"}
     * dataFieldObj : {"value":"CI_CODE","type":"TextInput","name":"CI_CODE"}
     */

    private String headerText;
    private int width;
    private String ID;
    /**
     * value : 0
     * name : 否
     */

    private RequiredObjBean requiredObj;
    /**
     * value : CI_CODE
     * type : TextInput
     * name : CI_CODE
     */

    private DataFieldObjBean dataFieldObj;

    public String getHeaderText() {
        return headerText;
    }

    public void setHeaderText(String headerText) {
        this.headerText = headerText;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public RequiredObjBean getRequiredObj() {
        return requiredObj;
    }

    public void setRequiredObj(RequiredObjBean requiredObj) {
        this.requiredObj = requiredObj;
    }

    public DataFieldObjBean getDataFieldObj() {
        return dataFieldObj;
    }

    public void setDataFieldObj(DataFieldObjBean dataFieldObj) {
        this.dataFieldObj = dataFieldObj;
    }

    public static class RequiredObjBean {
        private int value;
        private String name;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class DataFieldObjBean {
        private String value;
        private String type;
        private String name;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
