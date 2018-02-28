package com.powerge.wise.powerge.otherPages;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.View;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.databinding.ActivityDianLiangManagementBinding;

import java.util.ArrayList;

public class DianLiangManagementActivity extends AppCompatActivity {

    ActivityDianLiangManagementBinding binding;

    public static void start(Context context) {
        Intent starter = new Intent(context, DianLiangManagementActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dian_liang_management);
        binding.title.setText(getResources().getStringArray(R.array.item_name_array)[1]);
    }


    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_back) {
            finish();
        } else if (id == R.id.btn_fill_form) {
            DianLiangFillFormActivity.start(this);
        }
    }
}
