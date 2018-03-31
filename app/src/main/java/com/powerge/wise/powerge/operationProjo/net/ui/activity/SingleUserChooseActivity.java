package com.powerge.wise.powerge.operationProjo.net.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.powerge.wise.powerge.operationProjo.net.BaseActivity;
import com.powerge.wise.powerge.operationProjo.net.adapter.SingleUserAdapter;
import com.powerge.wise.powerge.operationProjo.net.manager.SingleUserManager;
import com.powerge.wise.powerge.operationProjo.net.net.RequestTask;
import com.powerge.wise.powerge.operationProjo.net.net.request.RequestYxyw;
import com.powerge.wise.powerge.operationProjo.net.net.response.QueryAllValidUsersResponse;
import com.powerge.wise.powerge.operationProjo.net.view.mview.EmptyView;
import com.powerge.wise.powerge.operationProjo.net.view.mview.LoadingView;
import com.powerge.wise.powerge.operationProjo.net.view.mview.MyTitle;
import com.powerge.wise.powerge.operationProjo.net.view.mview.SeachView;
import com.powerge.wise.powerge.operationProjo.net.view.mview.WorkOrderDataManager;
import com.wisesignsoft.OperationManagement.R;

import java.util.ArrayList;
import java.util.List;

public class SingleUserChooseActivity extends BaseActivity implements SeachView.ISearchView {
    private MyTitle mt_single_user;
    private SeachView sv_single_user;
    private TextView tv_single_user_total;
    private RecyclerView rv_single_user;
    private EmptyView ev_select_user;
    private List<QueryAllValidUsersResponse.ReturnValueBean> datas = new ArrayList<>();
    private String id;
    private SingleUserAdapter adapter;
    /*搜索关键字*/
    private String key;

    public static void startSelf(Context context, String id) {
        Intent intent = new Intent(context, SingleUserChooseActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_user_choose);
        SingleUserManager.getManager().addActivity(this);
        init();
        request();
    }

    private void init() {
        mt_single_user = (MyTitle) findViewById(R.id.mt_single_user);
        sv_single_user = (SeachView) findViewById(R.id.sv_single_user);
        tv_single_user_total = (TextView) findViewById(R.id.tv_single_user_total);
        rv_single_user = (RecyclerView) findViewById(R.id.rv_single_user);
        ev_select_user = (EmptyView) findViewById(R.id.ev_select_user);

        ev_select_user.setOnRefreshListener(new EmptyView.IRefreshListener() {
            @Override
            public void onRefresh() {
                request();
            }
        });
        mt_single_user.setBack(true, this);
        mt_single_user.setTitle("选择人员");

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        rv_single_user.setLayoutManager(manager);
        adapter = new SingleUserAdapter(this, datas);
        rv_single_user.setAdapter(adapter);

        id = getIntent().getStringExtra("id");
        sv_single_user.setISearchViewListener(this);
    }

    private void setDate() {
        setEmpty();
        adapter.notifyDataSetChanged();
        adapter.setOnISingleUserClickListener(new SingleUserAdapter.ISingleUserClickListener() {
            @Override
            public void setOnUserNameClickListener(String username) {
                WorkOrderDataManager.getManager().setSingleDateById(id, username);
                SingleUserManager.getManager().setTemp(username);
                finish();
            }
        });
    }

    private void request() {
        final LoadingView loadingView = LoadingView.getLoadingView(this);
        loadingView.show();
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        RequestYxyw.queryValidUsersByUserName(list, new RequestTask.ResultCallback<QueryAllValidUsersResponse>() {
            @Override
            public void onError(Exception e) {
                loadingView.stop(loadingView);
                ev_select_user.close();
            }

            @Override
            public void onResponse(QueryAllValidUsersResponse response) {
                loadingView.stop(loadingView);
                ev_select_user.close();
                if (datas != null) {
                    datas.clear();
                }
                if (response != null && response.getReturnValue() != null) {
                    datas.addAll(response.getReturnValue());
                }
                setDate();
            }
        });
    }

    private void requestSearch() {
        final LoadingView loadingView = LoadingView.getLoadingView(this);
        loadingView.show();
        List<String> list = new ArrayList<>();
        list.add(key);
        list.add("");
        RequestYxyw.queryValidUsersByUserName(list, new RequestTask.ResultCallback<QueryAllValidUsersResponse>() {
            @Override
            public void onError(Exception e) {
                loadingView.stop(loadingView);
                ev_select_user.close();
            }

            @Override
            public void onResponse(QueryAllValidUsersResponse response) {
                loadingView.stop(loadingView);
                ev_select_user.close();
                if (datas != null) {
                    datas.clear();
                }
                datas.addAll(response.getReturnValue());
                setDate();
            }
        });
    }
    private void setEmpty() {
        if (datas == null || datas.size() == 0) {
            ev_select_user.setVisibility(View.VISIBLE);
            rv_single_user.setVisibility(View.GONE);
        } else {
            ev_select_user.setVisibility(View.GONE);
            rv_single_user.setVisibility(View.VISIBLE);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        SingleUserManager.getManager().removeActivity(this);
    }

    @Override
    public void setOnSearchListener(String key) {
        this.key = key;
        requestSearch();
    }

    @Override
    public void setOnCancelListener() {
        this.key = "";
        request();
    }
}
