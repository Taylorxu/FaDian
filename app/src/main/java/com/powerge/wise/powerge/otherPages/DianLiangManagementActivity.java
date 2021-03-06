package com.powerge.wise.powerge.otherPages;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.powerge.wise.basestone.heart.network.FlatMapResponse;
import com.powerge.wise.basestone.heart.network.FlatMapTopRes;
import com.powerge.wise.basestone.heart.network.ResultModel;
import com.powerge.wise.basestone.heart.util.DensityUtil;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.bean.DianLiangBean;
import com.powerge.wise.powerge.bean.JiZuBean;
import com.powerge.wise.powerge.bean.User;
import com.powerge.wise.powerge.config.soap.ApiService;
import com.powerge.wise.powerge.config.soap.request.BaseUrl;
import com.powerge.wise.powerge.config.soap.request.RequestBody;
import com.powerge.wise.powerge.config.soap.request.RequestEnvelope;
import com.powerge.wise.powerge.databinding.ActivityDianLiangManagementBinding;
import com.powerge.wise.powerge.helper.EEMsgToastHelper;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DianLiangManagementActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    ActivityDianLiangManagementBinding binding;
    private ArrayList<JiZuBean> jiZuList;

    public static void start(Context context, List<JiZuBean> jiZuList) {
        Intent starter = new Intent(context, DianLiangManagementActivity.class);
        starter.putParcelableArrayListExtra(JiZuBean.INTENTKEY, (ArrayList<? extends Parcelable>) jiZuList);
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
        jiZuList = getIntent().getParcelableArrayListExtra(JiZuBean.INTENTKEY);
        createRadioBtnGroup(jiZuList);
        binding.jiZuGroups.setOnCheckedChangeListener(this);
        ((RadioButton)binding.jiZuGroups.getChildAt(0)).setChecked(true);
    }


    //TODO 数据
    private void getDianLiangData(String id) {
        if (User.getCurrentUser() == null) LoginActivity.start(this);
        final DianLiangBean dianLiangBean = DianLiangBean.newInstance();
        dianLiangBean.setNameSpace(BaseUrl.NAMESPACE_P);
        dianLiangBean.setUserName(User.getCurrentUser().getName());
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
                        EEMsgToastHelper.newInstance().selectWitch(e.getMessage());

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
            params.setMargins(DensityUtil.dip2px(this, 5), DensityUtil.dip2px(this, 10), DensityUtil.dip2px(this, 5), DensityUtil.dip2px(this, 10));
            radioButton.setLayoutParams(params);
            radioButton.setGravity(Gravity.CENTER);
            radioButton.setTextColor(getResources().getColorStateList(R.drawable.selector_primary_white_text));
            radioButton.setBackgroundResource(R.drawable.selector_primary_white_btn_bg);
            radioButton.setButtonDrawable(android.R.color.transparent);//隐藏单选圆形按钮
            radioButton.setTextSize(15f);
            radioButton.setText(jiZuBean.getName());
            radioButton.setTag(jiZuBean.getId());
            radioButton.setId(i);
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
        getDianLiangData(findViewById(checkedId).getTag().toString());


    }
}
