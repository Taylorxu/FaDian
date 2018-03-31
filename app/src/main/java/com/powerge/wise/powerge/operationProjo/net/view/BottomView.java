package com.powerge.wise.powerge.operationProjo.net.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.powerge.wise.powerge.operationProjo.net.bean.WorkOrder;
import com.powerge.wise.powerge.operationProjo.net.net.response.QueryAllValidDictDateResponse;
import com.powerge.wise.powerge.operationProjo.net.view.mview.BaseView;
import com.powerge.wise.powerge.operationProjo.net.view.mview.BottomDialog;
import com.powerge.wise.powerge.operationProjo.net.view.mview.WorkOrderDataManager;
import com.wisesignsoft.OperationManagement.R;

import java.util.List;

/**
 * 下拉控件
 * Created by ycs on 2016/12/5.
 */

public class BottomView extends RelativeLayout implements View.OnClickListener {

    private List<QueryAllValidDictDateResponse.ReturnValueBean.DictDatasBean> datas;
    private BaseView baseView;
    private WorkOrder wo;

    public BottomView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.bottom_view,this,true);
        baseView = (BaseView) view.findViewById(R.id.bv_bottom_view);
        baseView.setOnClickListener(this);

    }
    public void setData(WorkOrder wo, List<QueryAllValidDictDateResponse.ReturnValueBean.DictDatasBean> datas){
        this.wo = wo;
        this.datas = datas;
        String title = wo.getName();
        String content = wo.getValue();
        String value = "";
        for(QueryAllValidDictDateResponse.ReturnValueBean.DictDatasBean bean:datas){
            if(bean.getDictId().equals(content)){
                value = bean.getDictName();
            }
        }
        if(TextUtils.isEmpty(value)){
            value = content;
            for(QueryAllValidDictDateResponse.ReturnValueBean.DictDatasBean bean:datas){
                if(bean.getDictName().equals(value)){
                    String id = bean.getDictId();
                    WorkOrderDataManager.getManager().setSingleDateById2(wo.getID(),id);
                }
            }
        }
        if(!TextUtils.isEmpty(title)){
            if(wo.isRequired()){
                baseView.setTv_left(title+" *");
            }else {
                baseView.setTv_left(title);
            }
        }
        if(!TextUtils.isEmpty(value)){
            baseView.setTv_right(value);
        }else {
            baseView.setTv_right("");
        }
        if(!wo.isModified()){
            baseView.clearFocus();
            baseView.setFocusable(false);
            baseView.setClickable(false);
        }
    }

    @Override
    public void onClick(View view) {
        final BottomDialog dialog = new BottomDialog(getContext(), R.style.add_dialog);
        dialog.setUpData(datas);
        dialog.setOnTitleClickListener(new BottomDialog.OnTitleClickListener() {
            @Override
            public void onTitleRightClickListener(String province,String name) {
                baseView.setTv_right(name);
                WorkOrderDataManager.getManager().setSingleDateById(wo.getID(), province);
            }
        });
        if (!dialog.isShowing()) {
            dialog.show();
        }
    }
}
