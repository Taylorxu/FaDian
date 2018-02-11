package com.wisesignsoft.OperationManagement.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wisesignsoft.OperationManagement.R;
import com.wisesignsoft.OperationManagement.bean.WorkOrder;
import com.wisesignsoft.OperationManagement.manager.PositionManager;
import com.wisesignsoft.OperationManagement.view.mview.WorkOrderDataManager;

/**
 * Created by ycs on 2017/1/18.
 */

public class PositionView extends RelativeLayout implements View.OnClickListener {

    private TextView tv_title;
    private TextView tv_content;
    private ImageView bt_position;
    private WorkOrder wo;

    public PositionView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.position_view, this, true);
        tv_title = (TextView) view.findViewById(R.id.tv_position_title);
        tv_content = (TextView) view.findViewById(R.id.tv_position_content);
        bt_position = (ImageView) view.findViewById(R.id.bt_position);
        bt_position.setOnClickListener(this);
    }

    public void setData(WorkOrder wo) {
        this.wo = wo;
        String title = wo.getName();
        String content = wo.getValue();
        if (!TextUtils.isEmpty(title)) {
            if (wo.isRequired()) {
                tv_title.setText(title + " *");
            } else {
                tv_title.setText(title);
            }
        }
        if (TextUtils.isEmpty(content)) {
            tv_content.setText("");
        }
        if (!TextUtils.isEmpty(content) && !content.equals(tv_content.getText().toString())) {
            try {
                String[] strings = content.split("\\|");
                tv_content.setText(strings[2]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!wo.isModified()) {
            bt_position.setEnabled(false);
            bt_position.setFocusable(false);
            bt_position.setClickable(false);
        }
    }

    @Override
    public void onClick(View v) {
        String temp = PositionManager.getInstance().getPosition();
        if (TextUtils.isEmpty(temp)) {
            tv_content.setText("");
        } else {
            try {
                String[] strings = temp.split("\\|");
                tv_content.setText(strings[2]);
                WorkOrderDataManager.getManager().setSingleDateById(wo.getID(), strings[2]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
