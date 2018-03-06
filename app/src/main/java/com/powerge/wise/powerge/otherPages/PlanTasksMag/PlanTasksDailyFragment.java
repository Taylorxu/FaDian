package com.powerge.wise.powerge.otherPages.PlanTasksMag;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.powerge.wise.basestone.heart.ui.XAdapter;
import com.powerge.wise.powerge.BR;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.bean.Items;
import com.powerge.wise.powerge.databinding.FragmentPlanTasksDailyBinding;
import com.powerge.wise.powerge.databinding.ItemPlanTasksListBinding;

import java.util.ArrayList;
import java.util.List;

public class PlanTasksDailyFragment extends Fragment implements View.OnClickListener {
    FragmentPlanTasksDailyBinding binding;
    private static List<Items> list;

    public PlanTasksDailyFragment() {
    }

    public static PlanTasksDailyFragment newInstance() {
        Bundle args = new Bundle();
        PlanTasksDailyFragment fragment = new PlanTasksDailyFragment();
        fragment.setArguments(args);
        list = new ArrayList<>();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_plan_tasks_daily, container, false);
        getData();
        initView();

        return binding.getRoot();
    }


    XAdapter<Items, ItemPlanTasksListBinding> adapter = new XAdapter.SimpleAdapter<>(BR.item, R.layout.item_plan_tasks_list);

    private void initView() {
        binding.btn1Fh.setOnClickListener(this);
        binding.btn2Fh.setOnClickListener(this);
        binding.btn3Fh.setOnClickListener(this);
        binding.btnAllFh.setOnClickListener(this);

        binding.contentTasksList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.contentTasksList.setAdapter(adapter);
        adapter.setList(list);
    }

    private void getData() {
        for (int i = 0; i < 3; i++) {
            Items item = new Items();
            item.setState(i);
            item.setText1("渝水区改造" + i);
            item.setText3("安装部");
            item.setText4("2018-01-21");
            item.setText5("2018-02-09");
            item.setText6("这是备注");
            list.add(item);
        }
    }

    private void getDataclick(int length) {
        for (int i = length; i < length + 1; i++) {
            Items item = new Items();
            item.setState(i);
            item.setText1("渝水区改造" + i);
            item.setText3("安装部");
            item.setText4("2018-01-21");
            item.setText5("2018-02-09");
            item.setText6("这是备注");
            list.add(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1_fh:
                adapter.removeAll();
                getDataclick(0);
                adapter.setList(list);
                break;
            case R.id.btn_2_fh:
                adapter.removeAll();
                getDataclick(1);
                adapter.setList(list);
                break;
            case R.id.btn_3_fh:
                adapter.removeAll();
                getDataclick(2);
                adapter.setList(list);
                break;
            case R.id.btn_all_fh:
                adapter.removeAll();
                getData();
                adapter.setList(list);
                break;

        }
    }
}
