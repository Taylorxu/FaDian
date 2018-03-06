package com.powerge.wise.powerge.otherPages.JingSai;


import android.annotation.SuppressLint;
import android.database.DatabaseUtils;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.hyphenate.util.DensityUtil;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.bean.KaoHeChildItemBean;
import com.powerge.wise.powerge.bean.PaiMingChildItemBean;
import com.powerge.wise.powerge.databinding.FragmentJingSaiPaiMingBinding;

import java.util.ArrayList;
import java.util.List;

public class JingSaiPaiMingFragment extends Fragment {
    FragmentJingSaiPaiMingBinding binding;

    public JingSaiPaiMingFragment() {
    }

    public static JingSaiPaiMingFragment newInstance() {
        Bundle args = new Bundle();
        JingSaiPaiMingFragment fragment = new JingSaiPaiMingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_jing_sai_pai_ming, container, false);
        getData();
        initView();
        return binding.getRoot();
    }

    private void initView() {
        createMonthGroup();

        final JingSaiPaiMingExpandAdapter expandAdapter = new JingSaiPaiMingExpandAdapter(dataAll);
        binding.contentPaiMing.setAdapter(expandAdapter);
        binding.contentPaiMing.setGroupIndicator(null);
        binding.contentPaiMing.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                boolean groupExpanded = parent.isGroupExpanded(groupPosition);
                expandAdapter.setIndicatorState(groupPosition, groupExpanded);
                return false;
            }
        });
    }


    @SuppressLint("ResourceType")
    private void createMonthGroup() {
        for (int i = 1; i < 13; i++) {
            RadioButton radioButton = new RadioButton(getContext());
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(DensityUtil.dip2px(getContext(), 60), DensityUtil.dip2px(getContext(), 34));
            params.setMargins(DensityUtil.dip2px(getContext(), 5), DensityUtil.dip2px(getContext(), 10), DensityUtil.dip2px(getContext(), 5), DensityUtil.dip2px(getContext(), 10));
            radioButton.setLayoutParams(params);
            radioButton.setGravity(Gravity.CENTER);
            radioButton.setTextColor(getResources().getColorStateList(R.drawable.selector_primary_gray_text));
            radioButton.setBackgroundResource(R.drawable.selector_primary_white_btn_bg_border);
            radioButton.setButtonDrawable(android.R.color.transparent);//隐藏单选圆形按钮
            radioButton.setTextSize(14f);
            radioButton.setText(i + "月");
            radioButton.setTag(i + "月");
            if (i == 1) radioButton.setChecked(true);
            radioButton.setId(i);
            binding.monthGroup.addView(radioButton);

        }
    }

    List<PaiMingChildItemBean> dataAll = new ArrayList<>();

    private void getData() {
        for (int i = 0; i < 5; i++) {
            PaiMingChildItemBean bean = new PaiMingChildItemBean();
            bean.setZhiBan(i + 1 + "值");
            bean.setMingCi("第" + (i + 1) + "名");
            List<PaiMingChildItemBean.PmChildBean> datac = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                PaiMingChildItemBean.PmChildBean childBean = new PaiMingChildItemBean.PmChildBean();
                childBean.setHour_value("34");
                childBean.setZhi_biao("1#机组耗煤");
                childBean.setAllStart("100");
                datac.add(childBean);
            }

            bean.setKh_child(datac);
            dataAll.add(bean);
        }
    }

}
