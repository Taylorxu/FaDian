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
import com.wisesignsoft.OperationManagement.adapter.ContractFragmentAdapter;
import com.wisesignsoft.OperationManagement.adapter.SelectUserAdapter;
import com.wisesignsoft.OperationManagement.net.response.FindAllDeptTreeResponse;
import com.wisesignsoft.OperationManagement.utils.DividerItemDecoration;
import com.wisesignsoft.OperationManagement.view.mview.EmptyView;
import com.wisesignsoft.OperationManagement.view.mview.MyTitle;
import com.wisesignsoft.OperationManagement.view.mview.SeachView;

import java.io.Serializable;
import java.util.List;

public class SelectDeptActivity extends BaseActivity implements SeachView.ISearchView {

    private MyTitle mt_select_dept;
    private SeachView sv_select_dept;
    private RecyclerView rv_select_dept;
    private EmptyView ev_select_dept;
    private List<FindAllDeptTreeResponse.Children> datas;

    public static void startSelf(Context context, List<FindAllDeptTreeResponse.Children> datas) {
        Intent intent = new Intent(context, SelectDeptActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("datas", (Serializable) datas);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_dept);
        init();
        request();
    }

    private void init() {
        mt_select_dept = (MyTitle) findViewById(R.id.mt_select_dept);
        sv_select_dept = (SeachView) findViewById(R.id.sv_select_dept);
        rv_select_dept = (RecyclerView) findViewById(R.id.rv_select_dept);
        ev_select_dept = (EmptyView) findViewById(R.id.ev_select_dept);

        mt_select_dept.setBack(true, this);
        mt_select_dept.setTitle("选择部门");

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        rv_select_dept.setLayoutManager(manager);
        rv_select_dept.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
        sv_select_dept.setISearchViewListener(this);
        sv_select_dept.setHint("请输入用户名");
    }

    private void request() {
        datas = (List<FindAllDeptTreeResponse.Children>) getIntent().getSerializableExtra("datas");
        setEmpty();
        initData();
    }

    private void setEmpty() {
        if (datas == null || datas.size() == 0) {
            ev_select_dept.setVisibility(View.VISIBLE);
            rv_select_dept.setVisibility(View.GONE);
        } else {
            ev_select_dept.setVisibility(View.GONE);
            rv_select_dept.setVisibility(View.VISIBLE);
        }
    }

    private void initData() {
        ContractFragmentAdapter adapter = new ContractFragmentAdapter(this, datas);
        rv_select_dept.setAdapter(adapter);
        adapter.setOnContractListener(new ContractFragmentAdapter.IContractFragmentListener() {
            @Override
            public void setOnNameClickListener(String id) {
                SelectUserActivity.startSelf(SelectDeptActivity.this, id,"");
            }

            @Override
            public void setOnEntityClickListener(List<FindAllDeptTreeResponse.Children> datas) {
                SelectDeptActivity.startSelf(SelectDeptActivity.this, datas);
            }
        });
    }

    @Override
    public void setOnSearchListener(String key) {
        SelectUserActivity.startSelf(SelectDeptActivity.this,"",key);
    }

    @Override
    public void setOnCancelListener() {

    }
}
