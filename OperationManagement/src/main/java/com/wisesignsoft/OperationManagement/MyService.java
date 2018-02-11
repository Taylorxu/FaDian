package com.wisesignsoft.OperationManagement;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.wisesignsoft.OperationManagement.db.MySharedpreferences;
import com.wisesignsoft.OperationManagement.manager.PositionManager;
import com.wisesignsoft.OperationManagement.net.RequestTask;
import com.wisesignsoft.OperationManagement.net.request.RequestUser;
import com.wisesignsoft.OperationManagement.net.response.UpdateUserPos;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ycs on 2016/11/26.
 */

public class MyService extends Service {
    private MyLocationListenner myListener = new MyLocationListenner();
    private String temp;
    private Timer timer;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            // 定位初始化
            if (MySharedpreferences.getUser() != null) {
                List<String> list = new ArrayList<>();
                list.add(MySharedpreferences.getUser().getUsername());
                list.add(temp);
//                Toast.makeText(MyService.this, "地图定位："+temp, Toast.LENGTH_SHORT).show();
                RequestUser.updateUserPos(list, new RequestTask.ResultCallback<UpdateUserPos>() {
                    @Override
                    public void onError(Exception e) {
                    }

                    @Override
                    public void onResponse(UpdateUserPos response) {
//                        Toast.makeText(MyService.this, "上送成功", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return false;
        }
    });
    private TimerTask timerTask;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LocationClient mLocClient = new LocationClient(MyApplication.getContext());
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        option.setIsNeedAddress(true);
        mLocClient.setLocOption(option);
        mLocClient.start();
        int time = intent.getIntExtra("time", 1000);
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(0);
            }
        };
        if(time>0){// 当time为零的时候，会有IllegalArgumentException: Non-positive period 添加此判断
            timer.schedule(timerTask, 0, time);
        }else{
            timer.schedule(timerTask,0);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        timerTask.cancel();
        timer.cancel();
        timerTask = null;
        timer = null;
        super.onDestroy();
    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null) {
                return;
            }
            double lat = location.getLatitude();
            double loc = location.getLongitude();
            String address = location.getAddress().address;
            temp = lat + "|" + loc + "|" + address;
            PositionManager.getInstance().setPosition(temp);
        }

        public void d(BDLocation poiLocation) {
            String address = poiLocation.getAddress().address;
            Log.i("YCS", "onReceivePoi: address:" + address);
        }
    }
}
