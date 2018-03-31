package com.powerge.wise.powerge.helper;

import android.widget.Toast;

import com.powerge.wise.powerge.MyApplication;

/**
 * Created by Administrator on 2018/3/16.
 */

public class EEMsgToastHelper {
    public EEMsgToastHelper() {

    }

    public static EEMsgToastHelper newInstance() {
        EEMsgToastHelper fragment = new EEMsgToastHelper();
        return fragment;
    }

    public void selectWitch(String exception) {
        if (exception.indexOf("Failed to connect") > -1) {
            toastMesg("服务连接失败");
        } else {
            toastMesg(exception);
        }
    }

    public void toastMesg(String msg) {
        Toast.makeText(MyApplication.getInstance().getBaseContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
