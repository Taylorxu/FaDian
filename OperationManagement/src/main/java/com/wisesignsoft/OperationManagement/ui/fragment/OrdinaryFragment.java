package com.wisesignsoft.OperationManagement.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wisesignsoft.OperationManagement.BaseFragment;
import com.wisesignsoft.OperationManagement.R;
import com.wisesignsoft.OperationManagement.adapter.OrdinaryAdapter;
import com.wisesignsoft.OperationManagement.bean.OrdinaryModel;
import com.wisesignsoft.OperationManagement.bean.User;
import com.wisesignsoft.OperationManagement.db.MySharedpreferences;
import com.wisesignsoft.OperationManagement.net.RequestTask;
import com.wisesignsoft.OperationManagement.net.request.RequestDept;
import com.wisesignsoft.OperationManagement.net.request.RequestUser;
import com.wisesignsoft.OperationManagement.net.request.RequestYxyw;
import com.wisesignsoft.OperationManagement.net.response.BaseResponse;
import com.wisesignsoft.OperationManagement.net.response.FindAllDeptTreeResponse;
import com.wisesignsoft.OperationManagement.net.response.QueryAllValidUsersResponse;
import com.wisesignsoft.OperationManagement.net.response.QueryUnhandleProcessCountResponse;
import com.wisesignsoft.OperationManagement.net.response.QueryUserResourceResponse;
import com.wisesignsoft.OperationManagement.net.response.QueryValidUsersByAccountResponse;
import com.wisesignsoft.OperationManagement.net.response.UpdateUserPos;
import com.wisesignsoft.OperationManagement.ui.activity.ContractInfoActivity;
import com.wisesignsoft.OperationManagement.ui.activity.KnowSearchActivity;
import com.wisesignsoft.OperationManagement.ui.activity.MyTemplateActivity;
import com.wisesignsoft.OperationManagement.ui.activity.NewTemplateActivity;
import com.wisesignsoft.OperationManagement.ui.activity.NewWorkOrderActivity;
import com.wisesignsoft.OperationManagement.ui.activity.ServiceReportActivity;
import com.wisesignsoft.OperationManagement.ui.activity.SolvingActivity;
import com.wisesignsoft.OperationManagement.ui.activity.SovledActivity;
import com.wisesignsoft.OperationManagement.utils.OrdinaryUtil;
import com.wisesignsoft.OperationManagement.view.mview.DividerGridItemDecoration;
import com.wisesignsoft.OperationManagement.view.mview.LoadingView;
import com.wisesignsoft.OperationManagement.view.mview.MyDialog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by ycs on 2016/11/18.
 */

public class OrdinaryFragment extends BaseFragment implements View.OnClickListener {
    /*待处理*/
    private RelativeLayout rl_ordinary_solving;
    /*待处理数量*/
    private TextView tv_ordinary_num;
    /*已完成*/
    private RelativeLayout rl_ordinary_solved;
    /*grid view*/
    private RecyclerView rv_ordinary;

    List<String> list;
    private List<QueryUserResourceResponse.ResourcesBean> datas;
    private List<OrdinaryModel> models = new ArrayList<>();
    private OrdinaryAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_ordinary, container, false);
        rl_ordinary_solving = (RelativeLayout) view.findViewById(R.id.rl_ordinary_solving);
        tv_ordinary_num = (TextView) view.findViewById(R.id.tv_ordinary_num);
        rl_ordinary_solved = (RelativeLayout) view.findViewById(R.id.rl_ordinary_solved);
        rv_ordinary = (RecyclerView) view.findViewById(R.id.rv_ordinary);

        GridLayoutManager manager = new GridLayoutManager(getContext(), 3);
        rv_ordinary.setLayoutManager(manager);
        rv_ordinary.addItemDecoration(new DividerGridItemDecoration(getContext()));
//        request();
        setListener();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void setListener() {
        rl_ordinary_solving.setOnClickListener(this);
        rl_ordinary_solved.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        request();
    }

    private void request() {
        final LoadingView loadingView = LoadingView.getLoadingView(getContext());
        loadingView.show();
        list = new ArrayList<>();
        list.add(MySharedpreferences.getUser().getUsername());
        RequestYxyw.queryUnhandleProcessCount(list, new RequestTask.ResultCallback<QueryUnhandleProcessCountResponse>() {
            @Override
            public void onError(Exception e) {
                loadingView.stop(loadingView);
            }

            @Override
            public void onResponse(QueryUnhandleProcessCountResponse response) {
                loadingView.stop(loadingView);
                String num = response.getReturnValue();
                if (!TextUtils.isEmpty(num)) {
                    tv_ordinary_num.setVisibility(View.VISIBLE);
                    int num_int = Integer.parseInt(num);
                    String temp;
                    if (num_int > 99) {
                        temp = "99+";
                    } else {
                        temp = num;
                    }
                    tv_ordinary_num.setText(temp);
                } else {
                    tv_ordinary_num.setVisibility(View.GONE);
                }
            }
        });
        list = new ArrayList<>();
        list.add(MySharedpreferences.getUser().getUsername());
        RequestYxyw.queryUserResource(list, new RequestTask.ResultCallback<QueryUserResourceResponse>() {
            @Override
            public void onError(Exception e) {
                loadingView.stop(loadingView);
            }

            @Override
            public void onResponse(QueryUserResourceResponse response) {
                if (response != null && response.getResources() != null && response.getResources().size() > 0) {
                    datas = response.getResources();
                    initData(datas);
                }
            }
        });
        List<String> list = new ArrayList<>();
        list.add(MySharedpreferences.getUser().getUsername());
        RequestYxyw.queryValidUsersByAccount(list, new RequestTask.ResultCallback<QueryValidUsersByAccountResponse>() {
            @Override
            public void onError(Exception e) {

            }

            @Override
            public void onResponse(QueryValidUsersByAccountResponse response) {
                String stute = response.getReturnValue().getUserState();
                User user = MySharedpreferences.getUser();
                if ("1".equals(stute)) {
                    user.setStatue(1);
                    for (OrdinaryModel bean : models) {
                        if (bean.getName().equals("更改状态")) {
                            bean.setReId(R.mipmap.state_false);
                        }
                    }
                } else {
                    user.setStatue(0);
                    for (OrdinaryModel bean : models) {
                        if (bean.getName().equals("更改状态")) {
                            bean.setReId(R.mipmap.changestate);
                        }
                    }
                }
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
                MySharedpreferences.putUser(user);
            }
        });
    }

    /**
     * 设置权限资源数据
     *
     * @param datas
     */
    private void initData(List<QueryUserResourceResponse.ResourcesBean> datas) {
        if (datas != null && datas.size() > 0) {
            models = OrdinaryUtil.toOrdinaryModel(datas);
            adapter = new OrdinaryAdapter(getContext(), models, this);
            rv_ordinary.setAdapter(adapter);

            adapter.setOnIOrdinary(new OrdinaryAdapter.IOrdinary() {
                @Override
                public void setOnIOrdinary(OrdinaryModel resUrl) {
                    toOtherPager(resUrl);
                }
            });
        }
    }

    private void toOtherPager(OrdinaryModel resUrl) {
        Log.i("YCS", "toOtherPager: name：" + resUrl);
        Intent intent;
        switch (resUrl.getResUrl()) {
            case "NEW_TASK":
                intent = new Intent(getActivity(), NewWorkOrderActivity.class);
                startActivity(intent);
                break;
            case "NEW_TEMPLATE"://新建模板
                intent = new Intent(getActivity(), NewTemplateActivity.class);
                startActivity(intent);
                break;
            case "MY_TEMPLATE"://我的模板
                intent = new Intent(getActivity(), MyTemplateActivity.class);
                startActivity(intent);
                break;
            case "CONSTRACT_SEARCH"://合同信息
                intent = new Intent(getActivity(), ContractInfoActivity.class);
                intent.putExtra("key", resUrl.getResPar());
                startActivity(intent);
                break;
            case "CHANGE_STATUS"://更改状态
                updateStatue();
                break;
            case "SR_SEARCH"://服务报告
                intent = new Intent(getActivity(), ServiceReportActivity.class);
                intent.putExtra("key", resUrl.getResPar());
                startActivity(intent);
                break;
            case "KNOWLEDGE_SEARCH"://知识检索
            case "KNOWLEDGE_QUERY":
                intent = new Intent(getActivity(), KnowSearchActivity.class);
                intent.putExtra("key", resUrl.getResPar());
                startActivity(intent);
                break;
            case "ACCOUNT_BOOK_QUERY":
                String resPar = resUrl.getResPar();
                if ("res".equals(resPar)) {
                    intent = new Intent(getActivity(), ServiceReportActivity.class);
                    intent.putExtra("key", resPar);
                    startActivity(intent);
                } else if ("CONTRACTS".equals(resPar)) {
                    intent = new Intent(getActivity(), ContractInfoActivity.class);
                    intent.putExtra("key", resPar);
                    startActivity(intent);
                }
                break;
        }
    }

    /*
        0   忙
        1   闲
     */
    private void updateStatue() {
        int statue = MySharedpreferences.getUser().getStatue();
        if (statue == 0) {
            setStatue(1, "设置状态为闲");
        } else {
            setStatue(0, "设置状态为忙");
        }
    }

    private void setStatue(final int statue, String title) {
        MyDialog myDialog = new MyDialog(getContext());
        myDialog.setData(title, myDialog, new MyDialog.IMyDialog() {
            @Override
            public void setMyDialog() {
                final LoadingView loadingView = LoadingView.getLoadingView(getContext());
                loadingView.show();
                List<String> list = new ArrayList<>();
                list.add(MySharedpreferences.getUser().getUsername());
                list.add(statue + "");
                RequestUser.updateUserSta(list, new RequestTask.ResultCallback<BaseResponse>() {
                    @Override
                    public void onError(Exception e) {
                        loadingView.stop(loadingView);
                    }

                    @Override
                    public void onResponse(BaseResponse response) {
                        User user = MySharedpreferences.getUser();
                        if (statue == 1) {
                            user.setStatue(1);
                            MySharedpreferences.putUser(user);
                            if (models.size() > 0) {
                                for (OrdinaryModel bean : models) {
                                    if (bean.getName().equals("更改状态")) {
                                        bean.setReId(R.mipmap.state_false);
                                    }
                                }
                            }
                        } else {
                            user.setStatue(0);
                            MySharedpreferences.putUser(user);
                            if (models.size() > 0) {
                                for (OrdinaryModel bean : models) {
                                    if (bean.getName().equals("更改状态")) {
                                        bean.setReId(R.mipmap.changestate);
                                    }
                                }
                            }
                        }
                        adapter.notifyDataSetChanged();
                        loadingView.stop(loadingView);
                    }
                });
            }
        });
        myDialog.show();
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        int i = view.getId();
        if (i == R.id.rl_ordinary_solving) {
            intent = new Intent();
            intent.setClass(getActivity(), SolvingActivity.class);
            startActivity(intent);

        } else if (i == R.id.rl_ordinary_solved) {
            intent = new Intent();
            intent.setClass(getActivity(), SovledActivity.class);
            startActivity(intent);

        }
    }
}
