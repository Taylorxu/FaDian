package com.powerge.wise.powerge.operationProjo.net.manager;


import com.powerge.wise.powerge.operationProjo.net.ui.activity.NewTemplateActivity3;
import com.powerge.wise.powerge.operationProjo.net.ui.activity.NewWorkOrderActivity2;
import com.powerge.wise.powerge.operationProjo.net.ui.activity.OrderSolvedActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ycs on 2017/1/9.
 */
public class WorkOrderSolvingManager {
    private static WorkOrderSolvingManager instance;
    private List<OrderSolvedActivity> list = new ArrayList<>();
    private List<NewWorkOrderActivity2> list2 = new ArrayList<>();
    private List<NewTemplateActivity3> list3 = new ArrayList<>();

    public synchronized static WorkOrderSolvingManager getInstance() {
        if (instance == null) {
            instance = new WorkOrderSolvingManager();
        }
        return instance;
    }

    private WorkOrderSolvingManager() {
    }

    public void setList(OrderSolvedActivity activity) {
        try {
            list.add(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delList(OrderSolvedActivity activity) {
        try {
            list.remove(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        if (list.size() > 0) {
            for (OrderSolvedActivity activity : list) {
                activity.finish();
            }
        }
        if (list2.size() > 0) {
            for (NewWorkOrderActivity2 activity2 : list2) {
                activity2.finish();
            }
        }
        if(list3.size()>0){
            for(NewTemplateActivity3 activity3:list3){
                activity3.finish();
            }
        }
    }

    public void setList2(NewWorkOrderActivity2 activity2) {
        try {
            list2.add(activity2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delList2(NewWorkOrderActivity2 activity2) {
        try {
            list2.remove(activity2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setList3(NewTemplateActivity3 activity3) {
        try {
            list3.add(activity3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delList3(NewTemplateActivity3 activity3) {
        try {
            list3.remove(activity3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
