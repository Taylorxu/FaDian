package com.powerge.wise.powerge.bean;

import android.text.format.DateFormat;

import java.util.Date;

/**
 * Created by Administrator on 2018/3/2.
 */

public class HuanBaoBean {


    /**
     * time : 9:00
     * jizu : {"name":"1#机组","SO":"100","NO":"300","yanchen":"600"}
     * huanBaoJu : {"name":"1#机组","SO":"100","NO":"300","yanchen":"600"}
     */

    private String time;
    private JizuBean jizu;
    private HuanBaoJuBean huanBaoJu;

    public String getTime() {
        return time;
    }

    public String getFormTime() {
        Date date = new Date(System.currentTimeMillis());
        String r = (String) DateFormat.format("hh:mm:ss", date);
        return r;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public JizuBean getJizu() {
        return jizu;
    }

    public void setJizu(JizuBean jizu) {
        this.jizu = jizu;
    }

    public HuanBaoJuBean getHuanBaoJu() {
        return huanBaoJu;
    }

    public void setHuanBaoJu(HuanBaoJuBean huanBaoJu) {
        this.huanBaoJu = huanBaoJu;
    }

    public static class JizuBean {
        /**
         * name : 1#机组
         * SO : 100
         * NO : 300
         * yanchen : 600
         */

        private String name;
        private String SO;
        private String NO;
        private String yanchen;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSO() {
            return SO;
        }

        public void setSO(String SO) {
            this.SO = SO;
        }

        public String getNO() {
            return NO;
        }

        public void setNO(String NO) {
            this.NO = NO;
        }

        public String getYanchen() {
            return yanchen;
        }

        public void setYanchen(String yanchen) {
            this.yanchen = yanchen;
        }
    }

    public static class HuanBaoJuBean {
        /**
         * name : 1#机组
         * SO : 100
         * NO : 300
         * yanchen : 600
         */

        private String name;
        private String SO;
        private String NO;
        private String yanchen;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSO() {
            return SO;
        }

        public void setSO(String SO) {
            this.SO = SO;
        }

        public String getNO() {
            return NO;
        }

        public void setNO(String NO) {
            this.NO = NO;
        }

        public String getYanchen() {
            return yanchen;
        }

        public void setYanchen(String yanchen) {
            this.yanchen = yanchen;
        }
    }
}
