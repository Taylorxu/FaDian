package com.powerge.wise.powerge.operationProjo.net.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.operationProjo.net.BaseActivity;
import com.powerge.wise.powerge.operationProjo.net.bean.BMForm;
import com.powerge.wise.powerge.operationProjo.net.bean.ButtonModel;
import com.powerge.wise.powerge.operationProjo.net.db.MySharedpreferences;
import com.powerge.wise.powerge.operationProjo.net.manager.WorkOrderSolvingManager;
import com.powerge.wise.powerge.operationProjo.net.net.RequestTask;
import com.powerge.wise.powerge.operationProjo.net.net.request.RequestDict;
import com.powerge.wise.powerge.operationProjo.net.net.request.RequestProcess;
import com.powerge.wise.powerge.operationProjo.net.net.response.BaseResponse;
import com.powerge.wise.powerge.operationProjo.net.net.response.OpenTaskDetailResponse;
import com.powerge.wise.powerge.operationProjo.net.net.response.QueryAllValidDictDateResponse;
import com.powerge.wise.powerge.operationProjo.net.utils.PullPaseXmlUtil;
import com.powerge.wise.powerge.operationProjo.net.view.ButtonView;
import com.powerge.wise.powerge.operationProjo.net.view.TempTreeSelectionDataManager;
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
 
 

public class OrderSolvedActivity extends BaseActivity {

    private MyTitle mt_order_solved;
    private WorkOrderDetailView wodv_solved;
    private LoadingView loadingView;
    private String pikey;
    private String taskId;
    private String taskNodeType;
    private LinearLayout ll_new_temp;
    private BMForm bmForm;

    public static void startSelf(Context context, String current, String picky) {
        Intent intent = new Intent(context, OrderSolvedActivity.class);
        intent.putExtra("current", current);
        intent.putExtra("picky", picky);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_solved);
        WorkOrderSolvingManager.getInstance().setList(this);
        EventBus.getDefault().register(this);
        init();
        request();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WorkOrderSolvingManager.getInstance().delList(this);
        EventBus.getDefault().unregister(this);
        WorkOrderDataManager.getManager().clearDate();
    }


    @Override
    protected void onResume() {
        super.onResume();
        String temp = TempTreeSelectionDataManager.getManager().getTemp();
        if (!TextUtils.isEmpty(temp)) {
//            treeSelectionView.setData(temp);
            TempTreeSelectionDataManager.getManager().clearTemp();
        }
        Log.i("YCS", "onEventMainThread: ABC");
        wodv_solved.refresh();
    }

    @Subscribe
    public void onEventMainThread(String temp) {
        Log.i("YCS", "onEventMainThread: DEF");
        wodv_solved.refresh();
    }

    private void init() {
        loadingView = LoadingView.getLoadingView(this);
        loadingView.show();
        mt_order_solved = (MyTitle) findViewById(R.id.mt_order_solved);
        wodv_solved = (WorkOrderDetailView) findViewById(R.id.wodv_solved);
        ll_new_temp = (LinearLayout) findViewById(R.id.ll_new_template);

        mt_order_solved.setBackListener(true, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
        mt_order_solved.setTitle(getResources().getString(R.string.order_solved_title));
        mt_order_solved.setTvRight(true, "操作记录", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WonhInfoActivity.startSelf(OrderSolvedActivity.this, pikey);
            }
        });
    }

    private void request() {
        String current = getIntent().getStringExtra("current");
        String picky = getIntent().getStringExtra("picky");
        List<String> list = new ArrayList<>();
        list.add(current);
        list.add(picky);
        list.add(MySharedpreferences.getUser().getName());
        RequestProcess.openTaskDetail(list, new RequestTask.ResultCallback<OpenTaskDetailResponse>() {
            @Override
            public void onError(Exception e) {
                loadingView.stop(loadingView);
            }

            @Override
            public void onResponse(final OpenTaskDetailResponse response) {
                List<String> list1 = new ArrayList<>();
                pikey = response.getPIKEY();
                taskId = response.getCURRENT_TASKID();
                taskNodeType = response.getTaskNodeType();
                Map<String, String> map = new HashMap<String, String>();
                map.put("taskNodeType", taskNodeType);
                map.put("PIKEY", pikey);
                map.put("taskId", taskId);
                WorkOrderDataManager.getManager().setMapInit(map);
                RequestDict.queryAllValidDictDate(list1, new RequestTask.ResultCallback<QueryAllValidDictDateResponse>() {
                    @Override
                    public void onError(Exception e) {
                        loadingView.stop(loadingView);
                    }

                    @Override
                    public void onResponse(QueryAllValidDictDateResponse response1) {
                        loadingView.stop(loadingView);
                        String formDocument = response.getFormDocument();
                        List datas = PullPaseXmlUtil.pase(formDocument);
                        wodv_solved.setData(datas, OrderSolvedActivity.this);
                        setButton(datas);
                    }
                });

            }
        });
    }

    public void commit() {
        final LoadingView loadingView = LoadingView.getLoadingView(this);
        loadingView.show();
        WorkOrderDataManager.getManager().solvedMap(new WorkOrderDataManager.LoadListener3() {
            @Override
            public void setLoadListenenr3() {
                if (!WorkOrderDataManager.getManager().isCommit(OrderSolvedActivity.this)) {
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
                        Toast.makeText(OrderSolvedActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }

            @Override
            public void setUnLoadListener() {
                if (!WorkOrderDataManager.getManager().isCommit(OrderSolvedActivity.this)) {
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
                        Toast.makeText(OrderSolvedActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(OrderSolvedActivity.this, "保存草稿成功", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(OrderSolvedActivity.this, "保存草稿成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        });
    }

    private void requestCancel() {
        finish();
//        final LoadingView loadingView = LoadingView.getLoadingView(this);
//        loadingView.show();
//        List<String> list = new ArrayList<>();
//        list.add(pikey);
//        list.add(MySharedpreferences.getUser().getName());
//        RequestProcess.deleteProcessInfoOnFirst(list, new RequestTask.ResultCallback<BaseResponse>() {
//            @Override
//            public void onError(Exception e) {
//                loadingView.stop(loadingView);
//            }
//
//            @Override
//            public void onResponse(BaseResponse response) {
//                loadingView.stop(loadingView);
//                Toast.makeText(OrderSolvedActivity.this, "取消保存草稿成功", Toast.LENGTH_SHORT).show();
//                finish();
//            }
//        });
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