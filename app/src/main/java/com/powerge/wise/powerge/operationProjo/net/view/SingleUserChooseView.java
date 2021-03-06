package com.powerge.wise.powerge.operationProjo.net.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.operationProjo.net.bean.WorkOrder;
import com.powerge.wise.powerge.operationProjo.net.manager.SingleUserManager;
import com.powerge.wise.powerge.operationProjo.net.net.RequestTask;
import com.powerge.wise.powerge.operationProjo.net.net.request.RequestYxyw;
import com.powerge.wise.powerge.operationProjo.net.net.response.QueryAllValidUsersResponse;
import com.powerge.wise.powerge.operationProjo.net.net.response.QueryValidUsersByIdsResponse;
import com.powerge.wise.powerge.operationProjo.net.ui.activity.SingleUserChooseActivity;
import com.powerge.wise.powerge.operationProjo.net.view.mview.WorkOrderDataManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 单选人员组件
 * Created by ycs on 2016/12/15.
 */

public class SingleUserChooseView extends RelativeLayout {
    private TextView tv_single_user_left;
    private TextView tv_single_user_right;
    private RelativeLayout rl_single_user;
    private WorkOrder wo;

    public SingleUserChooseView(Context context) {
        super(context);
        init(context);
    }

    private void init(final Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_user_choose, this, true);
        rl_single_user = (RelativeLayout) view.findViewById(R.id.rl_single_user);
        tv_single_user_left = (TextView) view.findViewById(R.id.tv_single_user_left);
        tv_single_user_right = (TextView) view.findViewById(R.id.tv_single_user_right);
        rl_single_user.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                SingleUserChooseActivity.startSelf(context, wo.getID());
            }
        });
    }

    public void setDate(final WorkOrder wo) {
        this.wo = wo;
        String title = wo.getName();
        final String content = wo.getValue();
        if (!TextUtils.isEmpty(title)) {
            if (wo.isRequired()) {
                tv_single_user_left.setText(title + " *");
            } else {
                tv_single_user_left.setText(title);
            }
        }
        if (!TextUtils.isEmpty(content)) {
            List<String> list = new ArrayList<>();
            list.add(content);
            RequestYxyw.queryValidUsersByIds(list, new RequestTask.ResultCallback<QueryValidUsersByIdsResponse>() {
                @Override
                public void onError(Exception e) {

                }

                @Override
                public void onResponse(QueryValidUsersByIdsResponse response) {
                    try {
                        if (response == null || response.getReturnValue() == null || response.getReturnValue().size() == 0) {
                            if (TextUtils.isEmpty(content)) {
                                tv_single_user_right.setText("");
                            } else {
                                tv_single_user_right.setText(content);
                            }
                            //默认是name
                            List<String> list = new ArrayList<String>();
                            list.add(content);
                            list.add("");
                            RequestYxyw.queryValidUsersByUserName(list, new RequestTask.ResultCallback<QueryAllValidUsersResponse>() {
                                @Override
                                public void onError(Exception e) {

                                }

                                @Override
                                public void onResponse(QueryAllValidUsersResponse response) {
                                    if (response != null && response.getReturnValue() != null && response.getReturnValue().size() > 0) {
                                        String userId = response.getReturnValue().get(0).getUserId();
                                        WorkOrderDataManager.getManager().setSingleDateById2(wo.getID(), userId);
                                    }
                                }
                            });
                        } else {
                            String userName = response.getReturnValue().get(0).getUserName();
                            if (TextUtils.isEmpty(userName)) {
                                tv_single_user_right.setText("");
                            } else {
                                tv_single_user_right.setText(userName);
                            }
                        }
                    } catch (Exception e) {
                        Log.i("YCS", "onResponse: 单选人员异常");
                        e.printStackTrace();
                    }
                }
            });
        }else {
            tv_single_user_right.setText("");
        }
        if (!wo.isModified()) {
            rl_single_user.clearFocus();
            rl_single_user.setFocusable(false);
            rl_single_user.setClickable(false);

        }
    }

    /**
     * 列表控件赋值用
     *
     * @param key
     * @param value
     */
    public void setData(String key, String value) {
        if (!TextUtils.isEmpty(key)) {
            tv_single_user_left.setText(key);
        }
        if (!TextUtils.isEmpty(value)) {
            tv_single_user_right.setText(value);
        }
    }

    public String getValue() {
        String result = SingleUserManager.getManager().getTemp();
        SingleUserManager.getManager().clear();
        return result;
    }
}
