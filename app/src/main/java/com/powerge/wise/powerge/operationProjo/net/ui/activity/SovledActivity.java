package com.powerge.wise.powerge.operationProjo.net.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.operationProjo.net.BaseActivity;
import com.powerge.wise.powerge.operationProjo.net.adapter.SovledAdapter;
import com.powerge.wise.powerge.operationProjo.net.db.MySharedpreferences;
import com.powerge.wise.powerge.operationProjo.net.net.RequestTask;
import com.powerge.wise.powerge.operationProjo.net.net.request.RequestYxyw;
import com.powerge.wise.powerge.operationProjo.net.net.response.FindUnHandleProcessResponse;
import com.powerge.wise.powerge.operationProjo.net.utils.SwipeRefreshUtil;
import com.powerge.wise.powerge.operationProjo.net.view.mview.EmptyView;
import com.powerge.wise.powerge.operationProjo.net.view.mview.LoadingView;
import com.powerge.wise.powerge.operationProjo.net.view.mview.MyTitle;
import com.powerge.wise.powerge.operationProjo.net.view.mview.RefreshRecyclerView;
import com.powerge.wise.powerge.operationProjo.net.view.mview.SeachView;

import java.util.ArrayList;
import java.util.List;

public class SovledActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, RefreshRecyclerView.OnLoadMoreListener, SeachView.ISearchView {

    private MyTitle mt_solved;
    private TextView tv_doing;
    private TextView tv_finish;
    private SeachView sv_solved;
    private RefreshRecyclerView rv_solved;
    /*正在进行中模式*/
    private static final int TYPE1 = 0;
    /*已完成模式*/
    private static final int TYPE2 = 1;
    /*当前模式,默认是进行中模式*/
    private int type = TYPE1;
    private SwipeRefreshLayout srl_solved;
    private EmptyView ev_solved;
    /*数据的当前页面*/
    private int currentPage = 0;
    /*搜索关键词*/
    private String key = "";
    /*总数*/
    private String total;
    private List<FindUnHandleProcessResponse.DataBean> datas = new ArrayList<>();
    private RequestTask.ResultCallback<FindUnHandleProcessResponse> callback = new RequestTask.ResultCallback<FindUnHandleProcessResponse>() {
        @Override
        public void onError(Exception e) {
            if (srl_solved.isRefreshing()) {
                srl_solved.setRefreshing(false);
            }
            loadingView.stop(loadingView);
        }

        @Override
        public void onResponse(FindUnHandleProcessResponse response) {
            loadingView.stop(loadingView);
            total = response.getTotal();
            if (srl_solved.isRefreshing()) {
                srl_solved.setRefreshing(false);
            }
            if (currentPage == 0) {
                datas.clear();
            }
            tv_total.setText("共"+response.getTotal()+"条数据");
            datas.addAll(response.getData());
            setEmpty(datas);
            rv_solved.notifyData();
        }
    };
    private LoadingView loadingView;
    private EditText et;
    private TextView tv_total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sovled);
        init();
    }

    private void init() {
        mt_solved = (MyTitle) findViewById(R.id.mt_sovled_title);
        tv_doing = (TextView) findViewById(R.id.tv_sovled_doing);
        tv_finish = (TextView) findViewById(R.id.tv_sovled_finish);
        sv_solved = (SeachView) findViewById(R.id.sv_sovled);
        srl_solved = (SwipeRefreshLayout) findViewById(R.id.srl_solved);
        rv_solved = (RefreshRecyclerView) findViewById(R.id.rv_solved);
        ev_solved = (EmptyView) findViewById(R.id.ev_solved);
        tv_total = (TextView) findViewById(R.id.tv_total);
        /*设置标题*/
        mt_solved.setBack(true, this);
        mt_solved.setTitle("已处理");
        /*设置刷新和列表*/
        SwipeRefreshUtil.setConfig(srl_solved, this);
        SwipeRefreshUtil.setRecyclerConfig(rv_solved, this, this);
         /*设置空页面*/
        ev_solved.setData(R.mipmap.gap, getResources().getString(R.string.empty_text));
        ev_solved.setOnRefreshListener(listener);
        /*设置适配器*/
        SovledAdapter adapter = new SovledAdapter(this, datas);
        rv_solved.setAdapter(adapter);
        adapter.setOnItemSolvedListener(new SovledAdapter.ISolvedItemClickListener() {
            @Override
            public void setOnItemSolvedClickListener(String key) {
                WorkOrderDetailActivity.startSelf(SovledActivity.this,key);
            }
        });
        /*设置页面点击切换*/
        tv_doing.setOnClickListener(this);
        tv_finish.setOnClickListener(this);
        /*默认选中进行中*/
        selectDoing();
        sv_solved.setISearchViewListener(this);
        sv_solved.setHint("请输入标题或工单号");
    }

    /*进行中模式*/
    private void selectDoing() {
        loadingView = LoadingView.getLoadingView(this);
        loadingView.show();
        type = TYPE1;
        tv_doing.setSelected(true);
        tv_finish.setSelected(false);
        initRequest();
        requestFirst();
    }

    /*已完成模式*/
    private void selectFinish() {
        loadingView = LoadingView.getLoadingView(this);
        loadingView.show();
        type = TYPE2;
        tv_doing.setSelected(false);
        tv_finish.setSelected(true);
        initRequest();
        requestFirst();
    }

    /*设置空页面*/
    private void setEmpty(List list) {
        if (list.size() > 0) {
            srl_solved.setVisibility(View.VISIBLE);
            ev_solved.setVisibility(View.GONE);
        } else {
            srl_solved.setVisibility(View.GONE);
            ev_solved.setVisibility(View.VISIBLE);
        }
    }

    /*网络请求*/
    private void request(List<String> param) {
        switch (type) {
            case TYPE1:
                RequestYxyw.findHandledProcess(param, callback);
                break;
            case TYPE2:
                RequestYxyw.findEndedProcess(param, callback);
                break;
        }
    }

    /*请求第一页数据*/
    private void requestFirst() {
        initRequest();
        List<String> list = new ArrayList<>();
        list.add(key);
        list.add(MySharedpreferences.getUser().getName());
        list.add(currentPage + "");
        list.add("10");
        request(list);
    }

    /*请求下一页数据*/
    private void requestNext() {
        currentPage += 10;
        if (currentPage > Integer.parseInt(total)) {
            Toast.makeText(this, "已经没有更多数据了", Toast.LENGTH_SHORT).show();
            rv_solved.setLoadMoreEnable(false);
            return;
        }
        List<String> list = new ArrayList<>();
        list.add(key);
        list.add(MySharedpreferences.getUser().getName());
        list.add(currentPage + "");
        list.add("10");
        request(list);
    }

    /*初始化请求状态*/
    private void initRequest() {
        currentPage = 0;
        rv_solved.setLoadMoreEnable(true);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.tv_sovled_doing) {
            selectDoing();

        } else if (i == R.id.tv_sovled_finish) {
            selectFinish();

        }
    }

    @Override
    public void onRefresh() {
        requestFirst();
    }

    @Override
    public void loadMoreListener() {
        requestNext();
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
        this.key="";
        requestFirst();
    }
}
