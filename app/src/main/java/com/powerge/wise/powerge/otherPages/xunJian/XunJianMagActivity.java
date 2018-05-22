package com.powerge.wise.powerge.otherPages.xunJian;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.powerge.wise.basestone.heart.network.FlatMapResponse;
import com.powerge.wise.basestone.heart.network.FlatMapTopRes;
import com.powerge.wise.basestone.heart.network.ResultModel;
import com.powerge.wise.basestone.heart.ui.XAdapter;
import com.powerge.wise.basestone.heart.ui.XViewHolder;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.bean.User;
import com.powerge.wise.powerge.bean.XunJianSignBean;
import com.powerge.wise.powerge.config.soap.ApiService;
import com.powerge.wise.powerge.config.soap.request.BaseUrl;
import com.powerge.wise.powerge.config.soap.request.RequestBody;
import com.powerge.wise.powerge.config.soap.request.RequestEnvelope;
import com.powerge.wise.powerge.databinding.ActivityXunJianMagBinding;
import com.powerge.wise.powerge.databinding.ItemXunJianSingListBinding;
import com.powerge.wise.powerge.helper.BluToothLEHelper;
import com.powerge.wise.powerge.helper.EEMsgToastHelper;
import com.powerge.wise.powerge.helper.RequestPermissionsHelper;

import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.powerge.wise.powerge.helper.BluToothLEHelper.REQUEST_ENABLE_BT;

public class XunJianMagActivity extends AppCompatActivity implements XunJianDateFragment.OnDateCehckedListener {
    ActivityXunJianMagBinding binding;
    private String TAG = "BLUE_TOOTH_LOG";
    private String date, termType;
    Timer timer = new Timer();
    private String DateChecked;
    private String UUID = "b5b182c7-eab1-4988-aa99-b5c1517008d9";
    private String permissionParam = android.Manifest.permission.ACCESS_COARSE_LOCATION;
    private BluToothLEHelper bluToothLEHelper = null;

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

    /**
     * 在保存巡检项信息后进入该界面 获取新数据
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (termType != null) createData();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void initView() {
        binding.dateContent.setAdapter(new XunJianDatePagerAdapter(getSupportFragmentManager()));
        binding.xunJianTabL.setupWithViewPager(binding.dateContent);
        bluToothLEHelper = new BluToothLEHelper.Builder().setParams(this, getBaseContext(), callback).build();
        stopThenStart();
        RequestPermissionsHelper.instant(this, getBaseContext(), permissionParam); // 获取地址权限
        initContentSign();
    }

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

    @Override
    public void onDateCehckedListener(Map<String, String> params) {
        DateChecked = params.get("checkId");
        termType = params.get("termType");
        date = params.get("date");
        createData();

    }

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


    private void stopThenStart() {

        final Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        for (XunJianSignBean bean : adapter.getList()) {
                            bean.setEnable(false);
                            adapter.notifyDataSetChanged();
                        }
                        bluToothLEHelper.startLeScan();
                        break;
                }
            }
        };


        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 1;
                mHandler.sendMessage(message);
                bluToothLEHelper.stopLeScan();
            }
        }, 10000, 30000);


    }

    final BluetoothAdapter.LeScanCallback callback = new BluetoothAdapter.LeScanCallback() {
        @SuppressLint("NewApi")
        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
            final iBeaconClass.iBeacon ibeacon = iBeaconClass.fromScanData(device, rssi, scanRecord);
            if (ibeacon != null && adapter.getList() != null) {
                if ((Integer.parseInt(termType) == 2 && Integer.parseInt(DateChecked) == 1) || (Integer.parseInt(termType) != 2 && Integer.parseInt(DateChecked) == 3)) {
                    if (UUID.equals(ibeacon.proximityUuid)) {
                        for (XunJianSignBean bean : adapter.getList()) {
                            int major_data = Integer.parseInt(bean.getBlueToothNo());
                            if (major_data == ibeacon.major) {
                                bean.setEnable(true);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                }
            }


        }

    };


    @SuppressLint("NewApi")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BT) {
            bluToothLEHelper.startLeScan();
        }
    }

    @SuppressLint("NewApi")
    @Override
    protected void onStop() {
        super.onStop();
        if (bluToothLEHelper != null) bluToothLEHelper.stopLeScan();
        timer.cancel();
    }

    @SuppressLint("NewApi")
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bluToothLEHelper != null) bluToothLEHelper.stopLeScan();
        timer.cancel();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
        }
    }

}
