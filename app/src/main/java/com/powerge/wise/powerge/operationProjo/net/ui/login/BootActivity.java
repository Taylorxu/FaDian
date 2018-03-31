package com.powerge.wise.powerge.operationProjo.net.ui.login;

import android.Manifest;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.powerge.wise.powerge.operationProjo.net.BaseActivity;
import com.powerge.wise.powerge.operationProjo.net.db.MySharedpreferences;
import com.powerge.wise.powerge.operationProjo.net.runtimepermissions.PermissionListener;
import com.powerge.wise.powerge.operationProjo.net.runtimepermissions.PermissionOption;
import com.powerge.wise.powerge.operationProjo.net.runtimepermissions.PermissionUtil;
import com.wisesignsoft.OperationManagement.R;

import java.util.List;

public class BootActivity extends BaseActivity {

    private ImageView iv_boot;
    private String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION
            , Manifest.permission.WRITE_EXTERNAL_STORAGE
            , Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO};
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boot);
        String url = MySharedpreferences.getServerString();
        if (TextUtils.isEmpty(url)) {
            MySharedpreferences.putServerString("http://work.wisesignsoft.com:20184/MWEB/");
        }
        initView();
    }

    private void initView() {
        iv_boot = (ImageView) findViewById(R.id.iv_boot);
        setAnimation(iv_boot);
    }


    private void setAnimation(View view) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 0.1f, 1.0f).setDuration(2000);
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                PermissionUtil.getInstance(BootActivity.this).request(new PermissionOption.Builder()
                                .setPermissions(permission)
                                .build()
                        , new PermissionListener() {
                            @Override
                            public void onGranted() {
                                if (MySharedpreferences.getLoginStatusBoolean()) {
                                    toMain();
                                } else {
                                    toLogin();
                                }
                            }

                            @Override
                            public void onDenied(List<String> permissions) {
                                finish();
                            }
                        });

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        objectAnimator.setRepeatCount(0);
        objectAnimator.start();
    }

    private void toLogin() {
        /*Intent intent = new Intent();
        intent.setClass(this, LoginActivity.class);
        startActivity(intent);*/
        finish();
    }

    private void toMain() {
       /* Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        startActivity(intent);*/
        finish();
    }
}
