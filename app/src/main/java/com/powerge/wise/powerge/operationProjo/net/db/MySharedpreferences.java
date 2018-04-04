package com.powerge.wise.powerge.operationProjo.net.db;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.powerge.wise.powerge.MyApplication;
import com.powerge.wise.powerge.bean.User;
import com.powerge.wise.powerge.operationProjo.net.Constant;
import com.powerge.wise.powerge.operationProjo.net.ui.login.ServerAddressActivity;


/**
 * Created by Mr.Z on 15/12/11.
 */
public class MySharedpreferences {
    static Context context = MyApplication.getContext();
    static Gson gson = new Gson();
    /*自己信息的sp*/
    static SharedPreferences mySp = context.getSharedPreferences(Constant.USERINFOSP, Activity.MODE_PRIVATE);
    /*状态信息的sp*/
    static SharedPreferences statusSp = context.getSharedPreferences(Constant.STATUSINFO, Activity.MODE_PRIVATE);
    /*服务器地址*/
    static SharedPreferences serverSp = context.getSharedPreferences(Constant.SERVERURL, Activity.MODE_PRIVATE);
    /*所有用户信息的sp*/
    static SharedPreferences userSp = context.getSharedPreferences(Constant.USERINFOSPLIST, Activity.MODE_PRIVATE);

    /*除了服务器地址，其他都清空了*/
    public static void clear() {
        mySp.edit().clear().commit();
        statusSp.edit().clear().commit();
        userSp.edit().clear().commit();
    }

    /**
     * 保存自己信息
     *
     * @param user
     */
    public static void putUser(User user) {
        String json = gson.toJson(user);
        mySp.edit().putString(Constant.USERINFO, json).commit();
    }

    /**
     * 获取自己信息
     *
     * @return
     */
    public static User getUser() {
        String json = mySp.getString(Constant.USERINFO, "");
        User user = gson.fromJson(json, User.class);
        return user;
    }


    /**
     * 获取登录状态
     *
     * @return
     */
    public static boolean getLoginStatusBoolean() {
        boolean status = statusSp.getBoolean(Constant.ISLOGIN, false);
        return status;
    }

    /**
     * 获取是否第一次登录状态
     *
     * @return
     */
    public static boolean getFirstStatusBoolean() {
        boolean status = statusSp.getBoolean(Constant.ISFIRST, false);
        return status;
    }

    /**
     * 保存服务器地址
     *
     * @param value
     * @param callback
     */
    public static void putServerString(String value, ServerAddressActivity.Callback callback) {
        if (serverSp.edit().putString(Constant.URL, value).commit()) {
            callback.onStartLogin();
        } else {
            callback.onFail();
        }
    }

    /**
     * 获取服务器地址
     *
     * @return
     */
    public static String getServerString() {
        String serverUrl = serverSp.getString(Constant.URL, "");
        return serverUrl;
    }

}
