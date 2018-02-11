package com.wisesignsoft.OperationManagement.ui.activity;

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
import com.wisesignsoft.OperationManagement.adapter.SolvingAdapter;
import com.wisesignsoft.OperationManagement.db.MySharedpreferences;
import com.wisesignsoft.OperationManagement.net.RequestTask;
import com.wisesignsoft.OperationManagement.net.request.RequestProcess;
import com.wisesignsoft.OperationManagement.net.request.RequestYxyw;
import com.wisesignsoft.OperationManagement.net.response.BaseResponse;
import com.wisesignsoft.OperationManagement.net.response.FindUnHandleProcessResponse;
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

public class SolvingActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, RefreshRecyclerView.OnLoadMoreListener, SeachView.ISearchView {

    private MyTitle mt_solving;
    private SeachView sv_solving;
    private SwipeRefreshLayout srl_solving;
    private RefreshRecyclerView rrv_solving;
    /*数据的当前页面*/
    private int currentPage = 0;
    /*搜索关键词*/
    private String key = "";
    /*总数*/
    private String total;
    /*用于跟适配器绑定的数据*/
    private List<FindUnHandleProcessResponse.DataBean> datas = new ArrayList<>();
    /*最初的数据*/
    private List<FindUnHandleProcessResponse.DataBean> datas1 = new ArrayList<>();
    /*搜索后的数据*/
    private List<FindUnHandleProcessResponse.DataBean> datas2 = new ArrayList<>();
    private RequestTask.ResultCallback<FindUnHandleProcessResponse> callback = new RequestTask.ResultCallback<FindUnHandleProcessResponse>() {
        @Override
        public void onError(Exception e) {
            if (srl_solving.isRefreshing()) {
                srl_solving.setRefreshing(false);
            }
            loadingView.stop(loadingView);
        }

        @Override
        public void onResponse(FindUnHandleProcessResponse response) {
            loadingView.stop(loadingView);
            total = response.getTotal();
            if (srl_solving.isRefreshing()) {
                srl_solving.setRefreshing(false);
            }
            if (currentPage == 0) {
                datas.clear();
                datas1.clear();
                datas2.clear();
            }
            tv_total.setText("共"+total+"条数据");
            datas.addAll(response.getData());
            setEmpty(datas);
            rrv_solving.notifyData();
        }
    };
    private EmptyView ev_solving;
    private LoadingView loadingView;
    private TextView tv_total;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solving);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestFirst();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        loadingView = LoadingView.getLoadingView(this);
        loadingView.show();
        mt_solving = (MyTitle) findViewById(R.id.mt_solving);
        sv_solving = (SeachView) findViewById(R.id.sv_solving);
        srl_solving = (SwipeRefreshLayout) findViewById(R.id.srl_solving);
        rrv_solving = (RefreshRecyclerView) findViewById(R.id.rrv_solving);
        ev_solving = (EmptyView) findViewById(R.id.ev_solving);
        tv_total = (TextView) findViewById(R.id.tv_total);
        /*设置标题*/
        mt_solving.setBack(true, this);
        mt_solving.setTitle(getResources().getString(R.string.my_solving));
        /*设置刷新和列表*/
        SwipeRefreshUtil.setConfig(srl_solving, this);
        SwipeRefreshUtil.setRecyclerConfig(rrv_solving, this, this);
        /*设置空页面*/
        ev_solving.setData(R.mipmap.home, getResources().getString(R.string.empty_text));
        ev_solving.setOnRefreshListener(listener);
        /*设置适配器*/
        SolvingAdapter adapter = new SolvingAdapter(this, datas);
        rrv_solving.setAdapter(adapter);
        /*设置搜索*/
        sv_solving.setISearchViewListener(this);
        sv_solving.setHint("请输入标题或工单号");
        adapter.setOnISolving(new SolvingAdapter.ISolving() {
            @Override
            public void setOnClick(String currentDealer, String pikey) {
                OrderSolvedActivity.startSelf(SolvingActivity.this, currentDealer, pikey);
            }
        });
    }

    private void setEmpty(List list) {
        if (list.size() > 0) {
            ev_solving.setVisibility(View.GONE);
            srl_solving.setVisibility(View.VISIBLE);
        } else {
            ev_solving.setVisibility(View.VISIBLE);
            srl_solving.setVisibility(View.GONE);
        }
    }

    /**
     * 请求网络
     *
     * @param param
     */
    private void request(List<String> param) {
        RequestYxyw.findUnhandleProcess(param, callback);
    }

    /**
     * 请求第一页数据
     */
    private void requestFirst() {
        currentPage = 0;
        rrv_solving.setLoadMoreEnable(true);
        List<String> list = new ArrayList<>();
        list.add(key);
        list.add(MySharedpreferences.getUser().getUsername());
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
            Toast.makeText(SolvingActivity.this, "已经没有更多数据了", Toast.LENGTH_SHORT).show();
            rrv_solving.setLoadMoreEnable(false);
            return;
        }
        List<String> list = new ArrayList<>();
        list.add(key);
        list.add(MySharedpreferences.getUser().getUsername());
        list.add(currentPage + "");
        list.add("10");
        request(list);
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
    public void loadMoreListener() {
        requestNext();
    }

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
}
