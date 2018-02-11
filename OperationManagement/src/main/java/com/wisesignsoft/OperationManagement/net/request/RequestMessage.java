package com.wisesignsoft.OperationManagement.net.request;

import android.util.Log;

import com.wisesignsoft.OperationManagement.Protocol;
import com.wisesignsoft.OperationManagement.net.RequestTask;
import com.wisesignsoft.OperationManagement.net.response.BaseResponse;
import com.wisesignsoft.OperationManagement.net.response.FindUnReadedMsgResponse;

import java.util.List;

/**
 * Created by ycs on 2016/12/8.
 */

public class RequestMessage {
    private static final String message_name_space = Protocol.message_name_space;

    /**
     *  查询未读消息列表
     * @param list
     * @param callback
     */
    public static void findUnReadedMsg(List<String> list, final RequestTask.ResultCallback<FindUnReadedMsgResponse> callback){
        String url = Protocol.getMessageHostUrl();
        String method = Protocol.findUnReadedMsg;
        RequestTask.getTask().runTask(message_name_space, method, url, list, new RequestTask.ResultCallback<FindUnReadedMsgResponse>() {
            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }

            @Override
            public void onResponse(FindUnReadedMsgResponse response) {
                callback.onResponse(response);
            }
        });
    }

    /**
     * 查询未读消息数量
     * @param list
     */
    public static void findUnReadedMsgCount(List<String> list, final RequestTask.ResultCallback<BaseResponse> callback){
        String url = Protocol.getMessageHostUrl();
        String method = Protocol.findUnReadedMsgCount;
        RequestTask.getTask().runTask(message_name_space, method, url, list, new RequestTask.ResultCallback<BaseResponse>() {
            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }

            @Override
            public void onResponse(BaseResponse response) {
                callback.onResponse(response);
            }
        });
    }

    /**
     * 标记消息为已读
     * @param list
     * @param callback
     */
    public static void setMsgReaded(List<String> list, final RequestTask.ResultCallback<BaseResponse> callback){
        String url = Protocol.getMessageHostUrl();
        String method = Protocol.setMsgReaded;
        RequestTask.getTask().runTask(message_name_space, method, url, list, new RequestTask.ResultCallback<BaseResponse>() {
            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }

            @Override
            public void onResponse(BaseResponse response) {
                callback.onResponse(response);
            }
        });
    }
}
