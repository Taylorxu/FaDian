package com.wisesignsoft.OperationManagement.net.response;

/**
 * Created by ycs on 2016/12/1.
 */

public class OpenTaskDetailResponse extends BaseResponse {

    /**
     * state : true
     * PIKEY : P1610130003
     * CURRENT_TASKID : 330260
     * firstrequest :
     * taskNodeType : common
     * formDocument : &lt;?xml version="1.0" encoding="UTF-8"?&gt;
     &lt;BMForm-config&gt;
     &lt;BMForm bmModelName="PRO" superBmModelName="PROCESS_BASE" bmIcon="undefined" bmHelp="undefined" bmDisplayName="问题流程表单" width="700" height="700" order="0" modelName="PRO_PROCESS" dmDisplayName="问题流程" hasFlowChart="true" hasDataRelation="false" hasAuditRecord="false" hasOperLog="false" hasKB="false" hasbmHelp="false" conditionJudgment="[{'keyName':'问题状态','value':'问题发起--问题合规审核','operation':'==','key':'PROCESS_STATE==PRO_STA:e9f85444-f18c-4fea-86cc-55be4e2580fa','valueDesc':'提交审核','valueName':{'value':'PRO_STA:e9f85444-f18c-4fea-86cc-55be4e2580fa','name':'问题新建'}}]" sortingSet="null" specificValueUpdate=""&gt;
     &lt;BMFormValidate bmValidateJson=""/&gt;
     &lt;BMFormProcessor bmProcessorJson=""/&gt;
     &lt;BMFormDataLinkage dataLinkageJson="[{'expreDesc':'[\n{\n\t\'dataLinkCondition\': \'PRO_URG=低 and PRO_EFF=一般\',\n\t\'results\': [{\n\t\t\'dataLinkControlType\': \'1\',\n\t\t\'resultAttr\': \'PRO_PRI\',\n\t\t\'resultValue\': \'低\'\n\t}]\n},\n{\n\t\'dataLinkCondition\': \'PRO_URG=低 and PRO_EFF=严重\',\n\t\'results\': [{\n\t\t\'dataLinkControlType\': \'1\',\n\t\t\'resultAttr\': \'PRO_PRI\',\n\t\t\'resultValue\': \'低\'\n\t}]\n},\n{\n\t\'dataLinkCondition\': \'PRO_URG=低 and PRO_EFF=重大\',\n\t\'results\': [{\n\t\t\'dataLinkControlType\': \'1\',\n\t\t\'resultAttr\': \'PRO_PRI\',\n\t\t\'resultValue\': \'中\'\n\t}]\n},\n{\n\t\'dataLinkCondition\': \'PRO_URG=中 and PRO_EFF=一般\',\n\t\'results\': [{\n\t\t\'dataLinkControlType\': \'1\',\n\t\t\'resultAttr\': \'PRO_PRI\',\n\t\t\'resultValue\': \'低\'\n\t}]\n},\n{\n\t\'dataLinkCondition\': \'PRO_URG=中 and PRO_EFF=严重\',\n\t\'results\': [{\n\t\t\'dataLinkControlType\': \'1\',\n\t\t\'resultAttr\': \'PRO_PRI\',\n\t\t\'resultValue\': \'中\'\n\t}]\n},\n{\n\t\'dataLinkCondition\': \'PRO_URG=中 and PRO_EFF=重大\',\n\t\'results\': [{\n\t\t\'dataLinkControlType\': \'1\',\n\t\t\'resultAttr\': \'PRO_PRI\',\n\t\t\'resultValue\': \'高\'\n\t}]\n},\n{\n\t\'dataLinkCondition\': \'PRO_URG=高 and PRO_EFF=一般\',\n\t\'results\': [{\n\t\t\'dataLinkControlType\': \'1\',\n\t\t\'resultAttr\': \'PRO_PRI\',\n\t\t\'resultValue\': \'中\'\n\t}]\n},\n{\n\t\'dataLinkCondition\': \'PRO_URG=高 and PRO_EFF=严重\',\n\t\'results\': [{\n\t\t\'dataLinkControlType\': \'1\',\n\t\t\'resultAttr\': \'PRO_PRI\',\n\t\t\'resultValue\': \'高\'\n\t}]\n},\n{\n\t\'dataLinkCondition\': \'PRO_URG=高 and PRO_EFF=重大\',\n\t\'results\': [{\n\t\t\'dataLinkControlType\': \'1\',\n\t\t\'resultAttr\': \'PRO_PRI\',\n\t\t\'resultValue\': \'高\'\n\t}]\n}\n]','nodeType':'ComboBox','name':'紧急度','mx_internal_uid':'49B76AB7-B397-2D8E-C42A-9F60340FEED8','methodName':'commonDataLinkage','methodDesc':'通用联动方法','ID':'combobox_4'},{'expreDesc':'[\n{\n\t\'dataLinkCondition\': \'PRO_URG=低 and PRO_EFF=一般\',\n\t\'results\': [{\n\t\t\'dataLinkControlType\': \'1\',\n\t\t\'resultAttr\': \'PRO_PRI\',\n\t\t\'resultValue\': \'低\'\n\t}]\n},\n{\n\t\'dataLinkCondition\': \'PRO_URG=低 and PRO_EFF=严重\',\n\t\'results\': [{\n\t\t\'dataLinkControlType\': \'1\',\n\t\t\'resultAttr\': \'PRO_PRI\',\n\t\t\'resultValue\': \'低\'\n\t}]\n},\n{\n\t\'dataLinkCondition\': \'PRO_URG=低 and PRO_EFF=重大\',\n\t\'results\': [{\n\t\t\'dataLinkControlType\': \'1\',\n\t\t\'resultAttr\': \'PRO_PRI\',\n\t\t\'resultValue\': \'中\'\n\t}]\n},\n{\n\t\'dataLinkCondition\': \'PRO_URG=中 and PRO_EFF=一般\',\n\t\'results\': [{\n\t\t\'dataLinkControlType\': \'1\',\n\t\t\'resultAttr\': \'PRO_PRI\',\n\t\t\'resultValue\': \'低\'\n\t}]\n},\n{\n\t\'dataLinkCondition\': \'PRO_URG=中 and PRO_EFF=严重\',\n\t\'results\': [{\n\t\t\'dataLinkControlType\': \'1\',\n\t\t\'resultAttr\': \'PRO_PRI\',\n\t\t\'resultValue\': \'中\'\n\t}]\n},\n{\n\t\'dataLinkCondition\': \'PRO_URG=中 and PRO_EFF=重大\',\n\t\'results\': [{\n\t\t\'dataLinkControlType\': \'1\',\n\t\t\'resultAttr\': \'PRO_PRI\',\n\t\t\'resultValue\': \'高\'\n\t}]\n},\n{\n\t\'dataLinkCondition\': \'PRO_URG=高 and PRO_EFF=一般\',\n\t\'results\': [{\n\t\t\'dataLinkControlType\': \'1\',\n\t\t\'resultAttr\': \'PRO_PRI\',\n\t\t\'resultValue\': \'中\'\n\t}]\n},\n{\n\t\'dataLinkCondition\': \'PRO_URG=高 and PRO_EFF=严重\',\n\t\'results\': [{\n\t\t\'dataLinkControlType\': \'1\',\n\t\t\'resultAttr\': \'PRO_PRI\',\n\t\t\'resultValue\': \'高\'\n\t}]\n},\n{\n\t\'dataLinkCondition\': \'PRO_URG=高 and PRO_EFF=重大\',\n\t\'results\': [{\n\t\t\'dataLinkControlType\': \'1\',\n\t\t\'resultAttr\': \'PRO_PRI\',\n\t\t\'resultValue\': \'高\'\n\t}]\n}\n]','nodeType':'ComboBox','name':'影响度','mx_internal_uid':'474A3B5A-41C7-1291-B2FF-9F6047009F4A','methodName':'commonDataLinkage','methodDesc':'通用联动方法','ID':'combobox_5'}]"/&gt;
     &lt;Section ID="Section0" label="问题发起" isCurrent="true"&gt;
     &lt;TextInput seeHisWorkOrderInfo="false" x="20.5" isVisible="true" y="39" value="" modified="true" hasBusinessKey="false" width="644" required="true" height="23" bmModelName="PRO" maxChars="85" dmAttrDisplayName="工单标题" dmAttrName="TITLE" ID="textinput_1" isDispatcherEvent="false" identityRule="null" name="问题标题"/&gt;
     &lt;TextArea seeHisWorkOrderInfo="false" x="22" isVisible="true" y="124" value="" modified="true" hasBusinessKey="false" width="642" required="true" height="110" name="问题描述" bmModelName="PRO" dmAttrDisplayName="问题描述" maxChars="333" ID="textarea_1" isDispatcherEvent="false" addToItemIds="" dmAttrName="PRO_DES"/&gt;
     &lt;TreeData seeHisWorkOrderInfo="false" x="352" isVisible="true" y="84.1" value="" modified="true" hasBusinessKey="false" srclib_label="问题分类" srclib="PRO_VER" required="true" isMult="false" ID="treedata_1" bmModelName="PRO" dmAttrDisplayName="问题分类" dmAttrName="PRO_VER" value_label="null" height="24" name="问题分类" width="314" isDispatcherEvent="false"/&gt;
     &lt;ComboBox seeHisWorkOrderInfo="false" x="406" isVisible="true" y="249.15" value="" modified="true" hasBusinessKey="false" srclib_label="问题来源" srclib="PRO_RES" required="true" height="23" ID="combobox_2" bmModelName="PRO" dmAttrDisplayName="问题来源" dmAttrName="PRO_SRC" value_label="null" isDispatcherEvent="false" name="问题来源" width="258"/&gt;
     &lt;ComboBox seeHisWorkOrderInfo="false" x="10" isVisible="true" y="256.3" value="" modified="true" hasBusinessKey="false" srclib_label="问题紧急程度" srclib="PRO_ERG" required="true" height="23" ID="combobox_6" bmModelName="PRO" dmAttrDisplayName="问题优先级" dmAttrName="PRO_PRI" value_label="null" isDispatcherEvent="false" name="问题优先级" width="325"/&gt;
     &lt;DataDisplayUser x="35" isVisible="true" y="358" value="2967e352-8749-4c3a-b377-008ae1f0395d" modified="false" hasBusinessKey="false" displayName="系统管理员" required="false" height="23" ID="datadisplayuser_9" bmModelName="PRO" dmAttrDisplayName="问题发起人" dmAttrName="PRO_START_PRO" value_label="当前处理人" name="问题发起人" width="240"/&gt;
     &lt;DataDisplayDate x="417" isVisible="true" y="355.3" value="2016-12-01 12:39:50" modified="false" hasBusinessKey="false" displayName="2016-12-01 12:39:50" required="false" height="23" ID="datadisplaydate_9" bmModelName="PRO" dmAttrDisplayName="问题发起时间" dmAttrName="PRO_START_TIME" value_label="null" name="问题发起时间" width="240"/&gt;
     &lt;TextInput seeHisWorkOrderInfo="false" x="471" isVisible="false" y="421" value="P1610130003" modified="false" hasBusinessKey="false" width="200" required="false" height="23" bmModelName="PRO" maxChars="16" dmAttrDisplayName="单号" dmAttrName="PIKEY" ID="textinput_3" isDispatcherEvent="false" identityRule="null" name="问题编号ID"/&gt;
     &lt;TextInput seeHisWorkOrderInfo="false" x="20" isVisible="true" y="298" value="10%" modified="true" hasBusinessKey="false" width="200" required="false" height="23" bmModelName="PRO" maxChars="85" dmAttrDisplayName="问题进度" dmAttrName="PRO_PROCESS" ID="textinput_4" isDispatcherEvent="false" identityRule="null" name="问题进度"/&gt;
     &lt;ComboBox seeHisWorkOrderInfo="false" x="21" isVisible="true" y="84" value="PRO_STA:e9f85444-f18c-4fea-86cc-55be4e2580fa" modified="false" hasBusinessKey="false" srclib_label="问题状态" srclib="PRO_STA" required="true" height="23" ID="combobox_12" bmModelName="PRO" dmAttrDisplayName="问题状态" dmAttrName="PRO_STATE" value_label="问题新建" isDispatcherEvent="false" name="问题状态" width="270"/&gt;
     &lt;DateFeild seeHisWorkOrderInfo="false" x="383" isUpdateBeforeDate="false" isVisible="true" y="297" value="2016-12-01 12:39:50" modified="true" hasBusinessKey="false" width="280" required="false" height="25" name="期望实施时间" bmModelName="PRO" dmAttrDisplayName="期望实施时间" dmAttrName="EXP_TIME" value_label="当前时间" hasDateTime="true" isUpdateLaterDate="false" ID="datefeild_1" isDispatcherEvent="false"/&gt;
     &lt;/Section&gt;
     &lt;Section ID="Section1" label="审核"&gt;
     &lt;DataDisplayUser x="26" isVisible="true" y="83.25" value="" modified="false" hasBusinessKey="false" displayName="" required="false" height="23" ID="datadisplayuser_10" bmModelName="PRO" dmAttrDisplayName="问题经理签名" dmAttrName="PRO_NAME" value_label="" name="问题经理签名" width="240"/&gt;
     &lt;DataDisplayDate x="350" isVisible="true" y="79" value="" modified="false" hasBusinessKey="false" displayName="" required="false" height="22" ID="datadisplaydate_10" bmModelName="PRO" dmAttrDisplayName="问题经理审核时间" dmAttrName="PRO_TIME" value_label="" name="问题经理审核时间" width="304"/&gt;
     &lt;TextInput seeHisWorkOrderInfo="false" x="27" isVisible="true" y="29" value="问题已确认，同意分派。" modified="false" hasBusinessKey="false" width="649" required="false" height="23" bmModelName="PRO" maxChars="85" dmAttrDisplayName="问题经理意见" dmAttrName="PRO_OPI" ID="textinput_5" isDispatcherEvent="false" identityRule="null" name="问题经理意见"/&gt;
     &lt;/Section&gt;
     &lt;Section ID="Section2" label="问题原因调查与解决方案制定"&gt;
     &lt;TextArea seeHisWorkOrderInfo="false" x="37" isVisible="true" y="14" value="" modified="false" hasBusinessKey="false" width="626" required="false" height="110" name="问题原因" bmModelName="PRO" dmAttrDisplayName="根本原因分析" maxChars="333" ID="textarea_3" isDispatcherEvent="false" addToItemIds="" dmAttrName="ROO_RES_ANA"/&gt;
     &lt;TextArea seeHisWorkOrderInfo="false" x="13" isVisible="true" y="149.5" value="" modified="false" hasBusinessKey="false" width="650" required="false" height="110" name="问题解决方案" bmModelName="PRO" dmAttrDisplayName="解决方案" maxChars="333" ID="textarea_4" isDispatcherEvent="false" addToItemIds="" dmAttrName="PRO_SOL"/&gt;
     &lt;DataDisplayUser x="27" isVisible="true" y="524.15" value="" modified="false" hasBusinessKey="false" displayName="" required="false" height="23" ID="datadisplayuser_3" bmModelName="PRO" dmAttrDisplayName="问题处理人" dmAttrName="PRO_DEA_PER" value_label="" name="问题处理人" width="240"/&gt;
     &lt;DataDisplayDate x="431" isVisible="true" y="524.15" value="" modified="false" hasBusinessKey="false" displayName="" required="false" height="23" ID="datadisplaydate_3" bmModelName="PRO" dmAttrDisplayName="问题处理时间" dmAttrName="PRO_DEA_TIME" value_label="" name="问题处理时间" width="240"/&gt;
     &lt;ResModelComponents x="27.5" isVisible="true" y="281.5" value="" modified="false" hasBusinessKey="false" resModelConfigure="{'displayName':'配置项表单顶级模型','attrDatasOfForm':[{'mx_internal_uid':'BB3B3D17-EC47-A1C4-4795-9F73249EFE58','hasBusinessKey':false,'hasMultiChoice':false,'hasModified':true,'dmAttrDisplayName':'配置项编号','dmAttrName':'CI_CODE','required':false,'hasDateTime':false,'columnLinkType':null,'id':'textinput_3','type':'TextInput','name':'配置项编号','orderType':0,'columnWidth':200,'selected':true,'dictModelName':null},{'mx_internal_uid':'FD581770-0F77-0F57-5ABF-9F7324B21A93','hasBusinessKey':false,'hasMultiChoice':false,'hasModified':true,'dmAttrDisplayName':'配置项名称','dmAttrName':'NAME','required':false,'hasDateTime':false,'columnLinkType':null,'id':'textinput_4','type':'TextInput','name':'配置项名称','orderType':0,'columnWidth':200,'selected':true,'dictModelName':null}],'className':'CI_FORM'}" required="false" height="221" bmModelName="PRO" width="639" dmAttrDisplayName="关联配置项" dmAttrName="LIN_CON" name="关联配置项" ID="delcomponents_1"/&gt;
     &lt;/Section&gt;
     &lt;Section ID="Section3" label="问题原因与方案评审"&gt;
     &lt;TextArea seeHisWorkOrderInfo="false" x="49" isVisible="true" y="25" value="" modified="false" hasBusinessKey="false" width="626" required="false" height="101" name="评审结果" bmModelName="PRO" dmAttrDisplayName="评审结果" maxChars="333" ID="textarea_8" isDispatcherEvent="false" addToItemIds="" dmAttrName="PRO_RES"/&gt;
     &lt;DataDisplayUser x="52" isVisible="true" y="176" value="" modified="false" hasBusinessKey="false" displayName="" required="false" height="23" ID="datadisplayuser_11" bmModelName="PRO" dmAttrDisplayName="问题经理签名" dmAttrName="PRO_NAME_A" value_label="" name="问题经理签名" width="240"/&gt;
     &lt;DataDisplayDate x="400" isVisible="true" y="176" value="" modified="false" hasBusinessKey="false" displayName="" required="false" height="19" ID="datadisplaydate_11" bmModelName="PRO" dmAttrDisplayName="问题经理审核时间" dmAttrName="PRO_TIME_A" value_label="" name="问题经理审核时间" width="274"/&gt;
     &lt;/Section&gt;
     &lt;Section ID="Section4" label="问题实施"&gt;
     &lt;DataDisplayUser x="22" isVisible="true" y="174.3" value="" modified="false" hasBusinessKey="false" displayName="" required="false" height="23" ID="datadisplayuser_6" bmModelName="PRO" dmAttrDisplayName="问题解决人" dmAttrName="PRO_SOL_PER" value_label="" name="问题解决人" width="240"/&gt;
     &lt;DataDisplayDate x="387" isVisible="true" y="175.3" value="" modified="false" hasBusinessKey="false" displayName="" required="false" height="23" ID="datadisplaydate_6" bmModelName="PRO" dmAttrDisplayName="问题解决时间" dmAttrName="PRO_SOL_TIME" value_label="" name="问题解决时间" width="240"/&gt;
     &lt;TextArea seeHisWorkOrderInfo="false" x="22" isVisible="true" y="24" value="" modified="false" hasBusinessKey="false" width="631" required="false" height="103" name="问题实施结果" bmModelName="PRO" dmAttrDisplayName="问题实施结果" maxChars="333" ID="textarea_9" isDispatcherEvent="false" addToItemIds="" dmAttrName="PRO_RESULT"/&gt;
     &lt;/Section&gt;
     &lt;Section ID="Section5" label="问题验证"&gt;
     &lt;ComboBox seeHisWorkOrderInfo="false" x="46" isVisible="true" y="32.6" value="" modified="false" hasBusinessKey="false" srclib_label="问题验证结果" srclib="PRO_VER_RES" required="false" height="23" ID="combobox_11" bmModelName="PRO" dmAttrDisplayName="问题验证结果" dmAttrName="PRO_VER_RES" value_label="null" isDispatcherEvent="false" name="问题验证结果" width="278"/&gt;
     &lt;TextArea seeHisWorkOrderInfo="false" x="47" isVisible="true" y="89" value="" modified="false" hasBusinessKey="false" width="618" required="false" height="87" name="问题验证说明" bmModelName="PRO" dmAttrDisplayName="问题验证说明" maxChars="333" ID="textarea_10" isDispatcherEvent="false" addToItemIds="" dmAttrName="PRO_VER_DES"/&gt;
     &lt;DataDisplayUser x="47" isVisible="true" y="211.15" value="" modified="false" hasBusinessKey="false" displayName="" required="false" height="23" ID="datadisplayuser_12" bmModelName="PRO" dmAttrDisplayName="问题验证人" dmAttrName="PRO_VER_NAME" value_label="" name="问题验证人" width="240"/&gt;
     &lt;DataDisplayDate x="382" isVisible="true" y="211.15" value="" modified="false" hasBusinessKey="false" displayName="" required="false" height="23" ID="datadisplaydate_12" bmModelName="PRO" dmAttrDisplayName="问题验证时间" dmAttrName="PRO_VER_TIME" value_label="" name="问题验证时间" width="240"/&gt;
     &lt;/Section&gt;
     &lt;Section ID="Section6" label="问题关闭"&gt;
     &lt;TextArea seeHisWorkOrderInfo="false" x="47" isVisible="true" y="15.5" value="" modified="false" hasBusinessKey="false" width="605" required="false" height="140" name="问题回顾说明" bmModelName="PRO" dmAttrDisplayName="回顾描述" maxChars="85" ID="textarea_7" isDispatcherEvent="false" addToItemIds="" dmAttrName="REV_DES"/&gt;
     &lt;DataDisplayUser x="47" isVisible="true" y="188.95" value="" modified="false" hasBusinessKey="false" displayName="" required="false" height="23" ID="datadisplayuser_7" bmModelName="PRO" dmAttrDisplayName="问题解决人" dmAttrName="REV_SOL_PER" value_label="" name="问题关闭人" width="240"/&gt;
     &lt;DataDisplayDate x="372" isVisible="true" y="189.95" value="" modified="false" hasBusinessKey="false" displayName="" required="false" height="23" ID="datadisplaydate_7" bmModelName="PRO" dmAttrDisplayName="回顾问题解决时间" dmAttrName="REV_SOL_TIME" value_label="" name="问题关闭时间" width="240"/&gt;
     &lt;/Section&gt;
     &lt;Section ID="Section7" label="关联工单"&gt;
     &lt;WORelating x="10.5" isVisible="true" y="44.5" value="" modified="false" hasBusinessKey="false" dmAttrName="RELEATED_WORKORDERS" required="false" height="293" bmModelName="PRO" dmAttrDisplayName="关联工单" width="660" name="关联的工单号" isShowBorder="true" ID="worelating_1"/&gt;
     &lt;/Section&gt;
     &lt;Button ID="submit"&gt;&lt;nextNode name="问题发起--问题合规审核" to="审核" isDefaultPath="false" taskStrategy="{'strategyKey':'assignee','strategyValue':'{\'roleName\':\'问题经理\',\'roleId\':\'8a0c18aa-1967-4f2f-8df6-7558bac2a891\'}'}" specificValueUpdate="{'values':[{'formItem':{'seeHisWorkOrderInfo':false,'isCanDelete':true,'isCanAdd':true,'configModifiedField':null,'id':'combobox_12','isFilterUser':false,'nodeType':'ComboBox','name':'问题状态','allColumnsJson':null,'modified':false,'mx_internal_uid':'02FEEA2A-0603-3926-471F-0041A75019F9','sectionId':'Section0','modelName':'PRO_PROCESS','isVisible':true,'srclib':'PRO_STA','precision':null,'parentDeptJSON':null,'dmAttrName':'PRO_STATE','ID':'combobox_12','dataProvider':null,'isExecute':false,'isMult':false,'value':'','srclib_label':'问题状态','value_label':'null','dmAttrDisplayName':'问题状态','required':true,'maxChars':null,'isCanUpdate':true,'bmModelName':'PRO'},'formItemValue':{'value':'PRO_STA:61f04fa2-ad6c-45b5-ac63-37219c379f9a','name':'待审核'}},{'formItem':{'seeHisWorkOrderInfo':false,'isCanDelete':true,'isCanAdd':true,'configModifiedField':null,'parentDeptJSON':null,'isFilterUser':false,'nodeType':'TextInput','name':'问题进度','id':'textinput_4','modified':true,'mx_internal_uid':'07BF2DF5-C792-D274-2800-00687A9C03F6','sectionId':'Section0','modelName':'PRO_PROCESS','isVisible':true,'srclib':'','precision':null,'allColumnsJson':null,'dmAttrName':'PRO_PROCESS','ID':'textinput_4','dataProvider':null,'isExecute':false,'isMult':false,'value':'20%','srclib_label':null,'value_label':'20%','dmAttrDisplayName':'问题进度','required':false,'maxChars':85,'isCanUpdate':true,'bmModelName':'PRO'},'formItemValue':'10%'}]}" nameDesc="提交审核" isDependCondition="false"/&gt;&lt;/Button&gt;
     &lt;/BMForm&gt;
     &lt;/BMForm-config&gt;
     */

    private boolean state;
    private String PIKEY;
    private String CURRENT_TASKID;
    private String firstrequest;
    private String taskNodeType;
    private String formDocument;

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getPIKEY() {
        return PIKEY;
    }

    public void setPIKEY(String PIKEY) {
        this.PIKEY = PIKEY;
    }

    public String getCURRENT_TASKID() {
        return CURRENT_TASKID;
    }

    public void setCURRENT_TASKID(String CURRENT_TASKID) {
        this.CURRENT_TASKID = CURRENT_TASKID;
    }

    public String getFirstrequest() {
        return firstrequest;
    }

    public void setFirstrequest(String firstrequest) {
        this.firstrequest = firstrequest;
    }

    public String getTaskNodeType() {
        return taskNodeType;
    }

    public void setTaskNodeType(String taskNodeType) {
        this.taskNodeType = taskNodeType;
    }

    public String getFormDocument() {
        return formDocument;
    }

    public void setFormDocument(String formDocument) {
        this.formDocument = formDocument;
    }
}
