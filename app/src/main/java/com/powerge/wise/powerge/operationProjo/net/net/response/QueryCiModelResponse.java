package com.powerge.wise.powerge.operationProjo.net.net.response;

import java.util.List;

/**
 * Created by ycs on 2016/12/7.
 */

public class QueryCiModelResponse extends BaseResponse {

    /**
     * ciTypeCn : 自动化设备
     * ciType : AUTO
     * parentCiType : CI_FORM
     * fields : [{"field":"IMS_HOST_NAME","isRequired":"false","fieldType":"general","fieldCn":"主机名称","dictType":null,"dataLength":"255","precision":"2"}]
     */

    private List<ReturnValueBean> returnValue;

    public List<ReturnValueBean> getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(List<ReturnValueBean> returnValue) {
        this.returnValue = returnValue;
    }

    public static class ReturnValueBean {
        private String ciTypeCn;
        private String ciType;
        private String parentCiType;
        /**
         * field : IMS_HOST_NAME
         * isRequired : false
         * fieldType : general
         * fieldCn : 主机名称
         * dictType : null
         * dataLength : 255
         * precision : 2
         */

        private List<FieldsBean> fields;

        public String getCiTypeCn() {
            return ciTypeCn;
        }

        public void setCiTypeCn(String ciTypeCn) {
            this.ciTypeCn = ciTypeCn;
        }

        public String getCiType() {
            return ciType;
        }

        public void setCiType(String ciType) {
            this.ciType = ciType;
        }

        public String getParentCiType() {
            return parentCiType;
        }

        public void setParentCiType(String parentCiType) {
            this.parentCiType = parentCiType;
        }

        public List<FieldsBean> getFields() {
            return fields;
        }

        public void setFields(List<FieldsBean> fields) {
            this.fields = fields;
        }

        public static class FieldsBean {
            private String field;
            private String isRequired;
            private String fieldType;
            private String fieldCn;
            private Object dictType;
            private String dataLength;
            private String precision;

            public String getField() {
                return field;
            }

            public void setField(String field) {
                this.field = field;
            }

            public String getIsRequired() {
                return isRequired;
            }

            public void setIsRequired(String isRequired) {
                this.isRequired = isRequired;
            }

            public String getFieldType() {
                return fieldType;
            }

            public void setFieldType(String fieldType) {
                this.fieldType = fieldType;
            }

            public String getFieldCn() {
                return fieldCn;
            }

            public void setFieldCn(String fieldCn) {
                this.fieldCn = fieldCn;
            }

            public Object getDictType() {
                return dictType;
            }

            public void setDictType(Object dictType) {
                this.dictType = dictType;
            }

            public String getDataLength() {
                return dataLength;
            }

            public void setDataLength(String dataLength) {
                this.dataLength = dataLength;
            }

            public String getPrecision() {
                return precision;
            }

            public void setPrecision(String precision) {
                this.precision = precision;
            }
        }
    }
}
