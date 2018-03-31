package com.powerge.wise.powerge.operationProjo.net.net.request;


import com.powerge.wise.powerge.operationProjo.net.Protocol;
import com.powerge.wise.powerge.operationProjo.net.net.RequestTask;
import com.powerge.wise.powerge.operationProjo.net.net.response.LinkResponse;

import java.util.List;

/**
 * Created by ycs on 2016/12/7.
 */

public class RequestBusiness {
    private static String business_name_space = Protocol.business_name_space;

    /**
     * 调用联动
     *
     * @param list
     * @param callback
     */
    public static void invokeDataLinkageMethod(List<String> list, final RequestTask.ResultCallback<LinkResponse> callback) {
        String url = Protocol.getBusinessHostUrl();
        String method = Protocol.invokeDataLinkageMethod;
        RequestTask.getTask().runTask(business_name_space, method, url, list, new RequestTask.ResultCallback<LinkResponse>() {
            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }

            @Override
            public void onResponse(LinkResponse response) {
                callback.onResponse(response);
            }
        });
    }
}
