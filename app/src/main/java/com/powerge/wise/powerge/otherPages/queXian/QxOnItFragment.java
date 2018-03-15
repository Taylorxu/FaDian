package com.powerge.wise.powerge.otherPages.queXian;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.databinding.FragmentQxOnItBinding;

public class QxOnItFragment extends Fragment {

    public QxOnItFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    public static QxOnItFragment newInstance() {
        QxOnItFragment fragment = new QxOnItFragment();
        return fragment;
    }

    FragmentQxOnItBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_qx_on_it, container, false);

        return binding.getRoot();
    }


}
