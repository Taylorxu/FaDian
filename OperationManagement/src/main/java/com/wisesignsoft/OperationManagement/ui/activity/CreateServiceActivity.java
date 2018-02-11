package com.wisesignsoft.OperationManagement.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wisesignsoft.OperationManagement.BaseActivity;
import com.wisesignsoft.OperationManagement.R;
import com.wisesignsoft.OperationManagement.db.MySharedpreferences;
import com.wisesignsoft.OperationManagement.net.RequestTask;
import com.wisesignsoft.OperationManagement.net.request.RequestCi;
import com.wisesignsoft.OperationManagement.net.request.RequestProcess;
import com.wisesignsoft.OperationManagement.net.response.AddCiResponse;
import com.wisesignsoft.OperationManagement.net.response.BaseResponse;
import com.wisesignsoft.OperationManagement.net.response.CreateCiResponse;
import com.wisesignsoft.OperationManagement.utils.PullPaseXmlUtil;
import com.wisesignsoft.OperationManagement.view.mview.LoadingView;
import com.wisesignsoft.OperationManagement.view.mview.MyTitle;
import com.wisesignsoft.OperationManagement.view.mview.WorkOrderDataManager;
import com.wisesignsoft.OperationManagement.view.mview.WorkOrderDetailView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CreateServiceActivity extends BaseActivity implements View.OnClickListener {
    private MyTitle mt_create_service;
    private WorkOrderDetailView wodv_create_service;
    private String keyParam;

    public static void startSelf(Context context, String key) {
        Intent intent = new Intent(context, CreateServiceActivity.class);
        intent.putExtra("key", key);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_service);
        EventBus.getDefault().register(this);
        initView();
        request();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        WorkOrderDataManager.getManager().clearDate();
    }

    @Subscribe
    public void onEventMainThread(String temp) {
        if (wodv_create_service != null) {
            wodv_create_service.refresh();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (wodv_create_service != null) {
            wodv_create_service.refresh();
        }
    }

    /**
     * 初始化控件
     */
    private void initView() {
        keyParam = getIntent().getStringExtra("key");
        mt_create_service = (MyTitle) findViewById(R.id.mt_create_sercice);
        wodv_create_service = (WorkOrderDetailView) findViewById(R.id.wodv_create_service);
        Button bt_commit_service = (Button) findViewById(R.id.bt_commit_service);
        bt_commit_service.setOnClickListener(this);

        mt_create_service.setBack(true, this);
        mt_create_service.setTitle("添加服务报单");
    }

    /**
     * 请求数据
     */
    private void request() {
        final LoadingView loadingView = LoadingView.getLoadingView(this);
        loadingView.show();
        List<String> list = new ArrayList<>();
        list.add(keyParam);
        list.add(MySharedpreferences.getUser().getUsername());
        RequestCi.createCi(list, new RequestTask.ResultCallback<CreateCiResponse>() {
            @Override
            public void onError(Exception e) {
                loadingView.stop(loadingView);
            }

            @Override
            public void onResponse(CreateCiResponse response) {
                String form = response.getReturnValue().getFormDocument();
                List datas = PullPaseXmlUtil.pase(form);
                wodv_create_service.setData(datas, CreateServiceActivity.this);
                loadingView.stop(loadingView);
            }
        });
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.bt_commit_service) {
            WorkOrderDataManager.getManager().solvedMap(new WorkOrderDataManager.LoadListener3() {
                @Override
                public void setLoadListenenr3() {
                    if (!WorkOrderDataManager.getManager().isCommit(CreateServiceActivity.this)) {
                        return;
                    }
                    Map<String, String> map = WorkOrderDataManager.getManager().getReturnString();
                    Gson gson = new Gson();
                    String result = gson.toJson(map);
                    List<String> list = new ArrayList<>();
                    list.add(keyParam);
                    list.add(result);
                    RequestCi.addCi(list, new RequestTask.ResultCallback<AddCiResponse>() {
                        @Override
                        public void onError(Exception e) {

                        }

                        @Override
                        public void onResponse(AddCiResponse response) {
                            finish();
                        }
                    });
                }

                @Override
                public void setUnLoadListener() {
                    if (!WorkOrderDataManager.getManager().isCommit(CreateServiceActivity.this)) {
                        return;
                    }
                    Map<String, String> map = WorkOrderDataManager.getManager().getReturnString();
                    Gson gson = new Gson();
                    String result = gson.toJson(map);
                    List<String> list = new ArrayList<>();
                    list.add(keyParam);
                    list.add(result);
                    RequestCi.addCi(list, new RequestTask.ResultCallback<AddCiResponse>() {
                        @Override
                        public void onError(Exception e) {

                        }

                        @Override
                        public void onResponse(AddCiResponse response) {
                            finish();
                        }
                    });
                }
            });

        }
    }
}
