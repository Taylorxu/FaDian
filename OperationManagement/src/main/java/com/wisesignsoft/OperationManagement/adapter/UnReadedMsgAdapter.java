package com.wisesignsoft.OperationManagement.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wisesignsoft.OperationManagement.R;
import com.wisesignsoft.OperationManagement.net.response.FindUnReadedMsgResponse;

import java.util.List;

/**
 * Created by ycs on 2016/12/8.
 */

public class UnReadedMsgAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<FindUnReadedMsgResponse.UnReadedMsg> datas;
    private IUnReadedMsg iUnReadedMsg;
    public UnReadedMsgAdapter(Context context, List<FindUnReadedMsgResponse.UnReadedMsg> datas){
        this.context = context;
        this.datas = datas;
    }
    public interface IUnReadedMsg{
        void setIUnReadedMsg(FindUnReadedMsgResponse.UnReadedMsg msg);
    }
    public void setIUnReadedMsg(IUnReadedMsg iUnReadedMsg){
        this.iUnReadedMsg = iUnReadedMsg;
    }
    private static class MyHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        TextView tv_content;
        TextView tv_time;
        public MyHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_item_unreaded_name);
            tv_content = (TextView) itemView.findViewById(R.id.tv_item_unreaded_content);
            tv_time = (TextView) itemView.findViewById(R.id.tv_item_unreaded_time);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_unreaded_msg,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MyHolder){
            final FindUnReadedMsgResponse.UnReadedMsg msg = datas.get(position);
            ((MyHolder) holder).tv_title.setText(msg.getUserName());
            ((MyHolder) holder).tv_content.setText(msg.getTitle());
            ((MyHolder) holder).tv_time.setText(msg.getCreatetime());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iUnReadedMsg.setIUnReadedMsg(msg);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return (datas == null||datas.size() == 0)?0:datas.size();
    }
}
