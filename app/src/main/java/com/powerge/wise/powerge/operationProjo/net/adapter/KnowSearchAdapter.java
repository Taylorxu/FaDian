package com.powerge.wise.powerge.operationProjo.net.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.powerge.wise.powerge.operationProjo.net.ui.activity.KnowSearchActivity;
import com.wisesignsoft.OperationManagement.R;

import java.util.List;
import java.util.Map;


/**
 * Created by ycs on 2016/11/24.
 */

public class KnowSearchAdapter extends RecyclerView.Adapter {
    private Context context;
    private IKnowReport iKnowReport;
    private List<Map<String,String>> datas;

    public KnowSearchAdapter(Context context, List<Map<String, String>> datas) {
        this.context = context;
        this.datas = datas;
    }

    private static class MyHolder extends RecyclerView.ViewHolder {
        TextView tv_know_name;
        TextView tv_know_time;

        public MyHolder(View itemView) {
            super(itemView);
            tv_know_name = (TextView) itemView.findViewById(R.id.tv_item_know_name);
            tv_know_time = (TextView) itemView.findViewById(R.id.tv_item_know_time);
        }
    }
    public interface IKnowReport{
        void setOnClick(int position);
    }
    public void setOnIKnowReport(IKnowReport iKnowReport){
        this.iKnowReport = iKnowReport;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_know, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof MyHolder){
            Map<String, String> bean = datas.get(position);
            String key = ((KnowSearchActivity)context).getKey();
            String name = bean.get(key);
            String time = bean.get("CREATEDATE");
            if (!TextUtils.isEmpty(name)) {
                ((MyHolder) holder).tv_know_name.setText(name);
            }else {
                ((MyHolder) holder).tv_know_name.setText("无标题");
            }
            ((MyHolder) holder).tv_know_time.setText(time);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iKnowReport.setOnClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        Log.i("YCS", "getItemCount: datas:"+datas.size());
        return (datas == null||datas.size() == 0)?0:datas.size();
    }
}
