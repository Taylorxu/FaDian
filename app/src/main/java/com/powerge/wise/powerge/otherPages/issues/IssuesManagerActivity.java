package com.powerge.wise.powerge.otherPages.issues;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.powerge.wise.basestone.heart.util.DensityUtil;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.databinding.ActivityIssuesManagerBinding;
import com.powerge.wise.powerge.operationProjo.net.ui.activity.NewWorkOrderActivity;

public class IssuesManagerActivity extends AppCompatActivity {
    public static void start(Context context) {
        Intent starter = new Intent(context, IssuesManagerActivity.class);
        context.startActivity(starter);
    }

    ActivityIssuesManagerBinding binding;
    String[] topBtnName = new String[]{"待处理", "进行中", "已完成"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_issues_manager);
        initView();
        createRadioBtnGroup();
    }

    private void initView() {
        IssuesFragmentsAdapter adapter = new IssuesFragmentsAdapter(getSupportFragmentManager());
        binding.contentIssuesVp.setAdapter(adapter);
        binding.jiZuGroups.setOnCheckedChangeListener(OnNISListener);
        binding.contentIssuesVp.addOnPageChangeListener(onPageChangeListener);
    }


    @SuppressLint("ResourceType")
    private void createRadioBtnGroup() {
        for (int i = 0; i < 3; i++) {
            RadioButton radioButton = new RadioButton(this);
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(DensityUtil.dip2px(this, 100), DensityUtil.dip2px(this, 40));
            params.setMargins(DensityUtil.dip2px(this, 5), DensityUtil.dip2px(this, 10), DensityUtil.dip2px(this, 5), DensityUtil.dip2px(this, 10));
            radioButton.setLayoutParams(params);
            radioButton.setGravity(Gravity.CENTER);
            radioButton.setTextColor(getResources().getColorStateList(R.drawable.selector_primary_white_text));
            radioButton.setBackgroundResource(R.drawable.selector_primary_white_btn_bg);
            radioButton.setButtonDrawable(android.R.color.transparent);//隐藏单选圆形按钮
            radioButton.setTextSize(15f);
            radioButton.setText(topBtnName[i]);
            radioButton.setTag(i);
            if (i == 0) radioButton.setChecked(true);
            radioButton.setId(i);
            binding.jiZuGroups.addView(radioButton);
        }
    }

    RadioGroup.OnCheckedChangeListener OnNISListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            binding.contentIssuesVp.setCurrentItem(checkedId, false);
        }
    };


    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            RadioButton radioButton = (RadioButton) binding.jiZuGroups.getChildAt(position);
            radioButton.setChecked(true);

        }
    };

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_create:
                Intent intent = new Intent(this, NewWorkOrderActivity.class);
                startActivity(intent);
                break;
        }
    }
}
