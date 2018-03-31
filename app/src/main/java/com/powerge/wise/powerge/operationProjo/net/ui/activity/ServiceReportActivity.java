
package com.powerge.wise.powerge.operationProjo.net.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.operationProjo.net.BaseActivity;
import com.powerge.wise.powerge.operationProjo.net.adapter.ServiceReportAdapter;
import com.powerge.wise.powerge.operationProjo.net.bean.ContractInfoFindParamBean;
import com.powerge.wise.powerge.operationProjo.net.bean.ContractInfoSoreParamBean;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ServiceReportActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, RefreshRecyclerView.OnLoadMoreListener, SeachView.ISearchView {

    private MyTitle mt_service;
    private SeachView sv_service;
    private RecyclerView rv_service;
    private EditText et;
    /*数据的当前页面*/
    private int currentPage = 0;
    /*搜索关键词*/
    private String key = "";
    /*总数*/
    private int total;
    private List<Map<String, String>> datas = new ArrayList<>();
    private String keyParam;
    private List<QueryModelByBmModelNameResponse.ReturnValueBean.AttrDefineListBean> attrDefineList;
    private String str_key;

    private RequestTask.ResultCallback<QueryDataResponse> callback = new RequestTask.ResultCallback<QueryDataResponse>() {
        @Override
        public void onError(Exception e) {
            if (srl_service_report.isRefreshing()) {
                srl_service_report.setRefreshing(false);
            }
            loadingView.stop(loadingView);
        }

        @Override
        public void onResponse(QueryDataResponse response) {
            loadingView.stop(loadingView);
            total = response.getReturnValue().getTotal();
            if (srl_service_report.isRefreshing()) {
                srl_service_report.setRefreshing(false);
            }
            if (currentPage == 0) {
                datas.clear();
            }
            datas.addAll(response.getReturnValue().getData());
            setEmpty(datas);
            rrv_service_report.notifyData();
        }
    };
    private LoadingView loadingView;
    private SwipeRefreshLayout srl_service_report;
    private RefreshRecyclerView rrv_service_report;
    private EmptyView ev_service_report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_report);
        init();
        requestModelName();
//        requestFirst();
    }

    private void init() {
        keyParam = getIntent().getStringExtra("key");
        if (TextUtils.isEmpty(keyParam)) {
            keyParam = "res";
        }
        loadingView = LoadingView.getLoadingView(this);
        loadingView.show();
        mt_service = (MyTitle) findViewById(R.id.mt_service_report);
        sv_service = (SeachView) findViewById(R.id.sv_service_report);
        srl_service_report = (SwipeRefreshLayout) findViewById(R.id.srl_service_report);
        rrv_service_report = (RefreshRecyclerView) findViewById(R.id.rrv_service_report);
        ev_service_report = (EmptyView) findViewById(R.id.ev_service_report);
        /*设置标题*/
        mt_service.setBack(true, this);
        mt_service.setTitle(getResources().getString(R.string.service_report));
        mt_service.setTvRight(true, "添加", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateServiceActivity.startSelf(ServiceReportActivity.this, keyParam);
            }
        });
        /*设置刷新和列表*/
        SwipeRefreshUtil.setConfig(srl_service_report, this);
        SwipeRefreshUtil.setRecyclerConfig(rrv_service_report, this, this);
        /*设置空页面*/
        ev_service_report.setData(R.mipmap.gap, getResources().getString(R.string.empty_text));
        ev_service_report.setOnRefreshListener(listener);
        /*设置适配器*/
        ServiceReportAdapter adapter = new ServiceReportAdapter(this, datas);
        rrv_service_report.setAdapter(adapter);
        /*设置搜索*/
        sv_service.setISearchViewListener(this);
        sv_service.setHint("请输入服务报告、项目或客户的名称");
        /*item点击事件*/
        adapter.setOnIServiceReport(new ServiceReportAdapter.IServiceReport() {
            @Override
            public void setOnClick(int i) {
                Map<String, String> bean = datas.get(i);
                String id = bean.get("OBJECTID");
                ServiceDetailActivity.startSelf(ServiceReportActivity.this, id, keyParam);
            }
        });
    }

    private void setEmpty(List list) {
        if (list.size() > 0) {
            ev_service_report.setVisibility(View.GONE);
            srl_service_report.setVisibility(View.VISIBLE);
        } else {
            ev_service_report.setVisibility(View.VISIBLE);
            srl_service_report.setVisibility(View.GONE);
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
        list.add(keyParam);
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
        rrv_service_report.setLoadMoreEnable(true);
        List<String> list = new ArrayList<>();
        /*参数1-业务模型编码*/
        list.add(keyParam);
        /*参数2-数据状态,0 表示有效,1表示无效,-1或者空表示全部,为整型*/
        list.add("0");
        /*参数3-查询对象集合*/
        List<ContractInfoFindParamBean> been = new ArrayList<>();
//        ContractInfoFindParamBean bean1 = new ContractInfoFindParamBean();
//        bean1.setConnector("like");
//        bean1.setDmAttrName("RES_RENA");
//        bean1.setValue(key);
//        ContractInfoFindParamBean bean2 = new ContractInfoFindParamBean();
//        bean2.setConnector("like");
//        bean2.setDmAttrName("PROJ_NAME");
//        bean2.setValue(key);
//        ContractInfoFindParamBean bean3 = new ContractInfoFindParamBean();
//        bean3.setConnector("like");
//        bean3.setDmAttrName("CUST_NAME");
//        bean3.setValue(key);
//        been.add(bean1);
//        been.add(bean2);
//        been.add(bean3);
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
        ContractInfoSoreParamBean bean = new ContractInfoSoreParamBean();
        bean.setDmAttrName("LAST_MODIFIED_TIME");
        bean.setOrderType(1);
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
            Toast.makeText(ServiceReportActivity.this, "已经没有更多数据了", Toast.LENGTH_SHORT).show();
            rrv_service_report.setLoadMoreEnable(false);
            return;
        }
        List<String> list = new ArrayList<>();
        /*参数1-业务模型编码*/
        list.add(keyParam);
        /*参数2-数据状态,0 表示有效,1表示无效,-1或者空表示全部,为整型*/
        list.add("0");
        /*参数3-查询对象集合*/
        List<ContractInfoFindParamBean> been = new ArrayList<>();
//        ContractInfoFindParamBean bean1 = new ContractInfoFindParamBean();
//        bean1.setConnector("like");
//        bean1.setDmAttrName("RES_RENA");
//        bean1.setValue(key);
//        ContractInfoFindParamBean bean2 = new ContractInfoFindParamBean();
//        bean2.setConnector("like");
//        bean2.setDmAttrName("PROJ_NAME");
//        bean2.setValue(key);
//        ContractInfoFindParamBean bean3 = new ContractInfoFindParamBean();
//        bean3.setConnector("like");
//        bean3.setDmAttrName("CUST_NAME");
//        bean3.setValue(key);
//        been.add(bean1);
//        been.add(bean2);
//        been.add(bean3);
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
        ContractInfoSoreParamBean bean = new ContractInfoSoreParamBean();
        bean.setDmAttrName("LAST_MODIFIED_TIME");
        bean.setOrderType(1);
        been1.add(bean);
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

    public String getKey() {
        return str_key;
    }
}
