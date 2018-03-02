package com.powerge.wise.powerge.otherPages.huaBao;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.databinding.ActivityHuanBaoBinding;

public class HuanBaoActivity extends AppCompatActivity {
    ActivityHuanBaoBinding binding;

    public static void start(Context context) {
        Intent starter = new Intent(context, HuanBaoActivity.class);
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
