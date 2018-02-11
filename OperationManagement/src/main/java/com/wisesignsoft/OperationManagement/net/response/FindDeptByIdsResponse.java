package com.wisesignsoft.OperationManagement.net.response;

import com.wisesignsoft.OperationManagement.bean.DeptBean;

import java.util.List;

/**
 * Created by ycs on 2017/1/4.
 */

public class FindDeptByIdsResponse extends BaseResponse {
    private List<DeptBean> result;

    public List<DeptBean> getResult() {
        return result;
    }

    public void setResult(List<DeptBean> result) {
        this.result = result;
    }
}
