package com.powerge.wise.basestone.heart.ui;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by XZG on 2018/2/9.
 */

public abstract class XAdapter<Data,Binding extends ViewDataBinding> extends RecyclerView.Adapter<XViewHolder<Data,Binding>>{
    List<Data> list;

    public XAdapter() {

    }

    @Override
    public XViewHolder<Data, Binding> onCreateViewHolder(ViewGroup parent, int viewType) {
        XViewHolder<Data, Binding> viewHolder=new XViewHolder<>(LayoutInflater.from(parent.getContext()).inflate(holderLayout(viewType),parent,false));
        initHolder(viewHolder,viewType);
        return viewHolder;
    }


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    protected void initHolder(XViewHolder<Data, Binding> holder, int viewType) {}

    public abstract int holderLayout(int viewType);
    public void setList(List<Data> data){
        this.list=data;
        notifyDataSetChanged();
    }



    public static class SimpleAdapter<Data, Binding extends ViewDataBinding> extends XAdapter<Data, Binding> {
        int holderLayout;
        int variableId;

        public SimpleAdapter(int variableId, int holderLayout) {
            this.variableId=variableId;
            this.holderLayout=holderLayout;
        }


        @Override
        public void onBindViewHolder(XViewHolder<Data, Binding> holder, int position) {
            holder.fill(variableId, list.get(position));
        }

        @Override
        public int holderLayout(int viewType) {
            return holderLayout;
        }
    }


}