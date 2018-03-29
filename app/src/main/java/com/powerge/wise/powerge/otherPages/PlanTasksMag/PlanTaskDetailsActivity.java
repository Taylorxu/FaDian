package com.powerge.wise.powerge.otherPages.PlanTasksMag;

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
import com.powerge.wise.powerge.bean.PlanTaskBean;
import com.powerge.wise.powerge.bean.PlanTaskDetailBean;
import com.powerge.wise.powerge.bean.User;
import com.powerge.wise.powerge.config.soap.ApiService;
import com.powerge.wise.powerge.config.soap.request.BaseUrl;
import com.powerge.wise.powerge.config.soap.request.RequestBody;
import com.powerge.wise.powerge.config.soap.request.RequestEnvelope;
import com.powerge.wise.powerge.databinding.ActivityPlanTaskDetailsBinding;
import com.powerge.wise.powerge.databinding.ItemPlanTaskDetailListBinding;
import com.powerge.wise.powerge.helper.EEMsgToastHelper;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PlanTaskDetailsActivity extends AppCompatActivity {
    private String taskId;
    private int currentPage;

    public static void start(Context context, String title, String id) {
        Intent starter = new Intent(context, PlanTaskDetailsActivity.class);
        starter.putExtra(TITLE, title);
        starter.putExtra(ID, id);
        context.startActivity(starter);
    }

    public static String TITLE = "TITLEKEY", ID = "IDKEY";
    ActivityPlanTaskDetailsBinding binding;

    XAdapter<PlanTaskDetailBean, ItemPlanTaskDetailListBinding> adapter = new XAdapter.SimpleAdapter<>(BR.data, R.layout.item_plan_task_detail_list);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_plan_task_details);
        binding.title.setText(getIntent().getStringExtra(TITLE));
        taskId = getIntent().getStringExtra(ID);
        binding.contentPlan.setLayoutManager(new LinearLayoutManager(this));
        binding.refreshLayout.setOnRefreshListener(refreshListener);
        binding.contentPlan.setOnLoadMoreListener(onLoadMoreListener);
        binding.contentPlan.setAdapter(adapter);
    }


    SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            binding.contentPlan.setState(PagingRecyclerView.State.Refresh);
        }
    };

    PagingRecyclerView.OnLoadMoreListener onLoadMoreListener = new PagingRecyclerView.OnLoadMoreListener() {
        @Override
        public void onLoadMore(int page) {
            getData(page);
        }
    };

    /**
     * @param page
     */
    private void getData(int page) {
        PlanTaskDetailBean planTaskBean = new PlanTaskDetailBean();
        planTaskBean.setNameSpace(BaseUrl.NAMESPACE_P);
        planTaskBean.setArg1(taskId);
        planTaskBean.setArg2(String.valueOf(page));
        planTaskBean.setUserName(User.getCurrentUser().getName());
        RequestEnvelope.getRequestEnvelope().setBody(new RequestBody<>(planTaskBean));

        ApiService.Creator.get().queryWorkTask(RequestEnvelope.getRequestEnvelope())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .flatMap(new FlatMapResponse<ResultModelData<ResultModelData.ReturnValueBean<PlanTaskDetailBean>>>())
                .flatMap(new FlatMapTopResList<ResultModelData.ReturnValueBean<PlanTaskDetailBean>>())
                .subscribe(new Subscriber<ResultModelData.ReturnValueBean<PlanTaskDetailBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        EEMsgToastHelper.newInstance().selectWitch(e.getMessage());
                    }

                    @Override
                    public void onNext(ResultModelData.ReturnValueBean<PlanTaskDetailBean> returnValueBean) {
                        if (returnValueBean.getCurrentPage().equals("1")) {
                            adapter.setList(returnValueBean.getResultList());
                        } else {
                            adapter.addItems(returnValueBean.getResultList());
                        }
                        binding.refreshLayout.setRefreshing(false);
                        binding.contentPlan.setState(returnValueBean.getResultList() == null || returnValueBean.getResultList().size() < 10 ? PagingRecyclerView.State.NoMore : PagingRecyclerView.State.LoadSuccess);
                        currentPage = Integer.parseInt(returnValueBean.getCurrentPage());
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
