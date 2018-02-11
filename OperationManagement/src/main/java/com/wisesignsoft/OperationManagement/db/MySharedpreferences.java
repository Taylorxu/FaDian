package com.wisesignsoft.OperationManagement.db;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import com.google.gson.reflect.TypeToken;
import com.hyphenate.easeui.domain.EaseUser;
import com.wisesignsoft.OperationManagement.Constant;
import com.wisesignsoft.OperationManagement.MyApplication;
import com.wisesignsoft.OperationManagement.bean.User;

import java.util.ArrayList;
import java.util.List;


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
    public static void clear(){
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
     * 保存状态
     *
     * @param key
     * @param status
     */
    public static void putStatusBoolean(String key, boolean status) {
        statusSp.edit().putBoolean(key, status).commit();
    }

    /**
     * 获取地图开启状态
     *
     * @return
     */
    public static boolean getMapStatusBoolean() {
        boolean status = statusSp.getBoolean(Constant.ISDING, true);
        return status;
    }

    /**
     * 设置地图状态
     * @param is
     */
    public static void putMapStatusBoolean(boolean is){
        MySharedpreferences.putStatusBoolean(Constant.ISDING,is);
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
     */
    public static void putServerString(String value) {
        serverSp.edit().putString(Constant.URL, value).commit();
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
    /**
     * 保存用户信息
     *
     * @param user
     */
    public static void putUserList(EaseUser user) {
        List<EaseUser> list = getUserList();
        if(list == null){
            list = new ArrayList<>();
        }
        list.add(user);
        String json = gson.toJson(list);
        userSp.edit().putString(Constant.USERINFOLIST, json).commit();
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public static List<EaseUser> getUserList() {
        String json = userSp.getString(Constant.USERINFOLIST, "");
        List<EaseUser> list = gson.fromJson(json,new TypeToken<List<EaseUser>>(){}.getType());
        return list;
    }
}