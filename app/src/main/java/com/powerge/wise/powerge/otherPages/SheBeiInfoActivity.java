package com.powerge.wise.powerge.otherPages;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ExpandableListView;

import com.powerge.wise.basestone.heart.network.FlatMapResponse;
import com.powerge.wise.basestone.heart.network.FlatMapTopRes;
import com.powerge.wise.basestone.heart.network.FlatMapTopResList;
import com.powerge.wise.basestone.heart.network.NetConfig;
import com.powerge.wise.basestone.heart.network.ResultModel;
import com.powerge.wise.basestone.heart.network.ResultModelData;
import com.powerge.wise.basestone.heart.ui.EndLessOnScrollListener;
import com.powerge.wise.basestone.heart.ui.view.LoadMoreExpandableListView;
import com.powerge.wise.basestone.heart.util.LogUtils;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.bean.SheBeiRootBean;
import com.powerge.wise.powerge.bean.User;
import com.powerge.wise.powerge.config.soap.ApiService;
import com.powerge.wise.powerge.config.soap.beans.LoginBean;
import com.powerge.wise.powerge.config.soap.request.BaseUrl;
import com.powerge.wise.powerge.config.soap.request.RequestBody;
import com.powerge.wise.powerge.config.soap.request.RequestEnvelope;
import com.powerge.wise.powerge.databinding.ActivitySheBeiInfoBinding;
import com.wisesignsoft.OperationManagement.utils.LogUtil;
import com.wisesignsoft.OperationManagement.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SheBeiInfoActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    public static void start(Context context) {
        Intent starter = new Intent(context, SheBeiInfoActivity.class);
        context.startActivity(starter);
    }

    ActivitySheBeiInfoBinding binding;
    ExpandableListAdapter adapter = new ExpandableListAdapter();
    List<SheBeiRootBean> list = new ArrayList<>();
    int currentPage = 0;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_she_bei_info);
        binding.title.setText(getResources().getStringArray(R.array.item_name_array)[4]);
        binding.refreshLayout.setColorSchemeColors(R.color.colorPrimary);
        binding.refreshLayout.setOnRefreshListener(this);
        initView();
    }

    private void initView() {
        binding.refreshLayout.setRefreshing(true);
        getSheBeiData(1);
        binding.contentSheBei.setAdapter(adapter);
        binding.contentSheBei.setGroupIndicator(null);
        binding.contentSheBei.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                boolean groupExpanded = parent.isGroupExpanded(groupPosition);
                adapter.setIndicatorState(groupPosition, groupExpanded);
                return false;
            }
        });
        binding.contentSheBei.setOnLoadMoreListener(new LoadMoreExpandableListView.OnLoadMoreListener() {
            @Override
            public void onloadMore() {
                getSheBeiData(currentPage + 1);
            }
        });

    }

    private void getSheBeiData(final int page) {
        final SheBeiRootBean sheBeiRootBean = SheBeiRootBean.newInstance();
        sheBeiRootBean.setNameSpace(BaseUrl.NAMESPACE_P);
        sheBeiRootBean.setPage(String.valueOf(page));
        sheBeiRootBean.setKeyWord("");
        sheBeiRootBean.setUserName(User.getCurrentUser().getAccount());

        RequestEnvelope.getRequestEnvelope().setBody(new RequestBody<>(sheBeiRootBean));
        ApiService.Creator.get().queryDevicesData(RequestEnvelope.getRequestEnvelope())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new FlatMapResponse<ResultModelData<ResultModelData.ReturnValueBean<SheBeiRootBean>>>())
                .flatMap(new FlatMapTopResList<ResultModelData.ReturnValueBean<SheBeiRootBean>>())
                .subscribe(new Subscriber<ResultModelData.ReturnValueBean<SheBeiRootBean>>() {
                    @Override
                    public void onCompleted() {
                        binding.refreshLayout.setRefreshing(false);
                        binding.contentSheBei.setLoadCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if (e.getMessage().indexOf("java.net.ConnectException: Connection refused") > 0) {
                            ToastUtil.toast(getBaseContext(), "服务连接失败");
                        }
                        binding.refreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onNext(ResultModelData.ReturnValueBean<SheBeiRootBean> returnValueBean) {
                        if (returnValueBean.getCurrentPage().equals("1")) {
                            adapter.setList(returnValueBean.getResultList());
                        } else {
                            adapter.addItems(returnValueBean.getResultList());
                        }
                        binding.refreshLayout.setRefreshing(false);
                        currentPage = Integer.parseInt(returnValueBean.getCurrentPage());
                    }
                });
    }


    public void onClick(View view) {
        if (view.getId() == R.id.btn_back) {
            finish();
        }
    }

    @Override
    public void onRefresh() {
        getSheBeiData(1);
    }
}
