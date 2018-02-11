package com.wisesignsoft.OperationManagement.db;

import android.text.TextUtils;

import com.hyphenate.easeui.domain.EaseUser;
import com.wisesignsoft.OperationManagement.R;
import com.wisesignsoft.OperationManagement.bean.OrdinaryModel;
import com.wisesignsoft.OperationManagement.bean.User;
import com.wisesignsoft.OperationManagement.net.RequestTask;
import com.wisesignsoft.OperationManagement.net.request.RequestYxyw;
import com.wisesignsoft.OperationManagement.net.response.QueryValidUsersByAccountResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ycs on 2017/1/2.
 */

public class UserDataManager {
    private static UserDataManager manager;
    private UserDataManager(){}
    public static synchronized UserDataManager getManager(){
        if(manager == null){
            manager = new UserDataManager();
        }
        return manager;
    }

    /**
     * 保存用户信息
     * 逻辑：从本地文件中取出用户表，先根据id判断是否已经保存过这个用户，如果没有就去服务器获取这个用户信息并保存。
     * @param userName
     */
    public void setUser(String userName){
        List<EaseUser> list = MySharedpreferences.getUserList();
        if(list == null){
            list = new ArrayList<>();
        }
        if(list.size() == 0){
            requestUserInfo(userName);
        }else {
            if(!isContant(userName,list)){
                requestUserInfo(userName);
            }
        }
    }

    /**
     * 主动跟别人聊天的时候，可以直接获取到对方的信息不需要请求网络
     * @param user
     */
    public void setUser(EaseUser user){
        List<EaseUser> list = MySharedpreferences.getUserList();
        if(list == null){
            list = new ArrayList<>();
        }
        if(list.size() == 0){
            list.add(user);
        }else {
            for(EaseUser eu:list){
                if(eu.getUsername().equals(user.getUsername())){
                    return;
                }
            }
            list.add(user);
        }
    }

    /**
     * 根据id获取name
     * 逻辑：从本地取出用户表，先判断是否本地存在这个用户，如果没有就去网络获取
     * @param userName
     * @return
     */
    public String getUser(String userName){
        List<EaseUser> list = MySharedpreferences.getUserList();
        if(list == null){
            list = new ArrayList<>();
        }
        if(list.size() == 0){
            requestUserInfo(userName);
        }else {
            String name = getName(userName,list);
            if(TextUtils.isEmpty(name)){
                requestUserInfo(userName);
            }else {
                return name;
            }
        }
        return "";
    }

    /**
     * 判断该用户是否已经被保存过
     * @param userName
     * @param list
     * @return
     */
    private boolean isContant(String userName,List<EaseUser> list){
        for(EaseUser eu:list){
            if(eu.getUsername().equals(userName)){
                return true;
            }
        }
        return false;
    }
    /**
     * 根据id去获取name
     * @param userName
     * @param list
     * @return
     */
    private String getName(String userName,List<EaseUser> list){
        for(EaseUser eu:list){
            if(eu.getUsername().equals(userName)){
                return eu.getNick();
            }
        }
        return "";
    }
    private void requestUserInfo(final String userName){
        List<String> list = new ArrayList<>();
        list.add(userName);
        RequestYxyw.queryValidUsersByAccount(list, new RequestTask.ResultCallback<QueryValidUsersByAccountResponse>() {
            @Override
            public void onError(Exception e) {

            }

            @Override
            public void onResponse(QueryValidUsersByAccountResponse response) {
                EaseUser user = new EaseUser(userName);
                user.setNick(response.getReturnValue().getUserName());
                MySharedpreferences.putUserList(user);
            }
        });
    }
}
