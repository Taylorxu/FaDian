package com.powerge.wise.powerge.otherPages.JingSai;


import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.powerge.wise.basestone.heart.network.FlatMapResponse;
import com.powerge.wise.basestone.heart.network.FlatMapTopRes;
import com.powerge.wise.basestone.heart.network.ResultModel;
import com.powerge.wise.basestone.heart.util.DensityUtil;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.bean.PaiMingChildItemBean;
import com.powerge.wise.powerge.bean.User;
import com.powerge.wise.powerge.config.soap.ApiService;
import com.powerge.wise.powerge.config.soap.request.BaseUrl;
import com.powerge.wise.powerge.config.soap.request.RequestBody;
import com.powerge.wise.powerge.config.soap.request.RequestEnvelope;
import com.powerge.wise.powerge.databinding.FragmentJingSaiPaiMingBinding;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class JingSaiPaiMingFragment extends Fragment {
    FragmentJingSaiPaiMingBinding binding;
    JingSaiPaiMingExpandAdapter expandAdapter = new JingSaiPaiMingExpandAdapter();

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
        getData();
        initView();
        return binding.getRoot();
    }

    private void initView() {
        createMonthGroup();
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


    @SuppressLint("ResourceType")
    private void createMonthGroup() {
        for (int i = 1; i < 13; i++) {
            RadioButton radioButton = new RadioButton(getContext());
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(DensityUtil.dip2px(getContext(), 60), DensityUtil.dip2px(getContext(), 34));
            params.setMargins(DensityUtil.dip2px(getContext(), 5), DensityUtil.dip2px(getContext(), 10), DensityUtil.dip2px(getContext(), 5), DensityUtil.dip2px(getContext(), 10));
            radioButton.setLayoutParams(params);
            radioButton.setGravity(Gravity.CENTER);
            radioButton.setTextColor(getResources().getColorStateList(R.drawable.selector_primary_gray_text));
            radioButton.setBackgroundResource(R.drawable.selector_primary_white_btn_bg_border);
            radioButton.setButtonDrawable(android.R.color.transparent);//隐藏单选圆形按钮
            radioButton.setTextSize(14f);
            radioButton.setText(i + "月");
            radioButton.setTag(i + "月");
            if (i == 1) radioButton.setChecked(true);
            radioButton.setId(i);
            binding.monthGroup.addView(radioButton);

        }
    }

    private void getData() {
        PaiMingChildItemBean nameBean = new PaiMingChildItemBean();
        nameBean.setNameSpace(BaseUrl.NAMESPACE_P);
        nameBean.setUserName(User.getCurrentUser().getName());
        nameBean.setArg1("2018-03");

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

}
