package com.wisesignsoft.OperationManagement.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.wisesignsoft.OperationManagement.BaseActivity;
import com.wisesignsoft.OperationManagement.R;
import com.wisesignsoft.OperationManagement.net.RequestTask;
import com.wisesignsoft.OperationManagement.net.request.RequestProcess;
import com.wisesignsoft.OperationManagement.net.response.WorkOrderDetailResponse;
import com.wisesignsoft.OperationManagement.view.mview.KeyValueView;
import com.wisesignsoft.OperationManagement.view.mview.LoadingView;
import com.wisesignsoft.OperationManagement.view.mview.MyTitle;
import com.wisesignsoft.OperationManagement.view.mview.WorkOrderDetailView;

import java.util.ArrayList;
import java.util.List;

public class WorkOrderDetailActivity extends BaseActivity {

    private MyTitle mt_wo_detail;
    private KeyValueView kvv_wo_detail;
    private WorkOrderDetailView wodv_wo_detail;
    private String key;

    public static void startSelf(Context context, String key) {
        Intent intent = new Intent(context, WorkOrderDetailActivity.class);
        intent.putExtra("key", key);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_order_detail);
        initView();
        request();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        key = getIntent().getStringExtra("key");
        mt_wo_detail = (MyTitle) findViewById(R.id.mt_work_order_detail);
        kvv_wo_detail = (KeyValueView) findViewById(R.id.kvv_work_order_detail);
        wodv_wo_detail = (WorkOrderDetailView) findViewById(R.id.wodv_work_order_detail);

        mt_wo_detail.setBack(true,this);
        mt_wo_detail.setTitle("工单详情");
        mt_wo_detail.setTvRight(true, "操作记录", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WonhInfoActivity.startSelf(WorkOrderDetailActivity.this,key);
            }
        });
        kvv_wo_detail.setValueText(key);
    }

    /**
     * 请求数据
     */
    private void request() {
        final LoadingView loadingView = LoadingView.getLoadingView(this);
        loadingView.show();
        List<String> list = new ArrayList<>();
        list.add(key);
        RequestProcess.findWorkOrderDetailByPiKey(list, new RequestTask.ResultCallback<WorkOrderDetailResponse>() {
            @Override
            public void onError(Exception e) {
                loadingView.stop(loadingView);
            }

            @Override
            public void onResponse(WorkOrderDetailResponse response) {
                List datas = response.getResult();
                wodv_wo_detail.setData(datas, WorkOrderDetailActivity.this);
                loadingView.stop(loadingView);
            }
        });
    }
}
