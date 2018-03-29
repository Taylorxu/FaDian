package com.powerge.wise.powerge.mainPage;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.bean.User;
import com.powerge.wise.powerge.databinding.FragmentFourthBinding;
import com.powerge.wise.powerge.otherPages.LoginActivity;


public class FourthFragment extends Fragment implements View.OnClickListener {
    FragmentFourthBinding binding;

    public FourthFragment() {

    }

    public static FourthFragment newInstance() {
        Bundle args = new Bundle();
        FourthFragment fragment = new FourthFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fourth, container, false);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {//<21
            binding.btnLogOut.setBackgroundResource(R.drawable.shape_corners_2_primary);
        }else{
            binding.btnLogOut.setBackgroundResource(R.drawable.selector_ripple);
        }
        binding.title.setText(getResources().getString(R.string.title_mine));
        binding.btnLogOut.setOnClickListener(this);
        return binding.getRoot();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_log_out:
                User.logout();
                LoginActivity.start(getContext());
                break;
            case R.id.btn_back:
                getActivity().finish();
                break;
        }
    }
}
