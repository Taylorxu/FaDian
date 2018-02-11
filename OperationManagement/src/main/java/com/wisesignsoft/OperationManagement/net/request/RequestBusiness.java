package com.wisesignsoft.OperationManagement.net.request;

import com.wisesignsoft.OperationManagement.Protocol;
import com.wisesignsoft.OperationManagement.net.RequestTask;
import com.wisesignsoft.OperationManagement.net.response.LinkResponse;
import com.wisesignsoft.OperationManagement.net.response.QueryAttchmentByIdsResponse;
import com.wisesignsoft.OperationManagement.net.response.UploadFileResponse;

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
