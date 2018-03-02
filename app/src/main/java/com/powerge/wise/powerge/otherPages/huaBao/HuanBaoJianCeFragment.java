package com.powerge.wise.powerge.otherPages.huaBao;


import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.databinding.FragmentHuanBaoJianCeBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HuanBaoJianCeFragment extends Fragment {
    HuanBaoJianCeFragment jianCeFragment;

    public HuanBaoJianCeFragment() {
    }

    public HuanBaoJianCeFragment newInstance() {
        if (jianCeFragment == null) {
            jianCeFragment = new HuanBaoJianCeFragment();
        }
        return jianCeFragment;
    }

    FragmentHuanBaoJianCeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_huan_bao_jian_ce, container, false);
        initView();
        return binding.getRoot();
    }


    private void initView() {
        binding.btnAllLine.setOnClickListener(new onClickShowLine());
        binding.btnYangLine.setOnClickListener(new onClickShowLine());
        binding.btnLiuLine.setOnClickListener(new onClickShowLine());
        binding.btnYanChenLine.setOnClickListener(new onClickShowLine());
        drawLinChart();
    }

    //SO Nox 烟尘
    private int[] mColors = new int[]{
            Color.rgb(64, 153, 255),
            Color.rgb(239, 151, 36),
            Color.rgb(196, 8, 243)
    };

    private void drawLinChart() {
        LineChart lineCharts[] = new LineChart[]{binding.chart1};
        lineCharts[0].setViewPortOffsets(100, 50, 20, 100);
        lineCharts[0].setDrawGridBackground(false);
        lineCharts[0].getDescription().setEnabled(false);
        lineCharts[0].setDrawBorders(false);//去掉边框
        lineCharts[0].getLegend().setEnabled(false);//去掉图例
        lineCharts[0].getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineCharts[0].getXAxis().setDrawGridLines(false);
        lineCharts[0].getAxisRight().setEnabled(false);
        lineCharts[0].getAxisLeft().setDrawGridLines(true);
        lineCharts[0].getAxisLeft().setGridDashedLine(new DashPathEffect(new float[]{8, 10, 8, 10}, 0));
        lineCharts[0].setTouchEnabled(true);
        lineCharts[0].setDragEnabled(true);
        lineCharts[0].setScaleEnabled(true);
        lineCharts[0].setPinchZoom(false);

        setChart1Data();
    }


    ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();

    private void setChart1Data() {


        ArrayList<Entry> values = new ArrayList<Entry>();
        ArrayList<Entry> values1 = new ArrayList<Entry>();
        ArrayList<Entry> values2 = new ArrayList<Entry>();
        ArrayList<Entry>[] arrayLists = new ArrayList[]{values, values1, values2};
        for (int z = 0; z < 3; z++) {
            if (z == 0) {
                //Entry 类是每条线在x、y 轴 上的数值
                for (int i = 0; i < 20; i++) {
                    float mult = 30 / 2f;
                    float val = (float) (Math.random() * mult) + 50;
                    arrayLists[z].add(new Entry(i, (float) val));
                }
            } else {
                for (int i = 0; i < 20; i++) {
                    double val = (Math.random() * 30) + 10;
                    arrayLists[z].add(new Entry(i, (float) val));
                }
            }

            //每个LineDataSet 代表着一条线
            LineDataSet d = new LineDataSet(arrayLists[z], "DataSet " + (z + 1));
            d.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            d.setLineWidth(2f);
//            d.setCircleRadius(4f);

            int color = mColors[z];
            d.setColor(color);
            d.setHighLightColor(Color.RED);
            d.setHighlightEnabled(true);
//            d.setCircleColor(color);
            dataSets.add(d);
        }


        LineData data = new LineData(dataSets);
        binding.chart1.setData(data);
        binding.chart1.animateXY(3000, 3000);
        binding.chart1.invalidate();
    }

    public class onClickShowLine implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.btn_all_line) {
                for (int i = 0; i < dataSets.size(); i++) {
                    dataSets.get(i).setVisible(true);
                }
            } else {
                for (int i = 0; i < dataSets.size(); i++) {
                    if (view.getTag().equals(i + "")) {
                        dataSets.get(i).setVisible(true);
                    } else {
                        dataSets.get(i).setVisible(false);
                    }
                }
            }
            binding.chart1.invalidate();
        }


    }


}
