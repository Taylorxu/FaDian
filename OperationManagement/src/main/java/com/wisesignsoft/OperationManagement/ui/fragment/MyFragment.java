package com.wisesignsoft.OperationManagement.ui.fragment;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseConstant;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.wisesignsoft.OperationManagement.BaseFragment;
import com.wisesignsoft.OperationManagement.Constant;
import com.wisesignsoft.OperationManagement.R;
import com.wisesignsoft.OperationManagement.db.MySharedpreferences;
import com.wisesignsoft.OperationManagement.db.UserDataManager;
import com.wisesignsoft.OperationManagement.ui.activity.ChatActivity;
import com.wisesignsoft.OperationManagement.view.MySwitch;
import com.wisesignsoft.OperationManagement.view.mview.MyDialog;

/**
 * Created by ycs on 2016/11/18.
 */

public class MyFragment extends BaseFragment implements MySwitch.SwitchClickListener, View.OnClickListener {

    private MySwitch iv_mine;
    private TextView tv_version;
    private RelativeLayout tv_exit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        iv_mine = (MySwitch) view.findViewById(R.id.iv_mine);
        tv_version = (TextView) view.findViewById(R.id.tv_version);
        tv_exit = (RelativeLayout) view.findViewById(R.id.tv_exit);
        tv_version.setText(getVersion());
        iv_mine.setData(MySharedpreferences.getMapStatusBoolean());
        iv_mine.setSwitchListener(this);
        tv_exit.setOnClickListener(this);
        return view;
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public String getVersion() {
        try {
            PackageManager manager = getActivity().getPackageManager();
            PackageInfo info = manager.getPackageInfo(getActivity().getPackageName(), 0);
            String version = info.versionName;
            return "V" + version;
        } catch (Exception e) {
            e.printStackTrace();
            return "获取版本信息失败";
        }
    }

    @Override
    public void setSwitchClickListener() {
        MySharedpreferences.putMapStatusBoolean(iv_mine.isSelected());
//        ((MainActivity) getActivity()).mapService();
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.tv_exit) {
            MyDialog myDialog = new MyDialog(getContext());
            myDialog.setData("确定退出登录？", myDialog, new MyDialog.IMyDialog() {
                @Override
                public void setMyDialog() {
                    EMClient.getInstance().logout(true, new EMCallBack() {

                        @Override
                        public void onSuccess() {
                                /*清理数据*/
                            PushAgent mPushAgent = PushAgent.getInstance(getContext());
                            mPushAgent.deleteAlias(MySharedpreferences.getUser().getUsername(), "Operation", new UTrack.ICallBack() {
                                @Override
                                public void onMessage(boolean b, String s) {
                                    Log.i("YCS", "onMessage: b:" + b + "=====s:" + s);
                                }
                            });
                            MySharedpreferences.clear();
                         /*   Intent intent = new Intent(getActivity(), LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);*/
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void onProgress(int progress, String status) {
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void onError(int code, String message) {
                            // TODO Auto-generated method stub

                        }
                    });
                }
            });
            myDialog.show();

        }
    }
}