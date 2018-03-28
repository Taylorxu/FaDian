package com.powerge.wise.powerge.otherPages.JingSai;

import android.content.Context;
import android.content.Intent;
import android.database.DatabaseUtils;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;

import com.powerge.wise.basestone.heart.network.FlatMapResponse;
import com.powerge.wise.basestone.heart.network.FlatMapTopRes;
import com.powerge.wise.basestone.heart.network.ResultModel;
import com.powerge.wise.basestone.heart.ui.XAdapter;
import com.powerge.wise.basestone.heart.ui.XViewHolder;
import com.powerge.wise.powerge.BR;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.bean.JiZuBean;
import com.powerge.wise.powerge.bean.User;
import com.powerge.wise.powerge.bean.ZhiBIaoValueBean;
import com.powerge.wise.powerge.bean.ZhiBaioNameBean;
import com.powerge.wise.powerge.config.soap.ApiService;
import com.powerge.wise.powerge.config.soap.request.BaseUrl;
import com.powerge.wise.powerge.config.soap.request.RequestBody;
import com.powerge.wise.powerge.config.soap.request.RequestEnvelope;
import com.powerge.wise.powerge.databinding.ActivityJingSaiBinding;

import com.powerge.wise.basestone.heart.network.Notification;
import com.powerge.wise.powerge.databinding.ItemJingSaiPopBinding;
import com.powerge.wise.powerge.databinding.ItemJingSaiRadioPopBinding;
import com.powerge.wise.powerge.databinding.ItemZbNameListBinding;
import com.powerge.wise.powerge.databinding.ItemZbValueListBinding;
import com.powerge.wise.powerge.databinding.JingSaiPopListBinding;
import com.powerge.wise.powerge.otherPages.JingJiZhiBiaoActivity;
import com.wisesignsoft.OperationManagement.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class JingSaiActivity extends AppCompatActivity implements View.OnClickListener {
    public Subscription notification;
    private ArrayList<JiZuBean> jiZuList;
    ActivityJingSaiBinding binding;
    PopupWindow window = null;
    RadioButton oldRadioBtn = null, oldRadioBtn1 = null;

    public static void start(Context context, List<JiZuBean> jiZuList) {
        Intent starter = new Intent(context, JingSaiActivity.class);
        starter.putParcelableArrayListExtra(JiZuBean.INTENTKEY, (ArrayList<? extends Parcelable>) jiZuList);
        context.startActivity(starter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        notification = Notification.register(action1);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_jing_sai);
        jiZuList = getIntent().getParcelableArrayListExtra(JiZuBean.INTENTKEY);
        binding.title.setText(getResources().getStringArray(R.array.item_name_array)[7]);
        binding.jingSaiMainPage.setAdapter(new JingSaiFragmentAdapter(getSupportFragmentManager()));
        binding.jingSaiTabL.setupWithViewPager(binding.jingSaiMainPage);
        getZBNameList();
    }


    /**
     * 获取指标名称列表
     */
    private void getZBNameList() {
        ZhiBaioNameBean nameBean = new ZhiBaioNameBean();
        nameBean.setNameSpace(BaseUrl.NAMESPACE_P);
        nameBean.setUserName(User.getCurrentUser().getName());
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
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<ZhiBaioNameBean> list) {
                        if (list.size() > 0) {
                            zhibiaoAdapter.setList(list);
                        }
                    }
                });

    }

    XAdapter<ZhiBaioNameBean, ItemJingSaiRadioPopBinding> zhibiaoAdapter = new XAdapter.SimpleAdapter<>(BR.data, R.layout.item_jing_sai_radio_pop);
    XAdapter<JiZuBean, ItemJingSaiPopBinding> jiZuAdapter = new XAdapter.SimpleAdapter<>(BR.data, R.layout.item_jing_sai_pop);

    private void showPop(int type) {
        JingSaiPopListBinding popBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.jing_sai_pop_list, null, false);
        if (window != null) {
            window.dismiss();
        }
        popBinding.jingSaiPopList.setLayoutManager(new LinearLayoutManager(this));
        if (type == 0) {
            popBinding.jingSaiPopList.setAdapter(zhibiaoAdapter);
            zhibiaoAdapter.setItemClickListener(new XAdapter.OnItemClickListener<ZhiBaioNameBean, ItemJingSaiRadioPopBinding>() {
                @Override
                public void onItemClick(XViewHolder<ZhiBaioNameBean, ItemJingSaiRadioPopBinding> holder) {
                    holder.getBinding().radioBtn.setChecked(true);
                    if (oldRadioBtn != null) oldRadioBtn.setChecked(false);
                    oldRadioBtn = holder.getBinding().radioBtn;
                }
            });
        } else {
            jiZuAdapter.setList(jiZuList);
            popBinding.jingSaiPopList.setAdapter(jiZuAdapter);
            zhibiaoAdapter.setItemClickListener(new XAdapter.OnItemClickListener<JiZuBean, ItemJingSaiPopBinding>() {
                @Override
                public void onItemClick(XViewHolder<JiZuBean, ItemJingSaiPopBinding> holder) {
                    holder.getBinding().radioBtn.setChecked(true);
                    if (oldRadioBtn1 != null) oldRadioBtn1.setChecked(false);
                    oldRadioBtn1 = holder.getBinding().radioBtn;
                }
            });
        }
        popBinding.btnSure.setOnClickListener(this);
        popBinding.btnCancel.setOnClickListener(this);
        window = new PopupWindow(popBinding.getRoot(), LinearLayout.LayoutParams.MATCH_PARENT, 900);

        window.setAnimationStyle(R.style.popup_window_anim);
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8F8F8")));
        window.setFocusable(false);
        window.setOutsideTouchable(false);
        window.update();
        window.showAtLocation(popBinding.getRoot(), Gravity.BOTTOM, 0, 0);

    }


    Action1<Notification> action1 = new Action1<Notification>() {
        @Override
        public void call(Notification notification) {
            if (notification.getCode() == 001) {
                showPop(0);
            } else if (notification.getCode() == 002) {
                showPop(1);
            }
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        notification.unsubscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        notification.unsubscribe();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sure:
                ToastUtil.toast(this, "sure");
                break;
            case R.id.btn_cancel:
                window.dismiss();
                break;
        }
    }


}
