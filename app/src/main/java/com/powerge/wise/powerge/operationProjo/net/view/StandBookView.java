package com.powerge.wise.powerge.operationProjo.net.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.powerge.wise.powerge.operationProjo.net.bean.ColumnsJsonBean;
import com.powerge.wise.powerge.operationProjo.net.bean.Dict;
import com.powerge.wise.powerge.operationProjo.net.bean.ResColumnsJsonBean;
import com.powerge.wise.powerge.operationProjo.net.bean.ResModelConfigure;
import com.powerge.wise.powerge.operationProjo.net.ui.activity.ListActivity;
import com.powerge.wise.powerge.operationProjo.net.view.mview.ListItemView;
import com.wisesignsoft.OperationManagement.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ycs on 2016/12/12.
 */
public class StandBookView extends LinearLayout {

    private LinearLayout ll_stand_book;
    private TextView tv_stand_book_del;
    private TextView tv_stand_book_title;

    private IStandBookClickListener listener;
    private Map<String, String> map;
    private Map<String, Object> map2;
    private Map<String, Dict> map3;
    private List<ColumnsJsonBean> datas1 = new ArrayList<>();
    private ResModelConfigure datas2;
    private List<ResColumnsJsonBean> datas3 = new ArrayList<>();
    private int type;
    private String id;
    /*列表*/
    private static final int TYPE1 = 1;
    /*配置项列表*/
    private static final int TYPE2 = 2;
    /*台账列表*/
    private static final int TYPE3 = 3;

    public StandBookView(Context context) {
        super(context);
        init(context);
    }

    private void init(final Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.stand_book_view, this, true);
        tv_stand_book_title = (TextView) view.findViewById(R.id.tv_stand_book_title);
        tv_stand_book_del = (TextView) view.findViewById(R.id.tv_stand_book_del);
        ll_stand_book = (LinearLayout) view.findViewById(R.id.ll_stand_book);
        tv_stand_book_del.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.setDel();
            }
        });
        ll_stand_book.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (type) {
                    case TYPE1:
                        ListActivity.startSelf(context, map, datas1,id);
                        break;
                    case TYPE2:
                        break;
                    case TYPE3:
                        break;
                }
            }
        });
    }

    public void setData1(Context context, List<ColumnsJsonBean> datas, Map<String, String> map, int type,String id) {
        this.map = map;
        this.id = id;
        if (datas != null) {
            this.datas1.addAll(datas);
        }
        this.type = type;
        for (ColumnsJsonBean d : datas) {
            ListItemView itemView = new ListItemView(context);
            itemView.setDate(d.getHeaderText(), map.get(d.getDataFieldObj().getName()));
            ll_stand_book.addView(itemView);
        }
    }

    public void setData2(Context context, ResModelConfigure datas, Map<String, Object> map, int type) {
        this.map2 = map;
        this.datas2 = datas;
        this.type = type;
        List<ResModelConfigure.AttrDatasOfFormBean> beens = datas.getAttrDatasOfForm();
        for (ResModelConfigure.AttrDatasOfFormBean d : beens) {
            ListItemView itemView = new ListItemView(context);
            itemView.setDate(d.getName(), (String) map.get(d.getDmAttrName()));
            ll_stand_book.addView(itemView);
        }
    }

    public void setData3(Context context, List<ResColumnsJsonBean> datas, Map<String, Dict> map, int type) {
        this.map3 = map;
        if (datas != null) {
            this.datas3.addAll(datas);
        }
        this.type = type;
        for (ResColumnsJsonBean d : datas) {
            ListItemView itemView = new ListItemView(context);
            String key = d.getDataFieldObj().getName();
            Dict dict = map.get(key);
            if (dict == null) {
                itemView.setDate(d.getHeaderText(), null);
            } else {
                itemView.setDate(d.getHeaderText(), map.get(d.getDataFieldObj().getName()).getValue());
            }
            ll_stand_book.addView(itemView);
        }
    }

    public void setOnIStandBookClickListener(IStandBookClickListener listener) {
        this.listener = listener;
    }

    public interface IStandBookClickListener {
        void setDel();
    }

    public void setDeleteHide() {
        tv_stand_book_del.setVisibility(GONE);
    }
}
