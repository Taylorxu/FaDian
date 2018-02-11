package com.wisesignsoft.OperationManagement.net.response;

import com.wisesignsoft.OperationManagement.bean.District;

import java.util.List;

/**
 * Created by ycs on 2016/12/2.
 */

public class QueryAllValidDictDateResponse extends BaseResponse {

    /**
     * dictDatas : [{"dictValue":"1","dictId":"INC_TYPEE:6aba22dd-c789-4d51-aec8-a2147afa761b","dictParentValue":null,"dictName":"故障与检修"},{"dictValue":"3","dictId":"INC_TYPEE:e15dc89c-0d27-4f2c-a8a3-fc959cbff25a","dictParentValue":null,"dictName":"常规变更"},{"dictValue":"2","dictId":"INC_TYPEE:1f2053eb-0ee0-4c7e-aa77-bd2c9734544a","dictParentValue":null,"dictName":"服务请求"}]
     * key : INC_TYPEE
     */

    private List<ReturnValueBean> returnValue;

    public List<ReturnValueBean> getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(List<ReturnValueBean> returnValue) {
        this.returnValue = returnValue;
    }

    public static class ReturnValueBean {
        private String key;
        /**
         * dictValue : 1
         * dictId : INC_TYPEE:6aba22dd-c789-4d51-aec8-a2147afa761b
         * dictParentValue : null
         * dictName : 故障与检修
         */

        private List<DictDatasBean> dictDatas;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public List<DictDatasBean> getDictDatas() {
            return dictDatas;
        }

        public void setDictDatas(List<DictDatasBean> dictDatas) {
            this.dictDatas = dictDatas;
        }

        public static class DictDatasBean extends District {
            private String dictValue;
            private String dictId;
            private Object dictParentValue;
            private String dictName;

            public String getDictValue() {
                return dictValue;
            }

            public void setDictValue(String dictValue) {
                this.dictValue = dictValue;
            }

            public String getDictId() {
                return dictId;
            }

            public void setDictId(String dictId) {
                this.dictId = dictId;
            }

            public Object getDictParentValue() {
                return dictParentValue;
            }

            public void setDictParentValue(Object dictParentValue) {
                this.dictParentValue = dictParentValue;
            }

            public String getDictName() {
                return dictName;
            }

            public void setDictName(String dictName) {
                this.dictName = dictName;
            }
        }
    }
}
