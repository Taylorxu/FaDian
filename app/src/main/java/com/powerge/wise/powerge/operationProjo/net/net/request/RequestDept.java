package com.powerge.wise.powerge.operationProjo.net.net.request;


import com.powerge.wise.powerge.operationProjo.net.Protocol;
import com.powerge.wise.powerge.operationProjo.net.net.RequestTask;
import com.powerge.wise.powerge.operationProjo.net.net.response.FindAllDeptTreeResponse;
import com.powerge.wise.powerge.operationProjo.net.net.response.FindDeptByIdsResponse;
import com.powerge.wise.powerge.operationProjo.net.net.response.FindUserByDeptIdResponse;

import java.util.List;


/**
 * Created by ycs on 2016/12/15.
 */

public class RequestDept {
    private static String deptnamespace = Protocol.dept_name_space;
    /**
     * 查询所有组织结构以树形返回
     *
     * @param list
     * @param callback
     */
    public static void findAllDeptTree(List<String> list, final RequestTask.ResultCallback<FindAllDeptTreeResponse> callback) {
        String url = Protocol.getDeptHostUrl();
        String method = Protocol.findAllDeptTree;
        RequestTask.getTask().runTask(deptnamespace, method, url, list, new RequestTask.ResultCallback<FindAllDeptTreeResponse>() {
            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }

            @Override
            public void onResponse(FindAllDeptTreeResponse response) {
                callback.onResponse(response);
            }
        });
    }
    /**
     * 查询所有组织结构
     *
     * @param list
     * @param callback
     */
    public static void findAllDept(List<String> list, final RequestTask.ResultCallback<FindAllDeptTreeResponse> callback) {
        String url = Protocol.getDeptHostUrl();
        String method = Protocol.findAllDept;
        RequestTask.getTask().runTask(deptnamespace, method, url, list, new RequestTask.ResultCallback<FindAllDeptTreeResponse>() {
            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }

            @Override
            public void onResponse(FindAllDeptTreeResponse response) {
                callback.onResponse(response);
            }
        });
    }
    /**
     * 查询组织下人员
     *
     * @param list
     * @param callback
     */
    public static void findUserByDeptId(List<String> list, final RequestTask.ResultCallback<FindUserByDeptIdResponse> callback) {
        String url = Protocol.getDeptHostUrl();
        String method = Protocol.findUserByDeptId;
        RequestTask.getTask().runTask(deptnamespace, method, url, list, new RequestTask.ResultCallback<FindUserByDeptIdResponse>() {
            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }

            @Override
            public void onResponse(FindUserByDeptIdResponse response) {
                callback.onResponse(response);
            }
        });
    }
    /**
     * 根据ids查询部门信息
     *
     * @param list
     * @param callback
     */
    public static void findDeptByIds(List<String> list, final RequestTask.ResultCallback<FindDeptByIdsResponse> callback) {
        String url = Protocol.getDeptHostUrl();
        String method = Protocol.findDeptByIds;
        RequestTask.getTask().runTask(deptnamespace, method, url, list, new RequestTask.ResultCallback<FindDeptByIdsResponse>() {
            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }

            @Override
            public void onResponse(FindDeptByIdsResponse response) {
                callback.onResponse(response);
            }
        });
    }
}
