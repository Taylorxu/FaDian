package com.powerge.wise.powerge.otherPages.queXian;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.powerge.wise.powerge.R;

public class QxDoneFragment extends Fragment {

    public QxDoneFragment() {

    }


    public static QxDoneFragment newInstance( ) {
        QxDoneFragment fragment = new QxDoneFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_qx_done, container, false);
    }




}
