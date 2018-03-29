package com.powerge.wise.powerge.otherPages;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.powerge.wise.basestone.heart.network.FlatMapResponse;
import com.powerge.wise.basestone.heart.network.FlatMapTopRes;
import com.powerge.wise.basestone.heart.network.ResultModel;
import com.powerge.wise.basestone.heart.ui.XAdapter;
import com.powerge.wise.powerge.BR;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.bean.FuHeHourDataBean;
import com.powerge.wise.powerge.bean.User;
import com.powerge.wise.powerge.config.soap.ApiService;
import com.powerge.wise.powerge.config.soap.request.BaseUrl;
import com.powerge.wise.powerge.config.soap.request.RequestBody;
import com.powerge.wise.powerge.config.soap.request.RequestEnvelope;
import com.powerge.wise.powerge.databinding.ActivityFuHeOneHourDataBinding;
import com.powerge.wise.powerge.databinding.ItemFhOneDataListBinding;
import com.powerge.wise.powerge.helper.EEMsgToastHelper;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FuHeOneHourDataActivity extends AppCompatActivity {
    static String FHHOURPKEY = "FHHOURPKEY";
    ActivityFuHeOneHourDataBinding binding;

    public static void start(Context context, String jizu) {
        Intent starter = new Intent(context, FuHeOneHourDataActivity.class);
        starter.putExtra(FHHOURPKEY, jizu);
        context.startActivity(starter);
    }

    XAdapter<FuHeHourDataBean, ItemFhOneDataListBinding> adapter = new XAdapter.SimpleAdapter<>(BR.data, R.layout.item_fh_one_data_list);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fu_he_one_hour_data);
        getData(getIntent().getStringExtra(FHHOURPKEY));
        binding.title.setText("一小时数据");
        binding.contentData.setLayoutManager(new LinearLayoutManager(this));
        binding.contentData.setAdapter(adapter);


    }

    private void getData(String jizu) {
        FuHeHourDataBean dataBean = new FuHeHourDataBean();
        dataBean.setNameSpace(BaseUrl.NAMESPACE_P);
        dataBean.setUserName(User.getCurrentUser().getName());
        dataBean.setArg1(jizu);
        RequestEnvelope.getRequestEnvelope().setBody(new RequestBody<>(dataBean));
        ApiService.Creator.get().queryLoadDetailsInHour(RequestEnvelope.getRequestEnvelope())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .flatMap(new FlatMapResponse<ResultModel<List<FuHeHourDataBean>>>())
                .flatMap(new FlatMapTopRes<List<FuHeHourDataBean>>())
                .subscribe(new Subscriber<List<FuHeHourDataBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        EEMsgToastHelper.newInstance().selectWitch(e.getMessage());
                    }

                    @Override
                    public void onNext(List<FuHeHourDataBean> fuHeHourDataBeans) {
                        if (fuHeHourDataBeans.size() > 0) adapter.setList(fuHeHourDataBeans);
                    }
                });
    }

    public void onClick(View view) {
        finish();
    }
}
