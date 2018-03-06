package com.powerge.wise.powerge.otherPages.xunJian;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.hyphenate.util.DensityUtil;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.databinding.ActivityXunJianMagBinding;

public class XunJianMagActivity extends AppCompatActivity {
    ActivityXunJianMagBinding binding;

    public static void start(Context context) {
        Intent starter = new Intent(context, XunJianMagActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_xun_jian_mag);
        binding.title.setText("巡检管理");
        initView();
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
        }
    }

    private void initView() {
        //初始化 月份 buttion 列表
        createMonthGroup();
    }

    @SuppressLint("ResourceType")
    private void createMonthGroup() {
        for (int i = 0; i < 13; i++) {
            RadioButton radioButton = new RadioButton(this);
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(DensityUtil.dip2px(this, 60), DensityUtil.dip2px(this, 34));
            params.setMargins(DensityUtil.dip2px(this, 5), DensityUtil.dip2px(this, 10), DensityUtil.dip2px(this, 5), DensityUtil.dip2px(this, 10));
            radioButton.setLayoutParams(params);
            radioButton.setGravity(Gravity.CENTER);
            radioButton.setTextColor(getResources().getColorStateList(R.drawable.selector_primary_gray_text));
            radioButton.setBackgroundResource(R.drawable.selector_primary_white_btn_bg_border);
            radioButton.setButtonDrawable(android.R.color.transparent);//隐藏单选圆形按钮
            radioButton.setTextSize(14f);
            if (i == 0) {
                radioButton.setText("全部");
                radioButton.setTag("全部");
                radioButton.setChecked(true);

            } else {
                radioButton.setText(i + "月");
                radioButton.setTag(i + "月");
            }
            radioButton.setId(i);
            binding.monthGroup.addView(radioButton);

        }
    }
}
