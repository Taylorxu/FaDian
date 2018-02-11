package com.wisesignsoft.OperationManagement;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.umeng.message.UmengMessageService;
import com.wisesignsoft.OperationManagement.bean.Message;

import org.android.agoo.common.AgooConstants;

/**
 * Created by ycs on 2017/2/14.
 */

public class MyPushIntentService extends UmengMessageService {
    @Override
    public void onMessage(Context context, Intent intent) {
        try {
            String message = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
            Log.i("YCS", "onMessage: " + message);
            Gson gson = new Gson();
            Message entry = gson.fromJson(message,Message.class);
            showNotification(context,entry);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示提示
     *
     * @param entry
     */
    private void showNotification(Context context, Message entry) {
//        Bitmap btm = BitmapFactory.decodeResource(context.getResources(), R.mipmap.logo);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.logo)//设置小图标
                .setContentTitle(context.getString(R.string.app_name))//设置标题
                .setContentText(entry.getBody().getText())//设置内容
                .setDefaults(Notification.DEFAULT_VIBRATE)//设置声音，灯光，振动
                .setAutoCancel(true)//自己维护通知的消失
                .setTicker(entry.getBody().getTicker());//第一次提示消息的时候显示在通知栏上;
        //获取通知管理器对象
        mNotificationManager.notify(123456, mBuilder.build());
    }
}
