package com.powerge.wise.powerge.operationProjo.net.view.mview;


import com.powerge.wise.powerge.operationProjo.net.bean.WorkOrder;
import com.powerge.wise.powerge.operationProjo.net.net.response.QueryAllValidDictDateResponse;

import java.util.List;

/**
 * Created by ycs on 2016/12/13.
 */

public interface ISetData {
    void setData(WorkOrder wo, List<QueryAllValidDictDateResponse.ReturnValueBean.DictDatasBean> datas);
}
