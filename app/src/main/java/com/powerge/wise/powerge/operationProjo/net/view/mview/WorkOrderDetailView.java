package com.powerge.wise.powerge.operationProjo.net.view.mview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.operationProjo.net.BaseActivity;
import com.powerge.wise.powerge.operationProjo.net.bean.Section;
import com.powerge.wise.powerge.operationProjo.net.bean.WorkOrder;
import com.powerge.wise.powerge.operationProjo.net.net.RequestTask;
import com.powerge.wise.powerge.operationProjo.net.net.request.RequestDict;
import com.powerge.wise.powerge.operationProjo.net.net.response.QueryAllValidDictDateResponse;
import com.powerge.wise.powerge.operationProjo.net.view.AttachmentView;
import com.powerge.wise.powerge.operationProjo.net.view.BMFormDataLinkage;
import com.powerge.wise.powerge.operationProjo.net.view.BottomView;
import com.powerge.wise.powerge.operationProjo.net.view.CheckBoxView;
import com.powerge.wise.powerge.operationProjo.net.view.ConfigureChangeView;
import com.powerge.wise.powerge.operationProjo.net.view.DataDisplayDateView;
import com.powerge.wise.powerge.operationProjo.net.view.DataDisplayUserView;
import com.powerge.wise.powerge.operationProjo.net.view.DeptChooseView;
import com.powerge.wise.powerge.operationProjo.net.view.MultRoleChooseView;
import com.powerge.wise.powerge.operationProjo.net.view.MultSelectConfigureChangeView;
import com.powerge.wise.powerge.operationProjo.net.view.MultSelectListView;
import com.powerge.wise.powerge.operationProjo.net.view.MultSelectResModelView;
import com.powerge.wise.powerge.operationProjo.net.view.MultUserChooseView;
import com.powerge.wise.powerge.operationProjo.net.view.NoteButtonView;
import com.powerge.wise.powerge.operationProjo.net.view.NumberView;
import com.powerge.wise.powerge.operationProjo.net.view.ResModelSelectView;
import com.powerge.wise.powerge.operationProjo.net.view.SingleRoleChooseView;
import com.powerge.wise.powerge.operationProjo.net.view.SingleSelectView;
import com.powerge.wise.powerge.operationProjo.net.view.SingleTextView;
import com.powerge.wise.powerge.operationProjo.net.view.SingleUserChooseView;
import com.powerge.wise.powerge.operationProjo.net.view.TextFieldView;
import com.powerge.wise.powerge.operationProjo.net.view.TimeView;
import com.powerge.wise.powerge.operationProjo.net.view.TreeSelectionView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Created by ycs on 2016/12/6.
 */

public class WorkOrderDetailView extends LinearLayout {

    private LinearLayout ll_work_order_detail;
    private List<QueryAllValidDictDateResponse.ReturnValueBean> returnValue;
    /*存储所有的view*/
    private Map<String, View> views = new HashMap<>();
    /*只是为了台账选择组件,存储id和attrName的对应关系*/
    private Map<String, String> views2 = new HashMap<>();
    private List<Map<String, Object>> links;

    public WorkOrderDetailView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.work_order_detail, this, true);
        ll_work_order_detail = view.findViewById(R.id.ll_work_order_detail);
        request();
    }

    private void request() {

    }

    public void setData(final List datas, final Context context) {
        if (datas == null || datas.size() == 0) {
            return;
        }
        final LoadingView loadingView = LoadingView.getLoadingView(context);
        loadingView.show();
        List<String> list1 = new ArrayList<>();
        RequestDict.queryAllValidDictDate(list1, new RequestTask.ResultCallback<QueryAllValidDictDateResponse>() {
            @Override
            public void onError(Exception e) {
                loadingView.stop(loadingView);
            }

            @Override
            public void onResponse(QueryAllValidDictDateResponse response) {
                returnValue = response.getReturnValue();
                for (int i = 0; i < datas.size(); i++) {
                    Object o = datas.get(i);
                    if (o instanceof Section) {
                        SectionView sectionView = new SectionView(context);
                        views.put(((Section) o).getID(), sectionView);
                        sectionView.setData(((Section) o).getLabel(), ((Section) o).isCurrent());
                        ll_work_order_detail.addView(sectionView);
                        List<WorkOrder> data1 = ((Section) o).getSection();
                        if (data1 != null && data1.size() > 0) {
                            for (WorkOrder wo : data1) {
                                if (!wo.isVisible() && !wo.getViewName().equals("Position")) {
                                    continue;
                                }
                                if (links != null) {
                                    for (Map<String, Object> map : links) {
                                        String id = (String) map.get("ID");
                                        String methodName = (String) map.get("methodName");
                                        String expreDesc = (String) map.get("expreDesc");
                                        String view_id = wo.getID();
                                        if (view_id.equals(id)) {
                                            wo.setLink(true);
                                            wo.setMethodName(methodName);
                                            wo.setExpreDesc(expreDesc);
                                        }
                                    }
                                }
                                String viewName = wo.getViewName();
                                Log.i("YCS", "initData: name：" + viewName);
                                Log.i("YCS", "initData: viewName:" + wo.toString());
                                views2.put(wo.getID(), wo.getDmAttrName());
                                switch (viewName) {
                                    case "TextInput":   //输入框
                                        SingleTextView singleTextView = new SingleTextView(context);
                                        views.put(wo.getID(), singleTextView);
                                        sectionView.getLl_section_view().addView(singleTextView);
                                        singleTextView.setData(wo);
                                        break;
                                    case "TextArea":    //文本域
                                        TextFieldView textFieldView = new TextFieldView(context);
                                        views.put(wo.getID(), textFieldView);
                                        textFieldView.setData(wo);
                                        sectionView.getLl_section_view().addView(textFieldView);
                                        break;
                                    case "TreeData":    //树形数据选择组件
                                        TreeSelectionView treeSelectionView = new TreeSelectionView(context);
                                        views.put(wo.getID(), treeSelectionView);
                                        sectionView.getLl_section_view().addView(treeSelectionView);
                                        List<QueryAllValidDictDateResponse.ReturnValueBean.DictDatasBean> datas = transformDatasBean(getDictDatasBean(wo.getSrclib()));
                                        treeSelectionView.setData(wo, datas);
                                        break;
                                    case "ComboBox":    //组合框
                                        BottomView bottomView = new BottomView(context);
                                        views.put(wo.getID(), bottomView);
                                        sectionView.getLl_section_view().addView(bottomView);
                                        List<QueryAllValidDictDateResponse.ReturnValueBean.DictDatasBean> list = transformDatasBean(getDictDatasBean(wo.getSrclib()));
                                        bottomView.setData(wo, list);
                                        break;
                                    case "DataDisplayUser":    //人员显示组件
                                        DataDisplayUserView dataDisplayUserView = new DataDisplayUserView(context);
                                        views.put(wo.getID(), dataDisplayUserView);
                                        sectionView.getLl_section_view().addView(dataDisplayUserView);
                                        dataDisplayUserView.setData(wo);
                                        break;
                                    case "DataDisplayDate":     //时间显示组件
                                        DataDisplayDateView dataDisplayDateView = new DataDisplayDateView(context);
                                        views.put(wo.getID(), dataDisplayDateView);
                                        sectionView.getLl_section_view().addView(dataDisplayDateView);
                                        dataDisplayDateView.setData(wo);
                                        break;
                                    case "DateFeild":   //时间组件
                                        TimeView timeView = new TimeView(context);
                                        views.put(wo.getID(), timeView);
                                        sectionView.getLl_section_view().addView(timeView);
                                        timeView.setData(wo);
                                        break;
                                    case "ResModelSelect":  //模型数据选择组件,碰到这种主键表示接下来是一个项目选择模型的台账数据，單選
                                        ResModelSelectView resModelSelectView = new ResModelSelectView(context);
                                        views.put(wo.getID(), resModelSelectView);
                                        sectionView.getLl_section_view().addView(resModelSelectView);
                                        resModelSelectView.setDate(wo);
                                        break;

                                    case "Attachment":     //附件组件
                                        AttachmentView attachmentView = new AttachmentView(context, (BaseActivity) context);
                                        views.put(wo.getID(), attachmentView);
                                        sectionView.getLl_section_view().addView(attachmentView);
                                        attachmentView.setData(wo);
                                        break;

                                    case "RadioButtons":    //单选钮
                                        SingleSelectView singleSelectView = new SingleSelectView(context);
                                        views.put(wo.getID(), singleSelectView);
                                        sectionView.getLl_section_view().addView(singleSelectView);
                                        List<QueryAllValidDictDateResponse.ReturnValueBean.DictDatasBean> list1 = getDictDatasBean(wo.getSrclib());
                                        if (list1 != null && list1.size() > 0) {
                                            singleSelectView.setData(wo, list1);
                                        }
                                        break;
                                    case "CheckBox"://复选框
                                    case "MultList"://多选框
                                        CheckBoxView checkBoxView = new CheckBoxView(context);
                                        views.put(wo.getID(), checkBoxView);
                                        sectionView.getLl_section_view().addView(checkBoxView);
                                        List<QueryAllValidDictDateResponse.ReturnValueBean.DictDatasBean> list2 = getDictDatasBean(wo.getSrclib());
                                        if (list2 != null && list2.size() > 0) {
                                            checkBoxView.setData(wo, list2);
                                        }
                                        break;
                                    case "NumericStepper":  //数字组件
                                        NumberView numberView = new NumberView(context);
                                        views.put(wo.getID(), numberView);
                                        sectionView.getLl_section_view().addView(numberView);
                                        numberView.setDate(wo);
                                        break;
                                    case "SingleUserChoose":
                                    case "PersonInfo":      //人员组件，單選一樣//人员信息组件，單選人員
                                        SingleUserChooseView singleUserChooseView = new SingleUserChooseView(context);
                                        views.put(wo.getID(), singleUserChooseView);
                                        sectionView.getLl_section_view().addView(singleUserChooseView);
                                        singleUserChooseView.setDate(wo);
                                        break;
                                    case "MultUserChoose":      //人员列表组件，多選人員
                                        MultUserChooseView multUserChooseView = new MultUserChooseView(context);
                                        views.put(wo.getID(), multUserChooseView);
                                        sectionView.getLl_section_view().addView(multUserChooseView);
                                        multUserChooseView.setData(wo);
                                        break;
                                    case "DeptChoose":      //部门组件，單選
                                        DeptChooseView deptChooseView = new DeptChooseView(context);
                                        views.put(wo.getID(), deptChooseView);
                                        sectionView.getLl_section_view().addView(deptChooseView);
                                        deptChooseView.setData(wo);
                                        break;
                                    case "RoleSelect":      //角色组件，多選
                                        break;
                                    case "SingleRoleChoose"://根据数据抓到的角色组件,單選
                                        if (wo.isHasMult()) {
                                            MultRoleChooseView multRoleChooseView = new MultRoleChooseView(context,wo);
                                            views.put(wo.getID(), multRoleChooseView);
                                            sectionView.getLl_section_view().addView(multRoleChooseView);
                                            multRoleChooseView.setData(wo);
                                        } else {
                                            SingleRoleChooseView singleRoleChooseView = new SingleRoleChooseView(context);
                                            views.put(wo.getID(), singleRoleChooseView);
                                            sectionView.getLl_section_view().addView(singleRoleChooseView);
                                            singleRoleChooseView.setData(wo);
                                        }
                                        break;
                                    case "DynamicDataGrid":     //列表组件
                                        MultSelectListView multSelectListView = new MultSelectListView(context);
                                        views.put(wo.getID(), multSelectListView);
                                        sectionView.getLl_section_view().addView(multSelectListView);
                                        multSelectListView.setData(wo);
                                        break;

                                    case "ResDynamicDataGrid":       //模型数据列表组件，台賬
                                        MultSelectResModelView multSelectResModelView = new MultSelectResModelView(context);
                                        views.put(wo.getID(), multSelectResModelView);
                                        sectionView.getLl_section_view().addView(multSelectResModelView);
                                        multSelectResModelView.setData(wo);
                                        break;
                                    case "ConfigureChage":      //配置选择组件,單選
                                        ConfigureChangeView configureChangeView = new ConfigureChangeView(context);
                                        views.put(wo.getID(), configureChangeView);
                                        sectionView.getLl_section_view().addView(configureChangeView);
                                        configureChangeView.setData(wo);
                                        break;
                                    case "ResModelComponents"://配置项列表组件，多選配置項
                                        MultSelectConfigureChangeView multSelectConfigureChangeView = new MultSelectConfigureChangeView(context);
                                        views.put(wo.getID(), multSelectConfigureChangeView);
                                        sectionView.getLl_section_view().addView(multSelectConfigureChangeView);
                                        multSelectConfigureChangeView.setData(wo);
                                        break;
                                    case "nextNode":
                                        NoteButtonView noteButtonView = new NoteButtonView(context);
                                        views.put(wo.getID(), noteButtonView);
                                        sectionView.getLl_section_view().addView(noteButtonView);
                                        break;
                                    case "Modeldatachange":     //模型数据变更组件,不用
                                        break;
                                    case "Delcomponents":       //配置项列表组件，不用
                                        break;
                                    case "Interlinkage":    //超链接，不用
                                        break;
                                    case "WORelating":      //关联工单组件,先類型，再工單,暫不做
                                        break;
                                    case "BorderGroup ":    //组框组件--这个是显示字体颜色大小的
                                        break;
                                }
                            }
                        }
                    } else if (o instanceof BMFormDataLinkage) {
                        String str = ((BMFormDataLinkage) o).getDataLinkageJson();
                        Gson gson = new Gson();
                        links = gson.fromJson(str, new TypeToken<List<Map<String, Object>>>() {
                        }.getType());
                    }
                }
                /*保存初始化所有的数据和控件*/
                WorkOrderDataManager.getManager().setDate(datas, views, views2);
                loadingView.stop(loadingView);
            }
        });
    }

    public void refresh() {
        Log.i("YCS", "refresh: QWER");
        Set<String> keySet = views.keySet();
        List datas = WorkOrderDataManager.getManager().getDate();
        for (int i = 0; i < datas.size(); i++) {//section
            Object o = datas.get(i);
            if (o instanceof Section) {
                List<WorkOrder> list = ((Section) o).getSection();
                for (String key : keySet) {
                    for (WorkOrder wo : list) {
                        if (wo.getID().equals(key)) {
                            Object oo = views.get(key);
                            refreshValue(oo, wo);
                            Log.i("YCS", "refresh: wo.getName:" + wo.getName());
                            continue;
                        }
                    }
                }
            }
        }
    }

    private void refreshValue(Object o, WorkOrder wo) {
        if (o instanceof SingleTextView) {
            ((SingleTextView) o).setData(wo);
        } else if (o instanceof TextFieldView) {
            ((TextFieldView) o).setData(wo);
        } else if (o instanceof TreeSelectionView) {
            List<QueryAllValidDictDateResponse.ReturnValueBean.DictDatasBean> datas = transformDatasBean(getDictDatasBean(wo.getSrclib()));
            ((TreeSelectionView) o).setData(wo, datas);
        } else if (o instanceof BottomView) {
            List<QueryAllValidDictDateResponse.ReturnValueBean.DictDatasBean> list = transformDatasBean(getDictDatasBean(wo.getSrclib()));
            ((BottomView) o).setData(wo, list);
        } else if (o instanceof DataDisplayUserView) {
            ((DataDisplayUserView) o).setData(wo);
        } else if (o instanceof DataDisplayDateView) {
            ((DataDisplayDateView) o).setData(wo);
        } else if (o instanceof TimeView) {
            ((TimeView) o).setData(wo);
        } else if (o instanceof ResModelSelectView) {
            ((ResModelSelectView) o).setDate(wo);
        } else if (o instanceof AttachmentView) {
            ((AttachmentView) o).setData(wo);
        } else if (o instanceof SingleSelectView) {
            List<QueryAllValidDictDateResponse.ReturnValueBean.DictDatasBean> list1 = getDictDatasBean(wo.getSrclib());
            ((SingleSelectView) o).setData(wo, list1);
        } else if (o instanceof CheckBoxView) {
            List<QueryAllValidDictDateResponse.ReturnValueBean.DictDatasBean> list2 = getDictDatasBean(wo.getSrclib());
            ((CheckBoxView) o).setData(wo, list2);
        } else if (o instanceof NumberView) {
            ((NumberView) o).setDate(wo);
        } else if (o instanceof SingleUserChooseView) {
            ((SingleUserChooseView) o).setDate(wo);
        } else if (o instanceof MultUserChooseView) {
            ((MultUserChooseView) o).setData(wo);
        } else if (o instanceof DeptChooseView) {
            ((DeptChooseView) o).setData(wo);
        } else if (o instanceof SingleRoleChooseView) {
            ((SingleRoleChooseView) o).setData(wo);
        } else if (o instanceof MultRoleChooseView) {
            ((MultRoleChooseView) o).setData(wo);
        } else if (o instanceof MultSelectListView) {
            ((MultSelectListView) o).setData(wo);
        } else if (o instanceof MultSelectResModelView) {
            ((MultSelectResModelView) o).setData(wo);
        } else if (o instanceof MultSelectConfigureChangeView) {
            ((MultSelectConfigureChangeView) o).setData(wo);
        } else if (o instanceof ConfigureChangeView) {
            ((ConfigureChangeView) o).setData(wo);
        }
    }


    /**
     * 组合框去找数据
     *
     * @param key
     * @return
     */
    private List<QueryAllValidDictDateResponse.ReturnValueBean.DictDatasBean> getDictDatasBean(String key) {
        if (returnValue != null && returnValue.size() > 0) {
            for (QueryAllValidDictDateResponse.ReturnValueBean bean : returnValue) {
                if (key.equals(bean.getKey())) {
                    return bean.getDictDatas();
                }
            }
        }
        return null;
    }

    /**
     * 转换成组合框需要的model
     *
     * @param datas
     * @return
     */
    private List<QueryAllValidDictDateResponse.ReturnValueBean.DictDatasBean> transformDatasBean(List<QueryAllValidDictDateResponse.ReturnValueBean.DictDatasBean> datas) {
        for (QueryAllValidDictDateResponse.ReturnValueBean.DictDatasBean bean : datas) {
            bean.setName(bean.getDictName());
        }
        return datas;
    }
}
