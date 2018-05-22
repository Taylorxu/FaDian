package com.powerge.wise.powerge.otherPages.xunJian;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.powerge.wise.basestone.heart.network.FlatMapResponse;
import com.powerge.wise.basestone.heart.network.FlatMapTopRes;
import com.powerge.wise.basestone.heart.network.ResultModel;
import com.powerge.wise.basestone.heart.ui.XAdapter;
import com.powerge.wise.basestone.heart.ui.XViewHolder;
import com.powerge.wise.powerge.BR;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.bean.User;
import com.powerge.wise.powerge.bean.XuJianCheckBean;
import com.powerge.wise.powerge.bean.XunJianFormBean;
import com.powerge.wise.powerge.bean.XunJianSignBean;
import com.powerge.wise.powerge.config.soap.ApiService;
import com.powerge.wise.powerge.config.soap.request.BaseUrl;
import com.powerge.wise.powerge.config.soap.request.RequestBody;
import com.powerge.wise.powerge.config.soap.request.RequestEnvelope;
import com.powerge.wise.powerge.databinding.ActivityXjFillFormBinding;
import com.powerge.wise.powerge.databinding.ItemXunJianFillFormBinding;
import com.powerge.wise.powerge.helper.BluToothLEHelper;
import com.powerge.wise.powerge.helper.EEMsgToastHelper;
import com.powerge.wise.powerge.operationProjo.net.utils.LogUtil;
import com.powerge.wise.powerge.operationProjo.net.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class XjFillFormActivity extends AppCompatActivity {
    public static String extraKeyEdit = "ISEDIT", extraKeyParcelable = "PARCELABLE", extraKeytermType = "TERMTYPE", extraKeyDate = "DATE", extraKeyTitle = "TITLE";
    public static String extraResult = "RESULTOKEXTRA";
    private XunJianSignBean xunJianSignBean;
    private BluToothLEHelper bluToothLEHelper = null;
    private String UUID = "b5b182c7-eab1-4988-aa99-b5c1517008d9";
    private Boolean isEdit;
    private String termType;
    public static int requestCode = 200;
    private ActivityXjFillFormBinding binding;
    private XAdapter<XunJianFormBean, ItemXunJianFillFormBinding> adapter = new XAdapter.SimpleAdapter<>(BR.data, R.layout.item_xun_jian_fill_form);

    private BluetoothAdapter mBluetoothAdapter;

    public static void starter(Context context, boolean isEdit, XunJianSignBean xunJianSign, String termType) {
        Intent starter = new Intent(context, XjFillFormActivity.class);
        starter.putExtra(extraKeyEdit, isEdit);
        starter.putExtra(extraKeyParcelable, xunJianSign);
        starter.putExtra(extraKeytermType, termType);
        context.startActivity(starter);
    }

    public static void starter(Context context, boolean isEdit, String date, String title) {
        Intent starter = new Intent(context, XjFillFormActivity.class);
        starter.putExtra(extraKeyEdit, isEdit);
        starter.putExtra(extraKeyDate, date);
        starter.putExtra(extraKeyTitle, title);
        context.startActivity(starter);
    }

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_xj_fill_form);
        isEdit = getIntent().getBooleanExtra(extraKeyEdit, true);
        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        initView();

    }

    private void initView() {
        binding.contentList.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        binding.contentList.setAdapter(adapter);
        binding.contentList.addItemDecoration(new DividerItemDecoration(getBaseContext(), LinearLayout.VERTICAL));
        // 编辑
        if (isEdit) {
            xunJianSignBean = getIntent().getParcelableExtra(extraKeyParcelable);
            binding.title.setText(xunJianSignBean.getName() + "检查项");
            termType = getIntent().getStringExtra(extraKeytermType);
            adapter.setItemClickListener(itemClickListener);
            putNameData(xunJianSignBean.getPointNo());
        } else {  //查看 添加数据
            binding.title.setText(getIntent().getStringExtra(extraKeyTitle) + "检查项");
            binding.btSave.setVisibility(View.GONE);
            putNameData(getIntent().getStringExtra(extraKeyDate));
        }
    }


    private void putNameData(String arg1) {
        Observable observable = null;
        if (isEdit) {
            XunJianFormBean bean = new XunJianFormBean();
            bean.setNameSpace(BaseUrl.NAMESPACE_P);
            bean.setUserName(User.getCurrentUser().getName());
            bean.setArg1(arg1);
            RequestEnvelope.getRequestEnvelope().setBody(new RequestBody<>(bean));
            observable = ApiService.Creator.get().queryItemsOfPoint(RequestEnvelope.getRequestEnvelope());
        } else {
            XuJianCheckBean bean = new XuJianCheckBean();
            bean.setNameSpace(BaseUrl.NAMESPACE_P);
            bean.setUserName(User.getCurrentUser().getName());
            bean.setArg1(arg1);
            RequestEnvelope.getRequestEnvelope().setBody(new RequestBody<>(bean));
            observable = ApiService.Creator.get().queryCheckResults(RequestEnvelope.getRequestEnvelope());

        }

        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .flatMap(new FlatMapResponse<ResultModel<List<XunJianFormBean>>>())
                .flatMap(new FlatMapTopRes<List<XunJianFormBean>>())
                .subscribe(new Subscriber<List<XunJianFormBean>>() {
                    @Override
                    public void onCompleted() {
                        crossfade();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        EEMsgToastHelper.newInstance().selectWitch(e.getMessage());
                        crossfade();
                    }

                    @SuppressLint("NewApi")
                    @Override
                    public void onNext(List<XunJianFormBean> list) {
                        adapter.setList(list);
                    }
                });
    }

    public void crossfade() {
        binding.contentList.setAlpha(0f);
        binding.contentList.setVisibility(View.VISIBLE);
        binding.contentList.animate().alpha(1f)
                .setDuration(1000)
                .setListener(null);

        binding.progressBar.animate()
                .alpha(0f)
                .setDuration(500)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        binding.progressBar.setVisibility(View.GONE);
                    }
                });

    }

    XAdapter.OnItemClickListener<XunJianFormBean, ItemXunJianFillFormBinding> itemClickListener = new XAdapter.OnItemClickListener<XunJianFormBean, ItemXunJianFillFormBinding>() {
        @Override
        public void onItemClick(XViewHolder<XunJianFormBean, ItemXunJianFillFormBinding> holder) {
            XjEdititemActivity.start(XjFillFormActivity.this, holder.getBinding().getData());
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == requestCode) {
                XunJianFormBean formBean = data.getParcelableExtra(extraResult);
                for (int i = 0; i < adapter.getList().size(); i++) {
                    if (formBean.getCheckItem().equals(adapter.getItemData(i).getCheckItem())) {
                        adapter.getItemData(i).setCheckResult(formBean.getCheckResult());
                        break;
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.bt_save:
                binding.btSave.setEnabled(false);
                signAction();
                break;
        }
    }


    final BluetoothAdapter.LeScanCallback callback = new BluetoothAdapter.LeScanCallback() {
        @SuppressLint("NewApi")
        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
            final iBeaconClass.iBeacon ibeacon = iBeaconClass.fromScanData(device, rssi, scanRecord);
            if (ibeacon != null && UUID.equals(ibeacon.proximityUuid) && ibeacon.major == Integer.decode(xunJianSignBean.getBlueToothNo())) {
                LogUtil.log("XXXXXXXXXXXXXXXX+++++++++++++++++++++++++++++++++");
                handler.removeCallbacks(runnable);
                mBluetoothAdapter.stopLeScan(callback);
                requestSign();
            }
        }
    };


    /**
     * 签到
     */
    private String itemParamsString;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @SuppressLint("NewApi")
        @Override
        public void run() {
            binding.progressBar.setVisibility(View.GONE);
            ToastUtil.toast(getBaseContext(), "未搜索到巡检点,请在巡检点附近再次尝试连接");
            mBluetoothAdapter.stopLeScan(callback);
            binding.btSave.setEnabled(true);
        }
    };

    @SuppressLint("NewApi")
    private void signAction() {
        List<FormData> list = new ArrayList<>();
        for (XunJianFormBean form : adapter.getList()) {
            if (form.checkEmpty()) {
                ToastUtil.toast(getBaseContext(), "请填写" + form.getCheckItem());
                break;
            }
            FormData data = new FormData(form.getCheckItemId(), form.getCheckItem(), form.getCheckResult());
            list.add(data);
        }
        Gson gson = new Gson();
        itemParamsString = gson.toJson(list);
        waitingSign();
        mBluetoothAdapter.startLeScan(callback);
        handler.postDelayed(runnable, 30000);
    }

    private void requestSign() {
        SignSoapRequest bean = new SignSoapRequest();
        bean.setNameSpace(BaseUrl.NAMESPACE_P);
        bean.setUserName(User.getCurrentUser().getName());
        bean.setArg1(xunJianSignBean.getPointNo());
        bean.setArg2(xunJianSignBean.getName());
        bean.setArg3(xunJianSignBean.getBlueToothNo());
        bean.setArg4(termType);
        bean.setArg5(itemParamsString);

        RequestEnvelope.getRequestEnvelope().setBody(new RequestBody<>(bean));
        ApiService.Creator.get().inspectPoint(RequestEnvelope.getRequestEnvelope())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .flatMap(new FlatMapResponse<ResultModel<SignSoapRequest>>())
                .flatMap(new FlatMapTopRes<SignSoapRequest>())
                .subscribe(new Subscriber<SignSoapRequest>() {
                    public void onCompleted() {
                        finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if (e != null) {
                            EEMsgToastHelper.newInstance().selectWitch(e.getMessage());
                        }
                        binding.progressBar.setVisibility(View.GONE);
                        binding.btSave.setEnabled(true);
                    }

                    @Override
                    public void onNext(SignSoapRequest x) {
                        Toast.makeText(getBaseContext(), "签到成功", Toast.LENGTH_SHORT).show();
                        binding.progressBar.setVisibility(View.GONE);

                    }
                });
    }

    public void waitingSign() {
        binding.progressBar.animate()
                .alpha(1f)
                .setDuration(200)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        binding.progressBar.setVisibility(View.VISIBLE);
                    }
                });
    }

    private class FormData {
        /**
         * {checkNo="检查项编号1"; checkName="检查项名称1"; checkData="检查项结果1"}
         * checkData为选项时：0-正常，1-异常
         */
        private String checkNo;
        private String checkName;
        private String checkData;

        public FormData(String checkNo, String checkName, String checkData) {
            this.checkNo = checkNo;
            this.checkName = checkName;
            this.checkData = checkData;
        }
    }


    @SuppressLint("NewApi")
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
