package com.powerge.wise.powerge.operationProjo.net.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.operationProjo.net.ui.activity.SelectAccountActivity;
import com.powerge.wise.powerge.operationProjo.net.ui.activity.SelectAccountActivity4;

import java.util.List;
import java.util.Map;


/**
 * Created by ycs on 2016/12/7.
 */

public class SelectAccountAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<Map<String, String>> datas;
    private IContractInfo iContractInfo;

    public SelectAccountAdapter(Context context, List<Map<String, String>> datas) {
        this.context = context;
        this.datas = datas;
    }

    private static class MyHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        TextView tv_time;
        TextView tv_company;

        public MyHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_item_select_account_title);
            tv_time = (TextView) itemView.findViewById(R.id.tv_item_select_account_time);
            tv_company = (TextView) itemView.findViewById(R.id.tv_item_select_account_company);
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_select_account, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MyHolder) {
            Map<String, String> bean = datas.get(position);
            String key = "";
            String content = "";
            String time = "";
            try {
                key = ((SelectAccountActivity) context).getKey();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                key = ((SelectAccountActivity4) context).getKey();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!TextUtils.isEmpty(key)) {
                String name = bean.get(key);
                if (!TextUtils.isEmpty(name)) {
                    ((MyHolder) holder).tv_title.setText(name);
                } else {
                    ((MyHolder) holder).tv_title.setText("无标题");
                }
            } else {
                ((MyHolder) holder).tv_title.setText(bean.get("K_TITLE"));
            }
//            content = bean.get("CUST_NAME") + "(" + bean.get("CUST_NO") + ")";
            time = bean.get("CREATEDATE");
            ((MyHolder) holder).tv_company.setText(content);
            ((MyHolder) holder).tv_time.setText(time + "  最后更新");
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
