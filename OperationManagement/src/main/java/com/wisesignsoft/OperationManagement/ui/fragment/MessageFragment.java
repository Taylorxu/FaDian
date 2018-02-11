package com.wisesignsoft.OperationManagement.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.wisesignsoft.OperationManagement.BaseFragment;
import com.wisesignsoft.OperationManagement.R;
import com.wisesignsoft.OperationManagement.adapter.MessageAdapter;
import com.wisesignsoft.OperationManagement.db.MySharedpreferences;
import com.wisesignsoft.OperationManagement.net.RequestTask;
import com.wisesignsoft.OperationManagement.net.request.RequestMessage;
import com.wisesignsoft.OperationManagement.net.response.BaseResponse;
import com.wisesignsoft.OperationManagement.ui.activity.ChatActivity;
import com.wisesignsoft.OperationManagement.ui.activity.MessageActivity;
import com.wisesignsoft.OperationManagement.utils.DividerItemDecoration;
import com.wisesignsoft.OperationManagement.view.mview.MyDialog;
import com.wisesignsoft.OperationManagement.view.mview.MyDialog2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Created by ycs on 2016/11/18.
 */

public class MessageFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private TextView tv_message;
    private SwipeRefreshLayout srl_message;
    private RecyclerView rv_message;

    //数据源
    List<EMConversation> datas = new ArrayList<>();
    private MessageAdapter adapter;
    private LinearLayout ll_fragment_message;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message,container,false);
        init(view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    private void init(View view){
        ll_fragment_message = (LinearLayout) view.findViewById(R.id.ll_fragment_message);
        tv_message = (TextView) view.findViewById(R.id.tv_red);
        srl_message = (SwipeRefreshLayout) view.findViewById(R.id.srl_message);
        rv_message = (RecyclerView) view.findViewById(R.id.rv_message);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        rv_message.setLayoutManager(manager);
        rv_message.addItemDecoration(new DividerItemDecoration(getContext(),RecyclerView.VERTICAL));
        srl_message.setOnRefreshListener(this);
        srl_message.setColorSchemeResources(R.color.colorAccent,R.color.colorPrimary,R.color.colorPrimaryDark);

        //注册长按事件
        registerForContextMenu(rv_message);
        request();

        ll_fragment_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MessageActivity.class);
                startActivity(intent);
            }
        });
    }
    private void request() {
        List<String> list = new ArrayList<>();
        list.add(MySharedpreferences.getUser().getUsername());
        RequestMessage.findUnReadedMsgCount(list, new RequestTask.ResultCallback<BaseResponse>() {
            @Override
            public void onError(Exception e) {

            }

            @Override
            public void onResponse(BaseResponse response) {
                try{
                    tv_message.setText(response.getI());
                }catch (Exception e){
                    e.printStackTrace();
                }
                if(datas != null){
                    datas.clear();
                }
                datas.addAll(loadConversationList());
                initData();
            }
        });
    }
    private void initData() {
        if (adapter == null) {
            adapter = new MessageAdapter(getActivity(),datas);
            rv_message.setAdapter(adapter);
        } else {
            rv_message.post(new Runnable() {
                @Override
                public void run() {
                adapter.notifyDataSetChanged();
                }
            });
        }
        adapter.setIMessageAdapter(new MessageAdapter.IMessageAdapter() {
            @Override
            public void setIMessageClick(int position) {
                EMConversation conversation = datas.get(position);
                String username = conversation.getUserName();
                if (username.equals(EMClient.getInstance().getCurrentUser())) {
                    Toast.makeText(getActivity(), R.string.Cant_chat_with_yourself, Toast.LENGTH_SHORT).show();
                } else {
                    // 进入聊天页面
                    Intent intent = new Intent(getActivity(), ChatActivity.class);
//                    if (conversation.isGroup()) {
//                        intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_GROUP);
//                    }
                    // it's single chat
                    intent.putExtra(EaseConstant.EXTRA_USER_ID, username);
                    startActivity(intent);
//                    ChatActivity.startSelf(getActivity(),);
                }
            }

            @Override
            public void setOnLongClick(final int position) {
                Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                long[] pattern = {100, 400};
                vibrator.vibrate(pattern, -1);
                MyDialog2 dialog = new MyDialog2(getContext());
                dialog.setiDelete(new MyDialog2.IDelete() {
                    @Override
                    public void setDelete1() {
                        EMConversation tobeDeleteCons = datas.get(position);
                        try {
                            EMClient.getInstance().chatManager().deleteConversation(tobeDeleteCons.getUserName(), true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                        onRefresh();
                    }

                    @Override
                    public void setDelete2() {
                        EMConversation tobeDeleteCons = datas.get(position);
                        try {
                            EMClient.getInstance().chatManager().deleteConversation(tobeDeleteCons.getUserName(), false);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                        onRefresh();
                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    public void onRefresh() {
        request();
        srl_message.setRefreshing(false);
    }
    /**
     * 获取会话列表
     *
     * @return +
     */
    protected List<EMConversation> loadConversationList() {
        // 获取所有会话，包括陌生人
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
        // 过滤掉messages size为0的conversation
        /**
         * 如果在排序过程中有新消息收到，lastMsgTime会发生变化
         * 影响排序过程，Collection.sort会产生异常
         * 保证Conversation在Sort过程中最后一条消息的时间不变
         * 避免并发问题
         */
        List<Pair<Long, EMConversation>> sortList = new ArrayList<Pair<Long, EMConversation>>();
        synchronized (conversations) {
            for (EMConversation conversation : conversations.values()) {
                if (conversation.getAllMessages().size() != 0) {
                    //if(conversation.getType() != EMConversationType.ChatRoom){
                    sortList.add(new Pair<Long, EMConversation>(conversation.getLastMessage().getMsgTime(), conversation));
                    //}
                }
            }
        }
        try {
            // Internal is TimSort algorithm, has bug
            sortConversationByLastChatTime(sortList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<EMConversation> list = new ArrayList<EMConversation>();
        for (Pair<Long, EMConversation> sortItem : sortList) {
            list.add(sortItem.second);
        }
        List<EMConversation> temp = new ArrayList<>();
        for (EMConversation conversation : list) {
            if (conversation.getType() == EMConversation.EMConversationType.Chat) {
                String field = conversation.getExtField();
                if (!TextUtils.isEmpty(field) && field.startsWith("true")) {
                    temp.add(conversation);
                }
            }
        }
        list.removeAll(temp);
        list.addAll(0, temp);
        return list;
    }

    /**
     * 根据最后一条消息的时间排序
     */
    private void sortConversationByLastChatTime(List<Pair<Long, EMConversation>> conversationList) {
        Collections.sort(conversationList, new Comparator<Pair<Long, EMConversation>>() {
            @Override
            public int compare(final Pair<Long, EMConversation> con1, final Pair<Long, EMConversation> con2) {

                if (con1.first == con2.first) {
                    return 0;
                } else if (con2.first > con1.first) {
                    return 1;
                } else {
                    return -1;
                }
            }

        });
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        Log.i("YCS", "创建一个menu");

        getActivity().getMenuInflater().inflate(R.menu.em_delete_message, menu);
    }

    @Override
    public void onStart() {
        super.onStart();
        EMClient.getInstance().chatManager().addMessageListener(messageListener);
        refresh();
//        ((MessageFragment) getParentFragment()).updateChatRedDot();
    }

    @Override
    public void onStop() {
        super.onStop();
        EMClient.getInstance().chatManager().removeMessageListener(messageListener);
    }

    private EMMessageListener messageListener = new EMMessageListener() {
        @Override
        public void onMessageReceived(List<EMMessage> list) {
            //收到消息
            refresh();
//            ((MessageFragment) getParentFragment()).updateChatRedDot();
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> list) {

        }

        @Override
        public void onMessageReadAckReceived(List<EMMessage> list) {

        }

        @Override
        public void onMessageDeliveryAckReceived(List<EMMessage> list) {

        }

        @Override
        public void onMessageChanged(EMMessage emMessage, Object o) {

        }
    };

    public void refresh() {
        mHandler.sendEmptyMessage(1);
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message message) {
            request();
        }
    };
}
