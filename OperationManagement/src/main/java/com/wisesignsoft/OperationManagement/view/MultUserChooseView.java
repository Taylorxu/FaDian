package com.wisesignsoft.OperationManagement.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wisesignsoft.OperationManagement.R;
import com.wisesignsoft.OperationManagement.adapter.MultUserChooseViewAdapter;
import com.wisesignsoft.OperationManagement.bean.UserInfoBean;
import com.wisesignsoft.OperationManagement.bean.WorkOrder;
import com.wisesignsoft.OperationManagement.net.RequestTask;
import com.wisesignsoft.OperationManagement.net.request.RequestYxyw;
import com.wisesignsoft.OperationManagement.net.response.QueryAllValidUsersResponse;
import com.wisesignsoft.OperationManagement.net.response.QueryValidUsersByIdsResponse;
import com.wisesignsoft.OperationManagement.ui.activity.MultUserChooseActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 人员多选控件
 * Created by ycs on 2016/12/12.15601301227
 */
public class MultUserChooseView extends LinearLayout {

    private RecyclerView rv;
    private List<QueryValidUsersByIdsResponse.ReturnValueBean> datas = new ArrayList<>();
    private WorkOrder wo;
    private MultUserChooseViewAdapter adapter;
    private TextView tv_mult_user_title;

    public MultUserChooseView(Context context) {
        super(context);
        init(context);
    }

    private void init(final Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.mult_user_choose, this, true);
        rv = (RecyclerView) view.findViewById(R.id.rv_mult_user_choose);
        tv_mult_user_title = (TextView) view.findViewById(R.id.tv_mult_user_title);

        RecyclerView.LayoutManager manager = new GridLayoutManager(context, 4);
        rv.setLayoutManager(manager);
        adapter = new MultUserChooseViewAdapter(context, datas);
        rv.setAdapter(adapter);
        adapter.setOnIMultUserChooseListener(new MultUserChooseViewAdapter.IMultUserChoose() {
            @Override
            public void setOnIMultUserChoose(QueryValidUsersByIdsResponse.ReturnValueBean bean) {
                String userId = bean.getUserId();
                datas.remove(bean);
                removeUser(userId);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void setOnAddUser() {
                if (wo.isModified()) {
                    MultUserChooseActivity.startSelf(context, wo.getValue(), wo.getID());
                }
            }
        });
    }

    private void removeUser(String useid) {
        String[] temps = wo.getValue().split(",");
        List<String> list = Arrays.asList(temps);
        ArrayList<String> arrayList = new ArrayList<>(list);
        arrayList.remove(useid);
        String value = "";
        for (int i = 0; i < arrayList.size(); i++) {
            value = value + "," + arrayList.get(i);
        }
        if (value.length() > 0) {
            value = value.substring(1);
        }
        wo.setValue(value);
    }

    public void setData(WorkOrder wo) {
        this.wo = wo;
        String title = wo.getName();
        String ids = wo.getValue();
        if (!TextUtils.isEmpty(title)) {
            if (wo.isRequired()) {
                tv_mult_user_title.setText(title + " *");
            } else {
                tv_mult_user_title.setText(title);
            }
        }
        if (!TextUtils.isEmpty(ids)) {
            List<String> list = new ArrayList<>();
            list.add(ids);
            RequestYxyw.queryValidUsersByIds(list, new RequestTask.ResultCallback<QueryValidUsersByIdsResponse>() {
                @Override
                public void onError(Exception e) {

                }

                @Override
                public void onResponse(QueryValidUsersByIdsResponse response) {
                    if (datas != null) {
                        datas.clear();
                    }
                    datas.addAll(response.getReturnValue());
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }
}
