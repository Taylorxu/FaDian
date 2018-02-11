package com.wisesignsoft.OperationManagement.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wisesignsoft.OperationManagement.BaseActivity;
import com.wisesignsoft.OperationManagement.R;
import com.wisesignsoft.OperationManagement.adapter.MultUserAdapter;
import com.wisesignsoft.OperationManagement.adapter.WOSUAdapter;
import com.wisesignsoft.OperationManagement.bean.TaskStrategy;
import com.wisesignsoft.OperationManagement.bean.UserBean2;
import com.wisesignsoft.OperationManagement.db.MySharedpreferences;
import com.wisesignsoft.OperationManagement.manager.WorkOrderSolvingManager;
import com.wisesignsoft.OperationManagement.net.RequestTask;
import com.wisesignsoft.OperationManagement.net.request.RequestDept;
import com.wisesignsoft.OperationManagement.net.request.RequestProcess;
import com.wisesignsoft.OperationManagement.net.request.RequestRole;
import com.wisesignsoft.OperationManagement.net.request.RequestYxyw;
import com.wisesignsoft.OperationManagement.net.response.BaseResponse;
import com.wisesignsoft.OperationManagement.net.response.FindUserByRoleIdResponse;
import com.wisesignsoft.OperationManagement.net.response.QueryAllValidUsersResponse;
import com.wisesignsoft.OperationManagement.utils.DividerItemDecoration;
import com.wisesignsoft.OperationManagement.view.mview.EmptyView;
import com.wisesignsoft.OperationManagement.view.mview.LoadingView;
import com.wisesignsoft.OperationManagement.view.mview.MyTitle;
import com.wisesignsoft.OperationManagement.view.mview.SeachView;
import com.wisesignsoft.OperationManagement.view.mview.WorkOrderDataManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WorkOrderSolvedUserActivity extends BaseActivity implements SeachView.ISearchView {
    private MyTitle mt_mult_user;
    private SeachView sv_mult_user;
    private TextView tv_mult_user_total;
    private RecyclerView rv_mult_user;
    private EmptyView ev_select_user;
    /*选中的数据*/
    private List<UserBean2> returnValue = new ArrayList<>();
    /*数据源*/
    private List<UserBean2> userBean2s = new ArrayList<>();
    private String id;
    private String ID;
    private String taskKey;
    private String key;
    private WOSUAdapter adapter;

    public static void startSelf(Context context,String id,String ID,String taskKey) {
        Intent intent = new Intent(context, WorkOrderSolvedUserActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("ID",ID);
        intent.putExtra("taskKey",taskKey);
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
        id = getIntent().getStringExtra("id");
        ID = getIntent().getStringExtra("ID");
        taskKey = getIntent().getStringExtra("taskKey");

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
        mt_mult_user.setTitle("工单处理人");
        mt_mult_user.setTvRight(true, "确定", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String result = WorkOrderSolvedUserActivity.this.toString(returnValue);
                TaskStrategy taskStrategy = new TaskStrategy();
                taskStrategy.setStrategyKey(taskKey);
                taskStrategy.setStrategyValue(result);
                WorkOrderDataManager.getManager().setCeLueById(ID,taskStrategy);
                commit();
            }
        });

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        rv_mult_user.setLayoutManager(manager);
        rv_mult_user.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        adapter = new WOSUAdapter(this, userBean2s);
        rv_mult_user.setAdapter(adapter);
        sv_mult_user.setISearchViewListener(this);
        sv_mult_user.setHint("请输入用户名");
    }

    private void requestSearch() {
        final LoadingView loadingView = LoadingView.getLoadingView(this);
        loadingView.show();
        List<String> list = new ArrayList<>();
        list.add(key);
        list.add(id);
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
                if (response != null && response.getReturnValue() != null) {
                    setResults(WorkOrderSolvedUserActivity.this.toString(returnValue));
                    tresForm1(response.getReturnValue());
                }

            }
        });
    }
    private void setEmpty() {
        if (userBean2s == null || userBean2s.size() == 0) {
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
        list.add(id);
        RequestRole.findUserByRoleId(list, new RequestTask.ResultCallback<FindUserByRoleIdResponse>() {
            @Override
            public void onError(Exception e) {
                ev_select_user.close();
                loadingView.stop(loadingView);
            }

            @Override
            public void onResponse(FindUserByRoleIdResponse response) {
                ev_select_user.close();
                loadingView.stop(loadingView);
                tresForm2(response.getResult());
            }
        });
    }

    private void initData() {
        setEmpty();
        adapter.notifyDataSetChanged();
        adapter.setIMultUserClickListener(new WOSUAdapter.IMultUserClickListener() {
            @Override
            public void setOnMultUserClickListener(int position, boolean isSelect) {
                if (isSelect) {
                    returnValue.add(userBean2s.get(position));
                } else {
                    returnValue.remove(userBean2s.get(position));
                }

            }
        });
    }

    private void setResults(String ids) {
        if (TextUtils.isEmpty(ids)) {
            return;
        }
        String[] temps = ids.split(",");
        for (UserBean2 bean : userBean2s) {
            bean.setSelect(false);
            for (String temp : temps) {
                if (temp.equals(bean.getId())) {
                    bean.setSelect(true);
                    returnValue.add(bean);
                }
            }
        }
    }

    private String toString(List<UserBean2> results) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < results.size(); i++) {
            String id = results.get(i).getId();
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
    private void tresForm1(List<QueryAllValidUsersResponse.ReturnValueBean> returnValue){
        List<UserBean2> list = new ArrayList<>();
        for(QueryAllValidUsersResponse.ReturnValueBean bean:returnValue){
            UserBean2 bean2 = new UserBean2();
            bean2.setId(bean.getUserId());
            bean2.setUserName(bean.getUserName());
            bean2.setSelect(bean.isSelect());
            list.add(bean2);
        }
        userBean2s.clear();
        userBean2s.addAll(list);
        initData();
    }
    private void tresForm2(List<FindUserByRoleIdResponse.ResultBean> result){
        List<UserBean2> list = new ArrayList<>();
        for(FindUserByRoleIdResponse.ResultBean bean:result){
            UserBean2 bean2 = new UserBean2();
            bean2.setId(bean.getUserId());
            bean2.setUserName(bean.getUserName());
            bean2.setSelect(bean.isSelect());
            list.add(bean2);
        }
        userBean2s.clear();
        userBean2s.addAll(list);
        initData();
    }
    public void commit() {
        final LoadingView loadingView = LoadingView.getLoadingView(this);
        loadingView.show();
        WorkOrderDataManager.getManager().solvedMap(new WorkOrderDataManager.LoadListener3() {
            @Override
            public void setLoadListenenr3() {
                if (!WorkOrderDataManager.getManager().isCommit(WorkOrderSolvedUserActivity.this)) {
                    loadingView.stop(loadingView);
                    return;
                }
                Map<String, String> map = WorkOrderDataManager.getManager().getReturnString();
                Gson gson = new Gson();
                String result = gson.toJson(map);
                List<String> list = new ArrayList<>();
                list.add(result);
                list.add(MySharedpreferences.getUser().getUsername());
                RequestProcess.submitTask(list, new RequestTask.ResultCallback<BaseResponse>() {
                    @Override
                    public void onError(Exception e) {
                        loadingView.stop(loadingView);
                    }

                    @Override
                    public void onResponse(BaseResponse response) {
                        loadingView.stop(loadingView);
                        Toast.makeText(WorkOrderSolvedUserActivity.this, "处理成功", Toast.LENGTH_SHORT).show();
                        WorkOrderSolvingManager.getInstance().clear();
                        finish();
                    }
                });
            }

            @Override
            public void setUnLoadListener() {
                if (!WorkOrderDataManager.getManager().isCommit(WorkOrderSolvedUserActivity.this)) {
                    loadingView.stop(loadingView);
                    return;
                }
                Map<String, String> map = WorkOrderDataManager.getManager().getReturnString();
                Gson gson = new Gson();
                String result = gson.toJson(map);
                List<String> list = new ArrayList<>();
                list.add(result);
                list.add(MySharedpreferences.getUser().getUsername());
                RequestProcess.submitTask(list, new RequestTask.ResultCallback<BaseResponse>() {
                    @Override
                    public void onError(Exception e) {
                        loadingView.stop(loadingView);
                    }

                    @Override
                    public void onResponse(BaseResponse response) {
                        loadingView.stop(loadingView);
                        Toast.makeText(WorkOrderSolvedUserActivity.this, "处理成功", Toast.LENGTH_SHORT).show();
                        WorkOrderSolvingManager.getInstance().clear();
                        finish();
                    }
                });
            }
        });
    }
}
