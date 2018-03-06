package com.powerge.wise.powerge.otherPages.PlanTasksMag;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.powerge.wise.powerge.R;

public class PlanTasksDailyFragment extends Fragment {


    public PlanTasksDailyFragment() {
    }

    public static PlanTasksDailyFragment newInstance() {
        Bundle args = new Bundle();
        PlanTasksDailyFragment fragment = new PlanTasksDailyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_plan_tasks_daily, container, false);
    }

}
