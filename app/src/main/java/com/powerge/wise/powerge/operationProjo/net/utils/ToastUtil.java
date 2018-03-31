package com.powerge.wise.powerge.operationProjo.net.utils;

import android.content.Context;
import android.widget.Toast;

import com.powerge.wise.powerge.operationProjo.net.Appconfig;

/**
 * Created by ycs on 2016/11/17.
 */

public class ToastUtil {
    public static void toast(Context c, String msg) {
        if (Appconfig.isToast)
            Toast.makeText(c, msg, Toast.LENGTH_SHORT).show();
    }
}
