package com.powerge.wise.powerge.otherPages;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;

import com.hyphenate.util.DensityUtil;
import com.powerge.wise.basestone.heart.network.FlatMapResponse;
import com.powerge.wise.basestone.heart.network.FlatMapTopRes;
import com.powerge.wise.basestone.heart.network.ResultModel;
import com.powerge.wise.basestone.heart.ui.XAdapter;
import com.powerge.wise.basestone.heart.util.DensityUtils;
import com.powerge.wise.powerge.BR;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.bean.KaoHeChildItemBean;
import com.powerge.wise.powerge.bean.SimpleListTextItem;
import com.powerge.wise.powerge.bean.User;
import com.powerge.wise.powerge.bean.ZhiBIaoValueBean;
import com.powerge.wise.powerge.bean.ZhiBaioNameBean;
import com.powerge.wise.powerge.config.soap.ApiService;
import com.powerge.wise.powerge.config.soap.request.BaseUrl;
import com.powerge.wise.powerge.config.soap.request.RequestBody;
import com.powerge.wise.powerge.config.soap.request.RequestEnvelope;
import com.powerge.wise.powerge.databinding.ActivityJingJiZhiBiaoBinding;
import com.powerge.wise.powerge.databinding.ItemJiZuZhiBiaoBinding;
import com.powerge.wise.powerge.databinding.ItemZbNameListBinding;
import com.powerge.wise.powerge.databinding.ItemZbValueListBinding;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class JingJiZhiBiaoActivity extends AppCompatActivity {
    public static void start(Context context) {
        Intent starter = new Intent(context, JingJiZhiBiaoActivity.class);
        context.startActivity(starter);
    }

    ActivityJingJiZhiBiaoBinding binding;
    List<ZhiBaioNameBean> baioNameList = new ArrayList<>();
    XAdapter<ZhiBaioNameBean, ItemZbNameListBinding> nameListXAdapter = new XAdapter.SimpleAdapter<>(BR.data, R.layout.item_zb_name_list);
    XAdapter<ZhiBIaoValueBean.DetailsBean, ItemZbValueListBinding> valueListXAdapter = new XAdapter.SimpleAdapter<>(BR.data, R.layout.item_zb_value_list);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_jing_ji_zhi_biao);
        binding.title.setText(getResources().getStringArray(R.array.item_name_array)[2]);
        initView();
        getZBNameList();
        getZBValueList();
    }

    private void initView() {
        binding.zhiBiaoListId.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        binding.zhiBiaoListId.setAdapter(nameListXAdapter);

    }

    public void onClick(View view) {
        if (view.getId() == R.id.btn_back) {
            finish();
        }
    }

    /**
     * 获取指标名称列表
     */
    private void getZBNameList() {
        ZhiBaioNameBean nameBean = new ZhiBaioNameBean();
        nameBean.setNameSpace(BaseUrl.NAMESPACE_P);
        nameBean.setUserName(User.getCurrentUser().getAccount());
        RequestEnvelope.getRequestEnvelope().setBody(new RequestBody<>(nameBean));
        ApiService.Creator.get().queryEconomicIndicatorsList(RequestEnvelope.getRequestEnvelope())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new FlatMapResponse<ResultModel<List<ZhiBaioNameBean>>>())
                .flatMap(new FlatMapTopRes<List<ZhiBaioNameBean>>())
                .subscribe(new Subscriber<List<ZhiBaioNameBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<ZhiBaioNameBean> list) {
                        if (list.size() > 0) nameListXAdapter.setList(list);
                    }
                });

    }

    private void getZBValueList() {
        ZhiBIaoValueBean valueBean = new ZhiBIaoValueBean();
        valueBean.setNameSpace(BaseUrl.NAMESPACE_P);
        valueBean.setUserName(User.getCurrentUser().getAccount());
        RequestEnvelope.getRequestEnvelope().setBody(new RequestBody<>(valueBean));
        ApiService.Creator.get().queryEconomicIndicators(RequestEnvelope.getRequestEnvelope())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new FlatMapResponse<ResultModel<List<ZhiBIaoValueBean>>>())
                .flatMap(new FlatMapTopRes<List<ZhiBIaoValueBean>>())
                .subscribe(new Subscriber<List<ZhiBIaoValueBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<ZhiBIaoValueBean> list) {
                        if (list.size() > 0) createZBValueView(list);
                    }
                });
    }


    private void createZBValueView(List<ZhiBIaoValueBean> list) {
        WindowManager wm1 = this.getWindowManager();
        int width1 = wm1.getDefaultDisplay().getWidth() / 2 - 200;
        for (int i = 0; i < list.size(); i++) {
            LinearLayout view = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.item_ji_zu_zhi_biao, binding.contentJiZu, false);
          /*  LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(width1, LinearLayout.LayoutParams.WRAP_CONTENT );
            view.setLayoutParams(param);*/
            ItemJiZuZhiBiaoBinding itemBiaoBinding = DataBindingUtil.bind(view.getRootView());
            itemBiaoBinding.zbValueList.setLayoutManager(new LinearLayoutManager(getBaseContext()));
            itemBiaoBinding.zbValueList.setAdapter(valueListXAdapter);
            valueListXAdapter.setList(list.get(i).getDetails());
            itemBiaoBinding.setItemZhiBiao(list.get(i));
            binding.contentJiZu.addView(view);
        }
    }
}
