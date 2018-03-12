package com.powerge.wise.powerge.otherPages.xunJian;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.hyphenate.util.DensityUtil;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.databinding.FragmentXunJianDateBinding;
import com.wisesignsoft.OperationManagement.utils.LogUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link XunJianDateFragment.OnDateCehckedListener} interface
 * to handle interaction events.
 */
public class XunJianDateFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private OnDateCehckedListener mListener;
    private int dateType;

    public XunJianDateFragment() {

    }

    public static XunJianDateFragment newInstance(int sectionNumber) {
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        XunJianDateFragment fragment = new XunJianDateFragment();
        fragment.setArguments(args);
        return fragment;
    }

    FragmentXunJianDateBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_xun_jian_date, container, false);
        dateType = getArguments().getInt(ARG_SECTION_NUMBER, 0);
        createMonthGroup();
        return binding.getRoot();
    }

    String week[] = new String[]{"上一周", "本周", "下一周"};

    //初始化 月,周，日份 buttion 列表
    @SuppressLint("ResourceType")
    private void createMonthGroup() {
        int leng = 7, radioBtWidth = 60;
        if (dateType == 0) {
            radioBtWidth = 90;
            createDay(dateType);
        } else if (dateType == 2) {
            createDay(dateType);
        } else if (dateType == 1) {
            radioBtWidth = 90;
            leng = 3;
        }

        for (int i = 0; i < leng; i++) {
            RadioButton radioButton = new RadioButton(getContext());
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(DensityUtil.dip2px(getContext(), radioBtWidth), DensityUtil.dip2px(getContext(), 34));
            params.setMargins(DensityUtil.dip2px(getContext(), 5), DensityUtil.dip2px(getContext(), 10), DensityUtil.dip2px(getContext(), 5), DensityUtil.dip2px(getContext(), 10));
            radioButton.setLayoutParams(params);
            radioButton.setGravity(Gravity.CENTER);

            radioButton.setTextColor(getResources().getColorStateList(R.drawable.selector_primary_gray_text));
            radioButton.setBackgroundResource(R.drawable.selector_primary_white_btn_bg_border);
            radioButton.setButtonDrawable(android.R.color.transparent);//隐藏单选圆形按钮
            radioButton.setTextSize(14f);
            if (i == 3 || i == 1) {
                radioButton.setChecked(true);
            }
            if (leng > 3) {
                radioButton.setText(dateList.get(i));
                radioButton.setTag(dateList.get(i));
            } else {
                radioButton.setText(week[i]);
                radioButton.setTag(week[i]);
            }

            radioButton.setId(i);
            binding.monthGroup.addView(radioButton);

        }
    }

    LinkedList<String> dateList = new LinkedList<>();

    public void createDay(int dateType) {
        Calendar c = Calendar.getInstance();
        int current_day, current_month, current_year;
        String mYear, mMonth, mDay = null;
        current_year = c.get(Calendar.YEAR);
        current_day = c.get(Calendar.DATE);
        current_month = c.get(Calendar.MONTH);


        for (int i = 1; i < 4; i++) {
            c.clear();
            c.set(Calendar.MONTH, current_month);
            c.set(Calendar.DAY_OF_MONTH, current_day);
            c.set(Calendar.YEAR, current_year);

            if (dateType == 2) c.add(Calendar.DATE, -i);//j记住是DATE
            if (dateType == 0) c.add(Calendar.MONTH, -i);
            mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
            mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前日份的日期号码
            mYear = String.valueOf(c.get(Calendar.YEAR));// 获取当前年份
            if (dateType == 0) dateList.add(mYear + "年" + mMonth + "月");
            if (dateType == 2) dateList.add(mMonth + "月" + mDay + "日");

        }
        Collections.reverse(dateList);
        for (int i = 0; i < 4; i++) {
            c.clear();
            c.set(Calendar.MONTH, current_month);
            c.set(Calendar.DAY_OF_MONTH, current_day);
            c.set(Calendar.YEAR, current_year);
            if (dateType == 2) c.add(Calendar.DATE, +i);//j记住是DATE
            if (dateType == 0) c.add(Calendar.MONTH, +i);
            mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
            mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前日份的日期号码
            mYear = String.valueOf(c.get(Calendar.YEAR));// 获取当前年份
            if (dateType == 0) dateList.add(mYear + "年" + mMonth + "月");
            if (dateType == 2) dateList.add(mMonth + "月" + mDay + "日");

        }
        LogUtil.log(dateList.toString());
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onDateCehckedListener(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDateCehckedListener) {
            mListener = (OnDateCehckedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnDateCehckedListener {
        // TODO: Update argument type and name
        void onDateCehckedListener(Uri uri);
    }
}
