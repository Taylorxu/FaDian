package com.powerge.wise.powerge.otherPages.issues;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.databinding.FragmentIssuesWaittingBinding;
import com.powerge.wise.powerge.operationProjo.net.adapter.SolvingAdapter;
import com.powerge.wise.powerge.operationProjo.net.db.MySharedpreferences;
import com.powerge.wise.powerge.operationProjo.net.net.RequestTask;
import com.powerge.wise.powerge.operationProjo.net.net.request.RequestYxyw;
import com.powerge.wise.powerge.operationProjo.net.net.response.FindUnHandleProcessResponse;
import com.powerge.wise.powerge.operationProjo.net.ui.activity.OrderSolvedActivity;
import com.powerge.wise.powerge.operationProjo.net.utils.SwipeRefreshUtil;
import com.powerge.wise.powerge.operationProjo.net.view.mview.EmptyView;
import com.powerge.wise.powerge.operationProjo.net.view.mview.LoadingView;
import com.powerge.wise.powerge.operationProjo.net.view.mview.RefreshRecyclerView;
import com.powerge.wise.powerge.operationProjo.net.view.mview.SeachView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class IssuesWaittingFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RefreshRecyclerView.OnLoadMoreListener, SeachView.ISearchView {
    FragmentIssuesWaittingBinding binding;

    public IssuesWaittingFragment() {

    }

    public static IssuesWaittingFragment newInstance() {
        IssuesWaittingFragment fragment = new IssuesWaittingFragment();
        return fragment;
    }

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
            if (binding.srlSolving.isRefreshing()) {
                binding.srlSolving.setRefreshing(false);
            }
            loadingView.stop(loadingView);
        }

        @Override
        public void onResponse(FindUnHandleProcessResponse response) {
            loadingView.stop(loadingView);
            total = response.getTotal();
            if (binding.srlSolving.isRefreshing()) {
                binding.srlSolving.setRefreshing(false);
            }
            if (currentPage == 0) {
                datas.clear();
                datas1.clear();
                datas2.clear();
            }
            binding.tvTotal.setText("共" + total + "条数据");
            datas.addAll(response.getData());
            setEmpty(datas);
            binding.rrvSolving.notifyData();
        }
    };
    private LoadingView loadingView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.fragment_issues_waitting, container, false);
        initView();
        return binding.getRoot();
    }


    @Override
    public void onResume() {
        super.onResume();
        requestFirst();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        loadingView = LoadingView.getLoadingView(getContext());
        loadingView.show();

        /*设置刷新和列表*/
        SwipeRefreshUtil.setConfig(binding.srlSolving, this);
        SwipeRefreshUtil.setRecyclerConfig(binding.rrvSolving, getContext(), this);
        /*设置空页面*/
        binding.evSolving.setData(com.wisesignsoft.OperationManagement.R.mipmap.home, getResources().getString(com.wisesignsoft.OperationManagement.R.string.empty_text));
        binding.evSolving.setOnRefreshListener(listener);
        /*设置适配器*/
        SolvingAdapter adapter = new SolvingAdapter(getContext(), datas);
        binding.rrvSolving.setAdapter(adapter);
        /*设置搜索*/
        binding.svSolving.setISearchViewListener(this);
        binding.svSolving.setHint("请输入标题或工单号");
        adapter.setOnISolving(new SolvingAdapter.ISolving() {
            @Override
            public void setOnClick(String currentDealer, String pikey) {
                OrderSolvedActivity.startSelf(getContext(), currentDealer, pikey);
            }
        });
    }

    private void setEmpty(List list) {
        if (list.size() > 0) {
            binding.evSolving.setVisibility(View.GONE);
            binding.srlSolving.setVisibility(View.VISIBLE);
        } else {
            binding.evSolving.setVisibility(View.VISIBLE);
            binding.srlSolving.setVisibility(View.GONE);
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
        binding.rrvSolving.setLoadMoreEnable(true);
        List<String> list = new ArrayList<>();
        list.add(key);
        list.add(MySharedpreferences.getUser().getName());
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
            Toast.makeText(getContext(), "已经没有更多数据了", Toast.LENGTH_SHORT).show();
            binding.rrvSolving.setLoadMoreEnable(false);
            return;
        }
        List<String> list = new ArrayList<>();
        list.add(key);
        list.add(MySharedpreferences.getUser().getName());
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
