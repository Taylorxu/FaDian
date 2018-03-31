package com.powerge.wise.powerge.operationProjo.net.view;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.powerge.wise.powerge.operationProjo.net.bean.ColumnsJsonBean;
import com.powerge.wise.powerge.operationProjo.net.bean.WorkOrder;
import com.powerge.wise.powerge.operationProjo.net.view.mview.ListDateView;
import com.powerge.wise.powerge.operationProjo.net.view.mview.WorkOrderDataManager;
import com.wisesignsoft.OperationManagement.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 添加普通列表
 * Created by ycs on 2016/12/27.
 */

public class MultSelectListView extends ListDateView implements ListDateView.IListDateClickListener {
    private WorkOrder wo;
    private List<ColumnsJsonBean> datas;

    public MultSelectListView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        initView(context, R.mipmap.add1, "添加列表");
        setIListDataClickListener(this);
    }

    public void setData(WorkOrder wo) {
        this.wo = wo;
        if (wo.isRequired()) {
            setTitle(wo.getName() + " *");
        } else {
            setTitle(wo.getName());
        }
        String columnsJson = wo.getColumnsJson();
        Gson gson = new Gson();
        datas = gson.fromJson(columnsJson, new TypeToken<List<ColumnsJsonBean>>() {
        }.getType());
        String str = wo.getValue();
        List<Map<String, String>> list;
        if (TextUtils.isEmpty(str)) {
            Map<String, String> map = new HashMap<>();
            list = new ArrayList<>();
            list.add(map);
        } else {
            list = gson.fromJson(str, new TypeToken<List<Map<String, String>>>() {
            }.getType());
        }
        clearView();
        for (Map<String, String> map : list) {
            StandBookView standBookView = new StandBookView(getContext());
            addStandBook(standBookView);
            standBookView.setData1(getContext(), datas, map, 1, wo.getID());
        }
    }

    @Override
    public void setOnAddView() {
        Gson gson = new Gson();
         /*测试数据*/
//        String str = "[{'p_code':'INC161206002','b_sn':'INC16120602','change_sn':'INC161206002','removed_sn':'INC161206002'}]";
        String str = wo.getValue();
        List<Map<String, String>> list = gson.fromJson(str, new TypeToken<List<Map<String, String>>>() {
        }.getType());
        if (list == null) {
            list = new ArrayList<>();
        }
        Map<String, String> map = new HashMap<>();
        list.add(map);
        String temp = gson.toJson(list);
        WorkOrderDataManager.getManager().setSingleDateById(wo.getID(), temp);
        StandBookView standBookView = new StandBookView(getContext());
        addStandBook(standBookView);
        standBookView.setData1(getContext(), datas, map, 1, wo.getID());
    }

    @Override
    public void setOnDelView() {
        Gson gson = new Gson();
         /*测试数据*/
//        String str = "[{'p_code':'INC161206002','b_sn':'INC16120602','change_sn':'INC161206002','removed_sn':'INC161206002'}]";
        String str = wo.getValue();
        List<Map<String, String>> list = gson.fromJson(str, new TypeToken<List<Map<String, String>>>() {
        }.getType());
        if (list.size() > 1) {
            list.remove(0);
            String temp = gson.toJson(list);
            WorkOrderDataManager.getManager().setSingleDateById(wo.getID(), temp);
        }
    }
}
