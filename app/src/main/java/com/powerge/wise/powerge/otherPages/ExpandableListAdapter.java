package com.powerge.wise.powerge.otherPages;

import android.databinding.DataBindingUtil;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.powerge.wise.basestone.heart.util.LogUtils;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.bean.SheBeiRootBean;
import com.powerge.wise.powerge.databinding.ItemSecondExpandListBinding;
import com.powerge.wise.powerge.databinding.ItemTextBinding;
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

    ItemTextExpandListBinding expandListBinding;
    ItemTextBinding textBinding;
    ItemTextChildExpandListBinding childExpandListBinding;
    ItemSecondExpandListBinding secondExpandListBinding;

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

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Log.e("root groupPosition", "------" + groupPosition);
        if (convertView == null) {
            secondExpandListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_second_expand_list, parent, false);
            convertView = secondExpandListBinding.getRoot();
            convertView.setTag(secondExpandListBinding);
        } else {
            secondExpandListBinding = (ItemSecondExpandListBinding) convertView.getTag();
        }
        final SecondExpandAdapter listAdapter = new SecondExpandAdapter(list.get(groupPosition).getSheBeiChild().get(childPosition), childPosition);
        secondExpandListBinding.childExpandListContent.setAdapter(listAdapter);
        secondExpandListBinding.childExpandListContent.setGroupIndicator(null);
        secondExpandListBinding.childExpandListContent.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                boolean groupExpanded = parent.isGroupExpanded(childPosition);//需要传入 child的 position
                listAdapter.setIndicatorState2level(childPosition, groupExpanded);
                return false;
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    // 根据分组的展开闭合状态设置指示器
    public void setIndicatorState(int groupPosition, boolean isExpanded) {
        if (isExpanded) {
            mIndicators.get(groupPosition).setImageResource(R.drawable.ic_scroll);
        } else {
            mIndicators.get(groupPosition).setImageResource(R.drawable.ic_expand);
        }
    }

    /*root下面的二级expandListView*/
    class SecondExpandAdapter extends BaseExpandableListAdapter {
        private final int childPosition;
        SheBeiRootBean.SheBeiChildBean sheBeiChildBeans;
        private SparseArray<ImageView> mIndicators;

        /**
         * @param sheBeiChild
         * @param childPosition 最顶部的 每个子view possition（有多少层字view 就会在开展开时调用多少次）
         */
        public SecondExpandAdapter(SheBeiRootBean.SheBeiChildBean sheBeiChild, int childPosition) {
            this.sheBeiChildBeans = sheBeiChild;
            this.childPosition = childPosition;
            mIndicators = new SparseArray<>();
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            if (convertView == null) {
                childExpandListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_text_child_expand_list, parent, false);
                convertView = childExpandListBinding.getRoot();
                convertView.setTag(childExpandListBinding);
            } else {
                childExpandListBinding = (ItemTextChildExpandListBinding) convertView.getTag();
            }
            childExpandListBinding.setExpandChild(sheBeiChildBeans);
            mIndicators.put(childPosition, childExpandListBinding.btnExpand);//对应的存储 expand的 possition 和在expandview上的图标
            setIndicatorState2level(childPosition, isExpanded);//初始化 expandlist 的展开状态图标
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            if (convertView == null) {
                textBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_text, parent, false);
                convertView = textBinding.getRoot();
                convertView.setTag(textBinding);
            } else {
                textBinding = (ItemTextBinding) convertView.getTag();
            }
            textBinding.textContent.setText(sheBeiChildBeans.getName());
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        public void setIndicatorState2level(int groupPosition, boolean groupExpanded) {
            if (groupExpanded) {
                mIndicators.get(groupPosition).setImageResource(R.drawable.ic_scroll);
            } else {
                mIndicators.get(groupPosition).setImageResource(R.drawable.ic_expand);
            }
        }


        @Override

        public int getGroupCount() {
            return 1;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return 1;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return sheBeiChildBeans.getName();
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return sheBeiChildBeans.getName();
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


    }


}
