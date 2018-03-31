package com.powerge.wise.powerge.operationProjo.net.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.operationProjo.net.BaseActivity;
import com.powerge.wise.powerge.operationProjo.net.adapter.ContractInfoAdapter;
import com.powerge.wise.powerge.operationProjo.net.bean.ContractBean;
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


public class ContractInfoActivity extends BaseActivity implements RefreshRecyclerView.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, SeachView.ISearchView {

    private MyTitle mt_title;
    private SeachView sv_contract_info;
    private TextView tv_contract_info;
    private SwipeRefreshLayout srl_contract_info;
    private LoadingView loadingView;
    private RefreshRecyclerView rrv_contract_info;
    private EmptyView ev_contract_info;

    /*数据的当前页面*/
    private int currentPage = 0;
    /*搜索关键词*/
    private String key = "";
    /*总数*/
    private int total;
    private List<Map<String, String>> datas = new ArrayList<>();
    private String keyParam;
    /*客户信息*/
    private List<ContractBean> lists = new ArrayList<>();
    /*非转包客户*/
    private List<ContractBean> lists2 = new ArrayList<>();
    private RequestTask.ResultCallback<QueryDataResponse> callback = new RequestTask.ResultCallback<QueryDataResponse>() {
        @Override
        public void onError(Exception e) {
            if (srl_contract_info.isRefreshing()) {
                srl_contract_info.setRefreshing(false);
            }
            loadingView.stop(loadingView);
        }

        @Override
        public void onResponse(QueryDataResponse response) {
            loadingView.stop(loadingView);
            total = response.getReturnValue().getTotal();
            tv_contract_info.setText("共有" + total + "条数据");
            if (srl_contract_info.isRefreshing()) {
                srl_contract_info.setRefreshing(false);
            }
            if (currentPage == 0) {
                datas.clear();
            }
            datas.addAll(response.getReturnValue().getData());
            setEmpty(datas);
            rrv_contract_info.notifyData();
        }
    };
    private List<QueryModelByBmModelNameResponse.ReturnValueBean.AttrDefineListBean> attrDefineList;
    private String str_key;
    private ContractInfoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract_info);
        init();
        requestModelName();
    }

    private void init() {
        //获取传递过来的数据
        keyParam = getIntent().getStringExtra("key");
        if (TextUtils.isEmpty(keyParam)) {
            keyParam = "CONTRACTS";
        }
        loadingView = LoadingView.getLoadingView(this);
        loadingView.show();
        mt_title = (MyTitle) findViewById(R.id.mt_contract_info);
        sv_contract_info = (SeachView) findViewById(R.id.sv_contract_info);
        tv_contract_info = (TextView) findViewById(R.id.tv_contract_info);
        srl_contract_info = (SwipeRefreshLayout) findViewById(R.id.srl_contract_info);
        rrv_contract_info = (RefreshRecyclerView) findViewById(R.id.rrv_contract_info);
        ev_contract_info = (EmptyView) findViewById(R.id.ev_contract_info);
        /*设置标题*/
        mt_title.setBack(true, this);
        mt_title.setTitle(getResources().getString(R.string.contract_info));
        /*设置刷新和列表*/
        SwipeRefreshUtil.setConfig(srl_contract_info, this);
        SwipeRefreshUtil.setRecyclerConfig(rrv_contract_info, this, this);
        /*设置空页面*/
        ev_contract_info.setData(R.mipmap.gap, getResources().getString(R.string.empty_text));
        ev_contract_info.setOnRefreshListener(listener);
        /*设置适配器*/
        adapter = new ContractInfoAdapter(this, datas);
        rrv_contract_info.setAdapter(adapter);
        /*设置搜索*/
        sv_contract_info.setISearchViewListener(this);
        sv_contract_info.setHint("请输入客户名称或者项目名称");
        /*item点击事件*/
        adapter.setOnIContractInfo(new ContractInfoAdapter.IContractInfo() {
            @Override
            public void setOnClick(int i) {
                ContractDetailActivity.startSelf(ContractInfoActivity.this, datas.get(i), attrDefineList);
            }
        });
    }

    private void setEmpty(List list) {
        if (list.size() > 0) {
            ev_contract_info.setVisibility(View.GONE);
            srl_contract_info.setVisibility(View.VISIBLE);
        } else {
            ev_contract_info.setVisibility(View.VISIBLE);
            srl_contract_info.setVisibility(View.GONE);
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

    /**
     * 请求第一页数据
     */
    private void requestFirst() {
        currentPage = 0;
        rrv_contract_info.setLoadMoreEnable(true);
        List<String> list = new ArrayList<>();
        /*参数1-业务模型编码*/
        list.add(keyParam);
        /*参数2-数据状态,0 表示有效,1表示无效,-1或者空表示全部,为整型*/
        list.add("0");
        /*参数3-查询对象集合*/
        List<ContractInfoFindParamBean> been = new ArrayList<>();
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
            Toast.makeText(ContractInfoActivity.this, "已经没有更多数据了", Toast.LENGTH_SHORT).show();
            rrv_contract_info.setLoadMoreEnable(false);
            return;
        }
        List<String> list = new ArrayList<>();
        /*参数1-业务模型编码*/
        list.add(keyParam);
        /*参数2-数据状态,0 表示有效,1表示无效,-1或者空表示全部,为整型*/
        list.add("0");
        /*参数3-查询对象集合*/
        List<ContractInfoFindParamBean> been = new ArrayList<>();
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

    private String getKey(List<QueryModelByBmModelNameResponse.ReturnValueBean.AttrDefineListBean> attrDefineList) {
        for (QueryModelByBmModelNameResponse.ReturnValueBean.AttrDefineListBean bean : attrDefineList) {
            if (bean.isHasBusinessKey()) {
                return bean.getDmAttrName();
            }
        }
        return null;
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
    public String getKey(){
        return str_key;
    }
}
