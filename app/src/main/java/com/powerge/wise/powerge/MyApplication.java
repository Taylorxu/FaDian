package com.powerge.wise.powerge;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.powerge.wise.basestone.heart.WApp;

import java.util.Map;

/**
 * Created by Administrator on 2018/1/30.
 */

public class MyApplication extends WApp {
    @Override
    public void onCreate() {
        super.onCreate();

    }

    /**
     * 请求数据时添加头信息例如用户的唯一凭证
     * @return
     */
    @Override
    public Map<String, String> getHttpHeaders() {
        return null;
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
