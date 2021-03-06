package com.powerge.wise.powerge.operationProjo.net.view.mview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.powerge.wise.powerge.R;


/**
 * Created by ycs on 2016/11/24.
 */

public class KeyValueView extends LinearLayout {
    /*key文本*/
    private String keyText;
    /*key颜色*/
    private int keyColor;
    /*key字体大小*/
    private int keySize;
    /*value文本*/
    private String valueText;
    /*value颜色*/
    private int valueColor;
    /*value字体大小*/
    private int valueSize;
    private TextView tv_key;
    private TextView tv_value;

    public KeyValueView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.KeyValueView, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.KeyValueView_keyText) {
                keyText = a.getString(attr);

            } else if (attr == R.styleable.KeyValueView_keyColor) {
                keyColor = a.getColor(attr, Color.BLACK);

            } else if (attr == R.styleable.KeyValueView_keySize) {
                keySize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));

            } else if (attr == R.styleable.KeyValueView_valueText) {
                valueText = a.getString(attr);

            } else if (attr == R.styleable.KeyValueView_valueColor) {
                valueColor = a.getColor(attr, Color.BLACK);

            } else if (attr == R.styleable.KeyValueView_valueSize) {
                valueSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));

            }
        }
        a.recycle();
        View view = LayoutInflater.from(context).inflate(R.layout.key_value_view, this, true);
        tv_key = view.findViewById(R.id.tv_key);
        tv_value = view.findViewById(R.id.tv_value);

        tv_key.setText(keyText);
        tv_key.setTextSize(TypedValue.COMPLEX_UNIT_PX, keySize);
        tv_key.setTextColor(keyColor);

        tv_value.setText(valueText);
        tv_value.setTextSize(TypedValue.COMPLEX_UNIT_PX, valueSize);
        tv_value.setTextColor(valueColor);
    }

    public KeyValueView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public KeyValueView(Context context){
        this(context,null);
    }

    public void setValueText(String string) {
        tv_value.setText(string);
    }

    public void setKeyText(String title) {
        tv_key.setText(title);
    }
}
