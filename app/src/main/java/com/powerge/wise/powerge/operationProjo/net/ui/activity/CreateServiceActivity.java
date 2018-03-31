package com.powerge.wise.powerge.operationProjo.net.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.operationProjo.net.BaseActivity;
import com.powerge.wise.powerge.operationProjo.net.db.MySharedpreferences;
import com.powerge.wise.powerge.operationProjo.net.net.RequestTask;
import com.powerge.wise.powerge.operationProjo.net.net.request.RequestCi;
import com.powerge.wise.powerge.operationProjo.net.net.response.AddCiResponse;
import com.powerge.wise.powerge.operationProjo.net.net.response.CreateCiResponse;
import com.powerge.wise.powerge.operationProjo.net.utils.PullPaseXmlUtil;
import com.powerge.wise.powerge.operationProjo.net.view.mview.LoadingView;
import com.powerge.wise.powerge.operationProjo.net.view.mview.MyTitle;
import com.powerge.wise.powerge.operationProjo.net.view.mview.WorkOrderDataManager;
import com.powerge.wise.powerge.operationProjo.net.view.mview.WorkOrderDetailView;

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
        list.add(MySharedpreferences.getUser().getName());
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
