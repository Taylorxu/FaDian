package com.powerge.wise.powerge.operationProjo.net.net.request;


import com.powerge.wise.powerge.operationProjo.net.Protocol;
import com.powerge.wise.powerge.operationProjo.net.net.RequestTask;
import com.powerge.wise.powerge.operationProjo.net.net.response.BaseResponse;
import com.powerge.wise.powerge.operationProjo.net.net.response.UpdateUserPos;

import java.util.List;

/**
 * Created by ycs on 2016/12/1.
 */

public class RequestUser {
    private static String usernamespace = Protocol.user_name_space;

    /**
     * 上送自己当前位置
     *
     * @param list
     * @param callback
     */
    public static void updateUserPos(List<String> list, final RequestTask.ResultCallback<UpdateUserPos> callback) {
        String url = Protocol.getUserHostUrl();
        String method = Protocol.updateUserPos;
        RequestTask.getTask().runTask(usernamespace, method, url, list, new RequestTask.ResultCallback<UpdateUserPos>() {
            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }

            @Override
            public void onResponse(UpdateUserPos response) {
                callback.onResponse(response);
            }
        });
    }

    /**
     * 修改人员的忙闲状态
     * @param list
     * @param callback
     */
    public static void updateUserSta(List<String> list, final RequestTask.ResultCallback<BaseResponse> callback){
        String url = Protocol.getUserHostUrl();
        String method = Protocol.updateUserSta;
        RequestTask.getTask().runTask(usernamespace, method, url, list, new RequestTask.ResultCallback<BaseResponse>() {
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
