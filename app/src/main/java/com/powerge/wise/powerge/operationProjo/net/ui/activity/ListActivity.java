package com.powerge.wise.powerge.operationProjo.net.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.powerge.wise.powerge.operationProjo.net.BaseActivity;
import com.powerge.wise.powerge.operationProjo.net.bean.ColumnsJsonBean;
import com.powerge.wise.powerge.operationProjo.net.utils.ToastUtil;
import com.powerge.wise.powerge.operationProjo.net.view.NumberView;
import com.powerge.wise.powerge.operationProjo.net.view.SingleTextView;
import com.powerge.wise.powerge.operationProjo.net.view.TextFieldView;
import com.powerge.wise.powerge.operationProjo.net.view.TimeView;
import com.powerge.wise.powerge.operationProjo.net.view.mview.MyTitle;
import com.powerge.wise.powerge.operationProjo.net.view.mview.WorkOrderDataManager;
import com.wisesignsoft.OperationManagement.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ListActivity extends BaseActivity {

    private MyTitle mt_list;
    private LinearLayout ll_list;
    private List<ColumnsJsonBean> datas;
    private Map<String, String> map;
    private String id;

    public static void startSelf(Context context, Map<String, String> map, List<ColumnsJsonBean> datas, String id) {
        Intent intent = new Intent(context, ListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("map", (Serializable) map);
        bundle.putSerializable("datas", (Serializable) datas);
        intent.putExtras(bundle);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        init();
    }

    private void init() {
        map = (Map<String, String>) getIntent().getSerializableExtra("map");
        datas = (List<ColumnsJsonBean>) getIntent().getSerializableExtra("datas");
        id = getIntent().getStringExtra("id");
        mt_list = (MyTitle) findViewById(R.id.mt_list);
        ll_list = (LinearLayout) findViewById(R.id.ll_list_activity);

        mt_list.setBack(true, this);
        mt_list.setTitle("列表");
        mt_list.setTvRight(true, "确定", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getValue()) {
                    finish();
                }
            }
        });
        setData1(this);
    }

    private void setData1(Context context) {
        for (ColumnsJsonBean b : datas) {
            ColumnsJsonBean.DataTypeObjBean model = b.getDataTypeObj();
            switch (model.getValue()) {
                case "text":
                    SingleTextView singleTextView = new SingleTextView(context);
                    ll_list.addView(singleTextView);

                    int value = b.getRequiredObj().getValue();
                    if (value != 0) {
                        singleTextView.setRequireData(b.getHeaderText(), map.get(b.getDataFieldObj().getName()));
                    } else {
                        singleTextView.setData(b.getHeaderText(), map.get(b.getDataFieldObj().getName()));
                    }
                    singleTextView.setKey(b.getDataFieldObj().getName());
                    break;
                case "textArea":
                    TextFieldView textFieldView = new TextFieldView(context);
                    ll_list.addView(textFieldView);
                    textFieldView.setData(b.getHeaderText(), map.get(b.getDataFieldObj().getName()));
                    break;
                case "number":
                    NumberView numberView = new NumberView(context);
                    ll_list.addView(numberView);
                    numberView.setData(b.getHeaderText(), map.get(b.getDataFieldObj().getName()), b.getRequiredObj().getValue());
                    break;
                case "datetime":
                    TimeView timeView = new TimeView(context);
                    ll_list.addView(timeView);
                    timeView.setData(b.getHeaderText(), map.get(b.getDataFieldObj().getName()));
                    break;
                case "personInfo":
//                    SingleUserChooseView singleUserChooseView = new SingleUserChooseView(context);
//                    ll_list.addView(singleUserChooseView);
//                    singleUserChooseView.setData(b.getHeaderText(), map.get(b.getDataFieldObj().getName()));
                    break;
                case "treeData":
//                    TreeSelectionView treeSelectionView = new TreeSelectionView(context);
//                    ll_list.addView(treeSelectionView);
                    break;
                case "dictionary":
                    break;
            }
        }
    }

    private boolean getValue() {
        int ii = ll_list.getChildCount();
        if (ii > 0) {
            for (int i = 0; i < ii; i++) {
                View view = ll_list.getChildAt(i);
                if (view instanceof SingleTextView) {
                    String key = ((SingleTextView) view).getKey();
                    String value = ((SingleTextView) view).getValue();
                    int isRequire = datas.get(i).getRequiredObj().getValue();
                    if (isRequire > 0 && TextUtils.isEmpty(value)) {
                        ToastUtil.toast(ListActivity.this, "请填写" + datas.get(i).getHeaderText());
                        return false;
                    }
                    map.put(key, value);
                }
                if (view instanceof NumberView) {
                    String v = ((NumberView) view).getValue();
                    int value = datas.get(i).getRequiredObj().getValue();
                    if (value > 0 && TextUtils.isEmpty(((NumberView) view).getValue())) {
                        ToastUtil.toast(ListActivity.this, "请填写" + datas.get(i).getHeaderText());
                        return false;
                    }
                    map.put(datas.get(i).getDataFieldObj().getName(), v);
                }
            }
        }
        String value = WorkOrderDataManager.getManager().getSingleDateById(id);
        Gson gson = new Gson();
        List<Map<String, String>> list = gson.fromJson(value, new TypeToken<List<Map<String, String>>>() {
        }.getType());
        if (list == null) {
            list = new ArrayList<>();
            list.add(map);
            WorkOrderDataManager.getManager().setSingleDateById(id, gson.toJson(list));
        } else {
            try {
                list.remove(map);
                list.add(map);
                WorkOrderDataManager.getManager().setSingleDateById(id, gson.toJson(list));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
