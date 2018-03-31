package com.powerge.wise.powerge.operationProjo.net.net.request;

import com.powerge.wise.powerge.operationProjo.net.Protocol;
import com.powerge.wise.powerge.operationProjo.net.net.RequestTask;
import com.powerge.wise.powerge.operationProjo.net.net.response.PreviewOnlineResponse;
import com.powerge.wise.powerge.operationProjo.net.net.response.QueryAttchmentByIdsResponse;
import com.powerge.wise.powerge.operationProjo.net.net.response.UploadFileResponse;

import java.util.List;

/**
 * Created by ycs on 2016/12/7.
 */

public class RequestAttachment {
    private static String attachment_name_space = Protocol.attachment_name_space;

    /**
     * 上传附件
     * @param list
     * @param callback
     */
    public static void uploadFile(List<String> list, final RequestTask.ResultCallback<UploadFileResponse> callback){
        String url = Protocol.getAttachmentHostUrl();
        String method = Protocol.uploadFile;
        RequestTask.getTask().runTask(attachment_name_space, method, url, list, new RequestTask.ResultCallback<UploadFileResponse>() {
            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }

            @Override
            public void onResponse(UploadFileResponse response) {
                callback.onResponse(response);
            }
        });
    }
    /**
     * 根据ids查询附件信息
     * @param list
     * @param callback
     */
    public static void queryAttchmentByIds(List<String> list, final RequestTask.ResultCallback<QueryAttchmentByIdsResponse> callback){
        String url = Protocol.getAttachmentHostUrl();
        String method = Protocol.queryAttchmentByIds;
        RequestTask.getTask().runTask(attachment_name_space, method, url, list, new RequestTask.ResultCallback<QueryAttchmentByIdsResponse>() {
            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }

            @Override
            public void onResponse(QueryAttchmentByIdsResponse response) {
                callback.onResponse(response);
            }
        });
    }

    /**
     * 根据id查询附件预览地址
     * @param list
     * @param callback
     */
    public static void previewOnline(List<String> list, final RequestTask.ResultCallback<PreviewOnlineResponse> callback){
        String url = Protocol.getAttachmentHostUrl();
        String method = Protocol.previewOnline;
        RequestTask.getTask().runTask(attachment_name_space, method, url, list, new RequestTask.ResultCallback<PreviewOnlineResponse>() {
            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }

            @Override
            public void onResponse(PreviewOnlineResponse response) {
                callback.onResponse(response);
            }
        });
    }
}
