package com.powerge.wise.powerge.operationProjo.net.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.powerge.wise.powerge.operationProjo.net.bean.WorkOrder;
import com.powerge.wise.powerge.operationProjo.net.view.mview.WorkOrderDataManager;
import com.wisesignsoft.OperationManagement.R;


/**
 * 文本域组件
 * Created by ycs on 2016/11/23.
 */
public class TextFieldView extends RelativeLayout implements TextWatcher {

    private EditText et;
    private TextView tv_text_field;
    private TextView tv_text;
    private WorkOrder wo;

    public TextFieldView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.text_field_view, this, true);
        et = (EditText) view.findViewById(R.id.et_text_field);
        tv_text_field = (TextView) view.findViewById(R.id.tv_text_field);
        /*tv_text = (TextView) view.findViewById(R.id.tv_text);
        tv_text.setMovementMethod(ScrollingMovementMethod.getInstance());
        tv_text.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    //通知父控件不要干扰
                    tv_text.getParent().requestDisallowInterceptTouchEvent(true);
                }
                if(event.getAction()==MotionEvent.ACTION_MOVE){
                    //通知父控件不要干扰
                    tv_text.getParent().requestDisallowInterceptTouchEvent(true);
                }
                if(event.getAction()==MotionEvent.ACTION_UP){
                    tv_text.getParent().requestDisallowInterceptTouchEvent(false);
                }
                return false;
            }
        });*/
    }

    /**
     * 普通控件用
     *
     * @param wo
     */
    public void setData(WorkOrder wo) {
        et.addTextChangedListener(this);
        this.wo = wo;
        String title = wo.getName();
        String content = wo.getValue();
        if (!TextUtils.isEmpty(title)) {
            if (wo.isRequired()) {
                tv_text_field.setText(title + " *");
            } else {
                tv_text_field.setText(title);
            }
        }
        if (!wo.isModified()) {
            et.setEnabled(false);
            et.setFocusable(false);
            et.clearFocus();
            et.setVisibility(GONE);
//            tv_text.setVisibility(VISIBLE);
        }
        if (TextUtils.isEmpty(content)) {
            et.setText("");
        }
        if (!TextUtils.isEmpty(content) && !content.equals(et.getText().toString().trim())) {
            et.setText(content);
          /*  if (tv_text.getVisibility() == VISIBLE)
                tv_text.setText(content);*/
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
            tv_text_field.setText(key);
        }
        if (!TextUtils.isEmpty(value)) {
            et.setText(value);
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
        String temp = et.getText().toString().trim();
        WorkOrderDataManager.getManager().setSingleDateById(wo.getID(), temp);
    }

    public String getValue() {
        return et.getText().toString().trim();
    }
}
