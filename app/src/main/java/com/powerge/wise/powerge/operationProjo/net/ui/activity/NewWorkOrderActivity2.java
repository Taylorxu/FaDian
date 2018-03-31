package com.powerge.wise.powerge.operationProjo.net.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.operationProjo.net.BaseActivity;
import com.powerge.wise.powerge.operationProjo.net.bean.BMForm;
import com.powerge.wise.powerge.operationProjo.net.bean.ButtonModel;
import com.powerge.wise.powerge.operationProjo.net.db.MySharedpreferences;
import com.powerge.wise.powerge.operationProjo.net.manager.WorkOrderSolvingManager;
import com.powerge.wise.powerge.operationProjo.net.net.RequestTask;
import com.powerge.wise.powerge.operationProjo.net.net.request.RequestProcess;
import com.powerge.wise.powerge.operationProjo.net.net.response.BaseResponse;
import com.powerge.wise.powerge.operationProjo.net.net.response.CreateProcessByKeyAndCreatorResponse;
import com.powerge.wise.powerge.operationProjo.net.utils.PullPaseXmlUtil;
import com.powerge.wise.powerge.operationProjo.net.view.ButtonView;
import com.powerge.wise.powerge.operationProjo.net.view.mview.KeyValueView;
import com.powerge.wise.powerge.operationProjo.net.view.mview.LoadingView;
import com.powerge.wise.powerge.operationProjo.net.view.mview.MyDialog;
import com.powerge.wise.powerge.operationProjo.net.view.mview.MyTitle;
import com.powerge.wise.powerge.operationProjo.net.view.mview.WorkOrderDataManager;
import com.powerge.wise.powerge.operationProjo.net.view.mview.WorkOrderDetailView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class NewWorkOrderActivity2 extends BaseActivity {

    private MyTitle mt_new_work_order2;
    private WorkOrderDetailView wodv_new_work_order2;
    private String key;
    private String type;
    private KeyValueView kvv_type;
    private KeyValueView kvv_code;
    private TextView tv_back;
    //工单号
    private String code;
    private String pikey;
    private String taskId;
    private String taskNodeType;
    private BMForm bmForm;
    private LinearLayout ll_new_temp;

    public static void startSelf(Context context, String key, String type) {
        Intent intent = new Intent(context, NewWorkOrderActivity2.class);
        intent.putExtra("key", key);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_work_order2);
        WorkOrderSolvingManager.getInstance().setList2(this);
        EventBus.getDefault().register(this);
        init();
        request();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        WorkOrderSolvingManager.getInstance().delList2(this);
        WorkOrderDataManager.getManager().clearDate();
    }

    @Subscribe
    public void onEventMainThread(String temp) {
        wodv_new_work_order2.refresh();
    }

    private void init() {
        mt_new_work_order2 = (MyTitle) findViewById(R.id.mt_new_work_order_2);
        wodv_new_work_order2 = (WorkOrderDetailView) findViewById(R.id.wodv_new_work_order2);
        kvv_type = (KeyValueView) findViewById(R.id.kvv_new_work_order_type);
        kvv_code = (KeyValueView) findViewById(R.id.kvv_new_work_order_code);
        tv_back = (TextView) findViewById(R.id.tv_back);
        ll_new_temp = (LinearLayout) findViewById(R.id.ll_new_template);

        mt_new_work_order2.setTitle("新建工单");
        key = getIntent().getStringExtra("key");
        type = getIntent().getStringExtra("type");
        mt_new_work_order2.setBackListener(true, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("YCS", "onClick: 点击了返回");
                save();
            }
        });

        kvv_type.setValueText(type);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void request() {
        final LoadingView loadingView = LoadingView.getLoadingView(this);
        loadingView.show();
        List<String> list = new ArrayList<>();
        list.add(key);
        list.add(MySharedpreferences.getUser().getName());
        RequestProcess.createProcessByKeyAndCreator(list, new RequestTask.ResultCallback<CreateProcessByKeyAndCreatorResponse>() {
            @Override
            public void onError(Exception e) {
                loadingView.stop(loadingView);
            }

            @Override
            public void onResponse(CreateProcessByKeyAndCreatorResponse response) {
                code = response.getPIKEY();
                kvv_code.setValueText(code);
                List datas = PullPaseXmlUtil.pase(response.getFormDocument());
                pikey = response.getPIKEY();
                taskId = response.getCURRENT_TASKID();
                taskNodeType = response.getTaskNodeType();
                Map<String, String> map = new HashMap<String, String>();
                map.put("taskNodeType", taskNodeType);
                map.put("PIKEY", pikey);
                map.put("taskId", taskId);
                WorkOrderDataManager.getManager().setMapInit(map);
                wodv_new_work_order2.setData(datas, NewWorkOrderActivity2.this);
                setButton(datas);
                loadingView.stop(loadingView);
            }
        });
    }

    private void save() {
        MyDialog dialog = new MyDialog(this);
        dialog.setData("是否保存为草稿", dialog, new MyDialog.IMyDialog2() {
            @Override
            public void setMyDialogSure() {
                requestSure();
            }

            @Override
            public void setMyDialogCancel() {
                requestCancel();
            }
        });
        dialog.show();
    }

    private void requestCancel() {
        final LoadingView loadingView = LoadingView.getLoadingView(this);
        loadingView.show();
        List<String> list = new ArrayList<>();
        list.add(code);
        list.add(MySharedpreferences.getUser().getName());
        RequestProcess.deleteProcessInfoOnFirst(list, new RequestTask.ResultCallback<BaseResponse>() {
            @Override
            public void onError(Exception e) {
                loadingView.stop(loadingView);
            }

            @Override
            public void onResponse(BaseResponse response) {
                loadingView.stop(loadingView);
                Toast.makeText(NewWorkOrderActivity2.this, "取消保存草稿成功", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    /**
     * 保存工单到草稿
     */
    private void requestSure() {
        final LoadingView loadingView = LoadingView.getLoadingView(this);
        loadingView.show();
        WorkOrderDataManager.getManager().solvedMap(new WorkOrderDataManager.LoadListener3() {
            @Override
            public void setLoadListenenr3() {
                Map<String, String> map = WorkOrderDataManager.getManager().getReturnStringModel();
                Gson gson = new Gson();
                String result = gson.toJson(map);
                List<String> list = new ArrayList<>();
                list.add(result);
                list.add(MySharedpreferences.getUser().getName());
                RequestProcess.saveProcessSketch(list, new RequestTask.ResultCallback<BaseResponse>() {
                    @Override
                    public void onError(Exception e) {
                        loadingView.stop(loadingView);
                    }

                    @Override
                    public void onResponse(BaseResponse response) {
                        loadingView.stop(loadingView);
                        Toast.makeText(NewWorkOrderActivity2.this, "保存草稿成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }

            @Override
            public void setUnLoadListener() {
                Map<String, String> map = WorkOrderDataManager.getManager().getReturnStringModel();
                Gson gson = new Gson();
                String result = gson.toJson(map);
                List<String> list = new ArrayList<>();
                list.add(result);
                list.add(MySharedpreferences.getUser().getName());
                RequestProcess.saveProcessSketch(list, new RequestTask.ResultCallback<BaseResponse>() {
                    @Override
                    public void onError(Exception e) {
                        loadingView.stop(loadingView);
                    }

                    @Override
                    public void onResponse(BaseResponse response) {
                        loadingView.stop(loadingView);
                        Toast.makeText(NewWorkOrderActivity2.this, "保存草稿成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        wodv_new_work_order2.refresh();
    }

    public void commit() {
        final LoadingView loadingView = LoadingView.getLoadingView(this);
        loadingView.show();
        WorkOrderDataManager.getManager().solvedMap(new WorkOrderDataManager.LoadListener3() {
            @Override
            public void setLoadListenenr3() {
                if (!WorkOrderDataManager.getManager().isCommit(NewWorkOrderActivity2.this)) {
                    loadingView.stop(loadingView);
                    return;
                }
                Map<String, String> map = WorkOrderDataManager.getManager().getReturnString();
                Gson gson = new Gson();
                String result = gson.toJson(map);
                List<String> list = new ArrayList<>();
                list.add(result);
                list.add(MySharedpreferences.getUser().getName());
                RequestProcess.submitTask(list, new RequestTask.ResultCallback<BaseResponse>() {
                    @Override
                    public void onError(Exception e) {
                        loadingView.stop(loadingView);
                    }

                    @Override
                    public void onResponse(BaseResponse response) {
                        loadingView.stop(loadingView);
                        Toast.makeText(NewWorkOrderActivity2.this, "新建成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }

            @Override
            public void setUnLoadListener() {
                if (!WorkOrderDataManager.getManager().isCommit(NewWorkOrderActivity2.this)) {
                    loadingView.stop(loadingView);
                    return;
                }
                Map<String, String> map = WorkOrderDataManager.getManager().getReturnString();
                Gson gson = new Gson();
                String result = gson.toJson(map);
                List<String> list = new ArrayList<>();
                list.add(result);
                list.add(MySharedpreferences.getUser().getName());
                RequestProcess.submitTask(list, new RequestTask.ResultCallback<BaseResponse>() {
                    @Override
                    public void onError(Exception e) {
                        loadingView.stop(loadingView);
                    }

                    @Override
                    public void onResponse(BaseResponse response) {
                        loadingView.stop(loadingView);
                        Toast.makeText(NewWorkOrderActivity2.this, "新建成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        });
    }

    private void setButton(List datas) {
        for (Object o : datas) {
            if (o instanceof ButtonModel) {
                List<ButtonModel.NextNode> list = ((ButtonModel) o).getNextNode();
                if (list == null || list.size() == 0) {
                    continue;
                }
                ButtonView buttonView = new ButtonView(this);
                buttonView.setData((ButtonModel) o, bmForm.getConditionJudgment());
                ll_new_temp.addView(buttonView);
            } else if (o instanceof BMForm) {
                bmForm = ((BMForm) o);
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            save();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
