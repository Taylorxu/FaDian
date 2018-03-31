package com.powerge.wise.powerge.operationProjo.net.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.powerge.wise.powerge.MyApplication;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.operationProjo.net.BaseActivity;
import com.powerge.wise.powerge.operationProjo.net.adapter.AttachmentViewAdapter;
import com.powerge.wise.powerge.operationProjo.net.bean.WorkOrder;
import com.powerge.wise.powerge.operationProjo.net.net.RequestTask;
import com.powerge.wise.powerge.operationProjo.net.net.request.RequestAttachment;
import com.powerge.wise.powerge.operationProjo.net.net.request.RequestKnow;
import com.powerge.wise.powerge.operationProjo.net.net.response.FindAttachmentInfoResponse;
import com.powerge.wise.powerge.operationProjo.net.net.response.PreviewOnlineResponse;
import com.powerge.wise.powerge.operationProjo.net.runtimepermissions.PermissionListener;
import com.powerge.wise.powerge.operationProjo.net.runtimepermissions.PermissionOption;
import com.powerge.wise.powerge.operationProjo.net.runtimepermissions.PermissionUtil;
import com.powerge.wise.powerge.operationProjo.net.utils.StringUtils;
import com.powerge.wise.powerge.operationProjo.net.view.mview.LoadingView;
import com.powerge.wise.powerge.operationProjo.net.view.mview.WorkOrderDataManager;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ycs on 2016/12/5.
 */

public class AttachmentView extends LinearLayout implements AttachmentViewAdapter.IAttachmentListener {

    private TextView tv_attachment_view;
    private RecyclerView rv_attachment_view;
    private BaseActivity activity;
    private static final int IMAGE_PICKER = 1;
    private WorkOrder wo;
    private List<ImageItem> datas = new ArrayList<>();
    private AttachmentViewAdapter attachmentViewAdapter;
    private LoadingView loadingView;

    public AttachmentView(Context context, BaseActivity activity) {
        super(context);
        this.activity = activity;
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.attachment_view, this, true);
        tv_attachment_view = (TextView) view.findViewById(R.id.tv_attachment_view);
        rv_attachment_view = (RecyclerView) view.findViewById(R.id.rv_attachment_view);

        RecyclerView.LayoutManager manager = new GridLayoutManager(context, 4);
        rv_attachment_view.setLayoutManager(manager);
        attachmentViewAdapter = new AttachmentViewAdapter(getContext(), datas, activity);
        rv_attachment_view.setAdapter(attachmentViewAdapter);
        attachmentViewAdapter.setOnIAttachmentClickListener(this);
        loadingView = LoadingView.getLoadingView(context);
    }

    public void setData(final WorkOrder wo) {
        if (datas != null) {
            datas.clear();
        }
        this.wo = wo;
        attachmentViewAdapter.setWorkOrder(wo);
        String title = wo.getName();
        if (!TextUtils.isEmpty(title)) {
            if (wo.isRequired()) {
                tv_attachment_view.setText(title + "上传(上传文件不能超过50MB)*");
            } else {
                tv_attachment_view.setText(title+"上传(上传文件不能超过50MB)");
            }
        }
        String value = wo.getValue();
        if(WorkOrderDataManager.getManager().getAdapterIds().get(wo.getID()) != null){
            value = WorkOrderDataManager.getManager().getAdapterIds().get(wo.getID());
        }
        if(!TextUtils.isEmpty(value)){
            List<String> list = new ArrayList<>();
            list.add(value);
            RequestKnow.findAttachmentInfo(list, new RequestTask.ResultCallback<FindAttachmentInfoResponse>() {
                @Override
                public void onError(final Exception e) {
                    Log.i("liang", "onResponse: 失败");
                }

                @Override
                public void onResponse(final FindAttachmentInfoResponse response) {
                    Log.i("liang", "onResponse: 成功");
                    List<FindAttachmentInfoResponse.ResultBean> attrList = response.getResult();
                    for (FindAttachmentInfoResponse.ResultBean resultBean : attrList){
                        ImageItem imageItem = new ImageItem();
                        imageItem.id = resultBean.getId();
                        imageItem.preview = resultBean.getPreview();
                        imageItem.name = resultBean.getName();
                        switch (resultBean.getExt()){
                            case "doc":
                            case "docx":
                            case "docm":
                            case "dotx":
                            case "dotm":
                                imageItem.imageId = R.mipmap.file_doc;
                                break;
                            case "xls":
                            case "xlsx":
                            case "xlsm":
                            case "xltx":
                            case "xltm":
                            case "xlsb":
                            case "xlam":
                                imageItem.imageId = R.mipmap.file_xls;
                                break;
                            case "ppt":
                            case "pptx":
                            case "pptm":
                            case "ppsx":
                            case "ppsm":
                            case "potx":
                            case "potm":
                                imageItem.imageId = R.mipmap.file_ppt;
                                break;
                            case "txt":
                                imageItem.imageId = R.mipmap.file_txt;
                                break;
                            case "htm":
                            case "html":
                                imageItem.imageId = R.mipmap.file_htm;
                                break;
                            case "pdf":
                                imageItem.imageId = R.mipmap.file_pdf;
                                break;
                            case "jpg":
                                imageItem.imageId = R.mipmap.file_jpg;
                                break;
                            case "png":
                                imageItem.imageId = R.mipmap.file_png;
                                break;
                            default:
                                imageItem.imageId = R.mipmap.file_all;
                                break;
                        }
                        datas.add(imageItem);
                    }
                    List<ImageItem> localImage = wo.getImags();
                    if (localImage != null) {
                        datas.addAll(localImage);
                    }
                    attachmentViewAdapter.setClick(wo.isModified());
                    attachmentViewAdapter.notifyDataSetChanged();
                }
            });
        }else {
            List<ImageItem> localImage = wo.getImags();
            if (localImage != null) {
                datas.addAll(localImage);
            }
            attachmentViewAdapter.setClick(wo.isModified());
            attachmentViewAdapter.notifyDataSetChanged();
        }
    }

    private String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION
            , Manifest.permission.WRITE_EXTERNAL_STORAGE
            , Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO};
    @Override
    public void setOnAdd() {
        if (!wo.isModified()) {
            return;
        }
        PermissionUtil.getInstance(activity).request(new PermissionOption.Builder()
                        .setPermissions(permission)
                        .build()
                , new PermissionListener() {
                    @Override
                    public void onGranted() {
                        tanKuang();
                    }

                    @Override
                    public void onDenied(List<String> permissions) {
                    }
                });

    }

    @Override
    public void setOnLoad(ImageItem imageItem) {
        if (!TextUtils.isEmpty(imageItem.preview)) {
            openUrl(imageItem.preview);
        }else {
            loadingView.show();
            List<String> list = new ArrayList<>();
            list.add(imageItem.id);
            RequestAttachment.previewOnline(list, new RequestTask.ResultCallback<PreviewOnlineResponse>() {
                @Override
                public void onError(final Exception e) {
                    Log.i("liang", "onResponse: 失败");
                    loadingView.stop(loadingView);
                }

                @Override
                public void onResponse(final PreviewOnlineResponse response) {
                    Log.i("liang", "onResponse: 成功");
                    loadingView.stop(loadingView);
                    if(response == null){
                        Intent intent = new Intent();
                        intent.setAction("operationerror");
                        intent.putExtra("error_code", 500);
                        intent.putExtra("msg", "服务器繁忙");
                        MyApplication.getContext().sendBroadcast(intent);
                    }else if(StringUtils.isNotBlank(response.getPromptMsg())){
                        Intent intent = new Intent();
                        intent.setAction("operationerror");
                        intent.putExtra("error_code", 500);
                        intent.putExtra("msg", response.getPromptMsg());
                        MyApplication.getContext().sendBroadcast(intent);
                    }else {
                        openUrl(response.getPreViewUrl());
                    }
                }
            });
        }
    }

    private void openUrl(String url){
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri newsurl = Uri.parse(url);
        intent.setData(newsurl);
        getContext().startActivity(intent);
    }

    private void tanKuang() {
        Intent intent = new Intent(activity, ImageGridActivity.class);
        intent.putExtra("id", wo.getID());
        activity.startActivityForResult(intent, IMAGE_PICKER);
    }
}
