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
public class HuanBaoKaoHeFragment extends Fragment {

    HuanBaoKaoHeFragment kaoHeFragment;

    public HuanBaoKaoHeFragment() {
        // Required empty public constructor
    }

    public HuanBaoKaoHeFragment newInstance() {
        if (kaoHeFragment == null) {
            kaoHeFragment = new HuanBaoKaoHeFragment();
        }
        return kaoHeFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_huan_bao_kao_he, container, false);
    }

}
