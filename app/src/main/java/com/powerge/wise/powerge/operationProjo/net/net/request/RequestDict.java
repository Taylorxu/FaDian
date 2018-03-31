package com.powerge.wise.powerge.operationProjo.net.net.request;


import com.powerge.wise.powerge.operationProjo.net.Protocol;
import com.powerge.wise.powerge.operationProjo.net.net.RequestTask;
import com.powerge.wise.powerge.operationProjo.net.net.response.QueryAllValidDictDateResponse;
import com.powerge.wise.powerge.operationProjo.net.net.response.QueryValidCiByModelNameResponse;

import java.util.List;

/**
 * Created by ycs on 2016/12/1.
 */

public class RequestDict {
    private static String dictnamespace = Protocol.dict_name_space;

    /**
     * 查询单个字典有效数据
     *
     * @param list
     * @param callback
     */
    public static void queryValidCiByModelName(List<String> list, final RequestTask.ResultCallback<QueryValidCiByModelNameResponse> callback) {
        String url = Protocol.getDictHostUrl();
        String method = Protocol.queryValidCiByModelName;
        RequestTask.getTask().runTask(dictnamespace, method, url, list, new RequestTask.ResultCallback<QueryValidCiByModelNameResponse>() {
            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }

            @Override
            public void onResponse(QueryValidCiByModelNameResponse response) {
                callback.onResponse(response);
            }
        });
    }

    /**
     * 查询所有字典有效数据
     *
     * @param list
     * @param callback
     */
    public static void queryAllValidDictDate(List<String> list, final RequestTask.ResultCallback<QueryAllValidDictDateResponse> callback) {
        String url = Protocol.getDictHostUrl();
        String method = Protocol.queryAllValidDictDate;
        RequestTask.getTask().runTask(dictnamespace, method, url, list, new RequestTask.ResultCallback<QueryAllValidDictDateResponse>() {
            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }

            @Override
            public void onResponse(QueryAllValidDictDateResponse response) {
                callback.onResponse(response);
            }
        });
    }
}
