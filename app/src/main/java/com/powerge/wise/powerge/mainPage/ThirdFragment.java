package com.powerge.wise.powerge.mainPage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.powerge.wise.powerge.R;


public class ThirdFragment extends Fragment {

    public ThirdFragment() {

    }

    public ThirdFragment newInstance(){
        ThirdFragment secondFragment = new ThirdFragment();
        return secondFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_third, container, false);
    }




}
