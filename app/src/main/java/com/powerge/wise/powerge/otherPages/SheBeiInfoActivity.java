package com.powerge.wise.powerge.otherPages;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.powerge.wise.basestone.heart.network.FlatMapResponse;
import com.powerge.wise.basestone.heart.network.FlatMapTopResList;
import com.powerge.wise.basestone.heart.network.ResultModelData;
import com.powerge.wise.basestone.heart.ui.view.LoadMoreExpandableListView;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.bean.SheBeiRootBean;
import com.powerge.wise.powerge.bean.User;
import com.powerge.wise.powerge.config.soap.ApiService;
import com.powerge.wise.powerge.config.soap.request.BaseUrl;
import com.powerge.wise.powerge.config.soap.request.RequestBody;
import com.powerge.wise.powerge.config.soap.request.RequestEnvelope;
import com.powerge.wise.powerge.databinding.ActivitySheBeiInfoBinding;
import com.powerge.wise.powerge.helper.EEMsgToastHelper;
import com.powerge.wise.powerge.zxing.activity.CaptureActivity;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;
import com.yanzhenjie.permission.SettingService;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SheBeiInfoActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    public static void start(Context context, String result) {
        Intent starter = new Intent(context, SheBeiInfoActivity.class);
        context.startActivity(starter);
    }

    ActivitySheBeiInfoBinding binding;
    ExpandableListAdapter adapter = new ExpandableListAdapter();
    int currentPage = 0;
    private static String keyWord = "";

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_she_bei_info);
        binding.title.setText(getResources().getStringArray(R.array.item_name_array)[4]);
        binding.refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        binding.refreshLayout.setOnRefreshListener(this);
        initView();

    }


    private void initView() {
        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            keyWord = bundle.getString("result");
            binding.editSearchInfo.setText(bundle.getString("result"));
        }
        binding.refreshLayout.setRefreshing(true);
        getSheBeiData(1);
        binding.contentSheBei.setAdapter(adapter);
        binding.contentSheBei.setGroupIndicator(null);
        binding.contentSheBei.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                boolean groupExpanded = parent.isGroupExpanded(groupPosition);
                adapter.setIndicatorState(groupPosition, groupExpanded);
                return false;
            }
        });
        binding.contentSheBei.setOnLoadMoreListener(new LoadMoreExpandableListView.OnLoadMoreListener() {
            @Override
            public void onloadMore() {
                getSheBeiData(currentPage + 1);
            }
        });
        binding.editSearchInfo.setOnEditorActionListener(onEditorActionListener);

    }

    private void getSheBeiData(final int page) {
        final SheBeiRootBean sheBeiRootBean = SheBeiRootBean.newInstance();
        sheBeiRootBean.setNameSpace(BaseUrl.NAMESPACE_P);
        sheBeiRootBean.setPage(String.valueOf(page));
        sheBeiRootBean.setKeyWord(keyWord);
        sheBeiRootBean.setUserName(User.getCurrentUser().getName());

        RequestEnvelope.getRequestEnvelope().setBody(new RequestBody<>(sheBeiRootBean));
        ApiService.Creator.get().queryDevicesData(RequestEnvelope.getRequestEnvelope())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new FlatMapResponse<ResultModelData<ResultModelData.ReturnValueBean<SheBeiRootBean>>>())
                .flatMap(new FlatMapTopResList<ResultModelData.ReturnValueBean<SheBeiRootBean>>())
                .subscribe(new Subscriber<ResultModelData.ReturnValueBean<SheBeiRootBean>>() {
                    @Override
                    public void onCompleted() {
                        binding.refreshLayout.setRefreshing(false);
                        binding.contentSheBei.setLoadCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        EEMsgToastHelper.newInstance().selectWitch(e.getMessage());
                        binding.refreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onNext(ResultModelData.ReturnValueBean<SheBeiRootBean> returnValueBean) {
                        if (returnValueBean.getResultList().size() == 0) return;
                        if (returnValueBean.getCurrentPage().equals("1")) {
                            adapter.setList(returnValueBean.getResultList());
                        } else {
                            adapter.addItems(returnValueBean.getResultList());
                        }
                        if (returnValueBean.getResultList().size() < 10) {
                            binding.contentSheBei.setLoadNoMore();
                        } else {
                            binding.contentSheBei.setLoadCompleted();
                        }

                        binding.refreshLayout.setRefreshing(false);
                        currentPage = Integer.parseInt(returnValueBean.getCurrentPage());
                    }
                });
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                keyWord = "";
                finish();
                break;
            case R.id.btn_scan:
                //调用二维码扫描
                requestPermissions();

                break;
            case R.id.btn_search:
                startSearch();
                break;
        }
    }

    @Override
    public void onRefresh() {
        getSheBeiData(1);
    }

    TextView.OnEditorActionListener onEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager.isActive()) {
                    inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                }

                startSearch();
                return true;
            } else {
                return false;
            }
        }
    };

    /**
     * 调用查询接口
     */
    public void startSearch() {
        keyWord = binding.editSearchInfo.getText().toString();
        getSheBeiData(1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            keyWord = bundle.getString("result");
            binding.editSearchInfo.setText(bundle.getString("result"));
            startSearch();
        }
    }


    private void requestPermissions() {
        AndPermission.with(this)
                .permission(android.Manifest.permission.CAMERA
                )
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        startActivity(new Intent(SheBeiInfoActivity.this, CaptureActivity.class));
                        finish();
                    }
                })
                .rationale(rationaleListener)
                .onDenied(action)
                .start();
    }


    /**
     * Rationale支持，这里自定义对话框。
     * 用户往往会拒绝一些权限，而程序的继续运行又必须使用这些权限，此时我们应该友善的提示用户。
     */
    private Rationale rationaleListener = new Rationale() {
        @Override
        public void showRationale(Context context, List<String> permissions, final RequestExecutor executor) {

            new AlertDialog.Builder(SheBeiInfoActivity.this).setTitle("权限申请")
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

    /**
     * 当用户点击应用程序的某个按钮，而他又总是拒绝我们需要的某个权限时，应用程序可能不会响应（但不是ANR），为了避免这种情况，我们应该在用户总是拒绝某个权限时提示用户去系统设置中授权哪些权限给我们，无论用户是否真的会授权给我们。
     */
    Action action = new Action() {
        @Override
        public void onAction(List<String> permissions) {
            if (AndPermission.hasAlwaysDeniedPermission(getBaseContext(), permissions)) {   // 这些权限被用户总是拒绝。
                final SettingService settingService = AndPermission.permissionSetting(getBaseContext());
                // 这里使用一个Dialog展示没有这些权限应用程序无法继续运行，询问用户是否去设置中授权。
                new AlertDialog.Builder(SheBeiInfoActivity.this).setTitle("权限申请")
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
//                                settingService.cancel();
                            }
                        }).show();

            }
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        keyWord = "";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        keyWord = "";
    }
}
