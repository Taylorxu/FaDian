package com.powerge.wise.powerge.operationProjo.net.ui.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.config.soap.ApiService;
import com.powerge.wise.powerge.operationProjo.net.BaseActivity;
import com.powerge.wise.powerge.operationProjo.net.Protocol;
import com.powerge.wise.powerge.operationProjo.net.db.MySharedpreferences;
import com.powerge.wise.powerge.operationProjo.net.utils.ToastUtil;
import com.powerge.wise.powerge.operationProjo.net.view.mview.MyTitle;
import com.powerge.wise.powerge.otherPages.LoginActivity;


public class ServerAddressActivity extends BaseActivity implements View.OnClickListener {

    private MyTitle mt_server;
    private EditText et_server;
    private Button bt_server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_address);
        initView();
    }

    private void initView() {
        mt_server = (MyTitle) findViewById(R.id.mt_server);
        et_server = (EditText) findViewById(R.id.et_server_url);
        bt_server = (Button) findViewById(R.id.bt_server_save);

        mt_server.setBack(true, this);
        mt_server.setTitle(getResources().getString(R.string.server_title));

        String hostUrl = Protocol.getHostUrl();
        if (!TextUtils.isEmpty(hostUrl)) {
            et_server.setText(hostUrl);
        }
        bt_server.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String server_url = et_server.getText().toString().trim();
        if (TextUtils.isEmpty(server_url)) {
            ToastUtil.toast(this, getResources().getString(R.string.toast_server));
            return;
        }else if(!server_url.endsWith("/")){
            ToastUtil.toast(this,getResources().getString(R.string.toast_server_end));
            return;
        }
        MySharedpreferences.putServerString(server_url, new Callback() {
            @Override
            public void onStartLogin() {
                ToastUtil.toast(ServerAddressActivity.this, getResources().getString(R.string.toast_server_save));
                ApiService.Creator.setNull();
                LoginActivity.start(ServerAddressActivity.this);
                finish();
            }

            @Override
            public void onFail() {
                ToastUtil.toast(ServerAddressActivity.this, getResources().getString(R.string.toast_server_save));
            }
        });

    }

    public interface Callback {
        void onStartLogin();
        void onFail();
    }

}
