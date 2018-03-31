package com.powerge.wise.powerge.operationProjo.net.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.powerge.wise.powerge.operationProjo.net.net.response.FindAllDeptTreeResponse;
import com.wisesignsoft.OperationManagement.R;

import java.util.List;

/**
 * Created by ycs on 2016/12/16.
 */

public class ContractFragmentAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<FindAllDeptTreeResponse.Children> datas;
    private IContractFragmentListener listener;

    public ContractFragmentAdapter(Context context, List<FindAllDeptTreeResponse.Children> datas) {
        this.context = context;
        this.datas = datas;
    }

    private static class MyHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        ImageView iv_drop;
        ImageView iv_entity;

        public MyHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_contract_fragment_name);
            iv_drop = (ImageView) itemView.findViewById(R.id.iv_fragment_contract_drop);
            iv_entity = (ImageView) itemView.findViewById(R.id.iv_fragment_contract_entity);
        }
    }
    public interface IContractFragmentListener{
        void setOnNameClickListener(String id);
        void setOnEntityClickListener(List<FindAllDeptTreeResponse.Children> datas);
    }
    public void setOnContractListener(IContractFragmentListener listener){
        this.listener = listener;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_fragment_contract, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyHolder) {
            final FindAllDeptTreeResponse.Children bean = datas.get(position);
            ((MyHolder) holder).tv_name.setText(bean.getDeptFullName());
            if(bean.getChildren() == null || bean.getChildren().size() == 0){
                ((MyHolder) holder).iv_entity.setVisibility(View.GONE);
            }else {
                ((MyHolder) holder).iv_entity.setVisibility(View.VISIBLE);
            }
            ((MyHolder) holder).iv_entity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.setOnEntityClickListener(bean.getChildren());
                }
            });
            ((MyHolder) holder).tv_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.setOnNameClickListener(bean.getDeptId());
                }
            });
            ((MyHolder) holder).iv_drop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.setOnNameClickListener(bean.getDeptId());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return (datas == null || datas.size() == 0) ? 0 : datas.size();
    }
}
