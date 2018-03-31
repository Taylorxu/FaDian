package com.powerge.wise.powerge.otherPages;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.powerge.wise.basestone.heart.network.FlatMapResponse;
import com.powerge.wise.basestone.heart.network.FlatMapTopResList;
import com.powerge.wise.basestone.heart.network.ResultModelData;
import com.powerge.wise.basestone.heart.ui.XAdapter;
import com.powerge.wise.basestone.heart.ui.view.PagingRecyclerView;
import com.powerge.wise.powerge.BR;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.bean.TongJiForm;
import com.powerge.wise.powerge.bean.User;
import com.powerge.wise.powerge.config.soap.ApiService;
import com.powerge.wise.powerge.config.soap.request.BaseUrl;
import com.powerge.wise.powerge.config.soap.request.RequestBody;
import com.powerge.wise.powerge.config.soap.request.RequestEnvelope;
import com.powerge.wise.powerge.databinding.ActivityTongJiFormBinding;
import com.powerge.wise.powerge.databinding.ItemTongJiBinding;
import com.powerge.wise.powerge.helper.EEMsgToastHelper;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TongJiFormActivity extends AppCompatActivity {

    ActivityTongJiFormBinding binding;

    public static void start(Context context) {
        Intent starter = new Intent(context, TongJiFormActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tong_ji_form);
        binding.title.setText(getResources().getStringArray(R.array.item_name_array)[3]);

        initView();
    }

    XAdapter<TongJiForm, ItemTongJiBinding> adapter = new XAdapter.SimpleAdapter<>(BR.textItem, R.layout.item_tong_ji);

    private void initView() {
        binding.refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        binding.refreshLayout.setOnRefreshListener(refreshListener);
        binding.contentForm.setOnLoadMoreListener(onLoadMoreListener);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.contentForm.setLayoutManager(layoutManager);
        binding.contentForm.setAdapter(adapter);
    }


    SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            binding.contentForm.setState(PagingRecyclerView.State.Refresh);
        }
    };

    PagingRecyclerView.OnLoadMoreListener onLoadMoreListener = new PagingRecyclerView.OnLoadMoreListener() {
        @Override
        public void onLoadMore(int page) {
            getData();
        }
    };


    private void getData() {
        final TongJiForm tongJiFormBean = TongJiForm.newInstance();
        tongJiFormBean.setNameSpace(BaseUrl.NAMESPACE_P);
        tongJiFormBean.setUserName(User.getCurrentUser().getName());
        RequestEnvelope.getRequestEnvelope().setBody(new RequestBody<>(tongJiFormBean));

        ApiService.Creator.get().queryStatisticData(RequestEnvelope.getRequestEnvelope())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new FlatMapResponse<ResultModelData<ResultModelData.ReturnValueBean<TongJiForm>>>())
                .flatMap(new FlatMapTopResList<ResultModelData.ReturnValueBean<TongJiForm>>())
                .subscribe(new Subscriber<ResultModelData.ReturnValueBean<TongJiForm>>() {
                    @Override
                    public void onCompleted() {
                        binding.refreshLayout.setRefreshing(false);
                        binding.contentForm.setState(PagingRecyclerView.State.LoadSuccess);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        EEMsgToastHelper.newInstance().selectWitch(e.getMessage());
                        binding.refreshLayout.setRefreshing(false);
                        binding.contentForm.setState(PagingRecyclerView.State.LoadFail);
                    }

                    @Override
                    public void onNext(ResultModelData.ReturnValueBean<TongJiForm> returnValueBean) {
                        if (returnValueBean.getCurrentPage().equals("1")) {
                            adapter.setList(returnValueBean.getResultList());
                        } else {
                            adapter.addItems(returnValueBean.getResultList());
                        }
                        binding.refreshLayout.setRefreshing(false);
                        binding.contentForm.setState(returnValueBean.getResultList() == null || returnValueBean.getResultList().size() < 10 ?  PagingRecyclerView.State.NoMore : PagingRecyclerView.State.LoadSuccess);
                    }
                });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
        }

    }

}
