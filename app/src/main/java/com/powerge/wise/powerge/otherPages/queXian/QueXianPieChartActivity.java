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
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.powerge.wise.basestone.heart.network.FlatMapResponse;
import com.powerge.wise.basestone.heart.network.FlatMapTopRes;
import com.powerge.wise.basestone.heart.network.FlatMapTopResList;
import com.powerge.wise.basestone.heart.network.ResultModel;
import com.powerge.wise.basestone.heart.network.ResultModelData;
import com.powerge.wise.basestone.heart.ui.view.PagingRecyclerView;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.bean.QueXianFormBean;
import com.powerge.wise.powerge.bean.QueXianMagBean;
import com.powerge.wise.powerge.bean.User;
import com.powerge.wise.powerge.config.soap.ApiService;
import com.powerge.wise.powerge.config.soap.request.BaseUrl;
import com.powerge.wise.powerge.config.soap.request.RequestBody;
import com.powerge.wise.powerge.config.soap.request.RequestEnvelope;
import com.powerge.wise.powerge.databinding.ActivityQueXianPieChartBinding;
import com.powerge.wise.powerge.helper.EEMsgToastHelper;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
        getPieCHartData();
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

    /**
     * 获取 环形图数据
     */
    private void getPieCHartData() {
        final QueXianFormBean formBean = new QueXianFormBean();
        formBean.setNameSpace(BaseUrl.NAMESPACE_P);
        formBean.setUserName(User.getCurrentUser().getName());
        formBean.setArg1("03-01");
        RequestEnvelope.getRequestEnvelope().setBody(new RequestBody<>(formBean));

        ApiService.Creator.get().queryIssueStatisticsMonthly(RequestEnvelope.getRequestEnvelope())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new FlatMapResponse<ResultModel<QueXianFormBean>>())
                .flatMap(new FlatMapTopRes<QueXianFormBean>())
                .subscribe(new Subscriber<QueXianFormBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        EEMsgToastHelper.newInstance().selectWitch(e.getMessage());
                    }

                    @Override
                    public void onNext(QueXianFormBean queXianFormBeans) {
                        if(queXianFormBeans!=null){
                            setData(queXianFormBeans);
                        }else{
                            Toast.makeText(getBaseContext(),"暂无数据",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void setData(QueXianFormBean data) {

        for (int i = 0; i < pieCharts.length; i++) {
            ArrayList<PieEntry> entries = new ArrayList<>();
            entries.add(new PieEntry(QueXianFormBean.getPieChartData(i)));

            PieDataSet dataSet = new PieDataSet(entries, "PieData" + i);
            dataSet.setDrawIcons(false);
            dataSet.setSliceSpace(1f);
            dataSet.setSelectionShift(5f);
            dataSet.setColors(mColors[i]);

            PieData pieData = new PieData(dataSet);
            pieData.setDrawValues(false);//不在环形上显示数据

            pieCharts[i].setData(pieData);
            pieCharts[i].invalidate();
        }
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
