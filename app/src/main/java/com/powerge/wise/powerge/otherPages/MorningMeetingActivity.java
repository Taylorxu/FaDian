package com.powerge.wise.powerge.otherPages;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.powerge.wise.basestone.heart.network.FlatMapResponse;
import com.powerge.wise.basestone.heart.network.FlatMapTopResList;
import com.powerge.wise.basestone.heart.network.ResultModelData;
import com.powerge.wise.basestone.heart.ui.XAdapter;
import com.powerge.wise.basestone.heart.ui.view.PagingRecyclerView;
import com.powerge.wise.powerge.BR;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.bean.MorningMeetingBean;
import com.powerge.wise.powerge.bean.User;
import com.powerge.wise.powerge.config.soap.ApiService;
import com.powerge.wise.powerge.config.soap.request.BaseUrl;
import com.powerge.wise.powerge.config.soap.request.RequestBody;
import com.powerge.wise.powerge.config.soap.request.RequestEnvelope;
import com.powerge.wise.powerge.databinding.ActivityMorningMeetingBinding;
import com.powerge.wise.powerge.databinding.ItemMorningMeetingBinding;
import com.powerge.wise.powerge.databinding.ItemZhiZhangLogesBinding;
import com.wisesignsoft.OperationManagement.utils.ToastUtil;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MorningMeetingActivity extends AppCompatActivity {


    private int currentPage;

    public static void start(Context context) {
        Intent starter = new Intent(context, MorningMeetingActivity.class);
        context.startActivity(starter);
    }

    ActivityMorningMeetingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_morning_meeting);
        binding.title.setText(getResources().getStringArray(R.array.item_name_array)[6]);
        initView();
    }

    XAdapter<MorningMeetingBean, ItemMorningMeetingBinding> adapter = new XAdapter.SimpleAdapter<>(BR.textItem, R.layout.item_morning_meeting);

    @SuppressLint("ResourceAsColor")
    private void initView() {
        binding.refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        binding.refreshLayout.setOnRefreshListener(refreshListener);
        binding.contentLog.setOnLoadMoreListener(onLoadMoreListener);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.contentLog.setLayoutManager(layoutManager);
        binding.contentLog.setAdapter(adapter);
       /* DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.item_line));
        binding.contentLog.addItemDecoration(divider);*/
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

    public void getData(int page) {
        final MorningMeetingBean morningMeetingBean = MorningMeetingBean.newInstance();
        morningMeetingBean.setNameSpace(BaseUrl.NAMESPACE_P);
        morningMeetingBean.setArg1(String.valueOf(page));
        morningMeetingBean.setUserName(User.getCurrentUser().getAccount());

        RequestEnvelope.getRequestEnvelope().setBody(new RequestBody<>(morningMeetingBean));
        ApiService.Creator.get().queryProductionEarlyMeetingData(RequestEnvelope.getRequestEnvelope())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new FlatMapResponse<ResultModelData<ResultModelData.ReturnValueBean<MorningMeetingBean>>>())
                .flatMap(new FlatMapTopResList<ResultModelData.ReturnValueBean<MorningMeetingBean>>())
                .subscribe(new Subscriber<ResultModelData.ReturnValueBean<MorningMeetingBean>>() {
                    @Override
                    public void onCompleted() {
                        binding.refreshLayout.setRefreshing(false);
                        binding.contentLog.setState(PagingRecyclerView.State.LoadSuccess);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if (e.getMessage().indexOf("java.net.ConnectException: Connection refused") > 0) {
                            ToastUtil.toast(getBaseContext(), "服务连接失败");
                        }
                        binding.refreshLayout.setRefreshing(false);
                        binding.contentLog.setState(PagingRecyclerView.State.LoadFail);
                    }

                    @Override
                    public void onNext(ResultModelData.ReturnValueBean<MorningMeetingBean> returnValueBean) {
                        if (returnValueBean.getCurrentPage().equals("1")) {
                            adapter.setList(returnValueBean.getResultList());
                        } else {
                            adapter.addItems(returnValueBean.getResultList());
                        }
                        binding.refreshLayout.setRefreshing(false);
                        binding.contentLog.setState(PagingRecyclerView.State.LoadSuccess);
                        currentPage = Integer.parseInt(returnValueBean.getCurrentPage());
                    }
                });
    }


    public void onClick(View view) {
        finish();
    }
}
