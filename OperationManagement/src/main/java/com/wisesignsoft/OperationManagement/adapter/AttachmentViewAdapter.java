package com.wisesignsoft.OperationManagement.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lzy.imagepicker.bean.ImageItem;
import com.wisesignsoft.OperationManagement.BaseActivity;
import com.wisesignsoft.OperationManagement.R;
import com.wisesignsoft.OperationManagement.bean.WorkOrder;
import com.wisesignsoft.OperationManagement.view.mview.WorkOrderDataManager;

import java.util.List;

/**
 * Created by ycs on 2016/12/5.
 */

public class AttachmentViewAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<ImageItem> list;
    private Activity activity;
    private static final int TYPE1 = 0;
    private static final int TYPE2 = 1;
    private boolean isClick;
    private IAttachmentListener listener;

    private WorkOrder workOrder;

    public AttachmentViewAdapter(Context context, List<ImageItem> list, Activity activity) {
        this.context = context;
        this.list = list;
        this.activity = activity;
    }

    private static class MyHolder extends RecyclerView.ViewHolder {
        ImageView iv_attachment_view_content;
        ImageView iv_attachment_view_del;
        TextView tv_attachment_name;

        public MyHolder(View itemView) {
            super(itemView);
            iv_attachment_view_content = (ImageView) itemView.findViewById(R.id.iv_attachment_view_content);
            iv_attachment_view_del = (ImageView) itemView.findViewById(R.id.iv_attachment_view_del);
            tv_attachment_name = (TextView) itemView.findViewById(R.id.tv_attachment_name);
        }
    }

    private static class MyHolder2 extends RecyclerView.ViewHolder {
        ImageView iv_attachment_view;

        public MyHolder2(View itemView) {
            super(itemView);
            iv_attachment_view = (ImageView) itemView.findViewById(R.id.iv_attachment_view);
        }
    }

    public interface IAttachmentListener {
        void setOnAdd();

        void setOnLoad(ImageItem imageItem);
    }

    public void setOnIAttachmentClickListener(IAttachmentListener listener) {
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE1) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_attachment_view2, parent, false);
            return new MyHolder2(view);
        }
        View view = LayoutInflater.from(context).inflate(R.layout.item_attachment_view, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MyHolder) {
            final ImageItem image = list.get(position);
            if(TextUtils.isEmpty(image.id)){
                String path = image.path;
                Glide.with(activity).load(path).into(((MyHolder) holder).iv_attachment_view_content);
            }else {
                Glide.with(activity).load(image.imageId).into(((MyHolder) holder).iv_attachment_view_content);
            }
            if(isClick){
                ((MyHolder) holder).iv_attachment_view_del.setVisibility(View.VISIBLE);
            }else {
                ((MyHolder) holder).iv_attachment_view_del.setVisibility(View.GONE);
            }
            ((MyHolder) holder).iv_attachment_view_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isClick) {
                        if(workOrder.getImags() != null){
                            ImageItem del = list.get(position);
                            for (ImageItem image : workOrder.getImags()){
                                if(image.equals(del)){
                                    workOrder.getImags().remove(del);
                                    break;
                                }
                            }
                        }
                        list.remove(position);
                        StringBuffer sb = new StringBuffer();
                        for (ImageItem item : list){
                            if(!TextUtils.isEmpty(item.id)){
                                sb.append(item.id + ",");
                            }
                        }
                        if(sb.length() > 0){
                            WorkOrderDataManager.getManager().getAdapterIds().put(workOrder.getID(), sb.substring(0, sb.length() - 1));
                        }else {
                            WorkOrderDataManager.getManager().getAdapterIds().put(workOrder.getID(), "");
                        }
                        notifyDataSetChanged();
                    }
                }
            });
            ((MyHolder) holder).iv_attachment_view_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(TextUtils.isEmpty(image.id)){
                        Toast.makeText(context, "本地图片不支持预览", Toast.LENGTH_SHORT).show();
                    }else {
                        listener.setOnLoad(image);
                    }
                }
            });
            ((MyHolder) holder).tv_attachment_name.setText(image.name);
        } else if (holder instanceof MyHolder2) {
            ((MyHolder2) holder).iv_attachment_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isClick) {
                        listener.setOnAdd();
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return (list == null || list.size() == 0) ? 1 : list.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == list.size()) {
            return TYPE1;
        }
        return TYPE2;
    }

    public void setClick(boolean isClick) {
        this.isClick = isClick;
    }
    public void setWorkOrder(WorkOrder wo){
        this.workOrder = wo;
    }
}
