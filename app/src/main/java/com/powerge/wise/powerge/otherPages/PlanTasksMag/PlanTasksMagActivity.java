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
import com.powerge.wise.basestone.heart.ui.XViewHolder;
import com.powerge.wise.basestone.heart.ui.view.PagingRecyclerView;
import com.powerge.wise.powerge.BR;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.bean.GonGaoBean;
import com.powerge.wise.powerge.bean.PlanTaskBean;
import com.powerge.wise.powerge.bean.User;
import com.powerge.wise.powerge.config.soap.ApiService;
import com.powerge.wise.powerge.config.soap.request.BaseUrl;
import com.powerge.wise.powerge.config.soap.request.RequestBody;
import com.powerge.wise.powerge.config.soap.request.RequestEnvelope;
import com.powerge.wise.powerge.databinding.ActivityPlanTasksMagBinding;
import com.powerge.wise.powerge.databinding.ItemPlanTasksListBinding;
import com.powerge.wise.powerge.helper.EEMsgToastHelper;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PlanTasksMagActivity extends AppCompatActivity {
    ActivityPlanTasksMagBinding binding;
    private int currentPage;

    public static void start(Context context) {
        Intent starter = new Intent(context, PlanTasksMagActivity.class);
        context.startActivity(starter);
    }

    XAdapter<PlanTaskBean, ItemPlanTasksListBinding> adapter = new XAdapter.SimpleAdapter<>(BR.data, R.layout.item_plan_tasks_list);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_plan_tasks_mag);
        binding.title.setText("计划任务管理");
        binding.contentPlan.setLayoutManager(new LinearLayoutManager(this));
        binding.refreshLayout.setOnRefreshListener(refreshListener);
        binding.contentPlan.setOnLoadMoreListener(onLoadMoreListener);
        binding.contentPlan.setAdapter(adapter);
        adapter.setItemClickListener(new XAdapter.OnItemClickListener<PlanTaskBean, ItemPlanTasksListBinding>() {
            @Override
            public void onItemClick(XViewHolder<PlanTaskBean, ItemPlanTasksListBinding> holder) {
                PlanTaskDetailsActivity.start(getBaseContext(), holder.getBinding().getData().getName(), holder.getBinding().getData().getObjID());
            }
        });
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
        PlanTaskBean planTaskBean = new PlanTaskBean();
        planTaskBean.setNameSpace(BaseUrl.NAMESPACE_P);
        planTaskBean.setArg1(String.valueOf(page));
        planTaskBean.setUserName(User.getCurrentUser().getName());
        RequestEnvelope.getRequestEnvelope().setBody(new RequestBody<>(planTaskBean));

        ApiService.Creator.get().queryWorkPlan(RequestEnvelope.getRequestEnvelope())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .flatMap(new FlatMapResponse<ResultModelData<ResultModelData.ReturnValueBean<PlanTaskBean>>>())
                .flatMap(new FlatMapTopResList<ResultModelData.ReturnValueBean<PlanTaskBean>>())
                .subscribe(new Subscriber<ResultModelData.ReturnValueBean<PlanTaskBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        EEMsgToastHelper.newInstance().selectWitch(e.getMessage());
                    }

                    @Override
                    public void onNext(ResultModelData.ReturnValueBean<PlanTaskBean> returnValueBean) {
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
