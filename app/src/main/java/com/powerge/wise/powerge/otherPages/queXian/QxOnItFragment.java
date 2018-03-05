package com.powerge.wise.powerge.otherPages.queXian;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.powerge.wise.powerge.R;

public class QxOnItFragment extends Fragment {

    public QxOnItFragment() {

    }


    public static QxOnItFragment newInstance() {
        QxOnItFragment fragment = new QxOnItFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_qx_on_it, container, false);
    }


}
