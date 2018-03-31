package com.powerge.wise.powerge.mainPage;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RadioGroup;

import com.powerge.wise.basestone.heart.network.Notification;
import com.powerge.wise.basestone.heart.util.RxBus;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.bean.User;
import com.powerge.wise.powerge.databinding.ActivityMainBinding;
import com.powerge.wise.powerge.otherPages.LoginActivity;
import com.powerge.wise.powerge.zxing.activity.CaptureActivity;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;
import com.yanzhenjie.permission.SettingService;

import java.util.List;

public class MainActivity extends AppCompatActivity implements FirstFragment.OnFirstFragmentInteractionListener {
    private MainAdapter mSectionsPagerAdapter;
    ActivityMainBinding mainBinding;

    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        starter.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(starter);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (User.getCurrentUser() == null || !User.getCurrentUser().isLogin()) {
            LoginActivity.start(this);
            return;
        }
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mSectionsPagerAdapter = new MainAdapter(getSupportFragmentManager());
        mainBinding.container.setAdapter(mSectionsPagerAdapter);
        mainBinding.container.addOnPageChangeListener(onPageChangeListener);
        mainBinding.navigation.setOnCheckedChangeListener(OnNISListener);
        mainBinding.tab1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (mainBinding.tab1.isChecked()) {
                        RxBus.getDefault().post(new Notification(004, 0));
                    }
                } else {
                    mainBinding.container.setCurrentItem(0, false);
                }
                return true;
            }
        });

    }

    RadioGroup.OnCheckedChangeListener OnNISListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.tab1:
                    RxBus.getDefault().post(new Notification(004, 0));
                    break;
                case R.id.tab2:
                    mainBinding.container.setCurrentItem(1, false);
                    break;
                case R.id.tab3:
                    mainBinding.container.setCurrentItem(2, false);
                    break;
                case R.id.tab4:
                    mainBinding.container.setCurrentItem(3, false);
                    break;
            }
        }
    };


    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    mainBinding.tab1.setChecked(true);
                    break;
                case 1:
                    mainBinding.tab2.setChecked(true);
                    break;
                case 2:
                    mainBinding.tab3.setChecked(true);
                    break;
                case 3:
                    mainBinding.tab4.setChecked(true);
                    break;
            }

        }
    };

    /**
     * 第一个fragment的接口
     *
     * @param
     */
    @Override
    public void onFragmentInteraction(int where) {
        if (where == 0) {
            requestPermissions();
        }
    }





    private void requestPermissions() {
        AndPermission.with(this)
                .permission(android.Manifest.permission.CAMERA
                )
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        startActivity(new Intent(MainActivity.this, CaptureActivity.class));
                    }
                })
                .rationale(rationaleListener)
                .onDenied(action)
                .start();
    }


    /**
     * Rationale支持，这里自定义对话框。
     * 用户往往会拒绝一些权限，而程序的继续运行又必须使用这些权限，此时我们应该友善的提示用户。
     */
    private Rationale rationaleListener = new Rationale() {
        @Override
        public void showRationale(Context context, List<String> permissions, final RequestExecutor executor) {

            new AlertDialog.Builder(MainActivity.this).setTitle("权限申请")
                    .setMessage("为了更好地使用 电厂助手 \n 请您打开权限")
                    .setPositiveButton("允许", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            executor.execute();

                        }
                    })
                    .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            executor.cancel();
                        }
                    }).show();


        }
    };

    /**
     * 当用户点击应用程序的某个按钮，而他又总是拒绝我们需要的某个权限时，应用程序可能不会响应（但不是ANR），为了避免这种情况，我们应该在用户总是拒绝某个权限时提示用户去系统设置中授权哪些权限给我们，无论用户是否真的会授权给我们。
     */
    Action action = new Action() {
        @Override
        public void onAction(List<String> permissions) {
            if (AndPermission.hasAlwaysDeniedPermission(getBaseContext(), permissions)) {   // 这些权限被用户总是拒绝。
                final SettingService settingService = AndPermission.permissionSetting(getBaseContext());
                // 这里使用一个Dialog展示没有这些权限应用程序无法继续运行，询问用户是否去设置中授权。
                new AlertDialog.Builder(MainActivity.this).setTitle("权限申请")
                        .setMessage("没有这些权限应用某些程序无法继续运行，是否去设置中授权")
                        .setPositiveButton("允许", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                settingService.execute();
                            }
                        })
                        .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestPermissions();
//                                settingService.cancel();
                            }
                        }).show();

            }
        }
    };


}
