package com.powerge.wise.powerge.otherPages.huaBao;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.bean.JiZuBean;
import com.powerge.wise.powerge.databinding.ActivityHuanBaoBinding;

import java.util.ArrayList;
import java.util.List;

public class HuanBaoActivity extends AppCompatActivity {
    ActivityHuanBaoBinding binding;

    public static void start(Context context, List<JiZuBean> jiZuList) {
        Intent starter = new Intent(context, HuanBaoActivity.class);
        starter.putParcelableArrayListExtra(JiZuBean.INTENTKEY, (ArrayList<? extends Parcelable>) jiZuList);
        context.startActivity(starter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_huan_bao);
        binding.title.setText(getResources().getStringArray(R.array.item_name_array)[8]);
        binding.huanBaoMainPage.setAdapter(new HuanBaoFragmentAdapter(getSupportFragmentManager()));
        binding.huanBaoTabL.setupWithViewPager(binding.huanBaoMainPage);

    }

    public void onClick(View view) {
        finish();
    }
}
