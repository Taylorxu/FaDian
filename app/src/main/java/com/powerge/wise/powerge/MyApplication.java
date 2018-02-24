package com.powerge.wise.powerge;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.powerge.wise.basestone.heart.WApp;

import java.io.File;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Administrator on 2018/1/30.
 */

public class MyApplication extends WApp {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        configRealm();
        frescoConfig();
    }

    private void frescoConfig() {
        Fresco.initialize(getInstance());//初始化Fresc
    }

    private void configRealm() {
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("faDian.realm")
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }

    /**
     * 请求数据时添加头信息例如用户的唯一凭证
     *
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
