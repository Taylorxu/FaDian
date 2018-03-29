package com.powerge.wise.powerge.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/29.
 */

public class MainPageBean extends RootBean {

    /**
     * allLoadRatio : 0.0
     * unitsLoadDataList : [{"value":1174,"name":"2002"},{"value":878,"name":"1001"}]
     * allLoad : 1174.0
     * allPowerGene : 0.0
     */

    private String allLoadRatio;
    private String allLoad;
    private String allPowerGene;
    private List<UnitsLoadDataListBean> unitsLoadDataList;

    public String getAllLoadRatio() {
        return allLoadRatio;
    }

    public void setAllLoadRatio(String allLoadRatio) {
        this.allLoadRatio = allLoadRatio;
    }

    public String getAllLoad() {
        return allLoad;
    }

    public void setAllLoad(String allLoad) {
        this.allLoad = allLoad;
    }

    public String getAllPowerGene() {
        return allPowerGene;
    }

    public void setAllPowerGene(String allPowerGene) {
        this.allPowerGene = allPowerGene;
    }

    public List<UnitsLoadDataListBean> getUnitsLoadDataList() {
        return unitsLoadDataList;
    }

    public void setUnitsLoadDataList(List<UnitsLoadDataListBean> unitsLoadDataList) {
        this.unitsLoadDataList = unitsLoadDataList;
    }

    public static class UnitsLoadDataListBean {
        /**
         * value : 1174
         * name : 2002
         */

        private int value;
        private String name;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
