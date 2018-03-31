package com.powerge.wise.powerge.operationProjo.net.net.request;


import com.powerge.wise.powerge.operationProjo.net.Protocol;
import com.powerge.wise.powerge.operationProjo.net.net.RequestTask;
import com.powerge.wise.powerge.operationProjo.net.net.response.AddCiResponse;
import com.powerge.wise.powerge.operationProjo.net.net.response.CreateCiResponse;
import com.powerge.wise.powerge.operationProjo.net.net.response.QueryCiDetailResponse;
import com.powerge.wise.powerge.operationProjo.net.net.response.QueryCiModelResponse;
import com.powerge.wise.powerge.operationProjo.net.net.response.QueryModelByBmModelNameResponse;

import java.util.List;

/**
 * Created by ycs on 2016/12/7.
 */

public class RequestCi {
    private static String ci_name_space = Protocol.ci_name_space;

    /**
     * 查询配合项模型
     * @param list
     * @param callback
     */
    public static void queryCiModel(List<String> list, final RequestTask.ResultCallback<QueryCiModelResponse> callback){
        String url = Protocol.getCiHostUrl();
        String method = Protocol.queryCiModel;
        RequestTask.getTask().runTask(ci_name_space, method, url, list, new RequestTask.ResultCallback<QueryCiModelResponse>() {
            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }

            @Override
            public void onResponse(QueryCiModelResponse response) {
                callback.onResponse(response);
            }
        });
    }
    /**
     * 查询业务模型
     * @param list
     * @param callback
     */
    public static void queryModelByBmModelName(List<String> list, final RequestTask.ResultCallback<QueryModelByBmModelNameResponse> callback){
        String url = Protocol.getCiHostUrl();
        String method = Protocol.queryModelByBmModelName;
        RequestTask.getTask().runTask(ci_name_space, method, url, list, new RequestTask.ResultCallback<QueryModelByBmModelNameResponse>() {
            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }

            @Override
            public void onResponse(QueryModelByBmModelNameResponse response) {
                callback.onResponse(response);
            }
        });
    }
    /**
     * 查询业务模型
     * @param list
     * @param callback
     */
    public static void queryCiDetail(List<String> list, final RequestTask.ResultCallback<QueryCiDetailResponse> callback){
        String url = Protocol.getCiHostUrl();
        String method = Protocol.queryCiDetail;
        RequestTask.getTask().runTask(ci_name_space, method, url, list, new RequestTask.ResultCallback<QueryCiDetailResponse>() {
            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }

            @Override
            public void onResponse(QueryCiDetailResponse response) {
                callback.onResponse(response);
            }
        });
    }
    /**
     * 创建一个台账
     * @param list
     * @param callback
     */
    public static void createCi(List<String> list, final RequestTask.ResultCallback<CreateCiResponse> callback){
        String url = Protocol.getCiHostUrl();
        String method = Protocol.createCi;
        RequestTask.getTask().runTask(ci_name_space, method, url, list, new RequestTask.ResultCallback<CreateCiResponse>() {
            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }

            @Override
            public void onResponse(CreateCiResponse response) {
                callback.onResponse(response);
            }
        });
    }
    /**
     * 创建一个台账
     * @param list
     * @param callback
     */
    public static void addCi(List<String> list, final RequestTask.ResultCallback<AddCiResponse> callback){
        String url = Protocol.getCiHostUrl();
        String method = Protocol.addCi;
        RequestTask.getTask().runTask(ci_name_space, method, url, list, new RequestTask.ResultCallback<AddCiResponse>() {
            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }

            @Override
            public void onResponse(AddCiResponse response) {
                callback.onResponse(response);
            }
        });
    }
}
