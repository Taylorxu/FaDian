package com.powerge.wise.powerge.operationProjo.net.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.operationProjo.net.BaseActivity;
import com.powerge.wise.powerge.operationProjo.net.net.RequestTask;
import com.powerge.wise.powerge.operationProjo.net.net.request.RequestProcess;
import com.powerge.wise.powerge.operationProjo.net.net.response.OpenTepletResponse;
import com.powerge.wise.powerge.operationProjo.net.utils.PullPaseXmlUtil;
import com.powerge.wise.powerge.operationProjo.net.view.mview.KeyValueView;
import com.powerge.wise.powerge.operationProjo.net.view.mview.LoadingView;
import com.powerge.wise.powerge.operationProjo.net.view.mview.MyTitle;
import com.powerge.wise.powerge.operationProjo.net.view.mview.WorkOrderDetailView;

import java.util.ArrayList;
import java.util.List;


public class TemplateDetailActivity extends BaseActivity {

    private MyTitle mt_template_detail;
    private KeyValueView kvv_template_detail;
    private WorkOrderDetailView wodv_template_detail;
    private String id;

    public static void startSelf(Context context,String ID){
        Intent intent = new Intent(context,TemplateDetailActivity.class);
        intent.putExtra("ID",ID);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template_detail);
        init();
        request();
    }
    private void init(){
        mt_template_detail = (MyTitle) findViewById(R.id.mt_template_detail);
        kvv_template_detail = (KeyValueView) findViewById(R.id.kvv_template_detail);
        wodv_template_detail = (WorkOrderDetailView) findViewById(R.id.wodv_template_detail);

        mt_template_detail.setBack(true,this);
        mt_template_detail.setTitle("模板详情");

        kvv_template_detail.setValueText("从草稿中选择");
        id = getIntent().getStringExtra("ID");
    }
    private void request(){
        final LoadingView loadingView = LoadingView.getLoadingView(this);
        loadingView.show();
        List<String> list = new ArrayList<>();
        list.add(id);
        RequestProcess.openTeplet(list, new RequestTask.ResultCallback<OpenTepletResponse>() {
            @Override
            public void onError(Exception e) {
            loadingView.stop(loadingView);
            }

            @Override
            public void onResponse(OpenTepletResponse response) {
                String str = response.getReturnValue().getFormDocument();
                List datas = PullPaseXmlUtil.pase(str);
                Log.i("YCS", "onResponse: size:"+datas.size());
                wodv_template_detail.setData(datas,TemplateDetailActivity.this);
                loadingView.stop(loadingView);
            }
        });
    }
}
