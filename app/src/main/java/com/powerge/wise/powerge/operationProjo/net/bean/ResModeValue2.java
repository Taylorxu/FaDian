package com.powerge.wise.powerge.operationProjo.net.bean;

import java.util.List;

/**
 * Created by ycs on 2017/1/4.
 */

public class ResModeValue2 {
    /**
     * bmDisplayName : 主机设备
     * dmAttrName : OBJ_MXLBZJ
     * dmDisplayName : 主机设备
     * toBmModelName : HOST_DEVICE
     * bmModelName : protest
     * configValueJson : [{"required":false,"columnWidth":200,"dmAttrName":"CI_CODE","id":"textinput_1","name":"配置项编号","type":"TextInput"},{"required":false,"columnWidth":200,"dmAttrName":"NAME","id":"textinput_2","name":"配置项名称","type":"TextInput"},{"required":false,"columnWidth":200,"dmAttrName":"CPU_NUMBER","id":"textinput_3","name":"CPU数量","type":"TextInput"},{"required":false,"columnWidth":200,"dmAttrName":"OPERATING_SYSTEM","id":"textinput_4","name":"操作系统","type":"TextInput"},{"required":false,"columnWidth":200,"dmAttrName":"MANAGE_IP","id":"textinput_5","name":"管理IP","type":"TextInput"},{"required":false,"columnWidth":200,"dmAttrName":"MEMORY_CAPACITY","id":"textinput_6","name":"内存容量","type":"TextInput"},{"required":false,"columnWidth":200,"dmAttrName":"RELATED_APP","id":"resmodelselect_1","name":"应用","type":"ResModelSelect"},{"required":false,"columnWidth":200,"dmAttrName":"RELATED_APP_LIST","id":"resmodelselect_1_ResDynamicDataGrid","name":"应用","type":"ResDynamicDataGrid"}]
     */

    private String bmDisplayName;
    private String dmAttrName;
    private String dmDisplayName;
    private String toBmModelName;
    private String bmModelName;
    /**
     * required : false
     * columnWidth : 200
     * dmAttrName : CI_CODE
     * id : textinput_1
     * name : 配置项编号
     * type : TextInput
     */

    private List<ConfigValueJsonBean> configValueJson;

    public String getBmDisplayName() {
        return bmDisplayName;
    }

    public void setBmDisplayName(String bmDisplayName) {
        this.bmDisplayName = bmDisplayName;
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

    public String getBmModelName() {
        return bmModelName;
    }

    public void setBmModelName(String bmModelName) {
        this.bmModelName = bmModelName;
    }

    public List<ConfigValueJsonBean> getConfigValueJson() {
        return configValueJson;
    }

    public void setConfigValueJson(List<ConfigValueJsonBean> configValueJson) {
        this.configValueJson = configValueJson;
    }

    public static class ConfigValueJsonBean {
        private boolean required;
        private int columnWidth;
        private String dmAttrName;
        private String id;
        private String name;
        private String type;

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

        public String getDmAttrName() {
            return dmAttrName;
        }

        public void setDmAttrName(String dmAttrName) {
            this.dmAttrName = dmAttrName;
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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
