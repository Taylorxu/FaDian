package com.wisesignsoft.OperationManagement.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wisesignsoft.OperationManagement.BaseActivity;
import com.wisesignsoft.OperationManagement.R;
import com.wisesignsoft.OperationManagement.db.MySharedpreferences;
import com.wisesignsoft.OperationManagement.net.RequestTask;
import com.wisesignsoft.OperationManagement.net.request.RequestCi;
import com.wisesignsoft.OperationManagement.net.response.QueryCiDetailResponse;
import com.wisesignsoft.OperationManagement.utils.PullPaseXmlUtil;
import com.wisesignsoft.OperationManagement.view.mview.LoadingView;
import com.wisesignsoft.OperationManagement.view.mview.MyTitle;
import com.wisesignsoft.OperationManagement.view.mview.WorkOrderDetailView;

import java.util.ArrayList;
import java.util.List;

public class ServiceDetailActivity extends BaseActivity {
    private MyTitle mt_service_detail;
    private WorkOrderDetailView wodv_service_detail;
    private String id;
    private String keyParam;

    public static void startSelf(Context context,String id,String key){
        Intent intent = new Intent(context,ServiceDetailActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("key",key);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_detail);
        init();
        request();
    }
    private void init(){
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        keyParam = intent.getStringExtra("key");
        mt_service_detail = (MyTitle) findViewById(R.id.mt_service_detail);
        wodv_service_detail = (WorkOrderDetailView) findViewById(R.id.wodv_service_detail);

        mt_service_detail.setBack(true,this);
        mt_service_detail.setTitle("服务报告详情");
    }
    private void request(){
        final LoadingView loadingView = LoadingView.getLoadingView(this);
        loadingView.show();
        List<String> list = new ArrayList<>();
        list.add(keyParam);
        list.add(id);
        list.add(MySharedpreferences.getUser().getUsername());
        RequestCi.queryCiDetail(list, new RequestTask.ResultCallback<QueryCiDetailResponse>() {
            @Override
            public void onError(Exception e) {
                loadingView.stop(loadingView);
            }

            @Override
            public void onResponse(QueryCiDetailResponse response) {
                String form = response.getReturnValue().getFormDocument();
                List datas = PullPaseXmlUtil.pase(form);
                wodv_service_detail.setData(datas,ServiceDetailActivity.this);
                loadingView.stop(loadingView);
            }
        });
    }
}
