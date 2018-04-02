package com.powerge.wise.powerge.otherPages;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.bean.User;
import com.powerge.wise.powerge.mainPage.MainActivity;
import com.powerge.wise.powerge.operationProjo.net.Protocol;
import com.powerge.wise.powerge.operationProjo.net.ui.login.ServerAddressActivity;

public class SplashActivity extends AppCompatActivity {

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    MainActivity.start(getBaseContext());
                    finish();
                    break;
                case 1:
                    Intent intent = new Intent(SplashActivity.this, ServerAddressActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case 2:
                    LoginActivity.start(SplashActivity.this);
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkEveryThing();
            }
        },2000
        );


    }

    private void checkEveryThing() {
        //服务器地址
        String hostUrl = Protocol.getHostUrl();
        if (null != User.getCurrentUser() && User.getCurrentUser().isLogin()) {
            handler.sendEmptyMessage(0);
            return;
        } else {
            if (hostUrl.isEmpty()) {
                handler.sendEmptyMessage(1);
                return;
            } else {
                handler.sendEmptyMessage(2);

            }
        }

    }
}
