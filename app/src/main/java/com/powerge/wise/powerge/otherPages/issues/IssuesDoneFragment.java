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
import com.powerge.wise.powerge.databinding.FragmentIssuesDoneBinding;
import com.powerge.wise.powerge.operationProjo.net.adapter.SovledAdapter;
import com.powerge.wise.powerge.operationProjo.net.db.MySharedpreferences;
import com.powerge.wise.powerge.operationProjo.net.net.RequestTask;
import com.powerge.wise.powerge.operationProjo.net.net.request.RequestYxyw;
import com.powerge.wise.powerge.operationProjo.net.net.response.FindUnHandleProcessResponse;
import com.powerge.wise.powerge.operationProjo.net.ui.activity.WorkOrderDetailActivity;
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
public class IssuesDoneFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RefreshRecyclerView.OnLoadMoreListener, SeachView.ISearchView {
    FragmentIssuesDoneBinding binding;

    public IssuesDoneFragment() {
        // Required empty public constructor
    }

    public static IssuesDoneFragment newInstance() {
        IssuesDoneFragment fragment = new IssuesDoneFragment();
        return fragment;
    }


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
            if (binding.srlSolved.isRefreshing()) {
                binding.srlSolved.setRefreshing(false);
            }
            loadingView.stop(loadingView);
        }

        @Override
        public void onResponse(FindUnHandleProcessResponse response) {
            loadingView.stop(loadingView);
            total = response.getTotal();
            if (binding.srlSolved.isRefreshing()) {
                binding.srlSolved.setRefreshing(false);
            }
            if (currentPage == 0) {
                datas.clear();
            }
            binding.tvTotal.setText("共" + response.getTotal() + "条数据");
            datas.addAll(response.getData());
            setEmpty(datas);
            binding.rvSolved.notifyData();
        }
    };
    private LoadingView loadingView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.fragment_issues_done, container, false);
        init();
        return binding.getRoot();
    }


    private void init() {


        /*设置刷新和列表*/
        SwipeRefreshUtil.setConfig(binding.srlSolved, this);
        SwipeRefreshUtil.setRecyclerConfig(binding.rvSolved, getContext(), this);
         /*设置空页面*/
        binding.evSolved.setData(R.mipmap.home, getResources().getString(R.string.empty_text));
        binding.evSolved.setOnRefreshListener(listener);
        /*设置适配器*/
        SovledAdapter adapter = new SovledAdapter(getContext(), datas);
        binding.rvSolved.setAdapter(adapter);
        adapter.setOnItemSolvedListener(new SovledAdapter.ISolvedItemClickListener() {
            @Override
            public void setOnItemSolvedClickListener(String key) {
                WorkOrderDetailActivity.startSelf(getContext(), key);
            }
        });

        /*默认选中进行中*/
        selectDoing();
        binding.svSovled.setISearchViewListener(this);
        binding.svSovled.setHint("请输入标题或工单号");
    }

    /*进行中模式*/
    private void selectDoing() {
        loadingView = LoadingView.getLoadingView(getContext());
        loadingView.show();
        initRequest();
        requestFirst();
    }


    /*设置空页面*/
    private void setEmpty(List list) {
        if (list.size() > 0) {
            binding.srlSolved.setVisibility(View.VISIBLE);
            binding.evSolved.setVisibility(View.GONE);
        } else {
            binding.srlSolved.setVisibility(View.GONE);
            binding.evSolved.setVisibility(View.VISIBLE);
        }
    }

    /*网络请求*/
    private void request(List<String> param) {
        RequestYxyw.findEndedProcess(param, callback);
    }

    /*请求第一页数据*/
    private void requestFirst() {
        initRequest();
        List<String> list = new ArrayList<>();
        list.add(key);
        list.add( MySharedpreferences.getUser().getName());
        list.add(currentPage + "");
        list.add("10");
        request(list);
    }

    /*请求下一页数据*/
    private void requestNext() {
        currentPage += 10;
        if (currentPage > Integer.parseInt(total)) {
            Toast.makeText(getContext(), "已经没有更多数据了", Toast.LENGTH_SHORT).show();
            binding.rvSolved.setLoadMoreEnable(false);
            return;
        }
        List<String> list = new ArrayList<>();
        list.add(key);
        list.add( MySharedpreferences.getUser().getName());
        list.add(currentPage + "");
        list.add("10");
        request(list);
    }

    /*初始化请求状态*/
    private void initRequest() {
        currentPage = 0;
        binding.rvSolved.setLoadMoreEnable(true);
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
