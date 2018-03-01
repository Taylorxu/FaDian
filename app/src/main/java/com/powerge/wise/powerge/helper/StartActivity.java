package com.powerge.wise.powerge.helper;

import android.content.Context;

import com.powerge.wise.powerge.otherPages.DianLiangManagementActivity;
import com.powerge.wise.powerge.otherPages.FuHeManagementActivity;
import com.powerge.wise.powerge.otherPages.JingJiZhiBiaoActivity;
import com.powerge.wise.powerge.otherPages.MorningMeetingActivity;
import com.powerge.wise.powerge.otherPages.SheBeiInfoActivity;
import com.powerge.wise.powerge.otherPages.ZHiZhangLogActivity;
import com.wisesignsoft.OperationManagement.utils.ToastUtil;

import static com.wisesignsoft.OperationManagement.MyApplication.getContext;

/**
 * Created by Administrator on 2018/3/1.
 */

public class StartActivity {
    public static void go(int where, Context context) {
        if (where == 0) {
            FuHeManagementActivity.start(context);
        } else if (where == 1) {
            DianLiangManagementActivity.start(context);
        } else if (where == 2) {
            JingJiZhiBiaoActivity.start(context);
        } else if (where == 4) {
            SheBeiInfoActivity.start(context);
        } else if (where == 5) {
            ZHiZhangLogActivity.start(context);
        } else if (where == 6) {
            MorningMeetingActivity.start(context);
        } else {
            ToastUtil.toast(context, "暂无");
        }
    }
}
