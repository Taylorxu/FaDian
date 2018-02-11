package com.wisesignsoft.OperationManagement.view.mview;

import com.wisesignsoft.OperationManagement.bean.WorkOrder;
import com.wisesignsoft.OperationManagement.net.response.QueryAllValidDictDateResponse;

import java.util.List;

/**
 * Created by ycs on 2016/12/13.
 */

public interface ISetData {
    void setData(WorkOrder wo, List<QueryAllValidDictDateResponse.ReturnValueBean.DictDatasBean> datas);
}
