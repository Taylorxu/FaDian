package com.powerge.wise.powerge.operationProjo.net.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.operationProjo.net.bean.ButtonModel;
import com.powerge.wise.powerge.operationProjo.net.bean.Section;
import com.powerge.wise.powerge.operationProjo.net.bean.StractgyBean;
import com.powerge.wise.powerge.operationProjo.net.bean.WorkOrder;
import com.powerge.wise.powerge.operationProjo.net.ui.activity.NewTemplateActivity2;
import com.powerge.wise.powerge.operationProjo.net.ui.activity.NewTemplateActivity3;
import com.powerge.wise.powerge.operationProjo.net.ui.activity.NewWorkOrderActivity2;
import com.powerge.wise.powerge.operationProjo.net.ui.activity.OrderSolvedActivity;
import com.powerge.wise.powerge.operationProjo.net.ui.activity.WorkOrderSolvedUserActivity;
import com.powerge.wise.powerge.operationProjo.net.ui.activity.WorkOrderSolvedUserActivity2;
import com.powerge.wise.powerge.operationProjo.net.utils.ToastUtil;
import com.powerge.wise.powerge.operationProjo.net.view.mview.WorkOrderDataManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ycs on 2017/1/5.
 */

public class ButtonView extends LinearLayout {

    private LinearLayout ll_button_view;
    private ButtonModel button;
    private List<ButtonModel.NextNode> trueList = new ArrayList<>();

    public ButtonView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.button_view, this, true);
        ll_button_view = (LinearLayout) view.findViewById(R.id.ll_button_view);
    }

    public void setData(final ButtonModel button, final String con) {
        this.button = button;
        List<ButtonModel.NextNode> list = button.getNextNode();
        for (ButtonModel.NextNode node : list) {
            if ("true".equals(node.getIsDependCondition())) {
                trueList.add(node);
            } else {
                Gson gson = new Gson();
                final String value = gson.toJson(node);
                NoteButtonView noteButtonView = new NoteButtonView(getContext());
                ll_button_view.addView(noteButtonView);
                noteButtonView.setData(node.getNameDesc());
                noteButtonView.setBtOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        WorkOrderDataManager.getManager().setSingleDateById(button.getID(), value);
                        toNode();
                    }
                });
            }
        }
        if (trueList.size() == 1) {
            ButtonModel.NextNode node = trueList.get(0);
            NoteButtonView noteButtonView = new NoteButtonView(getContext());
            ll_button_view.addView(noteButtonView);
            noteButtonView.setData(node.getNameDesc());
            Gson gson = new Gson();
            final String value = gson.toJson(node);
            noteButtonView.setBtOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    WorkOrderDataManager.getManager().setSingleDateById(button.getID(), value);
                    toNode();
                }
            });
        }
        if (trueList.size() > 1) {
            NoteButtonView noteButtonView = new NoteButtonView(getContext());
            ll_button_view.addView(noteButtonView);
            noteButtonView.setData("提交");
            noteButtonView.setBtOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    WorkOrderDataManager.getManager().setLineLoadById(button, con);
                    toNode();
                }
            });
        }
    }

    private void toNode() {
        List date = WorkOrderDataManager.getManager().getDate();
        for (int i = 0; i < date.size(); i++) {
            if (date.get(i) instanceof Section) {
                List<WorkOrder> section = ((Section) date.get(i)).getSection();
                for (int j = 0; j < section.size(); j++) {
                    WorkOrder workOrder = section.get(j);
                    if (workOrder.isRequired() && TextUtils.isEmpty(workOrder.getValue())) {
                        ToastUtil.toast(getContext(), "请设置" + workOrder.getName());
                        return;
                    }
                }
            }
        }

        String value = WorkOrderDataManager.getManager().getSingleDateById(button.getID());
        Gson gson = new Gson();
        ButtonModel.NextNode node = gson.fromJson(value, ButtonModel.NextNode.class);
        String strategy = node.getTaskStrategy();
        StractgyBean bean = gson.fromJson(strategy, StractgyBean.class);
        String key = bean.getStrategyKey();
        String value2 = bean.getStrategyValue();
        Log.i("YCS", "toNode: value2:" + value2);
        StractgyBean.Value value1 = new StractgyBean.Value();
        try {
            value1 = gson.fromJson(value2, StractgyBean.Value.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (key == null) {
            key = "";
        }
        if (getContext() instanceof NewTemplateActivity2) {
            ((NewTemplateActivity2) getContext()).commit();
        } else {
            switch (key) {
                case "assignee":
                    WorkOrderSolvedUserActivity.startSelf(getContext(), value1.getRoleId(), button.getID(), "assignee");
                    break;
                case "assigneeForDept":
                    WorkOrderSolvedUserActivity2.startSelf(getContext(), value1.getRoleId(), button.getID(), "assigneeForDept");
                    break;
                default:
                    if (getContext() instanceof NewWorkOrderActivity2) {
                        ((NewWorkOrderActivity2) getContext()).commit();
                    } else if (getContext() instanceof NewTemplateActivity3) {
                        ((NewTemplateActivity3) getContext()).commit();
                    } else if (getContext() instanceof OrderSolvedActivity) {
                        ((OrderSolvedActivity) getContext()).commit();
                    }
            }
        }
    }
}
