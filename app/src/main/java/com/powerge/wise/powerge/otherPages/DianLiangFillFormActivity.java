package com.powerge.wise.powerge.otherPages;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.databinding.ActivityDianLiangFillFormBinding;

public class DianLiangFillFormActivity extends AppCompatActivity {
    ActivityDianLiangFillFormBinding binding;

    public static void start(Context context) {
        Intent starter = new Intent(context, DianLiangFillFormActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dian_liang_fill_form);
        binding.title.setText(getResources().getStringArray(R.array.title_array)[0]);
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_back) {
            finish();
        }
    }
}
