package com.powerge.wise.powerge.operationProjo.net.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.powerge.wise.powerge.operationProjo.net.net.response.FindWonhInfoResponse;
import com.wisesignsoft.OperationManagement.R;

import java.util.List;

/**
 * Created by ycs on 2017/1/7.
 */

public class WonhInfoAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<FindWonhInfoResponse.WonhListBean> datas;
    public WonhInfoAdapter(Context context, List<FindWonhInfoResponse.WonhListBean> datas){
        this.context = context;
        this.datas = datas;
    }
    public static class MyHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        TextView tv_user;
        TextView tv_line;
        TextView tv_time;
        public MyHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_wonh_name);
            tv_user = (TextView) itemView.findViewById(R.id.tv_wonh_user);
            tv_line = (TextView) itemView.findViewById(R.id.tv_wonh_line);
            tv_time = (TextView) itemView.findViewById(R.id.tv_wonh_time);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_wonh_info,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MyHolder){
            FindWonhInfoResponse.WonhListBean bean = datas.get(position);
            String name = bean.getNodeName();
            String user = bean.getNodeUser();
            String line = bean.getNodeLineName();
            String time = bean.getNodeOpTime();
            if(!TextUtils.isEmpty(name)){
                ((MyHolder) holder).tv_name.setText(name);
            }
            if(!TextUtils.isEmpty(user)){
                ((MyHolder) holder).tv_user.setText(user);
            }
            if(!TextUtils.isEmpty(line)){
                ((MyHolder) holder).tv_line.setText(line);
            }
            if(!TextUtils.isEmpty(time)){
                ((MyHolder) holder).tv_time.setText(time);
            }
        }
    }

    @Override
    public int getItemCount() {
        return (datas == null || datas.size()==0)?0:datas.size();
    }
}
