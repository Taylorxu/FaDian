package com.powerge.wise.powerge.otherPages;

import android.databinding.DataBindingUtil;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;

import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.bean.SheBeiRootBean;
import com.powerge.wise.powerge.databinding.ItemTextChildExpandListBinding;
import com.powerge.wise.powerge.databinding.ItemTextExpandListBinding;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/1.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    List<SheBeiRootBean> list;
    private SparseArray<ImageView> mIndicators;

    public ExpandableListAdapter(List<SheBeiRootBean> list) {
        this.list = list;
        this.mIndicators = new SparseArray<>();
    }

    //分组 个数
    @Override
    public int getGroupCount() {
        return list.size();
    }

    //组中的view个数
    @Override
    public int getChildrenCount(int groupPosition) {
        return list.get(groupPosition).getSheBeiChild().size();
    }

    //获取组的数据
    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    //获取组中view的数据
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition).getSheBeiChild().get(childPosition);
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

    ItemTextExpandListBinding expandListBinding;

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            expandListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_text_expand_list, parent, false);
            convertView = expandListBinding.getRoot();
            convertView.setTag(expandListBinding);
        } else {
            expandListBinding = (ItemTextExpandListBinding) convertView.getTag();
        }
        expandListBinding.setExpand(list.get(groupPosition));
        //      把位置和图标添加到Map
        mIndicators.put(groupPosition, expandListBinding.btnRootExpand);
        //      根据分组状态设置Indicator
        setIndicatorState(groupPosition, isExpanded);
        return convertView;
    }

    ItemTextChildExpandListBinding childExpandListBinding;

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            childExpandListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_text_child_expand_list, parent, false);
            convertView = childExpandListBinding.getRoot();
            convertView.setTag(childExpandListBinding);
        } else {
            childExpandListBinding = (ItemTextChildExpandListBinding) convertView.getTag();
        }
        childExpandListBinding.setExpandChild(list.get(groupPosition).getSheBeiChild().get(childPosition));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    //            根据分组的展开闭合状态设置指示器
    public void setIndicatorState(int groupPosition, boolean isExpanded) {
        if (isExpanded) {
            mIndicators.get(groupPosition).setImageResource(R.drawable.ic_scroll);
        } else {
            mIndicators.get(groupPosition).setImageResource(R.drawable.ic_expand);
        }
    }


}
