package com.powerge.wise.powerge.operationProjo.net;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.powerge.wise.powerge.MyApplication;

public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {
        int error_code = Integer.parseInt(intent.getStringExtra("error_code"));
        String msg = intent.getStringExtra("msg");
        if (!"0".equals(error_code)) {
            Toast.makeText(MyApplication.getContext(), msg, Toast.LENGTH_SHORT).show();
        }
    }
}