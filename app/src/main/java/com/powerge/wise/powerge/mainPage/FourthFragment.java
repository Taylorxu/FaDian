package com.powerge.wise.powerge.mainPage;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.databinding.FragmentFourthBinding;


public class FourthFragment extends Fragment {
    FragmentFourthBinding binding;

    public FourthFragment() {

    }

    public static FourthFragment newInstance() {
        Bundle args = new Bundle();
        FourthFragment fragment = new FourthFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fourth, container, false);
        binding.title.setText(getResources().getString(R.string.title_mine));
        return binding.getRoot();
    }


}
