package com.powerge.wise.powerge.otherPages.huaBao;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.powerge.wise.powerge.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HuanBaoJianCeFragment extends Fragment {
    HuanBaoJianCeFragment jianCeFragment;

    public HuanBaoJianCeFragment() {
    }

    public HuanBaoJianCeFragment newInstance() {
        if (jianCeFragment == null) {
            jianCeFragment = new HuanBaoJianCeFragment();
        }
        return jianCeFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_huan_bao_jian_ce, container, false);
    }

}
