package com.powerge.wise.powerge;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.lzy.imagepickerdemo.imageloader.GlideImageLoader;
import com.powerge.wise.basestone.heart.WApp;

import java.util.Map;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Administrator on 2018/1/30.
 */

public class MyApplication extends WApp {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Realm.init(this);
        configRealm();
        frescoConfig();
        initImagePick();
    }

    public static Context getContext() {
        return context;
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

    private void initImagePick() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(false);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(9);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
    }
}
