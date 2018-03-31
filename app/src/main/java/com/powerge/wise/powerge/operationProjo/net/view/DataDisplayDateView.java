package com.powerge.wise.powerge.operationProjo.net.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.powerge.wise.powerge.operationProjo.net.bean.WorkOrder;
import com.powerge.wise.powerge.operationProjo.net.view.mview.KeyValueView;
import com.wisesignsoft.OperationManagement.R;

/**
 * 时间展示控件
 * Created by ycs on 2016/12/2.
 */

public class DataDisplayDateView extends LinearLayout {

    private KeyValueView kvv_data_display_date;

    public DataDisplayDateView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.data_display_date, this, true);
        kvv_data_display_date = (KeyValueView) view.findViewById(R.id.kvv_data_display_date);
    }

    public void setData(WorkOrder wo) {
        String title = wo.getName()+"：";
        String content = wo.getValue();
        String displayName = wo.getDisplayName();
        if (!TextUtils.isEmpty(title)) {
            if(wo.isRequired()){
                kvv_data_display_date.setKeyText(title+" *");
            }else {
                kvv_data_display_date.setKeyText(title);
            }
        }
        if (!TextUtils.isEmpty(content)) {
            kvv_data_display_date.setValueText(content);
        }else {
            kvv_data_display_date.setValueText("");
        }
        if (!TextUtils.isEmpty(displayName)){
            kvv_data_display_date.setValueText(displayName);
        }else {
            kvv_data_display_date.setValueText("");
        }
    }
}
