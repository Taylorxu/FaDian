package com.powerge.wise.powerge.otherPages;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.hyphenate.util.DensityUtil;
import com.powerge.wise.basestone.heart.network.FlatMapResponse;
import com.powerge.wise.basestone.heart.network.FlatMapTopRes;
import com.powerge.wise.basestone.heart.network.FlatMapTopResList;
import com.powerge.wise.basestone.heart.network.ResultModel;
import com.powerge.wise.basestone.heart.network.ResultModelData;
import com.powerge.wise.basestone.heart.ui.XAdapter;
import com.powerge.wise.basestone.heart.util.LogUtils;
import com.powerge.wise.powerge.BR;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.bean.DianLiangBean;
import com.powerge.wise.powerge.bean.FuHeAllData;
import com.powerge.wise.powerge.bean.FuHeYTChartLineBean;
import com.powerge.wise.powerge.bean.FuHeYTFormDataBean;
import com.powerge.wise.powerge.bean.Items;
import com.powerge.wise.powerge.bean.JiZuBean;
import com.powerge.wise.powerge.bean.MorningMeetingBean;
import com.powerge.wise.powerge.bean.PeroidDateLineListBean;
import com.powerge.wise.powerge.bean.SimpleListTextItem;
import com.powerge.wise.powerge.bean.User;
import com.powerge.wise.powerge.config.soap.ApiService;
import com.powerge.wise.powerge.config.soap.request.BaseUrl;
import com.powerge.wise.powerge.config.soap.request.RequestBody;
import com.powerge.wise.powerge.config.soap.request.RequestEnvelope;
import com.powerge.wise.powerge.databinding.ActivityFuHeMagmentBinding;
import com.powerge.wise.powerge.databinding.ItemFirstFragmentGridListBinding;
import com.powerge.wise.powerge.databinding.ItemFuheTableListBinding;
import com.powerge.wise.powerge.databinding.ItemTowTextBinding;
import com.powerge.wise.powerge.helper.EEMsgToastHelper;
import com.wisesignsoft.OperationManagement.utils.LogUtil;
import com.wisesignsoft.OperationManagement.utils.ToastUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FuHeManagementActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    ActivityFuHeMagmentBinding binding;
    LineChart lineCharts[];
    private String start_date, end_date;

    public static void start(Context context) {
        Intent starter = new Intent(context, FuHeManagementActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fu_he_magment);
        binding.title.setText(getResources().getStringArray(R.array.item_name_array)[0]);
        lineCharts = new LineChart[]{binding.chart1, binding.chart2};
        binding.jiZuGroups.setOnCheckedChangeListener(this);
        initChartView();
        initDatePeriod();
        initAdapters();
        getJiZuData();
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
     * 获取机组数据
     */
    private void getJiZuData() {
        if (User.getCurrentUser() == null) LoginActivity.start(this);
        final JiZuBean jiZuBean = JiZuBean.newInstance();
        jiZuBean.setNameSpace(BaseUrl.NAMESPACE_P);
        jiZuBean.setUserName(User.getCurrentUser().getAccount());
        RequestEnvelope.getRequestEnvelope().setBody(new RequestBody<>(jiZuBean));
        ApiService.Creator.get().queryUnits(RequestEnvelope.getRequestEnvelope())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new FlatMapResponse<ResultModelData<ResultModelData.ReturnValueBean<JiZuBean>>>())
                .flatMap(new FlatMapTopResList<ResultModelData.ReturnValueBean<JiZuBean>>())
                .subscribe(new Subscriber<ResultModelData.ReturnValueBean<JiZuBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        EEMsgToastHelper.newInstance().selectWitch(e.getCause().getMessage());

                    }

                    @Override
                    public void onNext(ResultModelData.ReturnValueBean<JiZuBean> returnValueBean) {
                        createRadioBtnGroup(returnValueBean.getResultList());
                    }
                });
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
        heYTChartLineBean.setUserName(User.getCurrentUser().getAccount());
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
                        EEMsgToastHelper.newInstance().selectWitch(e.getCause().getMessage());

                    }

                    @Override
                    public void onNext(FuHeYTChartLineBean returnValueBean) {
                        if (returnValueBean.getToday().size() == 0 || returnValueBean.getYesterday().size() == 0) {//TODO 按理来说 每个机组都应有数据
                            binding.textDataEmpty0.setVisibility(View.VISIBLE);
                        } else {
                            setYTChartData(returnValueBean);
                        }
                    }
                });
    }

    private void getFuHeYTFormData(String checkedId) {
        if (User.getCurrentUser() == null) LoginActivity.start(this);
        final FuHeYTFormDataBean ytFormDataBean = FuHeYTFormDataBean.newInstance();
        ytFormDataBean.setNameSpace(BaseUrl.NAMESPACE_P);
        ytFormDataBean.setUserName(User.getCurrentUser().getAccount());
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
                            EEMsgToastHelper.newInstance().selectWitch(e.getCause().getMessage());
                        binding.textDataEmpty0.setVisibility(View.VISIBLE);
                        binding.textDataEmpty0.setText("数据获取失败");
                        binding.chart1.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNext(List<FuHeYTFormDataBean> fuHeYTFormDataBeans) {
                        if (fuHeYTFormDataBeans.size() > 0) {
                            tableListAdapter.setList(fuHeYTFormDataBeans);
                            binding.chart1.setVisibility(View.VISIBLE);
                            binding.textDataEmpty0.setVisibility(View.GONE);
                        } else {
                            binding.textDataEmpty0.setVisibility(View.VISIBLE);
                            binding.chart1.setVisibility(View.GONE);
                        }
                    }
                });
    }


/***************************************************************************end***********************************************************************/

/**************************************************************负荷率变化趋势图***************************************************************************/

    /**
     * 获取查询日负荷率曲线
     */
    private void queryLoadRatioData(String checkedId) {
        if (User.getCurrentUser() == null) LoginActivity.start(this);
        final PeroidDateLineListBean dateLineListBean = PeroidDateLineListBean.newInstance();
        dateLineListBean.setNameSpace(BaseUrl.NAMESPACE_P);
        dateLineListBean.setUserName(User.getCurrentUser().getAccount());
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
                        EEMsgToastHelper.newInstance().selectWitch(e.getCause().getMessage());

                    }

                    @Override
                    public void onNext(List<PeroidDateLineListBean> PeroidDateLineListBean) {
                        setRatioChartData(PeroidDateLineListBean);
                        perdayAdapter.setList(PeroidDateLineListBean);
                    }
                });
    }


    /****************************************************************end************************************************************************************/
    /**
     * 初始化统计图
     *
     */

    private void initChartView() {
        for (int i = 0; i < lineCharts.length; i++) {
            lineCharts[i].setViewPortOffsets(110, 50, 20, 100);
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
                lineCharts[i].getXAxis().setGranularity(1f);
                lineCharts[i].getXAxis().setValueFormatter(new XFormatter());
            }
        }


    }


    private int[] mColors = new int[]{
            Color.rgb(239, 151, 36),// 黄色 昨日
            Color.rgb(196, 8, 243)
    };


    private void setYTChartData(FuHeYTChartLineBean chartData) {
        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        ArrayList<Entry> values = new ArrayList<>();
        ArrayList<Entry> values1 = new ArrayList<>();
        ArrayList<Entry>[] arrayLists = new ArrayList[]{values, values1};
        for (int z = 0; z < 2; z++) {
            if (z == 0) {
                for (FuHeYTChartLineBean.YesterdayBean ybean : chartData.getYesterday()) {
                    String x = ybean.getX().substring(0, ybean.getX().indexOf(":"));
                    Entry entry = new Entry(Float.parseFloat(x), Float.parseFloat(ybean.getY()));
                    values.add(entry);
                }
            } else {
                for (FuHeYTChartLineBean.TodayBean todayBean : chartData.getToday()) {
                    String x = todayBean.getX().substring(0, todayBean.getX().indexOf(":"));
                    Entry entry = new Entry(Float.parseFloat(x), Float.parseFloat(todayBean.getY()));
                    values1.add(entry);
                }
            }

            //每个LineDataSet 代表着一条线
            LineDataSet d = new LineDataSet(arrayLists[z], "DataSet " + (z + 1));
            d.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            d.setLineWidth(2f);

            int color = mColors[z];
            d.setColor(color);
            d.setHighLightColor(Color.RED);
            d.setHighlightEnabled(true);
            dataSets.add(d);
        }

        LineData data = new LineData(dataSets);
        binding.chart1.setData(data);
        binding.chart1.animateXY(3000, 3000);
        binding.chart1.invalidate();
    }

    private void setRatioChartData(List<PeroidDateLineListBean> today) {
        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        ArrayList<Entry> values = new ArrayList<Entry>();
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (PeroidDateLineListBean bean : today) {
            Date date = null;
            try {
                date = mFormat.parse(bean.getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Entry entry = new Entry(Float.parseFloat(String.valueOf(date.getTime())), Float.parseFloat(bean.getLoadRatio()));
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
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == DatePeriodSelectActivity.REQUEST_CODE_F_DATE_PER) {
                binding.textStartText.setText(data.getStringExtra(DatePeriodSelectActivity.INTENT_STARTDATE));
                binding.textEndText.setText(data.getStringExtra(DatePeriodSelectActivity.INTENT_ENDDATE));
            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        getFuHeYTData(String.valueOf(checkedId));
        queryLoadRatioData(String.valueOf(checkedId));
    }

    private class XFormatter implements IAxisValueFormatter {
        private SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd");

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return mFormat.format(new Date((long) value));
        }
    }
}
