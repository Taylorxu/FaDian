package com.wisesignsoft.OperationManagement;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.baidu.mapapi.SDKInitializer;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.lzy.imagepickerdemo.imageloader.GlideImageLoader;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.wisesignsoft.OperationManagement.chat.MDHelper;
import com.wisesignsoft.OperationManagement.net.response.CreateCiResponse;
import com.wisesignsoft.OperationManagement.utils.CrashHandler;

import org.android.agoo.huawei.HuaWeiRegister;

/**
 * Created by ycs on 2016/11/17.
 */

public class MyApplication extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        SDKInitializer.initialize(context);
        MDHelper.getInstance().init(getContext());
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
        initImagePick();
        initPush();
    }

    private void initPush() {
        /**
         * 初始化common库
         * 参数1:上下文，不能为空
         * 参数2:设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
         * 参数3:Push推送业务的secret
         */
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "3744d14862a345832c4572313f95b25e");
        HuaWeiRegister.register(getBaseContext());
        PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                Log.e("deviceToken=== ",deviceToken);
            }
            @Override
            public void onFailure(String s,  String s1) {
                Log.e(" 友盟初始化失败=== ",s1);
            }
        });
    }

    public static Context getContext() {
        return context;
    }

    /**
     * 初始化imagepick
     */
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
