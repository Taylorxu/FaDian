package com.powerge.wise.powerge.otherPages.xunJian;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.powerge.wise.basestone.heart.network.FlatMapResponse;
import com.powerge.wise.basestone.heart.network.FlatMapTopRes;
import com.powerge.wise.basestone.heart.network.ResultModel;
import com.powerge.wise.basestone.heart.ui.XAdapter;
import com.powerge.wise.basestone.heart.ui.XViewHolder;
import com.powerge.wise.powerge.BR;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.bean.User;
import com.powerge.wise.powerge.bean.XunJianFormBean;
import com.powerge.wise.powerge.config.soap.ApiService;
import com.powerge.wise.powerge.config.soap.request.BaseUrl;
import com.powerge.wise.powerge.config.soap.request.RequestBody;
import com.powerge.wise.powerge.config.soap.request.RequestEnvelope;
import com.powerge.wise.powerge.databinding.ActivityXjFillFormBinding;
import com.powerge.wise.powerge.databinding.ItemXunJianFillFormBinding;
import com.powerge.wise.powerge.helper.EEMsgToastHelper;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class XjFillFormActivity extends AppCompatActivity {
    public static String extraKeyEdit = "ISEDIT";
    public static String extraKeyTitle = "TITLE";
    public static String extraKeyPointId = "POINTID ";
    public static String extraResult = "RESULTOKEXTRA";
    private String title, pointId;
    private Boolean isEdit;
    public static int requestCode = 200;
    private ActivityXjFillFormBinding binding;
    private XAdapter<XunJianFormBean, ItemXunJianFillFormBinding> adapter = new XAdapter.SimpleAdapter<>(BR.data, R.layout.item_xun_jian_fill_form);

    public static void starter(Context context, Boolean isEdit, String title, String id) {
        Intent starter = new Intent(context, XjFillFormActivity.class);
        starter.putExtra(extraKeyEdit, isEdit);
        starter.putExtra(extraKeyTitle, title);
        starter.putExtra(extraKeyPointId, id);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_xj_fill_form);
        isEdit = getIntent().getBooleanExtra(extraKeyEdit, true);
        title = getIntent().getStringExtra(extraKeyTitle);
        pointId = getIntent().getStringExtra(extraKeyPointId);
        initView();

    }

    private void initView() {
        binding.title.setText(title + "检查项");
        binding.contentList.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        binding.contentList.setAdapter(adapter);
        binding.contentList.addItemDecoration(new DividerItemDecoration(getBaseContext(), LinearLayout.VERTICAL));
        // 编辑
        if (isEdit) {
            binding.progressBar.setVisibility(View.GONE);
            binding.contentList.setVisibility(View.VISIBLE);
            adapter.setItemClickListener(itemClickListener);
            putNameData();
        } else {  //查看 添加数据
            binding.btSave.setVisibility(View.GONE);
            putData();
        }
    }

    private void putData() {
        List<XunJianFormBean> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            XunJianFormBean formBean = new XunJianFormBean();
            formBean.setCheckItem("温度" + i);
            list.add(formBean);
        }
        adapter.setList(list);
        crossfade();
    }

    private void putNameData() {
        XunJianFormBean bean = new XunJianFormBean();
        bean.setNameSpace(BaseUrl.NAMESPACE_P);
        bean.setUserName(User.getCurrentUser().getName());
        bean.setArg1(pointId);
        RequestEnvelope.getRequestEnvelope().setBody(new RequestBody<>(bean));
        ApiService.Creator.get().queryItemsOfPoint(RequestEnvelope.getRequestEnvelope())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .flatMap(new FlatMapResponse<ResultModel<List<XunJianFormBean>>>())
                .flatMap(new FlatMapTopRes<List<XunJianFormBean>>())
                .subscribe(new Subscriber<List<XunJianFormBean>>() {
                    @Override
                    public void onCompleted() {
                        crossfade();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        EEMsgToastHelper.newInstance().selectWitch(e.getMessage());
                        crossfade();
                    }

                    @SuppressLint("NewApi")
                    @Override
                    public void onNext(List<XunJianFormBean> list) {
                        adapter.setList(list);
                    }
                });

    }

    public void crossfade() {
        binding.contentList.setAlpha(0f);
        binding.contentList.setVisibility(View.VISIBLE);
        binding.contentList.animate().alpha(1f)
                .setDuration(1000)
                .setListener(null);

        binding.progressBar.animate()
                .alpha(0f)
                .setDuration(500)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        binding.progressBar.setVisibility(View.GONE);
                    }
                });

    }

    XAdapter.OnItemClickListener<XunJianFormBean, ItemXunJianFillFormBinding> itemClickListener = new XAdapter.OnItemClickListener<XunJianFormBean, ItemXunJianFillFormBinding>() {
        @Override
        public void onItemClick(XViewHolder<XunJianFormBean, ItemXunJianFillFormBinding> holder) {
            XjEdititemActivity.start(XjFillFormActivity.this, holder.getBinding().getData());
        }
    };

    String options[] = new String[]{"是", "否"};

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == requestCode) {
                XunJianFormBean formBean = data.getParcelableExtra(extraResult);
                for (int i = 0; i < adapter.getList().size(); i++) {
                    if (formBean.getCheckItem().equals(adapter.getItemData(i).getCheckItem())) {
                        if (formBean.getRadioBtResult() == 9) {
                            adapter.getItemData(i).setCheckResult(formBean.getCheckResult());
                        } else {
                            adapter.getItemData(i).setCheckResult(options[formBean.getRadioBtResult()]);
                        }
                        break;
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
        }
    }
}
