package com.powerge.wise.powerge.operationProjo.net.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.operationProjo.net.bean.WorkOrder;
import com.powerge.wise.powerge.operationProjo.net.net.RequestTask;
import com.powerge.wise.powerge.operationProjo.net.net.request.RequestYxyw;
import com.powerge.wise.powerge.operationProjo.net.net.response.QueryValidUsersByIdsResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * 人员展示控件
 * Created by ycs on 2016/12/2.
 */

public class DataDisplayUserView extends RelativeLayout {

    private TextView tv_data_display_user_text;
    private TextView tv_data_display_user;

    public DataDisplayUserView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.data_display_user, this, true);
        tv_data_display_user_text = (TextView) view.findViewById(R.id.tv_data_display_user_text);
        tv_data_display_user = (TextView) view.findViewById(R.id.tv_data_display_user);
    }

    public void setData(WorkOrder wo) {
        String title = wo.getName();
        String content = wo.getValue();
        String display = wo.getDisplayName();
        if (!TextUtils.isEmpty(title)) {
            if (wo.isRequired()) {
                tv_data_display_user_text.setText(title + " *");
            } else {
                tv_data_display_user_text.setText(title);
            }
        }
        if (!TextUtils.isEmpty(content)) {
            request(content);
        }else {
            if (!TextUtils.isEmpty(display)){
                tv_data_display_user.setText(display);
            }
        }
    }

    private void request(String id) {
        List<String> list = new ArrayList<>();
        list.add(id);
        RequestYxyw.queryValidUsersByIds(list, new RequestTask.ResultCallback<QueryValidUsersByIdsResponse>() {
            @Override
            public void onError(Exception e) {

            }

            @Override
            public void onResponse(QueryValidUsersByIdsResponse response) {
                try {
                    String userName = response.getReturnValue().get(0).getUserName();
                    if (!TextUtils.isEmpty(userName)) {
                        tv_data_display_user.setText(userName);
                    }else {
                        tv_data_display_user.setText("");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
