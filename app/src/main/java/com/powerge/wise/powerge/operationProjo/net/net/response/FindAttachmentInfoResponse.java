package com.powerge.wise.powerge.operationProjo.net.net.response;

import com.powerge.wise.powerge.operationProjo.net.utils.StringUtils;

import java.util.List;

/**
 * Created by sun on 2017/12/30.
 */

public class FindAttachmentInfoResponse {
    private List<ResultBean> result;

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean{
        /**
         * id : 附件ID
         * name : 附件名称
         */

        private String id;
        private String name;
        private String size;
        private String preview;

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

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getExt(){
            if(StringUtils.isNotBlank(this.name)){
                String[] ext = name.split("\\.");
                return ext[ext.length - 1];
            }
            return "";
        }

        public String getPreview() {
            return preview;
        }

        public void setPreview(String preview) {
            this.preview = preview;
        }
    }
}
