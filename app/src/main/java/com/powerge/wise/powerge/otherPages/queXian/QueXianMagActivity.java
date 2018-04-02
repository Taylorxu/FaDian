package com.powerge.wise.powerge.otherPages.queXian;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.powerge.wise.basestone.heart.network.FlatMapResponse;
import com.powerge.wise.basestone.heart.network.FlatMapTopResList;
import com.powerge.wise.basestone.heart.network.ResultModelData;
import com.powerge.wise.basestone.heart.ui.XAdapter;
import com.powerge.wise.basestone.heart.ui.view.PagingRecyclerView;
import com.powerge.wise.powerge.BR;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.bean.QueXianMagBean;
import com.powerge.wise.powerge.bean.User;
import com.powerge.wise.powerge.config.soap.ApiService;
import com.powerge.wise.powerge.config.soap.request.BaseUrl;
import com.powerge.wise.powerge.config.soap.request.RequestBody;
import com.powerge.wise.powerge.config.soap.request.RequestEnvelope;
import com.powerge.wise.powerge.databinding.ActivityDateSelectBinding;
import com.powerge.wise.powerge.databinding.ActivityQueXianMagBinding;
import com.powerge.wise.powerge.databinding.ItemQxFlBinding;
import com.powerge.wise.powerge.helper.EEMsgToastHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class QueXianMagActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityQueXianMagBinding binding;
    private int currentPage = 0;
    private PopupWindow window;
    private String dateParam;
    int calendarArray[] = new int[3];

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
        initPopInnerView();
        binding.textDate.setText(dateParam);
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
            getData(page);
        }
    };


    private void getData(int page) {
        final QueXianMagBean queXianMagBean = QueXianMagBean.newInstance();
        queXianMagBean.setNameSpace(BaseUrl.NAMESPACE_P);
        queXianMagBean.setArg1(dateParam);
        queXianMagBean.setArg2(String.valueOf(page));
        queXianMagBean.setUserName(User.getCurrentUser().getName());
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
                        EEMsgToastHelper.newInstance().selectWitch(e.getMessage());
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
                QueXianPieChartActivity.start(this, calendarArray);
                break;
            case R.id.text_1:
                QueXianPieChartActivity.start(this, calendarArray);
                break;
            case R.id.btn_slect_date:
                showPopDateSelector();
                break;
        }
    }

    ActivityDateSelectBinding popBinding;

    private void initPopInnerView() {
        final Calendar calendar = Calendar.getInstance();
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        int year = calendar.get(Calendar.YEAR);
        int monthOfYear = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.set(year, monthOfYear, dayOfMonth);
        dateParam = format.format(calendar.getTime());
        setCalendarArray(year, monthOfYear, dayOfMonth);
        popBinding = DataBindingUtil.inflate(LayoutInflater.from(getBaseContext()), R.layout.activity_date_select, null, false);
        popBinding.datePickerView.init(year, monthOfYear, dayOfMonth, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(year, monthOfYear, dayOfMonth);
                dateParam = format.format(calendar.getTime());
                setCalendarArray(year, monthOfYear, dayOfMonth);
                binding.textDate.setText(dateParam);
            }
        });
        popBinding.btnBack.setOnClickListener(new PopClick());
        popBinding.btnDone.setOnClickListener(new PopClick());

        window = new PopupWindow(popBinding.getRoot(), LinearLayout.LayoutParams.MATCH_PARENT, 900);
    }

    /**
     * 创建日历时间数组
     * 传给报表activity
     *
     * @param year
     * @param monthOfYear
     * @param dayOfMonth
     */
    private void setCalendarArray(int year, int monthOfYear, int dayOfMonth) {
        calendarArray[0] = year;
        calendarArray[1] = monthOfYear;
        calendarArray[2] = dayOfMonth;
    }

    /**
     * 显示时间pop
     */
    private void showPopDateSelector() {

        window.setAnimationStyle(R.style.popup_window_anim);
        window.setTouchable(true);
        window.setOutsideTouchable(true);
        window.setAnimationStyle(R.style.popup_window_anim);
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8F8F8")));
        window.setFocusable(false);
        window.setOutsideTouchable(false);
        window.showAtLocation(popBinding.getRoot(), Gravity.BOTTOM, 0, 0);

    }

    private class PopClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_back:
                    window.dismiss();
                    break;
                case R.id.btn_done:
                    getData(1);
                    window.dismiss();
                    break;
            }
        }
    }

}
