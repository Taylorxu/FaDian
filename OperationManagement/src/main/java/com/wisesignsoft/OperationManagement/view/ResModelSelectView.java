package com.wisesignsoft.OperationManagement.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.google.gson.Gson;
import com.wisesignsoft.OperationManagement.R;
import com.wisesignsoft.OperationManagement.bean.ResModeValue;
import com.wisesignsoft.OperationManagement.bean.ResModeValue2;
import com.wisesignsoft.OperationManagement.bean.WorkOrder;
import com.wisesignsoft.OperationManagement.ui.activity.SelectAccountActivity;
import com.wisesignsoft.OperationManagement.ui.activity.SelectAccountActivity2;
import com.wisesignsoft.OperationManagement.ui.activity.SelectAccountActivity3;
import com.wisesignsoft.OperationManagement.view.mview.BaseView;

import java.util.List;

/**
 * 台账单选组件
 * Created by ycs on 2016/12/15.
 */
public class ResModelSelectView extends FrameLayout {

    private BaseView bv_res_model_select;
    private WorkOrder wo;
    private String modelName;
    private String modelName2;
    private List<ResModeValue.ConfigValueJsonBean> beanList;
    private List<ResModeValue2.ConfigValueJsonBean> beanList2;

    public ResModelSelectView(Context context) {
        super(context);
        init(context);
    }
    private void init(final Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.res_model_select,this,true);
        bv_res_model_select = (BaseView) view.findViewById(R.id.bv_res_model_select);

        bv_res_model_select.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(beanList == null||beanList.size() == 0){
                    SelectAccountActivity3.startSelf(getContext(),wo.getValue(),wo.getID(),wo.getBmModelName(),wo.getDmAttrName());
                }else {
                    SelectAccountActivity.startSelf(context,wo.getResModelValueJson(),wo.getID());
                }
            }
        });
    }
    public void setDate(WorkOrder wo){
        this.wo = wo;
        String title = wo.getName();
        if(!TextUtils.isEmpty(title)){
            if (wo.isRequired()){
                bv_res_model_select.setTv_left(title+" *");
            }else {
                bv_res_model_select.setTv_left(title);
            }
        }
        if(!wo.isModified()){
            bv_res_model_select.setFocusable(false);
            bv_res_model_select.setClickable(false);
            bv_res_model_select.clearFocus();
        }
        String att = wo.getResModelValueJson();
        Gson gson = new Gson();
        ResModeValue c = gson.fromJson(att,ResModeValue.class);
        modelName = c.getToBmModelName();
        beanList = c.getConfigValueJson();
        if(beanList == null || beanList.size() == 0){
            ResModeValue2 c2 = gson.fromJson(att,ResModeValue2.class);
            modelName2 = c2.getToBmModelName();
            beanList2 = c2.getConfigValueJson();
        }
    }
}
