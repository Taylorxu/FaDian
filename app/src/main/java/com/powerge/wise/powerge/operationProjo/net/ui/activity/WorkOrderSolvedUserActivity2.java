package com.powerge.wise.powerge.operationProjo.net.ui.activity;

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
import com.powerge.wise.powerge.operationProjo.net.BaseActivity;
import com.powerge.wise.powerge.operationProjo.net.adapter.WOSUAdapter2;
import com.powerge.wise.powerge.operationProjo.net.bean.TaskStrategy;
import com.powerge.wise.powerge.operationProjo.net.db.MySharedpreferences;
import com.powerge.wise.powerge.operationProjo.net.manager.WorkOrderSolvingManager;
import com.powerge.wise.powerge.operationProjo.net.net.RequestTask;
import com.powerge.wise.powerge.operationProjo.net.net.request.RequestProcess;
import com.powerge.wise.powerge.operationProjo.net.net.request.RequestYxyw;
import com.powerge.wise.powerge.operationProjo.net.net.response.BaseResponse;
import com.powerge.wise.powerge.operationProjo.net.net.response.QueryValidUserBydeptResponse;
import com.powerge.wise.powerge.operationProjo.net.utils.DividerItemDecoration;
import com.powerge.wise.powerge.operationProjo.net.view.mview.EmptyView;
import com.powerge.wise.powerge.operationProjo.net.view.mview.LoadingView;
import com.powerge.wise.powerge.operationProjo.net.view.mview.MyTitle;
import com.powerge.wise.powerge.operationProjo.net.view.mview.SeachView;
import com.powerge.wise.powerge.operationProjo.net.view.mview.WorkOrderDataManager;
import com.wisesignsoft.OperationManagement.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

;
 

public class WorkOrderSolvedUserActivity2 extends BaseActivity implements SeachView.ISearchView {
    private MyTitle mt_mult_user;
    private SeachView sv_mult_user;
    private TextView tv_mult_user_total;
    private RecyclerView rv_mult_user;
    private EmptyView ev_select_user;
    /*选中的数据*/
    private List<QueryValidUserBydeptResponse.ReturnValueBean> returnValue = new ArrayList<>();
    /*数据源*/
    private List<QueryValidUserBydeptResponse.ReturnValueBean> userBean2s = new ArrayList<>();
    /*搜索出来的数据*/
    private List<QueryValidUserBydeptResponse.ReturnValueBean> searchDatas = new ArrayList<>();
    private String id;
    private String ID;
    private String taskKey;
    private String key;
    private WOSUAdapter2 adapter;

    /*做本地搜索*/
    private Map<String,QueryValidUserBydeptResponse.ReturnValueBean> map = new HashMap<>();

    public static void startSelf(Context context,String id,String ID,String taskKey) {
        Intent intent = new Intent(context, WorkOrderSolvedUserActivity2.class);
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
                String result = WorkOrderSolvedUserActivity2.this.toString(returnValue);
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
        adapter = new WOSUAdapter2(this, userBean2s);
        rv_mult_user.setAdapter(adapter);
        sv_mult_user.setISearchViewListener(this);
        sv_mult_user.setHint("请输入用户名");
    }

    private void requestSearch() {
        if(!TextUtils.isEmpty(key)){
            searchDatas.clear();
            Set<String> set = map.keySet();
            Iterator<String> iterator = set.iterator();
            while (iterator.hasNext()){
                String name = iterator.next();
                if(!TextUtils.isEmpty(name)&&name.contains(key)){
                    searchDatas.add(map.get(key));
                }
            }
            setResults(WorkOrderSolvedUserActivity2.this.toString(returnValue));
            if(userBean2s != null){
                userBean2s.clear();
            }
            userBean2s.addAll(searchDatas);
            initData();
        }
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
        RequestYxyw.queryValidUserBydept(list, new RequestTask.ResultCallback<QueryValidUserBydeptResponse>() {
            @Override
            public void onError(Exception e) {
                ev_select_user.close();
                loadingView.stop(loadingView);
            }

            @Override
            public void onResponse(QueryValidUserBydeptResponse response) {
                ev_select_user.close();
                loadingView.stop(loadingView);
                if(userBean2s != null){
                    userBean2s.clear();
                }
                userBean2s.addAll(response.getReturnValue());
                setLocalMap();
                initData();
            }
        });
    }

    private void initData() {
        setEmpty();
        adapter.notifyDataSetChanged();
        adapter.setIMultUserClickListener(new WOSUAdapter2.IMultUserClickListener() {
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
        for (QueryValidUserBydeptResponse.ReturnValueBean bean : userBean2s) {
            bean.setSelect(false);
            for (String temp : temps) {
                if (temp.equals(bean.getUserId())) {
                    bean.setSelect(true);
                    returnValue.add(bean);
                }
            }
        }
    }
    private void setLocalMap(){
        for(QueryValidUserBydeptResponse.ReturnValueBean bean : userBean2s){
            String name = bean.getUserName();
            map.put(name,bean);
        }
    }

    private String toString(List<QueryValidUserBydeptResponse.ReturnValueBean> results) {
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
    public void commit() {
        final LoadingView loadingView = LoadingView.getLoadingView(this);
        loadingView.show();
        WorkOrderDataManager.getManager().solvedMap(new WorkOrderDataManager.LoadListener3() {
            @Override
            public void setLoadListenenr3() {
                if (!WorkOrderDataManager.getManager().isCommit(WorkOrderSolvedUserActivity2.this)) {
                    loadingView.stop(loadingView);
                    return;
                }
                Map<String, String> map = WorkOrderDataManager.getManager().getReturnString();
                Gson gson = new Gson();
                String result = gson.toJson(map);
                List<String> list = new ArrayList<>();
                list.add(result);
                list.add(MySharedpreferences.getUser().getName());
                RequestProcess.submitTask(list, new RequestTask.ResultCallback<BaseResponse>() {
                    @Override
                    public void onError(Exception e) {
                        loadingView.stop(loadingView);
                    }

                    @Override
                    public void onResponse(BaseResponse response) {
                        loadingView.stop(loadingView);
                        Toast.makeText(WorkOrderSolvedUserActivity2.this, "处理成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }

            @Override
            public void setUnLoadListener() {
                if (!WorkOrderDataManager.getManager().isCommit(WorkOrderSolvedUserActivity2.this)) {
                    loadingView.stop(loadingView);
                    return;
                }
                Map<String, String> map = WorkOrderDataManager.getManager().getReturnString();
                Gson gson = new Gson();
                String result = gson.toJson(map);
                List<String> list = new ArrayList<>();
                list.add(result);
                list.add(MySharedpreferences.getUser().getName());
                RequestProcess.submitTask(list, new RequestTask.ResultCallback<BaseResponse>() {
                    @Override
                    public void onError(Exception e) {
                        loadingView.stop(loadingView);
                    }

                    @Override
                    public void onResponse(BaseResponse response) {
                        loadingView.stop(loadingView);
                        Toast.makeText(WorkOrderSolvedUserActivity2.this, "处理成功", Toast.LENGTH_SHORT).show();
                        WorkOrderSolvingManager.getInstance().clear();
                        finish();
                    }
                });
            }
        });
    }
}
