package com.powerge.wise.powerge.operationProjo.net.net.response;

import java.util.List;


/**
 * Created by sun on 2017/12/30.
 */

public class PreviewOnlineResponse {
    /**
     * preViewUrl : 附件预览地址
     * promptMsg : 附件转换异常信息
     */

    private String preViewUrl;
    private String promptMsg;

    public String getPreViewUrl() {
        return preViewUrl;
    }

    public void setPreViewUrl(String preViewUrl) {
        this.preViewUrl = preViewUrl;
    }

    public String getPromptMsg() {
        return promptMsg;
    }

    public void setPromptMsg(String promptMsg) {
        this.promptMsg = promptMsg;
    }
}
