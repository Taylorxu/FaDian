package com.powerge.wise.powerge.operationProjo.net.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.powerge.wise.powerge.operationProjo.net.BaseActivity;
import com.powerge.wise.powerge.operationProjo.net.adapter.UnReadedMsgAdapter;
import com.powerge.wise.powerge.operationProjo.net.db.MySharedpreferences;
import com.powerge.wise.powerge.operationProjo.net.net.RequestTask;
import com.powerge.wise.powerge.operationProjo.net.net.request.RequestMessage;
import com.powerge.wise.powerge.operationProjo.net.net.response.BaseResponse;
import com.powerge.wise.powerge.operationProjo.net.net.response.FindUnReadedMsgResponse;
import com.powerge.wise.powerge.operationProjo.net.utils.DividerItemDecoration;
import com.powerge.wise.powerge.operationProjo.net.view.mview.EmptyView;
import com.powerge.wise.powerge.operationProjo.net.view.mview.MyTitle;
import com.wisesignsoft.OperationManagement.R;

import java.util.ArrayList;
import java.util.List;



public class MessageActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    private MyTitle mt_message;
    private SwipeRefreshLayout srl_message;
    private RecyclerView rv_message;
    private EmptyView ev_message;

    private List<FindUnReadedMsgResponse.UnReadedMsg> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        init();
    }
    private void init(){
        mt_message = (MyTitle) findViewById(R.id.mt_message);
        srl_message = (SwipeRefreshLayout) findViewById(R.id.srl_message);
        rv_message = (RecyclerView) findViewById(R.id.rv_message);
        ev_message = (EmptyView) findViewById(R.id.ev_message);

        mt_message.setBack(true,this);
        mt_message.setTitle("消息通知");
        srl_message.setColorSchemeResources(R.color.colorPrimaryDark,R.color.colorAccent,R.color.colorAccent);
        srl_message.setOnRefreshListener(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv_message.setLayoutManager(layoutManager);
        rv_message.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

        srl_message.post(new Runnable() {
            @Override
            public void run() {
                srl_message.setRefreshing(true);
                onRefresh();
            }
        });
    }
    private void request(){
        List<String> list = new ArrayList<>();
        list.add(MySharedpreferences.getUser().getName());
        RequestMessage.findUnReadedMsg(list, new RequestTask.ResultCallback<FindUnReadedMsgResponse>() {
            @Override
            public void onError(Exception e) {
                if(srl_message.isRefreshing()){
                    srl_message.setRefreshing(false);
                }
            }

            @Override
            public void onResponse(FindUnReadedMsgResponse response) {
                if(srl_message.isRefreshing()){
                    srl_message.setRefreshing(false);
                }
                datas = response.getResult();
                Log.i("YCS", "onResponse: size:"+datas.size());
                initData();
            }
        });
    }

    private void initData(){
        setEmpty();
        UnReadedMsgAdapter adapter = new UnReadedMsgAdapter(this,datas);
        rv_message.setAdapter(adapter);
        adapter.setIUnReadedMsg(new UnReadedMsgAdapter.IUnReadedMsg() {
            @Override
            public void setIUnReadedMsg(final FindUnReadedMsgResponse.UnReadedMsg msg) {
                MessageDetailActivity.startSelf(MessageActivity.this,msg);
                List<String> list = new ArrayList<String>();
                list.add(msg.getId());
                RequestMessage.setMsgReaded(list, new RequestTask.ResultCallback<BaseResponse>() {
                    @Override
                    public void onError(Exception e) {
                    }

                    @Override
                    public void onResponse(BaseResponse response) {
                    }
                });
            }
        });
    }


    private void setEmpty(){
        if(datas == null||datas.size() == 0){
            srl_message.setVisibility(View.GONE);
            ev_message.setVisibility(View.VISIBLE);
            ev_message.setOnRefreshListener(new EmptyView.IRefreshListener() {
                @Override
                public void onRefresh() {
                    request();
                }
            });
        }else {
            srl_message.setVisibility(View.VISIBLE);
            ev_message.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRefresh() {
        request();
    }
}
