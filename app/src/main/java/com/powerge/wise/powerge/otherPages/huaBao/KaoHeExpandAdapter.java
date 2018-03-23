package com.powerge.wise.powerge.otherPages.huaBao;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;

import com.powerge.wise.basestone.heart.ui.XAdapter;
import com.powerge.wise.powerge.BR;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.bean.KaoHeChildItemBean;
import com.powerge.wise.powerge.databinding.ItemKaoHeListExpandChildBinding;
import com.powerge.wise.powerge.databinding.ItemKaoheChildListBinding;
import com.powerge.wise.powerge.databinding.ItemTextKaoHeExpandRootBinding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

/**
 * Created by Administrator on 2018/3/5.
 */

public class KaoHeExpandAdapter extends BaseExpandableListAdapter {
    List<KaoHeChildItemBean> list = new ArrayList<>();
    private SparseArray<ImageView> mIndicators;

    public void setList(List<KaoHeChildItemBean> listp) {
        this.list = listp;
        notifyDataSetChanged();
    }

    public KaoHeExpandAdapter() {
        mIndicators = new SparseArray<>();
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition).getDetails().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    ItemTextKaoHeExpandRootBinding expandRootBinding;

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            expandRootBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_text_kao_he_expand_root, parent, false);
            convertView = expandRootBinding.getRoot();
            convertView.setTag(expandRootBinding);
        } else {
            expandRootBinding = (ItemTextKaoHeExpandRootBinding) convertView.getTag();

        }
        expandRootBinding.setKaohe(list.get(groupPosition));
        expandRootBinding.setMany("考核" + list.get(groupPosition).getCount() + "次");
        expandRootBinding.setTime(Calendar.getInstance().get(Calendar.YEAR)+"年"+list.get(groupPosition).getMonth());
        mIndicators.put(groupPosition, expandRootBinding.btnRootExpand);
        setIndicatorState(groupPosition, isExpanded);
        return convertView;
    }

    ItemKaoHeListExpandChildBinding expandChildBinding;

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            expandChildBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_kao_he_list_expand_child, parent, false);
            convertView = expandChildBinding.getRoot();
            convertView.setTag(expandChildBinding);
        } else {
            expandChildBinding = (ItemKaoHeListExpandChildBinding) convertView.getTag();
        }
        expandChildBinding.contentKaoheChildList.setLayoutManager(new LinearLayoutManager(parent.getContext()));
        XAdapter<KaoHeChildItemBean.DetailsBean, ItemKaoheChildListBinding> adapter = new XAdapter.SimpleAdapter<>(BR.khChild, R.layout.item_kaohe_child_list);
        expandChildBinding.contentKaoheChildList.setAdapter(adapter);
        adapter.setList(list.get(groupPosition).getDetails());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    // 根据分组的展开闭合状态设置指示器
    public void setIndicatorState(int groupPosition, boolean isExpanded) {
        if (isExpanded) {
            mIndicators.get(groupPosition).setImageResource(R.drawable.ic_scroll);
        } else {
            mIndicators.get(groupPosition).setImageResource(R.drawable.ic_expand);
        }
    }
}
