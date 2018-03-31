package com.powerge.wise.powerge.operationProjo.net.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.operationProjo.net.BaseActivity;
import com.powerge.wise.powerge.operationProjo.net.adapter.NewWorkOrderAdapter;
import com.powerge.wise.powerge.operationProjo.net.db.MySharedpreferences;
import com.powerge.wise.powerge.operationProjo.net.net.RequestTask;
import com.powerge.wise.powerge.operationProjo.net.net.request.RequestProcess;
import com.powerge.wise.powerge.operationProjo.net.net.response.FindCanCreateProcessResponse;
import com.powerge.wise.powerge.operationProjo.net.view.mview.MyTitle;

import java.util.ArrayList;
import java.util.List;


public class NewWorkOrderActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout rl_my_template;
    private Button bt_next;
    private List<FindCanCreateProcessResponse.ResultBean> datas;
    private RecyclerView rv_new_order;
    private NewWorkOrderAdapter adapter;
    private MyTitle mt_new_work_order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_work_order);
        init();
        request();
    }

    private void init() {
        mt_new_work_order = (MyTitle) findViewById(R.id.mt_order_title);
        rl_my_template = (RelativeLayout) findViewById(R.id.rl_my_template);
        bt_next = (Button) findViewById(R.id.bt_next);
        bt_next.setOnClickListener(this);
        rv_new_order = (RecyclerView) findViewById(R.id.rv_new_word_order);
        RecyclerView.LayoutManager manager = new GridLayoutManager(this, 3);
        rv_new_order.setLayoutManager(manager);

        mt_new_work_order.setBack(true,this);
        mt_new_work_order.setTitle("新建工单");
        rl_my_template.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.rl_my_template) {
            MyTemplateActivity.startSelf(NewWorkOrderActivity.this, 9);

        } else if (i == R.id.bt_next) {
            int currentPosition = adapter.getCurrentPosition();
            if (currentPosition == -1) {
                Toast.makeText(NewWorkOrderActivity.this, "请选中一个类型", Toast.LENGTH_SHORT).show();
            } else {
                NewWorkOrderActivity2.startSelf(NewWorkOrderActivity.this, datas.get(currentPosition).getKey(), datas.get(currentPosition).getName());
            }

        }
    }

    private void request() {
        List<String> list = new ArrayList<>();
        list.add(MySharedpreferences.getUser().getName());
        RequestProcess.findCanCreateProcess(list, new RequestTask.ResultCallback<FindCanCreateProcessResponse>() {
            @Override
            public void onError(Exception e) {

            }

            @Override
            public void onResponse(FindCanCreateProcessResponse response) {
                datas = response.getResult();
                initData(datas);
            }
        });
    }

    private void initData(List<FindCanCreateProcessResponse.ResultBean> datas) {
        adapter = new NewWorkOrderAdapter(this, datas);
        rv_new_order.setAdapter(adapter);
    }
}
