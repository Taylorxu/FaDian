package com.powerge.wise.powerge.operationProjo.net.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.operationProjo.net.bean.ConfigureBean;
import com.powerge.wise.powerge.operationProjo.net.bean.WorkOrder;
import com.powerge.wise.powerge.operationProjo.net.ui.activity.SelectAccountActivity4;

import java.util.Map;

/**
 * Created by ycs on 2017/1/5.
 */

public class ConfigureChangeView extends RelativeLayout implements View.OnClickListener {
    private TextView tv_configure_left;
    private TextView tv_configure_right;
    private WorkOrder wo;
    private RelativeLayout rl_configure;

    public ConfigureChangeView(Context context) {
        super(context);
        init(context);
    }
    private void init(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.configure_change,this,true);
        rl_configure = (RelativeLayout) view.findViewById(R.id.rl_configure);
        tv_configure_left = (TextView) view.findViewById(R.id.tv_configure_left);
        tv_configure_right = (TextView) view.findViewById(R.id.tv_configure_right);
        rl_configure.setOnClickListener(this);
    }
    public void setData(WorkOrder wo){
        this.wo = wo;
        String title = wo.getName();
        String value = wo.getValue();
        Log.i("YCS", "setData: 配置项选择："+title+"===value:"+value);
        if(!TextUtils.isEmpty(title)){
            if(wo.isRequired()){
                tv_configure_left.setText(title+" *");
            }else {
                tv_configure_left.setText(title);
            }
        }
        if(!wo.isModified()) {
            rl_configure.clearFocus();
            rl_configure.setFocusable(false);
            rl_configure.setClickable(false);
        }
        if(!TextUtils.isEmpty(value)){
            Gson gson = new Gson();
            Map<String ,ConfigureBean> data = gson.fromJson(value,new TypeToken<Map<String,ConfigureBean>>(){}.getType());
            ConfigureBean bean = data.get(wo.getDmAttrName());
            tv_configure_right.setText(bean.getTextValue());
        }
    }

    @Override
    public void onClick(View view) {
        String res = wo.getResModelConfigure();
        Gson gson = new Gson();
        Map<String,String> map = gson.fromJson(res,new TypeToken<Map<String,String>>(){}.getType());
        SelectAccountActivity4.startSelf(getContext(),map.get("className"),wo.getDmAttrName(),wo.getID());
    }
}
