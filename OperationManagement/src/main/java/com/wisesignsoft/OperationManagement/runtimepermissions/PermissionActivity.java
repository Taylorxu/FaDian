package com.wisesignsoft.OperationManagement.runtimepermissions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

/**
 * 日期 2017/5/9.
 * 功能描述：
 */

public class PermissionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //不接受触摸屏事件
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        if (savedInstanceState == null)
            PermissionUtil.getInstance(this).getPermissionManager().checkRequestPermissionRationale(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        PermissionUtil.getInstance(this).getPermissionManager().checkRequestPermissionRationale(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtil.getInstance(this).getPermissionManager().onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        PermissionUtil.getInstance(this).getPermissionManager().onActivityResult(requestCode, resultCode, data);
    }
}
