package com.wisesignsoft.OperationManagement.view;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wisesignsoft.OperationManagement.R;
import com.wisesignsoft.OperationManagement.bean.ColumnsJsonBean;
import com.wisesignsoft.OperationManagement.bean.ResModeValue;
import com.wisesignsoft.OperationManagement.bean.ResModelConfigure;
import com.wisesignsoft.OperationManagement.bean.WorkOrder;
import com.wisesignsoft.OperationManagement.ui.activity.ConfigureListActivity;
import com.wisesignsoft.OperationManagement.ui.activity.MultSelectResModelActivity;
import com.wisesignsoft.OperationManagement.ui.activity.SelectAccountActivity2;
import com.wisesignsoft.OperationManagement.view.mview.ListDateView;
import com.wisesignsoft.OperationManagement.view.mview.WorkOrderDataManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 添加配置项列表
 * Created by ycs on 2016/12/27.
 */

public class MultSelectConfigureChangeView extends ListDateView implements ListDateView.IListDateClickListener {
    private WorkOrder wo;
    private ResModelConfigure datas;

    public MultSelectConfigureChangeView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        initView(context, R.mipmap.peizhi, "添加配置项");
        setIListDataClickListener(this);
    }

    public void setData(WorkOrder wo) {
        this.wo = wo;
        setTitle(wo.getName());
        String configure = wo.getResModelConfigure();
        Gson gson = new Gson();
        datas = gson.fromJson(configure, ResModelConfigure.class);
        String str = wo.getValue();
        List<Map<String, Object>> list;
//        if (TextUtils.isEmpty(str)) {
//            Map<String, Object> map = new HashMap<>();
//            list = new ArrayList<>();
//            list.add(map);
//        } else {
//
//        }

        if (!TextUtils.isEmpty(str)) {
            list = gson.fromJson(str, new TypeToken<List<Map<String, Object>>>() {
            }.getType());
            clearView();
            for (Map<String, Object> map : list) {

                StandBookView standBookView = new StandBookView(getContext());
                addStandBook(standBookView);
                standBookView.setData2(getContext(), datas, map, 1);
            }
        }
    }

    @Override
    public void setOnAddView() {
        Gson gson = new Gson();
        ResModelConfigure c = gson.fromJson(wo.getResModelConfigure(), ResModelConfigure.class);
        SelectAccountActivity2.startSelf(getContext(), wo.getValue(), wo.getID(), c.getClassName(), c.getDisplayName());
    }

    @Override
    public void setOnDelView() {
        Gson gson = new Gson();
        String str = wo.getValue();
        List<Map<String, String>> list = gson.fromJson(str, new TypeToken<List<Map<String, String>>>() {
        }.getType());
        list.remove(0);
        String temp = gson.toJson(list);
        WorkOrderDataManager.getManager().setSingleDateById(wo.getID(), temp);
    }
}
