package com.powerge.wise.powerge.otherPages.xunJian;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.powerge.wise.basestone.heart.util.DensityUtil;
import com.powerge.wise.basestone.heart.util.LogUtils;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.databinding.FragmentXunJianDateBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link XunJianDateFragment.OnDateCehckedListener} interface
 * to handle interaction events.
 */
public class XunJianDateFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private OnDateCehckedListener mListener;
    private int dateType;
    private int termType;
    private String datep;
    private boolean shouldCheck;
    List<Integer> possition = new ArrayList<>();//记录被渲染fragment的位置

    public XunJianDateFragment() {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            shouldCheck = true;
            checkRadioBtn();
        } else {
            shouldCheck = false;
        }
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
        binding.monthGroup.setOnCheckedChangeListener(this);
        checkRadioBtn();
        return binding.getRoot();
    }

    public void checkRadioBtn() {
        LogUtils.e("type=" + dateType + "--------should" + shouldCheck);
        if (shouldCheck && null != binding) {
            createMonthGroup();

          /*  for (int l = 0; l < binding.monthGroup.getChildCount(); l++) {
                if (l == (binding.monthGroup.getChildCount() - 1) / 2) {
                    RadioButton radioButton = (RadioButton) binding.monthGroup.getChildAt(l);
                    radioButton.setChecked(true);
                }
            }*/
        }
    }



    //初始化 月,周，日份 buttion 列表
    @SuppressLint("ResourceType")
    private void createMonthGroup() {
        if(binding.monthGroup.getChildCount()>0)binding.monthGroup.removeAllViews();
        String [] week = new String[]{"上一周", "本周", "下一周"};
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

            if (leng > 3) {
                if(i==3)radioButton.setChecked(true);
                radioButton.setText(dateList.get(i));
                radioButton.setTag(dateList.get(i));
            } else {
                if(i==1)radioButton.setChecked(true);
                radioButton.setText(week[i]);
                radioButton.setTag(week[i]);
            }

            radioButton.setId(i);
            binding.monthGroup.addView(radioButton);

        }
    }

    LinkedList<String> dateList = new LinkedList<>();

    @SuppressLint("WrongConstant")
    public String getfirstDayOfWeek(int type) {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        int current_day, current_month, current_year;
        String mYear, mMonth, mDay;
        current_year = c.get(Calendar.YEAR);
        current_day = c.get(Calendar.DAY_OF_WEEK);
        current_month = c.get(Calendar.MONTH);
        c.clear();
        c.set(Calendar.MONTH, current_month);
        c.set(Calendar.DAY_OF_MONTH, current_day);
        c.set(Calendar.YEAR, current_year);

        if (type == 2) {//获取下周第一天
            c.add(Calendar.DATE, +7);
        } else if (type == 0) {// 上周 的一天
            c.add(Calendar.DATE, -7);
        } else {//本周，获取本周第一天

        }
        mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前日份的日期号码
        mYear = String.valueOf(c.get(Calendar.YEAR));// 获取当前年份
        return mYear + "-" + mMonth + "-" + mDay;


    }

    public void createDay(int dateType) {
        if (dateList.size() > 0) {
            dateList.clear();
        }
        Calendar c = Calendar.getInstance();
        int current_day, current_month, current_year;
        String mYear, mMonth, mDay;
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

    }


    public void onButtonPressed(int checkedId) {
        if (mListener != null) {
            Map<String, String> p = new HashMap<>();
            p.put("termType", String.valueOf(termType));
            p.put("date", datep);
            p.put("checkId", String.valueOf(checkedId));
            mListener.onDateCehckedListener(p);
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

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (shouldCheck) {//可见的dateType 作为参数
            if (dateType == 1) {//周期 为周
                termType = 2;
                datep = getfirstDayOfWeek(checkedId);
            } else if (dateType == 0 || dateType == 2) {
                String dateChecked = dateList.get(checkedId);
                if (dateChecked.indexOf("年") < 0) {//日
                    dateChecked = Calendar.getInstance().get(Calendar.YEAR) + "年" + dateChecked;
                } else if (dateChecked.indexOf("日") < 0) {
                    dateChecked += Calendar.getInstance().get(Calendar.DATE) + "日";
                }
                dateChecked = dateChecked.replace("年", "-").replace("月", "-").replace("日", "");
                datep = dateChecked;
                if (dateType == 0) {
                    termType = 3;//月份
                } else if (dateType == 2) {
                    termType = 1;//日
                }
            }
            onButtonPressed(checkedId);
        }
    }

    public interface OnDateCehckedListener {
        // TODO: Update argument type and name
        void onDateCehckedListener(Map<String, String> p);
    }
}
