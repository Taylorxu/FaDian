package com.powerge.wise.powerge.bean;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.SpannedString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

/**
 * Created by Administrator on 2018/3/6.
 */

public class XunJianSign {
    public boolean isSign;
    public String xunJianDian;
    public String shouldSign;

    public boolean isSign() {
        return isSign;
    }

    public void setSign(boolean sign) {
        isSign = sign;
    }

    public String getXunJianDian() {
        return xunJianDian;
    }

    public void setXunJianDian(String xunJianDian) {
        this.xunJianDian = xunJianDian;
    }

    public String getShouldSign() {
        return shouldSign;
    }

    public void setShouldSign(String shouldSign) {
        this.shouldSign = shouldSign;
    }

    /**
     * 为巡检次数设置颜色
     *
     * @return
     */
    public SpannableString formShouldSign() {
        SpannableString s = new SpannableString(getShouldSign());
        int index = shouldSign.indexOf("/");
        s.setSpan(new ForegroundColorSpan(Color.rgb(66, 177, 255)), 0, index, 0);
        s.setSpan(new ForegroundColorSpan(Color.rgb(153, 153, 153)), index, shouldSign.length(), 0);
        return s;
    }
}
