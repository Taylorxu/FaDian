package com.powerge.wise.powerge.otherPages.huaBao;


import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.hyphenate.util.DensityUtil;
import com.powerge.wise.basestone.heart.network.FlatMapResponse;
import com.powerge.wise.basestone.heart.network.FlatMapTopRes;
import com.powerge.wise.basestone.heart.network.FlatMapTopResList;
import com.powerge.wise.basestone.heart.network.ResultModel;
import com.powerge.wise.basestone.heart.network.ResultModelData;
import com.powerge.wise.basestone.heart.ui.XAdapter;
import com.powerge.wise.powerge.BR;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.bean.HuanBaoBean;
import com.powerge.wise.powerge.bean.JiZuBean;
import com.powerge.wise.powerge.bean.User;
import com.powerge.wise.powerge.config.soap.ApiService;
import com.powerge.wise.powerge.config.soap.request.BaseUrl;
import com.powerge.wise.powerge.config.soap.request.RequestBody;
import com.powerge.wise.powerge.config.soap.request.RequestEnvelope;
import com.powerge.wise.powerge.databinding.FragmentHuanBaoJianCeBinding;
import com.powerge.wise.powerge.databinding.HuanBaoItemDuiBiBinding;
import com.powerge.wise.powerge.helper.EEMsgToastHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HuanBaoJianCeFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {
    HuanBaoJianCeFragment jianCeFragment;
    FragmentHuanBaoJianCeBinding binding;
    List<JiZuBean> jiZuList;

    private int ji_zu_checked;
    private String indicator = "1";
    private String[] dateX;

    public HuanBaoJianCeFragment() {
    }

    public HuanBaoJianCeFragment newInstance() {
        if (jianCeFragment == null) {
            jianCeFragment = new HuanBaoJianCeFragment();
        }
        return jianCeFragment;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        jiZuList = getActivity().getIntent().getParcelableArrayListExtra(JiZuBean.INTENTKEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_huan_bao_jian_ce, container, false);
        initView();
        return binding.getRoot();
    }

    XAdapter<HuanBaoBean, HuanBaoItemDuiBiBinding> adapter = new XAdapter.SimpleAdapter<>(BR.hbata, R.layout.huan_bao_item_dui_bi);

    @SuppressLint("ResourceAsColor")
    private SpannableString generateCenterSpannableText(String text) {
        SpannableString s = new SpannableString(text);
        s.setSpan(new RelativeSizeSpan(0.5f), text.length() - 1, text.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        return s;
    }

    private void initView() {
        binding.btnYangLine.setText(generateCenterSpannableText("SO2"));
        binding.btnLiuLine.setText(generateCenterSpannableText("NOX"));
        binding.btnYangLine.setOnClickListener(new onClickShowLine());
        binding.btnLiuLine.setOnClickListener(new onClickShowLine());
        binding.btnYanChenLine.setOnClickListener(new onClickShowLine());
        binding.jiZuGroups.setOnCheckedChangeListener(this);
        createRadioBtnGroup();
        binding.content.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.content.setAdapter(adapter);
        adapter.setList(listData);

    }

    List<HuanBaoBean> listData = new ArrayList<>();

    private void getData() {
        HuanBaoBean huanBaoBean = HuanBaoBean.newInstance();
        huanBaoBean.setNameSpace(BaseUrl.NAMESPACE_P);
        huanBaoBean.setUserName(User.getCurrentUser().getAccount());
        huanBaoBean.setArg1(String.valueOf(ji_zu_checked));
        huanBaoBean.setArg2(indicator);
        RequestEnvelope.getRequestEnvelope().setBody(new RequestBody<>(huanBaoBean));
        ApiService.Creator.get().queryEnvironmentalIndicators(RequestEnvelope.getRequestEnvelope())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new FlatMapResponse<ResultModel<List<HuanBaoBean>>>())
                .flatMap(new FlatMapTopRes<List<HuanBaoBean>>())
                .subscribe(new Subscriber<List<HuanBaoBean>>() {
                    @Override
                    public void onCompleted() {
                        binding.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        EEMsgToastHelper.newInstance().selectWitch(e.getCause().getMessage());

                    }

                    @Override
                    public void onNext(List<HuanBaoBean> list) {
                        if (!binding.chart1.isEmpty()) binding.chart1.clearValues();
                        if (list.size() > 1) {//一条数据，画图会崩溃
                            setChart1Data(list);
                        }
                        adapter.setList(list);
                    }
                });
    }

    //SO Nox 烟尘
    private int[] mColors = new int[]{
            Color.rgb(64, 153, 255),
            Color.rgb(239, 151, 36),
            Color.rgb(196, 8, 243)
    };

    private void drawLinChart() {

        binding.chart1.setViewPortOffsets(100, 50, 20, 100);
        binding.chart1.setDrawGridBackground(false);
        binding.chart1.getDescription().setEnabled(false);
        binding.chart1.setDrawBorders(false);//去掉边框
        binding.chart1.getLegend().setEnabled(false);//去掉图例
        binding.chart1.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        binding.chart1.getXAxis().setDrawGridLines(false);
        binding.chart1.getAxisRight().setEnabled(false);
        binding.chart1.getAxisLeft().setDrawGridLines(true);
        binding.chart1.getAxisLeft().setGridDashedLine(new DashPathEffect(new float[]{8, 10, 8, 10}, 0));
        binding.chart1.setTouchEnabled(true);
        binding.chart1.setDragEnabled(true);
        binding.chart1.setScaleEnabled(true);
        binding.chart1.setPinchZoom(false);
        binding.chart1.getXAxis().setGranularity(1f);
        binding.chart1.getXAxis().setValueFormatter(new XFormatter());

    }


    private void setChart1Data(List<HuanBaoBean> data) {
        drawLinChart();
        dateX = new String[data.size()];
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        ArrayList<Entry> value = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            dateX[i] = data.get(i).getTime();
            Entry entry = new Entry(i, Integer.parseInt(data.get(i).getValue()));
            value.add(entry);
        }
        //每个LineDataSet 代表着一条线
        LineDataSet d = new LineDataSet(value, "DataSet3");
        d.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        d.setLineWidth(2f);
        int color = mColors[Integer.parseInt(indicator) - 1];
        d.setColor(color);
        d.setHighLightColor(Color.RED);
        d.setHighlightEnabled(true);
        dataSets.add(d);
        LineData lineData = new LineData(dataSets);
        binding.chart1.setData(lineData);
        binding.chart1.animateXY(3000, 3000);
        binding.chart1.invalidate();
        binding.progressBar.setVisibility(View.GONE);
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        ji_zu_checked = checkedId;
        getData();
    }

    public class onClickShowLine implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            indicator = String.valueOf(view.getTag());
            getData();
        }


    }

    @SuppressLint("ResourceType")
    private void createRadioBtnGroup() {
        if (jiZuList.size() == 0) return;
        for (int i = 0; i < jiZuList.size(); i++) {
            JiZuBean jiZuBean = jiZuList.get(i);
            RadioButton radioButton = new RadioButton(getContext());
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(DensityUtil.dip2px(getContext(), 100), DensityUtil.dip2px(getContext(), 40));
            params.setMargins(DensityUtil.dip2px(getContext(), 5), DensityUtil.dip2px(getContext(), 10), DensityUtil.dip2px(getContext(), 5), DensityUtil.dip2px(getContext(), 10));
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

    private class XFormatter implements IAxisValueFormatter {

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            String date = dateX[(int) value];
            return date;

        }

    }

}
