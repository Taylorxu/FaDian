package com.powerge.wise.powerge.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.powerge.wise.powerge.BR;

/**
 * Created by Administrator on 2018/3/26.
 */

public class ZhiBaioNameBean extends RootBean {
    /**
     * { name= "指标名称";
     * status= "指标状态";} 0=关闭 1 打开
     */
    String id;
    String name;
    String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Bindable
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        notifyPropertyChanged(BR.status);
    }

    public boolean getVisibility() {
        if ("0".equals(status)) {
            return false;
        } else {
            return true;
        }
    }
}
