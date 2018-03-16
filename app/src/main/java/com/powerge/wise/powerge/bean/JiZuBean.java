package com.powerge.wise.powerge.bean;

import org.simpleframework.xml.Element;

/**
 * Created by Administrator on 2018/3/16.
 */

public class JiZuBean extends RootBean {

    private String name;
    private String id;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static JiZuBean newInstance() {
        return new JiZuBean();
    }
}
