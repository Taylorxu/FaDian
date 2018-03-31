package com.powerge.wise.powerge.operationProjo.net.runtimepermissions;

import android.content.Context;

/**
 * 日期 2017/5/9.
 * 功能描述：6.0权限管理类
 */

public class PermissionUtil {

    private static PermissionUtil mInstance;
    private PermissionManager permissionManager;

    public PermissionUtil(Context context) {
        permissionManager = new PermissionManager(context.getApplicationContext());
    }

    public static PermissionUtil getInstance(Context context) {
        if (null == mInstance) {
            synchronized (PermissionUtil.class) {
                if (null == mInstance) {
                    mInstance = new PermissionUtil(context);
                }
            }
        }
        return mInstance;
    }

    public PermissionManager getPermissionManager() {
        return permissionManager;
    }

    /**
     * 开始请求
     *
     * @param options
     * @param acpListener
     */
    public void request(PermissionOption options, PermissionListener acpListener) {
        if (options == null) new NullPointerException("AcpOptions is null...");
        if (acpListener == null) new NullPointerException("AcpListener is null...");
        permissionManager.request(options, acpListener);
    }


}
