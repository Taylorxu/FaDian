package com.powerge.wise.powerge.operationProjo.net.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.powerge.wise.powerge.operationProjo.net.BaseActivity;
import com.powerge.wise.powerge.operationProjo.net.adapter.SelectAccountAdapter3;
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
import com.powerge.wise.powerge.operationProjo.net.view.mview.WorkOrderDataManager;
import com.wisesignsoft.OperationManagement.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class SelectAccountActivity3 extends BaseActivity implements RefreshRecyclerView.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, SeachView.ISearchView {
    private MyTitle mt_title;
    private SeachView sv_select_account;
    private TextView tv_select_account_total;
    private SwipeRefreshLayout srl_select_account;
    private LoadingView loadingView;
    private RefreshRecyclerView rrv_select_account;
    private EmptyView ev_select_account;
    /*数据的当前页面*/
    private int currentPage = 0;
    /*搜索关键词*/
    private String key = "";
    /*总数*/
    private int total;
    private List<QueryModelByBmModelNameResponse.ReturnValueBean.AttrDefineListBean> attrDefineList;
    private String str_key;

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
            solvedData(value);
            initData();
        }
    };
    private String id;
    private String value;
    /*总的数据*/
    private List<Map<String, String>> datas = new ArrayList<>();
    /*传递过来的数据*/
    private List<Map<String, String>> beforeData = new ArrayList<>();
    /*上送的数据*/
    private List<Map<String, String>> behindData = new ArrayList<>();
    private String att;
    private String dmAttrName;

    public static void startSelf(Context context, String value, String id, String key,String dmAttrName) {
        Intent intent = new Intent(context, SelectAccountActivity3.class);
        intent.putExtra("value", value);
        intent.putExtra("id", id);
        intent.putExtra("key", key);
        intent.putExtra("dmAttrName",dmAttrName);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_account);
        init();
        requestModelName();
    }

    private void init() {
        /*处理传递过来的数据*/
        id = getIntent().getStringExtra("id");
        value = getIntent().getStringExtra("value");
        att = getIntent().getStringExtra("key");
        dmAttrName = getIntent().getStringExtra("dmAttrName");

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
        mt_title.setTvRight(true, "确定", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (behindData != null) {
                    StringBuffer sb = new StringBuffer();
                    for (int i = 0; i < behindData.size(); i++) {
                        String id = behindData.get(i).get("OBJECTID");
                        if (i == behindData.size() - 1) {
                            sb.append(id);
                        }else {
                            sb.append(id).append("");
                        }
                    }
                    WorkOrderDataManager.getManager().setSingleDateById(id, sb.toString());
                    WorkOrderDataManager.getManager().setResModelSelect3(dmAttrName,behindData);
                    finish();
                }
            }
        });
        /*设置刷新和列表*/
        SwipeRefreshUtil.setConfig(srl_select_account, this);
        SwipeRefreshUtil.setRecyclerConfig(rrv_select_account, this, this);
        /*设置空页面*/
        ev_select_account.setData(R.mipmap.home, getResources().getString(R.string.empty_text));
        ev_select_account.setOnRefreshListener(listener);
        /*设置适配器*/
        SelectAccountAdapter3 adapter = new SelectAccountAdapter3(this, datas);
        rrv_select_account.setAdapter(adapter);
        /*设置搜索*/
        sv_select_account.setISearchViewListener(this);
        sv_select_account.setHint("请输入关键字");
        /*item点击事件*/
        adapter.setOnIContractInfo(new SelectAccountAdapter3.IContractInfo() {
            @Override
            public void setOnClick(int position) {
                Map<String, String> map = datas.get(position);
                if ("true".equals(map.get("isSelect"))) {
                    behindData.add(map);
                } else {
                    behindData.remove(map);
                }
            }
        });
    }

    /**
     * 处理数据
     */
    private void solvedData(String ids) {
        if (TextUtils.isEmpty(value)) {
            return;
        }
        String[] strs = ids.split(",");
        for (Map<String, String> map : datas) {
            String id = map.get("OBJECTID");
            map.put("isSelect", "false");
            for (String id1 : strs) {
                if (id.equals(id1)) {
                    map.put("isSelect", "true");
                    beforeData.add(map);
                }
            }
        }
        behindData.addAll(beforeData);
    }

    private void initData() {
        for (Map<String, String> map : datas) {
            map.put("isSelect", "false");
            String id = map.get("OBJECTID");
            for (Map<String, String> map1 : beforeData) {
                String id1 = map1.get("OBJECTID");
                if (id.equals(id1)) {
                    map.put("isSelect", "true");
                }
            }
        }
        rrv_select_account.notifyData();
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

    /**
     * 请求第一页数据
     */
    private void requestFirst() {
        currentPage = 0;
        rrv_select_account.setLoadMoreEnable(true);
        List<String> list = new ArrayList<>();
        /*参数1-业务模型编码*/
        list.add(att);
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

    /**
     * 请求下一页数据
     */
    private void requestNext() {
        currentPage += 10;
        if (currentPage > total) {
            Toast.makeText(SelectAccountActivity3.this, "已经没有更多数据了", Toast.LENGTH_SHORT).show();
            rrv_select_account.setLoadMoreEnable(false);
            return;
        }
        List<String> list = new ArrayList<>();
        /*参数1-业务模型编码*/
        list.add(att);
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
    private void requestModelName() {
        List<String> list = new ArrayList<>();
        list.add(att);
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
    public String getKey(){
        return str_key;
    }
    private String getKey(List<QueryModelByBmModelNameResponse.ReturnValueBean.AttrDefineListBean> attrDefineList) {
        for (QueryModelByBmModelNameResponse.ReturnValueBean.AttrDefineListBean bean : attrDefineList) {
            if (bean.isHasBusinessKey()) {
                return bean.getDmAttrName();
            }
        }
        return null;
    }
}
