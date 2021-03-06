package com.powerge.wise.powerge.operationProjo.net.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.operationProjo.net.bean.UserInfoBean;

import java.util.List;

/**
 * Created by ycs on 2016/12/16.
 */

public class SelectUserAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<UserInfoBean> datas;
    private ISelectUserClickListener listener;

    public SelectUserAdapter(Context context, List<UserInfoBean> datas) {
        this.context = context;
        this.datas = datas;
    }
    public interface ISelectUserClickListener{
        void setOnUserNameClickListener(String username,String name);
    }
    public void setOnISingleUserClickListener(ISelectUserClickListener listener){
        this.listener = listener;
    }

    private static class MyHolder extends RecyclerView.ViewHolder {
        TextView tv_single_user;
        public MyHolder(View itemView) {
            super(itemView);
            tv_single_user = (TextView) itemView.findViewById(R.id.tv_item_single_user);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_single_user, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MyHolder){
            final UserInfoBean bean = datas.get(position);
            ((MyHolder) holder).tv_single_user.setText(bean.getUserName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.setOnUserNameClickListener(bean.getUserAccountnum(),bean.getUserName());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return (datas == null || datas.size() == 0) ? 0 : datas.size();
    }
}
