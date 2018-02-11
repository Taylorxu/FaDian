package com.wisesignsoft.OperationManagement.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.EaseUser;
import com.wisesignsoft.OperationManagement.BaseActivity;
import com.wisesignsoft.OperationManagement.R;
import com.wisesignsoft.OperationManagement.adapter.SelectUserAdapter;
import com.wisesignsoft.OperationManagement.adapter.SingleUserAdapter;
import com.wisesignsoft.OperationManagement.bean.UserInfoBean;
import com.wisesignsoft.OperationManagement.db.UserDataManager;
import com.wisesignsoft.OperationManagement.net.RequestTask;
import com.wisesignsoft.OperationManagement.net.request.RequestDept;
import com.wisesignsoft.OperationManagement.net.request.RequestYxyw;
import com.wisesignsoft.OperationManagement.net.response.FindAllDeptTreeResponse;
import com.wisesignsoft.OperationManagement.net.response.FindUserByDeptIdResponse;
import com.wisesignsoft.OperationManagement.net.response.FindUserByRoleIdResponse;
import com.wisesignsoft.OperationManagement.net.response.QueryAllValidUsersResponse;
import com.wisesignsoft.OperationManagement.utils.DividerItemDecoration;
import com.wisesignsoft.OperationManagement.view.mview.EmptyView;
import com.wisesignsoft.OperationManagement.view.mview.LoadingView;
import com.wisesignsoft.OperationManagement.view.mview.MyTitle;
import com.wisesignsoft.OperationManagement.view.mview.SeachView;

import java.util.ArrayList;
import java.util.List;

public class SelectUserActivity extends BaseActivity implements SeachView.ISearchView {

    private MyTitle mt_select_user;
    private SeachView sv_select_user;
    private RecyclerView rv_select_user;
    private EmptyView ev_select_user;
    private String id;
    private String key;
    private SelectUserAdapter adapter;
    private List<UserInfoBean> datas = new ArrayList<>();

    public static void startSelf(Context context, String id, String key) {
        Intent intent = new Intent(context, SelectUserActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("key", key);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user);
        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mt_select_user = (MyTitle) findViewById(R.id.mt_select_user);
        sv_select_user = (SeachView) findViewById(R.id.sv_select_user);
        rv_select_user = (RecyclerView) findViewById(R.id.rv_select_user);
        ev_select_user = (EmptyView) findViewById(R.id.ev_select_user);

        mt_select_user.setBack(true, this);
        mt_select_user.setTitle("选择人员");

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        rv_select_user.setLayoutManager(manager);
        rv_select_user.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        adapter = new SelectUserAdapter(this, datas);
        rv_select_user.setAdapter(adapter);
        ev_select_user.setOnRefreshListener(new EmptyView.IRefreshListener() {
            @Override
            public void onRefresh() {
                request();
            }
        });
        id = getIntent().getStringExtra("id");
        key = getIntent().getStringExtra("key");

        sv_select_user.setISearchViewListener(this);
        sv_select_user.setHint("请输入用户名");
        if (TextUtils.isEmpty(key)) {
            request();
        } else {
            requestSearch();
        }
    }

    /**
     * 请求数据
     */
    private void request() {
        final LoadingView loadingView = LoadingView.getLoadingView(this);
        loadingView.show();
        List<String> list = new ArrayList<>();
        list.add(id);
        RequestDept.findUserByDeptId(list, new RequestTask.ResultCallback<FindUserByDeptIdResponse>() {
            @Override
            public void onError(Exception e) {
                loadingView.stop(loadingView);
                ev_select_user.close();
            }

            @Override
            public void onResponse(FindUserByDeptIdResponse response) {
                ev_select_user.close();
                loadingView.stop(loadingView);
                if (datas != null) {
                    datas.clear();
                }
                if (response != null && response.getResult() != null) {
                    List<FindUserByDeptIdResponse.ResultBean> beans = response.getResult();
                    tresformData(beans);
                }
                setEmpty();
                initData();
            }
        });
    }

    private void requestSearch() {
        final LoadingView loadingView = LoadingView.getLoadingView(this);
        loadingView.show();
        List<String> list = new ArrayList<>();
        list.add(key);
        list.add(id);
        RequestYxyw.queryValidUsersByUserName(list, new RequestTask.ResultCallback<QueryAllValidUsersResponse>() {
            @Override
            public void onError(Exception e) {
                loadingView.stop(loadingView);
                ev_select_user.close();
            }

            @Override
            public void onResponse(QueryAllValidUsersResponse response) {
                ev_select_user.close();
                loadingView.stop(loadingView);
                if (response != null && response.getReturnValue() != null) {
                    List<QueryAllValidUsersResponse.ReturnValueBean> beans = response.getReturnValue();
                    if (datas != null) {
                        datas.clear();
                    }
                    tresformData2(beans);
                }
                setEmpty();
                initData();
            }
        });
    }

    private void setEmpty() {
        if (datas == null || datas.size() == 0) {
            ev_select_user.setVisibility(View.VISIBLE);
            rv_select_user.setVisibility(View.GONE);
        } else {
            ev_select_user.setVisibility(View.GONE);
            rv_select_user.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 填充数据
     */
    private void initData() {
        adapter.notifyDataSetChanged();
        adapter.setOnISingleUserClickListener(new SelectUserAdapter.ISelectUserClickListener() {
            @Override
            public void setOnUserNameClickListener(String username, String name) {
                EaseUser user = new EaseUser(username);
                user.setNick(name);
                UserDataManager.getManager().setUser(user);
//                Intent intent = new Intent(SelectUserActivity.this, ChatActivity.class);
//                intent.putExtra(EaseConstant.EXTRA_USER_ID, username);
//                startActivity(intent);
                ChatActivity.startSelf(SelectUserActivity.this, name, username);
            }
        });
    }

    /**
     * 将角色下的人员信息转换成我自己的bean
     *
     * @param beans
     */
    private void tresformData(List<FindUserByDeptIdResponse.ResultBean> beans) {
        for (FindUserByDeptIdResponse.ResultBean bean : beans) {
            UserInfoBean b = new UserInfoBean();
            b.setUserName(bean.getUserName());
            b.setUserAccountnum(bean.getUserAccountnum());
            datas.add(b);
        }
    }

    /**
     * 将搜索的人员信息转换成我自己的bean
     *
     * @param beans
     */
    private void tresformData2(List<QueryAllValidUsersResponse.ReturnValueBean> beans) {
        for (QueryAllValidUsersResponse.ReturnValueBean bean : beans) {
            UserInfoBean b = new UserInfoBean();
            b.setUserName(bean.getUserName());
            b.setUserAccountnum(bean.getUserAccountnum());
            datas.add(b);
        }
    }

    @Override
    public void setOnSearchListener(String key) {
        this.key = key;
        requestSearch();
    }

    @Override
    public void setOnCancelListener() {
        this.key = "";
    }
}
