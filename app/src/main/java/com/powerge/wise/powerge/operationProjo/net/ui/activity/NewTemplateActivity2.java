package com.powerge.wise.powerge.operationProjo.net.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.operationProjo.net.BaseActivity;
import com.powerge.wise.powerge.operationProjo.net.bean.BMForm;
import com.powerge.wise.powerge.operationProjo.net.bean.ButtonModel;
import com.powerge.wise.powerge.operationProjo.net.db.MySharedpreferences;
import com.powerge.wise.powerge.operationProjo.net.net.RequestTask;
import com.powerge.wise.powerge.operationProjo.net.net.request.RequestProcess;
import com.powerge.wise.powerge.operationProjo.net.net.response.BaseResponse;
import com.powerge.wise.powerge.operationProjo.net.net.response.CreateTemplateResponse;
import com.powerge.wise.powerge.operationProjo.net.utils.PullPaseXmlUtil;
import com.powerge.wise.powerge.operationProjo.net.view.ButtonView;
import com.powerge.wise.powerge.operationProjo.net.view.mview.LoadingView;
import com.powerge.wise.powerge.operationProjo.net.view.mview.MyTitle;
import com.powerge.wise.powerge.operationProjo.net.view.mview.WorkOrderDataManager;
import com.powerge.wise.powerge.operationProjo.net.view.mview.WorkOrderDetailView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class NewTemplateActivity2 extends BaseActivity {
    private MyTitle mt_new_template2;
    private WorkOrderDetailView wodv_new_template2;
    private String key;
    private String name;
    private BMForm bmForm;
    private LinearLayout ll_new_temp;

    public static void startSelf(Context context, String name, String key) {
        Intent intent = new Intent(context, NewTemplateActivity2.class);
        intent.putExtra("name", name);
        intent.putExtra("key", key);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_template2);
        EventBus.getDefault().register(this);
        init();
        request();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void init() {
        mt_new_template2 = (MyTitle) findViewById(R.id.mt_new_template2);
        wodv_new_template2 = (WorkOrderDetailView) findViewById(R.id.wodv_new_template2);
        ll_new_temp = (LinearLayout) findViewById(R.id.ll_new_template);

        mt_new_template2.setBack(true, this);
        mt_new_template2.setTitle("新建模板");

        key = getIntent().getStringExtra("key");
        name = getIntent().getStringExtra("name");
    }

    private void request() {
        final LoadingView loadingView = LoadingView.getLoadingView(this);
        loadingView.show();
        List<String> list = new ArrayList<>();
        list.add(key);
        list.add(MySharedpreferences.getUser().getName());
        RequestProcess.crateTemplet(list, new RequestTask.ResultCallback<CreateTemplateResponse>() {
            @Override
            public void onError(Exception e) {
                loadingView.stop(loadingView);
            }

            @Override
            public void onResponse(CreateTemplateResponse response) {
                String str = response.getReturnValue().getFormDocument();
                List datas = PullPaseXmlUtil.pase(str);
                wodv_new_template2.setData(datas, NewTemplateActivity2.this);
                setButton(datas);
                loadingView.stop(loadingView);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        wodv_new_template2.refresh();
    }
    @Subscribe
    public void onEventMainThread(String temp) {
        wodv_new_template2.refresh();
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

    public void commit() {
//        if (!WorkOrderDataManager.getManager().isCommit(this)) {
//            return;
//        }
        final LoadingView loadingView = LoadingView.getLoadingView(this);
        loadingView.show();
        WorkOrderDataManager.getManager().solvedMap(new WorkOrderDataManager.LoadListener3() {
            @Override
            public void setLoadListenenr3() {
                Map<String, String> map = WorkOrderDataManager.getManager().getReturnStringModel();
                Gson gson = new Gson();
                String result = gson.toJson(map);
                List<String> list = new ArrayList<>();
                list.add(key);
                list.add(result);
                list.add(MySharedpreferences.getUser().getName());
                RequestProcess.saveTeplet(list, new RequestTask.ResultCallback<BaseResponse>() {
                    @Override
                    public void onError(Exception e) {
                        loadingView.stop(loadingView);
                    }

                    @Override
                    public void onResponse(BaseResponse response) {
                        loadingView.stop(loadingView);
                        Toast.makeText(NewTemplateActivity2.this, "创建模板成功", Toast.LENGTH_SHORT).show();
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
                list.add(key);
                list.add(result);
                list.add(MySharedpreferences.getUser().getName());
                RequestProcess.saveTeplet(list, new RequestTask.ResultCallback<BaseResponse>() {
                    @Override
                    public void onError(Exception e) {
                        loadingView.stop(loadingView);
                    }

                    @Override
                    public void onResponse(BaseResponse response) {
                        loadingView.stop(loadingView);
                        Toast.makeText(NewTemplateActivity2.this, "创建模板成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        });
    }
}
