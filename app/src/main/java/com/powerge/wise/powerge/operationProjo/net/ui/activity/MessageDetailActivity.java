package com.powerge.wise.powerge.operationProjo.net.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.powerge.wise.powerge.operationProjo.net.BaseActivity;
import com.powerge.wise.powerge.operationProjo.net.net.response.FindUnReadedMsgResponse;
import com.powerge.wise.powerge.operationProjo.net.view.mview.MyTitle;
import com.wisesignsoft.OperationManagement.R;



public class MessageDetailActivity extends BaseActivity {

    private MyTitle mt_message_detail;
    private TextView tv_title;
    private TextView tv_name;
    private TextView tv_time;
    private TextView tv_content;

    public static void startSelf(Context context, FindUnReadedMsgResponse.UnReadedMsg msg){
        Intent intent = new Intent(context,MessageDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("model",msg);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        init();
        request();
    }
    private void init(){
        mt_message_detail = (MyTitle) findViewById(R.id.mt_message_detail);
        tv_title = (TextView) findViewById(R.id.tv_message_detail_title);
        tv_name = (TextView) findViewById(R.id.tv_message_detail_name);
        tv_time = (TextView) findViewById(R.id.tv_message_detail_time);
        tv_content = (TextView) findViewById(R.id.tv_message_detail_content);

        mt_message_detail.setBack(true,this);
        mt_message_detail.setTitle("消息通知");
    }

    private void request() {
        FindUnReadedMsgResponse.UnReadedMsg msg = (FindUnReadedMsgResponse.UnReadedMsg) getIntent().getSerializableExtra("model");
        initData(msg);
    }

    private void initData(FindUnReadedMsgResponse.UnReadedMsg msg) {
        tv_title.setText(msg.getTitle());
        tv_name.setText(msg.getUserName());
        tv_time.setText(msg.getCreatetime());
        tv_content.setText(msg.getContent());
    }
}
