package com.powerge.wise.powerge.otherPages;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.powerge.wise.basestone.heart.ui.XAdapter;
import com.powerge.wise.powerge.BR;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.bean.FuHeAllData;
import com.powerge.wise.powerge.bean.Items;
import com.powerge.wise.powerge.bean.SimpleListTextItem;
import com.powerge.wise.powerge.databinding.ActivityFuHeMagmentBinding;
import com.powerge.wise.powerge.databinding.ItemFirstFragmentGridListBinding;
import com.powerge.wise.powerge.databinding.ItemFuheTableListBinding;
import com.powerge.wise.powerge.databinding.ItemTowTextBinding;

import java.util.ArrayList;
import java.util.List;

public class FuHeManagementActivity extends AppCompatActivity {
    ActivityFuHeMagmentBinding binding;

    public static void start(Context context) {
        Intent starter = new Intent(context, FuHeManagementActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fu_he_magment);
        binding.title.setText(getResources().getStringArray(R.array.item_name_array)[0]);

        initChartView();//2张统计图
        initTableList();//机组负荷数据表
        initPerDayList();//每日负荷率
    }


    /**
     * 初始化 第一个列表
     */
    List<FuHeAllData> list = new ArrayList<>();
    XAdapter<FuHeAllData, ItemFuheTableListBinding> tableListAdapter = new XAdapter.SimpleAdapter<>(BR.fuheData, R.layout.item_fuhe_table_list);

    private void initTableList() {
        createTableListDate();
        binding.fuheTableList.setLayoutManager(new LinearLayoutManager(this));
        binding.fuheTableList.setAdapter(tableListAdapter);
        tableListAdapter.setList(list);
    }

    private void createTableListDate() {

        for (int i = 0; i < 3; i++) {
            FuHeAllData data = new FuHeAllData();
            if (i == 0) {
                data.setColumn("全长");
            } else if (i == 1) {
                data.setColumn("1#");
            } else {
                data.setColumn("2#");

            }
            data.setColumn1(50 + i + "%");
            data.setColumn2(50 + i + "%");
            data.setColumn3(500 + i + "mw");
            data.setColumn4(500 + i + "mw");
            list.add(data);
        }
    }

    XAdapter<SimpleListTextItem, ItemTowTextBinding> perdayAdapter = new XAdapter.SimpleAdapter<>(BR.textItem, R.layout.item_tow_text);

    private void initPerDayList() {

        createPerdayData();
        binding.perdayFhvContent.setLayoutManager(new LinearLayoutManager(this));
        binding.perdayFhvContent.setAdapter(perdayAdapter);
        perdayAdapter.setList(items);
    }

    List<SimpleListTextItem> items = new ArrayList<>();

    private void createPerdayData() {
        for (int i = 0; i < 10; i++) {
            SimpleListTextItem item = new SimpleListTextItem();
            item.setContent(200 + i + "%");
            items.add(item);
        }
    }

    /**
     * 初始化统计图
     */
    private void initChartView() {
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

        /*负荷率变化*/
        binding.chart2.setViewPortOffsets(100, 50, 20, 100);
        binding.chart2.setDrawGridBackground(false);
        binding.chart2.getDescription().setEnabled(false);
        binding.chart2.setDrawBorders(false);//去掉边框
        binding.chart2.getLegend().setEnabled(false);//去掉图例
        binding.chart2.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        binding.chart2.getXAxis().setDrawGridLines(false);
        binding.chart2.getAxisRight().setEnabled(false);
        binding.chart2.getAxisLeft().setDrawGridLines(true);
        binding.chart2.getAxisLeft().setGridDashedLine(new DashPathEffect(new float[]{8, 10, 8, 10}, 0));
        binding.chart2.setTouchEnabled(true);
        binding.chart2.setDragEnabled(true);
        binding.chart2.setScaleEnabled(true);
        binding.chart2.setPinchZoom(false);

        setChart1Data();
        setChart2Data();
    }


    private int[] mColors = new int[]{
            Color.rgb(239, 151, 36),
            Color.rgb(196, 8, 243)
    };


    private void setChart1Data() {

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();

        ArrayList<Entry> values = new ArrayList<Entry>();
        ArrayList<Entry> values1 = new ArrayList<Entry>();
        ArrayList<Entry>[] arrayLists = new ArrayList[]{values, values1};
        for (int z = 0; z < 2; z++) {
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

    private void setChart2Data() {

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();

        ArrayList<Entry> values = new ArrayList<Entry>();
        ArrayList<Entry> values1 = new ArrayList<Entry>();
        ArrayList<Entry>[] arrayLists = new ArrayList[]{values, values1};
        //Entry 类是每条线在x、y 轴 上的数值
        for (int i = 0; i < 20; i++) {
            float mult = 30 / 2f;
            float val = (float) (Math.random() * mult) + 50;
            arrayLists[0].add(new Entry(i, (float) val));
        }
        //每个LineDataSet 代表着一条线
        LineDataSet d = new LineDataSet(arrayLists[0], "DataSet " + (0 + 1));
        d.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        d.setLineWidth(2f);
//            d.setCircleRadius(4f);

        int color = mColors[0];
        d.setColor(color);
        d.setHighLightColor(Color.RED);
        d.setHighlightEnabled(true);
//            d.setCircleColor(color);
        dataSets.add(d);


        LineData data = new LineData(dataSets);
        binding.chart2.setData(data);
        binding.chart2.animateXY(3000, 3000);
        binding.chart2.invalidate();
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_back) {
            finish();
        }
    }

    /**
     * 昨天和今天 数据线 切换 TODO 如果是能切换则需要加一个全部按钮
     *
     * @param view
     */
    public void onClickChart(View view) {
        LineDataSet line_today = (LineDataSet) binding.chart1.getData().getDataSetByIndex(0);
        LineDataSet line_yesterday = (LineDataSet) binding.chart1.getData().getDataSetByIndex(1);
        if (view.getId() == R.id.bt_today) {
            if (!line_today.isVisible()) line_today.setVisible(true);
            line_yesterday.setVisible(false);
        } else if (view.getId() == R.id.bt_yesterday) {
            line_today.setVisible(false);
            if (!line_yesterday.isVisible()) line_yesterday.setVisible(true);
        }
        binding.chart1.invalidate();

    }
}
