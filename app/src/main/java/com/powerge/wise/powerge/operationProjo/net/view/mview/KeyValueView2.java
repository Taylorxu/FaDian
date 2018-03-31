package com.powerge.wise.powerge.operationProjo.net.view.mview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.powerge.wise.powerge.operationProjo.net.bean.ContractBean;
import com.wisesignsoft.OperationManagement.R;

/**
 * Created by ycs on 2016/12/19.
 */

public class KeyValueView2 extends LinearLayout {

    private TextView key;
    private TextView value;

    public KeyValueView2(Context context) {
        super(context);
        init(context);
    }

    public KeyValueView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.key_value_view2, this, true);
        key = (TextView) view.findViewById(R.id.key);
        value = (TextView) view.findViewById(R.id.value);
    }

    public void setData(ContractBean b) {
        String key = b.getKey();
        String value = b.getValue();
        this.key.setText(key);
        this.value.setText(value);
    }
    public void setKeyAndValue(String key,String value){
        this.key.setText(key);
        this.value.setText(value);
    }
}
