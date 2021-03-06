package com.powerge.wise.powerge.operationProjo.net.view.mview;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.powerge.wise.powerge.R;


/**
 * Created by ycs on 2016/12/17.
 */

public class ListItemView extends LinearLayout {

    private KeyValueView kvv_item;

    public ListItemView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_view, this, true);
        kvv_item = view.findViewById(R.id.kvv_list_item);
    }

    public void setDate(String key, String value) {
        if(!TextUtils.isEmpty(key)){
            kvv_item.setKeyText(key+"：");
        }
        if(!TextUtils.isEmpty(value)){
            kvv_item.setValueText(value);
        }
    }
}
