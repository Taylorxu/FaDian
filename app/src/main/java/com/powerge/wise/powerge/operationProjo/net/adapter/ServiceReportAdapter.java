package com.powerge.wise.powerge.operationProjo.net.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.operationProjo.net.ui.activity.ServiceReportActivity;

import java.util.List;
import java.util.Map;

/**
 * Created by ycs on 2016/11/24.
 */

public class ServiceReportAdapter extends RecyclerView.Adapter {
    private Context context;
    private IServiceReport iServiceReport;
    private List<Map<String,String>> datas;

    public ServiceReportAdapter(Context context,List<Map<String, String>> datas) {
        this.context = context;
        this.datas = datas;
    }

    private static class MyHolder extends RecyclerView.ViewHolder {
        TextView tv_service_name;
        TextView tv_service_identity;
        TextView tv_service_time;

        public MyHolder(View itemView) {
            super(itemView);
            tv_service_name = (TextView) itemView.findViewById(R.id.tv_item_service_name);
            tv_service_identity = (TextView) itemView.findViewById(R.id.tv_item_service_identity);
            tv_service_time = (TextView) itemView.findViewById(R.id.tv_item_service_time);
        }
    }
    public interface IServiceReport{
        void setOnClick(int position);
    }
    public void setOnIServiceReport(IServiceReport iServiceReport){
        this.iServiceReport = iServiceReport;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_service_report, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof MyHolder){
            Map<String, String> bean = datas.get(position);
            String key = ((ServiceReportActivity)context).getKey();
            String name = bean.get(key);
            String content = bean.get("RES_RENA") + "(" + bean.get("RES_UPP") + ")";
            String time = bean.get("RES_UPT");
            if (!TextUtils.isEmpty(name)) {
                ((MyHolder) holder).tv_service_name.setText(name);
            }else {
                ((MyHolder) holder).tv_service_name.setText("无标题");
            }
            ((MyHolder) holder).tv_service_identity.setText(content);
            ((MyHolder) holder).tv_service_time.setText(time);
//            String name = "";
//            String no="";
//            String createdate = "";
//            final Map<String, String> bean = datas.get(position);
//            Set beanSet = bean.keySet();
//            Iterator<String> iterable = beanSet.iterator();
//            while (iterable.hasNext()) {
//                String next = iterable.next();
//                String value = bean.get(next);
//                if ("RES_RENA".equals(next)) {
//                    name=value;
//                } else if ("RES_UPP".equals(next)) {
//                    no=value;
//                } else if ("RES_UPT".equals(next)) {
//                    createdate=value;
//                }
//            }
//            ((MyHolder) holder).tv_service_name.setText(name);
//            ((MyHolder) holder).tv_service_identity.setText(no);
//            ((MyHolder) holder).tv_service_time.setText(createdate);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iServiceReport.setOnClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return (datas == null||datas.size() == 0)?0:datas.size();
    }
}
