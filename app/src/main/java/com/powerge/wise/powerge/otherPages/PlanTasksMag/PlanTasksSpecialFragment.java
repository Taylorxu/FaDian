package com.powerge.wise.powerge.otherPages.PlanTasksMag;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.powerge.wise.powerge.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlanTasksSpecialFragment extends Fragment {


    public PlanTasksSpecialFragment() {
        // Required empty public constructor
    }

    public static PlanTasksSpecialFragment newInstance() {
        
        Bundle args = new Bundle();
        
        PlanTasksSpecialFragment fragment = new PlanTasksSpecialFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_plan_tasks_special, container, false);
    }

}
