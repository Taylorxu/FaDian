package com.powerge.wise.powerge.otherPages.JingSai;


import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.powerge.wise.basestone.heart.network.FlatMapResponse;
import com.powerge.wise.basestone.heart.network.FlatMapTopRes;
import com.powerge.wise.basestone.heart.network.ResultModel;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.bean.PaiMingChildItemBean;
import com.powerge.wise.powerge.bean.User;
import com.powerge.wise.powerge.config.soap.ApiService;
import com.powerge.wise.powerge.config.soap.request.BaseUrl;
import com.powerge.wise.powerge.config.soap.request.RequestBody;
import com.powerge.wise.powerge.config.soap.request.RequestEnvelope;
import com.powerge.wise.powerge.databinding.ActivityDateSelectBinding;
import com.powerge.wise.powerge.databinding.FragmentJingSaiPaiMingBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class JingSaiPaiMingFragment extends Fragment implements View.OnClickListener {
    FragmentJingSaiPaiMingBinding binding;
    JingSaiPaiMingExpandAdapter expandAdapter = new JingSaiPaiMingExpandAdapter();
    private PopupWindow window;
    private String dateParam;
    int yearText, monthOfYearText;

    public JingSaiPaiMingFragment() {
    }

    public static JingSaiPaiMingFragment newInstance() {
        Bundle args = new Bundle();
        JingSaiPaiMingFragment fragment = new JingSaiPaiMingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_jing_sai_pai_ming, container, false);
        initView();
        getData();
        return binding.getRoot();
    }

    private void initView() {
        initPopInnerView();
        binding.selectDateFrame.setOnClickListener(this);
        binding.contentPaiMing.setAdapter(expandAdapter);
        binding.contentPaiMing.setGroupIndicator(null);
        binding.contentPaiMing.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                boolean groupExpanded = parent.isGroupExpanded(groupPosition);
                expandAdapter.setIndicatorState(groupPosition, groupExpanded);
                return false;
            }
        });
    }


    private void getData() {
        PaiMingChildItemBean nameBean = new PaiMingChildItemBean();
        nameBean.setNameSpace(BaseUrl.NAMESPACE_P);
        nameBean.setUserName(User.getCurrentUser().getName());
        nameBean.setArg1(dateParam);

        RequestEnvelope.getRequestEnvelope().setBody(new RequestBody<>(nameBean));
        ApiService.Creator.get().queryRankOfMonthData(RequestEnvelope.getRequestEnvelope())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new FlatMapResponse<ResultModel<List<PaiMingChildItemBean>>>())
                .flatMap(new FlatMapTopRes<List<PaiMingChildItemBean>>())
                .subscribe(new Subscriber<List<PaiMingChildItemBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<PaiMingChildItemBean> paiMingChildItemBeans) {
                        expandAdapter.setList(paiMingChildItemBeans);
                    }
                });

    }


    ActivityDateSelectBinding popBinding;

    private void initPopInnerView() {


        final Calendar calendar = Calendar.getInstance();
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        yearText = calendar.get(Calendar.YEAR);
        monthOfYearText = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
//给显示
        binding.textYear.setText(String.valueOf(yearText));
        binding.textMonth.setText(String.valueOf(monthOfYearText));
//付给参数
        calendar.set(yearText, monthOfYearText - 1, dayOfMonth);
        dateParam = format.format(calendar.getTime());

        popBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.activity_date_select, null, false);
//设置最大的时间限制
        Date date = calendar.getTime();//当前时间 减去一月
        long time = date.getTime();
        popBinding.datePickerView.setMaxDate(time);
        //隐藏日选择
        ((ViewGroup) ((ViewGroup) popBinding.datePickerView.getChildAt(0)).getChildAt(0)).getChildAt(2).setVisibility(View.GONE);
        popBinding.datePickerView.init(yearText, monthOfYearText - 1, dayOfMonth, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYearText = monthOfYear - 1;
                calendar.set(year, monthOfYear - 1, dayOfMonth);
                dateParam = format.format(calendar.getTime());
            }
        });
        popBinding.btnBack.setOnClickListener(new PopClick());
        popBinding.btnDone.setOnClickListener(new PopClick());

        window = new PopupWindow(popBinding.getRoot(), LinearLayout.LayoutParams.MATCH_PARENT, 900);
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

    @Override
    public void onClick(View v) {
        showPopDateSelector();
    }

    private class PopClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_back:
                    window.dismiss();
                    break;
                case R.id.btn_done:
                    binding.textYear.setText(String.valueOf(yearText));
                    binding.textMonth.setText(String.valueOf(monthOfYearText));
                    getData();
                    window.dismiss();
                    break;
            }
        }
    }


}
