package com.wisesignsoft.OperationManagement.net.response;

/**
 * Created by ycs on 2016/12/20.
 */

public class CreateCiResponse extends BaseResponse {

    /**
     * formDocument : &lt;?xml version="1.0" encoding="UTF-8"?&gt;
     &lt;BMForm-config&gt;
     &lt;BMForm bmModelName="res" superBmModelName="ACCOUNT_FORM" bmIcon="" bmHelp="" bmDisplayName="服务报告管理" width="700" height="550" order="0" modelName="REPORTS" dmDisplayName="报告管理" hasFlowChart="false" hasDataRelation="false" hasAuditRecord="false" hasOperLog="false" hasKB="true" hasbmHelp="true"&gt;
     &lt;BMFormValidate bmValidateJson="null"/&gt;
     &lt;BMFormProcessor bmProcessorJson="null"/&gt;
     &lt;BMFormDataLinkage dataLinkageJson="null"/&gt;
     &lt;Section ID="Section0" label="服务报告信息" isCurrent="true"&gt;
     &lt;BorderGroup ID="bordergroup_1" x="72" y="74" styleJson="null" name="项目信息" isVisible="true" width="562" height="142" value=""/&gt;
     &lt;TextInput maxChars="85" hasBusinessKey="false" identityRule="null" dmAttrName="RES_RENA" y="32" value="" bmModelName="res" x="84" dmAttrDisplayName="服务报告名称" name="服务报告名称" width="512" height="23" ID="textinput_1" isDispatcherEvent="false" required="false" isVisible="true" seeHisWorkOrderInfo="false" modified="true"/&gt;
     &lt;DataDisplayUser hasBusinessKey="false" dmAttrName="RES_UPP" y="481.8" value="2967e352-8749-4c3a-b377-008ae1f0395d" bmModelName="res" x="107" dmAttrDisplayName="提交人" width="240" height="23" ID="datadisplayuser_1" value_label="创建人" name="提交人" required="false" isVisible="true" displayName="系统管理员" modified="true"/&gt;
     &lt;DataDisplayDate hasBusinessKey="false" dmAttrName="RES_UPT" y="482.85" value="2016-12-20 13:10:33" bmModelName="res" x="450" dmAttrDisplayName="提交时间" width="240" height="23" ID="datadisplaydate_1" value_label="当前时间" name="提交时间" required="false" isVisible="true" displayName="2016-12-20 13:10:33" modified="true"/&gt;
     &lt;Attachment hasBusinessKey="false" templateJson="" dmAttrName="RES_ANNX" y="239" value="" isDeleteAttachment="false" bmModelName="res" x="97" dmAttrDisplayName="服务报告附件" width="494" height="205" ID="attachment_1" name="报告附件上传" required="false" isVisible="true" modified="true"/&gt;
     &lt;ResModelSelect hasBusinessKey="false" name="项目信息选择" dmAttrName="OBJ_PROI" y="91" value="" isMult="false" allColumnsJson="null" dmAttrDisplayName="项目信息" width="153" queryScope="" height="25" ID="resmodelselect_1" x="476" realTimeUpdate="true" resModelValueJson="{'dmAttrName':'OBJ_PROI','dmDisplayName':'合同信息','bmModelName':'res','toBmModelName':'CONTRACTS','bmDisplayName':'合同信息','configValueJson':[{'fromDmAttrName':{'dmAttrName':'CUST_NO','id':'textinput_4'},'mx_internal_uid':'AE0708F2-2CC4-7F07-54AC-BE490AC64CB7','toFmAttrName':{'dmAttrName':'CUST_NO','id':'textinput_1'}},{'fromDmAttrName':{'dmAttrName':'CUST_NAME','id':'textinput_5'},'mx_internal_uid':'0039513D-25FF-5019-4F6D-BE49293E38D1','toFmAttrName':{'dmAttrName':'CUST_NAME','id':'textinput_2'}},{'fromDmAttrName':{'dmAttrName':'PROJ_NO','id':'textinput_2'},'mx_internal_uid':'FCD5AD9B-5394-C306-B3D8-BE4946EF4CDA','toFmAttrName':{'dmAttrName':'PROJ_NO','id':'textinput_4'}},{'fromDmAttrName':{'dmAttrName':'PROJ_NAME','id':'textinput_3'},'mx_internal_uid':'9033CAC8-F0EA-CAC5-1623-BE49CF85EC28','toFmAttrName':{'dmAttrName':'PROJ_NAME','id':'textinput_5'}},{'fromDmAttrName':{'dmAttrName':'CUST_DIS','id':'combobox_1'},'mx_internal_uid':'62D66280-25E7-93A4-7013-BE49F58930E5','toFmAttrName':{'dmAttrName':'CUST_DIS','id':'combobox_6'}}]}" bmModelName="res" isDispatcherEvent="false" isVisible="true" modified="true"/&gt;
     &lt;TextInput maxChars="85" hasBusinessKey="false" identityRule="null" dmAttrName="PROJ_NO" y="117" value="" bmModelName="res" x="87" dmAttrDisplayName="项目编号" name="项目编号" width="227" height="23" ID="textinput_2" isDispatcherEvent="false" required="false" isVisible="true" seeHisWorkOrderInfo="false" modified="true"/&gt;
     &lt;TextInput maxChars="85" hasBusinessKey="false" identityRule="null" dmAttrName="PROJ_NAME" y="117" value="" bmModelName="res" x="333" dmAttrDisplayName="项目名称" name="项目名称" width="283" height="23" ID="textinput_3" isDispatcherEvent="false" required="false" isVisible="true" seeHisWorkOrderInfo="false" modified="true"/&gt;
     &lt;TextInput maxChars="85" hasBusinessKey="false" identityRule="null" dmAttrName="CUST_NO" y="143" value="" bmModelName="res" x="87" dmAttrDisplayName="客户编号" name="客户编号" width="228" height="23" ID="textinput_4" isDispatcherEvent="false" required="false" isVisible="true" seeHisWorkOrderInfo="false" modified="true"/&gt;
     &lt;TextInput maxChars="85" hasBusinessKey="false" identityRule="null" dmAttrName="CUST_NAME" y="143" value="" bmModelName="res" x="333" dmAttrDisplayName="客户姓名" name="客户名称" width="284" height="23" ID="textinput_5" isDispatcherEvent="false" required="false" isVisible="true" seeHisWorkOrderInfo="false" modified="true"/&gt;
     &lt;ComboBox hasBusinessKey="false" name="区域" dmAttrName="CUST_DIS" y="169" value="" bmModelName="res" x="111" dmAttrDisplayName="区域" width="205" srclib="CUST_DIS" srclib_label="区域" height="23" ID="combobox_1" value_label="null" isDispatcherEvent="false" required="false" isVisible="true" seeHisWorkOrderInfo="false" modified="true"/&gt;
     &lt;/Section&gt;
     &lt;Button ID="submit"/&gt;
     &lt;/BMForm&gt;
     &lt;/BMForm-config&gt;
     */

    private ReturnValueBean returnValue;

    public ReturnValueBean getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(ReturnValueBean returnValue) {
        this.returnValue = returnValue;
    }

    public static class ReturnValueBean {
        private String formDocument;

        public String getFormDocument() {
            return formDocument;
        }

        public void setFormDocument(String formDocument) {
            this.formDocument = formDocument;
        }
    }
}
