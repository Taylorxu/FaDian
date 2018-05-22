package com.powerge.wise.powerge.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;
import com.yanzhenjie.permission.SettingService;

import java.util.List;

public class RequestPermissionsHelper {
    private Activity mActivity;
    private Context mContext;
    private String mPermission;
    private static RequestPermissionsHelper thisObject;

    public RequestPermissionsHelper(Activity mActivity, Context mContext,String permission) {
        this.mActivity = mActivity;
        this.mContext = mContext;
        this.mPermission = permission;
    }

    public static RequestPermissionsHelper instant(Activity mActivity, Context mContext,String permission) {
        if (thisObject == null) {
            thisObject = new RequestPermissionsHelper(mActivity, mContext,permission);
        }
        return thisObject;
    }

    /**
     * android.Manifest.permission.ACCESS_COARSE_LOCATION
     */
    private void requestPermissions() {
        AndPermission.with(mActivity).permission(mPermission)
                .rationale(rationaleListener)
                .onDenied(action)
                .start();
    }


    private Rationale rationaleListener = new Rationale() {
        @Override
        public void showRationale(Context context, List<String> permissions, final RequestExecutor executor) {

            new AlertDialog.Builder(context).setTitle("权限申请")
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


    Action action = new Action() {
        @Override
        public void onAction(List<String> permissions) {
            if (AndPermission.hasAlwaysDeniedPermission(mContext, permissions)) {   // 这些权限被用户总是拒绝。
                final SettingService settingService = AndPermission.permissionSetting(mContext);
                new AlertDialog.Builder(mContext).setTitle("权限申请")
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
                            }
                        }).show();

            }
        }
    };
}
