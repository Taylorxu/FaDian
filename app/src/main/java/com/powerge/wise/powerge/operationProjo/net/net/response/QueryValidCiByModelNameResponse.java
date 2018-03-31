package com.powerge.wise.powerge.operationProjo.net.net.response;

import java.util.List;

/**
 * Created by ycs on 2016/11/30.
 */

public class QueryValidCiByModelNameResponse extends BaseResponse {

    /**
     * dictDatas : [{"dictValue":"8","dictId":"INC_TYPE:1428fe44-159d-4d33-ad71-58f0cd79a410","dictParentValue":"1","dictName":"数据库"},{"dictValue":"15","dictId":"INC_TYPE:20441e49-93db-42e6-8616-a0789f94f473","dictParentValue":"DICT_ROOT","dictName":"监控告警"},{"dictValue":"6","dictId":"INC_TYPE:31f8a864-fbbc-412d-9e5b-d071b588b046","dictParentValue":"1","dictName":"其它硬件"},{"dictValue":"2","dictId":"INC_TYPE:359d193c-19f7-40b1-a962-c2945618800c","dictParentValue":"1","dictName":"服务器"},{"dictValue":"11","dictId":"INC_TYPE:48d06749-e696-45b9-a591-bca2f331855c","dictParentValue":"1","dictName":"其它软件"},{"dictValue":"16","dictId":"INC_TYPE:4aa756a8-3e2a-4539-b21e-a4a28e8e2473","dictParentValue":"15","dictName":"服务器"},{"dictValue":"26","dictId":"INC_TYPE:509209d5-60f0-4e08-a8d7-3cacfe5d22a1","dictParentValue":"22","dictName":"技术咨询"},{"dictValue":"23","dictId":"INC_TYPE:523aa754-d836-477b-ad5f-2518f15ce0d4","dictParentValue":"22","dictName":"账号与权限"},{"dictValue":"4","dictId":"INC_TYPE:735d013d-c448-429a-a43c-6c81db7ffc81","dictParentValue":"1","dictName":"存储备份设备"},{"dictValue":"10","dictId":"INC_TYPE:742a5159-4b2b-4044-88bc-267128d226b8","dictParentValue":"1","dictName":"应用系统"},{"dictValue":"7","dictId":"INC_TYPE:7a5deec5-e47d-47ea-9433-248c01ace241","dictParentValue":"1","dictName":"操作系统"},{"dictValue":"17","dictId":"INC_TYPE:87b907fa-bff6-4e31-a262-19cc22ff9e6e","dictParentValue":"15","dictName":"数据库"},{"dictValue":"20","dictId":"INC_TYPE:8bdf91d0-39d8-4283-98ec-ccd09a5d2154","dictParentValue":"15","dictName":"网络设备"},{"dictValue":"22","dictId":"INC_TYPE:8dab1571-9687-4e9a-811f-ea741d47f539","dictParentValue":"DICT_ROOT","dictName":"服务请求"},{"dictValue":"1","dictId":"INC_TYPE:9652c285-0699-40a0-baaa-11b10b490619","dictParentValue":"DICT_ROOT","dictName":"人工报障"},{"dictValue":"3","dictId":"INC_TYPE:9fb1b64c-0c1b-4ed6-a6d2-aa846877a491","dictParentValue":"1","dictName":"网络设备"},{"dictValue":"5","dictId":"INC_TYPE:a2ff1366-b359-4617-9de8-7de062ea8f5b","dictParentValue":"1","dictName":"桌面及外围设备"},{"dictValue":"18","dictId":"INC_TYPE:a37e06f9-e7e0-4680-a921-8bc2aa09fc06","dictParentValue":"15","dictName":"应用系统"},{"dictValue":"24","dictId":"INC_TYPE:a47da3f3-56d0-4b90-9e6c-0c907947d467","dictParentValue":"22","dictName":"数据查询与变更"},{"dictValue":"13","dictId":"INC_TYPE:b9c7111d-1410-4459-9973-66da6944c8ad","dictParentValue":"1","dictName":"机房环境"},{"dictValue":"25","dictId":"INC_TYPE:c1208966-db84-4135-9fd0-f72001e17f88","dictParentValue":"22","dictName":"安装维护"},{"dictValue":"12","dictId":"INC_TYPE:d31bb01f-0b73-47eb-8208-5250f3c78066","dictParentValue":"1","dictName":"安全事件"},{"dictValue":"19","dictId":"INC_TYPE:d5a0725e-6657-4bd2-a28b-3f5c9c62ed03","dictParentValue":"15","dictName":"中间件"},{"dictValue":"9","dictId":"INC_TYPE:e2c93cad-56fa-479d-b5c7-f41a680181cc","dictParentValue":"1","dictName":"中间件"},{"dictValue":"14","dictId":"INC_TYPE:e33ae604-206d-4dae-8b66-8b9f4086a294","dictParentValue":"1","dictName":"其他"},{"dictValue":"21","dictId":"INC_TYPE:e89e154d-c590-428f-ae3b-5981f606c961","dictParentValue":"15","dictName":"其它"},{"dictValue":"DICT_ROOT","dictId":"INC_TYPE:215da7c8-fdba-4cd6-b76d-ecc9889b79ad","dictParentValue":null,"dictName":"根节点"}]
     * isTree : true
     */

    private ReturnValueBean returnValue;

    public ReturnValueBean getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(ReturnValueBean returnValue) {
        this.returnValue = returnValue;
    }

    public static class ReturnValueBean {
        private String isTree;
        /**
         * dictValue : 8
         * dictId : INC_TYPE:1428fe44-159d-4d33-ad71-58f0cd79a410
         * dictParentValue : 1
         * dictName : 数据库
         */

        private List<DictDatasBean> dictDatas;

        public String getIsTree() {
            return isTree;
        }

        public void setIsTree(String isTree) {
            this.isTree = isTree;
        }

        public List<DictDatasBean> getDictDatas() {
            return dictDatas;
        }

        public void setDictDatas(List<DictDatasBean> dictDatas) {
            this.dictDatas = dictDatas;
        }

        public static class DictDatasBean {
            private String dictValue;
            private String dictId;
            private String dictParentValue;
            private String dictName;

            public String getDictValue() {
                return dictValue;
            }

            public void setDictValue(String dictValue) {
                this.dictValue = dictValue;
            }

            public String getDictId() {
                return dictId;
            }

            public void setDictId(String dictId) {
                this.dictId = dictId;
            }

            public String getDictParentValue() {
                return dictParentValue;
            }

            public void setDictParentValue(String dictParentValue) {
                this.dictParentValue = dictParentValue;
            }

            public String getDictName() {
                return dictName;
            }

            public void setDictName(String dictName) {
                this.dictName = dictName;
            }
        }
    }
}
