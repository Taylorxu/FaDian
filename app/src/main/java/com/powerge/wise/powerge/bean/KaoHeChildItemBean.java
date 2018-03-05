package com.powerge.wise.powerge.bean;

import android.text.format.DateFormat;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/3/5.
 */

public class KaoHeChildItemBean {

    /**
     * kh_time : 2019
     * kh_child : [{"time":"10202","zhi_biao":"OS","hour_value":"232","hb_limit":"898"}]
     */

    private String kh_time;
    private List<KhChildBean> kh_child;

    public String getKh_time() {
        return kh_time;
    }

    public String getFormtKh_time() {
        Date date = new Date(System.currentTimeMillis());
//        String r = (String) DateFormat.format("yyyy-MM", date);
        java.text.DateFormat dateFormat= java.text.DateFormat.getDateTimeInstance(java.text.DateFormat.YEAR_FIELD,java.text.DateFormat.MONTH_FIELD);
        return dateFormat.format(date);
    }

    public void setKh_time(String kh_time) {
        this.kh_time = kh_time;
    }

    public List<KhChildBean> getKh_child() {
        return kh_child;
    }

    public void setKh_child(List<KhChildBean> kh_child) {
        this.kh_child = kh_child;
    }

    public static class KhChildBean {
        /**
         * time : 10202
         * zhi_biao : OS
         * hour_value : 232
         * hb_limit : 898
         */

        private String time;
        private String zhi_biao;
        private String hour_value;
        private String hb_limit;

        public String getTime() {
            return time;
        }

        public String getFormTime() {
            Date date = new Date(System.currentTimeMillis());
            String r = (String) DateFormat.format("MM/dd hh:mm:dd", date);
            return r;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getZhi_biao() {
            return zhi_biao;
        }

        public void setZhi_biao(String zhi_biao) {
            this.zhi_biao = zhi_biao;
        }

        public String getHour_value() {
            return hour_value;
        }

        public void setHour_value(String hour_value) {
            this.hour_value = hour_value;
        }

        public String getHb_limit() {
            return hb_limit;
        }

        public void setHb_limit(String hb_limit) {
            this.hb_limit = hb_limit;
        }
    }
}
