package com.wisesignsoft.OperationManagement.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wisesignsoft.OperationManagement.R;
import com.wisesignsoft.OperationManagement.adapter.MultRoleChooseViewAdapter;
import com.wisesignsoft.OperationManagement.bean.GroupBean;
import com.wisesignsoft.OperationManagement.bean.WorkOrder;
import com.wisesignsoft.OperationManagement.net.RequestTask;
import com.wisesignsoft.OperationManagement.net.request.RequestRole;
import com.wisesignsoft.OperationManagement.net.response.FindRoleByGroupIdResponse;
import com.wisesignsoft.OperationManagement.ui.activity.MultRoleChooseActivity;
import com.wisesignsoft.OperationManagement.view.mview.WorkOrderDataManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色多选
 * Created by ycs on 2016/12/15.
 */
public class MultRoleChooseView extends LinearLayout {
    private RecyclerView rv;
    private List<FindRoleByGroupIdResponse.ReturnValueBean> datas = new ArrayList<>();
    private WorkOrder wo;
    private MultRoleChooseViewAdapter adapter;

    public MultRoleChooseView(Context context, WorkOrder wo) {
        super(context);
        init(context,wo);
    }

    private void init(final Context context, WorkOrder wo) {
        View view = LayoutInflater.from(context).inflate(R.layout.mult_role_choose, this, true);
        rv = (RecyclerView) view.findViewById(R.id.rv_mult_role_choose);
       TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_title.setText(wo.getDmAttrDisplayName());
        RecyclerView.LayoutManager manager = new GridLayoutManager(context, 5);
        rv.setLayoutManager(manager);

        adapter = new MultRoleChooseViewAdapter(context, datas);
        rv.setAdapter(adapter);
        adapter.setOnIMultUserChooseListener(new MultRoleChooseViewAdapter.IMultRoleChoose() {
            @Override
            public void setOnIMultRoleChoose(FindRoleByGroupIdResponse.ReturnValueBean bean) {
                if (MultRoleChooseView.this.wo.isModified()) {
                    datas.remove(bean);
                    adapter.notifyDataSetChanged();
                    StringBuffer sb = new StringBuffer();
                    for (int i = 0; i < datas.size() - 1; i++) {
                        String id = datas.get(i).getRoleId();
                        if (i == datas.size() - 2) {
                            sb.append(id);
                        } else {
                            sb.append(id).append(",");
                        }
                    }
                    MultRoleChooseView.this.wo.setValue(sb.toString());
                    Gson gson = new Gson();
                    GroupBean groupBean = gson.fromJson(MultRoleChooseView.this.wo.getParentRoleJSON(), GroupBean.class);
                    WorkOrderDataManager.getManager().setSingleDateById2(groupBean.getGroupId(), sb.toString());
                }
            }

            @Override
            public void setOnAddClick() {
                if (MultRoleChooseView.this.wo.isModified()) {
                    Gson gson = new Gson();
                    GroupBean bean = gson.fromJson(MultRoleChooseView.this.wo.getParentRoleJSON(), GroupBean.class);
                    MultRoleChooseActivity.startSelf(context, MultRoleChooseView.this.wo, bean.getGroupId(), MultRoleChooseView.this.wo.getID(), MultRoleChooseView.this.wo.getValue());

                }
            }
        });
    }

    public void setData(WorkOrder wo) {
        this.wo = wo;
        List<String> list = new ArrayList<>();
        String ids = wo.getValue();
        list.add(ids);
        RequestRole.findRoleByIds(list, new RequestTask.ResultCallback<FindRoleByGroupIdResponse>() {
            @Override
            public void onError(Exception e) {

            }

            @Override
            public void onResponse(FindRoleByGroupIdResponse response) {
                List<FindRoleByGroupIdResponse.ReturnValueBean> returnValue = response.getResult();
                if (datas != null) {
                    datas.clear();
                }

                if (returnValue != null)
                    datas.addAll(returnValue);
                datas.add(new FindRoleByGroupIdResponse.ReturnValueBean());
                adapter.notifyDataSetChanged();
            }
        });
    }
}
