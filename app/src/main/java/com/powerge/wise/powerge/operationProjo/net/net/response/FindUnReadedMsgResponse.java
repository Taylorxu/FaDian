package com.powerge.wise.powerge.operationProjo.net.net.response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ycs on 2016/12/8.
 */

public class FindUnReadedMsgResponse extends BaseResponse {
    private List<UnReadedMsg> result;

    public List<UnReadedMsg> getResult() {
        return result;
    }

    public void setResult(List<UnReadedMsg> result) {
        this.result = result;
    }

    public static class UnReadedMsg implements Serializable{

        /**
         * createtime : 2016-04-15 18:23:24
         * content : 有标题为【test1】,优先级为【低】的工单【#[I1604150013]】需要处理！
         * id : 消息ID
         * title : 有优先级为【低】的工单【#[I1604150013]】需要处理！
         * userName : 人员姓名
         */

        private String createtime;
        private String content;
        private String id;
        private String title;
        private String userName;

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
}
