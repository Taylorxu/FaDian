package com.powerge.wise.powerge.otherPages.JingSai;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.powerge.wise.basestone.heart.network.FlatMapResponse;
import com.powerge.wise.basestone.heart.network.FlatMapTopRes;
import com.powerge.wise.basestone.heart.network.Notification;
import com.powerge.wise.basestone.heart.network.ResultModel;
import com.powerge.wise.basestone.heart.ui.WFragment;
import com.powerge.wise.basestone.heart.ui.XAdapter;
import com.powerge.wise.basestone.heart.ui.XViewHolder;
import com.powerge.wise.basestone.heart.util.RxBus;
import com.powerge.wise.powerge.BR;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.bean.Items;
import com.powerge.wise.powerge.bean.JingSaiDeFenBean;
import com.powerge.wise.powerge.bean.SimpleListTextItem;
import com.powerge.wise.powerge.bean.User;
import com.powerge.wise.powerge.bean.ZhiBaioNameBean;
import com.powerge.wise.powerge.config.soap.ApiService;
import com.powerge.wise.powerge.config.soap.request.BaseUrl;
import com.powerge.wise.powerge.config.soap.request.RequestBody;
import com.powerge.wise.powerge.config.soap.request.RequestEnvelope;
import com.powerge.wise.powerge.databinding.FragmentJingSaiDeFenBinding;
import com.powerge.wise.powerge.databinding.ItemJingSaiDeFenBinding;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


public class JingSaiDeFenFragment extends WFragment<FragmentJingSaiDeFenBinding> implements View.OnClickListener {
    String unitName = "9999", indicator = "9999";

    public JingSaiDeFenFragment() {
    }

    public static JingSaiDeFenFragment newInstance() {
        JingSaiDeFenFragment fragment = new JingSaiDeFenFragment();
        return fragment;
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        getData();
        initView();
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_jing_sai_de_fen;
    }

    XAdapter<JingSaiDeFenBean.ResultListBean, ItemJingSaiDeFenBinding> adapter = new XAdapter.SimpleAdapter(BR.data, R.layout.item_jing_sai_de_fen);


    private void initView() {
        getBinding().btnAllZb.setOnClickListener(this);
        getBinding().btnAllJz.setOnClickListener(this);
        getBinding().contentDeFenList.setLayoutManager(new LinearLayoutManager(getContext()));
        getBinding().contentDeFenList.setAdapter(adapter);

    }


    public void getData() {

        if (getArguments() != null) {
            unitName = getArguments().getString("unitName");
            indicator = getArguments().getString("indicator");
            if (!indicator.equals("9999")) {
                getBinding().textAlign.setText(indicator + "指标");
            } else {
                getBinding().textAlign.setText("全部指标");
            }

            if (!unitName.equals("9999")) {
                getBinding().textAlign1.setText(unitName + "机组");
            } else {
                getBinding().textAlign1.setText("全部机组");
            }
        }
        JingSaiDeFenBean nameBean = new JingSaiDeFenBean();
        nameBean.setNameSpace(BaseUrl.NAMESPACE_P);
        nameBean.setUserName(User.getCurrentUser().getName());
        nameBean.setArg1(unitName);
        nameBean.setArg2(indicator);

        RequestEnvelope.getRequestEnvelope().setBody(new RequestBody<>(nameBean));
        ApiService.Creator.get().queryGroupScore(RequestEnvelope.getRequestEnvelope())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new FlatMapResponse<ResultModel<JingSaiDeFenBean>>())
                .flatMap(new FlatMapTopRes<JingSaiDeFenBean>())
                .subscribe(new Subscriber<JingSaiDeFenBean>() {
                    @Override
                    public void onCompleted() {
                        crossfade();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(JingSaiDeFenBean deFenBean) {
                        if (deFenBean != null) {
                            getBinding().textDayScore.setText(deFenBean.getDayAVGScore());
                            getBinding().textMonthScore.setText(deFenBean.getMonthAVGScore());
                            adapter.setList(deFenBean.getResultList());
                        }
                    }
                });

    }

    public void crossfade() {
        getBinding().contentDeFenList.setAlpha(0f);
        getBinding().contentDeFenList.setVisibility(View.VISIBLE);
        getBinding().contentDeFenList.animate().alpha(1f)
                .setDuration(1500)
                .setListener(null);

        getBinding().progressBar.animate()
                .alpha(0f)
                .setDuration(1500)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        getBinding().progressBar.setVisibility(View.GONE);
                    }
                });

    }

    @Override
    public void call(Notification notification) {
        if (notification.getCode() == 003) {
            getData();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_all_jz:
                RxBus.getDefault().post(new Notification(001, 0));
                break;
            case R.id.btn_all_zb:
                RxBus.getDefault().post(new Notification(002, 0));
                break;
        }
    }


}
