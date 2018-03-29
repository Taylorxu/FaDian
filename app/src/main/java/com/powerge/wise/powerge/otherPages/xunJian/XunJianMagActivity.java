package com.powerge.wise.powerge.otherPages.xunJian;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.powerge.wise.basestone.heart.network.FlatMapResponse;
import com.powerge.wise.basestone.heart.network.FlatMapTopRes;
import com.powerge.wise.basestone.heart.network.ResultModel;
import com.powerge.wise.basestone.heart.ui.XAdapter;
import com.powerge.wise.basestone.heart.ui.XViewHolder;
import com.powerge.wise.powerge.BR;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.bean.User;
import com.powerge.wise.powerge.bean.XunJianSignBean;
import com.powerge.wise.powerge.config.soap.ApiService;
import com.powerge.wise.powerge.config.soap.request.BaseUrl;
import com.powerge.wise.powerge.config.soap.request.RequestBody;
import com.powerge.wise.powerge.config.soap.request.RequestEnvelope;
import com.powerge.wise.powerge.databinding.ActivityXunJianMagBinding;
import com.powerge.wise.powerge.databinding.ItemXunJianSingListBinding;
import com.powerge.wise.powerge.helper.EEMsgToastHelper;
import com.powerge.wise.powerge.helper.LoadingWindowActivity;
import com.powerge.wise.powerge.otherPages.SheBeiInfoActivity;
import com.powerge.wise.powerge.zxing.activity.CaptureActivity;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;
import com.yanzhenjie.permission.SettingService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class XunJianMagActivity extends AppCompatActivity implements XunJianDateFragment.OnDateCehckedListener {
    ActivityXunJianMagBinding binding;
    private BluetoothAdapter mBluetoothAdapter;
    int REQUEST_ENABLE_BT = 0101;
    private String TAG = "BLUE_TOOTH_LOG";
    private String pointNo, termType;
    private Handler mHandler = new Handler();
    private boolean mScanning;

    public static void start(Context context) {
        Intent starter = new Intent(context, XunJianMagActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_xun_jian_mag);
        binding.title.setText("巡检管理");
        initView();
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
        }
    }

    private void initView() {
        binding.dateContent.setAdapter(new XunJianDatePagerAdapter(getSupportFragmentManager()));
        binding.xunJianTabL.setupWithViewPager(binding.dateContent);
        requestPermissions();
        initContentSign();
    }

    private String uuidChecked = null;
    XAdapter<XunJianSignBean, ItemXunJianSingListBinding> adapter = new XAdapter.SimpleAdapter<XunJianSignBean, ItemXunJianSingListBinding>(0, R.layout.item_xun_jian_sing_list) {
        @Override
        protected void initHolder(final XViewHolder<XunJianSignBean, ItemXunJianSingListBinding> holder, int viewType) {
            holder.getBinding().btnSign.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    uuidChecked = holder.getBinding().btnSign.getTag().toString();
                    pointNo = holder.getBinding().pointNo.getTag().toString();
                    initBlueTooth();
                }
            });
        }

        @Override
        public void onBindViewHolder(XViewHolder<XunJianSignBean, ItemXunJianSingListBinding> holder, int position) {
            super.onBindViewHolder(holder, position);
            holder.getBinding().setXunJianSign(getItemData(position));
        }
    };

    /*初始换巡检*/
    private void initContentSign() {
        binding.contentSingList.setLayoutManager(new LinearLayoutManager(this));
        binding.contentSingList.setAdapter(adapter);
        adapter.setItemClickListener(onItemClickListener);
    }

    XAdapter.OnItemClickListener onItemClickListener = new XAdapter.OnItemClickListener<XunJianSignBean, ItemXunJianSingListBinding>() {

        @Override
        public void onItemClick(XViewHolder<XunJianSignBean, ItemXunJianSingListBinding> holder) {
            XunJianDianSignListActivity.start(getBaseContext(), holder.getBinding().getXunJianSign().getInspectedDetails(), holder.getBinding().getXunJianSign().getName());
        }
    };

    private void createData(Map<String, String> params) {
        termType = params.get("termType");
        XunJianSignBean bean = new XunJianSignBean();
        bean.setNameSpace(BaseUrl.NAMESPACE_P);
        bean.setUserName(User.getCurrentUser().getName());
        bean.setArg1(termType);
        bean.setArg2(params.get("date"));
        RequestEnvelope.getRequestEnvelope().setBody(new RequestBody<>(bean));
        ApiService.Creator.get().queryInspectionResultData(RequestEnvelope.getRequestEnvelope())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .flatMap(new FlatMapResponse<ResultModel<List<XunJianSignBean>>>())
                .flatMap(new FlatMapTopRes<List<XunJianSignBean>>())
                .subscribe(new Subscriber<List<XunJianSignBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        EEMsgToastHelper.newInstance().selectWitch(e.getMessage());
                    }

                    @Override
                    public void onNext(List<XunJianSignBean> xunJianSignBeans) {
                        if (xunJianSignBeans.size() > 0) adapter.setList(xunJianSignBeans);
                    }
                });


    }

    /**
     * 签到
     */
    private void signAction() {
        SignSoapRequest bean = new SignSoapRequest();
        bean.setNameSpace(BaseUrl.NAMESPACE_P);
        bean.setUserName(User.getCurrentUser().getName());
        bean.setArg1(pointNo);
        bean.setArg2(uuidChecked);
        bean.setArg3(termType);
        RequestEnvelope.getRequestEnvelope().setBody(new RequestBody<>(bean));
        ApiService.Creator.get().inspectPoint(RequestEnvelope.getRequestEnvelope())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .flatMap(new FlatMapResponse<ResultModel<SignSoapRequest>>())
                .flatMap(new FlatMapTopRes<SignSoapRequest>())
                .subscribe(new Subscriber<SignSoapRequest>() {
                    @SuppressLint("NewApi")
                    @Override
                    public void onCompleted() {
                        showLoading(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if (e != null) {
                            EEMsgToastHelper.newInstance().selectWitch(e.getMessage());
                        }
                        showLoading(false);
                    }

                    @Override
                    public void onNext(SignSoapRequest x) {
                        List<XunJianSignBean> list = adapter.getList();
                        for (XunJianSignBean xun : list) {
                            if (xun.getPointNo().equals(pointNo)) {
                                xun.setInspectedNum(x.getInspectedNum());
                                xun.setInspectedDetails(x.getInspectedDetails());
                                break;
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                });

    }

    @Override
    public void onDateCehckedListener(Map<String, String> params) {
        createData(params);
    }


    private void initBlueTooth() {
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        //是否支持 低耗功能 BLE
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "该手机不支持低耗蓝牙", Toast.LENGTH_LONG);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {//必须是Android4.3 以上 (SDK_INT)API==18
                mBluetoothAdapter = bluetoothManager.getAdapter();
                if (!mBluetoothAdapter.isEnabled()) {//没打开蓝牙，则请求打开
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                } else {
                    scanLeDevice();
                }
            } else {
                Toast.makeText(this, "该手机不支持蓝牙", Toast.LENGTH_LONG);
            }
        }

    }


    private void requestPermissions() {
        AndPermission.with(this)
                .permission(android.Manifest.permission.ACCESS_COARSE_LOCATION
                )
               /* .onGranted(new Action() {
                    @SuppressLint("NewApi")
                    @Override
                    public void onAction(List<String> permissions) {
                        Toast.makeText(getBaseContext(), "授权成功", Toast.LENGTH_SHORT).show();
                    }
                })*/
                .rationale(rationaleListener)
                .onDenied(action)
                .start();
    }


    private Rationale rationaleListener = new Rationale() {
        @Override
        public void showRationale(Context context, List<String> permissions, final RequestExecutor executor) {

            new AlertDialog.Builder(XunJianMagActivity.this).setTitle("权限申请")
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
            if (AndPermission.hasAlwaysDeniedPermission(getBaseContext(), permissions)) {   // 这些权限被用户总是拒绝。
                final SettingService settingService = AndPermission.permissionSetting(getBaseContext());
                new AlertDialog.Builder(XunJianMagActivity.this).setTitle("权限申请")
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

    public void showLoading(boolean should) {
        binding.loadingRLayout.setVisibility(should ? View.VISIBLE : View.GONE);
        binding.loadingCenterText.setText("正在扫描中...");
        binding.contentSingList.setAlpha(should ? 0.5f : 1f);
    }

    @SuppressLint("NewApi")
    private void scanLeDevice() {
        showLoading(true);
        if (!mScanning) {//不在扫描中
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
                    mBluetoothAdapter.stopLeScan(callback);

                }
            }, 10000);

            mScanning = mBluetoothAdapter.startLeScan(callback);

        } else {
            Toast.makeText(getBaseContext(), "正在扫描中", Toast.LENGTH_SHORT).show();

        }
    }

    final BluetoothAdapter.LeScanCallback callback = new BluetoothAdapter.LeScanCallback() {
        @SuppressLint("NewApi")
        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
            final iBeaconClass.iBeacon ibeacon = iBeaconClass.fromScanData(device, rssi, scanRecord);
            if (ibeacon != null && uuidChecked != null) {
                Log.e(TAG, ibeacon.proximityUuid);
                if (ibeacon.proximityUuid.equals(uuidChecked.toLowerCase())) {
                    binding.loadingCenterText.setText("开始签到");
                    signAction();
                }
            }
        }

    };

    @SuppressLint("NewApi")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BT) {
            scanLeDevice();
        }
    }

    @SuppressLint("NewApi")
    @Override
    protected void onStop() {
        super.onStop();
        if (mBluetoothAdapter != null) mBluetoothAdapter.stopLeScan(callback);
    }

    @SuppressLint("NewApi")
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBluetoothAdapter != null) mBluetoothAdapter.stopLeScan(callback);
    }

}
