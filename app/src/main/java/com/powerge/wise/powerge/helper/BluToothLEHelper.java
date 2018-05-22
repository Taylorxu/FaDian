package com.powerge.wise.powerge.helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;

public final class BluToothLEHelper {
    public static int REQUEST_ENABLE_BT = 0101;
    private BluetoothAdapter mBluetoothAdapter;
    private final Activity mActivity;
    private final Context mContext;
    private BluetoothAdapter.LeScanCallback mScanCallback = null;

    private BluToothLEHelper(Activity activity, Context context, BluetoothAdapter.LeScanCallback scanCallback) {
        this.mActivity = activity;
        this.mContext = context;
        this.mScanCallback = scanCallback;
        initBlueTooth();
    }


    @SuppressLint("NewApi")
    public void initBlueTooth() {
        final BluetoothManager bluetoothManager = (BluetoothManager) mActivity.getSystemService(Context.BLUETOOTH_SERVICE);
        //是否支持 低耗功能 BLE
        if (!mActivity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(mActivity, "该手机不支持低耗蓝牙", Toast.LENGTH_LONG);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {//必须是Android4.3 以上 (SDK_INT)API==18
                mBluetoothAdapter = bluetoothManager.getAdapter();
                if (!mBluetoothAdapter.isEnabled()) {//没打开蓝牙，则请求打开
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    mActivity.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                } else {
                    startLeScan();
                }
            } else {
                Toast.makeText(mActivity, "该手机不支持蓝牙", Toast.LENGTH_LONG);
            }
        }

    }


    @SuppressLint("NewApi")
    public void startLeScan() {
        mBluetoothAdapter.startLeScan(new BluetoothAdapter.LeScanCallback() {
            @Override
            public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
                mScanCallback.onLeScan(device, rssi, scanRecord);
            }
        });
    }

    @SuppressLint("NewApi")
    public void stopLeScan() {
        mBluetoothAdapter.stopLeScan(mScanCallback);
    }


    public static final class Builder {
        private Activity mActivity;
        private Context mContext;
        private BluetoothAdapter.LeScanCallback mScanCallback = null;

        public Builder setParams(Activity activity, Context context, BluetoothAdapter.LeScanCallback scanCallback) {
            mActivity = activity;
            mContext = context;
            mScanCallback = scanCallback;
            return this;
        }

        public BluToothLEHelper build() {
            return new BluToothLEHelper(mActivity, mContext, mScanCallback);
        }
    }

}
