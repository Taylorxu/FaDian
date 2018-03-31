package com.powerge.wise.powerge.operationProjo.net.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.powerge.wise.powerge.operationProjo.net.BaseActivity;
import com.powerge.wise.powerge.operationProjo.net.adapter.WonhInfoAdapter;
import com.powerge.wise.powerge.operationProjo.net.net.RequestTask;
import com.powerge.wise.powerge.operationProjo.net.net.request.RequestProcess;
import com.powerge.wise.powerge.operationProjo.net.net.response.FindWonhInfoResponse;
import com.powerge.wise.powerge.operationProjo.net.view.mview.MyTitle;
import com.wisesignsoft.OperationManagement.R;

import java.util.ArrayList;
import java.util.List;

public class WonhInfoActivity extends BaseActivity {

    private RecyclerView rv_wonh;
    private MyTitle mt_wonh;
    private List<FindWonhInfoResponse.WonhListBean> datas = new ArrayList<>();
    private WonhInfoAdapter adapter;
    private String key;

    public static void startSelf(Context context, String key){
        Intent intent = new Intent(context,WonhInfoActivity.class);
        intent.putExtra("key",key);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wonh_info);
        init();
        request();
    }
    private void init(){
        key = getIntent().getStringExtra("key");
        mt_wonh = (MyTitle) findViewById(R.id.mt_wonh);
        rv_wonh = (RecyclerView) findViewById(R.id.rv_wonh);

        mt_wonh.setBack(true,this);
        mt_wonh.setTitle("工单处理过程");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv_wonh.setLayoutManager(layoutManager);
        adapter = new WonhInfoAdapter(this,datas);
        rv_wonh.setAdapter(adapter);
    }
    private void request(){
        List<String> list = new ArrayList<>();
        list.add(key);
        RequestProcess.findWonhInfo(list, new RequestTask.ResultCallback<FindWonhInfoResponse>() {
            @Override
            public void onError(Exception e) {

            }

            @Override
            public void onResponse(FindWonhInfoResponse response) {
                if(datas != null){
                    datas.clear();
                }
                datas.addAll(response.getWonhList());
                adapter.notifyDataSetChanged();
            }
        });
    }
}
