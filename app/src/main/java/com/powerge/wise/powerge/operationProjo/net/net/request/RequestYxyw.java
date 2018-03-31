package com.powerge.wise.powerge.operationProjo.net.net.request;

import android.util.Log;

import com.powerge.wise.powerge.operationProjo.net.Protocol;
import com.powerge.wise.powerge.operationProjo.net.net.RequestTask;
import com.powerge.wise.powerge.operationProjo.net.net.response.FindUnHandleProcessResponse;
import com.powerge.wise.powerge.operationProjo.net.net.response.LoginResponse;
import com.powerge.wise.powerge.operationProjo.net.net.response.QueryAllValidUsersResponse;
import com.powerge.wise.powerge.operationProjo.net.net.response.QueryDataResponse;
import com.powerge.wise.powerge.operationProjo.net.net.response.QueryUnhandleProcessCountResponse;
import com.powerge.wise.powerge.operationProjo.net.net.response.QueryUserPositionIntervalResponse;
import com.powerge.wise.powerge.operationProjo.net.net.response.QueryUserResourceResponse;
import com.powerge.wise.powerge.operationProjo.net.net.response.QueryValidUserBydeptResponse;
import com.powerge.wise.powerge.operationProjo.net.net.response.QueryValidUsersByAccountResponse;
import com.powerge.wise.powerge.operationProjo.net.net.response.QueryValidUsersByIdsResponse;

import java.util.List;

/**
 * Created by ycs on 2016/12/1.
 */

public class RequestYxyw {
    private static String yxywnamespace = Protocol.yxyw_name_space;

    /**
     * 登录
     *
     * @param list     参数集合
     * @param callback 回调
     */
    public static void login(List<String> list, final RequestTask.ResultCallback<LoginResponse> callback) {
        String url = Protocol.getYxywHostUrl();
        Log.i("YCS", "login: url:"+url);
        String method = Protocol.login;
        RequestTask.getTask().runTask(yxywnamespace, method, url, list, new RequestTask.ResultCallback<LoginResponse>() {
            @Override
            public void onError(Exception e) {
                String message = e.getMessage();
                Log.i("YCS", "onError: message:" + message);
                callback.onError(e);
            }

            @Override
            public void onResponse(LoginResponse response) {
                callback.onResponse(response);
            }
        });
    }

    /**
     * 获取上送位置的间隔
     *
     * @param list
     * @param callback
     */
    public static void queryUserPositionInterval(List<String> list, final RequestTask.ResultCallback<QueryUserPositionIntervalResponse> callback) {
        String url = Protocol.getYxywHostUrl();
        String method = Protocol.queryUserPositionInterval;
        RequestTask.getTask().runTask(yxywnamespace, method, url, list, new RequestTask.ResultCallback<QueryUserPositionIntervalResponse>() {
            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }

            @Override
            public void onResponse(QueryUserPositionIntervalResponse response) {
                callback.onResponse(response);
            }
        });
    }

    /**
     * 获取未处理的任务个数
     *
     * @param list
     * @param callback
     */
    public static void queryUnhandleProcessCount(List<String> list, final RequestTask.ResultCallback<QueryUnhandleProcessCountResponse> callback) {
        String url = Protocol.getYxywHostUrl();
        String method = Protocol.QueryUnhandleProcessCount;
        RequestTask.getTask().runTask(yxywnamespace, method, url, list, new RequestTask.ResultCallback<QueryUnhandleProcessCountResponse>() {
            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }

            @Override
            public void onResponse(QueryUnhandleProcessCountResponse response) {
                callback.onResponse(response);
            }
        });
    }

    /**
     * 查询用户的权限
     *
     * @param list
     * @param callback
     */
    public static void queryUserResource(List<String> list, final RequestTask.ResultCallback<QueryUserResourceResponse> callback) {
        String url = Protocol.getYxywHostUrl();
        String method = Protocol.queryUserResource;
        RequestTask.getTask().runTask(yxywnamespace, method, url, list, new RequestTask.ResultCallback<QueryUserResourceResponse>() {
            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }

            @Override
            public void onResponse(QueryUserResourceResponse response) {
                callback.onResponse(response);
            }
        });
    }

    /**
     * 获取待处理
     *
     * @param list
     * @param callback
     */
    public static void findUnhandleProcess(List<String> list, final RequestTask.ResultCallback<FindUnHandleProcessResponse> callback) {
        String url = Protocol.getYxywHostUrl();
        String method = Protocol.findUnhandleProcess;
        RequestTask.getTask().runTask(yxywnamespace, method, url, list, new RequestTask.ResultCallback<FindUnHandleProcessResponse>() {
            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }

            @Override
            public void onResponse(FindUnHandleProcessResponse response) {
                callback.onResponse(response);
            }
        });
    }

    /**
     * 获取已处理事件
     *
     * @param list
     * @param callback
     */
    public static void findHandledProcess(List<String> list, final RequestTask.ResultCallback<FindUnHandleProcessResponse> callback) {
        String url = Protocol.getYxywHostUrl();
        String method = Protocol.findHandledProcess;
        RequestTask.getTask().runTask(yxywnamespace, method, url, list, new RequestTask.ResultCallback<FindUnHandleProcessResponse>() {
            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }

            @Override
            public void onResponse(FindUnHandleProcessResponse response) {
                callback.onResponse(response);
            }
        });
    }

    /**
     * 获取已完成事件
     *
     * @param list
     * @param callback
     */
    public static void findEndedProcess(List<String> list, final RequestTask.ResultCallback<FindUnHandleProcessResponse> callback) {
        String url = Protocol.getYxywHostUrl();
        String method = Protocol.findEndedProcess;
        RequestTask.getTask().runTask(yxywnamespace, method, url, list, new RequestTask.ResultCallback<FindUnHandleProcessResponse>() {
            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }

            @Override
            public void onResponse(FindUnHandleProcessResponse response) {
                callback.onResponse(response);
            }
        });
    }

    /**
     * 查询所有有效人员
     *
     * @param list
     * @param callback
     */
    public static void queryAllValidUsers(List<String> list, final RequestTask.ResultCallback<QueryAllValidUsersResponse> callback) {
        String url = Protocol.getYxywHostUrl();
        String method = Protocol.queryAllValidUsers;
        RequestTask.getTask().runTask(yxywnamespace, method, url, list, new RequestTask.ResultCallback<QueryAllValidUsersResponse>() {
            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }

            @Override
            public void onResponse(QueryAllValidUsersResponse response) {
                callback.onResponse(response);
            }
        });
    }

    /**
     * 台账数据查询
     *
     * @param list
     * @param callback
     */
    public static void queryData(List<String> list, final RequestTask.ResultCallback<QueryDataResponse> callback) {
        String url = Protocol.getYxywHostUrl();
        String method = Protocol.queryData;
        RequestTask.getTask().runTask(yxywnamespace, method, url, list, new RequestTask.ResultCallback<QueryDataResponse>() {
            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }

            @Override
            public void onResponse(QueryDataResponse response) {
                callback.onResponse(response);
            }
        });
    }

    /**
     * 条件查询人员列表
     *
     * @param list
     * @param callback
     */
    public static void queryValidUsersByUserName(List<String> list, final RequestTask.ResultCallback<QueryAllValidUsersResponse> callback) {
        String url = Protocol.getYxywHostUrl();
        String method = Protocol.queryValidUsersByUserName;
        RequestTask.getTask().runTask(yxywnamespace, method, url, list, new RequestTask.ResultCallback<QueryAllValidUsersResponse>() {
            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }

            @Override
            public void onResponse(QueryAllValidUsersResponse response) {
                callback.onResponse(response);
            }
        });
    }

    /**
     * 根据人员账号获取人员详情
     *
     * @param list
     * @param callback
     */
    public static void queryValidUsersByAccount(List<String> list, final RequestTask.ResultCallback<QueryValidUsersByAccountResponse> callback) {
        String url = Protocol.getYxywHostUrl();
        String method = Protocol.queryValidUsersByAccount;
        RequestTask.getTask().runTask(yxywnamespace, method, url, list, new RequestTask.ResultCallback<QueryValidUsersByAccountResponse>() {
            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }

            @Override
            public void onResponse(QueryValidUsersByAccountResponse response) {
                callback.onResponse(response);
            }
        });
    }

    /**
     * 根据人员id获取人员详情
     *
     * @param list
     * @param callback
     */
    public static void queryValidUsersByIds(List<String> list, final RequestTask.ResultCallback<QueryValidUsersByIdsResponse> callback) {
        String url = Protocol.getYxywHostUrl();
        String method = Protocol.queryValidUsersByIds;
        RequestTask.getTask().runTask(yxywnamespace, method, url, list, new RequestTask.ResultCallback<QueryValidUsersByIdsResponse>() {
            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }

            @Override
            public void onResponse(QueryValidUsersByIdsResponse response) {
                callback.onResponse(response);
            }
        });
    }

    /**
     * 根据操作人id以条件排序查询其所在部门的所有人员
     *
     * @param list
     * @param callback
     */
    public static void queryValidUserBydept(List<String> list, final RequestTask.ResultCallback<QueryValidUserBydeptResponse> callback) {
        String url = Protocol.getYxywHostUrl();
        String method = Protocol.queryValidUserBydept;
        RequestTask.getTask().runTask(yxywnamespace, method, url, list, new RequestTask.ResultCallback<QueryValidUserBydeptResponse>() {
            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }

            @Override
            public void onResponse(QueryValidUserBydeptResponse response) {
                callback.onResponse(response);
            }
        });
    }
}
