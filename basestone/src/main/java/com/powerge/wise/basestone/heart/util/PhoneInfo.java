package com.powerge.wise.basestone.heart.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.powerge.wise.basestone.heart.WApp;

/**
 * Created by Administrator on 2018/1/31.
 */

public class PhoneInfo {


    private static PhoneInfo instance;

    public static PhoneInfo getInstance() {
        if (instance == null)
            instance = new PhoneInfo(WApp.getInstance().getApplicationContext());
        return instance;
    }

    private String imei = android.os.Build.UNKNOWN;//手机标识
    private String model = android.os.Build.UNKNOWN;//手机型号
    private String brand = android.os.Build.UNKNOWN;//手机品牌
    private String version = android.os.Build.UNKNOWN;//版本号
    private String channel = android.os.Build.UNKNOWN;//渠道

    public PhoneInfo(Context context) {
        this.model = android.os.Build.MODEL;
        this.brand = android.os.Build.BRAND;
        this.version = "0.1.1";
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (telephonyManager != null)
                this.imei = telephonyManager.getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (TextUtils.isEmpty(this.imei)) this.imei = android.os.Build.UNKNOWN;
        PackageManager packageManager = context.getPackageManager();
        if (packageManager != null) {
            ApplicationInfo applicationInfo = null;
            try {
                applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            if (applicationInfo != null && applicationInfo.metaData != null) {
                channel = applicationInfo.metaData.getString("UMENG_CHANNEL");
            } else this.channel = android.os.Build.UNKNOWN;
        }
    }

    public String getImei() {
        return imei;
    }

    public String getModel() {
        return model;
    }

    public String getBrand() {
        return brand;
    }

    public String getVersion() {
        return version;
    }

    public String getChannel() {
        return channel;
    }
}
