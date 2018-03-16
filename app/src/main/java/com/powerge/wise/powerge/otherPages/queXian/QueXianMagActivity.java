package com.powerge.wise.powerge.otherPages.queXian;

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
import com.powerge.wise.basestone.heart.ui.XAdapter;
import com.powerge.wise.basestone.heart.ui.view.PagingRecyclerView;
import com.powerge.wise.powerge.BR;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.bean.Items;
import com.powerge.wise.powerge.bean.QueXianMagBean;
import com.powerge.wise.powerge.bean.User;
import com.powerge.wise.powerge.bean.ZhiZhangLogBean;
import com.powerge.wise.powerge.config.soap.ApiService;
import com.powerge.wise.powerge.config.soap.request.BaseUrl;
import com.powerge.wise.powerge.config.soap.request.RequestBody;
import com.powerge.wise.powerge.config.soap.request.RequestEnvelope;
import com.powerge.wise.powerge.databinding.ActivityQueXianMagBinding;
import com.powerge.wise.powerge.databinding.ItemQxFlBinding;
import com.powerge.wise.powerge.helper.EEMsgToastHelper;
import com.powerge.wise.powerge.helper.StartActivity;
import com.wisesignsoft.OperationManagement.utils.ToastUtil;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class QueXianMagActivity extends AppCompatActivity {
    ActivityQueXianMagBinding binding;
    private int currentPage = 0;

    public static void start(Context context) {
        Intent starter = new Intent(context, QueXianMagActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_que_xian_mag);
        binding.title.setText(getResources().getStringArray(R.array.item_name_array)[9]);
        initView();
    }

    XAdapter<QueXianMagBean, ItemQxFlBinding> adapter = new XAdapter.SimpleAdapter<>(BR.item, R.layout.item_qx_fl);

    private void initView() {
        binding.refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        binding.refreshLayout.setOnRefreshListener(refreshListener);
        binding.contentQxList.setOnLoadMoreListener(onLoadMoreListener);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.contentQxList.setLayoutManager(layoutManager);
        binding.contentQxList.setAdapter(adapter);
    }

    SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            binding.contentQxList.setState(PagingRecyclerView.State.Refresh);
        }
    };


    PagingRecyclerView.OnLoadMoreListener onLoadMoreListener = new PagingRecyclerView.OnLoadMoreListener() {
        @Override
        public void onLoadMore(int page) {
            getData(currentPage + 1);
        }
    };


    private void getData(int page) {
        final QueXianMagBean queXianMagBean = QueXianMagBean.newInstance();
        queXianMagBean.setNameSpace(BaseUrl.NAMESPACE_P);
        queXianMagBean.setArg1("2018-03-01");
        queXianMagBean.setArg2(String.valueOf(page));
        queXianMagBean.setUserName(User.getCurrentUser().getAccount());
        RequestEnvelope.getRequestEnvelope().setBody(new RequestBody<>(queXianMagBean));

        ApiService.Creator.get().queryIssueDetails(RequestEnvelope.getRequestEnvelope())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new FlatMapResponse<ResultModelData<ResultModelData.ReturnValueBean<QueXianMagBean>>>())
                .flatMap(new FlatMapTopResList<ResultModelData.ReturnValueBean<QueXianMagBean>>())
                .subscribe(new Subscriber<ResultModelData.ReturnValueBean<QueXianMagBean>>() {
                    @Override
                    public void onCompleted() {
                        binding.refreshLayout.setRefreshing(false);
                        binding.contentQxList.setState(PagingRecyclerView.State.LoadSuccess);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        EEMsgToastHelper.newInstance().selectWitch(e.getCause().getMessage());
                        binding.refreshLayout.setRefreshing(false);
                        binding.contentQxList.setState(PagingRecyclerView.State.LoadFail);
                    }

                    @Override
                    public void onNext(ResultModelData.ReturnValueBean<QueXianMagBean> returnValueBean) {
                        if (returnValueBean.getCurrentPage().equals("1")) {
                            adapter.setList(returnValueBean.getResultList());
                        } else {
                            adapter.addItems(returnValueBean.getResultList());
                        }
                        binding.refreshLayout.setRefreshing(false);
                        binding.contentQxList.setState(returnValueBean.getResultList() == null || returnValueBean.getResultList().size() < 10 ? PagingRecyclerView.State.NoMore : PagingRecyclerView.State.LoadSuccess);
                        currentPage = Integer.parseInt(returnValueBean.getCurrentPage());
                    }
                });
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.ic_bao_biao:
                StartActivity.go(10, this);
                break;
            case R.id.text_1:
                StartActivity.go(10, this);
                break;
        }
    }

}
