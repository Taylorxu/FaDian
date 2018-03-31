package com.powerge.wise.powerge.operationProjo.net.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.operationProjo.net.BaseActivity;
import com.powerge.wise.powerge.operationProjo.net.adapter.MultResModelAdapter;
import com.powerge.wise.powerge.operationProjo.net.view.mview.EmptyView;
import com.powerge.wise.powerge.operationProjo.net.view.mview.MyTitle;
import com.powerge.wise.powerge.operationProjo.net.view.mview.SeachView;

import java.util.ArrayList;
import java.util.List;

public class MultSelectResModelActivity extends BaseActivity {
    private MyTitle mt_title;
    private SeachView sv_res_model;
    private RecyclerView rv_res_model;
    private EmptyView ev_res_model;
    private List datas = new ArrayList();

    public static void startSelf(Context context) {
        Intent intent = new Intent(context, MultSelectResModelActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mult_select_res_model);
        init();
    }

    private void init() {
        mt_title = (MyTitle) findViewById(R.id.mt_mult_res_model);
        sv_res_model = (SeachView) findViewById(R.id.sv_mult_res_model);
        rv_res_model = (RecyclerView) findViewById(R.id.rv_mult_res_model);
        ev_res_model = (EmptyView) findViewById(R.id.ev_mult_res_model);
        mt_title.setBack(true, this);
        mt_title.setTitle("选择配置项");
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        rv_res_model.setLayoutManager(manager);
        MultResModelAdapter adapter = new MultResModelAdapter(this, datas);
        rv_res_model.setAdapter(adapter);
    }
    private void request(){

    }
    private void initData(){

    }
    private void setEmpty() {
        if (datas == null || datas.size() == 0) {
            ev_res_model.setVisibility(View.VISIBLE);
            rv_res_model.setVisibility(View.GONE);
        } else {
            ev_res_model.setVisibility(View.GONE);
            rv_res_model.setVisibility(View.VISIBLE);
        }
    }
}
