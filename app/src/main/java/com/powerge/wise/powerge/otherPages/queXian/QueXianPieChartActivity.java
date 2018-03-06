package com.powerge.wise.powerge.otherPages.queXian;

import android.annotation.SuppressLint;
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
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.databinding.ActivityQueXianPieChartBinding;

import java.util.ArrayList;

public class QueXianPieChartActivity extends AppCompatActivity {
    ActivityQueXianPieChartBinding binding;
    PieChart[] pieCharts;

    public static void start(Context context) {
        Intent starter = new Intent(context, QueXianPieChartActivity.class);
        context.startActivity(starter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_que_xian_pie_chart);
        pieCharts = new PieChart[]{binding.pieChartJiShi, binding.pieChartEmergency, binding.pieChartUrgency, binding.pieChartNormal};
        binding.title.setText("缺陷报表");
        initView();

    }

    private void initView() {
        for (int i = 0; i < pieCharts.length; i++) {
            pieCharts[i].setUsePercentValues(true);
            pieCharts[i].getDescription().setEnabled(false);
            pieCharts[i].getLegend().setEnabled(false);

            pieCharts[i].setDragDecelerationFrictionCoef(0.95f);

            pieCharts[i].setDrawHoleEnabled(true);//置空中间,否则是扇形图
            pieCharts[i].setHoleColor(Color.WHITE);
            pieCharts[i].setDrawCenterText(true);
            pieCharts[i].setCenterTextSize(12f);
//            //先获取数据，将数据作为参数传过去
            pieCharts[i].setCenterText(generateCenterSpannableText(i));

            pieCharts[i].setTransparentCircleColor(Color.WHITE);
            pieCharts[i].setTransparentCircleAlpha(110);

            pieCharts[i].setHoleRadius(58f);
            pieCharts[i].setTransparentCircleRadius(61f);

            // 旋转
            pieCharts[i].setRotationAngle(0);
            pieCharts[i].setRotationEnabled(true);
            pieCharts[i].setHighlightPerTapEnabled(true);
            pieCharts[i].animateY(1400, Easing.EasingOption.EaseInOutQuad);

            setData(2, 100, i);
        }
    }

    @SuppressLint("Range")
    private int[] mtextColors = new int[]{
            Color.rgb(66, 177, 255),
            Color.rgb(255, 90, 107),
            Color.rgb(255, 199, 66),
            Color.rgb(38, 222, 138)
    };

    @SuppressLint("Range")
    private int[][] mColors = new int[][]{
            {Color.rgb(66, 177, 255), Color.rgb(229, 229, 229)},
            {Color.rgb(255, 90, 107), Color.rgb(229, 229, 229)},
            {Color.rgb(255, 199, 66), Color.rgb(229, 229, 229)},
            {Color.rgb(38, 222, 138), Color.rgb(229, 229, 229)}
    };

    private void setData(int count, float range, int p) {
        float mult = range;

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        for (int i = 0; i < count; i++) {
            entries.add(new PieEntry((float) ((Math.random() * mult) + mult / 5)));
        }

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setDrawIcons(false);
        dataSet.setSliceSpace(1f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(mColors[p]);

        PieData data = new PieData(dataSet);
        data.setDrawValues(false);//不在环形上显示数据

        pieCharts[p].setData(data);
        pieCharts[p].invalidate();
    }

    @SuppressLint("ResourceAsColor")
    private SpannableString generateCenterSpannableText(int p) {
        SpannableString s = null;
        if (p == 0) {
            s = new SpannableString("及时率\n75.3%");
            s.setSpan(new RelativeSizeSpan(1.2f), 3, 7, 0);
            s.setSpan(new StyleSpan(Typeface.NORMAL), 0, s.length(), 0);
            s.setSpan(new ForegroundColorSpan(mtextColors[0]), 0, s.length(), 0);

        } else if (p == 1) {
            s = new SpannableString("75.3%");
            s.setSpan(new StyleSpan(Typeface.NORMAL), 0, s.length(), 0);
            s.setSpan(new ForegroundColorSpan(mtextColors[1]), 0, s.length(), 0);
        } else if (p == 2) {
            s = new SpannableString("75.3%");
            s.setSpan(new StyleSpan(Typeface.NORMAL), 0, s.length(), 0);
            s.setSpan(new ForegroundColorSpan(mtextColors[2]), 0, s.length(), 0);
        } else if (p == 3) {
            s = new SpannableString("5.3%");
            s.setSpan(new StyleSpan(Typeface.NORMAL), 0, s.length(), 0);
            s.setSpan(new ForegroundColorSpan(mtextColors[3]), 0, s.length(), 0);
        }
        return s;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
        }
    }

}
