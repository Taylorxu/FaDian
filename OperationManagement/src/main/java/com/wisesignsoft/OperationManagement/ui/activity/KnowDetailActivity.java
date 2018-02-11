package com.wisesignsoft.OperationManagement.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.wisesignsoft.OperationManagement.BaseActivity;
import com.wisesignsoft.OperationManagement.R;
import com.wisesignsoft.OperationManagement.db.MySharedpreferences;
import com.wisesignsoft.OperationManagement.net.RequestTask;
import com.wisesignsoft.OperationManagement.net.request.RequestKnow;
import com.wisesignsoft.OperationManagement.net.response.FindKnowledgeListResponse;
import com.wisesignsoft.OperationManagement.net.response.OpenKnowDetailResponse;
import com.wisesignsoft.OperationManagement.utils.PullPaseXmlUtil;
import com.wisesignsoft.OperationManagement.view.mview.LoadingView;
import com.wisesignsoft.OperationManagement.view.mview.MyTitle;
import com.wisesignsoft.OperationManagement.view.mview.WorkOrderDetailView;

import java.util.ArrayList;
import java.util.List;

public class KnowDetailActivity extends BaseActivity {

    private MyTitle mt_know;
    private TextView tv_know_title;
    private TextView tv_konw_company;
    private TextView tv_konw_time;
    private WorkOrderDetailView wodv;
    private String id;
    private String key;
    private String title;
    private String time;
    private String name;

    public static void startSelf(Context context,String id,String key,String title,String name,String time){
        Intent intent = new Intent(context,KnowDetailActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("key",key);
        intent.putExtra("title",title);
        intent.putExtra("name",name);
        intent.putExtra("time",time);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_know_detail);
        init();
        request();
    }
    private void init(){
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        key = intent.getStringExtra("key");
        title = intent.getStringExtra("title");
        name = intent.getStringExtra("name");
        time = intent.getStringExtra("time");
        mt_know = (MyTitle) findViewById(R.id.mt_know_detail);
        tv_know_title = (TextView) findViewById(R.id.tv_know_detail_title);
        tv_konw_company = (TextView) findViewById(R.id.tv_know_detail_company_name);
        tv_konw_time = (TextView) findViewById(R.id.tv_know_detail_create_time);
        wodv = (WorkOrderDetailView) findViewById(R.id.wodv_know_detail);

        mt_know.setTitle("知识详情");
        mt_know.setBack(true,this);

        tv_know_title.setText(title);
        tv_konw_company.setText(name);
        tv_konw_time.setText(time);
    }
    private void request(){
        final LoadingView loadingView = LoadingView.getLoadingView(this);
        loadingView.show();
        List<String> list = new ArrayList<>();
        list.add(MySharedpreferences.getUser().getUsername());
        list.add(key);
        list.add(id);
        RequestKnow.openKnowDetail(list, new RequestTask.ResultCallback<OpenKnowDetailResponse>() {
            @Override
            public void onError(Exception e) {
            loadingView.stop(loadingView);
            }

            @Override
            public void onResponse(OpenKnowDetailResponse response) {
                String form = response.getFormDocument();
                List datas = PullPaseXmlUtil.pase(form);
                wodv.setData(datas,KnowDetailActivity.this);
                loadingView.stop(loadingView);
            }
        });
    }
}
