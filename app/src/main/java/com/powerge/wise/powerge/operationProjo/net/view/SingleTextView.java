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
 * 单行输入组件
 * Created by ycs on 2016/11/30.
 */
public class SingleTextView extends LinearLayout implements TextWatcher {

    private TextView tv_single_text;
    private EditText et_single_text;
    /*数据*/
    private WorkOrder wo;
    private String key;
    public boolean isRequire = false;

    public SingleTextView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_text_view, this, true);
        tv_single_text = (TextView) view.findViewById(R.id.tv_single_text);
        et_single_text = (EditText) view.findViewById(R.id.et_single_text);
    }

    /**
     * 通用工单赋值用
     *
     * @param wo
     */
    public void setData(WorkOrder wo) {
        et_single_text.addTextChangedListener(this);
        this.wo = wo;
        String title = wo.getName();
        String content = wo.getValue();
        if (!TextUtils.isEmpty(title)) {
            if (wo.isRequired()) {
                tv_single_text.setText(title + " *");
            } else {
                tv_single_text.setText(title);
            }
        }
        if (TextUtils.isEmpty(content)) {
            et_single_text.setText("");
        }
        if (!TextUtils.isEmpty(content) && !content.equals(et_single_text.getText().toString())) {
            et_single_text.setText(content);
        }
        if (!wo.isModified()) {
            et_single_text.setEnabled(false);
            et_single_text.setFocusable(false);
            et_single_text.clearFocus();
        }
    }

    /**
     * 列表控件赋值用
     *
     * @param key
     * @param value
     */
    public void setData(String key, String value) {
        if (!TextUtils.isEmpty(key)) {
            tv_single_text.setText(key);
        }
        if (!TextUtils.isEmpty(value)) {
            et_single_text.setText(value);
        }
    }

    public void setRequireData(String key, String value) {
        isRequire = true;
        if (!TextUtils.isEmpty(key)) {
            tv_single_text.setText(key + " *");
        }
        if (!TextUtils.isEmpty(value)) {
            et_single_text.setText(value);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        String temp = et_single_text.getText().toString().trim();
        WorkOrderDataManager.getManager().setSingleDateById(wo.getID(), temp);
    }

    public String getValue() {
        return et_single_text.getText().toString().trim();
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
