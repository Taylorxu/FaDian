package com.powerge.wise.powerge.operationProjo.net.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.powerge.wise.powerge.operationProjo.net.BaseActivity;
import com.powerge.wise.powerge.operationProjo.net.adapter.SelectAccountAdapter;
import com.powerge.wise.powerge.operationProjo.net.bean.ConfigureBean;
import com.powerge.wise.powerge.operationProjo.net.bean.ContractInfoFindParamBean;
import com.powerge.wise.powerge.operationProjo.net.bean.ContractInfoSoreParamBean;
import com.powerge.wise.powerge.operationProjo.net.bean.ResModeValue;
import com.powerge.wise.powerge.operationProjo.net.db.MySharedpreferences;
import com.powerge.wise.powerge.operationProjo.net.net.RequestTask;
import com.powerge.wise.powerge.operationProjo.net.net.request.RequestCi;
import com.powerge.wise.powerge.operationProjo.net.net.request.RequestYxyw;
import com.powerge.wise.powerge.operationProjo.net.net.response.QueryDataResponse;
import com.powerge.wise.powerge.operationProjo.net.net.response.QueryModelByBmModelNameResponse;
import com.powerge.wise.powerge.operationProjo.net.utils.SwipeRefreshUtil;
import com.powerge.wise.powerge.operationProjo.net.view.mview.EmptyView;
import com.powerge.wise.powerge.operationProjo.net.view.mview.LoadingView;
import com.powerge.wise.powerge.operationProjo.net.view.mview.MyTitle;
import com.powerge.wise.powerge.operationProjo.net.view.mview.RefreshRecyclerView;
import com.powerge.wise.powerge.operationProjo.net.view.mview.SeachView;
import com.powerge.wise.powerge.operationProjo.net.view.mview.WorkOrderDataManager;
import com.wisesignsoft.OperationManagement.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SelectAccountActivity4 extends BaseActivity implements RefreshRecyclerView.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, SeachView.ISearchView {
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
    private List<ResModeValue.ConfigValueJsonBean> bean;
    private String calssName;
    private String dmAttrName;
    private String id;
    private String str_key;

    public static void startSelf(Context context,String calssName,String dmAttrName,String id) {
        Intent intent = new Intent(context, SelectAccountActivity4.class);
        intent.putExtra("calssName",calssName);
        intent.putExtra("dmAttrName",dmAttrName);
        intent.putExtra("id",id);
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
        calssName = getIntent().getStringExtra("calssName");
        dmAttrName = getIntent().getStringExtra("dmAttrName");
        id = getIntent().getStringExtra("id");

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
                Map<String,String> allMap = datas.get(position);
                Map<String,ConfigureBean> map = new HashMap<String, ConfigureBean>();
                ConfigureBean bean = new ConfigureBean();
                bean.setOBJECTID(allMap.get("OBJECTID"));
                bean.setBmModelName(calssName);
                bean.setTextValue(allMap.get("NAME"));
                map.put(dmAttrName,bean);
                Gson gson = new Gson();
                String json = gson.toJson(map);
                WorkOrderDataManager.getManager().setSingleDateById(id,json);
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
        list.add(calssName);
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
        list.add(calssName);
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
        list.add(MySharedpreferences.getUser().getName());
        request(list);
    }

    /**
     * 请求下一页数据
     */
    private void requestNext() {
        currentPage += 10;
        if (currentPage > total) {
            Toast.makeText(SelectAccountActivity4.this, "已经没有更多数据了", Toast.LENGTH_SHORT).show();
            rrv_select_account.setLoadMoreEnable(false);
            return;
        }
        List<String> list = new ArrayList<>();
        /*参数1-业务模型编码*/
        list.add(calssName);
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
        list.add(MySharedpreferences.getUser().getName());
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
    private String getKey(List<QueryModelByBmModelNameResponse.ReturnValueBean.AttrDefineListBean> attrDefineList) {
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
