package com.powerge.wise.powerge.bean;

import android.text.format.DateFormat;
import android.text.format.DateUtils;

import java.util.Date;

/**
 * Created by Administrator on 2018/2/26.
 */

public class SimpleListTextItem {
    String title;
    String content;
    String date;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFormtDate() {
        Date date = new Date(System.currentTimeMillis());
        String r = (String) DateFormat.format("yyyy-MM-dd", date);
        return r;
    }
}
