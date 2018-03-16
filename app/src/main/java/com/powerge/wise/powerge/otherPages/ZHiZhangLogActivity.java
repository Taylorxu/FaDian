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

import com.powerge.wise.basestone.heart.network.FlatMapResponse;
import com.powerge.wise.basestone.heart.network.FlatMapTopResList;
import com.powerge.wise.basestone.heart.network.ResultModelData;
import com.powerge.wise.basestone.heart.ui.EndLessOnScrollListener;
import com.powerge.wise.basestone.heart.ui.XAdapter;
import com.powerge.wise.basestone.heart.ui.view.PagingRecyclerView;
import com.powerge.wise.powerge.BR;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.bean.MorningMeetingBean;
import com.powerge.wise.powerge.bean.SimpleListTextItem;
import com.powerge.wise.powerge.bean.User;
import com.powerge.wise.powerge.bean.ZhiZhangLogBean;
import com.powerge.wise.powerge.config.soap.ApiService;
import com.powerge.wise.powerge.config.soap.request.BaseUrl;
import com.powerge.wise.powerge.config.soap.request.RequestBody;
import com.powerge.wise.powerge.config.soap.request.RequestEnvelope;
import com.powerge.wise.powerge.databinding.ActivityZhiZhangLogBinding;
import com.powerge.wise.powerge.databinding.ItemZhiZhangLogesBinding;
import com.powerge.wise.powerge.helper.EEMsgToastHelper;
import com.wisesignsoft.OperationManagement.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ZHiZhangLogActivity extends AppCompatActivity {
    ActivityZhiZhangLogBinding binding;
    private int currentPage = 0;

    public static void start(Context context) {
        Intent starter = new Intent(context, ZHiZhangLogActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_zhi_zhang_log);
        binding.title.setText(getResources().getStringArray(R.array.item_name_array)[5]);

        initView();
    }

    XAdapter<ZhiZhangLogBean, ItemZhiZhangLogesBinding> adapter = new XAdapter.SimpleAdapter<>(BR.textItem, R.layout.item_zhi_zhang_loges);

    @SuppressLint("ResourceAsColor")
    private void initView() {
        binding.refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        binding.refreshLayout.setOnRefreshListener(refreshListener);
        binding.contentLog.setOnLoadMoreListener(onLoadMoreListener);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.contentLog.setLayoutManager(layoutManager);
        binding.contentLog.setAdapter(adapter);
    }


    SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            binding.contentLog.setState(PagingRecyclerView.State.Refresh);
        }
    };

    PagingRecyclerView.OnLoadMoreListener onLoadMoreListener = new PagingRecyclerView.OnLoadMoreListener() {
        @Override
        public void onLoadMore(int page) {
            getData(currentPage + 1);
        }
    };

    private void getData(int page) {
        final ZhiZhangLogBean zhiZhangLogBean = ZhiZhangLogBean.newInstance();
        zhiZhangLogBean.setNameSpace(BaseUrl.NAMESPACE_P);
        zhiZhangLogBean.setArg1(String.valueOf(page));
        zhiZhangLogBean.setUserName(User.getCurrentUser().getAccount());
        RequestEnvelope.getRequestEnvelope().setBody(new RequestBody<>(zhiZhangLogBean));

        ApiService.Creator.get().queryMonitorLogs(RequestEnvelope.getRequestEnvelope())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new FlatMapResponse<ResultModelData<ResultModelData.ReturnValueBean<ZhiZhangLogBean>>>())
                .flatMap(new FlatMapTopResList<ResultModelData.ReturnValueBean<ZhiZhangLogBean>>())
                .subscribe(new Subscriber<ResultModelData.ReturnValueBean<ZhiZhangLogBean>>() {
                    @Override
                    public void onCompleted() {
                        binding.refreshLayout.setRefreshing(false);
                        binding.contentLog.setState(PagingRecyclerView.State.LoadSuccess);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        EEMsgToastHelper.newInstance().selectWitch(e.getCause().getMessage());
                        binding.refreshLayout.setRefreshing(false);
                        binding.contentLog.setState(PagingRecyclerView.State.LoadFail);
                    }

                    @Override
                    public void onNext(ResultModelData.ReturnValueBean<ZhiZhangLogBean> returnValueBean) {
                        if (returnValueBean.getCurrentPage().equals("1")) {
                            adapter.setList(returnValueBean.getResultList());
                        } else {
                            adapter.addItems(returnValueBean.getResultList());
                        }
                        binding.refreshLayout.setRefreshing(false);
                        binding.contentLog.setState(returnValueBean.getResultList() == null || returnValueBean.getResultList().size() < 10 ? PagingRecyclerView.State.NoMore : PagingRecyclerView.State.LoadSuccess);
                        currentPage = Integer.parseInt(returnValueBean.getCurrentPage());
                    }
                });
    }

    public void onClick(View view) {
        finish();
    }
}
