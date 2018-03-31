package com.powerge.wise.powerge.operationProjo.net.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.powerge.wise.powerge.operationProjo.net.BaseActivity;
import com.powerge.wise.powerge.operationProjo.net.adapter.SingleRoleAdapter;
import com.powerge.wise.powerge.operationProjo.net.net.RequestTask;
import com.powerge.wise.powerge.operationProjo.net.net.request.RequestRole;
import com.powerge.wise.powerge.operationProjo.net.net.response.FindRoleByGroupIdResponse;
import com.powerge.wise.powerge.operationProjo.net.utils.DividerItemDecoration;
import com.powerge.wise.powerge.operationProjo.net.view.mview.LoadingView;
import com.powerge.wise.powerge.operationProjo.net.view.mview.MyTitle;
import com.powerge.wise.powerge.operationProjo.net.view.mview.SeachView;
import com.powerge.wise.powerge.operationProjo.net.view.mview.WorkOrderDataManager;
import com.wisesignsoft.OperationManagement.R;

import java.util.ArrayList;
import java.util.List;


public class SingleRoleChooseActivity extends BaseActivity {

    private MyTitle mt_single_user;
    private SeachView sv_single_user;
    private TextView tv_single_user_total;
    private RecyclerView rv_single_user;
    private List<FindRoleByGroupIdResponse.ReturnValueBean> returnValue;
    private String groupId;
    private String id;

    public static void startSelf(Context context, String groupId, String id) {
        Intent intent = new Intent(context, SingleRoleChooseActivity.class);
        intent.putExtra("groupId", groupId);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_role);
        init();
        request();
    }

    private void init() {
        groupId = getIntent().getStringExtra("groupId");
        id = getIntent().getStringExtra("id");
        mt_single_user = (MyTitle) findViewById(R.id.mt_single_user);
        sv_single_user = (SeachView) findViewById(R.id.sv_single_user);
        tv_single_user_total = (TextView) findViewById(R.id.tv_single_user_total);
        rv_single_user = (RecyclerView) findViewById(R.id.rv_single_user);

        mt_single_user.setBack(true, this);
        mt_single_user.setTitle("选择角色");

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        rv_single_user.setLayoutManager(manager);
        rv_single_user.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
    }

    public void setDate() {
        SingleRoleAdapter adapter = new SingleRoleAdapter(this, returnValue);
        rv_single_user.setAdapter(adapter);
        adapter.setOnISingleRoleClickListener(new SingleRoleAdapter.ISingleRoleClickListener() {
            @Override
            public void setOnISingleRoleClickListener(String name) {
                WorkOrderDataManager.getManager().setSingleDateById(id, name);
                finish();
            }
        });
    }

    private void request() {
        final LoadingView loadingView = LoadingView.getLoadingView(this);
        loadingView.show();
        List<String> list = new ArrayList<>();
        list.add(groupId);
        RequestRole.findRoleByGroupId(list, new RequestTask.ResultCallback<FindRoleByGroupIdResponse>() {
            @Override
            public void onError(Exception e) {
                loadingView.stop(loadingView);
            }

            @Override
            public void onResponse(FindRoleByGroupIdResponse response) {
                loadingView.stop(loadingView);
                returnValue = response.getReturnValue();
                setDate();
            }
        });
    }
}
