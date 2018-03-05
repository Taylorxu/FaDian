package com.powerge.wise.powerge.otherPages.queXian;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.databinding.ActivityQueXianMagBinding;

public class QueXianMagActivity extends AppCompatActivity {
    ActivityQueXianMagBinding binding;

    public static void start(Context context) {
        Intent starter = new Intent(context, QueXianMagActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_que_xian_mag);
        binding.title.setText(getResources().getStringArray(R.array.item_name_array)[9]);
        initView();
    }

    private void initView() {
        QxVPAdapter qxVPAdapter = new QxVPAdapter(getSupportFragmentManager());
        binding.contentQxList.setAdapter(qxVPAdapter);
        binding.queXianTabL.setupWithViewPager(binding.contentQxList);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;

            case R.id.ic_bao_biao:
                break;
            case R.id.text_1:
                break;
        }
    }

}
