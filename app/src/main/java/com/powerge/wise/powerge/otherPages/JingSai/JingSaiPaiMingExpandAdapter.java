package com.powerge.wise.powerge.otherPages.JingSai;

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
import com.powerge.wise.powerge.bean.PaiMingChildItemBean;
import com.powerge.wise.powerge.databinding.ItemPaiMingChildListBinding;
import com.powerge.wise.powerge.databinding.ItemPaiMingListExpandChildBinding;
import com.powerge.wise.powerge.databinding.ItemTextPaiMingExpandRootBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/5.
 */

public class JingSaiPaiMingExpandAdapter extends BaseExpandableListAdapter {
    List<PaiMingChildItemBean> list=new ArrayList<>();
    private SparseArray<ImageView> mIndicators;

    public JingSaiPaiMingExpandAdapter() {
        mIndicators = new SparseArray<>();
    }

    public void setList(List<PaiMingChildItemBean> listp) {
        this.list = listp;
        notifyDataSetChanged();
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

    ItemTextPaiMingExpandRootBinding expandRootBinding;

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            expandRootBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_text_pai_ming_expand_root, parent, false);
            convertView = expandRootBinding.getRoot();
            convertView.setTag(expandRootBinding);
        } else {
            expandRootBinding = (ItemTextPaiMingExpandRootBinding) convertView.getTag();

        }
        expandRootBinding.setPaiMing(list.get(groupPosition));
        mIndicators.put(groupPosition, expandRootBinding.btnRootExpand);
        setIndicatorState(groupPosition, isExpanded);
        return convertView;
    }

    ItemPaiMingListExpandChildBinding expandChildBinding;

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            expandChildBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_pai_ming_list_expand_child, parent, false);
            convertView = expandChildBinding.getRoot();
            convertView.setTag(expandChildBinding);
        } else {
            expandChildBinding = (ItemPaiMingListExpandChildBinding) convertView.getTag();
        }
        expandChildBinding.contentPaiMingChildList.setLayoutManager(new LinearLayoutManager(parent.getContext()));
        XAdapter<PaiMingChildItemBean.PmChildBean, ItemPaiMingChildListBinding> adapter = new XAdapter.SimpleAdapter<>(BR.pmChild, R.layout.item_pai_ming_child_list);
        expandChildBinding.contentPaiMingChildList.setAdapter(adapter);
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
