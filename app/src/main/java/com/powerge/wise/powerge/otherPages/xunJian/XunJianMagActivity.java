package com.powerge.wise.powerge.otherPages.xunJian;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.hyphenate.util.DensityUtil;
import com.powerge.wise.basestone.heart.ui.XAdapter;
import com.powerge.wise.basestone.heart.ui.XViewHolder;
import com.powerge.wise.powerge.BR;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.bean.XunJianSign;
import com.powerge.wise.powerge.databinding.ActivityXunJianMagBinding;
import com.powerge.wise.powerge.databinding.ItemXunJianSingListBinding;

import java.util.ArrayList;
import java.util.List;

public class XunJianMagActivity extends AppCompatActivity implements XunJianDateFragment.OnDateCehckedListener {
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
        binding.dateContent.setAdapter(new XunJianDatePagerAdapter(getSupportFragmentManager()));
        binding.xunJianTabL.setupWithViewPager(binding.dateContent);

        initContentSign();
    }

    List<XunJianSign> list = new ArrayList<>();

    /*初始换巡检*/
    private void initContentSign() {
        createData();
        XAdapter<XunJianSign, ItemXunJianSingListBinding> adapter = new XAdapter.SimpleAdapter<>(BR.xunJianSign, R.layout.item_xun_jian_sing_list);
        binding.contentSingList.setLayoutManager(new LinearLayoutManager(this));
        binding.contentSingList.setAdapter(adapter);
        adapter.setList(list);
        adapter.setItemClickListener(onItemClickListener);
    }

    XAdapter.OnItemClickListener onItemClickListener = new XAdapter.OnItemClickListener<XunJianSign, ItemXunJianSingListBinding>() {

        @Override
        public void onItemClick(XViewHolder<XunJianSign, ItemXunJianSingListBinding> holder) {
            XunJianDianSignListActivity.start(getBaseContext(), holder.getBinding().getXunJianSign().getXunJianDian());
        }
    };


    private void createData() {
        for (int i = 0; i < 20; i++) {
            XunJianSign sign = new XunJianSign();
            sign.setShouldSign(i + "/" + "5");
            if (i / 2 == 0) {
                sign.setSign(false);
            } else {
                sign.setSign(true);
            }
            sign.setXunJianDian("锅炉房" + i);
            list.add(sign);
        }

    }



    //TODO 选择时间后条件查询
    @Override
    public void onDateCehckedListener(Uri uri) {

    }
}
