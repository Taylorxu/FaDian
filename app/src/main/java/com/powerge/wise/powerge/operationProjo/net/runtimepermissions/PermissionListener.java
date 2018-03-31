package com.powerge.wise.powerge.operationProjo.net.runtimepermissions;

import java.util.List;

/**
 * 日期 2017/5/9.
 * 功能描述：
 */

public interface PermissionListener {
    /**
     * 同意
     */
    void onGranted();

    /**
     * 拒绝
     */
    void onDenied(List<String> permissions);
}
