package com.powerge.wise.powerge.operationProjo.net.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.operationProjo.net.BaseActivity;
import com.powerge.wise.powerge.operationProjo.net.db.MySharedpreferences;
import com.powerge.wise.powerge.operationProjo.net.net.RequestTask;
import com.powerge.wise.powerge.operationProjo.net.net.request.RequestProcess;
import com.powerge.wise.powerge.operationProjo.net.net.response.FindCanCreateProcessResponse;
import com.powerge.wise.powerge.operationProjo.net.view.SingleSelectView;
import com.powerge.wise.powerge.operationProjo.net.view.mview.MyTitle;

import java.util.ArrayList;
import java.util.List;


public class NewTemplateActivity extends BaseActivity implements View.OnClickListener {
    private MyTitle mt_new_template;
    private SingleSelectView ssv_new_template;
    private Button bt_next;
    private List<FindCanCreateProcessResponse.ResultBean> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_template);
        init();
        request();
    }

    private void init() {
        mt_new_template = (MyTitle) findViewById(R.id.mt_new_template);
        ssv_new_template = (SingleSelectView) findViewById(R.id.ssv_new_template);
        bt_next = (Button) findViewById(R.id.bt_next);

        mt_new_template.setBack(true, this);
        mt_new_template.setTitle("新建模板");
        bt_next.setOnClickListener(this);
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
        ssv_new_template.setDataSelf("类型", datas);
    }


    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.bt_next) {
            int position = ssv_new_template.getSelfCurrentPosition();
            if (position >= 0) {
                String key = datas.get(position).getKey();
                String name = datas.get(position).getName();
                NewTemplateActivity2.startSelf(NewTemplateActivity.this, name, key);
            } else {
                Toast.makeText(NewTemplateActivity.this, "请选择一个类型", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
