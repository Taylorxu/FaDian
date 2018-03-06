package com.powerge.wise.powerge.otherPages.JingSai;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.powerge.wise.powerge.R;

public class JingSaiDeFenFragment extends Fragment {


    public JingSaiDeFenFragment() {
    }

    public static JingSaiDeFenFragment newInstance() {

        Bundle args = new Bundle();

        JingSaiDeFenFragment fragment = new JingSaiDeFenFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_jing_sai_de_fen, container, false);
    }

}
