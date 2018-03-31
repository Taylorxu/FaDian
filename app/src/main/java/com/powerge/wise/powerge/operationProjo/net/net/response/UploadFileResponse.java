package com.powerge.wise.powerge.operationProjo.net.net.response;

/**
 * Created by ycs on 2017/1/5.
 */

public class UploadFileResponse extends BaseResponse {
    /**
     * id : attachment:380a546c-f216-43d1-9f0e-629a97d5ceba
     * filePath : 2017/201701/
     * size : 123387
     * uploadUserName : 系统管理员
     * name : S70101-20523218-1.jpg
     * uploadTimeString : 2017-01-09 18:06:18
     * attachmentDiskName : 1483956378875.jpg
     */

    private ReturnValueBean returnValue;
    public ReturnValueBean getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(ReturnValueBean returnValue) {
        this.returnValue = returnValue;
    }

    public static class ReturnValueBean {
        private String id;
        private String filePath;
        private String size;
        private String uploadUserName;
        private String name;
        private String uploadTimeString;
        private String attachmentDiskName;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getUploadUserName() {
            return uploadUserName;
        }

        public void setUploadUserName(String uploadUserName) {
            this.uploadUserName = uploadUserName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUploadTimeString() {
            return uploadTimeString;
        }

        public void setUploadTimeString(String uploadTimeString) {
            this.uploadTimeString = uploadTimeString;
        }

        public String getAttachmentDiskName() {
            return attachmentDiskName;
        }

        public void setAttachmentDiskName(String attachmentDiskName) {
            this.attachmentDiskName = attachmentDiskName;
        }
    }
}
