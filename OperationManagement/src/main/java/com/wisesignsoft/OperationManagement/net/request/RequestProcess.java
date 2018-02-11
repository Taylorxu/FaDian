package com.wisesignsoft.OperationManagement.net.request;

import com.wisesignsoft.OperationManagement.Protocol;
import com.wisesignsoft.OperationManagement.net.RequestTask;
import com.wisesignsoft.OperationManagement.net.response.BaseResponse;
import com.wisesignsoft.OperationManagement.net.response.CreateProcessByKeyAndCreatorResponse;
import com.wisesignsoft.OperationManagement.net.response.CreateProcessByTemplateResponse;
import com.wisesignsoft.OperationManagement.net.response.CreateTemplateResponse;
import com.wisesignsoft.OperationManagement.net.response.FindCanCreateProcessResponse;
import com.wisesignsoft.OperationManagement.net.response.FindWonhInfoResponse;
import com.wisesignsoft.OperationManagement.net.response.GetWoTempletListByUserAccountResponse;
import com.wisesignsoft.OperationManagement.net.response.OpenTaskDetailResponse;
import com.wisesignsoft.OperationManagement.net.response.OpenTepletResponse;
import com.wisesignsoft.OperationManagement.net.response.WorkOrderDetailResponse;

import java.util.List;

/**
 * Created by ycs on 2016/12/1.
 */

public class RequestProcess {
    private static String processnamespace = Protocol.process_name_space;

    /**
     * 新建工单
     *
     * @param list
     * @param callback
     */
    public static void createProcessByKeyAndCreator(List<String> list, final RequestTask.ResultCallback<CreateProcessByKeyAndCreatorResponse> callback) {
        String url = Protocol.getProcessHostUrl();
        String method = Protocol.createProcessByKeyAndCreator;
        RequestTask.getTask().runTask(processnamespace, method, url, list, new RequestTask.ResultCallback<CreateProcessByKeyAndCreatorResponse>() {
            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }

            @Override
            public void onResponse(CreateProcessByKeyAndCreatorResponse response) {
                callback.onResponse(response);
            }
        });
    }

    /**
     * 查询可创建的工单类型
     *
     * @param list
     * @param callback
     */
    public static void findCanCreateProcess(List<String> list, final RequestTask.ResultCallback<FindCanCreateProcessResponse> callback) {
        String url = Protocol.getProcessHostUrl();
        String method = Protocol.findCanCreateProcess;
        RequestTask.getTask().runTask(processnamespace, method, url, list, new RequestTask.ResultCallback<FindCanCreateProcessResponse>() {
            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }

            @Override
            public void onResponse(FindCanCreateProcessResponse response) {
                callback.onResponse(response);
            }
        });
    }

    /**
     * 查看工单处理过程
     *
     * @param list
     * @param callback
     */
    public static void findWonhInfo(List<String> list, final RequestTask.ResultCallback<FindWonhInfoResponse> callback) {
        String url = Protocol.getProcessHostUrl();
        String method = Protocol.findWonhInfo;
        RequestTask.getTask().runTask(processnamespace, method, url, list, new RequestTask.ResultCallback<FindWonhInfoResponse>() {
            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }

            @Override
            public void onResponse(FindWonhInfoResponse response) {
                callback.onResponse(response);
            }
        });
    }

    /**
     * 打开待办工单
     *
     * @param list
     * @param callback
     */
    public static void openTaskDetail(List<String> list, final RequestTask.ResultCallback<OpenTaskDetailResponse> callback) {
        String url = Protocol.getProcessHostUrl();
        String method = Protocol.openTaskDetail;
        RequestTask.getTask().runTask(processnamespace, method, url, list, new RequestTask.ResultCallback<OpenTaskDetailResponse>() {
            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }

            @Override
            public void onResponse(OpenTaskDetailResponse response) {
                callback.onResponse(response);
            }
        });
    }

    /**
     * 不保存工单
     *
     * @param list
     * @param callback
     */
    public static void deleteProcessInfoOnFirst(List<String> list, final RequestTask.ResultCallback<BaseResponse> callback) {
        String url = Protocol.getProcessHostUrl();
        String method = Protocol.deleteProcessInfoOnFirst;
        RequestTask.getTask().runTask(processnamespace, method, url, list, new RequestTask.ResultCallback<BaseResponse>() {
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
     * 保存工单到草稿
     *
     * @param list
     * @param callback
     */
    public static void saveProcessSketch(List<String> list, final RequestTask.ResultCallback<BaseResponse> callback) {
        String url = Protocol.getProcessHostUrl();
        String method = Protocol.saveProcessSketch;
        RequestTask.getTask().runTask(processnamespace, method, url, list, new RequestTask.ResultCallback<BaseResponse>() {
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
     * 创建模板
     *
     * @param list
     * @param callback
     */
    public static void crateTemplet(List<String> list, final RequestTask.ResultCallback<CreateTemplateResponse> callback) {
        String url = Protocol.getProcessHostUrl();
        String method = Protocol.crateTemplet;
        RequestTask.getTask().runTask(processnamespace, method, url, list, new RequestTask.ResultCallback<CreateTemplateResponse>() {
            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }

            @Override
            public void onResponse(CreateTemplateResponse response) {
                callback.onResponse(response);
            }
        });
    }

    /**
     * 获取我的模板
     * @param list
     * @param callback
     */
    public static void getWoTempletListByUserAccount(List<String> list, final RequestTask.ResultCallback<GetWoTempletListByUserAccountResponse> callback){
        String url = Protocol.getProcessHostUrl();
        String method = Protocol.getWoTempletListByUserAccount;
        RequestTask.getTask().runTask(processnamespace, method, url, list, new RequestTask.ResultCallback<GetWoTempletListByUserAccountResponse>() {
            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }

            @Override
            public void onResponse(GetWoTempletListByUserAccountResponse response) {
                callback.onResponse(response);
            }
        });
    }

    /**
     * 打开模板
     * @param list
     * @param callback
     */
    public static void openTeplet(List<String> list, final RequestTask.ResultCallback<OpenTepletResponse> callback){
        String url = Protocol.getProcessHostUrl();
        String method = Protocol.openTeplet;
        RequestTask.getTask().runTask(processnamespace, method, url, list, new RequestTask.ResultCallback<OpenTepletResponse>() {
            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }

            @Override
            public void onResponse(OpenTepletResponse response) {
                callback.onResponse(response);
            }
        });
    }
    /**
     * 查看工单详情
     * @param list
     * @param callback
     */
    public static void findWorkOrderDetailByPiKey(List<String> list, final RequestTask.ResultCallback<WorkOrderDetailResponse> callback){
        String url = Protocol.getProcessHostUrl();
        String method = Protocol.findWorkOrderDetailByPiKey;
        RequestTask.getTask().runTask(processnamespace, method, url, list, new RequestTask.ResultCallback<WorkOrderDetailResponse>() {
            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }

            @Override
            public void onResponse(WorkOrderDetailResponse response) {
                callback.onResponse(response);
            }
        });
    }
    /**
     * 根据模型创建工单
     * @param list
     * @param callback
     */
    public static void createProcessByTemplet(List<String> list, final RequestTask.ResultCallback<CreateProcessByTemplateResponse> callback){
        String url = Protocol.getProcessHostUrl();
        String method = Protocol.createProcessByTemplet;
        RequestTask.getTask().runTask(processnamespace, method, url, list, new RequestTask.ResultCallback<CreateProcessByTemplateResponse>() {
            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }

            @Override
            public void onResponse(CreateProcessByTemplateResponse response) {
                callback.onResponse(response);
            }
        });
    }
    /**
     * 提交工单
     * @param list
     * @param callback
     */
    public static void submitTask(List<String> list, final RequestTask.ResultCallback<BaseResponse> callback){
        String url = Protocol.getProcessHostUrl();
        String method = Protocol.submitTask;
        RequestTask.getTask().runTask(processnamespace, method, url, list, new RequestTask.ResultCallback<BaseResponse>() {
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
     * 新增模板
     * @param list
     * @param callback
     */
    public static void saveTeplet(List<String> list, final RequestTask.ResultCallback<BaseResponse> callback){
        String url = Protocol.getProcessHostUrl();
        String method = Protocol.saveTeplet;
        RequestTask.getTask().runTask(processnamespace, method, url, list, new RequestTask.ResultCallback<BaseResponse>() {
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
