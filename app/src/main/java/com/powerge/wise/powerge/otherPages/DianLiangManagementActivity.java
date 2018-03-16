package com.powerge.wise.powerge.otherPages;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.hyphenate.util.DensityUtil;
import com.powerge.wise.basestone.heart.network.FlatMapResponse;
import com.powerge.wise.basestone.heart.network.FlatMapTopRes;
import com.powerge.wise.basestone.heart.network.FlatMapTopResList;
import com.powerge.wise.basestone.heart.network.ResultModel;
import com.powerge.wise.basestone.heart.network.ResultModelData;
import com.powerge.wise.basestone.heart.ui.XAdapter;
import com.powerge.wise.basestone.heart.util.LogUtils;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.bean.DianLiangBean;
import com.powerge.wise.powerge.bean.Items;
import com.powerge.wise.powerge.bean.JiZuBean;
import com.powerge.wise.powerge.bean.SheBeiRootBean;
import com.powerge.wise.powerge.bean.User;
import com.powerge.wise.powerge.config.soap.ApiService;
import com.powerge.wise.powerge.config.soap.request.BaseUrl;
import com.powerge.wise.powerge.config.soap.request.RequestBody;
import com.powerge.wise.powerge.config.soap.request.RequestEnvelope;
import com.powerge.wise.powerge.databinding.ActivityDianLiangManagementBinding;
import com.powerge.wise.powerge.helper.EEMsgToastHelper;
import com.wisesignsoft.OperationManagement.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DianLiangManagementActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    ActivityDianLiangManagementBinding binding;

    public static void start(Context context) {
        Intent starter = new Intent(context, DianLiangManagementActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dian_liang_management);
        binding.title.setText(getResources().getStringArray(R.array.item_name_array)[1]);
        getJiZuData();
        binding.jiZuGroups.setOnCheckedChangeListener(this);
    }


    private void getJiZuData() {
        if (User.getCurrentUser() == null) LoginActivity.start(this);
        final JiZuBean jiZuBean = JiZuBean.newInstance();
        jiZuBean.setNameSpace(BaseUrl.NAMESPACE_P);
        jiZuBean.setUserName(User.getCurrentUser().getAccount());


        RequestEnvelope.getRequestEnvelope().setBody(new RequestBody<>(jiZuBean));
        ApiService.Creator.get().queryUnits(RequestEnvelope.getRequestEnvelope())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new FlatMapResponse<ResultModelData<ResultModelData.ReturnValueBean<JiZuBean>>>())
                .flatMap(new FlatMapTopResList<ResultModelData.ReturnValueBean<JiZuBean>>())
                .subscribe(new Subscriber<ResultModelData.ReturnValueBean<JiZuBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        EEMsgToastHelper.newInstance().selectWitch(e.getCause().getMessage());

                    }

                    @Override
                    public void onNext(ResultModelData.ReturnValueBean<JiZuBean> returnValueBean) {
                        createRadioBtnGroup(returnValueBean.getResultList());
                    }
                });
    }

    //TODO 数据
    private void getDianLiangData(String id) {
        if (User.getCurrentUser() == null) LoginActivity.start(this);
        final DianLiangBean dianLiangBean = DianLiangBean.newInstance();
        dianLiangBean.setNameSpace(BaseUrl.NAMESPACE_P);
        dianLiangBean.setUserName(User.getCurrentUser().getAccount());
        dianLiangBean.setArg1(id);
        RequestEnvelope.getRequestEnvelope().setBody(new RequestBody<>(dianLiangBean));
        ApiService.Creator.get().queryPowerGenerationData(RequestEnvelope.getRequestEnvelope())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new FlatMapResponse<ResultModel<DianLiangBean>>())
                .flatMap(new FlatMapTopRes<DianLiangBean>())
                .subscribe(new Subscriber<DianLiangBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        EEMsgToastHelper.newInstance().selectWitch(e.getCause().getMessage());

                    }

                    @Override
                    public void onNext(DianLiangBean returnValueBean) {
                        setDvData(returnValueBean);
                    }
                });
    }

    /**
     * 年月电量赋值
     *
     * @param returnValueBean
     */
    private void setDvData(DianLiangBean returnValueBean) {
        binding.setData(returnValueBean);
    }

    @SuppressLint("ResourceType")
    private void createRadioBtnGroup(List<JiZuBean> resultList) {
        for (int i = 0; i < resultList.size(); i++) {
            JiZuBean jiZuBean = resultList.get(i);
            RadioButton radioButton = new RadioButton(this);
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(DensityUtil.dip2px(this, 100), DensityUtil.dip2px(this, 40));
//            params.setMargins(DensityUtil.dip2px(this, 5), DensityUtil.dip2px(this, 10), DensityUtil.dip2px(this, 5), DensityUtil.dip2px(this, 10));
            radioButton.setLayoutParams(params);
            radioButton.setGravity(Gravity.CENTER);
            radioButton.setTextColor(getResources().getColorStateList(R.drawable.selector_primary_white_text));
            radioButton.setBackgroundResource(R.drawable.selector_primary_white_btn_bg);
            radioButton.setButtonDrawable(android.R.color.transparent);//隐藏单选圆形按钮
            radioButton.setTextSize(15f);
            radioButton.setText(jiZuBean.getName());
            radioButton.setTag(jiZuBean.getId());
            if (i == 0) radioButton.setChecked(true);
            radioButton.setId(i + 1);
            binding.jiZuGroups.addView(radioButton);
        }
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_back) {
            finish();
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        getDianLiangData(String.valueOf(checkedId));
    }
}
