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

import java.util.List;

/**
 * Created by Administrator on 2018/3/5.
 */

public class KaoHeExpandAdapter extends BaseExpandableListAdapter {
    List<KaoHeChildItemBean> list;
    private SparseArray<ImageView> mIndicators;

    public KaoHeExpandAdapter(List<KaoHeChildItemBean> listp) {
        this.list = listp;
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
        return list.get(groupPosition).getKh_child().get(childPosition);
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
        expandRootBinding.setMany("考核" + list.get(groupPosition).getKh_child().size() + "次");
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
        XAdapter<KaoHeChildItemBean.KhChildBean, ItemKaoheChildListBinding> adapter = new XAdapter.SimpleAdapter<>(BR.khChild, R.layout.item_kaohe_child_list);
        expandChildBinding.contentKaoheChildList.setAdapter(adapter);
        adapter.setList(list.get(groupPosition).getKh_child());
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
