package com.powerge.wise.powerge.operationProjo.net.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.wisesignsoft.OperationManagement.R;

import java.util.List;

/**
 * Created by ycs on 2016/11/25.
 */

public class CheckBoxDialogAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<String> list;

    public CheckBoxDialogAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    private static class MyHolder extends RecyclerView.ViewHolder {
        TextView tv_check;
        CheckBox cb_check;

        public MyHolder(View itemView) {
            super(itemView);
            tv_check = (TextView) itemView.findViewById(R.id.tv_item_check_box_dialog);
            cb_check = (CheckBox) itemView.findViewById(R.id.cb_item_check_box_dialog);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_check_box_dialog, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyHolder mHolder = (MyHolder) holder;
        mHolder.tv_check.setText(list.get(position));
        mHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
