package com.powerge.wise.powerge.operationProjo.net.net.request;


import com.powerge.wise.powerge.operationProjo.net.Protocol;
import com.powerge.wise.powerge.operationProjo.net.net.RequestTask;
import com.powerge.wise.powerge.operationProjo.net.net.response.FindRoleByGroupIdResponse;
import com.powerge.wise.powerge.operationProjo.net.net.response.FindUserByRoleIdResponse;

import java.util.List;


/**
 * Created by ycs on 2016/12/12.
 */

public class RequestRole {
    private static String role_name_space = Protocol.role_name_space;
    /**
     * 查询单个字典有效数据
     *
     * @param list
     * @param callback
     */
    public static void findRoleByGroupId(List<String> list, final RequestTask.ResultCallback<FindRoleByGroupIdResponse> callback) {
        String url = Protocol.getRoleHostUrl();
        String method = Protocol.findRoleByGroupId;
        RequestTask.getTask().runTask(role_name_space, method, url, list, new RequestTask.ResultCallback<FindRoleByGroupIdResponse>() {
            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }

            @Override
            public void onResponse(FindRoleByGroupIdResponse response) {
                callback.onResponse(response);
            }
        });
    }
    /**
     * 根据角色ids查询角色
     *
     * @param list
     * @param callback
     */
    public static void findRoleByIds(List<String> list, final RequestTask.ResultCallback<FindRoleByGroupIdResponse> callback) {
        String url = Protocol.getRoleHostUrl();
        String method = Protocol.findRoleByIds;
        RequestTask.getTask().runTask(role_name_space, method, url, list, new RequestTask.ResultCallback<FindRoleByGroupIdResponse>() {
            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }

            @Override
            public void onResponse(FindRoleByGroupIdResponse response) {
                callback.onResponse(response);
            }
        });
    }
    /**
     * 查询角色下人员
     *
     * @param list
     * @param callback
     */
    public static void findUserByRoleId(List<String> list, final RequestTask.ResultCallback<FindUserByRoleIdResponse> callback) {
        String url = Protocol.getRoleHostUrl();
        String method = Protocol.findUserByRoleId;
        RequestTask.getTask().runTask(role_name_space, method, url, list, new RequestTask.ResultCallback<FindUserByRoleIdResponse>() {
            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }

            @Override
            public void onResponse(FindUserByRoleIdResponse response) {
                callback.onResponse(response);
            }
        });
    }
}
