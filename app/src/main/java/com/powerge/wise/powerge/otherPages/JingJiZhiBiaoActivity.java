package com.powerge.wise.powerge.otherPages;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.hyphenate.util.DensityUtil;
import com.powerge.wise.basestone.heart.util.DensityUtils;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.bean.SimpleListTextItem;
import com.powerge.wise.powerge.databinding.ActivityJingJiZhiBiaoBinding;
import com.powerge.wise.powerge.databinding.ItemJiZuZhiBiaoBinding;

public class JingJiZhiBiaoActivity extends AppCompatActivity {
    public static void start(Context context) {
        Intent starter = new Intent(context, JingJiZhiBiaoActivity.class);
        context.startActivity(starter);
    }

    ActivityJingJiZhiBiaoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_jing_ji_zhi_biao);
        binding.title.setText(getResources().getStringArray(R.array.item_name_array)[2]);
        initView();
    }

    private void initView() {

        WindowManager wm1 = this.getWindowManager();
        int width1 = wm1.getDefaultDisplay().getWidth() / 2 - 200;
        for (int i = 0; i < 2; i++) {
            LinearLayout view = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.item_ji_zu_zhi_biao, binding.contentJiZu, false);
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(width1,
                    LinearLayout.LayoutParams.FILL_PARENT, 1.0f);
            view.setLayoutParams(param);
            ItemJiZuZhiBiaoBinding itemBiaoBinding = DataBindingUtil.bind(view.getRootView());
            SimpleListTextItem textItem = new SimpleListTextItem();
            textItem.setTitle(i+"#机组");
            textItem.setContent("100%");
            itemBiaoBinding.setItemZhiBiao(textItem);
            binding.contentJiZu.addView(view);
        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.btn_back) {
            finish();
        }
    }
}
