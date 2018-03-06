package com.powerge.wise.powerge.otherPages.PlanTasksMag;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.databinding.ActivityPlanTasksMagBinding;

public class PlanTasksMagActivity extends AppCompatActivity {
    ActivityPlanTasksMagBinding binding;

    public static void start(Context context) {
        Intent starter = new Intent(context, PlanTasksMagActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_plan_tasks_mag);
        binding.title.setText("计划任务管理");
        binding.contentPlan.setAdapter(new PlanTasksViewPagerAd(getSupportFragmentManager()));
        binding.planBaoTabL.setupWithViewPager(binding.contentPlan);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
        }
    }
}
