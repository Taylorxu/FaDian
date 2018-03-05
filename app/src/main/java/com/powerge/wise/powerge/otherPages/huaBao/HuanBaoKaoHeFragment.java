package com.powerge.wise.powerge.otherPages.huaBao;


import android.database.DatabaseUtils;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.bean.KaoHeChildItemBean;
import com.powerge.wise.powerge.databinding.FragmentHuanBaoKaoHeBinding;

import java.util.ArrayList;
import java.util.List;

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

    FragmentHuanBaoKaoHeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_huan_bao_kao_he, container, false);
        getData();
        iniView();

        return binding.getRoot();
    }

    private void iniView() {
        final KaoHeExpandAdapter expandAdapter = new KaoHeExpandAdapter(dataAll);
        binding.contentKaoHe.setAdapter(expandAdapter);
        binding.contentKaoHe.setGroupIndicator(null);
        binding.contentKaoHe.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                boolean groupExpanded = parent.isGroupExpanded(groupPosition);
                expandAdapter.setIndicatorState(groupPosition, groupExpanded);
                return false;
            }
        });
    }

    List<KaoHeChildItemBean> dataAll = new ArrayList<>();

    private void getData() {
        for (int i = 0; i < 5; i++) {
            KaoHeChildItemBean bean = new KaoHeChildItemBean();
            List<KaoHeChildItemBean.KhChildBean> datac = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                KaoHeChildItemBean.KhChildBean childBean = new KaoHeChildItemBean.KhChildBean();
                childBean.setHb_limit("1222");
                childBean.setHour_value("34");
                childBean.setZhi_biao("OS");
                datac.add(childBean);
            }

            bean.setKh_child(datac);
            dataAll.add(bean);
        }
    }

}
