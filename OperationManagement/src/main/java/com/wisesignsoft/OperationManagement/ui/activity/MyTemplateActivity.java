package com.wisesignsoft.OperationManagement.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wisesignsoft.OperationManagement.BaseActivity;
import com.wisesignsoft.OperationManagement.R;
import com.wisesignsoft.OperationManagement.adapter.MyTemplateAdapter;
import com.wisesignsoft.OperationManagement.adapter.SolvingAdapter;
import com.wisesignsoft.OperationManagement.db.MySharedpreferences;
import com.wisesignsoft.OperationManagement.net.RequestTask;
import com.wisesignsoft.OperationManagement.net.request.RequestProcess;
import com.wisesignsoft.OperationManagement.net.request.RequestYxyw;
import com.wisesignsoft.OperationManagement.net.response.FindCanCreateProcessResponse;
import com.wisesignsoft.OperationManagement.net.response.FindUnHandleProcessResponse;
import com.wisesignsoft.OperationManagement.net.response.GetWoTempletListByUserAccountResponse;
import com.wisesignsoft.OperationManagement.utils.SwipeRefreshUtil;
import com.wisesignsoft.OperationManagement.view.mview.EmptyView;
import com.wisesignsoft.OperationManagement.view.mview.LoadingView;
import com.wisesignsoft.OperationManagement.view.mview.MyTitle;
import com.wisesignsoft.OperationManagement.view.mview.RefreshRecyclerView;
import com.wisesignsoft.OperationManagement.view.mview.SeachView;

import java.util.ArrayList;
import java.util.List;

public class MyTemplateActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, RefreshRecyclerView.OnLoadMoreListener, SeachView.ISearchView {

    private MyTitle mt_my_template;
    private SeachView sv_my_template;
    private RefreshRecyclerView rrv_my_template;
    /*数据的当前页面*/
    private int currentPage = 0;
    /*搜索关键词*/
    private String key = "";
    /*总数*/
    private String total;
    /*工单类型拼接*/
    private String keys;
    /*数据*/
    private List<GetWoTempletListByUserAccountResponse.DataBean> datas = new ArrayList<>();
    private RequestTask.ResultCallback<GetWoTempletListByUserAccountResponse> callback = new RequestTask.ResultCallback<GetWoTempletListByUserAccountResponse>() {
        @Override
        public void onError(Exception e) {
            if (srl_my_template.isRefreshing()) {
                srl_my_template.setRefreshing(false);
            }
            loadingView.stop(loadingView);
        }

        @Override
        public void onResponse(GetWoTempletListByUserAccountResponse response) {
            loadingView.stop(loadingView);
            total = response.getTotal();
            if (srl_my_template.isRefreshing()) {
                srl_my_template.setRefreshing(false);
            }
            if (currentPage == 0) {
                datas.clear();
            }
            datas.addAll(response.getData());
            Log.i("YCS", "onResponse: size:" + datas.size());
            setEmpty(datas);
            rrv_my_template.notifyData();
        }
    };
    private SwipeRefreshLayout srl_my_template;
    private LoadingView loadingView;
    private EmptyView ev_my_template;
    private int type;

    public static void startSelf(Context context, int type) {
        Intent intent = new Intent(context, MyTemplateActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_template);
        init();
        requestKey();
//        requestFirst();
    }

    /**
     * 查询可创建的工单
     */
    private void requestKey() {
        List<String> list = new ArrayList<>();
        list.add(MySharedpreferences.getUser().getUsername());
        RequestProcess.findCanCreateProcess(list, new RequestTask.ResultCallback<FindCanCreateProcessResponse>() {
            @Override
            public void onError(Exception e) {
            }
            @Override
            public void onResponse(FindCanCreateProcessResponse response) {
                if(response != null){
                    List<FindCanCreateProcessResponse.ResultBean> datas = response.getResult();
                    setKeys(datas);
                    requestFirst();
                }
            }
        });
    }
    private void setKeys(List<FindCanCreateProcessResponse.ResultBean> datas){
        if(datas!=null&&datas.size()>0){
            StringBuilder sb = new StringBuilder();
            for(int i=0;i<datas.size();i++){
                String key = datas.get(i).getKey();
                if(i == datas.size()-1){
                    sb.append(key);
                }else {
                    sb.append(key).append(",");
                }
            }
            keys = sb.toString();
        }
    }

    private void init() {
        /*获取type*/
        type = getIntent().getIntExtra("type", 0);
        /*加载框*/
        loadingView = LoadingView.getLoadingView(this);
        loadingView.show();
        /*初始化控件*/
        mt_my_template = (MyTitle) findViewById(R.id.mt_my_template);
        sv_my_template = (SeachView) findViewById(R.id.sv_my_template);
        rrv_my_template = (RefreshRecyclerView) findViewById(R.id.rrv_my_template);
        srl_my_template = (SwipeRefreshLayout) findViewById(R.id.srl_my_template);
        ev_my_template = (EmptyView) findViewById(R.id.ev_my_template);
        /*设置标题*/
        mt_my_template.setBack(true, this);
        mt_my_template.setTitle("我的模板");
        /*设置刷新和列表*/
        SwipeRefreshUtil.setConfig(srl_my_template, this);
        SwipeRefreshUtil.setRecyclerConfig(rrv_my_template, this, this);
        /*设置空页面*/
        ev_my_template.setData(R.mipmap.home, getResources().getString(R.string.empty_text));
        ev_my_template.setOnRefreshListener(listener);
        /*设置适配器*/
        MyTemplateAdapter adapter = new MyTemplateAdapter(this, datas);
        rrv_my_template.setAdapter(adapter);
        /*搜索监听*/
        sv_my_template.setISearchViewListener(this);
        sv_my_template.setHint("请输入标题");
        adapter.setOnIMyTemplate(new MyTemplateAdapter.IMyTemplate() {
            @Override
            public void setOnClick(String id, String name) {
                if (type == 0) {
                    TemplateDetailActivity.startSelf(MyTemplateActivity.this, id);
                } else {
                    NewTemplateActivity3.startSelf(MyTemplateActivity.this, name, id);
                }
            }
        });
    }

    /*网络请求数据*/
    private void request(List<String> list) {
        RequestProcess.getWoTempletListByUserAccount(list, callback);
    }

    /*请求第一页数据*/
    private void requestFirst() {
        currentPage = 0;
        rrv_my_template.setLoadMoreEnable(true);
        List<String> list = new ArrayList<>();
        list.add(currentPage + "");
        list.add("10");
        list.add(MySharedpreferences.getUser().getUsername());
        list.add(keys);
        list.add(key);
        request(list);
    }

    /*请求下一页数据*/
    private void requestNext() {
        currentPage += 10;
        if (currentPage > Integer.parseInt(total)) {
            Toast.makeText(this, "已经没有更多数据了", Toast.LENGTH_SHORT).show();
            rrv_my_template.setLoadMoreEnable(false);
            return;
        }
        List<String> list = new ArrayList<>();
        list.add(currentPage + "");
        list.add("10");
        list.add(MySharedpreferences.getUser().getUsername());
        list.add(keys);
        list.add(key);
        request(list);
    }

    /*设置空页面*/
    private void setEmpty(List list) {
        if (list.size() > 0) {
            srl_my_template.setVisibility(View.VISIBLE);
            ev_my_template.setVisibility(View.GONE);
        } else {
            srl_my_template.setVisibility(View.GONE);
            ev_my_template.setVisibility(View.VISIBLE);
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
        this.key = "";
        requestFirst();
    }
}
