package com.powerge.wise.basestone.heart.ui;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by XZG on 2018/2/9.
 */

public class XViewHolder<Data,Binding extends ViewDataBinding> extends RecyclerView.ViewHolder {
    Binding binding;
    public XViewHolder(View itemView) {
        super(itemView);
        binding= DataBindingUtil.bind(itemView);
    }

    //为每个item中的BR 添加数据
    public void fill(int variableId, Data data){
        binding.setVariable(variableId,data);
    }
}
