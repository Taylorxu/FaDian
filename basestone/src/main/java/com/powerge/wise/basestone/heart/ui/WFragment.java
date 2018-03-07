package com.powerge.wise.basestone.heart.ui;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;


import com.powerge.wise.basestone.heart.network.Notification;

import rx.Subscription;
import rx.functions.Action1;


public abstract class WFragment<Bind extends ViewDataBinding> extends Fragment implements   Action1<Notification> {


    private Bind binding;//ViewDataBinding
    public Subscription subscription, notification;

    public WFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notification = Notification.register(this);
        if (getArguments() != null)
            getParams(getArguments());
    }

    protected void getParams(Bundle args) {
    }

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (layoutId() == 0) return super.onCreateView(inflater, container, savedInstanceState);
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, layoutId(), container, false);
            onCreateView(savedInstanceState);
        }
        return binding.getRoot();
    }

    @Override
    public void call(Notification notification) {

    }

    @Override
    public void onDestroy() {
        notification.unsubscribe();
        super.onDestroy();
    }

    protected abstract void onCreateView(Bundle savedInstanceState);

    protected abstract
    @LayoutRes
    int layoutId();

    public Bind getBinding() {
        return binding;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        resetSubscription();
    }

    protected void resetSubscription() {
        if (subscription != null && subscription.isUnsubscribed()) subscription.unsubscribe();
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    protected int getStateHeight() {
        int stateHeight = -1;
        //获取status_bar_height资源的ID
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            stateHeight = getResources().getDimensionPixelSize(resourceId);
        }
        return stateHeight;
    }
}
