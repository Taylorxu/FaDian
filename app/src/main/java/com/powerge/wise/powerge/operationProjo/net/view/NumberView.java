package com.powerge.wise.powerge.operationProjo.net.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.operationProjo.net.bean.WorkOrder;
import com.powerge.wise.powerge.operationProjo.net.view.mview.WorkOrderDataManager;



/**
 * 数字组件
 * Created by ycs on 2016/12/12.
 */
public class NumberView extends LinearLayout implements TextWatcher {
    private TextView tv_number;
    private EditText et_number;
    private WorkOrder wo;

    public NumberView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.number_view, this, true);
        tv_number = (TextView) view.findViewById(R.id.tv_number);
        et_number = (EditText) view.findViewById(R.id.et_number);

    }

    public void setDate(WorkOrder wo) {
        et_number.addTextChangedListener(this);
        this.wo = wo;
        String title = wo.getName();
        String content = wo.getValue();
        if (!TextUtils.isEmpty(title)) {
            if (wo.isRequired()) {
                tv_number.setText(title + " *");
            } else {
                tv_number.setText(title);
            }
        }
        if (!TextUtils.isEmpty(content) && !content.equals(et_number.getText().toString().trim())) {
            et_number.setText(content);
        }
        if (!wo.isModified()) {
            et_number.clearFocus();
            et_number.setClickable(false);
            et_number.setFocusable(false);
        }
    }

    /**
     * 列表控件赋值用
     *
     * @param key
     * @param value
     * @param i
     */
    public void setData(String key, String value, int i) {
        if (!TextUtils.isEmpty(key)) {
            if (i > 0) {
                tv_number.setText(key + " * ");
            } else {
                tv_number.setText(key);
            }
        }
        if (!TextUtils.isEmpty(value)) {
            et_number.setText(value);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String temp = et_number.getText().toString().trim();
        WorkOrderDataManager.getManager().setSingleDateById(wo.getID(), temp);
    }

    public String getValue() {
        return et_number.getText().toString().trim();
    }
}
