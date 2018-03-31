package com.powerge.wise.powerge.operationProjo.net.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.operationProjo.net.net.response.FindAllDeptTreeResponse;
import com.powerge.wise.powerge.operationProjo.net.ui.activity.AllDeptTreeActivity;
import com.powerge.wise.powerge.operationProjo.net.view.TempTreeSelectionDataManager;
import com.powerge.wise.powerge.operationProjo.net.view.mview.NextView;

import java.util.List;

/**
 * Created by ycs on 2016/12/15.
 */

public class AllDeptTreeAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<FindAllDeptTreeResponse.Children> datas;
    private String id;
    public AllDeptTreeAdapter(Context context, List<FindAllDeptTreeResponse.Children> datas,String id){
        this.context = context;
        this.datas = datas;
        this.id = id;
    }
    private class MyHolder extends RecyclerView.ViewHolder {
        NextView tv;
        public MyHolder(View itemView) {
            super(itemView);
            tv = (NextView) itemView.findViewById(R.id.nv_item_all_dept_tree);
        }
    }
    public interface IAllDeptTreeListener{
        void setOnIAllDeptTreeClick(String id);
    }
    private IAllDeptTreeListener listener;
    public void setOnIClick(IAllDeptTreeListener listener){
        this.listener = listener;;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_all_dapt_tree,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyHolder) {
            final FindAllDeptTreeResponse.Children model = datas.get(position);
            ((MyHolder) holder).tv.setData(model.getDeptFullName());
            if (model.getChildren() == null || model.getChildren().size() == 0) {
                ((MyHolder) holder).tv.setIV(false);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String temp = model.getDeptFullName();
                        TempTreeSelectionDataManager.getManager().setTemp(temp);
                        TempTreeSelectionDataManager.getManager().clearAllDeptTreeActivity();
                        listener.setOnIAllDeptTreeClick(model.getDeptId());
                    }
                });
            } else {
                ((MyHolder) holder).tv.setIV(true);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        datas = model.getChildren();
                        AllDeptTreeActivity.startSelf(context, datas,id);
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return (datas == null || datas.size() == 0) ? 0 : datas.size();
    }
}
