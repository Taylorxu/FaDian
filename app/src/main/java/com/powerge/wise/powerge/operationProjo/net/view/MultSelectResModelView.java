package com.powerge.wise.powerge.operationProjo.net.view;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.powerge.wise.powerge.operationProjo.net.bean.Dict;
import com.powerge.wise.powerge.operationProjo.net.bean.ResColumnsJsonBean;
import com.powerge.wise.powerge.operationProjo.net.bean.WorkOrder;
import com.powerge.wise.powerge.operationProjo.net.view.mview.ListDateView;
import com.powerge.wise.powerge.operationProjo.net.view.mview.WorkOrderDataManager;
import com.wisesignsoft.OperationManagement.R;

import java.util.List;
import java.util.Map;

/**
 * 添加台账列表
 * Created by ycs on 2016/12/17.
 */

public class MultSelectResModelView extends ListDateView implements ListDateView.IListDateClickListener {
    private WorkOrder wo;
    private List<ResColumnsJsonBean> datas;

    public MultSelectResModelView(Context context) {
        super(context);
        init(context);
    }
    private void init(Context context){
        initView(context, R.mipmap.taizhang,"添加台账");
        setIListDataClickListener(this);
    }
    public void setData(WorkOrder wo){
        this.wo = wo;
        setTitle(wo.getName());
        String columnsJson = wo.getColumnsJson();
        Gson gson = new Gson();
        datas = gson.fromJson(columnsJson,new TypeToken<List<ResColumnsJsonBean>>(){}.getType());
        String str = wo.getValue();
        List<Map<String,Dict>> list;
//        if(TextUtils.isEmpty(str)){
//            Map<String,Dict> map = new HashMap<>();
//            list = new ArrayList<>();
//            list.add(map);
//        }else {
//
//        }

        if(!TextUtils.isEmpty(str)){
            list = gson.fromJson(str,new TypeToken<List<Map<String,Dict>>>(){}.getType());
            for(Map<String,Dict> map:list){
                StandBookView standBookView = new StandBookView(getContext());
                addStandBook(standBookView);
                standBookView.setData3(getContext(), datas,map,1);
            }
        }
    }

    @Override
    public void setOnAddView() {
    }

    @Override
    public void setOnDelView() {
        Gson gson = new Gson();
        String str  = wo.getValue();
        List<Map<String,String>> list = gson.fromJson(str,new TypeToken<List<Map<String,String>>>(){}.getType());
        list.remove(0);
        String temp = gson.toJson(list);
        WorkOrderDataManager.getManager().setSingleDateById(wo.getID(),temp);
    }
}
