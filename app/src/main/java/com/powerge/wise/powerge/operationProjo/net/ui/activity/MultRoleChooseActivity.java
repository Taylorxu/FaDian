package com.powerge.wise.powerge.operationProjo.net.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.powerge.wise.powerge.operationProjo.net.BaseActivity;
import com.powerge.wise.powerge.operationProjo.net.adapter.MultRoleAdapter;
import com.powerge.wise.powerge.operationProjo.net.bean.WorkOrder;
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

public class MultRoleChooseActivity extends BaseActivity {
    private MyTitle mt_mult_role;
    private SeachView sv_mult_role;
    private TextView tv_mult_role_total;
    private RecyclerView rv_mult_role;
    private Button bt_mult_role;
    /*所有数据*/
    private List<FindRoleByGroupIdResponse.ReturnValueBean> returnValue;
    /*提交的数据*/
    private List<FindRoleByGroupIdResponse.ReturnValueBean> results = new ArrayList<>();

    private String groupId;
    private String id;
    private String value;

    public static void startSelf(Context context, WorkOrder wo, String groupId, String id, String value) {
        Intent intent = new Intent(context, MultRoleChooseActivity.class);
        intent.putExtra("groupId", groupId);
        intent.putExtra("id", id);
        intent.putExtra("wo", wo);
        intent.putExtra("value", value);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mult_role_choose);
        init();
        request();
    }

    private void init() {
        /*获取来自上个页面的数据*/
        groupId = getIntent().getStringExtra("groupId");
        id = getIntent().getStringExtra("id");
        value = getIntent().getStringExtra("value");

        mt_mult_role = (MyTitle) findViewById(R.id.mt_mult_role);
        sv_mult_role = (SeachView) findViewById(R.id.sv_mult_role);
        tv_mult_role_total = (TextView) findViewById(R.id.tv_mult_role_total);
        rv_mult_role = (RecyclerView) findViewById(R.id.rv_mult_role);
        bt_mult_role = (Button) findViewById(R.id.bt_mult_role);

        mt_mult_role.setBack(true, this);
        mt_mult_role.setTitle("选择角色");
        mt_mult_role.setTvRight(true, "确定", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFinishResults();
            }
        });
        bt_mult_role.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFinishResults();
            }
        });
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        rv_mult_role.setLayoutManager(manager);
        rv_mult_role.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
    }

    public void setFinishResults() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < results.size(); i++) {
            String id = results.get(i).getRoleId();
            if (i == results.size() - 1) {
                sb.append(id);
            } else {
                sb.append(id).append(",");
            }
        }
        WorkOrderDataManager.getManager().setSingleDateById2(id, sb.toString());
        finish();
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
                setResults();
                initData();
            }
        });
    }

    private void initData() {
        MultRoleAdapter adapter = new MultRoleAdapter(this, returnValue);
        rv_mult_role.setAdapter(adapter);
        adapter.setIMultRoleClickListener(new MultRoleAdapter.IMultRoleClickListener() {
            @Override
            public void setOnMultRoleClickListener(int position, boolean isSelect) {
                if (isSelect) {
                    results.add(returnValue.get(position));
                } else {
                    results.remove(returnValue.get(position));
                }
            }
        });
    }

    private void setResults() {
        String[] befores = value.split(",");
        for (FindRoleByGroupIdResponse.ReturnValueBean bean : returnValue) {
            String id = bean.getRoleId();
            bean.setSelect(false);
            for (String before : befores) {
                if (id.equals(before)) {
                    bean.setSelect(true);
                    results.add(bean);
                }
            }
        }
    }
}
