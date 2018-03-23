package com.powerge.wise.powerge.otherPages.huaBao;


import android.annotation.SuppressLint;
import android.content.Context;
import android.database.DatabaseUtils;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ExpandableListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.hyphenate.util.DensityUtil;
import com.powerge.wise.basestone.heart.network.FlatMapResponse;
import com.powerge.wise.basestone.heart.network.FlatMapTopRes;
import com.powerge.wise.basestone.heart.network.ResultModel;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.bean.HuanBaoBean;
import com.powerge.wise.powerge.bean.JiZuBean;
import com.powerge.wise.powerge.bean.KaoHeChildItemBean;
import com.powerge.wise.powerge.bean.User;
import com.powerge.wise.powerge.config.soap.ApiService;
import com.powerge.wise.powerge.config.soap.request.BaseUrl;
import com.powerge.wise.powerge.config.soap.request.RequestBody;
import com.powerge.wise.powerge.config.soap.request.RequestEnvelope;
import com.powerge.wise.powerge.databinding.FragmentHuanBaoKaoHeBinding;
import com.powerge.wise.powerge.helper.EEMsgToastHelper;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *
 */
public class HuanBaoKaoHeFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {
    HuanBaoKaoHeFragment kaoHeFragment;
    List<JiZuBean> jiZuList;
    private int ji_zu_checked;
    FragmentHuanBaoKaoHeBinding binding;
    KaoHeExpandAdapter expandAdapter = new KaoHeExpandAdapter();

    public HuanBaoKaoHeFragment newInstance() {
        if (kaoHeFragment == null) {
            kaoHeFragment = new HuanBaoKaoHeFragment();
        }
        return kaoHeFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        jiZuList = getActivity().getIntent().getParcelableArrayListExtra(JiZuBean.INTENTKEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_huan_bao_kao_he, container, false);
        getData();
        iniView();

        return binding.getRoot();
    }

    private void iniView() {
        createRadioBtnGroup();
        binding.jiZuGroups.setOnCheckedChangeListener(this);
        binding.contentKaoHe.setAdapter(expandAdapter);
        binding.contentKaoHe.setGroupIndicator(null);
        binding.contentKaoHe.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                boolean groupExpanded = parent.isGroupExpanded(groupPosition);
                expandAdapter.setIndicatorState(groupPosition, groupExpanded);
                return false;
            }
        });
    }


    private void getData() {
        KaoHeChildItemBean kaoHe = new KaoHeChildItemBean();
        kaoHe.setNameSpace(BaseUrl.NAMESPACE_P);
        kaoHe.setUserName(User.getCurrentUser().getAccount());
        kaoHe.setArg1(String.valueOf(ji_zu_checked));
        RequestEnvelope.getRequestEnvelope().setBody(new RequestBody<>(kaoHe));
        ApiService.Creator.get().queryEnvAssessmentMonthly(RequestEnvelope.getRequestEnvelope())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new FlatMapResponse<ResultModel<List<KaoHeChildItemBean>>>())
                .flatMap(new FlatMapTopRes<List<KaoHeChildItemBean>>())
                .subscribe(new Subscriber<List<KaoHeChildItemBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        EEMsgToastHelper.newInstance().selectWitch(e.getCause().getMessage());

                    }

                    @Override
                    public void onNext(List<KaoHeChildItemBean> list) {
                        if (list.size() > 0)
                            expandAdapter.setList(list);
                        else
                            expandAdapter.setList(null);
                    }
                });
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        ji_zu_checked = checkedId;
        getData();
    }

    @SuppressLint("ResourceType")
    private void createRadioBtnGroup() {
        if (jiZuList.size() == 0) return;
        for (int i = 0; i < jiZuList.size(); i++) {
            JiZuBean jiZuBean = jiZuList.get(i);
            RadioButton radioButton = new RadioButton(getContext());
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(DensityUtil.dip2px(getContext(), 100), DensityUtil.dip2px(getContext(), 40));
            params.setMargins(DensityUtil.dip2px(getContext(), 5), DensityUtil.dip2px(getContext(), 10), DensityUtil.dip2px(getContext(), 5), DensityUtil.dip2px(getContext(), 10));
            radioButton.setLayoutParams(params);
            radioButton.setGravity(Gravity.CENTER);
            radioButton.setTextColor(getResources().getColorStateList(R.drawable.selector_primary_white_text));
            radioButton.setBackgroundResource(R.drawable.selector_primary_white_btn_bg);
            radioButton.setButtonDrawable(android.R.color.transparent);//隐藏单选圆形按钮
            radioButton.setTextSize(15f);
            radioButton.setText(jiZuBean.getName());
            if (i == 0) radioButton.setChecked(true);
            radioButton.setId(Integer.parseInt(jiZuBean.getId()));
            binding.jiZuGroups.addView(radioButton);
        }
    }


}
