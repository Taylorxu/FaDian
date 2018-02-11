package com.wisesignsoft.OperationManagement.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.util.EasyUtils;

import com.wisesignsoft.OperationManagement.R;
import com.wisesignsoft.OperationManagement.ui.fragment.ChatFragment;

public class ChatActivity extends FragmentActivity {

    public static ChatActivity activityInstance;
    private EaseChatFragment chatFragment;
    String toChatUsername;

    public static void startSelf(Context context,String name,String id){
        Intent intent = new Intent(context,ChatActivity.class);
        intent.putExtra("name",name);
        intent.putExtra("userId",id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        activityInstance = this;
        //聊天人或群id
        toChatUsername = getIntent().getExtras().getString("userId");
//        toChatUsername = "admi";
        //可以直接new EaseChatFratFragment使用
        chatFragment = new ChatFragment();
        //传入参数
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityInstance = null;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        finish();
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        chatFragment.onBackPressed();

    }

    public String getToChatUsername() {
        return toChatUsername;
    }

}
