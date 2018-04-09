package com.powerge.wise.powerge.otherPages;

import android.databinding.DataBindingUtil;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;

import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.bean.SheBeiRootBean;
import com.powerge.wise.powerge.databinding.ItemTextBinding;
import com.powerge.wise.powerge.databinding.ItemTextExpandListBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/1.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    static List<SheBeiRootBean> list = new ArrayList<>();
    private SparseArray<ImageView> mIndicators;

    ItemTextExpandListBinding expandListBinding;
    ItemTextBinding textBinding;

    public ExpandableListAdapter(List<SheBeiRootBean> list) {
        this.list = list;
        this.mIndicators = new SparseArray<>();
    }

    public ExpandableListAdapter() {
        this.mIndicators = new SparseArray<>();
    }

    public void setList(List<SheBeiRootBean> listparam) {
        list = listparam;
        notifyDataSetChanged();
    }

    public void addItems(List<SheBeiRootBean> datas) {
        if (datas == null || datas.isEmpty()) return;
        if (this.list == null || this.list.isEmpty()) {
            setList(datas);
            return;
        }
        this.list.addAll(datas);
        notifyDataSetChanged();
    }

    //分组 个数
    @Override
    public int getGroupCount() {
        return list.size();
    }

    //组中的view个数
    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    //获取组的数据
    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    //获取组中view的数据
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition).getName();
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


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            expandListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_text_expand_list, parent, false);
            convertView = expandListBinding.getRoot();
            convertView.setTag(expandListBinding);
            //      把位置和图标添加到Map
            mIndicators.put(groupPosition, expandListBinding.btnRootExpand);
            //      根据分组状态设置Indicator
            setIndicatorState(groupPosition, isExpanded);
        } else {
            expandListBinding = (ItemTextExpandListBinding) convertView.getTag();
        }
        expandListBinding.setExpand(list.get(groupPosition));

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            textBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_text, parent, false);
            convertView = textBinding.getRoot();
            convertView.setTag(textBinding);
        } else {
            textBinding = (ItemTextBinding) convertView.getTag();
        }
        textBinding.textContent.setText(list.get(groupPosition).getDeviceDesc());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    // 根据分组的展开闭合状态设置指示器
    public void setIndicatorState(int groupPosition, boolean isExpanded) {
        if (isExpanded) {//需要展开
            RotateAnimation rotateAnimation = new RotateAnimation(90, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setDuration(200);
            rotateAnimation.setFillAfter(true);
            rotateAnimation.setInterpolator(new AnticipateOvershootInterpolator());
            mIndicators.get(groupPosition).startAnimation(rotateAnimation);
        }
        if (!isExpanded) {
            RotateAnimation rotateAnimation = new RotateAnimation(0, 90, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setDuration(200);
            rotateAnimation.setFillAfter(true);
            rotateAnimation.setInterpolator(new AnticipateOvershootInterpolator());
            mIndicators.get(groupPosition).startAnimation(rotateAnimation);
        }
    }


}
