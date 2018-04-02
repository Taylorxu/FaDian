package com.powerge.wise.powerge.helper;

import android.content.Context;

import com.powerge.wise.powerge.bean.JiZuBean;
import com.powerge.wise.powerge.operationProjo.net.utils.ToastUtil;
import com.powerge.wise.powerge.otherPages.DianLiangManagementActivity;
import com.powerge.wise.powerge.otherPages.FuHeManagementActivity;
import com.powerge.wise.powerge.otherPages.JingJiZhiBiaoActivity;
import com.powerge.wise.powerge.otherPages.JingSai.JingSaiActivity;
import com.powerge.wise.powerge.otherPages.MorningMeetingActivity;
import com.powerge.wise.powerge.otherPages.PlanTasksMag.PlanTasksMagActivity;
import com.powerge.wise.powerge.otherPages.SheBeiInfoActivity;
import com.powerge.wise.powerge.otherPages.TongJiFormActivity;
import com.powerge.wise.powerge.otherPages.ZHiZhangLogActivity;
import com.powerge.wise.powerge.otherPages.huaBao.HuanBaoActivity;
import com.powerge.wise.powerge.otherPages.issues.IssuesManagerActivity;
import com.powerge.wise.powerge.otherPages.queXian.QueXianMagActivity;
import com.powerge.wise.powerge.otherPages.xunJian.XunJianMagActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2018/3/1.
 */

public class StartActivity {
    static List<JiZuBean> list = new ArrayList<>();

    public static void go(int where, Context context, List<JiZuBean> jiZuList) {
        if (where == 0) {
            FuHeManagementActivity.start(context, jiZuList);
        } else if (where == 1) {
            DianLiangManagementActivity.start(context, jiZuList);
        } else if (where == 2) {
            JingJiZhiBiaoActivity.start(context);
        } else if (where == 3) {
            TongJiFormActivity.start(context);
        } else if (where == 4) {
            SheBeiInfoActivity.start(context);
        } else if (where == 5) {
            ZHiZhangLogActivity.start(context);
        } else if (where == 6) {
            MorningMeetingActivity.start(context);
        } else if (where == 7) {
            JingSaiActivity.start(context, jiZuList);
        } else if (where == 8) {
            if ("9999".equals(jiZuList.get(0).getId())) {
                if(list.size()>0)list.clear();
                list .addAll(jiZuList);
                list.remove(0);
            }
            HuanBaoActivity.start(context, list);
        } else if (where == 9) {
            QueXianMagActivity.start(context);
        } else if (where == 10) {
//            QueXianPieChartActivity.start(context);
        } else if (where == 11) {
            XunJianMagActivity.start(context);
        } else if (where == 12) {
            IssuesManagerActivity.start(context);
        } else if (where == 13) {
            PlanTasksMagActivity.start(context);
        } else {
            ToastUtil.toast(context, "暂无");
        }
    }
}
