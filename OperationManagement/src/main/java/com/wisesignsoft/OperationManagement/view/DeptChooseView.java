package com.wisesignsoft.OperationManagement.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.wisesignsoft.OperationManagement.R;
import com.wisesignsoft.OperationManagement.bean.WorkOrder;
import com.wisesignsoft.OperationManagement.net.RequestTask;
import com.wisesignsoft.OperationManagement.net.request.RequestDept;
import com.wisesignsoft.OperationManagement.net.response.FindAllDeptTreeResponse;
import com.wisesignsoft.OperationManagement.net.response.FindDeptByIdsResponse;
import com.wisesignsoft.OperationManagement.ui.activity.AllDeptTreeActivity;
import com.wisesignsoft.OperationManagement.utils.ToastUtil;
import com.wisesignsoft.OperationManagement.view.mview.BaseView;

import java.util.ArrayList;
import java.util.List;

/**
 * 部门单选控件
 * Created by ycs on 2016/12/15.
 */
public class DeptChooseView extends FrameLayout {
    private BaseView bv_dept_choose;
    private WorkOrder wo;

    public DeptChooseView(Context context) {
        super(context);
        init(context);
    }

    private void init(final Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.dept_choose, this, true);
        bv_dept_choose = (BaseView) view.findViewById(R.id.bv_dept_choose);
        bv_dept_choose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> list = new ArrayList<String>();
                RequestDept.findAllDeptTree(list, new RequestTask.ResultCallback<FindAllDeptTreeResponse>() {
                    @Override
                    public void onError(Exception e) {

                    }

                    @Override
                    public void onResponse(FindAllDeptTreeResponse response) {
                        AllDeptTreeActivity.startSelf(context, response.getResult(),wo.getID());
                    }
                });
            }
        });
    }

    public void setData(WorkOrder wo) {
        this.wo = wo;
        String title = wo.getName();
        String content = wo.getValue();
        if (!TextUtils.isEmpty(title)) {
            if (wo.isRequired()) {
                bv_dept_choose.setTv_left(title + " *");
            } else {
                bv_dept_choose.setTv_left(title);
            }
        }
        if(!TextUtils.isEmpty(content)){
            findDeptById(content);
        }else {
            bv_dept_choose.setTv_right("");
        }
        if(!wo.isModified()){
            bv_dept_choose.clearFocus();
            bv_dept_choose.setFocusable(false);
            bv_dept_choose.setClickable(false);
        }
    }
    private void findDeptById(final String id){
        List<String> list = new ArrayList<>();
        list.add(id);
        RequestDept.findDeptByIds(list, new RequestTask.ResultCallback<FindDeptByIdsResponse>() {
            @Override
            public void onError(Exception e) {
                bv_dept_choose.setTv_right(id);
            }

            @Override
            public void onResponse(FindDeptByIdsResponse response) {
                String name = response.getResult().get(0).getDeptFullName();
                bv_dept_choose.setTv_right(name);
            }
        });
    }
}
