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
import com.powerge.wise.basestone.heart.util.DensityUtils;
import com.powerge.wise.powerge.BR;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.bean.Items;
import com.powerge.wise.powerge.databinding.FragmentFirstBinding;
import com.powerge.wise.powerge.databinding.ItemFirstFragmentGridListBinding;
import com.powerge.wise.powerge.helper.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;


public class FirstFragment extends Fragment {

    private OnFirstFragmentInteractionListener mListener;
    FragmentFirstBinding fragmentBinding;
    List<Items> items = new ArrayList<>();

    XAdapter<Items, ItemFirstFragmentGridListBinding> adapter = new XAdapter.SimpleAdapter<>(BR.data, R.layout.item_first_fragment_grid_list);

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
        fragmentBinding.content.setLayoutManager(new GridLayoutManager(getContext(),4));
        fragmentBinding.content.addItemDecoration(new GridSpacingItemDecoration(4, DensityUtil.px2dip(getContext(),10),true));
        fragmentBinding.content.setHasFixedSize(true);
        fragmentBinding.content.setAdapter(adapter);
        adapter.setList(items);
    }

    public void createdData() {
        String [] name=getResources().getStringArray(R.array.item_name_array);
        for (int i = 0; i < name.length; i++) {
            Items items = new Items();
            items.setName(name[i]);
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
}
