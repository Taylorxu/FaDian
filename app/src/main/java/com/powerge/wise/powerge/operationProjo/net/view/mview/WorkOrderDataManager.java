package com.powerge.wise.powerge.operationProjo.net.view.mview;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.imagepicker.bean.ImageItem;
import com.powerge.wise.powerge.operationProjo.net.bean.BMForm;
import com.powerge.wise.powerge.operationProjo.net.bean.ButtonModel;
import com.powerge.wise.powerge.operationProjo.net.bean.ConditionJudgment;
import com.powerge.wise.powerge.operationProjo.net.bean.Dict;
import com.powerge.wise.powerge.operationProjo.net.bean.LinkBean;
import com.powerge.wise.powerge.operationProjo.net.bean.ResColumnsJsonBean;
import com.powerge.wise.powerge.operationProjo.net.bean.ResModeValue;
import com.powerge.wise.powerge.operationProjo.net.bean.Section;
import com.powerge.wise.powerge.operationProjo.net.bean.TaskStrategy;
import com.powerge.wise.powerge.operationProjo.net.bean.WorkOrder;
import com.powerge.wise.powerge.operationProjo.net.db.MySharedpreferences;
import com.powerge.wise.powerge.operationProjo.net.net.RequestTask;
import com.powerge.wise.powerge.operationProjo.net.net.request.RequestAttachment;
import com.powerge.wise.powerge.operationProjo.net.net.request.RequestBusiness;
import com.powerge.wise.powerge.operationProjo.net.net.response.LinkResponse;
import com.powerge.wise.powerge.operationProjo.net.net.response.UploadFileResponse;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by ycs on 2016/12/13.
 */

public class WorkOrderDataManager {
    private static WorkOrderDataManager manager;
    private Map<String, String> map = new HashMap<>();
    /*初始数据*/
    private List list = new ArrayList();
    /*初始控件*/
    private Map<String, View> views = new HashMap<>();
    /*控件id跟dmAttrName的对应关系*/
    private Map<String, String> views2 = new HashMap<>();
    List<String> ids = new ArrayList<>();
    /*一个附件上传的图片的角标*/
    private int position = 0;
    /*存储所有有附件的section的id*/
    private List<String> strs = new ArrayList<>();
    /*存储的整个工单的附件集合*/
    private List<List<ImageItem>> imageItems = new ArrayList<>();
    /*记录当前下载到那个section的附件*/
    private int index = 0;

    private Map<String, String> adapterIds = new HashMap<>();

    public Map<String, String> getAdapterIds(){
        return this.adapterIds;
    }

    private WorkOrderDataManager() {

    }

    public static synchronized WorkOrderDataManager getManager() {
        if (manager == null) {
            manager = new WorkOrderDataManager();
        }
        return manager;
    }

    /**
     * 存储初始的数据源和控件
     *
     * @param list
     */
    public void setDate(List list, Map<String, View> views, Map<String, String> views2) {
        this.list = list;
        this.views = views;
        this.views2 = views2;
    }

    /**
     * 清除数据源
     */
    public void clearDate() {
        manager = new WorkOrderDataManager();
    }

    /**
     * 获取数据源
     *
     * @return
     */
    public List getDate() {
        return list;
    }

    /**
     * 根据id查找控件，并赋值
     *
     * @param id
     * @param value
     */
    public void setSingleDateById(String id, String value) {
        BMForm bmForm = null;
        if (list == null || list.size() == 0) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {//section
            Object o = list.get(i);
            if (o instanceof Section) {
                List<WorkOrder> list = ((Section) o).getSection();
                for (WorkOrder wo : list) {
                    if (wo.getID().equals(id)) {
                        wo.setValue(value);
                        if (wo.isLink()) {
                            if (bmForm != null) {
                                setLink(wo, bmForm.getBmModelName());
                            }
                        }
                        break;
                    }
                }
            } else if (o instanceof ButtonModel) {
                String bt_id = ((ButtonModel) o).getID();
                if (id.equals(bt_id)) {
                    ((ButtonModel) o).setValue(value);
                }
            } else if (o instanceof BMForm) {
                bmForm = ((BMForm) o);
            }
        }
    }

    /**
     * 单纯赋值，不走联动
     *
     * @param id
     * @param value
     */
    public void setSingleDateById2(String id, String value) {
        if (list == null || list.size() == 0) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {//section
            Object o = list.get(i);
            if (o instanceof Section) {
                List<WorkOrder> list = ((Section) o).getSection();
                for (WorkOrder wo : list) {
                    if (wo.getID().equals(id)) {
                        wo.setValue(value);
                        break;
                    }
                }
            }
        }
    }

    public void setLink(WorkOrder wo, String bmName) {
        String methodName = wo.getMethodName();
        String expreDesc = wo.getExpreDesc();
        LinkBean bean = new LinkBean();
        bean.setBmModelName(bmName);
        bean.setAttrsMap(getReturnStringModel());
        bean.setExpression(expreDesc);
        List<String> list = new ArrayList<>();
        list.add(methodName);
        final Gson gson = new Gson();
        String b = gson.toJson(bean);
        list.add(b);
        RequestBusiness.invokeDataLinkageMethod(list, new RequestTask.ResultCallback<LinkResponse>() {
            @Override
            public void onError(Exception e) {
//                iSetDataById.setLink();
            }

            @Override
            public void onResponse(LinkResponse response) {
                if (response != null && !TextUtils.isEmpty(response.getReturnValue())) {
                    String json = response.getReturnValue();
                    List<LinkResponse.LinkResponse2> datas = gson.fromJson(json, new TypeToken<List<LinkResponse.LinkResponse2>>() {
                    }.getType());
                    setLink2(datas);
                }
            }
        });
    }

    private void setLink2(List<LinkResponse.LinkResponse2> datas) {
        for (LinkResponse.LinkResponse2 bean : datas) {
            setLink3(bean);
        }
        EventBus.getDefault().post("");
    }

    private void setLink3(LinkResponse.LinkResponse2 bean) {
        int type = bean.getControlType();
        String dmAttrName = bean.getDmAttrName();
        boolean hasEdit = bean.isHasEdit();
        boolean hasRequired = bean.isHasRequired();
        boolean hasVisble = bean.isHasVisible();
        String value = bean.getValue();

        if (list == null || list.size() == 0) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {//section
            Object o = list.get(i);
            if (o instanceof Section) {
                List<WorkOrder> list = ((Section) o).getSection();
                for (WorkOrder wo : list) {
                    if (TextUtils.isEmpty(wo.getDmAttrName())) {
                        continue;
                    }
                    if (wo.getDmAttrName().equals(dmAttrName)) {
                        switch (type) {
                            case 1:
                                wo.setValue(value);
                                break;
                            case 2:
                                break;
                            case 3:
                                wo.setModified(hasEdit);
                                break;
                            case 4:
                                wo.setVisible(hasVisble);
                                break;
                            case 5:
                                wo.setRequired(hasRequired);
                                break;
                        }
                        break;
                    }
                }
            }
        }
    }

    public void setLineLoadById(final ButtonModel button, String con) {
        List<ButtonModel.NextNode> nodes = button.getNextNode();
        Gson gson = new Gson();
        List<ConditionJudgment> conditionJudgment = gson.fromJson(con, new TypeToken<List<ConditionJudgment>>() {
        }.getType());
        for (ConditionJudgment c : conditionJudgment) {
            String keyStr = c.getKey();
            String value_name = c.getValue();
            String operation = c.getOperation();
            String[] keys = keyStr.split(operation);
            String key = keys[0];
            String value = keys[1];
            if (list == null || list.size() == 0) {
                return;
            }
            for (int i = 0; i < list.size(); i++) {//section
                Object o = list.get(i);
                if (o instanceof Section) {
                    List<WorkOrder> list = ((Section) o).getSection();
                    for (WorkOrder wo : list) {
                        String key1 = wo.getDmAttrName();
                        String value1 = wo.getValue();
                        if (TextUtils.isEmpty(key1)) {
                            continue;
                        }
                        if (TextUtils.isEmpty(value1)) {
                            continue;
                        }
                        if (key1.equals(key)) {
                            if (operation.equals("==")) {
                                if (value1.equals(value)) {
                                    for (ButtonModel.NextNode node : nodes) {
                                        if (value_name.equals(node.getName())) {
                                            setSingleDateById(button.getID(), gson.toJson(node));
                                            return;
                                        }
                                    }
                                }
                            } else {
                                if (!value1.equals(value)) {
                                    for (ButtonModel.NextNode node : nodes) {
                                        if (value_name.equals(node.getName())) {
                                            setSingleDateById(button.getID(), gson.toJson(node));
                                            return;
                                        }
                                    }
                                }
                            }
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * 根据id赋值策略
     *
     * @param id
     * @param value
     */
    public void setCeLueById(String id, TaskStrategy value) {
        if (list == null || list.size() == 0) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {//section
            Object o = list.get(i);
            if (o instanceof ButtonModel) {
                String bt_id = ((ButtonModel) o).getID();
                if (id.equals(bt_id)) {
                    ((ButtonModel) o).setTaskStrategy(value);
                }
            }
        }
    }

    /**
     * 根据id查找附件控件，给控件的图片集合赋值
     *
     * @param id
     */
    public void setAttachmentDateById2(String id, List<ImageItem> images) {
        if (list == null || list.size() == 0) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {//section
            Object o = list.get(i);
            if (o instanceof Section) {
                List<WorkOrder> list = ((Section) o).getSection();
                for (WorkOrder wo : list) {
                    if (wo.getID().equals(id) && "Attachment".equals(wo.getViewName())) {
                        if (wo.getImags() == null) {
                            wo.setImags(images);
                        } else {
                            wo.getImags().addAll(images);
                        }
                        break;
                    }
                }
            }
        }
        Log.i("YCS", "setSingleDateById: value:" + getSingleDateById(id));
    }

    public void removeAttachmentDateById2(String id, ImageItem image){
        if (list == null || list.size() == 0) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {//section
            Object o = list.get(i);
            if (o instanceof Section) {
                List<WorkOrder> list = ((Section) o).getSection();
                for (WorkOrder wo : list) {
                    if (wo.getID().equals(id) && "Attachment".equals(wo.getViewName())) {
                        if (wo.getImags() == null) {
                            break;
                        } else {
                            for (ImageItem item : wo.getImags()){
                                if(item.equals(image)){
                                    wo.getImags().remove(item);
                                    break;
                                }
                            }
                        }
                        break;
                    }
                }
            }
        }
    }

    /**
     * 逻辑：根据台账选择的attname去数据源中查找配套的台账列表控件，先判断上是否台账列表，然后在判断attname和toattname是否配套
     *
     * @param attName
     */
    public void setResModelSelect3(String attName, List<Map<String, String>> behindData) {
        if (list == null || list.size() == 0) {
            return;
        }
        WorkOrder wowo = null;
        List<ResColumnsJsonBean> datas = null;
        for (int i = 0; i < list.size(); i++) {//section
            Object o = list.get(i);
            if (o instanceof Section) {
                List<WorkOrder> list = ((Section) o).getSection();
                for (WorkOrder wo : list) {
                    if (wo.getViewName().equals("ResDynamicDataGrid") && wo.getToDmAttrName().equals(attName)) {
                        wowo = wo;
                        String columnsJson = wo.getColumnsJson();
                        Gson gson = new Gson();
                        datas = gson.fromJson(columnsJson, new TypeToken<List<ResColumnsJsonBean>>() {
                        }.getType());
                        break;
                    }
                }
            }
        }
        if (datas == null || datas.size() == 0) {
            return;
        }
        List<Map<String, Dict>> results = new ArrayList<>();
        for (Map<String, String> data : behindData) {
            Map<String, Dict> map = new HashMap<>();
            for (ResColumnsJsonBean bean : datas) {
                String key = bean.getDataFieldObj().getValue();
                Dict dict = new Dict();
                dict.setKey(key);
                dict.setValue(data.get(key));
                map.put(key, dict);
            }
            results.add(map);
        }
        Gson gson = new Gson();
        String json = gson.toJson(results);
        if (wowo != null) {
            setSingleDateById(wowo.getID(), json);
        }
    }

    /**
     * 逻辑描述：先根据from找到需要赋值的控件，然后根据to匹配map的key取出对应的value，最后把value赋值到控件里
     */
    public void setResModelValueByFromOrTo(List<ResModeValue.ConfigValueJsonBean> configValueJson, Map<String, String> data) {
        //获取所有的控件的id
        List<String> list_view_id = new ArrayList<>();
        Set viewSet = views.keySet();
        Iterator iterator = viewSet.iterator();
        while (iterator.hasNext()) {
            String viewId = (String) iterator.next();
            list_view_id.add(viewId);
        }
        //获取所有数据的dmAttrName
        List<String> list_map_key = new ArrayList<>();
        Set mapKey = data.keySet();
        Iterator iterator1 = mapKey.iterator();
        while (iterator1.hasNext()) {
            String key = (String) iterator1.next();
            list_map_key.add(key);
        }
        for (ResModeValue.ConfigValueJsonBean model : configValueJson) {
            String to = model.getToFmAttrName().getDmAttrName();
            String from = model.getFromDmAttrName().getDmAttrName();
            String d = data.get(to);
            for (String id : list_view_id) {
                String dmAttrName = views2.get(id);
                if (from.equals(dmAttrName)) {
                    setSingleDateById(id, d);
                    Log.i("YCS", "setResModelValueByFromOrTo: id:" + id + "===d:" + d);
                }
            }
        }
    }

    /**
     * 根据id获取value
     *
     * @param id
     */
    public String getSingleDateById(String id) {
        if (list == null || list.size() == 0) {
            return null;
        }
        String temp = null;
        w:
        for (int i = 0; i < list.size(); i++) {//section
            Object o = list.get(i);
            if (o instanceof Section) {
                List<WorkOrder> list = ((Section) o).getSection();
                for (WorkOrder wo : list) {
                    if (wo.getID().equals(id)) {
                        temp = wo.getValue();
                        return temp;
                    }
                }
            } else if (o instanceof ButtonModel) {
                temp = ((ButtonModel) o).getValue();
                break;
            }
        }
        return temp;
    }

    public void setMapInit(Map<String, String> map1) {
        map.putAll(map1);
    }

    /**
     * 上传图片第一级
     *
     * @param list
     * @param loadListener
     */
    private void setLoad3(final List<ImageItem> list, final LoadListener1 loadListener) {
        if (list.size() > 0 && position < list.size()) {
            ImageItem imageItem = list.get(position);
            if (imageItem == null) {
                return;
            }
            String path = imageItem.path;
            String name = imageItem.name;
            FileInputStream fis = null;
            ByteArrayOutputStream baos = null;
            byte[] bytes = null;
            String result = "";
            try {
                File file = new File(path);
                fis = new FileInputStream(file);
                baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int count = 0;
                while ((count = fis.read(buffer)) > 0) {
                    baos.write(buffer, 0, count);
                }
                bytes = baos.toByteArray();
                result = Base64.encodeToString(bytes, Base64.DEFAULT);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (baos != null) {
                        baos.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    if (fis != null) {
                        fis.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            List<String> list1 = new ArrayList();
            list1.add(result);
            if (TextUtils.isEmpty(name)) {
                String currentTime = System.currentTimeMillis() + ".jpg";
                list1.add(currentTime);
            } else {
                list1.add(name);
            }
            list1.add(MySharedpreferences.getUser().getName());
            RequestAttachment.uploadFile(list1, new RequestTask.ResultCallback<UploadFileResponse>() {
                @Override
                public void onError(Exception e) {

                }

                @Override
                public void onResponse(UploadFileResponse response) {
                    String id = response.getReturnValue().getId();
                    Log.i("YCS", "onResponse: id:" + id);
                    ids.add(id);
                    position++;
                    if (position >= list.size()) {
                        position = 0;
                        String attachIds = "";
                        if (ids.size() > 0) {
                            StringBuffer sb = new StringBuffer();
                            for (int i = 0; i < ids.size(); i++) {
                                String id1 = ids.get(i);
                                Log.i("YCS", "onResponse: id1:" + id1);
                                if (i == ids.size() - 1) {
                                    sb.append(id1);
                                } else {
                                    sb.append(id1).append(",");
                                }
                            }
                            attachIds = sb.toString();
                        }
                        loadListener.setLoadListener(attachIds);
                    } else {
                        setLoad3(list, loadListener);
                    }
                }
            });
        } else {
            loadListener.setLoadListener("");
        }
    }

    /**
     * 获取所有的图片map
     *
     * @return
     */
    private Map<String, List<ImageItem>> getImageMap() {
        Map<String, List<ImageItem>> imageMap = new HashMap<>();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {//section
                Object o = list.get(i);
                if (o instanceof Section) {
                    List<WorkOrder> list = ((Section) o).getSection();
                    for (WorkOrder wo : list) {
                        if (wo.getViewName().equals("Attachment")) {
                            List<ImageItem> images = wo.getImags();
                            if (images != null) {
                                imageMap.put(wo.getID(), images);
                            }
                        }
                    }
                }
            }
        }
        return imageMap;
    }

    /**
     * 最终级别
     */
    public void solvedMap(final LoadListener3 loadListener3) {
        Map<String, List<ImageItem>> map = getImageMap();
        if (map.size() > 0) {
            Set<String> set = map.keySet();
            Iterator<String> iterator = set.iterator();
            while (iterator.hasNext()) {
                String id = iterator.next();
                strs.add(id);
                imageItems.add(map.get(id));
            }
            setListener(new LoadListener2() {
                @Override
                public void setLoadListenenr2() {
                    loadListener3.setLoadListenenr3();
                }
            });
        } else {
            loadListener3.setUnLoadListener();
        }
    }


    /**
     * 第二级别
     *
     * @param listener
     */
    private void setListener(final LoadListener2 listener) {
        if (imageItems.size() > 0 && index <= imageItems.size()) {
            setLoad3(imageItems.get(index), new LoadListener1() {
                @Override
                public void setLoadListener(String ids) {
                    String id = strs.get(index);
                    if (!TextUtils.isEmpty(ids)) {
                        if(!TextUtils.isEmpty(adapterIds.get(id))){
                            ids = ids + "," + adapterIds.get(id);
                        }
                        setSingleDateById(id, ids);
                        index++;
                    }else {
                        setSingleDateById(id, adapterIds.get(id));
                    }
                    if (index == imageItems.size()) {
                        listener.setLoadListenenr2();
                    }
                }
            });
        }
    }

    /**
     * 获取最终上送的值
     *
     * @return
     */
    public Map<String, String> getReturnString() {
        if (list == null || list.size() == 0) {
            return null;
        }
        for (int i = 0; i < list.size(); i++) {//section
            Object o = list.get(i);
            if (o instanceof Section) {
                List<WorkOrder> list = ((Section) o).getSection();
                for (WorkOrder wo : list) {
                    if (!TextUtils.isEmpty(wo.getDmAttrName())) {
                        Log.d("内容", wo.getDmAttrName() + ":" + wo.getValue());
                        if (wo.getViewName().equals("TreeData")) {
                            String value = wo.getValue();
                            if (value != null) {
                                String[] temp = value.split(",");
                                if (temp.length == 2) {
                                    map.put(wo.getDmAttrName(), temp[0]);
                                } else {
                                    map.put(wo.getDmAttrName(), wo.getValue());
                                }
                            } else {
                                map.put(wo.getDmAttrName(), wo.getValue());
                            }

                        } else if(wo.getViewName().equals("DataDisplayUser")){
                            String value = wo.getValue();
                            if (value != null) {
                                String[] temp = value.split(",");
                                if (temp.length == 2) {
                                    map.put(wo.getDmAttrName(), temp[0]);
                                } else {
                                    map.put(wo.getDmAttrName(), wo.getValue());
                                }
                            } else {
                                map.put(wo.getDmAttrName(), wo.getValue());
                            }
                        }else {
                            map.put(wo.getDmAttrName(), wo.getValue());
                        }
                    }
                }
            } else if (o instanceof ButtonModel) {
                List<ButtonModel.NextNode> list = ((ButtonModel) o).getNextNode();
                if (list == null || list.size() == 0) {
                    continue;
                }
                String json = ((ButtonModel) o).getValue();
                TaskStrategy taskStrategy = ((ButtonModel) o).getTaskStrategy();
                Gson gson = new Gson();
                ButtonModel.NextNode node = gson.fromJson(json, ButtonModel.NextNode.class);
                map.put("outCome", node.getName());
                map.put("outComeDesc", node.getNameDesc());
                map.put("specificValueUpdate", node.getSpecificValueUpdate());
                if (taskStrategy != null) {
                    map.put("taskStrategy", gson.toJson(taskStrategy));
                } else {
                    String temp = node.getTaskStrategy();
                    map.put("taskStrategy", temp);
                }
            }
        }
        return map;
    }

    /**
     * 用于图片上传回调
     */
    public interface LoadListener1 {
        void setLoadListener(String ids);
    }

    /**
     * 用于附件上传回调
     */
    public interface LoadListener2 {
        void setLoadListenenr2();
    }

    /**
     * 用于activity回调
     */
    public interface LoadListener3 {
        void setLoadListenenr3();

        void setUnLoadListener();
    }

    /**
     * 模板获取最终上送的值
     *
     * @return
     */
    public Map<String, String> getReturnStringModel() {
        if (list == null || list.size() == 0) {
            return null;
        }
        for (int i = 0; i < list.size(); i++) {//section
            Object o = list.get(i);
            if (o instanceof Section) {
                List<WorkOrder> list = ((Section) o).getSection();
                for (WorkOrder wo : list) {
                    if (!TextUtils.isEmpty(wo.getDmAttrName())) {
                        map.put(wo.getDmAttrName(), wo.getValue());
                    }
                }
            }
        }
        return map;
    }

    /**
     * 判断是否有必填项没填
     *
     * @param c
     * @return
     */
    public boolean isCommit(Context c) {
        if (list == null || list.size() == 0) {
            return false;
        }
        for (int i = 0; i < list.size(); i++) {
            Object o = list.get(i);
            if (o instanceof Section) {
                List<WorkOrder> list = ((Section) o).getSection();
                for (WorkOrder wo : list) {
                    if (wo.isRequired() && TextUtils.isEmpty(wo.getValue())) {
                        Toast.makeText(c, "请填写" + wo.getName(), Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
