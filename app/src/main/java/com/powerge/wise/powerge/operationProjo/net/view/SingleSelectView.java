package com.powerge.wise.powerge.operationProjo.net.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.powerge.wise.powerge.operationProjo.net.adapter.NewWorkOrderAdapter;
import com.powerge.wise.powerge.operationProjo.net.adapter.SingleSelectViewAdapter;
import com.powerge.wise.powerge.operationProjo.net.bean.WorkOrder;
import com.powerge.wise.powerge.operationProjo.net.net.response.FindCanCreateProcessResponse;
import com.powerge.wise.powerge.operationProjo.net.net.response.QueryAllValidDictDateResponse;
import com.powerge.wise.powerge.operationProjo.net.view.mview.WorkOrderDataManager;
import com.wisesignsoft.OperationManagement.R;

import java.util.ArrayList;
import java.util.List;


/**
 * 单选组件
 * Created by ycs on 2016/12/2.
 */
public class SingleSelectView extends LinearLayout {

    private TextView tv_single_select;
    private RecyclerView rv_single_select;
    private NewWorkOrderAdapter adapterSelf;
    private WorkOrder wo;
    private SingleSelectViewAdapter adapter;
    private boolean is = false;
    private List<QueryAllValidDictDateResponse.ReturnValueBean.DictDatasBean> datas = new ArrayList<>();

    public SingleSelectView(Context context) {
        super(context);
        init(context);
    }

    public SingleSelectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_select_view, this, true);
        tv_single_select = (TextView) view.findViewById(R.id.tv_single_select_view);
        rv_single_select = (RecyclerView) view.findViewById(R.id.rv_single_select_view);

        RecyclerView.LayoutManager manager = new GridLayoutManager(context, 3);
        rv_single_select.setLayoutManager(manager);
        adapter = new SingleSelectViewAdapter(getContext(), datas);
        rv_single_select.setAdapter(adapter);
    }

    public void setData(final WorkOrder wo, final List<QueryAllValidDictDateResponse.ReturnValueBean.DictDatasBean> list) {
        this.wo = wo;
        String title = wo.getName();
        String value = wo.getValue();
        if (!TextUtils.isEmpty(title)) {
            if (wo.isRequired()) {
                tv_single_select.setText(title + " *");
            } else {
                tv_single_select.setText(title);
            }
        }
        adapter.setClick(wo.isModified());
        if (list != null) {
            datas.clear();
            datas.addAll(list);
            adapter.notifyDataSetChanged();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getDictId().equals(value)) {
                    is = true;
                    adapter.setSelect(i);
                    adapter.notifyDataSetChanged();
                }
            }
            if(!is){
                if(!TextUtils.isEmpty(value)){
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getDictName().equals(value)) {
                            is = false;
                            adapter.setSelect(i);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }
            adapter.setOnISingleSelectClickListener(new SingleSelectViewAdapter.ISingleSelectViewClickListener() {
                @Override
                public void setOnISingleSelectViewClick(int position) {
                    WorkOrderDataManager.getManager().setSingleDateById(wo.getID(), list.get(position).getDictId());
                }
            });
        }
    }

    public void setDataSelf(String title, List<FindCanCreateProcessResponse.ResultBean> datas) {
        if (!TextUtils.isEmpty(title)) {
            tv_single_select.setText(title);
        }
        if (datas != null) {
            adapterSelf = new NewWorkOrderAdapter(getContext(), datas);
            rv_single_select.setAdapter(adapterSelf);
        }
    }

    public int getSelfCurrentPosition() {
        return adapterSelf.getCurrentPosition();
    }
}
