package com.wisesignsoft.OperationManagement.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.utils.EaseSmileUtils;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.wisesignsoft.OperationManagement.R;
import com.wisesignsoft.OperationManagement.db.UserDataManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by ycs on 2016/12/7.
 */

public class MessageAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<EMConversation> datas;
    private IMessageAdapter iMessageAdapter;

    public MessageAdapter(Context context,List<EMConversation> datas){
        this.context = context;
        this.datas = datas;
    }

    private static class MyHolder extends RecyclerView.ViewHolder {
        TextView tv_chat_red;
        TextView tv_chat_name;
        TextView tv_chat_content;
        TextView tv_chat_time;

        public MyHolder(View itemView) {
            super(itemView);
            tv_chat_red = (TextView) itemView.findViewById(R.id.tv_chat_red);
            tv_chat_name = (TextView) itemView.findViewById(R.id.tv_chat_name);
            tv_chat_content = (TextView) itemView.findViewById(R.id.tv_chat_content);
            tv_chat_time = (TextView) itemView.findViewById(R.id.tv_chat_time);
        }
    }
    public interface IMessageAdapter{
        void setIMessageClick(int position);
        void setOnLongClick(int position);
    }
    public void setIMessageAdapter(IMessageAdapter iMessageAdapter){
        this.iMessageAdapter = iMessageAdapter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_message, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof MyHolder){
            EMConversation bean = datas.get(position);
            String username = bean.getUserName();
            EaseUserUtils.setUserNick(username,((MyHolder) holder).tv_chat_name);
//            if ("admin".equals(username)) {
//                ((MyHolder) holder).tv_chat_name.setText("系统消息");
//            }else {
//
//            }
            int count = bean.getUnreadMsgCount();
            if(count>0){
                ((MyHolder) holder).tv_chat_red.setVisibility(View.VISIBLE);
                ((MyHolder) holder).tv_chat_red.setText(count+"");
            }else {
                ((MyHolder) holder).tv_chat_red.setVisibility(View.GONE);
            }
            if (bean.getAllMsgCount() != 0) {
                // 把最后一条消息的内容作为item的message内容
                EMMessage lastMessage = bean.getLastMessage();
                String content = null;
                ((MyHolder) holder).tv_chat_content.setText(EaseSmileUtils.getSmiledText(context, EaseCommonUtils.getMessageDigest(lastMessage, (context))),
                        TextView.BufferType.SPANNABLE);
                if (content != null) {
                    ((MyHolder) holder).tv_chat_content.setText(content);
                }
                long time = lastMessage.getMsgTime();
                SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                //前面的lSysTime是秒数，先乘1000得到毫秒数，再转为java.util.Date类型
                java.util.Date dt = new Date(time);
                String sDateTime = sdf.format(dt);
                ((MyHolder) holder).tv_chat_time.setText(sDateTime);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iMessageAdapter.setIMessageClick(position);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    iMessageAdapter.setOnLongClick(position);
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        Log.i("YCS", "getItemCount: size:"+datas.size());
        return (datas == null || datas.size() == 0) ? 0 : datas.size();
    }
}
