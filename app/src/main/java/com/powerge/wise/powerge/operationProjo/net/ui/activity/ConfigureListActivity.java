package com.powerge.wise.powerge.operationProjo.net.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.operationProjo.net.BaseActivity;
import com.powerge.wise.powerge.operationProjo.net.bean.ResModelConfigure;
import com.powerge.wise.powerge.operationProjo.net.view.SingleTextView;
import com.powerge.wise.powerge.operationProjo.net.view.TextFieldView;
import com.powerge.wise.powerge.operationProjo.net.view.mview.MyTitle;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


public class ConfigureListActivity extends BaseActivity {

    private MyTitle mt_list;
    private LinearLayout ll_list;
    private Button bt_list;
    private Map<String, Object> map;
    private ResModelConfigure data;

    public static void startSelf(Context context, Map<String, Object> map, ResModelConfigure data) {
        Intent intent = new Intent(context, ConfigureListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("map", (Serializable) map);
        bundle.putSerializable("data", data);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_list);
        init();
    }

    private void init() {
        map = (Map<String, Object>) getIntent().getSerializableExtra("map");
        data = (ResModelConfigure) getIntent().getSerializableExtra("data");

        mt_list = (MyTitle) findViewById(R.id.mt_configure_list);
        ll_list = (LinearLayout) findViewById(R.id.ll_configure_list);
        bt_list = (Button) findViewById(R.id.bt_get_data);

        mt_list.setBack(true, this);
        mt_list.setTitle("配置项列表");
        mt_list.setTvRight(true, "确定", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        bt_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConfigureListActivity.this, SelectAccountActivity2.class);
                intent.putExtra("att", data.getClassName());
                ConfigureListActivity.this.startActivityForResult(intent, 2);
            }
        });
        setData(map);
    }

    private void setData(Map map) {
        List<ResModelConfigure.AttrDatasOfFormBean> beanList = data.getAttrDatasOfForm();
        for (ResModelConfigure.AttrDatasOfFormBean bean : beanList) {
            switch (bean.getType()) {
                case "TextInput":
                    SingleTextView singleTextView = new SingleTextView(this);
                    ll_list.addView(singleTextView);
                    singleTextView.setData(bean.getName(), (String) map.get(bean.getDmAttrName()));
                    break;
                case "TextArea":
                    TextFieldView textFieldView = new TextFieldView(this);
                    ll_list.addView(textFieldView);
                    textFieldView.setData(bean.getName(), (String) map.get(bean.getDmAttrName()));
                    break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2 && resultCode == 1&&data != null) {
            Map<String,String> map = (Map<String, String>) data.getSerializableExtra("data");
            setData(map);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
