package com.powerge.wise.powerge.mainPage;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hyphenate.util.DensityUtil;
import com.powerge.wise.basestone.heart.ui.XAdapter;
import com.powerge.wise.basestone.heart.ui.XViewHolder;
import com.powerge.wise.basestone.heart.util.DensityUtils;
import com.powerge.wise.powerge.BR;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.bean.Items;
import com.powerge.wise.powerge.bean.User;
import com.powerge.wise.powerge.databinding.FragmentFirstBinding;
import com.powerge.wise.powerge.databinding.ItemFirstFragmentGridListBinding;
import com.powerge.wise.powerge.helper.GridSpacingItemDecoration;
import com.powerge.wise.powerge.otherPages.FuHeManagementActivity;
import com.powerge.wise.powerge.otherPages.LoginActivity;
import com.wisesignsoft.OperationManagement.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;


public class FirstFragment extends Fragment {

    private OnFirstFragmentInteractionListener mListener;
    FragmentFirstBinding fragmentBinding;
    List<Items> items = new ArrayList<>();

    XAdapter<Items, ItemFirstFragmentGridListBinding> adapter = new XAdapter.SimpleAdapter<Items, ItemFirstFragmentGridListBinding>(BR.item, R.layout.item_first_fragment_grid_list) {
        @Override
        public void onBindViewHolder(XViewHolder<Items, ItemFirstFragmentGridListBinding> holder, int position) {
            super.onBindViewHolder(holder, position);
            holder.getBinding().imgItemGrid.setBackgroundResource(items.get(position).getIcon());
            holder.getBinding().textItemGrid.setText(items.get(position).getName());
        }
    };
    XAdapter.OnItemClickListener<Items, ItemFirstFragmentGridListBinding> itemClickListener = new XAdapter.OnItemClickListener<Items, ItemFirstFragmentGridListBinding>() {
        @Override
        public void onItemClick(XViewHolder<Items, ItemFirstFragmentGridListBinding> holder) {
            goToActivity(holder.getBinding().getItem().getNumber());
        }
    };


    public FirstFragment() {
    }

    public FirstFragment newInstance() {
        FirstFragment firstFragment = new FirstFragment();
        return firstFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_first, container, false);
        init();
        return fragmentBinding.getRoot();
    }


    private void init() {
        createdData();
        fragmentBinding.content.setLayoutManager(new GridLayoutManager(getContext(), 4));
        fragmentBinding.content.addItemDecoration(new GridSpacingItemDecoration(4, DensityUtil.px2dip(getContext(), 10), true));
        fragmentBinding.content.setHasFixedSize(true);
        fragmentBinding.content.setAdapter(adapter);
        adapter.setItemClickListener(itemClickListener);
        adapter.setList(items);
    }

    int[] icon = new int[]{R.drawable.ic_fuhe_mangment,
            R.drawable.ic_dianliang_guanli,
            R.drawable.ic_jingji_zhibiao,
            R.drawable.ic_tongji_baobiao,
            R.drawable.ic_shei_bei_xin_xi,
            R.drawable.ic_zhizhang_log,
            R.drawable.ic_shengchan_zaohui,
            R.drawable.ic_zhiji_jingsai,
            R.drawable.ic_huanbao_zhibiao,
            R.drawable.ic_que_xian_guan_li
    };

    public void createdData() {
        items.clear();
        String[] name = getResources().getStringArray(R.array.item_name_array);
        for (int i = 0; i < name.length; i++) {
            Items items = new Items();
            items.setName(name[i]);
            items.setNumber(i);
            items.setIcon(icon[i]);
            this.items.add(items);
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFirstFragmentInteractionListener) {
            mListener = (OnFirstFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFirstFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }

    /**
     * Grid的跳转
     *
     * @param number
     */
    private void goToActivity(int number) {
        if (User.getCurrentUser() == null || !User.getCurrentUser().isLogin()) {
            ToastUtil.toast(getContext(), "please login");
            LoginActivity.start(getContext());
            return;
        }
        if (number == 0) {
            FuHeManagementActivity.start(getContext());
        } else {
            ToastUtil.toast(getContext(), "暂无");
        }
    }
}
