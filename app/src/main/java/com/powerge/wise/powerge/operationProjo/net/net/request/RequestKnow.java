package com.powerge.wise.powerge.operationProjo.net.net.request;


import com.powerge.wise.powerge.operationProjo.net.Protocol;
import com.powerge.wise.powerge.operationProjo.net.net.RequestTask;
import com.powerge.wise.powerge.operationProjo.net.net.response.FindAttachmentInfoResponse;
import com.powerge.wise.powerge.operationProjo.net.net.response.FindKnowledgeListResponse;
import com.powerge.wise.powerge.operationProjo.net.net.response.OpenKnowDetailResponse;

import java.util.List;

/**
 * Created by ycs on 2016/12/7.
 */

public class RequestKnow {
    private static String knownamespace = Protocol.know_name_space;

    /**
     *  查询知识库列表
     * @param list
     * @param callback
     */
    public static void findKnowledgeList(List<String> list, final RequestTask.ResultCallback<FindKnowledgeListResponse> callback) {
        String url = Protocol.getKnowHostUrl();
        String method = Protocol.findKnowledgeList;
        RequestTask.getTask().runTask(knownamespace, method, url, list, new RequestTask.ResultCallback<FindKnowledgeListResponse>() {
            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }

            @Override
            public void onResponse(FindKnowledgeListResponse response) {
                callback.onResponse(response);
            }
        });
    }
    /**
     *  查询知识详情
     * @param list
     * @param callback
     */
    public static void openKnowDetail(List<String> list, final RequestTask.ResultCallback<OpenKnowDetailResponse> callback) {
        String url = Protocol.getKnowHostUrl();
        String method = Protocol.openKnowDetail;
        RequestTask.getTask().runTask(knownamespace, method, url, list, new RequestTask.ResultCallback<OpenKnowDetailResponse>() {
            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }

            @Override
            public void onResponse(OpenKnowDetailResponse response) {
                callback.onResponse(response);
            }
        });
    }

    /**
     * 根据ids查询附件信息
     * @param list
     * @param callback
     */
    public static void findAttachmentInfo(List<String> list, final RequestTask.ResultCallback<FindAttachmentInfoResponse> callback){
        String url = Protocol.getKnowHostUrl();
        String method = Protocol.findAttachmentInfo;
        RequestTask.getTask().runTask(knownamespace, method, url, list, new RequestTask.ResultCallback<FindAttachmentInfoResponse>() {
            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }

            @Override
            public void onResponse(FindAttachmentInfoResponse response) {
                callback.onResponse(response);
            }
        });
    }
}
