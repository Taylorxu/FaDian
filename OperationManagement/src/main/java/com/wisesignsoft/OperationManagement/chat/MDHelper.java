package com.wisesignsoft.OperationManagement.chat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.domain.EaseEmojicon;
import com.hyphenate.easeui.domain.EaseEmojiconGroupEntity;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.model.EaseAtMessageHelper;
import com.hyphenate.easeui.model.EaseNotifier;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.wisesignsoft.OperationManagement.bean.User;
import com.wisesignsoft.OperationManagement.db.MySharedpreferences;
import com.wisesignsoft.OperationManagement.db.UserDataManager;
import com.wisesignsoft.OperationManagement.ui.activity.ChatActivity;

import java.util.List;
import java.util.Map;

/**
 * Created by ycs on 2016/8/4.
 */
public class MDHelper {
    private static MDHelper instance;
    private Context appContext;
    private EaseUI easeUI;
    private LocalBroadcastManager broadcastManager;
    public boolean isVoiceCalling;
    public boolean isVideoCalling;
    private CallReceiver callReceiver;

    private MDHelper() {

    }
    public boolean isLoggedIn() {
        return EMClient.getInstance().isLoggedInBefore();
    }

    public synchronized static MDHelper getInstance() {

        if (instance == null) {
            instance = new MDHelper();
        }
        return instance;
    }

    public void init(Context context) {
        EMOptions options = initChatOptions();
        easeUI = EaseUI.getInstance();
        if (easeUI.init(context, options)) {
            appContext = context;
            //设为调试模式，打成正式包时，最好设为false，以免消耗额外的资源
            EMClient.getInstance().setDebugMode(true);
            //设置全局监听
            registerEventListener();
            broadcastManager = LocalBroadcastManager.getInstance(appContext);
            IntentFilter callFilter = new IntentFilter(EMClient.getInstance().callManager().getIncomingCallBroadcastAction());
            if(callReceiver == null){
                callReceiver = new CallReceiver();
            }
            appContext.registerReceiver(callReceiver, callFilter);
            easeUI.getNotifier().setNotificationInfoProvider(new EaseNotifier.EaseNotificationInfoProvider() {

                @Override
                public String getTitle(EMMessage message) {
                    //you can update title here
                    return null;
                }

                @Override
                public int getSmallIcon(EMMessage message) {
                    //you can update icon here
                    return 0;
                }

                @Override
                public String getDisplayedText(EMMessage message) {
                    // be used on notification bar, different text according the message type.
                    String ticker = EaseCommonUtils.getMessageDigest(message, appContext);
                    if(message.getType() == EMMessage.Type.TXT){
                        ticker = ticker.replaceAll("\\[.{2,3}\\]", "[表情]");
                    }
//                    EaseUser user = getUserInfo(message.getFrom());
//                    if(user != null){
//                        if(EaseAtMessageHelper.get().isAtMeMsg(message)){
//                            return String.format(appContext.getString(R.string.at_your_in_group), user.getNick());
//                        }
//                        return user.getNick() + ": " + ticker;
//                    }else{
//                        if(EaseAtMessageHelper.get().isAtMeMsg(message)){
//                            return String.format(appContext.getString(R.string.at_your_in_group), message.getFrom());
//                        }
//                        return message.getFrom() + ": " + ticker;
//                    }
                    return message.getFrom();
                }

                @Override
                public String getLatestText(EMMessage message, int fromUsersNum, int messageNum) {
                    // here you can customize the text.
                    // return fromUsersNum + "contacts send " + messageNum + "messages to you";
                    return null;
                }

                @Override
                public Intent getLaunchIntent(EMMessage message) {
                    // you can set what activity you want display when user click the notification
                    Intent intent = new Intent(appContext, ChatActivity.class);
                    // open calling activity if there is call
                    if(isVideoCalling){
                        intent = new Intent(appContext, VideoCallActivity.class);
                    }else if(isVoiceCalling){
                        intent = new Intent(appContext, VoiceCallActivity.class);
                    }else{
                        EMMessage.ChatType chatType = message.getChatType();
                        if (chatType == EMMessage.ChatType.Chat) { // single chat message
                            intent.putExtra("userId", message.getFrom());
                            intent.putExtra("chatType", EaseConstant.CHATTYPE_SINGLE);
                        } else { // group chat message
                            // message.getTo() is the group id
                            intent.putExtra("userId", message.getTo());
                            if(chatType == EMMessage.ChatType.GroupChat){
                                intent.putExtra("chatType", EaseConstant.CHATTYPE_GROUP);
                            }else{
                                intent.putExtra("chatType", EaseConstant.CHATTYPE_CHATROOM);
                            }

                        }
                    }
                    return intent;
                }
            });
            //设置表情provider
            easeUI.setEmojiconInfoProvider(new EaseUI.EaseEmojiconInfoProvider() {

                @Override
                public EaseEmojicon getEmojiconInfo(String emojiconIdentityCode) {
                    EaseEmojiconGroupEntity data = EmojiconExampleGroupData.getData();
                    for (EaseEmojicon emojicon : data.getEmojiconList()) {
                        if (emojicon.getIdentityCode().equals(emojiconIdentityCode)) {
                            return emojicon;
                        }
                    }
                    return null;
                }

                @Override
                public Map<String, Object> getTextEmojiconMapping() {
                    //返回文字表情emoji文本和图片(resource id或者本地路径)的映射map
                    return null;
                }
            });
            /*设置用户信息*/
            easeUI.setUserProfileProvider(new EaseUI.EaseUserProfileProvider() {

                @Override
                public EaseUser getUser(String username) {
                    return getUserInfo(username);
                }
            });
        }
    }
    private EaseUser getUserInfo(String username) {
        //获取user信息，demo是从内存的好友列表里获取，
        //实际开发中，可能还需要从服务器获取用户信息,
        //从服务器获取的数据，最好缓存起来，避免频繁的网络请求
        EaseUser user = new EaseUser(username);
        Log.i("YCS", "getUserInfo: username：" + username + "##user:" + EMClient.getInstance().getCurrentUser());
        if (username.equals(EMClient.getInstance().getCurrentUser())) {
            user.setNick(MySharedpreferences.getUser().getUsername());
            return user;
        } else {
            user.setNick(UserDataManager.getManager().getUser(username));
        }
        return user;
    }
    private EMOptions initChatOptions() {
        // 获取到EMChatOptions对象
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        // 设置是否需要已读回执
        options.setRequireAck(true);
        // 设置是否需要已送达回执
        options.setRequireDeliveryAck(false);
        return options;
    }

    EMMessageListener messageListener;

    /**
     * 全局事件监听
     * 因为可能会有UI页面先处理到这个消息，所以一般如果UI页面已经处理，这里就不需要再次处理
     * activityList.size() <= 0 意味着所有页面都已经在后台运行，或者已经离开Activity Stack
     */
    protected void registerEventListener() {
        messageListener = new EMMessageListener() {
            private BroadcastReceiver broadCastReceiver = null;

            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                for (EMMessage message : messages) {
                    //应用在后台，不需要刷新UI,通知栏提示新消息
                    String username = null;
                    // group message
                    if (message.getChatType() == EMMessage.ChatType.GroupChat || message.getChatType() == EMMessage.ChatType.ChatRoom) {
                        username = message.getTo();
                    } else {
                        username = message.getFrom();
                    }
                    UserDataManager.getManager().setUser(username);
                    getNotifier().onNewMsg(message);
                }
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {

            }

            @Override
            public void onMessageReadAckReceived(List<EMMessage> messages) {
            }

            @Override
            public void onMessageDeliveryAckReceived(List<EMMessage> message) {
            }

            @Override
            public void onMessageChanged(EMMessage message, Object change) {

            }
        };

        EMClient.getInstance().chatManager().addMessageListener(messageListener);
    }

    /**
     * 获取消息通知类
     *
     * @return
     */
    public EaseNotifier getNotifier() {
        return easeUI.getNotifier();
    }
}
