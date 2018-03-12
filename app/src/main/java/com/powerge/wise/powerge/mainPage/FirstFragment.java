package com.powerge.wise.powerge.mainPage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

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
import com.powerge.wise.powerge.databinding.PopWindowFirstFragmentBinding;
import com.powerge.wise.powerge.helper.GridSpacingItemDecoration;
import com.powerge.wise.powerge.helper.StartActivity;
import com.powerge.wise.powerge.otherPages.DianLiangManagementActivity;
import com.powerge.wise.powerge.otherPages.FuHeManagementActivity;
import com.powerge.wise.powerge.otherPages.JingJiZhiBiaoActivity;
import com.powerge.wise.powerge.otherPages.LoginActivity;
import com.powerge.wise.powerge.otherPages.MorningMeetingActivity;
import com.powerge.wise.powerge.otherPages.SheBeiInfoActivity;
import com.powerge.wise.powerge.otherPages.ZHiZhangLogActivity;
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

    public static FirstFragment newInstance() {
        FirstFragment firstFragment = new FirstFragment();
        return firstFragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (User.getCurrentUser() == null || !User.getCurrentUser().isLogin()) {
            ToastUtil.toast(getContext(), getResources().getString(R.string.error_login_login_need));
            LoginActivity.start(getContext());
            return;
        }
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

        fragmentBinding.xunJianBtn.setOnClickListener(new BtnOnClick());
        fragmentBinding.wenTiPcBtn.setOnClickListener(new BtnOnClick());
        fragmentBinding.planMagBtn.setOnClickListener(new BtnOnClick());
        fragmentBinding.btnOpenDoor.setOnClickListener(new BtnOnClick());
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
        StartActivity.go(number, getContext());
    }


    private class BtnOnClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.xun_jian_btn:
                    StartActivity.go(11, getContext());
                    break;
                case R.id.wen_ti_pc_btn:
                    StartActivity.go(12, getContext());
                    break;
                case R.id.plan_mag_btn:
                    StartActivity.go(13, getContext());
                    break;
                case R.id.btn_open_door:
                    showPopWindow();
                    break;
            }
        }
    }

    private PopupWindow window;

    @SuppressLint("Range")
    private void showPopWindow() {
        PopWindowFirstFragmentBinding popBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.pop_window_first_fragment, null, false);
//        window = new PopupWindow(popBinding.getRoot(), LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        window = new PopupWindow(popBinding.getRoot(), 520, 432);
        window.setAnimationStyle(R.style.popup_window_anim);
        window.setFocusable(true);
        window.setOutsideTouchable(true);
        window.update();
        int w = fragmentBinding.btnOpenDoor.getWidth();
        int h = fragmentBinding.btnOpenDoor.getHeight();
        int[] location = new int[2];
        fragmentBinding.btnOpenDoor.getLocationOnScreen(location);
        int lx = location[0] - window.getWidth() + w - 20;//20 是距离右边的距离值
        window.showAtLocation(popBinding.getRoot(), Gravity.NO_GRAVITY, lx, location[1] + h);
    }
}
