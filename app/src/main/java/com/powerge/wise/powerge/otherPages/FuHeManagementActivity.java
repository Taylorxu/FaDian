package com.powerge.wise.powerge.otherPages;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.powerge.wise.basestone.heart.network.FlatMapResponse;
import com.powerge.wise.basestone.heart.network.FlatMapTopRes;
import com.powerge.wise.basestone.heart.network.ResultModel;
import com.powerge.wise.basestone.heart.ui.XAdapter;
import com.powerge.wise.basestone.heart.util.DensityUtil;
import com.powerge.wise.powerge.BR;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.bean.FuHeYTChartLineBean;
import com.powerge.wise.powerge.bean.FuHeYTFormDataBean;
import com.powerge.wise.powerge.bean.JiZuBean;
import com.powerge.wise.powerge.bean.PeroidDateLineListBean;
import com.powerge.wise.powerge.bean.User;
import com.powerge.wise.powerge.config.soap.ApiService;
import com.powerge.wise.powerge.config.soap.request.BaseUrl;
import com.powerge.wise.powerge.config.soap.request.RequestBody;
import com.powerge.wise.powerge.config.soap.request.RequestEnvelope;
import com.powerge.wise.powerge.databinding.ActivityFuHeMagmentBinding;
import com.powerge.wise.powerge.databinding.ItemFuheTableListBinding;
import com.powerge.wise.powerge.databinding.ItemTowTextBinding;
import com.powerge.wise.powerge.helper.EEMsgToastHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FuHeManagementActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    ActivityFuHeMagmentBinding binding;
    LineChart lineCharts[];
    private String start_date, end_date;
    private int checkedIdp;
    private ArrayList<JiZuBean> jiZuList;

    public static void start(Context context, List<JiZuBean> jiZuList) {
        Intent starter = new Intent(context, FuHeManagementActivity.class);
        starter.putParcelableArrayListExtra(JiZuBean.INTENTKEY, (ArrayList<? extends Parcelable>) jiZuList);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fu_he_magment);
        binding.title.setText(getResources().getStringArray(R.array.item_name_array)[0]);
        jiZuList = getIntent().getParcelableArrayListExtra(JiZuBean.INTENTKEY);
        lineCharts = new LineChart[]{binding.chart1, binding.chart2};
        binding.jiZuGroups.setOnCheckedChangeListener(this);
        initChartView();
        initDatePeriod();
        initAdapters();
        createRadioBtnGroup(jiZuList);
    }

    /**
     * 初始化 第一个列表  查询负荷日统计数据
     */
    XAdapter<FuHeYTFormDataBean, ItemFuheTableListBinding> tableListAdapter = new XAdapter.SimpleAdapter<>(BR.fuheData, R.layout.item_fuhe_table_list);

    XAdapter<PeroidDateLineListBean, ItemTowTextBinding> perdayAdapter = new XAdapter.SimpleAdapter<>(BR.textItem, R.layout.item_tow_text);

    /**
     * 初始化 adapters  涉及到的列表
     */
    private void initAdapters() {
        //  机组负荷数据表
        binding.fuheTableList.setLayoutManager(new LinearLayoutManager(this));
        binding.fuheTableList.setAdapter(tableListAdapter);
        //日曲线下部分列表
        binding.perdayFhvContent.setLayoutManager(new LinearLayoutManager(this));
        binding.perdayFhvContent.setAdapter(perdayAdapter);
    }


    /**
     * 动态创建机组radiobutton
     *
     * @param resultList
     */
    @SuppressLint("ResourceType")
    private void createRadioBtnGroup(List<JiZuBean> resultList) {
        for (int i = 0; i < resultList.size(); i++) {
            JiZuBean jiZuBean = resultList.get(i);
            RadioButton radioButton = new RadioButton(this);
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(DensityUtil.dip2px(this, 100), DensityUtil.dip2px(this, 40));
            params.setMargins(DensityUtil.dip2px(this, 5), DensityUtil.dip2px(this, 10), DensityUtil.dip2px(this, 5), DensityUtil.dip2px(this, 10));
            radioButton.setLayoutParams(params);
            radioButton.setGravity(Gravity.CENTER);
            radioButton.setTextColor(getResources().getColorStateList(R.drawable.selector_primary_white_text));
            radioButton.setBackgroundResource(R.drawable.selector_primary_white_btn_bg);
            radioButton.setButtonDrawable(android.R.color.transparent);//隐藏单选圆形按钮
            radioButton.setTextSize(15f);
            radioButton.setText(jiZuBean.getName());
            if (i == 0) radioButton.setChecked(true);
            radioButton.setId(Integer.parseInt(jiZuBean.getId()));
            binding.jiZuGroups.addView(radioButton);
        }
    }

    /**************************************************************昨日，今日***************************************************************************/
    private void getFuHeYTData(final String checkedId) {
        if (User.getCurrentUser() == null) LoginActivity.start(this);
        final FuHeYTChartLineBean heYTChartLineBean = FuHeYTChartLineBean.newInstance();
        heYTChartLineBean.setNameSpace(BaseUrl.NAMESPACE_P);
        heYTChartLineBean.setUserName(User.getCurrentUser().getName());
        heYTChartLineBean.setArg1(checkedId);
        RequestEnvelope.getRequestEnvelope().setBody(new RequestBody<>(heYTChartLineBean));
        ApiService.Creator.get().queryLoadRealtimeData(RequestEnvelope.getRequestEnvelope())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new FlatMapResponse<ResultModel<FuHeYTChartLineBean>>())
                .flatMap(new FlatMapTopRes<FuHeYTChartLineBean>())
                .subscribe(new Subscriber<FuHeYTChartLineBean>() {
                    @Override
                    public void onCompleted() {
                        getFuHeYTFormData(checkedId);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        binding.textDataEmpty0.setVisibility(View.VISIBLE);
                        binding.textDataEmpty0.setText("数据获取失败");
                        EEMsgToastHelper.newInstance().selectWitch(e.getMessage());

                    }

                    @Override
                    public void onNext(FuHeYTChartLineBean returnValueBean) {
                        if (returnValueBean.getToday().size() == 0 && returnValueBean.getYesterday().size() == 0) {//TODO 按理来说 每个机组都应有数据
                            binding.textDataEmpty0.setVisibility(View.VISIBLE);
                            binding.chart1.setVisibility(View.GONE);
                        } else {
                            binding.textDataEmpty0.setVisibility(View.GONE);
                            binding.chart1.setVisibility(View.VISIBLE);
                            setYTChartData(returnValueBean);
                        }
                    }
                });
    }

    private void getFuHeYTFormData(String checkedId) {
        if (User.getCurrentUser() == null) LoginActivity.start(this);
        final FuHeYTFormDataBean ytFormDataBean = FuHeYTFormDataBean.newInstance();
        ytFormDataBean.setNameSpace(BaseUrl.NAMESPACE_P);
        ytFormDataBean.setUserName(User.getCurrentUser().getName());
        ytFormDataBean.setArg1(checkedId);//机组
        RequestEnvelope.getRequestEnvelope().setBody(new RequestBody<>(ytFormDataBean));
        ApiService.Creator.get().queryLoadStatisticData(RequestEnvelope.getRequestEnvelope())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new FlatMapResponse<ResultModel<List<FuHeYTFormDataBean>>>())
                .flatMap(new FlatMapTopRes<List<FuHeYTFormDataBean>>())
                .subscribe(new Subscriber<List<FuHeYTFormDataBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if (e.getCause() != null)
                            EEMsgToastHelper.newInstance().selectWitch(e.getMessage());
                        binding.textDataEmpty0.setVisibility(View.VISIBLE);
                        binding.textDataEmpty0.setText("数据获取失败");
                        binding.chart1.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNext(List<FuHeYTFormDataBean> fuHeYTFormDataBeans) {
                        if (fuHeYTFormDataBeans.size() > 0) {
                            tableListAdapter.setList(fuHeYTFormDataBeans);
                            binding.textDataEmpty.setVisibility(View.GONE);
                        } else {
                            binding.textDataEmpty.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }


/***************************************************************************end***********************************************************************/

    /**************************************************************负荷率变化趋势图***************************************************************************/
    String[] dateX = null;

    /**
     * 获取查询日负荷率曲线
     */
    private void queryLoadRatioData(String checkedId) {
        if (User.getCurrentUser() == null) LoginActivity.start(this);
        final PeroidDateLineListBean dateLineListBean = PeroidDateLineListBean.newInstance();
        dateLineListBean.setNameSpace(BaseUrl.NAMESPACE_P);
        dateLineListBean.setUserName(User.getCurrentUser().getName());
        dateLineListBean.setArg1(binding.textStartText.getText().toString());
        dateLineListBean.setArg2(binding.textEndText.getText().toString());
        dateLineListBean.setArg3(checkedId);
        RequestEnvelope.getRequestEnvelope().setBody(new RequestBody<>(dateLineListBean));
        ApiService.Creator.get().queryLoadRatioData(RequestEnvelope.getRequestEnvelope())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new FlatMapResponse<ResultModel<List<PeroidDateLineListBean>>>())
                .flatMap(new FlatMapTopRes<List<PeroidDateLineListBean>>())
                .subscribe(new Subscriber<List<PeroidDateLineListBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        binding.textDataEmpty0.setVisibility(View.VISIBLE);
                        binding.textDataEmpty0.setText("数据获取失败");
                        EEMsgToastHelper.newInstance().selectWitch(e.getMessage());

                    }

                    @Override
                    public void onNext(List<PeroidDateLineListBean> peroidDateLineListBean) {
                        if (peroidDateLineListBean.size() > 1) {
                            binding.textDataEmpty2.setVisibility(View.GONE);
                            binding.textDataEmpty3.setVisibility(View.GONE);
                            binding.chart2.setVisibility(View.VISIBLE);
                            binding.perdayFhvContent.setVisibility(View.VISIBLE);
                            dateX = new String[peroidDateLineListBean.size()];
                            setRatioChartData(peroidDateLineListBean);
                            perdayAdapter.setList(peroidDateLineListBean);
                        } else {
                            binding.chart2.setVisibility(View.GONE);
                            binding.perdayFhvContent.setVisibility(View.GONE);
                            perdayAdapter.setList(null);
                            binding.textDataEmpty2.setVisibility(View.VISIBLE);
                            binding.textDataEmpty3.setVisibility(View.VISIBLE);
                        }

                    }
                });
    }


    /****************************************************************end************************************************************************************/
    /**
     * 初始化统计图
     */

    private void initChartView() {
        for (int i = 0; i < lineCharts.length; i++) {
            lineCharts[i].setViewPortOffsets(DensityUtil.dip2px(getBaseContext(), 28), DensityUtil.dip2px(getBaseContext(), 13), DensityUtil.dip2px(getBaseContext(), 5), DensityUtil.dip2px(getBaseContext(), 25));
            lineCharts[i].setDrawGridBackground(false);
            lineCharts[i].getDescription().setEnabled(false);
            lineCharts[i].setDrawBorders(false);//去掉边框
            lineCharts[i].getLegend().setEnabled(false);//去掉图例
            lineCharts[i].getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
            lineCharts[i].getXAxis().setDrawGridLines(false);
            lineCharts[i].getAxisRight().setEnabled(false);
            lineCharts[i].getAxisLeft().setDrawGridLines(true);
            lineCharts[i].getAxisLeft().setGridDashedLine(new DashPathEffect(new float[]{8, 10, 8, 10}, 0));

            lineCharts[i].setTouchEnabled(true);
            lineCharts[i].setDragEnabled(true);
            lineCharts[i].setScaleEnabled(true);
            lineCharts[i].setPinchZoom(false);
            if (i == 1) {
                lineCharts[i].setViewPortOffsets(DensityUtil.dip2px(getBaseContext(), 20), DensityUtil.dip2px(getBaseContext(), 18), DensityUtil.dip2px(getBaseContext(), 15), DensityUtil.dip2px(getBaseContext(), 25));
                lineCharts[i].getXAxis().setGranularity(1f);
                lineCharts[i].getXAxis().setValueFormatter(new XFormatter());
            }
        }


    }


    private int[] mColors = new int[]{
            Color.rgb(239, 151, 36),// 黄色 昨日
            Color.rgb(196, 8, 243)
    };

    /**
     * 昨日 今日曲线图 填充数据
     *
     * @param chartData
     */
    private void setYTChartData(FuHeYTChartLineBean chartData) {
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        ArrayList<Entry> values = new ArrayList<>();
        ArrayList<Entry> values1 = new ArrayList<>();

        if (chartData.getYesterday().size() > 0) {
            for (FuHeYTChartLineBean.YesterdayBean ybean : chartData.getYesterday()) {
                String x = ybean.getX().substring(0, ybean.getX().indexOf(":"));
                Entry entry = new Entry(Float.parseFloat(x), Float.parseFloat(ybean.getY()));
                values.add(entry);
            }

            //每个LineDataSet 代表着一条线
            LineDataSet d = new LineDataSet(values, "DataSet " + 0);
            d.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            d.setLineWidth(2f);
            d.setDrawValues(false);//在线上不显示 数据值
            d.setDrawCircles(false);//不显示对相应x/y轴 点
            int color = mColors[0];
            d.setColor(color);
            d.setHighLightColor(Color.RED);
            d.setHighlightEnabled(true);
            dataSets.add(d);
        }
        if (chartData.getToday().size() > 0) {
            for (FuHeYTChartLineBean.TodayBean todayBean : chartData.getToday()) {
                String x = todayBean.getX().substring(0, todayBean.getX().indexOf(":"));
                Entry entry = new Entry(Float.parseFloat(x), Float.parseFloat(todayBean.getY()));
                values1.add(entry);
            }
            //每个LineDataSet 代表着一条线
            LineDataSet d = new LineDataSet(values1, "DataSet " + 1);
            d.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            d.setLineWidth(2f);
            d.setDrawValues(false);//在线上不显示 数据值
            d.setDrawCircles(false);//不显示对相应x/y轴 点
            int color = mColors[1];
            d.setColor(color);
            d.setHighLightColor(Color.RED);
            d.setHighlightEnabled(true);
            dataSets.add(d);
        }


        LineData data = new LineData(dataSets);
        binding.chart1.setData(data);
        binding.chart1.animateXY(1000, 1000);
        binding.chart1.invalidate();
    }

    private void setRatioChartData(List<PeroidDateLineListBean> peroidD) {
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        ArrayList<Entry> values = new ArrayList<>();

        for (int i = 0; i < peroidD.size(); i++) {
            dateX[i] = peroidD.get(i).getDate();
            Entry entry = new Entry(i, Float.parseFloat(peroidD.get(i).getLoadRatio()));
            values.add(entry);
        }
        LineDataSet d = new LineDataSet(values, "DataSet1");
        d.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        d.setLineWidth(2f);
        int color = mColors[1];
        d.setColor(color);
        d.setHighLightColor(Color.RED);
        d.setHighlightEnabled(true);
        dataSets.add(d);
        LineData data = new LineData(dataSets);
        binding.chart2.setData(data);
        binding.chart2.animateXY(3000, 3000);
        binding.chart2.invalidate();
    }

    /**
     * 负荷率变化趋势图 区间日期
     */
    private void initDatePeriod() {
        final Calendar calendar = Calendar.getInstance();
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        binding.textToday.setText(format.format(calendar.getTime()));
        binding.textEndText.setText(format.format(calendar.getTime()));
        int year = calendar.get(Calendar.YEAR);
        int monthOfYear = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH) - 6;
        calendar.set(year, monthOfYear, dayOfMonth);
        binding.textStartText.setText(format.format(calendar.getTime()));
        start_date = binding.textStartText.getText().toString();
        end_date = binding.textEndText.getText().toString();

    }


    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.icon_period_date:
                DatePeriodSelectActivity.start(this);
                break;
            case R.id.btn_hour_data:
                FuHeOneHourDataActivity.start(this, String.valueOf(checkedIdp));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == DatePeriodSelectActivity.REQUEST_CODE_F_DATE_PER) {
                binding.textStartText.setText(data.getStringExtra(DatePeriodSelectActivity.INTENT_STARTDATE));
                binding.textEndText.setText(data.getStringExtra(DatePeriodSelectActivity.INTENT_ENDDATE));
                queryLoadRatioData(String.valueOf(checkedIdp));
            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        checkedIdp = checkedId;
        getFuHeYTData(String.valueOf(checkedId));
        queryLoadRatioData(String.valueOf(checkedId));
    }

    private class XFormatter implements IAxisValueFormatter {

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            int index = (int) Math.abs(value);
            if (dateX.length > 0 && index <dateX.length) {
                String date = dateX[index];
                return date.substring(date.indexOf("-") + 1, date.length());
            }
            return String.valueOf(value);
        }

    }
}
