package com.wisesignsoft.OperationManagement.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wisesignsoft.OperationManagement.R;
import com.wisesignsoft.OperationManagement.bean.EventClassificationModel;
import com.wisesignsoft.OperationManagement.bean.WorkOrder;
import com.wisesignsoft.OperationManagement.net.RequestTask;
import com.wisesignsoft.OperationManagement.net.request.RequestDict;
import com.wisesignsoft.OperationManagement.net.response.QueryAllValidDictDateResponse;
import com.wisesignsoft.OperationManagement.net.response.QueryValidCiByModelNameResponse;
import com.wisesignsoft.OperationManagement.ui.activity.EventClassificationActivity;
import com.wisesignsoft.OperationManagement.view.mview.ISetData;
import com.wisesignsoft.OperationManagement.view.mview.LoadingView;

import java.util.ArrayList;
import java.util.List;

/**
 * 树形选择组件
 * Created by ycs on 2016/12/1.
 */

public class TreeSelectionView extends RelativeLayout implements ISetData {

    private TextView tv_tree_left;
    private TextView tv_tree_right;
    private RelativeLayout rl_tree_selection;
    private LoadingView loadingView;
    private WorkOrder wo;
    private List<QueryValidCiByModelNameResponse.ReturnValueBean.DictDatasBean> list1;

    public TreeSelectionView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.tree_selection_view, this, true);
        tv_tree_left = (TextView) view.findViewById(R.id.tv_tree_left);
        tv_tree_right = (TextView) view.findViewById(R.id.tv_tree_right);
        rl_tree_selection = (RelativeLayout) view.findViewById(R.id.rl_tree_selection);
    }

    @Override
    public void setData(final WorkOrder wo, List<QueryAllValidDictDateResponse.ReturnValueBean.DictDatasBean> datas) {
        this.wo = wo;
        String title = wo.getName();
        final String content = wo.getValue();
        final String param = wo.getSrclib();
        if (!TextUtils.isEmpty(title)) {
            if (wo.isRequired()) {
                tv_tree_left.setText(title + " *");
            } else {
                tv_tree_left.setText(title);
            }
        }
//        if(!wo.isModified()){
//            rl_tree_selection.setFocusable(false);
//            rl_tree_selection.setClickable(false);
//            rl_tree_selection.clearFocus();
//        }
        if (list1 != null) {
            if (!TextUtils.isEmpty(content)) {
                for (QueryValidCiByModelNameResponse.ReturnValueBean.DictDatasBean bean : list1) {
                    if (bean.getDictId().equals(content)) {
                        tv_tree_right.setText(bean.getDictName());
                    }
                }
            }
        } else {
            tv_tree_right.setText("");
        }

        if (datas != null) {
            String name = "";
            String[] temp = content.split(",");
            if (temp.length == 2) {
                name = temp[1];
            } else {
                name = content;
            }
            for (QueryAllValidDictDateResponse.ReturnValueBean.DictDatasBean bean : datas) {
                if (bean.getDictName().equals(name)) {
                    tv_tree_right.setText(bean.getDictName());
                }
                if (bean.getDictId().equals(name)) {
                    tv_tree_right.setText(bean.getDictName());
                }
            }
        } else {
            tv_tree_right.setText("");
        }
        if (wo.isModified()) {
            rl_tree_selection.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    List<String> list = new ArrayList<>();
                    list.add(param);
                    loadingView = LoadingView.getLoadingView(getContext());
                    loadingView.show();
                    RequestDict.queryValidCiByModelName(list, new RequestTask.ResultCallback<QueryValidCiByModelNameResponse>() {
                        @Override
                        public void onError(Exception e) {
                            loadingView.stop(loadingView);
                        }

                        @Override
                        public void onResponse(QueryValidCiByModelNameResponse response) {
                            list1 = response.getReturnValue().getDictDatas();
                            List<EventClassificationModel> datas = new ArrayList<>();
                            for (QueryValidCiByModelNameResponse.ReturnValueBean.DictDatasBean bean : list1) {
                                if ("DICT_ROOT".equals(bean.getDictParentValue())) {
                                    EventClassificationModel model = new EventClassificationModel();
                                    model.setDictId(bean.getDictId());
                                    model.setDictName(bean.getDictName());
                                    model.setDictParentValue(bean.getDictParentValue());
                                    model.setDictValue(bean.getDictValue());
                                    datas.add(model);
                                }
                            }
                            for (EventClassificationModel model : datas) {
                                List<EventClassificationModel> datas1 = new ArrayList<>();
                                for (QueryValidCiByModelNameResponse.ReturnValueBean.DictDatasBean bean : list1) {
                                    if (model.getDictValue().equals(bean.getDictParentValue())) {
                                        EventClassificationModel model1 = new EventClassificationModel();
                                        model1.setDictName(bean.getDictName());
                                        model1.setDictId(bean.getDictId());
                                        model1.setDictParentValue(bean.getDictParentValue());
                                        model1.setDictValue(bean.getDictValue());
                                        datas1.add(model1);
                                    }
                                }
                                model.setList(datas1);
                            }
                            for (EventClassificationModel model : datas) {
                                for (EventClassificationModel model1 : model.getList()) {
                                    List<EventClassificationModel> datas2 = new ArrayList<>();
                                    for (QueryValidCiByModelNameResponse.ReturnValueBean.DictDatasBean bean : list1) {
                                        if (model1.getDictValue().equals(bean.getDictParentValue())) {
                                            EventClassificationModel model2 = new EventClassificationModel();
                                            model2.setDictName(bean.getDictName());
                                            model2.setDictId(bean.getDictId());
                                            model2.setDictParentValue(bean.getDictParentValue());
                                            model2.setDictValue(bean.getDictValue());
                                            datas2.add(model2);
                                        }
                                    }
                                    model1.setList(datas2);
                                }
                            }
                            loadingView.stop(loadingView);
                            EventClassificationActivity.startSelf(getContext(), datas, wo.getID());
                        }
                    });
                }
            });
        }
    }
}
