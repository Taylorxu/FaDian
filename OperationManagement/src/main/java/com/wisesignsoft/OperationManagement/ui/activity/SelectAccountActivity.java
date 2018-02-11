package com.wisesignsoft.OperationManagement.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wisesignsoft.OperationManagement.BaseActivity;
import com.wisesignsoft.OperationManagement.R;
import com.wisesignsoft.OperationManagement.adapter.SelectAccountAdapter;
import com.wisesignsoft.OperationManagement.bean.ContractInfoFindParamBean;
import com.wisesignsoft.OperationManagement.bean.ContractInfoSoreParamBean;
import com.wisesignsoft.OperationManagement.bean.ResModeValue;
import com.wisesignsoft.OperationManagement.bean.WorkOrder;
import com.wisesignsoft.OperationManagement.db.MySharedpreferences;
import com.wisesignsoft.OperationManagement.net.RequestTask;
import com.wisesignsoft.OperationManagement.net.request.RequestCi;
import com.wisesignsoft.OperationManagement.net.request.RequestYxyw;
import com.wisesignsoft.OperationManagement.net.response.QueryDataResponse;
import com.wisesignsoft.OperationManagement.net.response.QueryModelByBmModelNameResponse;
import com.wisesignsoft.OperationManagement.utils.SwipeRefreshUtil;
import com.wisesignsoft.OperationManagement.view.mview.EmptyView;
import com.wisesignsoft.OperationManagement.view.mview.LoadingView;
import com.wisesignsoft.OperationManagement.view.mview.MyTitle;
import com.wisesignsoft.OperationManagement.view.mview.RefreshRecyclerView;
import com.wisesignsoft.OperationManagement.view.mview.SeachView;
import com.wisesignsoft.OperationManagement.view.mview.WorkOrderDataManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SelectAccountActivity extends BaseActivity implements RefreshRecyclerView.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, SeachView.ISearchView {
    private MyTitle mt_title;
    private SeachView sv_select_account;
    private TextView tv_select_account_total;
    private SwipeRefreshLayout srl_select_account;
    private LoadingView loadingView;
    private RefreshRecyclerView rrv_select_account;
    private EmptyView ev_select_account;
    /*属性表*/
    private List<QueryModelByBmModelNameResponse.ReturnValueBean.AttrDefineListBean> attrDefineList;
    /*数据的当前页面*/
    private int currentPage = 0;
    /*搜索关键词*/
    private String key = "";
    /*总数*/
    private int total;
    private List<Map<String,String>> datas = new ArrayList<>();
    private String modelName;
    private List<ResModeValue.ConfigValueJsonBean> bean;
    private String str_key;

    public static void startSelf(Context context,String att,String id) {
        Intent intent = new Intent(context, SelectAccountActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("att",att);
        context.startActivity(intent);
    }

    private RequestTask.ResultCallback<QueryDataResponse> callback = new RequestTask.ResultCallback<QueryDataResponse>() {
        @Override
        public void onError(Exception e) {
            if (srl_select_account.isRefreshing()) {
                srl_select_account.setRefreshing(false);
            }
            loadingView.stop(loadingView);
        }

        @Override
        public void onResponse(QueryDataResponse response) {
            loadingView.stop(loadingView);
            total = response.getReturnValue().getTotal();
            tv_select_account_total.setText("共有" + total + "条数据");
            if (srl_select_account.isRefreshing()) {
                srl_select_account.setRefreshing(false);
            }
            if (currentPage == 0) {
                datas.clear();
            }
            datas.addAll(response.getReturnValue().getData());
            setEmpty(datas);
            rrv_select_account.notifyData();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_account);
        init();
        requestModelName();
    }

    private void init() {
        //处理一下传递过来的数据
        String att = getIntent().getStringExtra("att");
        final String id = getIntent().getStringExtra("id");
        Gson gson = new Gson();
        ResModeValue c = gson.fromJson(att,ResModeValue.class);
        modelName = c.getToBmModelName();
        bean = c.getConfigValueJson();


        loadingView = LoadingView.getLoadingView(this);
        loadingView.show();
        mt_title = (MyTitle) findViewById(R.id.mt_select_account);
        sv_select_account = (SeachView) findViewById(R.id.sv_select_account);
        tv_select_account_total = (TextView) findViewById(R.id.tv_select_account_total);
        srl_select_account = (SwipeRefreshLayout) findViewById(R.id.srl_select_account);
        rrv_select_account = (RefreshRecyclerView) findViewById(R.id.rrv_select_account);
        ev_select_account = (EmptyView) findViewById(R.id.ev_select_account);
        /*设置标题*/
        mt_title.setBack(true, this);
        mt_title.setTitle("选择台账");
        /*设置刷新和列表*/
        SwipeRefreshUtil.setConfig(srl_select_account, this);
        SwipeRefreshUtil.setRecyclerConfig(rrv_select_account, this, this);
        /*设置空页面*/
        ev_select_account.setData(R.mipmap.home, getResources().getString(R.string.empty_text));
        ev_select_account.setOnRefreshListener(listener);
        /*设置适配器*/
        SelectAccountAdapter adapter = new SelectAccountAdapter(this, datas);
        rrv_select_account.setAdapter(adapter);
        /*设置搜索*/
        sv_select_account.setISearchViewListener(this);
        sv_select_account.setHint("请输入关键字");
        /*item点击事件*/
        adapter.setOnIContractInfo(new SelectAccountAdapter.IContractInfo() {
            @Override
            public void setOnClick(int position) {
                WorkOrderDataManager.getManager().setSingleDateById(id,datas.get(position).get("OBJECTID"));
                WorkOrderDataManager.getManager().setResModelValueByFromOrTo(bean,datas.get(position));
                finish();
            }
        });
    }

    private void setEmpty(List list) {
        if (list.size() > 0) {
            ev_select_account.setVisibility(View.GONE);
            srl_select_account.setVisibility(View.VISIBLE);
        } else {
            ev_select_account.setVisibility(View.VISIBLE);
            srl_select_account.setVisibility(View.GONE);
        }
    }

    /**
     * 请求网络
     *
     * @param param
     */
    private void request(List<String> param) {
        RequestYxyw.queryData(param, callback);
    }

    private void requestModelName() {
        List<String> list = new ArrayList<>();
        list.add(modelName);
        RequestCi.queryModelByBmModelName(list, new RequestTask.ResultCallback<QueryModelByBmModelNameResponse>() {

            @Override
            public void onError(Exception e) {

            }

            @Override
            public void onResponse(QueryModelByBmModelNameResponse response) {
                attrDefineList = response.getReturnValue().getAttrDefineList();
                str_key = getKey(attrDefineList);
                requestFirst();
            }
        });
    }

    /**
     * 请求第一页数据
     */
    private void requestFirst() {
        currentPage = 0;
        rrv_select_account.setLoadMoreEnable(true);
        List<String> list = new ArrayList<>();
        /*参数1-业务模型编码*/
        list.add(modelName);
        /*参数2-数据状态,0 表示有效,1表示无效,-1或者空表示全部,为整型*/
        list.add("0");
        /*参数3-查询对象集合*/
        List<ContractInfoFindParamBean> been = new ArrayList<>();
//        ContractInfoFindParamBean bean1 = new ContractInfoFindParamBean();
//        bean1.setConnector("like");
//        bean1.setDmAttrName("CUST_NAME");
//        bean1.setValue(key);
//        ContractInfoFindParamBean bean2 = new ContractInfoFindParamBean();
//        bean2.setConnector("like");
//        bean2.setDmAttrName("PROJ_NAME");
//        bean2.setValue(key);
//        been.add(bean1);
//        been.add(bean2);
        ContractInfoFindParamBean bean1 = new ContractInfoFindParamBean();
        bean1.setConnector("like");
        bean1.setDmAttrName(str_key);
        bean1.setValue(key);
        been.add(bean1);
        Gson gson = new Gson();
        String findStr = gson.toJson(been);
        list.add(findStr);
        /*参数4-排序对象集合*/
        List<ContractInfoSoreParamBean> been1 = new ArrayList<>();
        String soreStr = gson.toJson(been1);
        list.add(soreStr);
        list.add(currentPage + "");
        list.add("10");
        list.add(MySharedpreferences.getUser().getUsername());
        request(list);
    }

    /**
     * 请求下一页数据
     */
    private void requestNext() {
        currentPage += 10;
        if (currentPage > total) {
            Toast.makeText(SelectAccountActivity.this, "已经没有更多数据了", Toast.LENGTH_SHORT).show();
            rrv_select_account.setLoadMoreEnable(false);
            return;
        }
        List<String> list = new ArrayList<>();
        /*参数1-业务模型编码*/
        list.add(modelName);
        /*参数2-数据状态,0 表示有效,1表示无效,-1或者空表示全部,为整型*/
        list.add("");
        /*参数3-查询对象集合*/
        List<ContractInfoFindParamBean> been = new ArrayList<>();
//        ContractInfoFindParamBean bean1 = new ContractInfoFindParamBean();
//        bean1.setConnector("like");
//        bean1.setDmAttrName("CUST_NAME");
//        bean1.setValue(key);
//        ContractInfoFindParamBean bean2 = new ContractInfoFindParamBean();
//        bean2.setConnector("like");
//        bean2.setDmAttrName("PROJ_NAME");
//        bean2.setValue(key);
//        been.add(bean1);
//        been.add(bean2);
        ContractInfoFindParamBean bean1 = new ContractInfoFindParamBean();
        bean1.setConnector("like");
        bean1.setDmAttrName(str_key);
        bean1.setValue(key);
        been.add(bean1);
        Gson gson = new Gson();
        String findStr = gson.toJson(been);
        list.add(findStr);
        /*参数4-排序对象集合*/
        List<ContractInfoSoreParamBean> been1 = new ArrayList<>();
        String soreStr = gson.toJson(been1);
        list.add(soreStr);
        list.add(currentPage + "");
        list.add("10");
        list.add(MySharedpreferences.getUser().getUsername());
        request(list);
    }

    @Override
    public void loadMoreListener() {
        requestNext();
    }

    @Override
    public void onRefresh() {
        requestFirst();
    }

    /**
     * 空页面刷新
     */
    private EmptyView.IRefreshListener listener = new EmptyView.IRefreshListener() {
        @Override
        public void onRefresh() {
            requestFirst();
        }
    };

    @Override
    public void setOnSearchListener(String key) {
        this.key = key;
        requestFirst();
    }

    @Override
    public void setOnCancelListener() {
        this.key = "";
        requestFirst();
    }
    public String getKey(List<QueryModelByBmModelNameResponse.ReturnValueBean.AttrDefineListBean> attrDefineList) {
        for (QueryModelByBmModelNameResponse.ReturnValueBean.AttrDefineListBean bean : attrDefineList) {
            if (bean.isHasBusinessKey()) {
                return bean.getDmAttrName();
            }
        }
        return null;
    }
    public String getKey(){
        return str_key;
    }
}
