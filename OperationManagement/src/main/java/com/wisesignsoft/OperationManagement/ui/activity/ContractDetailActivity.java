package com.wisesignsoft.OperationManagement.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wisesignsoft.OperationManagement.BaseActivity;
import com.wisesignsoft.OperationManagement.R;
import com.wisesignsoft.OperationManagement.bean.ContractBean;
import com.wisesignsoft.OperationManagement.net.response.QueryDataResponse;
import com.wisesignsoft.OperationManagement.net.response.QueryModelByBmModelNameResponse;
import com.wisesignsoft.OperationManagement.view.mview.KeyValueView;
import com.wisesignsoft.OperationManagement.view.mview.KeyValueView2;
import com.wisesignsoft.OperationManagement.view.mview.MyTitle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ContractDetailActivity extends BaseActivity {

    private MyTitle mt_contract_detail;
    private TextView tv_contract_detail_title;
    private TextView tv_contract_detail_company_name;
    private TextView tv_contract_detail_create_time;
    private LinearLayout ll_contract_detail;
    private LinearLayout ll_contract_user;
    private LinearLayout ll_contract_detail2;
    private LinearLayout ll_contract_unuser;
    private List<ContractBean> lists1 = new ArrayList<>();
    private List<ContractBean> lists2 = new ArrayList<>();
    private Map<String, String> list;
    private List<QueryModelByBmModelNameResponse.ReturnValueBean.AttrDefineListBean> attrDefineList;

    public static void startSelf(Context context, List<ContractBean> lists,List<ContractBean> list2){
        Intent intent = new Intent(context,ContractDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("lists1", (Serializable) lists);
        bundle.putSerializable("lists2", (Serializable) list2);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
    public static void startSelf(Context context, Map<String, String> list, List<QueryModelByBmModelNameResponse.ReturnValueBean.AttrDefineListBean> attrDefineList){
        Intent intent = new Intent(context,ContractDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("lists1", (Serializable) list);
        bundle.putSerializable("lists2", (Serializable) attrDefineList);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract_detail);
        init();
        getValue("");
        setData();
    }
    private void init(){
        Intent intent = getIntent();
        list = (Map<String, String>) intent.getSerializableExtra("lists1");
        attrDefineList = (List<QueryModelByBmModelNameResponse.ReturnValueBean.AttrDefineListBean>) intent.getSerializableExtra("lists2");
        mt_contract_detail = (MyTitle) findViewById(R.id.mt_contract_detail);
        tv_contract_detail_title = (TextView) findViewById(R.id.tv_contract_detail_title);
        tv_contract_detail_company_name = (TextView) findViewById(R.id.tv_contract_detail_company_name);
        tv_contract_detail_create_time = (TextView) findViewById(R.id.tv_contract_detail_create_time);
        ll_contract_detail = (LinearLayout) findViewById(R.id.ll_contract_detail);
        ll_contract_user = (LinearLayout) findViewById(R.id.ll_contract_user);
        ll_contract_detail2 = (LinearLayout) findViewById(R.id.ll_contract_detail2);
        ll_contract_unuser = (LinearLayout) findViewById(R.id.ll_contract_unuser);

        mt_contract_detail.setBack(true,this);
        mt_contract_detail.setTitle("合同详情");
    }
    public void setData(){
        String p_name = "";
        String p_no = "";
        String c_name="";
        String c_time="";
        for (QueryModelByBmModelNameResponse.ReturnValueBean.AttrDefineListBean bean : attrDefineList) {
            String key = bean.getDmAttrName();
            String value = list.get(key);
            boolean is = getValue(key);
            if(is){
                if(key.startsWith("CUST")&&!key.equals("CUST_SUB")){
                    ContractBean b = new ContractBean();
                    b.setKey(bean.getName());
                    b.setValue(value);
                    lists1.add(b);
                }else if(key.startsWith("PROJ")){
                    ContractBean b = new ContractBean();
                    b.setKey(bean.getName());
                    b.setValue(value);
                    lists2.add(b);
                }
            }
        }
        Set<String> set = list.keySet();
        Iterator<String> i = set.iterator();
        while (i.hasNext()){
            String next = i.next();
            String value = list.get(next);
            if("PROJ_NAME".equals(next)){
                p_name = value;
                continue;
            }else if("PROJ_NO".equals(next)){
                p_no = value;
                continue;
            }else if("CUST_NAME".equals(next)){
                c_name=value;
                continue;
            }else if("CREATEDATE".equals(next)){
                c_time = value;
                continue;
            }
        }
        Log.i("YCS", "setData: size1:"+lists1.size());
        Log.i("YCS", "setData: size2:"+lists2.size());
        for(ContractBean b1:lists1){
            KeyValueView2 keyValueView2 = new KeyValueView2(this);
            keyValueView2.setData(b1);
            ll_contract_user.addView(keyValueView2);
        }
        for(ContractBean b2:lists2){
            KeyValueView2 keyValueView2 = new KeyValueView2(this);
            keyValueView2.setData(b2);
            ll_contract_unuser.addView(keyValueView2);
        }
        tv_contract_detail_title.setText(p_name+"("+p_no+")");
        tv_contract_detail_company_name.setText(c_name);
        tv_contract_detail_create_time.setText(c_time);
    }
    private boolean getValue(String key){
        Set<String> set = list.keySet();
        Iterator<String> i = set.iterator();
        while (i.hasNext()){
//            Log.i("YCS", "getValue: next:"+i.next());
            if(key.equals(i.next())){
                return true;
            }
        }
        return false;
    }
}
