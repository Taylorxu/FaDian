package com.wisesignsoft.OperationManagement.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.wisesignsoft.OperationManagement.BaseActivity;
import com.wisesignsoft.OperationManagement.R;
import com.wisesignsoft.OperationManagement.adapter.MultUserAdapter;
import com.wisesignsoft.OperationManagement.net.RequestTask;
import com.wisesignsoft.OperationManagement.net.request.RequestYxyw;
import com.wisesignsoft.OperationManagement.net.response.QueryAllValidUsersResponse;
import com.wisesignsoft.OperationManagement.utils.DividerItemDecoration;
import com.wisesignsoft.OperationManagement.view.mview.EmptyView;
import com.wisesignsoft.OperationManagement.view.mview.LoadingView;
import com.wisesignsoft.OperationManagement.view.mview.MyTitle;
import com.wisesignsoft.OperationManagement.view.mview.SeachView;
import com.wisesignsoft.OperationManagement.view.mview.WorkOrderDataManager;

import java.util.ArrayList;
import java.util.List;

public class MultUserChooseActivity extends BaseActivity implements SeachView.ISearchView {
    private MyTitle mt_mult_user;
    private SeachView sv_mult_user;
    private TextView tv_mult_user_total;
    private RecyclerView rv_mult_user;
    private EmptyView ev_select_user;
    /*选中的数据*/
    private List<QueryAllValidUsersResponse.ReturnValueBean> returnValue = new ArrayList<>();
    private List<QueryAllValidUsersResponse.ReturnValueBean> datas = new ArrayList<>();
    private List<QueryAllValidUsersResponse.ReturnValueBean> deletes = new ArrayList<>();
    private String ids;
    private String id;
    private String key;
    private MultUserAdapter adapter;

    public static void startSelf(Context context, String ids, String id) {
        Intent intent = new Intent(context, MultUserChooseActivity.class);
        intent.putExtra("ids", ids);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mult_user_choose);
        init();
        request();
    }

    private void init() {
        /*获取前面的页面传递的数据*/
        ids = getIntent().getStringExtra("ids");
        id = getIntent().getStringExtra("id");

        mt_mult_user = (MyTitle) findViewById(R.id.mt_mult_user);
        sv_mult_user = (SeachView) findViewById(R.id.sv_mult_user);
        tv_mult_user_total = (TextView) findViewById(R.id.tv_mult_user_total);
        rv_mult_user = (RecyclerView) findViewById(R.id.rv_mult_user);
        ev_select_user = (EmptyView) findViewById(R.id.ev_select_user);
        ev_select_user.setOnRefreshListener(new EmptyView.IRefreshListener() {
            @Override
            public void onRefresh() {
                request();
            }
        });

        mt_mult_user.setBack(true, this);
        mt_mult_user.setTitle("选择人员");
        mt_mult_user.setTvRight(true, "确定", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WorkOrderDataManager.getManager().setSingleDateById(id, MultUserChooseActivity.this.toString(returnValue));
                String value = WorkOrderDataManager.getManager().getSingleDateById(id);
                Log.i("YCS", "onClick: value:"+value);
                finish();
            }
        });

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        rv_mult_user.setLayoutManager(manager);
        rv_mult_user.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        adapter = new MultUserAdapter(this, datas);
        rv_mult_user.setAdapter(adapter);
        sv_mult_user.setISearchViewListener(this);
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
                if (response != null && response.getReturnValue() != null) {
                    datas.addAll(response.getReturnValue());
                    setResults(MultUserChooseActivity.this.toString(returnValue));
                }
                initData();
            }
        });
    }
    private void setEmpty() {
        if (datas == null || datas.size() == 0) {
            ev_select_user.setVisibility(View.VISIBLE);
            rv_mult_user.setVisibility(View.GONE);
        } else {
            ev_select_user.setVisibility(View.GONE);
            rv_mult_user.setVisibility(View.VISIBLE);
        }
    }
    private void request() {
        final LoadingView loadingView = LoadingView.getLoadingView(this);
        loadingView.show();
        List<String> list = new ArrayList<>();
        RequestYxyw.queryValidUsersByUserName(list, new RequestTask.ResultCallback<QueryAllValidUsersResponse>() {
            @Override
            public void onError(Exception e) {
                ev_select_user.close();
                loadingView.stop(loadingView);
            }

            @Override
            public void onResponse(QueryAllValidUsersResponse response) {
                ev_select_user.close();
                loadingView.stop(loadingView);
                if (datas != null) {
                    datas.clear();
                }
                datas.addAll(response.getReturnValue());
                setResults(ids);
                initData();
            }
        });
    }

    private void initData() {
        setEmpty();
        adapter.notifyDataSetChanged();
        adapter.setIMultUserClickListener(new MultUserAdapter.IMultUserClickListener() {
            @Override
            public void setOnMultUserClickListener(int position, boolean isSelect) {
                if (isSelect) {
                    returnValue.add(datas.get(position));
                } else {
                    returnValue.remove(datas.get(position));
                }

            }
        });
    }

    private void setResults(String ids) {
        if (TextUtils.isEmpty(ids)) {
            return;
        }
        String[] temps = ids.split(",");
        for (QueryAllValidUsersResponse.ReturnValueBean bean : datas) {
            bean.setSelect(false);
            for (String temp : temps) {
                if (temp.equals(bean.getUserId())) {
                    bean.setSelect(true);
                    returnValue.add(bean);
                }
            }
        }
    }

    private String toString(List<QueryAllValidUsersResponse.ReturnValueBean> results) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < results.size(); i++) {
            String id = results.get(i).getUserId();
            if (i == results.size() - 1) {
                sb.append(id);
            } else {
                sb.append(id).append(",");
            }
        }
        return sb.toString();
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
