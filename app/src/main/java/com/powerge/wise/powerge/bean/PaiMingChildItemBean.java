package com.powerge.wise.powerge.bean;

import android.text.format.DateFormat;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/3/5.
 */

public class PaiMingChildItemBean {



    private String mingCi;
    private String zhiBan;
    private List<PmChildBean> kh_child;

    public String getMingCi() {
        return mingCi;
    }

    public void setMingCi(String mingCi) {
        this.mingCi = mingCi;
    }

    public String getZhiBan() {
        return zhiBan;
    }

    public void setZhiBan(String zhiBan) {
        this.zhiBan = zhiBan;
    }

    public List<PmChildBean> getKh_child() {
        return kh_child;
    }

    public void setKh_child(List<PmChildBean> kh_child) {
        this.kh_child = kh_child;
    }

    public static class PmChildBean {


        private String zhi_biao;
        private String hour_value;
        private String allStart;

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

        public String getAllStart() {
            return allStart;
        }

        public void setAllStart(String allStart) {
            this.allStart = allStart;
        }
    }
}
