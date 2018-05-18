package com.powerge.wise.powerge.otherPages.xunJian;

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
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.powerge.wise.basestone.heart.network.FlatMapResponse;
import com.powerge.wise.basestone.heart.network.FlatMapTopRes;
import com.powerge.wise.basestone.heart.network.ResultModel;
import com.powerge.wise.basestone.heart.ui.XAdapter;
import com.powerge.wise.basestone.heart.ui.XViewHolder;
import com.powerge.wise.basestone.heart.util.LogUtils;
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
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;
import com.yanzhenjie.permission.SettingService;

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
    private String date, termType;
    private Handler mHandler = new Handler();
    private boolean mScanning;
    private String DateChecked;
    private String UUID = "b5b182c7-eab1-4988-aa99-b5c1517008d9";

    public static void start(Context context) {
        Intent starter = new Intent(context, XunJianMagActivity.class);
        context.startActivity(starter);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_xun_jian_mag);
        binding.title.setText("巡检管理");
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(termType!=null)createData();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void initView() {
        binding.dateContent.setAdapter(new XunJianDatePagerAdapter(getSupportFragmentManager()));
        binding.xunJianTabL.setupWithViewPager(binding.dateContent);
        initBlueTooth();  //初始化蓝牙，成立后扫描
        requestPermissions(); // 获取地址权限
        initContentSign();
    }

    private String uuidChecked = null;
    XAdapter<XunJianSignBean, ItemXunJianSingListBinding> adapter = new XAdapter.SimpleAdapter<XunJianSignBean, ItemXunJianSingListBinding>(0, R.layout.item_xun_jian_sing_list) {
        @Override
        protected void initHolder(final XViewHolder<XunJianSignBean, ItemXunJianSingListBinding> holder, int viewType) {
            holder.getBinding().btnSign.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    XjFillFormActivity.starter(getBaseContext(), true, holder.getBinding().getXunJianSign(), termType);
                }
            });
        }

        @Override
        public void onBindViewHolder(XViewHolder<XunJianSignBean, ItemXunJianSingListBinding> holder, int position) {
            super.onBindViewHolder(holder, position);
            XunJianSignBean signBean = getItemData(position);
            signBean.setEnable(true);//初始化 签到按钮不可点
            holder.getBinding().setXunJianSign(signBean);
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

    private void createData() {
        XunJianSignBean bean = new XunJianSignBean();
        bean.setNameSpace(BaseUrl.NAMESPACE_P);
        bean.setUserName(User.getCurrentUser().getName());
        bean.setArg1(termType);
        bean.setArg2(date);
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

                    @SuppressLint("NewApi")
                    @Override
                    public void onNext(List<XunJianSignBean> xunJianSignBeans) {
                        adapter.setList(xunJianSignBeans);
                       /* if (mBluetoothAdapter != null) mBluetoothAdapter.stopLeScan(callback);

                        if (!mBluetoothAdapter.isEnabled()) {//没打开蓝牙，则请求打开
                            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                        } else {
                            scanLeDevice();
                        }*/
                    }
                });


    }


    @Override
    public void onDateCehckedListener(Map<String, String> params) {
        DateChecked = params.get("checkId");
        termType = params.get("termType");
        date = params.get("date");
        createData();

    }


    @SuppressLint("NewApi")
    private void initBlueTooth() {
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        //是否支持 低耗功能 BLE
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "该手机不支持低耗蓝牙", Toast.LENGTH_LONG);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {//必须是Android4.3 以上 (SDK_INT)API==18
                mBluetoothAdapter = bluetoothManager.getAdapter();
            } else {
                Toast.makeText(this, "该手机不支持蓝牙", Toast.LENGTH_LONG);
            }
        }

    }


    @SuppressLint("NewApi")
    private void scanLeDevice() {
        if (adapter.getList() == null) return;
        Log.e(TAG, adapter.getList().toString());
        mBluetoothAdapter.startLeScan(callback);

    }


    final BluetoothAdapter.LeScanCallback callback = new BluetoothAdapter.LeScanCallback() {
        @SuppressLint("NewApi")
        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
            final iBeaconClass.iBeacon ibeacon = iBeaconClass.fromScanData(device, rssi, scanRecord);
            if (ibeacon != null) {
                if (UUID.equals(ibeacon.proximityUuid)) {
                    LogUtils.e(iBeaconClass.bytesToHexString(scanRecord) + "   ----------------------");
                }
                if ((Integer.parseInt(termType) == 2 && Integer.parseInt(DateChecked) == 1) || (Integer.parseInt(termType) != 2 && Integer.parseInt(DateChecked) == 3)) {
                    LogUtils.e(ibeacon.proximityUuid + "   ----------------------");
                    if (UUID.equals(ibeacon.proximityUuid)) {
                        for (XunJianSignBean bean : adapter.getList()) {
                            int major_data = Integer.parseInt(bean.getBlueToothNo());
                            String uuid_data = bean.getBlueToothUUID().toLowerCase();
                            if (major_data == ibeacon.major) {//如果搜到信标，签到按钮是不可编辑状态
                                bean.setEnable(true);
                                break;
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
//                LogUtils.e(iBeaconClass.bytesToHexString(scanRecord) + "   ----------------------");
            }


        }

    };


    private void requestPermissions() {
        AndPermission.with(this)
                .permission(android.Manifest.permission.ACCESS_COARSE_LOCATION
                )
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

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
        }
    }

}
