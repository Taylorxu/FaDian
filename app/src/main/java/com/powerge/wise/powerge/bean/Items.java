package com.powerge.wise.powerge.bean;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.format.DateFormat;
import android.text.style.ForegroundColorSpan;

import java.util.Date;

/**
 * Created by Administrator on 2018/2/9.
 */

public class Items {
    int id;
    public String name;
    public int number;
    public int icon;

    String text1;
    String text2;
    String text3;
    String text4;
    String text5;
    String text6;
    int state;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }


    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        Date date = new Date(System.currentTimeMillis());
        String r = (String) DateFormat.format("yyyy-MM-dd hh:MM:ss", date);
        return r;

    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public String getText3() {
        return text3;
    }

    public void setText3(String text3) {
        this.text3 = text3;
    }

    public String getText4() {
        return text4;
    }

    public void setText4(String text4) {
        this.text4 = text4;
    }

    public String getText5() {
        return text5;
    }

    public void setText5(String text5) {
        this.text5 = text5;
    }

    public String getText6() {
        return text6;
    }

    public void setText6(String text6) {
        this.text6 = text6;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    String stateArry[] = new String[]{"处理中", "超计划", "已完成"};

    public SpannableString getFormState() {
        SpannableString s = new SpannableString(stateArry[state]);
        if (state == 0) {//处理中
            s.setSpan(new ForegroundColorSpan(Color.rgb(17, 189, 106)), 0, 3, 0);
        } else if (state == 1) {//超计划
            s.setSpan(new ForegroundColorSpan(Color.rgb(227, 30, 77)), 0, 3, 0);
        } else {
            s.setSpan(new ForegroundColorSpan(Color.rgb(255, 138, 0)), 0, 3, 0);
        }
        return s;
    }
}
