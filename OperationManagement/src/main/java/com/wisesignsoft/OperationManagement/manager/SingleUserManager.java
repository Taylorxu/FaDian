package com.wisesignsoft.OperationManagement.manager;

import com.wisesignsoft.OperationManagement.ui.activity.SingleUserChooseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ycs on 2016/12/21.
 */

public class SingleUserManager {
    private static SingleUserManager manager;
    private SingleUserManager(){}
    public static synchronized SingleUserManager getManager(){
        if(manager == null){
            manager = new SingleUserManager();
        }
        return manager;
    }

    private String temp;
    private List<SingleUserChooseActivity> activities = new ArrayList<>();

    public void setTemp(String temp){
        this.temp = temp;
    }

    /**
     * 逻辑：只能取一次，取完之后就会被销毁
     * @return
     */
    public String getTemp(){
        String result = temp;
        clear();
        return result;
    }
    public void clear(){
        temp = null;
    }
    public void addActivity(SingleUserChooseActivity activity){
        activities.add(activity);
    }
    public void removeActivity(SingleUserChooseActivity activity){
        if(activities.contains(activity)){
            activities.remove(activity);
        }
    }
    public void clearActivities(){
        if(activities.size()>0){
            for(SingleUserChooseActivity activity:activities){
                activity.finish();
            }
        }
    }
}
