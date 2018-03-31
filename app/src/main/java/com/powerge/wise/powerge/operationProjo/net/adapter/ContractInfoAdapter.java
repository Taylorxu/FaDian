package com.powerge.wise.powerge.operationProjo.net.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.powerge.wise.powerge.operationProjo.net.ui.activity.ContractInfoActivity;
import com.wisesignsoft.OperationManagement.R;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by ycs on 2016/12/7.
 */

public class ContractInfoAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<Map<String, String>> datas;
    private IContractInfo iContractInfo;

    public ContractInfoAdapter(Context context, List<Map<String, String>> datas) {
        this.context = context;
        this.datas = datas;
    }

    private static class MyHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        TextView tv_content;
        TextView tv_time;

        public MyHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_item_contract_info_title);
            tv_content = (TextView) itemView.findViewById(R.id.tv_item_contract_info_content);
            tv_time = (TextView) itemView.findViewById(R.id.tv_item_contract_info_time);
        }
    }

    public interface IContractInfo {
        void setOnClick(int position);
    }

    public void setOnIContractInfo(IContractInfo iContractInfo) {
        this.iContractInfo = iContractInfo;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_contract_info, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MyHolder) {
            Map<String, String> bean = datas.get(position);
            String key = ((ContractInfoActivity) context).getKey();
            if (TextUtils.isEmpty(key)) {
                String proj_name = "";
                String proj_no = "";
                String createdate = "";
                String cust_name = "";
                String cust_no = "";
                Set beanSet = bean.keySet();
                Iterator<String> iterable = beanSet.iterator();
                while (iterable.hasNext()) {
                    String next = iterable.next();
                    String value = bean.get(next);
                    if ("PROJ_NAME".equals(next)) {
                        proj_name = value;
                    } else if ("PROJ_NO".equals(next)) {
                        proj_no = value;
                    } else if ("CREATEDATE".equals(next)) {
                        createdate = value;
                    } else if ("CUST_NAME".equals(next)) {
                        cust_name = value;
                    } else if ("CUST_NO".equals(next)) {
                        cust_no = value;
                    }
                }
                ((MyHolder) holder).tv_title.setText(proj_name + "(" + proj_no + ")");
                ((MyHolder) holder).tv_content.setText(cust_name + "(" + cust_no + ")");
                ((MyHolder) holder).tv_time.setText(createdate);
            } else {
                String name = bean.get(key);
                String content = bean.get("CUST_NAME") + "(" + bean.get("CUST_NO") + ")";
                String time = bean.get("CREATEDATE");
                if (!TextUtils.isEmpty(name)) {
                    ((MyHolder) holder).tv_title.setText(name);
                } else {
                    ((MyHolder) holder).tv_title.setText("无标题");
                }
                ((MyHolder) holder).tv_content.setText(content);
                ((MyHolder) holder).tv_time.setText(time);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iContractInfo.setOnClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return (datas == null || datas.size() == 0) ? 0 : datas.size();
    }
}
