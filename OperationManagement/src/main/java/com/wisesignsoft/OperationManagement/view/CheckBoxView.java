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
import com.wisesignsoft.OperationManagement.adapter.CheckBoxViewAdapter;
import com.wisesignsoft.OperationManagement.adapter.NewWorkOrderAdapter;
import com.wisesignsoft.OperationManagement.bean.WorkOrder;
import com.wisesignsoft.OperationManagement.net.response.FindCanCreateProcessResponse;
import com.wisesignsoft.OperationManagement.net.response.QueryAllValidDictDateResponse;
import com.wisesignsoft.OperationManagement.view.mview.WorkOrderDataManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 复选框
 * Created by ycs on 2016/12/1.
 */
public class CheckBoxView extends LinearLayout implements CheckBoxViewAdapter.ICheckBoxViewListener {

    private TextView tv_check_box;
    private RecyclerView rv_check_box;
    private CheckBoxViewAdapter adapter;
    private List<QueryAllValidDictDateResponse.ReturnValueBean.DictDatasBean> datas = new ArrayList<>();
    private WorkOrder wo;

    public CheckBoxView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.check_box_view, this, true);
        tv_check_box = (TextView) view.findViewById(R.id.tv_check_box_view);
        rv_check_box = (RecyclerView) view.findViewById(R.id.rv_check_box_view);

        RecyclerView.LayoutManager manager = new GridLayoutManager(context, 3);
        rv_check_box.setLayoutManager(manager);
        adapter = new CheckBoxViewAdapter(getContext(), datas);
        rv_check_box.setAdapter(adapter);
        adapter.setOnICheckBoxListener(this);
    }

    public void setData(WorkOrder wo, List<QueryAllValidDictDateResponse.ReturnValueBean.DictDatasBean> lists) {
        this.wo = wo;
        String str = wo.getName();
        String value = wo.getValue();
        if (!TextUtils.isEmpty(str)) {
            if (wo.isRequired()) {
                tv_check_box.setText(str + " *");
            } else {
                tv_check_box.setText(str);
            }
        }
        adapter.setClick(wo.isModified());
        datas.clear();
        datas.addAll(lists);
        adapter.notifyDataSetChanged();
        List<String> list = new ArrayList<>();
        if (!TextUtils.isEmpty(value)) {
            String[] ids = value.split(",");
            for(QueryAllValidDictDateResponse.ReturnValueBean.DictDatasBean bean:lists){
                for(String id:ids){
                    if(bean.getDictId().equals(id)){
                        list.add(id);
                    }
                }

            }
            if(list.size() == 0){
                for(QueryAllValidDictDateResponse.ReturnValueBean.DictDatasBean bean:lists){
                    for(String id:ids){
                        if(bean.getDictName().equals(id)){
                            list.add(bean.getDictId());
                        }
                    }

                }
            }
        }
        adapter.setIds(list);
        WorkOrderDataManager.getManager().setSingleDateById(wo.getID(),toStringByIds(list));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setOnItemClickListenenr() {
        List<String> ids = adapter.getIds();
        WorkOrderDataManager.getManager().setSingleDateById(wo.getID(), toStringByIds(ids));
    }

    private String toStringByIds(List<String> ids) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < ids.size(); i++) {
            String id = ids.get(i);
            if (i == ids.size() - 1) {
                sb.append(id);
            } else {
                sb.append(id).append(",");
            }
        }
        Log.i("YCS", "toStringByIds: str:"+sb.toString());
        return sb.toString();
    }
}
