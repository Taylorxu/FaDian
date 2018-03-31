package com.powerge.wise.powerge.operationProjo.net.net.response;

import java.util.List;

/**
 * Created by ycs on 2017/1/13.
 */

public class LinkResponse extends BaseResponse {

    /**
     * returnValue : [{"controlType":"1","dmAttrName":"OBJ_SYSFVIP","hasEdit":false,"hasRequired":false,"hasVisible":false,"value":"khlb:5c4cec68-0ea3-4fce-9005-913245393749","valueRange":[]}]
     */

    private String returnValue;

    public String getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(String returnValue) {
        this.returnValue = returnValue;
    }

    public static class LinkResponse2{

        /**
         * controlType : 1
         * dmAttrName : OBJ_SYSFVIP
         * hasEdit : false
         * hasRequired : false
         * hasVisible : false
         * value : khlb:5c4cec68-0ea3-4fce-9005-913245393749
         * valueRange : []
         */

        private int controlType;
        private String dmAttrName;
        private boolean hasEdit;
        private boolean hasRequired;
        private boolean hasVisible;
        private String value;
        private List<?> valueRange;

        public int getControlType() {
            return controlType;
        }

        public void setControlType(int controlType) {
            this.controlType = controlType;
        }

        public String getDmAttrName() {
            return dmAttrName;
        }

        public void setDmAttrName(String dmAttrName) {
            this.dmAttrName = dmAttrName;
        }

        public boolean isHasEdit() {
            return hasEdit;
        }

        public void setHasEdit(boolean hasEdit) {
            this.hasEdit = hasEdit;
        }

        public boolean isHasRequired() {
            return hasRequired;
        }

        public void setHasRequired(boolean hasRequired) {
            this.hasRequired = hasRequired;
        }

        public boolean isHasVisible() {
            return hasVisible;
        }

        public void setHasVisible(boolean hasVisible) {
            this.hasVisible = hasVisible;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public List<?> getValueRange() {
            return valueRange;
        }

        public void setValueRange(List<?> valueRange) {
            this.valueRange = valueRange;
        }
    }
}
