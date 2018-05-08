package com.powerge.wise.powerge.otherPages.xunJian;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.powerge.wise.basestone.heart.ui.XAdapter;
import com.powerge.wise.basestone.heart.ui.XViewHolder;
import com.powerge.wise.powerge.BR;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.bean.XunJianFormBean;
import com.powerge.wise.powerge.databinding.ActivityXjFillFormBinding;
import com.powerge.wise.powerge.databinding.ItemXunJianFillFormBinding;

import java.util.ArrayList;
import java.util.List;

public class XjFillFormActivity extends AppCompatActivity {
    public static String extraKeyEdit = "ISEDIT";
    public static String extraKeyTitle = "TITLE";
    private String title;
    private Boolean isEdit;
    public static int requestCode=200;
    private ActivityXjFillFormBinding binding;
    private XAdapter<XunJianFormBean, ItemXunJianFillFormBinding> adapter = new XAdapter.SimpleAdapter<>(BR.data, R.layout.item_xun_jian_fill_form);

    public static void starter(Context context, Boolean isEdit, String title) {
        Intent starter = new Intent(context, XjFillFormActivity.class);
        starter.putExtra(extraKeyEdit, isEdit);
        starter.putExtra(extraKeyTitle, title);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_xj_fill_form);
        isEdit = getIntent().getBooleanExtra(extraKeyEdit, true);
        title = getIntent().getStringExtra(extraKeyTitle);
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
            adapter.setItemClickListener(itemClickListener);
            putNameData();
        } else {  //查看 添加数据
            putData();
        }
    }

    private void putData() {
        List<XunJianFormBean> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            XunJianFormBean formBean = new XunJianFormBean();
            formBean.setItemName("温度" + i);
            formBean.setItemValue("50" + i);
            list.add(formBean);
        }
        adapter.setList(list);
        crossfade();
    }

    private void putNameData() {
        List<XunJianFormBean> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            XunJianFormBean formBean = new XunJianFormBean();
            formBean.setItemName("温度" + i);
            list.add(formBean);
        }
        adapter.setList(list);
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
            XjEdititemActivity.start(XjFillFormActivity.this, holder.getBinding().getData().getItemName());
        }
    };

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
        }
    }
}
