package com.wisesignsoft.OperationManagement.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wisesignsoft.OperationManagement.BaseActivity;
import com.wisesignsoft.OperationManagement.R;
import com.wisesignsoft.OperationManagement.adapter.AllDeptTreeAdapter;
import com.wisesignsoft.OperationManagement.net.response.FindAllDeptTreeResponse;
import com.wisesignsoft.OperationManagement.view.TempTreeSelectionDataManager;
import com.wisesignsoft.OperationManagement.view.mview.EmptyView;
import com.wisesignsoft.OperationManagement.view.mview.MyTitle;
import com.wisesignsoft.OperationManagement.view.mview.WorkOrderDataManager;

import java.io.Serializable;
import java.util.List;

public class AllDeptTreeActivity extends BaseActivity implements AllDeptTreeAdapter.IAllDeptTreeListener {

    private MyTitle mt_all_dept_tree;
    private RecyclerView rv;
    private EmptyView empty;
    private List<FindAllDeptTreeResponse.Children> datas;
    private String id;

    public static void startSelf(Context context,List<FindAllDeptTreeResponse.Children> datas,String id){
        Intent intent = new Intent();
        intent.setClass(context,AllDeptTreeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("datas", (Serializable) datas);
        intent.putExtras(bundle);
        intent.putExtra("id",id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_dept_tree);
        TempTreeSelectionDataManager.getManager().addAllDeptTreeActivity(this);
        init();
        request();
    }
    private void init(){
        /*获取控件id*/
        id = getIntent().getStringExtra("id");
        mt_all_dept_tree = (MyTitle) findViewById(R.id.mt_all_dept_tree);
        rv = (RecyclerView) findViewById(R.id.rv_all_dept_tree);
        empty = (EmptyView) findViewById(R.id.ev_all_dept_tree);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        rv.setLayoutManager(manager);

    }
    private void request(){
        datas = (List<FindAllDeptTreeResponse.Children>) getIntent().getSerializableExtra("datas");
        setEmpty();
        initData();
    }
    private void initData(){
        AllDeptTreeAdapter allDeptTreeAdapter = new AllDeptTreeAdapter(this,datas,id);
        rv.setAdapter(allDeptTreeAdapter);
        allDeptTreeAdapter.setOnIClick(this);
    }
    private void setEmpty(){
        if(datas == null || datas.size() == 0){
            empty.setVisibility(View.VISIBLE);
            rv.setVisibility(View.GONE);
        }else {
            empty.setVisibility(View.GONE);
            rv.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TempTreeSelectionDataManager.getManager().removeAllDeptTreeActivity(this);
    }

    @Override
    public void setOnIAllDeptTreeClick(String name) {
        WorkOrderDataManager.getManager().setSingleDateById(id,name);
    }
}
