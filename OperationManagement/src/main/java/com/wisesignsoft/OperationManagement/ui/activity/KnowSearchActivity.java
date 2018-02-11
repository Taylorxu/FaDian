package com.wisesignsoft.OperationManagement.ui.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wisesignsoft.OperationManagement.BaseActivity;
import com.wisesignsoft.OperationManagement.R;
import com.wisesignsoft.OperationManagement.adapter.KnowSearchAdapter;
import com.wisesignsoft.OperationManagement.adapter.ServiceReportAdapter;
import com.wisesignsoft.OperationManagement.db.MySharedpreferences;
import com.wisesignsoft.OperationManagement.net.RequestTask;
import com.wisesignsoft.OperationManagement.net.request.RequestCi;
import com.wisesignsoft.OperationManagement.net.request.RequestKnow;
import com.wisesignsoft.OperationManagement.net.response.FindKnowledgeListResponse;
import com.wisesignsoft.OperationManagement.net.response.QueryCiModelResponse;
import com.wisesignsoft.OperationManagement.net.response.QueryModelByBmModelNameResponse;
import com.wisesignsoft.OperationManagement.utils.SwipeRefreshUtil;
import com.wisesignsoft.OperationManagement.view.mview.EmptyView;
import com.wisesignsoft.OperationManagement.view.mview.LoadingView;
import com.wisesignsoft.OperationManagement.view.mview.MyTitle;
import com.wisesignsoft.OperationManagement.view.mview.RefreshRecyclerView;
import com.wisesignsoft.OperationManagement.view.mview.SeachView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class KnowSearchActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, RefreshRecyclerView.OnLoadMoreListener, SeachView.ISearchView {

    private MyTitle mt_know_search;
    private SeachView sv_know_search;
    private TextView tv_know_ssearch;
    private SwipeRefreshLayout srl_know_search;
    private RefreshRecyclerView rrv_know_search;
    private EmptyView ev_know_search;

    /*数据的当前页面*/
    private int currentPage = 0;
    /*搜索关键词*/
    private String key = "";
    /*总数*/
    private String total;
    private LoadingView loadingView;
    private List<Map<String, String>> datas = new ArrayList<>();
    private String keyParam;
    private List<QueryModelByBmModelNameResponse.ReturnValueBean.AttrDefineListBean> attrDefineList;
    private String str_key;

    private RequestTask.ResultCallback<FindKnowledgeListResponse> callback = new RequestTask.ResultCallback<FindKnowledgeListResponse>() {
        @Override
        public void onError(Exception e) {
            if (srl_know_search.isRefreshing()) {
                srl_know_search.setRefreshing(false);
            }
            loadingView.stop(loadingView);
        }

        @Override
        public void onResponse(FindKnowledgeListResponse response) {
            loadingView.stop(loadingView);
            total = response.getTotal();
            tv_know_ssearch.setText("总共有" + total + "条数据");
            if (srl_know_search.isRefreshing()) {
                srl_know_search.setRefreshing(false);
            }
            if (currentPage == 0) {
                datas.clear();
            }
            datas.addAll(response.getData());
            setEmpty(datas);
            rrv_know_search.notifyData();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_know_search);
        init();
        requestModelName();
        //ceshi();
    }

    private void ceshi() {
        List<String> list = new ArrayList<>();
        RequestCi.queryCiModel(list, new RequestTask.ResultCallback<QueryCiModelResponse>() {
            @Override
            public void onError(Exception e) {

            }

            @Override
            public void onResponse(QueryCiModelResponse response) {
                List<QueryCiModelResponse.ReturnValueBean> bean = response.getReturnValue();
                for (QueryCiModelResponse.ReturnValueBean b : bean) {
//                    b.getFields()
                }
            }
        });
    }

    private void init() {
        keyParam = getIntent().getStringExtra("key");
        if (TextUtils.isEmpty(keyParam)) {
            keyParam = "KNOWLEDGE";
        }
        loadingView = LoadingView.getLoadingView(this);
        loadingView.show();
        mt_know_search = (MyTitle) findViewById(R.id.mt_know_search);
        sv_know_search = (SeachView) findViewById(R.id.sv_know_search);
        tv_know_ssearch = (TextView) findViewById(R.id.tv_know_search);
        srl_know_search = (SwipeRefreshLayout) findViewById(R.id.srl_know_search);
        rrv_know_search = (RefreshRecyclerView) findViewById(R.id.rrv_know_search);
        ev_know_search = (EmptyView) findViewById(R.id.ev_know_search);
        /*设置标题*/
        mt_know_search.setBack(true, this);
        mt_know_search.setTitle("知识检索");
        /*设置刷新和列表*/
        SwipeRefreshUtil.setConfig(srl_know_search, this);
        SwipeRefreshUtil.setRecyclerConfig(rrv_know_search, this, this);
        /*设置空页面*/
        ev_know_search.setData(R.mipmap.home, getResources().getString(R.string.empty_text));
        ev_know_search.setOnRefreshListener(listener);
        /*设置适配器*/
        KnowSearchAdapter adapter = new KnowSearchAdapter(this, datas);
        rrv_know_search.setAdapter(adapter);
        /*设置搜索*/
        sv_know_search.setISearchViewListener(this);
        sv_know_search.setHint("请输入知识项的信息");
        /*item点击事件*/
        adapter.setOnIKnowReport(new KnowSearchAdapter.IKnowReport() {
            @Override
            public void setOnClick(int position) {
                Map<String, String> bean = datas.get(position);
                String id = bean.get("OBJECTID");
                String title = bean.get("K_TITLE");
                String name = bean.get("K_SUBMIT_USER");
                String time = bean.get("K_SUBMIT_TIME");
                KnowDetailActivity.startSelf(KnowSearchActivity.this, id, keyParam, title, name, time);
            }
        });
    }

    private void setEmpty(List list) {
        if (list.size() > 0) {
            ev_know_search.setVisibility(View.GONE);
            srl_know_search.setVisibility(View.VISIBLE);
        } else {
            ev_know_search.setVisibility(View.VISIBLE);
            srl_know_search.setVisibility(View.GONE);
        }
    }

    /**
     * 请求网络
     *
     * @param param
     */
    private void request(List<String> param) {
        RequestKnow.findKnowledgeList(param, callback);
    }

    /**
     * 请求第一页数据
     */
    private void requestFirst() {
        currentPage = 0;
        rrv_know_search.setLoadMoreEnable(true);
        List<String> list = new ArrayList<>();
        list.add(MySharedpreferences.getUser().getUsername());
        list.add(keyParam);
        list.add(key);
        list.add(currentPage + "");
        list.add("10");
        request(list);
    }

    /**
     * 请求下一页数据
     */
    private void requestNext() {
        currentPage += 10;
        if (currentPage > Integer.parseInt(total)) {
            Toast.makeText(KnowSearchActivity.this, "已经没有更多数据了", Toast.LENGTH_SHORT).show();
            rrv_know_search.setLoadMoreEnable(false);
            return;
        }
        List<String> list = new ArrayList<>();
        list.add(MySharedpreferences.getUser().getUsername());
        list.add(keyParam);
        list.add(key);
        list.add(currentPage + "");
        list.add("10");
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
    public String getKey() {
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
}
